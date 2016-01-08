package org.lare96.pastee

import java.net.{URL, URLConnection}

import org.lare96.pastee.util.HttpRequestData

import scala.util.Try
import scala.xml.XML

/** A class representing and holding functionality for submitting paste upload requests to Paste.ee.
  *
  * @param key The API key, `public` for an anonymous key.
  * @param description The description of the paste.
  * @param language The syntax highlighting that will be applied to the paste.
  * @param paste The actual paste content.
  * @param encrypted If the paste should be encrypted via GeSHi.
  * @param useHttps If the `HTTPS` protocol should be used instead of `HTTP`.
  * @param expireTime The amount of time in hours that this paste will expire after.
  * @param expireViews The amount of views that this paste must acquire in order to expire.
  * @author lare96 <http://github.org/lare96>
  */
final class PasteeUploadRequest(key: String = "public", description: String, language: String = "", paste: String,
                                encrypted: Boolean = false, useHttps: Boolean = true, expireTime: Int = 0, expireViews: Int = 0) {

  /** Synchronously attempts to upload a paste to Paste.ee with the specified settings.
    *
    * @return A `Try` instance holding the result of this request.
    */
  def sendAndWait = Try[PasteeUploadResponse] {
    val connection = new URL((if (useHttps) "https" else "http") + "://paste.ee/api").openConnection

    connection.setDoOutput(true)
    connection.setDoInput(true)

    encodeHttpRequest(connection)

    decodeHttpResponse(connection)
  }

  /** Encodes the `HTTP` request and writes it to the `OutputStream` from the `URLConnection`.
    *
    * @param connection The `URLConnection` to retrieve the `OutputStream` from.
    */
  private def encodeHttpRequest(connection: URLConnection) = {
    if (expireTime != 0 && expireViews != 0) {
      throw new IllegalStateException("cannot have both an expire time and view value.")
    }

    val requestData = new HttpRequestData

    requestData("key") = key
    requestData("description") = description
    requestData("paste") = paste

    if (!language.isEmpty) {
      requestData("language") = language
    }

    if (encrypted) {
      requestData("encrypted") = "1"
    }

    if (expireTime != 0) {
      requestData("expire") = expireTime.toString
    } else if (expireViews != 0) {
      requestData("expire") = s"views;$expireViews"
    }

    requestData("format") = "xml"

    val outputStream = connection.getOutputStream
    try {
      requestData.writeData(outputStream)
    } finally {
      outputStream.close()
    }
  }

  /** Decodes the `XML` response to the `HTTP` request.
    *
    * @param connection The `URLConnection` to retrieve the `InputStream` from.
    * @return The `PasteeUploadResponse` to the upload request.
    */
  private def decodeHttpResponse(connection: URLConnection): PasteeUploadResponse = {
    val inputStream = connection.getInputStream
    try {
      val xml = XML.load(inputStream)
      val responseStatus = (xml \ "status").text

      if (responseStatus == "error") {

        val errorCode = (xml \ "errorcode").text
        val errorMsg = (xml \ "error").text

        throw new PasteeResponseException(Integer.parseInt(errorCode), errorMsg)

      } else if (responseStatus == "success") {

        val id = (xml \ "paste" \ "id").text
        val link = (xml \ "paste" \ "link").text
        val raw = (xml \ "paste" \ "raw").text
        val download = (xml \ "paste" \ "download").text
        val min = (xml \ "paste" \ "min").text

        return new PasteeUploadResponse(id, link, raw, download, min)
      }
      throw new IllegalStateException(s"invalid response status: $responseStatus")
    } finally {
      inputStream.close()
    }
  }
}


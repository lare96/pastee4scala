package org.lare96.pastee

import java.net.URL

import scala.xml.{NodeSeq, XML}

/** A case class representing an upload request for a Paste.ee paste. After this `PasteeUploadRequest` class is instantiated it
  * can be manipulated to perform upload operations.
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
final case class PasteeUploadRequest(key: String = "public", description: String, language: String = "", paste: String,
                                     encrypted: Boolean = false, useHttps: Boolean = true, expireTime: Int = 0, expireViews: Int = 0) {

  /** Synchronously execute the code to download a Paste.ee paste.
    *
    * @return A `PasteeUploadResponse` object containing the result of the request.
    */
  def sendAndWait = send

  /** Open a connection to the Paste.ee API, send a POST request with the data contained within this class, and decode the response
    * from the `InputStream`.
    *
    * @return The `PasteeUploadResponse` to the upload request.
    */
  private def send = {
    val connection = new URL((if (useHttps) "https" else "http") + "://paste.ee/api").openConnection
    connection.setDoOutput(true)

    val outputStream = connection.getOutputStream
    try {
      val requestData = new PasteeUploadData(this)
      requestData.putBytes(outputStream)
    } finally {
      outputStream.close()
    }

    val inputStream = connection.getInputStream
    try {
      decodeXml(XML.load(inputStream))
    } finally {
      inputStream.close()
    }
  }

  /**
    * Decodes an `XML` tree in order to construct a `PasteeUploadResponse` instance.
    *
    * @param xml The `XML` tree to decode.
    * @return The `PasteeUploadResponse` to the upload request.
    */
  private def decodeXml(xml: NodeSeq): PasteeUploadResponse = {
    val responseStatus = (xml \ "status").text

    if (responseStatus == "error") {
      val errorCode = (xml \ "errorcode").text
      val errorMsg = (xml \ "error").text

      return new PasteeErrorUploadResponse(Integer.parseInt(errorCode), errorMsg)

    } else if (responseStatus == "success") {
      val id = (xml \ "paste" \ "id").text
      val link = (xml \ "paste" \ "link").text
      val raw = (xml \ "paste" \ "raw").text
      val download = (xml \ "paste" \ "download").text
      val min = (xml \ "paste" \ "min").text

      return new PasteeSuccessUploadResponse(id, link, raw, download, min)

    }
    throw new IllegalStateException(s"invalid response status: $responseStatus")
  }
}


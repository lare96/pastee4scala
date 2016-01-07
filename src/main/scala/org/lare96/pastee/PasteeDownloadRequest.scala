package org.lare96.pastee

import java.net.URL

import scala.io.Source

/** A case class representing a download request for a Paste.ee paste. After this `PasteeDownloadRequest` class is instantiated it
  * can be manipulated to retrieve the contents of a specified paste.
  *
  * @param id The identifier for the Paste.ee paste that will be downloaded. In other words, the `PasteeSuccessUploadResponse`
  *           `id` value.
  * @param useHttps If the `HTTPS` protocol should be used instead of `HTTP`.
  * @author lare96 <http://github.org/lare96>
  */
final case class PasteeDownloadRequest(id: String, useHttps: Boolean = true) {

  /** Synchronously execute the code to download a Paste.ee paste.
    *
    * @return The contents of the Paste.ee paste `id` that was specified.
    */
  def sendAndWait = send

  /** Open a connection with the link built from the specified `id` value, and read all of the data within its
    * `InputStream`.
    *
    * @return The content within the `InputStream`.
    */
  private def send = {
    val inputStream = new URL((if (useHttps) "https" else "http") + s"://paste.ee/r/$id").openConnection.getInputStream

    Source.fromInputStream(inputStream).mkString
  }
}

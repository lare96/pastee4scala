package org.lare96.pastee

import java.net.URL

import scala.io.Source
import scala.util.Try

/** A class representing and holding functionality for retrieving pastes from Paste.ee.
  *
  * @param id The identifier for the Paste.ee paste that will be downloaded.
  * @param useHttps If the `HTTPS` protocol should be used instead of `HTTP`.
  * @author lare96 <http://github.org/lare96>
  */
final class PasteeDownloadRequest(id: String, useHttps: Boolean = true) {

  /** Synchronously attempts to download a paste from Paste.ee with the specified settings.
    *
    * @return A `Try` instance holding the result of this request.
    */
  def sendAndWait = Try[String] {
    val inputStream = new URL((if (useHttps) "https" else "http") + s"://paste.ee/r/$id").openConnection.getInputStream

    Source.fromInputStream(inputStream).mkString
  }
}

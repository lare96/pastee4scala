package org.lare96.pastee

import java.io.OutputStream

import scala.collection.mutable

/** A wrapper for a `HashMap` that contains the raw data for a POST request and a functions to manipulate that data.
  *
  * @param request The `PasteeUploadRequest` containing the POST request data.
  * @author lare96 <http://github.org/lare96>
  */
final class PasteeUploadRequestData(request: PasteeUploadRequest) {

  /** A `HashMap` that contains the raw POST request data. */
  private val data = mutable.HashMap[String, String]()

  if (request.expireTime != 0 && request.expireViews != 0) {

    // The expire time will overwrite the expire views entry if both are set, so just
    // prevent the user from setting both.
    throw new IllegalStateException("cannot have both an expire time and view value.")
  }

  data("key") = request.key
  data("description") = request.description
  data("paste") = request.paste

  if (!request.language.isEmpty) {
    data("language") = request.language
  }
  if (request.encrypted) {
    data("encrypted") = "1"
  }
  if (request.expireTime != 0) {
    data("expire") = request.expireTime.toString
  } else if (request.expireViews != 0) {
    data("expire") = s"views;${request.expireViews}"
  }
  data("format") = "xml"

  /** @return The data within the `HashMap` in POST request parameter format. */
  override def toString = {
    val request = (for ((k, v) <- data) yield k + '=' + v + '&').mkString

    request.substring(0, request.length - 1)
  }

  /** @return `toString` as an array of bytes added to the specified `OutputStream`. */
  def putBytes(os: OutputStream) = os.write(toString.getBytes)
}
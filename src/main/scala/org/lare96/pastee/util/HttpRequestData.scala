package org.lare96.pastee.util

import java.io.OutputStream

import scala.collection.mutable

/** A wrapper for a `HashMap` that contains the raw data for a `POST` request and functions to manipulate that data.
  *
  * @author lare96 <http://github.org/lare96>
  */
final class HttpRequestData {

  /** A `HashMap` that contains the `POST` request parameters. */
  private val parameters = mutable.HashMap[String, String]()

  /**
    * Adds a new `POST` request parameter to this wrapper.
    *
    * @param key The key of the parameter.
    * @param value The value of the parameter.
    */
  def put(key: String, value: String) = parameters(key) = value

  /** Transforms the data into `POST` request parameter format and writes it as an array of bytes into the specified
    * `OutputStream`.
    *
    * @param os The `OutputStream` to write all the bytes to.
    */
  def writeData(os: OutputStream) = {
    val output = (for ((k, v) <- parameters) yield k + '=' + v + '&').mkString.dropRight(1).getBytes

    os.write(output)
  }
}

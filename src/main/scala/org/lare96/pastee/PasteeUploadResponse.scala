package org.lare96.pastee

/** An abstraction model that determines if an `PasteeUploadRequest` was successful or not.
  *
  * @param _successful If the upload request was successful.
  * @author lare96 <http://github.org/lare96>
  */
class PasteeUploadResponse(_successful: Boolean) {

  /** @return `true` if the upload request was successful, `false` otherwise. */
  def successful = _successful

  /** @return This instance casted to `PasteeSuccessUploadResponse`. Only use if `successful == true`. */
  def toSuccessResponse = this.asInstanceOf[PasteeSuccessUploadResponse]

  /** @return This instance casted to `PasteeErrorUploadResponse`. Only use if `successful == false`. */
  def toErrorResponse = this.asInstanceOf[PasteeErrorUploadResponse]
}

/** A `PasteeUploadResponse` implementation used for when an upload request was successful.
  *
  * @param id The identifier for the paste.
  * @param link The standard link for the paste.
  * @param raw The raw link for the paste.
  * @param download The download link for the paste.
  * @param min The min link for the paste.
  * @author lare96 <http://github.org/lare96>
  */
final case class PasteeSuccessUploadResponse(id: String, link: String, raw: String, download: String, min: String)
  extends PasteeUploadResponse(true)

/** A `PasteeUploadResponse` implementation used for when an upload request was unsuccessful.
  *
  * @param id The error identification opcode.
  * @param msg The error message.
  * @author lare96 <http://github.org/lare96>
  */
final case class PasteeErrorUploadResponse(id: Int, msg: String) extends PasteeUploadResponse(false)
package org.lare96.pastee

/** A case class representing a successful upload response from Paste.ee.
  *
  * @param id The identifier for the paste.
  * @param link The standard link for the paste.
  * @param raw The raw link for the paste.
  * @param download The download link for the paste.
  * @param min The min link for the paste.
  * @author lare96 <http://github.org/lare96>
  */
final case class PasteeUploadResponse(id: String, link: String, raw: String, download: String, min: String)
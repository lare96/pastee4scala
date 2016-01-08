package org.lare96.pastee

/** An `Exception` implementation thrown when an error response is received from Paste.ee.
  *
  * @param id The opcode of the error response message that was received.
  * @param msg The error response message that was received.
  * @author lare96 <http://github.org/lare96>
  */
final class PasteeResponseException(id: Int, msg: String) extends Exception(s"Paste.ee response error [opcode=$id, msg=$msg]")

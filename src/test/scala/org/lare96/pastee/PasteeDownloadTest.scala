package org.lare96.pastee

import java.io.FileNotFoundException

import org.junit.Test

/** A simple JUnit test that ensures Paste.ee downloading functions are working correctly.
  *
  * @author lare96 <http://github.org/lare96>
  */
final class PasteeDownloadTest {

  @Test(expected = classOf[FileNotFoundException])
  def testInvalidId(): Unit = {
    new PasteeDownloadRequest(id = "^#$*#*^*)///^%$$##").sendAndWait
  }
}

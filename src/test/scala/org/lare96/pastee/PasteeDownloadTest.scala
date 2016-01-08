package org.lare96.pastee

import org.junit.Assert.assertTrue
import org.junit.Test

/** A simple JUnit test that ensures Paste.ee downloading functions are working correctly.
  *
  * @author lare96 <http://github.org/lare96>
  */
final class PasteeDownloadTest {

  @Test
  def testInvalidId(): Unit = {
    assertTrue(new PasteeDownloadRequest(id = "^#$*#*^*)///^%$$##").sendAndWait.isFailure)
  }
}

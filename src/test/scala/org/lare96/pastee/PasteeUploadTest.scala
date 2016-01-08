package org.lare96.pastee

import org.junit.Assert._
import org.junit.Test

/** A simple JUnit test that ensures various Paste.ee downloading functions are working correctly.
  *
  * @author lare96 <http://github.org/lare96>
  */
final class PasteeUploadTest {

  @Test
  def testMultipleExpirationValues(): Unit = {
    assertTrue(new PasteeUploadRequest(description = "A test upload description.",
      paste = "Hello, World :)",
      expireTime = 1,
      expireViews = 1).sendAndWait.isFailure)
  }
}

import org.junit.Test
import org.lare96.pastee.PasteeUploadRequest

/** A simple JUnit test that ensures various Paste.ee downloading functions are working correctly.
  *
  * @author lare96 <http://github.org/lare96>
  */
final class PasteeUploadTest {

  @Test(expected = classOf[IllegalStateException])
  def testMultipleExpirationValues(): Unit = {
    new PasteeUploadRequest(description = "A test upload description.",
      paste = "Hello, World :)",
      expireTime = 1,
      expireViews = 1).sendAndWait
  }
}

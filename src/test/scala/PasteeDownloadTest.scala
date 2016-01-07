import java.io.FileNotFoundException

import org.junit.Test
import org.lare96.pastee.PasteeDownloadRequest

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

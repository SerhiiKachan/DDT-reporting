import org.testng.Assert;
import org.testng.annotations.Test;

public class FailedTest {
    @Test(testName = "Failed test")
    public void failTest() {
        Assert.assertEquals(new Object(), new Object());
    }
}

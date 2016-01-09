import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Jakobs on 2015-12-17.
 */
public class StatisticsTest {

    @Test
    public void filePerSecondTest() {
        Statistics statistics = new Statistics();

        Assert.assertEquals(0, statistics.getTotalFilesSent());

        statistics.fileSent();
        statistics.fileSent();

        float savedFps = statistics.getFilesSentPerSecond();
        Assert.assertTrue(savedFps > 0);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertNotEquals(statistics.getFilesSentPerSecond(), savedFps);
    }

    @Test
    public void totalFileCountTest() {
        Statistics statistics = new Statistics();

        Assert.assertEquals(0, statistics.getTotalFilesSent());

        statistics.fileSent();
        statistics.fileSent();

        Assert.assertEquals(2, statistics.getTotalFilesSent());

        statistics.fileSent();
        statistics.fileSent();
        statistics.fileSent();

        Assert.assertEquals(5, statistics.getTotalFilesSent());
    }
}
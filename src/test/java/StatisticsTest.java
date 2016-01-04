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

    int totalFilesSentTargetValue;
    float filesPerSecondTargetValue;

    @Test
    public void statisticsTargetTest() {
        totalFilesSentTargetValue = 0;
        filesPerSecondTargetValue = 0;

        StatisticsTarget target  = new StatisticsTarget() {
            @Override
            public void updateFilesPerSecond(float fps) {
                filesPerSecondTargetValue = fps;
            }

            @Override
            public void updateTotalFilesSent(int total) {
                totalFilesSentTargetValue = total;
            }
        };

        Statistics statistics = new Statistics();
        statistics.setStatisticsTarget(target);

        statistics.fileSent();
        statistics.fileSent();
        statistics.fileSent();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(3, totalFilesSentTargetValue);
    }

    @Test
    public void fpsIsDecreasingOverTimeTest() {
        totalFilesSentTargetValue = 0;
        filesPerSecondTargetValue = 0;

        StatisticsTarget target  = new StatisticsTarget() {
            @Override
            public void updateFilesPerSecond(float fps) {
                filesPerSecondTargetValue = fps;
            }

            @Override
            public void updateTotalFilesSent(int total) {
                totalFilesSentTargetValue = total;
            }
        };

        Statistics statistics = new Statistics();
        statistics.setStatisticsTarget(target);

        statistics.fileSent();
        statistics.fileSent();
        statistics.fileSent();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        float oldFPS = filesPerSecondTargetValue;
        float currentFPS;
        for(int i = 0 ; i < 5; i++) {

            currentFPS = filesPerSecondTargetValue;

            System.out.println("Values: " + currentFPS + " < " + oldFPS);
            Assert.assertTrue(currentFPS <= oldFPS);

            oldFPS = currentFPS;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
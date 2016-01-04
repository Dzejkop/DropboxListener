import java.io.File;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jakobs on 2015-12-10.
 */
public class Statistics {

    AtomicInteger totalFilesSent;

    AtomicInteger filesSentWithinSecond;

    float filesSentPerSecondAverage = 0;

    StatisticsTarget statisticsTarget;

    private boolean hasTarget = false;

    public Statistics() {
        totalFilesSent = new AtomicInteger();
        filesSentWithinSecond = new AtomicInteger();

        new Thread(() -> {

            long sleepTime = 1000;

            while(true) {
                update();
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public void setStatisticsTarget(StatisticsTarget target) {
        this.statisticsTarget = target;
        hasTarget = true;
    }

    public int getTotalFilesSent() {
        return totalFilesSent.get();
    }

    public float getFilesSentPerSecond() {
        filesSentPerSecondAverage = (filesSentPerSecondAverage + filesSentWithinSecond.get())/2.0f;
        filesSentWithinSecond.set(0);
        return filesSentPerSecondAverage;
    }

    public void update() {
        if(hasTarget) {
            statisticsTarget.updateFilesPerSecond(getFilesSentPerSecond());
            statisticsTarget.updateTotalFilesSent(getTotalFilesSent());
        }
    }

    public void fileSent() {
        totalFilesSent.incrementAndGet();
        filesSentWithinSecond.incrementAndGet();
    }

}

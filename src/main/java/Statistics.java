import java.io.File;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jakobs on 2015-12-10.
 */
public class Statistics {

    AtomicInteger totalFilesSent;

    long startTime;

    public Statistics() {
        totalFilesSent = new AtomicInteger();
        startTime = System.currentTimeMillis();
    }

    public int getTotalFilesSent() {
        return totalFilesSent.get();
    }

    public float getFilesSentPerSecond() {
        long now = System.currentTimeMillis();
        int seconds = (int)(now - startTime)/1000;
        int totalFiles = totalFilesSent.get();
        return (float)totalFiles / (float)seconds;
    }

    public void fileSent() {
        totalFilesSent.incrementAndGet();
    }

}

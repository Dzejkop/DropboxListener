/**
 * Created by Jakobs on 2015-12-14.
 */
public interface StatisticsTarget {

    void updateFilesPerSecond(float fps);
    void updateTotalFilesSent(int total);

}

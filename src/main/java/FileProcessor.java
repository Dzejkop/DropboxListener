import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Created by Jakobs on 2015-12-10.
 */
public class FileProcessor {

    DropboxManager dropboxManager;

    Executor executor;

    public FileProcessor(DropboxManager dropboxManager, int poolSize) {
        this.dropboxManager = dropboxManager;
        executor = Executors.newFixedThreadPool(poolSize);
    }

    public void submitFile(File f) {
        executor.execute(() -> {
            dropboxManager.uploadFile(f);
        });
    }

    public void submitFile(File f, Consumer<File> successCallback) {
        executor.execute(() -> {
            dropboxManager.uploadFile(f);

            // Callback
            successCallback.accept(f);
        });
    }

    public void submitFile(File f, Consumer<File> startCallback, Consumer<File> successCallback) {
        executor.execute(() -> {
            // Start
            startCallback.accept(f);

            dropboxManager.uploadFile(f);

            // Callback
            successCallback.accept(f);
        });
    }
}

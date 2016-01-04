import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Jakobs on 2015-12-10.
 */
public class DirectoryHandler {

    protected File directoryHandle;

    List<File> knownDirectoryState;

    List<Consumer<File>> newFileListeners;

    public DirectoryHandler(File directoryHandle) {
        this.directoryHandle = directoryHandle;

        if(directoryHandle == null) throw new NullPointerException("Null directory handle.");
        if(!directoryHandle.isDirectory()) throw new IllegalStateException("Handle doesn't point to a directory.");

        newFileListeners = new ArrayList<>();
        knownDirectoryState = new ArrayList<>();
    }

    public void listen() {
        List<File> directoryState = listFiles();
        if(!directoryState.equals(knownDirectoryState)) {
            onDirectoryContentChanged(directoryState);
        }
    }

    public void addOnNewFileDetectedListener(Consumer<File> newFileListener) {
        newFileListeners.add(newFileListener);
    }

    void onDirectoryContentChanged(List<File> newState) {
        newState.forEach(f -> {
            if(!knownDirectoryState.contains(f)) onNewFileDetected(f);
        });
        knownDirectoryState = newState;
    }

    void onNewFileDetected(File newFile) {
        newFileListeners.forEach(n -> n.accept(newFile));
    }

    List<File> listFiles() {
        return Arrays.asList(directoryHandle.listFiles());
    }

}

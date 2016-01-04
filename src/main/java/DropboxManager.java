import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.DbxUsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Jakobs on 2015-12-10.
 */
public class DropboxManager {

    public static final String DROPBOX_ACCESS_TOKEN = "o_GowWtsr5sAAAAAAAAAZnKoffs7hA9FdJSnCwn0vFANcz4-LVo-7Qp4WRYSJuYn";

    DbxClientV2 client;

    public DropboxManager() {
        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
        client = new DbxClientV2(config, DROPBOX_ACCESS_TOKEN);
    }

    public void uploadFile(File f) {
        boolean isDone = false;
        while(!isDone) {
            try {
                client.files.uploadBuilder("/" + f.getName()).run(new FileInputStream(f));
                isDone = true;
                System.out.println("File: " + f.getName() + " Upload successful.");
            } catch (IOException | DbxException e) {
                System.out.println("File: " + f.getName() + " Upload failed, retrying...");
            }
        }
    }

}

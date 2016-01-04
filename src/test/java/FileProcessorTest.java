import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by Jakobs on 2015-12-17.
 */
public class FileProcessorTest {

    boolean beginCallbackCheck;
    boolean successCallbackCheck;

    @Test
    public void fileProcessorCallbacksTest() {

        File f = Mockito.mock(File.class);

        DropboxManager manager = Mockito.mock(DropboxManager.class);

        FileProcessor processor = new FileProcessor(manager, 2);

        beginCallbackCheck = false;
        successCallbackCheck = false;

        processor.submitFile(f, (n) -> beginCallbackCheck = true, (n) -> successCallbackCheck = true);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(beginCallbackCheck);
        Assert.assertTrue(successCallbackCheck);
    }

}
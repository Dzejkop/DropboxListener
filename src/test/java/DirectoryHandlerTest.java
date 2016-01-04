import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by Jakobs on 2015-12-17.
 */
public class DirectoryHandlerTest {

    boolean listenersCheck = false;
    int listenersCountCheck = 0;

    @Test (expected = IllegalStateException.class)
    public void directoryHandlerConstructorIllegalArgumentTest() {

        // Mocked file handle, returns false on is directory call
        File f = Mockito.mock(File.class);
        Mockito.when(f.isDirectory()).then((v) -> false);

        new DirectoryHandler(f);
    }

    @Test
    public void directoryHandlerConstructorTest() {

        File f = Mockito.mock(File.class);
        Mockito.when(f.isDirectory()).then((v) -> true);

        Assert.assertNotNull(new DirectoryHandler(f));
    }

    @Test
    public void directoryHandlerListFilesTest() {
        File f1 = Mockito.mock(File.class);
        File f2 = Mockito.mock(File.class);
        File f3 = Mockito.mock(File.class);

        Mockito.when(f1.getName()).then((v) -> "f1");
        Mockito.when(f2.getName()).then((v) -> "f2");
        Mockito.when(f3.getName()).then((v) -> "f3");

        // Mocked directory with one file
        File mockedDirectory = Mockito.mock(File.class);
        Mockito.when(mockedDirectory.isDirectory()).then((v) -> true);
        Mockito.when(mockedDirectory.listFiles()).then((v) -> new File[]{f1});

        DirectoryHandler handler = new DirectoryHandler(mockedDirectory);

        Assert.assertTrue(handler.listFiles().contains(f1));

        Mockito.when(mockedDirectory.listFiles()).then((v) -> new File[]{f1, f2, f3});

        Assert.assertTrue(
                handler.listFiles().contains(f1) &&
                handler.listFiles().contains(f2) &&
                handler.listFiles().contains(f3)
        );
    }

    @Test
    public void directoryHandlerListenersTest() {

        File f1 = Mockito.mock(File.class);
        File f2 = Mockito.mock(File.class);
        File f3 = Mockito.mock(File.class);

        Mockito.when(f1.getName()).then((v) -> "f1");
        Mockito.when(f2.getName()).then((v) -> "f2");
        Mockito.when(f3.getName()).then((v) -> "f3");

        // Mocked directory with one file
        File mockedDirectory = Mockito.mock(File.class);
        Mockito.when(mockedDirectory.isDirectory()).then((v) -> true);
        Mockito.when(mockedDirectory.listFiles()).then((v) -> new File[]{f1});

        DirectoryHandler directoryHandler = new DirectoryHandler(mockedDirectory);

        listenersCheck = false;
        listenersCountCheck = 0;

        // Set up listeners
        directoryHandler.addOnNewFileDetectedListener((n) -> listenersCheck = true);
        directoryHandler.addOnNewFileDetectedListener((n) -> listenersCountCheck++);

        // Test
        directoryHandler.listen();
        Assert.assertTrue(listenersCheck);
        Assert.assertEquals(1, listenersCountCheck);

        // Part 2
        Mockito.when(mockedDirectory.listFiles()).then((v) -> new File[]{f1, f2, f3});

        // Test
        directoryHandler.listen();
        Assert.assertEquals(3, listenersCountCheck);
    }

    @Test (expected = NullPointerException.class)
    public void directoryHandlerConstructorNullPointerTest() {

        // Mocked file handle, returns false on is directory call
        File f = null;
        new DirectoryHandler(f);
    }

}
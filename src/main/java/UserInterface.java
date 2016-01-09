import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by Jakobs on 2015-12-10.
 */
public class UserInterface extends JFrame {

    Statistics statistics;
    DirectoryHandler directoryHandler;

    public UserInterface() throws HeadlessException {
        this.setContentPane(rootPanel);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setSize(400, 600);
        this.setVisible(true);

        beginButton.addActionListener(a -> start());
    }

    private DirectoryHandler setup() {
        DirectoryHandler directoryHandler = new DirectoryHandler(new File(targetDirectoryTextField.getText()));
        DropboxManager dropboxManager = new DropboxManager();
        FileProcessor fileProcessor = new FileProcessor(dropboxManager, numberOfThreadsSlider.getValue());
        statistics = new Statistics();

        // Listeners
        directoryHandler.addOnNewFileDetectedListener(n -> System.out.println("New file detected: " + n.getName()));
        directoryHandler.addOnNewFileDetectedListener(n -> fileProcessor.submitFile(n, f -> statistics.fileSent()));

        return directoryHandler;
    }

    private void start() {
        directoryHandler = setup();

        new Thread(() -> {
            while (true) {
                loop();
            }
        }).start();

        for(Component c : controlPanel.getComponents()) c.setEnabled(false);
        controlPanel.revalidate();
        controlPanel.doLayout();
    }

    public void loop() {
        directoryHandler.listen();
        updateTotalFilesSent(statistics.getTotalFilesSent());
        updateFilesPerSecond(statistics.getFilesSentPerSecond());
    }

    private JPanel rootPanel;
    private JButton beginButton;
    private JPanel controlPanel;
    private JPanel statisticsPanel;
    private JLabel totalFilesSentLabel;
    private JLabel filesPerSecondLabel;
    private JSlider numberOfThreadsSlider;
    private JTextField targetDirectoryTextField;


    public void updateFilesPerSecond(float fps) {
        filesPerSecondLabel.setText("Files per second: " + String.format("%.3f", fps));
    }

    public void updateTotalFilesSent(int total) {
        totalFilesSentLabel.setText("Total: " + total);
    }
}

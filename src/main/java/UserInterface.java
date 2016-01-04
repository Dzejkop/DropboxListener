import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by Jakobs on 2015-12-10.
 */
public class UserInterface extends JFrame implements StatisticsTarget {

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
        Statistics statistics = new Statistics();

        // Setup statistics
        statistics.setStatisticsTarget(this);

        // Listeners
        directoryHandler.addOnNewFileDetectedListener(n -> System.out.println("New file detected: " + n.getName()));
        directoryHandler.addOnNewFileDetectedListener(n -> fileProcessor.submitFile(n, f -> statistics.fileSent()));

        return directoryHandler;
    }

    private void start() {
        DirectoryHandler directoryHandler = setup();

        new Thread(() -> {
            while (true) {
                directoryHandler.listen();
            }
        }).start();

        for(Component c : controlPanel.getComponents()) c.setEnabled(false);
        controlPanel.revalidate();
        controlPanel.doLayout();
    }

    private JPanel rootPanel;
    private JButton beginButton;
    private JPanel controlPanel;
    private JPanel statisticsPanel;
    private JLabel totalFilesSentLabel;
    private JLabel filesPerSecondLabel;
    private JSlider numberOfThreadsSlider;
    private JTextField targetDirectoryTextField;


    @Override
    public void updateFilesPerSecond(float fps) {
        filesPerSecondLabel.setText("Files per second: " + String.format("%.3f", fps));
    }

    @Override
    public void updateTotalFilesSent(int total) {
        totalFilesSentLabel.setText("Total: " + total);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigurableTimer extends JFrame {

    private Timer timer;
    private int elapsedTimeInSeconds;
    private int configuredTimeInSeconds;

    private JLabel timeLabel;

    public ConfigurableTimer() {
        setTitle("Configurable Timer");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        initializeComponents();
        setupButtons();
        setupTimerConfig();
    }

    private void initializeComponents() {
        timeLabel = new JLabel("0:00", JLabel.CENTER);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(timeLabel);
    }

    private void setupButtons() {
        JButton startButton = createButton("Start", this::start);
        JButton stopButton = createButton("Stop", this::stop);
        JButton resetButton = createButton("Reset", this::reset);

        add(startButton);
        add(stopButton);
        add(resetButton);
    }

    private void setupTimerConfig() {
        String input = JOptionPane.showInputDialog(this, "Set timer duration in minutes:");
        try {
            configuredTimeInSeconds = Integer.parseInt(input) * 60;
            updateTimeLabel();
        } catch (NumberFormatException | NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Using default time: 1 minute.");
            configuredTimeInSeconds = 60;
            updateTimeLabel();
        }
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    private void start(ActionEvent event) {
        if (timer == null) {
            timer = new Timer(1000, e -> {
                if (elapsedTimeInSeconds > 0) {
                    elapsedTimeInSeconds--;
                    updateTimeLabel();
                } else {
                    stop(null);
                    JOptionPane.showMessageDialog(this, "Timer completed!");
                }
            });
            timer.start();
        }
    }

    private void stop(ActionEvent event) {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    private void reset(ActionEvent event) {
        stop(null); // Stop the timer if running
        elapsedTimeInSeconds = configuredTimeInSeconds;
        updateTimeLabel();
    }

    private void updateTimeLabel() {
        int minutes = elapsedTimeInSeconds / 60;
        int remainingSeconds = elapsedTimeInSeconds % 60;
        timeLabel.setText(String.format("%d:%02d", minutes, remainingSeconds));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConfigurableTimer().setVisible(true));
    }
}

package hr.nikola.swing.joption;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * @see https://stackoverflow.com/a/44417958/230513
 * @see https://stackoverflow.com/a/12451673/230513
 */

public class JOptionTimeTest implements ActionListener, PropertyChangeListener {

    private static final int TIME_OUT = 10;
    private int count = TIME_OUT;
    private final Timer timer = new Timer(1000, this);
    private final JDialog dialog = new JDialog();
    private final JOptionPane optPane = new JOptionPane();
    private final JLabel label = new JLabel(message());
    private final JLabel prompt = new JLabel("Enter Your Name:");
    private final JTextField text = new JTextField("Name");

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JOptionTimeTest().createGUI();
            }
        });
    }

    private void createGUI() {
        JFrame frame = new JFrame("JOptionTimeTest naziv");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        timer.setCoalesce(false);
        text.selectAll();
        Object[] array = {label, prompt, text};
        optPane.setMessage(array);
        optPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        optPane.setOptionType(JOptionPane.DEFAULT_OPTION);
        optPane.addPropertyChangeListener(this);
        dialog.add(optPane);
        dialog.pack();
        frame.add(new JLabel(frame.getTitle(), JLabel.CENTER));
        frame.pack();
        frame.setVisible(true);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        timer.start();
    }

    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();
        if (JOptionPane.VALUE_PROPERTY.equals(prop)) {
            thatsAllFolks();
        }
    }

    public void actionPerformed(ActionEvent e) {
        count--;
        label.setText(message());
        if (count == 0) {
            thatsAllFolks();
        }
        timer.restart();
    }

    private String message() {
        return "Closing in " + count + " seconds.";
    }

    private void thatsAllFolks() {
        dialog.setVisible(false);
        dialog.dispatchEvent(new WindowEvent(
            dialog, WindowEvent.WINDOW_CLOSING));
    }
}

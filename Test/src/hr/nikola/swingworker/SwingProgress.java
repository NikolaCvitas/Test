package hr.nikola.swingworker;




import java.awt.BorderLayout;
import java.util.List;
import javax.swing.*;


public class SwingProgress {

    public static void main(String[] args) {
        new SwingProgress().doJob();
    }

    public void doJob() {

        JTextArea msgLabel;
        JProgressBar progressBar;
        final int MAXIMUM = 100;
        JPanel panel;

        progressBar = new JProgressBar(0, MAXIMUM);
        progressBar.setIndeterminate(true);
        msgLabel = new JTextArea("deneme");
        msgLabel.setEditable(false);

        panel = new JPanel(new BorderLayout(5, 5));
        panel.add(msgLabel, BorderLayout.PAGE_START);
        panel.add(progressBar, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(11, 11, 11, 11));

        final JDialog dialog = new JDialog();
        dialog.getContentPane().add(panel);
        dialog.setResizable(false);
        dialog.pack();
        dialog.setSize(500, dialog.getHeight());
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setAlwaysOnTop(false);
        dialog.setVisible(true);
        msgLabel.setBackground(panel.getBackground());

        SwingWorker worker = new SwingWorker() {

            @Override
            protected void done() {
                // Close the dialog
                dialog.dispose();
            }

            @Override
            protected void process(List chunks) {
                // Here you can process the result of "doInBackGround()"
                // Set a variable in the dialog or etc.
            }

            @Override
            protected Object doInBackground() throws Exception {
                // Do the long running task here
                // Call "publish()" to pass the data to "process()"
                // return something meaningful
            	
            	System.out.println("doInBackground ");
                return null;
            }
        };

        worker.execute();

    }
}
package hr.nikola.swing.joption;




import javax.swing.JOptionPane;


public class ThreadJPane {
    public static void main(String[] args) {
        new Thread() {
                public void run() {
                    JOptionPane.showMessageDialog(null, "A");
                }
            }.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }

        JOptionPane.showMessageDialog(null, "B");
    }
}
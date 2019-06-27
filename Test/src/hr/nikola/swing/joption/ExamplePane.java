package hr.nikola.swing.joption;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class ExamplePane {
	
	
	public final static boolean showConfirmDialogWithTimeout(Object params, String title, int timeout_ms) {
	    final JOptionPane msg = new JOptionPane(params, JOptionPane.WARNING_MESSAGE, JOptionPane.CANCEL_OPTION);
	    final JDialog dlg = msg.createDialog(title);

	    msg.setInitialSelectionValue(JOptionPane.OK_OPTION);
	    dlg.setAlwaysOnTop(true);
	    dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    dlg.addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentShown(ComponentEvent e) {
	            super.componentShown(e);
	            final Timer t = new Timer(timeout_ms, new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    dlg.setVisible(false);
	                }

	            });
	            t.start();
	        }
	    });
	    dlg.setVisible(true);

	    Object selectedvalue = msg.getValue();
	    if (selectedvalue.equals(JOptionPane.CANCEL_OPTION)) {
	        return false;
	    } else {
	        return true;
	    }
	}
	
	private static void start() {

	    // example usage
	    String message = "U tijeku je izvoz podataka u PDF";
	    JLabel lbmsg = new JLabel(message);
	    boolean result = showConfirmDialogWithTimeout(lbmsg, "Izvoz u PDF", 10 * 1000);

	    if (result == false) {
	    	System.out.println(message + " cancel is selected");
	        
	    }
	    else {
	    	System.out.println(message + " timeout or okay is selected");
	       
	    }
	    
	}
	    
	    
	    public static void main(String[] args) {

	    	start();
	    }

}

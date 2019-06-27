package hr.nikola.swing.joption;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class ExamplePane {
	
	private static ArrayList<String> lista = new ArrayList<String>();
	
	private static final int TIME = 1000;
	
	private static final int MULTIPLE = 3;
	
	private static boolean FLAG = true;
	
	
	
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
	    boolean result = showConfirmDialogWithTimeout(lbmsg, "Izvoz u PDF", MULTIPLE  * TIME);

	    if (result == false) {
	    	FLAG = false;
	    	System.out.println(message + " cancel is selected FLAG:"+FLAG);
	    }
	    else {
	    	FLAG = true;
	    	System.out.println(message + " timeout or okay is selected FLAG:"+FLAG);
	    }
	    
	}
	
	private static void fillList() {
		
		String name ="ABC";
		
		lista.add(name);
		
		String name2 ="ABC2";
		
		lista.add(name2);
		String name3 ="ABC3";
		
		lista.add(name3);
		String name4 ="ABC4";
		
		lista.add(name4);
		
		String name5 ="ABC5";
		
		lista.add(name5);
		
		String name6 ="ABC6";
		
		lista.add(name6);
	}
	    
	    
	    public static void main(String[] args) {

	    	fillList();
	    	
	    	int i = 0;
	    	
	    	for(String name : lista) {
	    		i++;
	    		if(FLAG) {
	    			System.out.println(" iteration number: "+i);
		    		start();	
	    		}
	    	}
	    	
	    }

}

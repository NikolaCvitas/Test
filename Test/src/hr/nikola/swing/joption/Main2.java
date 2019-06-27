package hr.nikola.swing.joption;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Main2 {
	private JOptionPane pane;
	private JDialog dialog;
	private Timer t;
	
	public Main2() {
		t = new Timer(2000, closeJDialog);
		t.start();
		pane = new JOptionPane("My Dialog Message to You!");
		dialog = pane.createDialog("My Dialog Title");
		dialog.setVisible(true);
	}
	
	private ActionListener closeJDialog = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (dialog.isShowing()) {
				dialog.dispose();
				System.out.println("dialog disposed");
			}
		}


	};
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Main2();
			}
		});
	}
}

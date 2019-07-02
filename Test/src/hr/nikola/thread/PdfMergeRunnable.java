package hr.nikola.thread;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class PdfMergeRunnable implements Runnable {
	
	private ArrayList<String> list;
	
	private String name;
	

	
	
	public PdfMergeRunnable(String name) {
		super();
		this.name = name;
	}



	public PdfMergeRunnable(ArrayList<String> list) {
		super();
		this.list = list;
	}



	@Override
	public void run() {
        System.out.println("PdfMergeRunnable - START "+Thread.currentThread().getName());
        try {
            Thread.sleep(1000);

            System.out.println("PdfMergeRunnable name:"+name);
            
            final JOptionPane op = new JOptionPane( name, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[] {}, null );
            
            String dialogTitle = "dialogTitle";
            final JDialog dialog = op.createDialog( dialogTitle );
            dialog.setModal( true );
          //  dialog.setDefaultCloseOperation( JDialog.DO_NOTHING_ON_CLOSE );
            dialog.setVisible( true );
            
            if(list != null) {
            	for(String name :list) {
            		System.out.println("PDF list "+name);
            	}
            }
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("PdfMergeRunnable- END "+Thread.currentThread().getName());
		
	}

}

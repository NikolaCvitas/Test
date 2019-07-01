package hr.nikola.thread;

import java.util.ArrayList;

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

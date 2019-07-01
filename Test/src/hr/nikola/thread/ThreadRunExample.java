package hr.nikola.thread;

import java.util.ArrayList;

public class ThreadRunExample {

    public static void main(String[] args){
        Thread t1 = new Thread(new HeavyWorkRunnable(), "t1");
        Thread t2 = new Thread(new HeavyWorkRunnable(), "t2");
        System.out.println("Starting Runnable threads");
        t1.start();
        t2.start();
        System.out.println("Runnable Threads has been started");
        Thread t3 = new MyThread("t3");
        Thread t4 = new MyThread("t4");
        System.out.println("Starting MyThreads");
        t3.start();
        t4.start();
        
        
        Thread pdf = new Thread(new PdfMergeRunnable("nikola"), "pdfBox");
        pdf.start();
        
        
        Thread pdf2 = new Thread(new PdfMergeRunnable(getList()), "pdfBox2");
        pdf2.start();
        
        System.out.println("MyThreads has been started");
        
    }
    
    void Foo(String str) {
        class OneShotTask implements Runnable {
            String str;
            OneShotTask(String s) { str = s; }
            public void run() {
                someFunc(str);
            }
        }
        Thread t = new Thread(new OneShotTask(str));
        t.start();
    }

	public void someFunc(String str) {
		System.out.println(" someFunc XXXXX");
		
	}
	
	public static ArrayList getList() {
		
		ArrayList list = new ArrayList();
		
		list.add("ABC");
		list.add("EFG");
		list.add("HIJ");
		
		
		return list;
		
		
	}
    
    
}


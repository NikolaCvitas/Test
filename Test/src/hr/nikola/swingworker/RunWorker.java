package hr.nikola.swingworker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

public class RunWorker extends SwingWorker<String,  Void>
{
	private ArrayList<String> als;
	private String s;


	public RunWorker(String s, ArrayList<String> als) {
		this.s = s;
		this.als = als;
	}

	public String doInBackground() throws IOException
	{
		
		System.out.println("RunWorker.doInBackground START");
		//this is right way to do it and you are correct here.
		//Following called method must return ArrayList<String> 
		return AnotherClass.doSomething(s, als);
	}

	public void done()
	{
		ArrayList<String> retList = als;
		try 
		{ 
			//When doInBackground finishes done method is called and to get data from doInBackground it uses following method
			// retList = get();
			als = retList;
			
			
			System.out.println("RunWorker.done !!!!");
		} 
		catch (Exception ignore) 
		{
		}
	}

}

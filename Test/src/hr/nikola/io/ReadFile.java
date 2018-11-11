package hr.nikola.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {
	
	private Scanner  x;
	
	public void openFile()  {
		
		try {
			x =  new Scanner(new File("novi_tekst.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public void readFile() {
		while(x.hasNext()) {
			System.out.println("\n "+x.next());
		}
		
	}
	
	public void closeFile() {
		x.close();
	}

}

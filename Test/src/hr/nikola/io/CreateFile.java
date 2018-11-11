package hr.nikola.io;

import java.io.FileNotFoundException;
import java.util.Formatter;

public class CreateFile {
	
	private Formatter x;
	
	public void openFile() {
		
		try {
			x = new Formatter("novi_tekst.txt");
		} catch (FileNotFoundException e) {
			System.out.println("Exception openFile");
			e.printStackTrace();
		}
		
		
	}
	
	
	public void addRecords() {
		x.format("%s %s %s", "1","Nikola","Cvitaš");
	}
	
	public void close() {
		x.close();
	}
}

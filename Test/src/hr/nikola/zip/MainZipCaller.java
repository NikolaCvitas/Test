package hr.nikola.zip;

import java.io.File;

public class MainZipCaller {
	//C:\Users\Nikola\git\Test\Test
	  private static final String INPUT_FILE = "C:\\Users\\Nikola\\git\\Test\\Test\\novi_tekst.txt";
	    private static final String OUTPUT_FILE = "C:\\Users\\Nikola\\git\\Test\\Test\\noviTekst.zip";

	public static void main(String[] args) {
	    	
		CreateZip cz = new CreateZip();
		
	    	cz.zipFile(new File(INPUT_FILE), OUTPUT_FILE);
		
		
	
		
	//	cz.createZip();

	}

}

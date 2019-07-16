package hr.nikola.text;

import java.io.File;
import java.io.FileReader; 
public class ReadingFromFile 
{ 
	public static void main(String[] args) throws Exception 
	{ 
		// pass the path to the file as a parameter 
		FileReader fr = 
				new FileReader("C:\\Directory1\\test.txt"); 

		int i; 
		while ((i=fr.read()) != -1) {
			System.out.print((char) i); 
		}
			
		File file = readFileFromDisk("C:\\Directory1\\test.txt");
		
		System.out.println("\nread file"+file);
		
	} 
	
	
    private static File readFileFromDisk(final String p_name){
        File file = new File(p_name);
        return file;
    }
 

}

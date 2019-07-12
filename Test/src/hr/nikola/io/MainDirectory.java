package hr.nikola.io;

import java.io.IOException;

public class MainDirectory {
	
	public static void main(String[] args) {
		

		try {
			CreateDirectory.revCreateDirectory();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("MainDirectory END");
	}

}

package hr.nikola.io;

public class MainWriteCaller {

	public static void main(String[] args) {
		
		CreateFile mwc = new CreateFile();
		mwc.openFile();
		mwc.addRecords();
		mwc.close();
		
		System.out.println("MainWriteCaller END");
	}

}

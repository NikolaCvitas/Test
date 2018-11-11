package hr.nikola.io;

public class MainReadCaller {

	public static void main(String[] args) {
		
		
		ReadFile rf = new ReadFile();
		rf.openFile();
		rf.readFile();
		rf.closeFile();

	}

}

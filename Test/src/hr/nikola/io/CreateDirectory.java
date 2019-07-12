package hr.nikola.io;

import java.io.File;
import java.io.IOException;

public class CreateDirectory {
	
    public static void revCreateDirectory() throws IOException {
        //To create single directory/folder
        File file = new File("C:\\Directory1");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
                System.out.println( "file.getAbsolutePath() :"+file.getAbsolutePath() );
                System.out.println( "file.getCanonicalPath() :"+file.getCanonicalPath() );
                System.out.println( "file.getPath()  :"+file.getPath() );
                System.out.println( "file.getAbsoluteFile() :"+file.getAbsoluteFile() );
                System.out.println( "file.getCanonicalFile() :"+file.getCanonicalFile() );
                System.out.println( "file.getName() :"+file.getName() );
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        //To create multiple directories/folders
        File files = new File("C:\\Directory2\\Sub2\\Sub-Sub2");
        if (!files.exists()) {
            if (files.mkdirs()) {
                System.out.println("Multiple directories are created!");
            } else {
                System.out.println("Failed to create multiple directories!");
            }
        }

    }

}

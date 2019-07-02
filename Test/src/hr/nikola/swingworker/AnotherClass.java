package hr.nikola.swingworker;

import java.io.IOException;
import java.util.ArrayList;

public class AnotherClass{
	
    public static String doSomething(String ipRange, ArrayList<String> nmapPorts) {
        //do some stuff with the strings
    	String cmds="";
    	String aString ="";
        try{
            
			ProcessBuilder builder = new ProcessBuilder("someexe", "flag", cmds,
            "&cd");
            builder.redirectErrorStream(true);
            Process pr = builder.start();

			//do some stuff with the stream.

            aString = pr.toString();
            
        }catch (IOException e){
        	e.printStackTrace();
        }
		
        return aString;
    }
    
    
}

package hr.nikola.swingworker;

import java.io.IOException;
import java.util.ArrayList;

public class AnotherClass{
	
    public static String doSomething(String ipRange, ArrayList<String> list) throws IOException {
        //do some stuff with the strings

    	String aString ="";

        
        for(String str : list ) {
        	
        	aString =aString.concat(str);
        }
		
        System.out.println("AnotherClass.doSomething "+aString);
        return aString;
    }
    
    
}

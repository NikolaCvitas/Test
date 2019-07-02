package hr.nikola.utils;

import java.sql.Date;
import java.util.ArrayList;

public class StringUtils {
	
	
	
    public static String generateBlankString(int i) {
        String pom = "";
        for (int current = 1; current <= i; current++) {
            pom = pom + " ";
        }
        return pom;
    }
    
    public static boolean isDate(String s){
        try{
        	s=s.trim();
            Date.valueOf(s);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    
	public static ArrayList<String> getList() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("ABC");
		list.add("EFG");
		list.add("HIJ");
		list.add("FOO");
		list.add("ZOO");
		list.add("ROO");
		
		
		return list;
		
		
	}

}

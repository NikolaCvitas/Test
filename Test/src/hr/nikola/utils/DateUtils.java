package hr.nikola.utils;

import java.sql.Date;

public class DateUtils {
	
	
    public static Date getCurrentDate(){
    	java.sql.Date current = new java.sql.Date(System.currentTimeMillis());
    	return current;
}

}

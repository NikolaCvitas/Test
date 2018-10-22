package hr.nikola.utils;

public class StringUtils {
	
	
	
    public static String generateBlankString(int i) {
        String pom = "";
        for (int current = 1; current <= i; current++) {
            pom = pom + " ";
        }
        return pom;
    }

}

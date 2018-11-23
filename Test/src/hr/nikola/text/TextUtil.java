package hr.nikola.text;

public class TextUtil {
	
    /**
     * Method replaces croatian diacritic signs.
     * @param p_input
     * @return
     */
    private String cleanCroatianChars( String p_input ) {
        String r = "";
        char c;

        for ( int i = 0; i < p_input.length(); ++i ) {
            c = p_input.charAt( i );

            if ( c == '�' || c == '�' ) {
                r += "C";
            }
            else if ( c == '�' || c == '�' ) {
                r += "c";
            }
            else if ( c == '�' ) {
                r += "D";
            }
            else if ( c == '�' ) {
                r += "S";
            }
            else if ( c == '�' || c == '�' ) {
                r += "s";
            }
            else if ( c == '�' || c == '�' ) {
                r += "Z";
            }
            else if ( c == '�' || c == '�' ) {
                r += "z";
            }
            else {
                r += c;
            }

        }

        System.out.println( "r:" + r );

        return r;
    }

}

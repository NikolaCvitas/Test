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

            if ( c == 'È' || c == 'È' ) {
                r += "C";
            }
            else if ( c == 'æ' || c == 'è' ) {
                r += "c";
            }
            else if ( c == 'Ð' ) {
                r += "D";
            }
            else if ( c == 'Š' ) {
                r += "S";
            }
            else if ( c == 'œ' || c == 'š' ) {
                r += "s";
            }
            else if ( c == 'Ž' || c == '' ) {
                r += "Z";
            }
            else if ( c == 'ž' || c == 'Ÿ' ) {
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

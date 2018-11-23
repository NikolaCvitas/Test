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

            if ( c == 'Æ' || c == 'È' ) {
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
            else if (  c == 'š' ) {
                r += "s";
            }
            else if ( c == 'Ž' ) {
                r += "Z";
            }
            else if ( c == 'ž'  ) {
                r += "z";
            }
            else {
                r += c;
            }

        }

        System.out.println( "r:" + r );

        return r;
    }
    
    /**
     * Method replaces croatian diacritic signs with unicode characters.
     * @param p_input
     * @return
     */
    private String replaceCroatianCharsWithUnicode( String p_input ) {
        String r = "";
        char c;

        for ( int i = 0; i < p_input.length(); ++i ) {
            c = p_input.charAt( i );

            if ( c == 'Æ' ) {
                r += "\u0106";
            }
            else if ( c == 'È' ) {
                r += "\u010C";
            }
            else if ( c == 'æ' ) {
                r += "\u0107";
            }
            else if ( c == 'è' ) {
                r += "\u010d";
            }
            else if ( c == 'Ð' ) {
                r += "\u0110";
            }
            else if ( c == 'Š' ) {
                r += "\u0160";
            }
            else if ( c == 'š' ) {
                r += "\u0161";
            }
            else if ( c == 'Ž' ) {
                r += "\u017D";
            }
            else if ( c == 'ž' ) {
                r += "\u017e";
            }
            else {
                r += c;
            }

        }

        System.out.println( "r:" + r );

        return r;
    }

}

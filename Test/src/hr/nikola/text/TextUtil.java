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
            else if (  c == '�' ) {
                r += "s";
            }
            else if ( c == '�' ) {
                r += "Z";
            }
            else if ( c == '�'  ) {
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

            if ( c == '�' ) {
                r += "\u0106";
            }
            else if ( c == '�' ) {
                r += "\u010C";
            }
            else if ( c == '�' ) {
                r += "\u0107";
            }
            else if ( c == '�' ) {
                r += "\u010d";
            }
            else if ( c == '�' ) {
                r += "\u0110";
            }
            else if ( c == '�' ) {
                r += "\u0160";
            }
            else if ( c == '�' ) {
                r += "\u0161";
            }
            else if ( c == '�' ) {
                r += "\u017D";
            }
            else if ( c == '�' ) {
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

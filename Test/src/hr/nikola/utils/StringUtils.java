package hr.nikola.utils;

import java.sql.Date;
import java.util.ArrayList;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	// Max sizes when parsing method's input arguments
    private static final int     METHOD_ARGS__MAX_NUMBER_ELEMENTS = 10;
    private static final int     METHOD_ARGS__MAX_ELEMENT_LENGTH  = 100;

    // Command line arguments pattern
    private static final Pattern COMMAND_ARGUMENTS_PATTERN        = Pattern.compile("(?:\\\"[^\\\"]*\\\")|(?:\\'[^\\']*\\')|(?:[^\\s]+)");

    /**
     * Tests if a string is null or empty
     *
     * @param string
     * @return if we provided a null or empty string
     */
    public static boolean isNullOrEmpty( String string ) {

        return string == null || string.trim().length() < 1;
    }

    /**
     * Tests if 2 strings are equal, but given they are not null
     *
     * @param string1
     * @param string2
     * @return
     */
    public static boolean isNotNullAndEquals( String string1, String string2 ) {

        if (string1 == null) {
            return false;
        } else {
            return string1.equals(string2);
        }
    }



    /**
     * Convert a byte array to a hex representation
     *
     * @param bytes the input array of bytes
     * @return the hex presentation
     */
    public static String byteArray2Hex( byte[] bytes ) {

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            result.append(Integer.toString( (bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return result.toString();
    }

    /**
     * Get the MD5 sum of a content passed as String
     *
     * @param data
     * @return the MD5 sum
     */
    public static String md5sum( String data ) {

        byte[] defaultBytes = data.getBytes();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            return byteArray2Hex(messageDigest);
        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException(nsae);
        }
    }

    /**
     * Convert method's input arguments into a single String
     *
     * @param arguments list of arguments
     * @return string representation
     */
    public static String methodInputArgumentsToString( Object[] arguments ) {

        // no arguments
        if (arguments.length == 0) {
            return "{}";
        }

        StringBuilder argumentsStr = new StringBuilder();

        argumentsStr.append("{ ");
        for (Object argument : arguments) {
            if (argument != null && argument.getClass().isArray()) {

                // check the array length - if it is huge, we
                // don't want it in the console or in memory
                int arrayLenght = Array.getLength(argument);
                if (arrayLenght > METHOD_ARGS__MAX_NUMBER_ELEMENTS) {
                    // just give the array address
                    argumentsStr.append(argument.getClass().getSimpleName() + " array at address "
                                        + argument);
                } else {
                    argumentsStr.append("[");

                    for (int i = 0; i < arrayLenght; i++) {

                        Object arrayElement = Array.get(argument, i);
                        if (arrayElement instanceof String) {
                            argumentsStr.append(trimAndQuoteStringArgument((String) arrayElement));
                        } else {
                            argumentsStr.append(arrayElement);
                        }

                        if (i < arrayLenght - 1) {
                            argumentsStr.append(", ");
                        }
                    }

                    argumentsStr.append("]");
                }
            } else {
                if (argument instanceof String) {
                    argumentsStr.append(trimAndQuoteStringArgument((String) argument));
                } else {
                    argumentsStr.append(argument);
                }
            }
            argumentsStr.append(", ");
        }
        argumentsStr.delete(argumentsStr.length() - 2, argumentsStr.length());
        argumentsStr.append(" }");

        return argumentsStr.toString();
    }

    /**
     * Trim and add quotes to a string argument
     *
     * @param argument
     * @return
     */
    private static String trimAndQuoteStringArgument( String argument ) {

        StringBuilder trimmedArg = new StringBuilder();
        trimmedArg.append("\"");

        if (argument.length() > METHOD_ARGS__MAX_ELEMENT_LENGTH) {
            trimmedArg.append(argument.substring(0, METHOD_ARGS__MAX_ELEMENT_LENGTH));
            trimmedArg.append("...\" (argument has been trimmed)");
        } else {
            trimmedArg.append(argument);
            trimmedArg.append("\"");
        }

        return trimmedArg.toString();
    }

    /**
     * Parsing command and its arguments
     *
     * @param commandWithArguments command to parse
     * @return the command and its arguments in an array
     */
    public static String[] parseCommandLineArguments( String commandWithArguments ) {

        List<String> commandArguments = new ArrayList<String>();
        Matcher matcher = COMMAND_ARGUMENTS_PATTERN.matcher(commandWithArguments);
        while (matcher.find()) {
            String arg = matcher.group();
            if ( (arg.indexOf('"') == 0 && arg.lastIndexOf('"') == arg.length() - 1)
                 || (arg.indexOf('\'') == 0 && arg.lastIndexOf('\'') == arg.length() - 1)) {

                arg = arg.substring(1, arg.length() - 1);
            }
            commandArguments.add(arg);
        }
        return commandArguments.toArray(new String[0]);
    }
	
	
	
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

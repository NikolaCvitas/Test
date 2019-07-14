package hr.nikola.exception;

import java.util.logging.Logger;

public class MainExImpl {
	
	private static final Logger LOGGER = Logger.getLogger(MainExImpl.class.getName());

	public static void main(String[] args) {
		
		System.out.println("MainExImpl _________ ");
		handleExceptionInOneBlock();

	}
	
	public static void handleExceptionInOneBlock() {
		try {
			wrapException(new String("99999999"));
		} catch (MyBusinessException e) {
			// handle exception
			LOGGER.info("Logger Name: "+LOGGER.getName());
		}
	}

	private static void wrapException(String input) throws MyBusinessException {
		try {
			// do something
			System.out.println("wrapException __________ ");
		} catch (NumberFormatException e) {
			throw new MyBusinessException("A message that describes the error.", e, ErrorCode.INVALID_PORT_CONFIGURATION);
		}
	}
	
	
	private static void wrapException2(String input) {
		try {
			// do something
			System.out.println("wrapException2 __________ ");
		} catch (NumberFormatException e) {
			throw new MyUncheckedBusinessException("A message that describes the error.", e, ErrorCode.INVALID_PORT_CONFIGURATION);
		}
	}

}

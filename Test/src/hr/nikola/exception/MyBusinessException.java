package hr.nikola.exception;

/**
 * The MyBusinessException wraps all checked standard Java exception and enriches them with a custom error code.
 * You can use this code to retrieve localized error messages and to link to our online documentation.
 * 
 * @author Nikola
 */
public class MyBusinessException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8513280206133567549L;
	
	
	private final ErrorCode code;

	public MyBusinessException(ErrorCode code) {
		super();
		this.code = code;
	}

	public MyBusinessException(String message, Throwable cause, ErrorCode code) {
		super(message, cause);
		this.code = code;
	}

	public MyBusinessException(String message, ErrorCode code) {
		super(message);
		this.code = code;
	}

	public MyBusinessException(Throwable cause, ErrorCode code) {
		super(cause);
		this.code = code;
	}
	
	public ErrorCode getCode() {
		return this.code;
	}
}

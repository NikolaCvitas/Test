package hr.nikola.exception;

/**
 * The MyUncheckedBusinessException wraps all unchecked standard Java exception and enriches them with a custom error code.
 * You can use this code to retrieve localized error messages and to link to our online documentation.
 * 
 * @author Nikola
 */
public class MyUncheckedBusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8018095653493370940L;
	
	private final ErrorCode code;

	public MyUncheckedBusinessException(ErrorCode code) {
		super();
		this.code = code;
	}

	public MyUncheckedBusinessException(String message, Throwable cause, ErrorCode code) {
		super(message, cause);
		this.code = code;
	}

	public MyUncheckedBusinessException(String message, ErrorCode code) {
		super(message);
		this.code = code;
	}

	public MyUncheckedBusinessException(Throwable cause, ErrorCode code) {
		super(cause);
		this.code = code;
	}
	
	public ErrorCode getCode() {
		return this.code;
	}
}

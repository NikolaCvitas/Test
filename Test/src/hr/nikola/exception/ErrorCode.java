package hr.nikola.exception;

/**
 * 
 * @author Nikola
 *
 */
public class ErrorCode {
	
	
	public static ErrorCode INVALID_PORT_CONFIGURATION;
	
	private String code;

	public ErrorCode() {
		super();
	}
	
	

	public ErrorCode(String code) {
		super();
		this.code = code;
	}



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
	

}

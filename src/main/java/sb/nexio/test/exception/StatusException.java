package sb.nexio.test.exception;

import org.springframework.http.HttpStatus;

public class StatusException extends Exception {
	
private static final long serialVersionUID = 1L;
	
	private HttpStatus status;
	
	public StatusException(String msg, HttpStatus status){
		super(msg);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

}

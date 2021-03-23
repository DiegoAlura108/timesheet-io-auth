package br.com.timesheetio.auth.exception;


import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ObjectNotFoundException extends RuntimeException {

	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;

	private final HttpStatus status;
	
	public ObjectNotFoundException(String message, HttpStatus status) {

		super(message);
		this.status = status;
	}
}

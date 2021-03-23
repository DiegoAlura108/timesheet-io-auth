package br.com.timesheetio.auth.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PersonAuthToPersonMessageDTO implements Serializable {

	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;
	
	private String personAuthUserKey;
	
	private String personUserKey;
}

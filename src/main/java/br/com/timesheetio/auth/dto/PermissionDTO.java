package br.com.timesheetio.auth.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PermissionDTO implements Serializable {
	
	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String description;
}

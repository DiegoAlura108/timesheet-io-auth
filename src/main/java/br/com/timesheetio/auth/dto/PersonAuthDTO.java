package br.com.timesheetio.auth.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonAuthDTO implements Serializable {

	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String email;
	
	private String password;
	
	private List<PermissionDTO> roles;
	
	private String personAuthUserKey;
	
	private boolean accountNonExpired;

	private boolean accountNonLocked;

	private boolean credentialsNonExpired;

	private boolean enabled;
}

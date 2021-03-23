package br.com.timesheetio.auth.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
@Entity
@Table(name = "PERMISSION_AUTH")
public class PermissionAuthEntity implements GrantedAuthority, Serializable {

	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Override
	public String getAuthority() {
		
		return description;
	}
}

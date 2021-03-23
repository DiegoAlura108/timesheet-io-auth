package br.com.timesheetio.auth.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
@Table(name = "PERSON_AUTH")
public class PersonAuthEntity implements UserDetails, Serializable {

	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@JoinTable(name = "PERSON_AUTH_PERMISSIONS", joinColumns = { @JoinColumn(name = "ID_PERSON_AUTH") },
			inverseJoinColumns = { @JoinColumn(name = "ID_PERMISSION_AUTH") })
	@ManyToMany(fetch= FetchType.EAGER)
	private List<PermissionAuthEntity> permissions;
	
	@Column(name = "PERSON_AUTH_USER_KEY")
	private String personAuthUserKey;

	@Column(name = "ACCOUNT_NON_EXPIRED")
	private boolean accountNonExpired;

	@Column(name = "ACCOUNT_NON_LOCKED")
	private boolean accountNonLocked;

	@Column(name = "CREDENTIALS_NON_EXPIRED")
	private boolean credentialsNonExpired;

	@Column(name = "ENABLED")
	private boolean enabled;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.permissions;
	}

	@Override
	public boolean isAccountNonExpired() {

		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {

		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {

		return this.enabled;
	}

	@Override
	public String getUsername() {
		
		return this.email;
	}
}

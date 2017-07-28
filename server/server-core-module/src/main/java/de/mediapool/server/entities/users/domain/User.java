package de.mediapool.server.entities.users.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.mediapool.server.core.domain.DBEntry;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends DBEntry {

	private static final long serialVersionUID = 1L;

	private String username;

	@JsonIgnore
	private String password;
	
	private String realName;

	private Boolean isLocked = false;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id", cascade = CascadeType.ALL)
	private Set<UserRole> roles;

	public User(String username, String password) {
		this(username, password, new HashSet<>(), false);
	}

	public User() {
		super();
	}

	public User(String username, String password, Set<UserRole> roles, Boolean isLocked) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.isLocked = isLocked;
	}

	@Override
	public String toString() {
		return "UserNodeDTO [username=" + username + "]";
	}

}

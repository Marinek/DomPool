package de.mediapool.server.entities.users.domain;

import javax.persistence.Entity;

import de.mediapool.server.core.domain.DBEntry;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class UserRole extends DBEntry {

	private static final long serialVersionUID = 1L;

	private String title;

}

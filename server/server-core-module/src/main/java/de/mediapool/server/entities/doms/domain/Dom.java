package de.mediapool.server.entities.doms.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import de.mediapool.server.core.domain.DBEntry;
import de.mediapool.server.entities.users.domain.User;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Dom extends DBEntry {
	
	private static final long serialVersionUID = 1L;

	private Integer number;
	
	@ManyToOne
	private DomColor color;
	
	private String location;
	
	@ManyToOne
	private DomType type;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private User spotter;

}

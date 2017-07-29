package de.mediapool.server.entities.doms.domain;

import javax.persistence.Entity;

import de.mediapool.server.core.domain.DBEntry;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DomType extends DBEntry {

	private static final long serialVersionUID = 1L;

	private String typeName;
	
	@Override
	public String toString() {
		return typeName == null ? "" : typeName;
	}
}

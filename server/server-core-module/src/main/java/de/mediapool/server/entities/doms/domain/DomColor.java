package de.mediapool.server.entities.doms.domain;

import javax.persistence.Entity;

import de.mediapool.server.core.domain.DBEntry;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DomColor extends DBEntry {

	private static final long serialVersionUID = 1L;

	private String colorName;
	
	private String colorHex;
	
	@Override
	public String toString() {
		return colorName == null ? "" : colorName;
	}
}

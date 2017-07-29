package de.mediapool.server.entities.doms.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import de.mediapool.server.entities.doms.domain.DomType;
import de.mediapool.server.entities.doms.repository.DomTypeRespository;

@Service
public class DomTypeFormatter implements Formatter<DomType> {

	@Autowired
	private DomTypeRespository domTypeRespository;
	
	@Override
	public String print(DomType object, Locale arg1) {
		 return (object != null ? object.getId().toString() : "");
	}

	@Override
	public DomType parse(String arg0, Locale arg1) throws ParseException {
		DomType findOne = domTypeRespository.findOne(Long.parseLong(arg0));
		
		return findOne;
	}

}

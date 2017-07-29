package de.mediapool.server.entities.doms.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import de.mediapool.server.entities.doms.domain.DomColor;
import de.mediapool.server.entities.doms.repository.DomColorRespository;

@Service
public class DomColorFormatter implements Formatter<DomColor> {

	@Autowired
	private DomColorRespository domColorRespository;
	
	@Override
	public String print(DomColor object, Locale arg1) {
		 return (object != null ? object.getId().toString() : "");
	}

	@Override
	public DomColor parse(String arg0, Locale arg1) throws ParseException {
		DomColor findOne = domColorRespository.findOne(Long.parseLong(arg0));
		
		return findOne;
	}

}

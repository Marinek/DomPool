package de.mediapool.server.configuration;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import de.mediapool.server.entities.doms.repository.DomRepository;

public class ConfigurationTargetRepository  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationTargetRepository.class);
	
	private Map<String, Value> methodToRequestMapping = new HashMap<>();

	public void registerMapping (String methodName, Value requestMappingInfo) {
		methodToRequestMapping.put(methodName, requestMappingInfo);
	}
	
	public boolean canHandle(String methodName) {
		return methodToRequestMapping.containsKey(methodName);
	}
	
	public Object executeRestCall(String methodname) {
		LOGGER.trace("Invoking: executeRestCall (methodname)");
		
		DomRepository bean = SpringContextHolder.getApplicationContext().getBean(DomRepository.class);
		
		return String.valueOf(bean.count());
	}
}

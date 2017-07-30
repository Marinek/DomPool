package de.mediapool.server.configuration;

import org.springframework.beans.factory.annotation.Value;

public interface TestConfig extends ServerConfiguration {

	@Value("${hello}")
	public String getHello();
}

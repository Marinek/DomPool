package de.mediapool.server.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringContextHolder {

   private static ApplicationContext applicationContext = null;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    @Autowired
    public void setApplicationContext(ApplicationContext papplicationContext) throws BeansException {
         applicationContext = papplicationContext;
    }
}
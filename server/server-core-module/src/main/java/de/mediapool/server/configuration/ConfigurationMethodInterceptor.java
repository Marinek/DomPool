package de.mediapool.server.configuration;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class ConfigurationMethodInterceptor implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			
			Method currentMethod = invocation.getMethod();
			
			Object targetRestClient = invocation.getThis();
			
			if(targetRestClient instanceof ConfigurationTargetRepository) {
				ConfigurationTargetRepository restClientTargetRepository = (ConfigurationTargetRepository) targetRestClient;
				
				if(!restClientTargetRepository.canHandle(currentMethod.getName())) {
					return invocation.proceed();
				}
				
				return restClientTargetRepository.executeRestCall(currentMethod.getName());
			}
			
			return null;
			
		}
		
	}

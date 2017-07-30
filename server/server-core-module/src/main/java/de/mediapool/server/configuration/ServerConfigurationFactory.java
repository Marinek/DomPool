package de.mediapool.server.configuration;

import java.lang.reflect.Method;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

public class ServerConfigurationFactory implements FactoryBean<ServerConfiguration>, InitializingBean, BeanClassLoaderAware {

	private Class<? extends ServerConfiguration> clientInterface;

	private ClassLoader classLoader;

	@Override
	public ServerConfiguration getObject() throws Exception {
		ProxyFactory result = new ProxyFactory();

		ConfigurationTargetRepository restClientTargetInformation = new ConfigurationTargetRepository();
		

		result.setTarget(restClientTargetInformation);

		result.setInterfaces(new Class[] { clientInterface, ServerConfiguration.class } );

		for(Method method : clientInterface.getMethods()) {
			Value declaredAnnotation = method.getDeclaredAnnotation(Value.class);
			
			restClientTargetInformation.registerMapping(method.getName(), declaredAnnotation);
		}

		result.addAdvisor(ExposeInvocationInterceptor.ADVISOR);

		result.addAdvice(new ConfigurationMethodInterceptor());

		return (ServerConfiguration) result.getProxy(classLoader);
	}

	@Override
	public Class<?> getObjectType() {
		return clientInterface;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public Class<? extends ServerConfiguration> getClientInterface() {
		return clientInterface;
	}

	@Required
	public void setClientInterface(Class<? extends ServerConfiguration> repositoryInterface) {
		Assert.notNull(repositoryInterface);
		this.clientInterface = repositoryInterface;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
}

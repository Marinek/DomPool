package de.mediapool.server.configuration;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ServerConfigurationFactoryPopulator implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
		Assert.notNull(annotationMetadata, "AnnotationMetadata must not be null!");
		Assert.notNull(registry, "BeanDefinitionRegistry must not be null!");
		
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false) {
			
			protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
				return true;
			}

		};
		
		provider.addIncludeFilter(new InterfaceTypeFilter(ServerConfiguration.class));

		Set<BeanDefinition> findCandidateComponents = provider.findCandidateComponents("de.mediapool");
		
		for (BeanDefinition restClientDefinition : findCandidateComponents) {
			
			BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(ServerConfigurationFactory.class);

			Class<? extends ServerConfigurationFactory> restClientInterface = getInterface(restClientDefinition.getBeanClassName());
			
			definitionBuilder.addPropertyValue("clientInterface", restClientInterface);

			AbstractBeanDefinition beanDefinition = definitionBuilder.getBeanDefinition();
			
			String beanName = restClientInterface.getName();
			
			beanDefinition.setSynthetic(true);

			registry.registerBeanDefinition(beanName, beanDefinition);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Class<? extends ServerConfigurationFactory> getInterface(String beanClassName) {
		try {
			return (Class<? extends ServerConfigurationFactory>) Class.forName(beanClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private static class InterfaceTypeFilter extends AssignableTypeFilter {

		public InterfaceTypeFilter(Class<?> targetType) {
			super(targetType);
		}

		@Override
		public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {

			return metadataReader.getClassMetadata().isInterface() && super.match(metadataReader, metadataReaderFactory);
		}
	}
}

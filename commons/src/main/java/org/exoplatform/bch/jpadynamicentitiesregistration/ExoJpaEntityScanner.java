package org.exoplatform.bch.jpadynamicentitiesregistration;

import java.util.Set;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class ExoJpaEntityScanner implements Integrator {
  public void integrate(Configuration configuration,
                        SessionFactoryImplementor sessionFactory,
                        SessionFactoryServiceRegistry serviceRegistry) {

    Reflections reflections = new Reflections(new ConfigurationBuilder()
            .addUrls(ClasspathHelper.forJavaClassPath())
            .setScanners(new TypeAnnotationsScanner()));
    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(ExoJpaEntity.class);

    for (Class entityClass : annotated) {
      configuration.addAnnotatedClass(entityClass);
    }
    configuration.buildMappings();
  }

  public void integrate(MetadataImplementor metadata,
                        SessionFactoryImplementor sessionFactory,
                        SessionFactoryServiceRegistry serviceRegistry) {
    // Nothing
  }

  public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
    // Nothing
  }

}

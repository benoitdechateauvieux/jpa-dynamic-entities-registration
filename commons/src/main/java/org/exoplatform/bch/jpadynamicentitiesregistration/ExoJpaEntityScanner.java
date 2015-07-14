package org.exoplatform.bch.jpadynamicentitiesregistration;

import java.util.Set;

import javax.persistence.Entity;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

public class ExoJpaEntityScanner implements Integrator {

  public void integrate(Configuration configuration,
                        SessionFactoryImplementor sessionFactory,
                        SessionFactoryServiceRegistry serviceRegistry) {

    Reflections reflections = new Reflections(ClasspathHelper.forJavaClassPath());
    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Entity.class);

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

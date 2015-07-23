package org.exoplatform.bch.jpadynamicentitiesregistration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class ExoJpaEntityScanner implements Integrator {
    public void integrate(Configuration configuration,
                          SessionFactoryImplementor sessionFactory,
                          SessionFactoryServiceRegistry serviceRegistry) {

        registerEntities(configuration);
    }

    private void registerEntities(Configuration configuration) {
        try {
            Enumeration<URL> urls = ClassLoader.getSystemClassLoader().getResources("exo-jpa-entities/entities.idx");
            while (urls.hasMoreElements()) {
                InputStream stream = urls.nextElement().openStream();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    String entityClassName;
                    while ((entityClassName = reader.readLine()) != null) { //TODO isBlank
                        configuration.addAnnotatedClass(Class.forName(entityClassName));
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try { stream.close(); } catch (Throwable ignore) {}
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

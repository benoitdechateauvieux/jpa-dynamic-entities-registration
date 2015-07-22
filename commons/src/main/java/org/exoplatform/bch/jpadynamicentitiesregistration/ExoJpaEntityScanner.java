package org.exoplatform.bch.jpadynamicentitiesregistration;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

public class ExoJpaEntityScanner implements Integrator {
    public void integrate(Configuration configuration,
                          SessionFactoryImplementor sessionFactory,
                          SessionFactoryServiceRegistry serviceRegistry) {
        Set<Class> plfEntities = new HashSet<>();

        long start3 = System.currentTimeMillis();
        URL[] urls = ClasspathUrlFinder.findClassPaths(); // scan java.class.path
        AnnotationDB db = new AnnotationDB();
        try {
            db.scanArchives(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String entityClass : db.getAnnotationIndex().get(ExoJpaEntity.class.getName())) {
            try {
                plfEntities.add(Class.forName(entityClass));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        long end3 = System.currentTimeMillis();
        System.out.println("Scannotation : " +(end3-start3)+ " ms");


        for (Class entityClass : plfEntities) {
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

# jpa-dynamic-entities-registration
Example of dynamic registration of JPA entities declared in 2 different Maven modules (and thus, 2 different JAR)

### How it works ?
This example use [Hibernate integrators](https://docs.jboss.org/hibernate/orm/4.1/devguide/en-US/html/ch07.html#integrators).  
The integrator is declared in *commons/src/main/resources/META-INF/services/org.hibernate.integrator.spi.Integrator*  
This file just reference *ExoJpaEntityScanner*  

*ExoJpaEntityScanner* is invoked by Hibernate when the EntityManagerFactory is built.  
The class contains only one (interesting) method:  

```java
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
```

In order to obtain the list of classes annotated with @Entity, it use the [Reflections](https://github.com/ronmamo/reflections) framework.  
Then it declares those entities to the JPA Persistence Unit.
  
Another module (called *new-addon*) defines and uses its own PU and JPA entity.  

### How to test it ?
```maven
mvn clean test
```

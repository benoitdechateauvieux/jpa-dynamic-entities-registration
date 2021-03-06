/*
 * Copyright (C) 2015 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.bch.jpadynamicentitiesregistration;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * This service is responsible to create a single EntityManagerFactory, with the
 * persistence unit name passed from service init-params
 * <code>persistence.unit.name</code>.
 * <p>
 * The service is also bound to use of the RequestLifecycle that there is only
 * one EntityManager will be created at beginning of the request lifecycle. The
 * EntityManager instance will be maintained open and shared with all
 * applications relying on this through to the end of lifecycle.
 *
 * @author <a href="trongtt@gmail.com">Trong Tran</a>
 * @version $Revision$
 */
public class EntityManagerService {

  private static final EntityManagerService INSTANCE = new EntityManagerService();

  private static EntityManagerFactory entityManagerFactory;

  private ThreadLocal<EntityManager>  instance = new ThreadLocal<EntityManager>();

  public EntityManagerService() {
    //Get Hibernate properties
    final Properties properties = new Properties();
    try {
      properties.load(this.getClass().getResourceAsStream("/exo.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    String propertyValue = properties.getProperty("hibernate.show_sql");
    if (propertyValue != null) { //TODO is not null
      properties.put("hibernate.show_sql", propertyValue);
      System.out.println("Setting [hibernate.show_sql] to [" + propertyValue + "]");
    }
    entityManagerFactory = Persistence.createEntityManagerFactory("exo-pu", properties);
  }

  /**
   * @return the EntityManager instance available in current request lifecycle.
   *         Otherwise, it returns NULL.
   */
  public EntityManager getEntityManager() {
    if (instance.get() == null) {
      return null;
    } else {
      return instance.get();
    }
  }

  /**
   * Return a completely new instance of EntityManager. The EntityManager
   * instance is put in the threadLocal for further use.
   * 
   * @return return a completely new instance of EntityManager.
   */
  EntityManager createEntityManager() {
    EntityManager em = entityManagerFactory.createEntityManager();
    instance.set(em);
    return em;
  }

  public void startRequest() {
    createEntityManager();
  }

  public void endRequest() {
    closeEntityManager();
  }

  void closeEntityManager() {
    EntityManager em = getEntityManager();
    EntityTransaction tx = null;
    try {
      tx = em.getTransaction();
      if (tx.isActive()) {
        tx.commit();
      }
    } catch (RuntimeException e) {
      if (tx != null && tx.isActive()) {
        tx.rollback();
      }
    } finally {
      em.close();
      instance.set(null);

    }
  }

  public static EntityManagerService get() {
    return INSTANCE;
  }
}

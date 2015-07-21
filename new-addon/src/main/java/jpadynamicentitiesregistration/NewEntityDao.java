package jpadynamicentitiesregistration;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by bdechateauvieux on 7/14/15.
 */
public class NewEntityDao {
    private static final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("new-PU");

    protected EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }

    @SuppressWarnings("unchecked")
    public List<NewEntity> findAll() {
        EntityManager em = emFactory.createEntityManager();
        try {
            return em.createQuery("from NewEntity").getResultList();
        } finally {
            em.close();
        }
    }

    public void create(NewEntity newEntity) {
        EntityManager em = emFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(newEntity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}

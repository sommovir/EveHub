/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evehub.it.lessons.eve.db.controllers;

import evehub.it.lessons.eve.db.controllers.exceptions.NonexistentEntityException;
import evehub.it.lessons.eve.db.entities.Oggetto;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it>
 */
public class OggettoJpaController implements Serializable {

    public OggettoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Oggetto oggetto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(oggetto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Oggetto oggetto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            oggetto = em.merge(oggetto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = oggetto.getId();
                if (findOggetto(id) == null) {
                    throw new NonexistentEntityException("The oggetto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Oggetto oggetto;
            try {
                oggetto = em.getReference(Oggetto.class, id);
                oggetto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The oggetto with id " + id + " no longer exists.", enfe);
            }
            em.remove(oggetto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Oggetto> findOggettoEntities() {
        return findOggettoEntities(true, -1, -1);
    }

    public List<Oggetto> findOggettoEntities(int maxResults, int firstResult) {
        return findOggettoEntities(false, maxResults, firstResult);
    }

    private List<Oggetto> findOggettoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Oggetto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Oggetto findOggetto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Oggetto.class, id);
        } finally {
            em.close();
        }
    }

    public int getOggettoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Oggetto> rt = cq.from(Oggetto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

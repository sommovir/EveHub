/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evehub.it.lessons.eve.db.controllers;

import evehub.it.lessons.eve.db.controllers.exceptions.NonexistentEntityException;
import evehub.it.lessons.eve.db.entities.HubCommerciale;
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
public class HubCommercialeJpaController implements Serializable {

    public HubCommercialeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HubCommerciale hubCommerciale) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(hubCommerciale);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HubCommerciale hubCommerciale) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            hubCommerciale = em.merge(hubCommerciale);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = hubCommerciale.getId();
                if (findHubCommerciale(id) == null) {
                    throw new NonexistentEntityException("The hubCommerciale with id " + id + " no longer exists.");
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
            HubCommerciale hubCommerciale;
            try {
                hubCommerciale = em.getReference(HubCommerciale.class, id);
                hubCommerciale.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hubCommerciale with id " + id + " no longer exists.", enfe);
            }
            em.remove(hubCommerciale);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HubCommerciale> findHubCommercialeEntities() {
        return findHubCommercialeEntities(true, -1, -1);
    }

    public List<HubCommerciale> findHubCommercialeEntities(int maxResults, int firstResult) {
        return findHubCommercialeEntities(false, maxResults, firstResult);
    }

    private List<HubCommerciale> findHubCommercialeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HubCommerciale.class));
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

    public HubCommerciale findHubCommerciale(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HubCommerciale.class, id);
        } finally {
            em.close();
        }
    }

    public int getHubCommercialeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HubCommerciale> rt = cq.from(HubCommerciale.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

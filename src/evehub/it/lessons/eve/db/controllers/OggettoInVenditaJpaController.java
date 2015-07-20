/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evehub.it.lessons.eve.db.controllers;

import evehub.it.lessons.eve.db.controllers.exceptions.NonexistentEntityException;
import evehub.it.lessons.eve.db.entities.OggettoInVendita;
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
public class OggettoInVenditaJpaController implements Serializable {

    public OggettoInVenditaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OggettoInVendita oggettoInVendita) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(oggettoInVendita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OggettoInVendita oggettoInVendita) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            oggettoInVendita = em.merge(oggettoInVendita);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = oggettoInVendita.getId();
                if (findOggettoInVendita(id) == null) {
                    throw new NonexistentEntityException("The oggettoInVendita with id " + id + " no longer exists.");
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
            OggettoInVendita oggettoInVendita;
            try {
                oggettoInVendita = em.getReference(OggettoInVendita.class, id);
                oggettoInVendita.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The oggettoInVendita with id " + id + " no longer exists.", enfe);
            }
            em.remove(oggettoInVendita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OggettoInVendita> findOggettoInVenditaEntities() {
        return findOggettoInVenditaEntities(true, -1, -1);
    }

    public List<OggettoInVendita> findOggettoInVenditaEntities(int maxResults, int firstResult) {
        return findOggettoInVenditaEntities(false, maxResults, firstResult);
    }

    private List<OggettoInVendita> findOggettoInVenditaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OggettoInVendita.class));
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

    public OggettoInVendita findOggettoInVendita(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OggettoInVendita.class, id);
        } finally {
            em.close();
        }
    }

    public int getOggettoInVenditaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OggettoInVendita> rt = cq.from(OggettoInVendita.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evehub.it.lessons.eve.db.controllers;

import evehub.it.lessons.eve.db.controllers.exceptions.NonexistentEntityException;
import evehub.it.lessons.eve.db.entities.SistemaSolare;
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
public class SistemaSolareJpaController implements Serializable {

    public SistemaSolareJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SistemaSolare sistemaSolare) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(sistemaSolare);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SistemaSolare sistemaSolare) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            sistemaSolare = em.merge(sistemaSolare);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = sistemaSolare.getId();
                if (findSistemaSolare(id) == null) {
                    throw new NonexistentEntityException("The sistemaSolare with id " + id + " no longer exists.");
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
            SistemaSolare sistemaSolare;
            try {
                sistemaSolare = em.getReference(SistemaSolare.class, id);
                sistemaSolare.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sistemaSolare with id " + id + " no longer exists.", enfe);
            }
            em.remove(sistemaSolare);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SistemaSolare> findSistemaSolareEntities() {
        return findSistemaSolareEntities(true, -1, -1);
    }

    public List<SistemaSolare> findSistemaSolareEntities(int maxResults, int firstResult) {
        return findSistemaSolareEntities(false, maxResults, firstResult);
    }

    private List<SistemaSolare> findSistemaSolareEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SistemaSolare.class));
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

    public SistemaSolare findSistemaSolare(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SistemaSolare.class, id);
        } finally {
            em.close();
        }
    }

    public int getSistemaSolareCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SistemaSolare> rt = cq.from(SistemaSolare.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

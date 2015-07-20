/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evehub.it.lessons.eve.db.controllers;

import evehub.it.lessons.eve.db.controllers.exceptions.NonexistentEntityException;
import evehub.it.lessons.eve.db.entities.Personaggio;
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
public class PersonaggioJpaController implements Serializable {

    public PersonaggioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personaggio personaggio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(personaggio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personaggio personaggio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            personaggio = em.merge(personaggio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = personaggio.getId();
                if (findPersonaggio(id) == null) {
                    throw new NonexistentEntityException("The personaggio with id " + id + " no longer exists.");
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
            Personaggio personaggio;
            try {
                personaggio = em.getReference(Personaggio.class, id);
                personaggio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personaggio with id " + id + " no longer exists.", enfe);
            }
            em.remove(personaggio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personaggio> findPersonaggioEntities() {
        return findPersonaggioEntities(true, -1, -1);
    }

    public List<Personaggio> findPersonaggioEntities(int maxResults, int firstResult) {
        return findPersonaggioEntities(false, maxResults, firstResult);
    }

    private List<Personaggio> findPersonaggioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Personaggio.class));
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

    public Personaggio findPersonaggio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personaggio.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaggioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Personaggio> rt = cq.from(Personaggio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

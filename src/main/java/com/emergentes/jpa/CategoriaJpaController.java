/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emergentes.jpa;

import com.emergentes.entidades.Categoria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.emergentes.entidades.Platillo;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author LIMBERT
 */
public class CategoriaJpaController implements Serializable {

    public CategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoria categoria) {
        if (categoria.getPlatilloList() == null) {
            categoria.setPlatilloList(new ArrayList<Platillo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Platillo> attachedPlatilloList = new ArrayList<Platillo>();
            for (Platillo platilloListPlatilloToAttach : categoria.getPlatilloList()) {
                platilloListPlatilloToAttach = em.getReference(platilloListPlatilloToAttach.getClass(), platilloListPlatilloToAttach.getId());
                attachedPlatilloList.add(platilloListPlatilloToAttach);
            }
            categoria.setPlatilloList(attachedPlatilloList);
            em.persist(categoria);
            for (Platillo platilloListPlatillo : categoria.getPlatilloList()) {
                Categoria oldCategoriaIdOfPlatilloListPlatillo = platilloListPlatillo.getCategoriaId();
                platilloListPlatillo.setCategoriaId(categoria);
                platilloListPlatillo = em.merge(platilloListPlatillo);
                if (oldCategoriaIdOfPlatilloListPlatillo != null) {
                    oldCategoriaIdOfPlatilloListPlatillo.getPlatilloList().remove(platilloListPlatillo);
                    oldCategoriaIdOfPlatilloListPlatillo = em.merge(oldCategoriaIdOfPlatilloListPlatillo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoria categoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getId());
            List<Platillo> platilloListOld = persistentCategoria.getPlatilloList();
            List<Platillo> platilloListNew = categoria.getPlatilloList();
            List<Platillo> attachedPlatilloListNew = new ArrayList<Platillo>();
            for (Platillo platilloListNewPlatilloToAttach : platilloListNew) {
                platilloListNewPlatilloToAttach = em.getReference(platilloListNewPlatilloToAttach.getClass(), platilloListNewPlatilloToAttach.getId());
                attachedPlatilloListNew.add(platilloListNewPlatilloToAttach);
            }
            platilloListNew = attachedPlatilloListNew;
            categoria.setPlatilloList(platilloListNew);
            categoria = em.merge(categoria);
            for (Platillo platilloListOldPlatillo : platilloListOld) {
                if (!platilloListNew.contains(platilloListOldPlatillo)) {
                    platilloListOldPlatillo.setCategoriaId(null);
                    platilloListOldPlatillo = em.merge(platilloListOldPlatillo);
                }
            }
            for (Platillo platilloListNewPlatillo : platilloListNew) {
                if (!platilloListOld.contains(platilloListNewPlatillo)) {
                    Categoria oldCategoriaIdOfPlatilloListNewPlatillo = platilloListNewPlatillo.getCategoriaId();
                    platilloListNewPlatillo.setCategoriaId(categoria);
                    platilloListNewPlatillo = em.merge(platilloListNewPlatillo);
                    if (oldCategoriaIdOfPlatilloListNewPlatillo != null && !oldCategoriaIdOfPlatilloListNewPlatillo.equals(categoria)) {
                        oldCategoriaIdOfPlatilloListNewPlatillo.getPlatilloList().remove(platilloListNewPlatillo);
                        oldCategoriaIdOfPlatilloListNewPlatillo = em.merge(oldCategoriaIdOfPlatilloListNewPlatillo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoria.getId();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            List<Platillo> platilloList = categoria.getPlatilloList();
            for (Platillo platilloListPlatillo : platilloList) {
                platilloListPlatillo.setCategoriaId(null);
                platilloListPlatillo = em.merge(platilloListPlatillo);
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoria.class));
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

    public Categoria findCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> rt = cq.from(Categoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emergentes.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.emergentes.entidades.Categoria;
import com.emergentes.entidades.Pedido;
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
public class PlatilloJpaController implements Serializable {

    public PlatilloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Platillo platillo) {
        if (platillo.getPedidoList() == null) {
            platillo.setPedidoList(new ArrayList<Pedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoriaId = platillo.getCategoriaId();
            if (categoriaId != null) {
                categoriaId = em.getReference(categoriaId.getClass(), categoriaId.getId());
                platillo.setCategoriaId(categoriaId);
            }
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : platillo.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getId());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            platillo.setPedidoList(attachedPedidoList);
            em.persist(platillo);
            if (categoriaId != null) {
                categoriaId.getPlatilloList().add(platillo);
                categoriaId = em.merge(categoriaId);
            }
            for (Pedido pedidoListPedido : platillo.getPedidoList()) {
                Platillo oldPlatilloIdOfPedidoListPedido = pedidoListPedido.getPlatilloId();
                pedidoListPedido.setPlatilloId(platillo);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldPlatilloIdOfPedidoListPedido != null) {
                    oldPlatilloIdOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldPlatilloIdOfPedidoListPedido = em.merge(oldPlatilloIdOfPedidoListPedido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Platillo platillo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Platillo persistentPlatillo = em.find(Platillo.class, platillo.getId());
            Categoria categoriaIdOld = persistentPlatillo.getCategoriaId();
            Categoria categoriaIdNew = platillo.getCategoriaId();
            List<Pedido> pedidoListOld = persistentPlatillo.getPedidoList();
            List<Pedido> pedidoListNew = platillo.getPedidoList();
            if (categoriaIdNew != null) {
                categoriaIdNew = em.getReference(categoriaIdNew.getClass(), categoriaIdNew.getId());
                platillo.setCategoriaId(categoriaIdNew);
            }
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getId());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            platillo.setPedidoList(pedidoListNew);
            platillo = em.merge(platillo);
            if (categoriaIdOld != null && !categoriaIdOld.equals(categoriaIdNew)) {
                categoriaIdOld.getPlatilloList().remove(platillo);
                categoriaIdOld = em.merge(categoriaIdOld);
            }
            if (categoriaIdNew != null && !categoriaIdNew.equals(categoriaIdOld)) {
                categoriaIdNew.getPlatilloList().add(platillo);
                categoriaIdNew = em.merge(categoriaIdNew);
            }
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    pedidoListOldPedido.setPlatilloId(null);
                    pedidoListOldPedido = em.merge(pedidoListOldPedido);
                }
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Platillo oldPlatilloIdOfPedidoListNewPedido = pedidoListNewPedido.getPlatilloId();
                    pedidoListNewPedido.setPlatilloId(platillo);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldPlatilloIdOfPedidoListNewPedido != null && !oldPlatilloIdOfPedidoListNewPedido.equals(platillo)) {
                        oldPlatilloIdOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldPlatilloIdOfPedidoListNewPedido = em.merge(oldPlatilloIdOfPedidoListNewPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = platillo.getId();
                if (findPlatillo(id) == null) {
                    throw new NonexistentEntityException("The platillo with id " + id + " no longer exists.");
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
            Platillo platillo;
            try {
                platillo = em.getReference(Platillo.class, id);
                platillo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The platillo with id " + id + " no longer exists.", enfe);
            }
            Categoria categoriaId = platillo.getCategoriaId();
            if (categoriaId != null) {
                categoriaId.getPlatilloList().remove(platillo);
                categoriaId = em.merge(categoriaId);
            }
            List<Pedido> pedidoList = platillo.getPedidoList();
            for (Pedido pedidoListPedido : pedidoList) {
                pedidoListPedido.setPlatilloId(null);
                pedidoListPedido = em.merge(pedidoListPedido);
            }
            em.remove(platillo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Platillo> findPlatilloEntities() {
        return findPlatilloEntities(true, -1, -1);
    }

    public List<Platillo> findPlatilloEntities(int maxResults, int firstResult) {
        return findPlatilloEntities(false, maxResults, firstResult);
    }

    private List<Platillo> findPlatilloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Platillo.class));
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

    public Platillo findPlatillo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Platillo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlatilloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Platillo> rt = cq.from(Platillo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

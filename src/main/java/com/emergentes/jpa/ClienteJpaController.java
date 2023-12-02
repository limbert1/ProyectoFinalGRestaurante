/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emergentes.jpa;

import com.emergentes.entidades.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.emergentes.entidades.Pedido;
import com.emergentes.entidades.Restaurante;
import com.emergentes.entidades.Factura;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author LIMBERT
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getFacturaList() == null) {
            cliente.setFacturaList(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedidoId = cliente.getPedidoId();
            if (pedidoId != null) {
                pedidoId = em.getReference(pedidoId.getClass(), pedidoId.getId());
                cliente.setPedidoId(pedidoId);
            }
            Restaurante restauranteId = cliente.getRestauranteId();
            if (restauranteId != null) {
                restauranteId = em.getReference(restauranteId.getClass(), restauranteId.getId());
                cliente.setRestauranteId(restauranteId);
            }
            List<Factura> attachedFacturaList = new ArrayList<Factura>();
            for (Factura facturaListFacturaToAttach : cliente.getFacturaList()) {
                facturaListFacturaToAttach = em.getReference(facturaListFacturaToAttach.getClass(), facturaListFacturaToAttach.getFacturaId());
                attachedFacturaList.add(facturaListFacturaToAttach);
            }
            cliente.setFacturaList(attachedFacturaList);
            em.persist(cliente);
            if (pedidoId != null) {
                pedidoId.getClienteList().add(cliente);
                pedidoId = em.merge(pedidoId);
            }
            if (restauranteId != null) {
                restauranteId.getClienteList().add(cliente);
                restauranteId = em.merge(restauranteId);
            }
            for (Factura facturaListFactura : cliente.getFacturaList()) {
                Cliente oldClienteIdOfFacturaListFactura = facturaListFactura.getClienteId();
                facturaListFactura.setClienteId(cliente);
                facturaListFactura = em.merge(facturaListFactura);
                if (oldClienteIdOfFacturaListFactura != null) {
                    oldClienteIdOfFacturaListFactura.getFacturaList().remove(facturaListFactura);
                    oldClienteIdOfFacturaListFactura = em.merge(oldClienteIdOfFacturaListFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            Pedido pedidoIdOld = persistentCliente.getPedidoId();
            Pedido pedidoIdNew = cliente.getPedidoId();
            Restaurante restauranteIdOld = persistentCliente.getRestauranteId();
            Restaurante restauranteIdNew = cliente.getRestauranteId();
            List<Factura> facturaListOld = persistentCliente.getFacturaList();
            List<Factura> facturaListNew = cliente.getFacturaList();
            if (pedidoIdNew != null) {
                pedidoIdNew = em.getReference(pedidoIdNew.getClass(), pedidoIdNew.getId());
                cliente.setPedidoId(pedidoIdNew);
            }
            if (restauranteIdNew != null) {
                restauranteIdNew = em.getReference(restauranteIdNew.getClass(), restauranteIdNew.getId());
                cliente.setRestauranteId(restauranteIdNew);
            }
            List<Factura> attachedFacturaListNew = new ArrayList<Factura>();
            for (Factura facturaListNewFacturaToAttach : facturaListNew) {
                facturaListNewFacturaToAttach = em.getReference(facturaListNewFacturaToAttach.getClass(), facturaListNewFacturaToAttach.getFacturaId());
                attachedFacturaListNew.add(facturaListNewFacturaToAttach);
            }
            facturaListNew = attachedFacturaListNew;
            cliente.setFacturaList(facturaListNew);
            cliente = em.merge(cliente);
            if (pedidoIdOld != null && !pedidoIdOld.equals(pedidoIdNew)) {
                pedidoIdOld.getClienteList().remove(cliente);
                pedidoIdOld = em.merge(pedidoIdOld);
            }
            if (pedidoIdNew != null && !pedidoIdNew.equals(pedidoIdOld)) {
                pedidoIdNew.getClienteList().add(cliente);
                pedidoIdNew = em.merge(pedidoIdNew);
            }
            if (restauranteIdOld != null && !restauranteIdOld.equals(restauranteIdNew)) {
                restauranteIdOld.getClienteList().remove(cliente);
                restauranteIdOld = em.merge(restauranteIdOld);
            }
            if (restauranteIdNew != null && !restauranteIdNew.equals(restauranteIdOld)) {
                restauranteIdNew.getClienteList().add(cliente);
                restauranteIdNew = em.merge(restauranteIdNew);
            }
            for (Factura facturaListOldFactura : facturaListOld) {
                if (!facturaListNew.contains(facturaListOldFactura)) {
                    facturaListOldFactura.setClienteId(null);
                    facturaListOldFactura = em.merge(facturaListOldFactura);
                }
            }
            for (Factura facturaListNewFactura : facturaListNew) {
                if (!facturaListOld.contains(facturaListNewFactura)) {
                    Cliente oldClienteIdOfFacturaListNewFactura = facturaListNewFactura.getClienteId();
                    facturaListNewFactura.setClienteId(cliente);
                    facturaListNewFactura = em.merge(facturaListNewFactura);
                    if (oldClienteIdOfFacturaListNewFactura != null && !oldClienteIdOfFacturaListNewFactura.equals(cliente)) {
                        oldClienteIdOfFacturaListNewFactura.getFacturaList().remove(facturaListNewFactura);
                        oldClienteIdOfFacturaListNewFactura = em.merge(oldClienteIdOfFacturaListNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            Pedido pedidoId = cliente.getPedidoId();
            if (pedidoId != null) {
                pedidoId.getClienteList().remove(cliente);
                pedidoId = em.merge(pedidoId);
            }
            Restaurante restauranteId = cliente.getRestauranteId();
            if (restauranteId != null) {
                restauranteId.getClienteList().remove(cliente);
                restauranteId = em.merge(restauranteId);
            }
            List<Factura> facturaList = cliente.getFacturaList();
            for (Factura facturaListFactura : facturaList) {
                facturaListFactura.setClienteId(null);
                facturaListFactura = em.merge(facturaListFactura);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

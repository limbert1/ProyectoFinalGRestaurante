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
import com.emergentes.entidades.Platillo;
import com.emergentes.entidades.Cliente;
import com.emergentes.entidades.Pedido;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author LIMBERT
 */
public class PedidoJpaController implements Serializable {

    public PedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedido pedido) {
        if (pedido.getClienteList() == null) {
            pedido.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Platillo platilloId = pedido.getPlatilloId();
            if (platilloId != null) {
                platilloId = em.getReference(platilloId.getClass(), platilloId.getId());
                pedido.setPlatilloId(platilloId);
            }
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : pedido.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getId());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            pedido.setClienteList(attachedClienteList);
            em.persist(pedido);
            if (platilloId != null) {
                platilloId.getPedidoList().add(pedido);
                platilloId = em.merge(platilloId);
            }
            for (Cliente clienteListCliente : pedido.getClienteList()) {
                Pedido oldPedidoIdOfClienteListCliente = clienteListCliente.getPedidoId();
                clienteListCliente.setPedidoId(pedido);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldPedidoIdOfClienteListCliente != null) {
                    oldPedidoIdOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldPedidoIdOfClienteListCliente = em.merge(oldPedidoIdOfClienteListCliente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedido pedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido persistentPedido = em.find(Pedido.class, pedido.getId());
            Platillo platilloIdOld = persistentPedido.getPlatilloId();
            Platillo platilloIdNew = pedido.getPlatilloId();
            List<Cliente> clienteListOld = persistentPedido.getClienteList();
            List<Cliente> clienteListNew = pedido.getClienteList();
            if (platilloIdNew != null) {
                platilloIdNew = em.getReference(platilloIdNew.getClass(), platilloIdNew.getId());
                pedido.setPlatilloId(platilloIdNew);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getId());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            pedido.setClienteList(clienteListNew);
            pedido = em.merge(pedido);
            if (platilloIdOld != null && !platilloIdOld.equals(platilloIdNew)) {
                platilloIdOld.getPedidoList().remove(pedido);
                platilloIdOld = em.merge(platilloIdOld);
            }
            if (platilloIdNew != null && !platilloIdNew.equals(platilloIdOld)) {
                platilloIdNew.getPedidoList().add(pedido);
                platilloIdNew = em.merge(platilloIdNew);
            }
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.setPedidoId(null);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Pedido oldPedidoIdOfClienteListNewCliente = clienteListNewCliente.getPedidoId();
                    clienteListNewCliente.setPedidoId(pedido);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldPedidoIdOfClienteListNewCliente != null && !oldPedidoIdOfClienteListNewCliente.equals(pedido)) {
                        oldPedidoIdOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldPedidoIdOfClienteListNewCliente = em.merge(oldPedidoIdOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pedido.getId();
                if (findPedido(id) == null) {
                    throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.");
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
            Pedido pedido;
            try {
                pedido = em.getReference(Pedido.class, id);
                pedido.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }
            Platillo platilloId = pedido.getPlatilloId();
            if (platilloId != null) {
                platilloId.getPedidoList().remove(pedido);
                platilloId = em.merge(platilloId);
            }
            List<Cliente> clienteList = pedido.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.setPedidoId(null);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(pedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
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

    public Pedido findPedido(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedido> rt = cq.from(Pedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

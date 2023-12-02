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
import com.emergentes.entidades.Cliente;
import com.emergentes.entidades.Restaurante;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author LIMBERT
 */
public class RestauranteJpaController implements Serializable {

    public RestauranteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Restaurante restaurante) {
        if (restaurante.getClienteList() == null) {
            restaurante.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : restaurante.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getId());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            restaurante.setClienteList(attachedClienteList);
            em.persist(restaurante);
            for (Cliente clienteListCliente : restaurante.getClienteList()) {
                Restaurante oldRestauranteIdOfClienteListCliente = clienteListCliente.getRestauranteId();
                clienteListCliente.setRestauranteId(restaurante);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldRestauranteIdOfClienteListCliente != null) {
                    oldRestauranteIdOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldRestauranteIdOfClienteListCliente = em.merge(oldRestauranteIdOfClienteListCliente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Restaurante restaurante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Restaurante persistentRestaurante = em.find(Restaurante.class, restaurante.getId());
            List<Cliente> clienteListOld = persistentRestaurante.getClienteList();
            List<Cliente> clienteListNew = restaurante.getClienteList();
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getId());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            restaurante.setClienteList(clienteListNew);
            restaurante = em.merge(restaurante);
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.setRestauranteId(null);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Restaurante oldRestauranteIdOfClienteListNewCliente = clienteListNewCliente.getRestauranteId();
                    clienteListNewCliente.setRestauranteId(restaurante);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldRestauranteIdOfClienteListNewCliente != null && !oldRestauranteIdOfClienteListNewCliente.equals(restaurante)) {
                        oldRestauranteIdOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldRestauranteIdOfClienteListNewCliente = em.merge(oldRestauranteIdOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = restaurante.getId();
                if (findRestaurante(id) == null) {
                    throw new NonexistentEntityException("The restaurante with id " + id + " no longer exists.");
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
            Restaurante restaurante;
            try {
                restaurante = em.getReference(Restaurante.class, id);
                restaurante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The restaurante with id " + id + " no longer exists.", enfe);
            }
            List<Cliente> clienteList = restaurante.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.setRestauranteId(null);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(restaurante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Restaurante> findRestauranteEntities() {
        return findRestauranteEntities(true, -1, -1);
    }

    public List<Restaurante> findRestauranteEntities(int maxResults, int firstResult) {
        return findRestauranteEntities(false, maxResults, firstResult);
    }

    private List<Restaurante> findRestauranteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Restaurante.class));
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

    public Restaurante findRestaurante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Restaurante.class, id);
        } finally {
            em.close();
        }
    }

    public int getRestauranteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Restaurante> rt = cq.from(Restaurante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

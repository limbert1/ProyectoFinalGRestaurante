
package com.emergentes.bean;

import com.emergentes.entidades.Cliente;
import com.emergentes.jpa.ClienteJpaController;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Beancliente {
    private EntityManagerFactory emf;
    private ClienteJpaController jpacliente;

    public Beancliente() {
        emf = Persistence.createEntityManagerFactory("prorecto");
        jpacliente = new ClienteJpaController(emf);
    }

    public List<Cliente> listarcliente(){
        return jpacliente.findClienteEntities();
    }
       
    public void insertar(Cliente cliente){
        jpacliente.create(cliente);
    }
    
    public void editar(Cliente cliente){
        try {
            jpacliente.edit(cliente);
        } catch (Exception ex) {
            Logger.getLogger(Beancliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eliminar(Integer id){
        try {
            jpacliente.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Beancliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Cliente buscar(Integer id){
        return jpacliente.findCliente(id);
    }
}



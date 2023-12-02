
package com.emergentes.bean;

import com.emergentes.entidades.Factura;
import com.emergentes.jpa.FacturaJpaController;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Beanfactura {
    private EntityManagerFactory emf;
    private FacturaJpaController jpacliente;

    public Beanfactura() {
        emf = Persistence.createEntityManagerFactory("prorecto");
        jpacliente = new FacturaJpaController(emf);
    }

    public List<Factura> listarfact(){
        return jpacliente.findFacturaEntities();
    }
       
    public void insertar(Factura factura){
        jpacliente.create(factura);
    }
    
    public void editar(Factura factura){
        try {
            jpacliente.edit(factura);
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
    
    public Factura buscar(Integer id){
        return jpacliente.findFactura(id);
    }
}

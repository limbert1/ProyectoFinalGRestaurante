
package com.emergentes.bean;

import com.emergentes.entidades.Restaurante;
import com.emergentes.jpa.RestauranteJpaController;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class BeanRestaurante {
    
    private EntityManagerFactory emf;
    private RestauranteJpaController jpaRestaurante;

    public BeanRestaurante() {
        emf= Persistence.createEntityManagerFactory("prorecto");
        jpaRestaurante= new RestauranteJpaController(emf);     
    }
    public List<Restaurante> listartodo(){
        return jpaRestaurante.findRestauranteEntities();
    }
    public void insertar (Restaurante c){
        jpaRestaurante.create(c);
    }
    public void editar (Restaurante c){
        try {
            jpaRestaurante.edit(c);
        } catch (Exception ex) {
            Logger.getLogger(BeanRestaurante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void eliminar (Integer id){
        try {
            jpaRestaurante.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanRestaurante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Restaurante buscar(Integer id){
        return jpaRestaurante.findRestaurante(id);
    }
    
    
    
}

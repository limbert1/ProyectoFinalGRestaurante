
package com.emergentes.bean;

import com.emergentes.entidades.Platillo;
import com.emergentes.jpa.PlatilloJpaController;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BeanPlatillo {
    
      private EntityManagerFactory emf;
    private PlatilloJpaController jpaPlatillo;

    public BeanPlatillo() {
        emf= Persistence.createEntityManagerFactory("prorecto");
        jpaPlatillo= new PlatilloJpaController(emf);     
    }
    public List<Platillo> listartodo(){
        return jpaPlatillo.findPlatilloEntities();
    }
    public void insertar (Platillo platillo){
        jpaPlatillo.create(platillo);
    }
    public void editar (Platillo platillo){
        try {
            jpaPlatillo.edit(platillo);
        } catch (Exception ex) {
            Logger.getLogger(BeanPlatillo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void eliminar (Integer id){
        try {
            jpaPlatillo.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanPlatillo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Platillo buscar(Integer id){
        return jpaPlatillo.findPlatillo(id);
    }
    
    
    
}



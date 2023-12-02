package com.emergentes.bean;

import com.emergentes.entidades.Pedido;
import com.emergentes.jpa.PedidoJpaController;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BeanPedido {

    private EntityManagerFactory emf;
    private PedidoJpaController jpaPedido;

    public BeanPedido() {
        emf = Persistence.createEntityManagerFactory("prorecto");
        jpaPedido = new PedidoJpaController(emf);
    }

    public List<Pedido> listartodo() {
        return jpaPedido.findPedidoEntities();
    }

    public void insertar(Pedido platillo) {
        jpaPedido.create(platillo);
    }

    public void editar(Pedido platillo) {
        try {
            System.out.println("Antes de editar Pedido");
            jpaPedido.edit(platillo);
            System.out.println("Pedido editado con éxito");
        } catch (Exception ex) {
            Logger.getLogger(BeanPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminar(Integer id) {
        try {
            jpaPedido.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Pedido buscar(Integer id) {
        return jpaPedido.findPedido(id);
    }

}

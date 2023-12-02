/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emergentes.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author LIMBERT
 */
@Entity
@Table(name = "platillo")
@NamedQueries({
    @NamedQuery(name = "Platillo.findAll", query = "SELECT p FROM Platillo p"),
    @NamedQuery(name = "Platillo.findById", query = "SELECT p FROM Platillo p WHERE p.id = :id"),
    @NamedQuery(name = "Platillo.findByTitulo", query = "SELECT p FROM Platillo p WHERE p.titulo = :titulo"),
    @NamedQuery(name = "Platillo.findByDescripcion", query = "SELECT p FROM Platillo p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Platillo.findByDisponible", query = "SELECT p FROM Platillo p WHERE p.disponible = :disponible")})
public class Platillo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 128)
    @Column(name = "titulo")
    private String titulo;
    @Size(max = 128)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "disponible")
    private Integer disponible;
    @OneToMany(mappedBy = "platilloId")
    private List<Pedido> pedidoList;
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    @ManyToOne
    private Categoria categoriaId;

    public Platillo() {
        this.id = 0;
        this.titulo = "";
        this.descripcion = "";
        this.disponible = 0;
        this.categoriaId = new Categoria();
        pedidoList= new ArrayList<Pedido>();
    }

public Platillo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDisponible() {
        return disponible;
    }

    public void setDisponible(Integer disponible) {
        this.disponible = disponible;
    }

    public List<Pedido> getPedidoList() {
        return pedidoList;
    }

    public void setPedidoList(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }

    public Categoria getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Categoria categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Override
        public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
        public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Platillo)) {
            return false;
        }
        Platillo other = (Platillo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Platillo{" + "id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", disponible=" + disponible + ", pedidoList=" + pedidoList + ", categoriaId=" + categoriaId + '}';
    }

  
  

    

   
}

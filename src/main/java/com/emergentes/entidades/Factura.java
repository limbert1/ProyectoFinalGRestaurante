/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emergentes.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author LIMBERT
 */
@Entity
@Table(name = "factura")
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findByFacturaId", query = "SELECT f FROM Factura f WHERE f.facturaId = :facturaId"),
    @NamedQuery(name = "Factura.findByMonto", query = "SELECT f FROM Factura f WHERE f.monto = :monto"),
    @NamedQuery(name = "Factura.findByFecha", query = "SELECT f FROM Factura f WHERE f.fecha = :fecha")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "factura_id")
    private Integer facturaId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private Integer monto;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    @ManyToOne
    private Cliente clienteId;

    public Factura() {
        this.facturaId = 0;
        this.monto = 0;
        this.fecha = new Date();
        this.clienteId = new Cliente();

    }

    public Factura(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facturaId != null ? facturaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.facturaId == null && other.facturaId != null) || (this.facturaId != null && !this.facturaId.equals(other.facturaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Factura{" + "facturaId=" + facturaId + ", monto=" + monto + ", fecha=" + fecha + ", clienteId=" + clienteId + '}';
    }

   

    public void setFecha(LocalDate fecha) {
        this.fecha = java.sql.Date.valueOf(fecha); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

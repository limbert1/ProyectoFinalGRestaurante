package com.emergentes.entidades;

import com.emergentes.entidades.Cliente;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2023-12-01T21:43:22")
@StaticMetamodel(Factura.class)
public class Factura_ { 

    public static volatile SingularAttribute<Factura, Date> fecha;
    public static volatile SingularAttribute<Factura, Integer> monto;
    public static volatile SingularAttribute<Factura, Integer> facturaId;
    public static volatile SingularAttribute<Factura, Cliente> clienteId;

}
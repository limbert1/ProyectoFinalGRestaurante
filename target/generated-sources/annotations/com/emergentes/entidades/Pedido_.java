package com.emergentes.entidades;

import com.emergentes.entidades.Cliente;
import com.emergentes.entidades.Platillo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2023-12-01T21:43:22")
@StaticMetamodel(Pedido.class)
public class Pedido_ { 

    public static volatile ListAttribute<Pedido, Cliente> clienteList;
    public static volatile SingularAttribute<Pedido, Date> fecha;
    public static volatile SingularAttribute<Pedido, Platillo> platilloId;
    public static volatile SingularAttribute<Pedido, Integer> id;
    public static volatile SingularAttribute<Pedido, Integer> cantidad;

}
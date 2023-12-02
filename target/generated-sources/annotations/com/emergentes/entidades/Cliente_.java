package com.emergentes.entidades;

import com.emergentes.entidades.Factura;
import com.emergentes.entidades.Pedido;
import com.emergentes.entidades.Restaurante;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2023-12-01T21:43:22")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile ListAttribute<Cliente, Restaurante> restauranteList;
    public static volatile SingularAttribute<Cliente, Pedido> pedidoId;
    public static volatile SingularAttribute<Cliente, String> carnet;
    public static volatile SingularAttribute<Cliente, Restaurante> restauranteId;
    public static volatile ListAttribute<Cliente, Factura> facturaList;
    public static volatile SingularAttribute<Cliente, Integer> id;
    public static volatile SingularAttribute<Cliente, String> nombre;

}
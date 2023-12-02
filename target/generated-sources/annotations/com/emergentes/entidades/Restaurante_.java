package com.emergentes.entidades;

import com.emergentes.entidades.Cliente;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2023-12-01T21:43:22")
@StaticMetamodel(Restaurante.class)
public class Restaurante_ { 

    public static volatile ListAttribute<Restaurante, Cliente> clienteList;
    public static volatile SingularAttribute<Restaurante, Integer> id;
    public static volatile SingularAttribute<Restaurante, String> nombre;

}
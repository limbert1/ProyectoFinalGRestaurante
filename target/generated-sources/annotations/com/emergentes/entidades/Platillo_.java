package com.emergentes.entidades;

import com.emergentes.entidades.Categoria;
import com.emergentes.entidades.Pedido;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2023-12-01T21:43:22")
@StaticMetamodel(Platillo.class)
public class Platillo_ { 

    public static volatile SingularAttribute<Platillo, String> descripcion;
    public static volatile SingularAttribute<Platillo, String> titulo;
    public static volatile ListAttribute<Platillo, Pedido> pedidoList;
    public static volatile SingularAttribute<Platillo, Integer> id;
    public static volatile SingularAttribute<Platillo, Integer> disponible;
    public static volatile SingularAttribute<Platillo, Categoria> categoriaId;

}
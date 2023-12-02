/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.emergentes.controller;

import com.emergentes.bean.BeanCategoria;
import com.emergentes.bean.BeanPlatillo;
import com.emergentes.bean.BeanPlatillo;
import com.emergentes.bean.BeanPlatillo;
import com.emergentes.entidades.Categoria;
import com.emergentes.entidades.Platillo;
import com.emergentes.entidades.Platillo;
import com.emergentes.entidades.Platillo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PlatilloServlet", urlPatterns = {"/PlatilloServlet"})
public class PlatilloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id;
        BeanPlatillo daoPlatillo = new BeanPlatillo();
        
        BeanCategoria daoCategoria = new BeanCategoria();/////citamos a los bean del anterior objeto a los usadores
        List<Categoria> lista;////y traemos a la lista, esto pasa cuando trabajamos con las llaves foraneas
         
        Platillo platillo = new Platillo();
       

        String action = (request.getParameter("action") != null) ? request.getParameter("action") : "view";///si no esta vacio se vee

        switch (action) {
            case "add":

                lista = daoCategoria.listartodo();
                request.setAttribute("categorias", lista);

                request.setAttribute("platillo", platillo);
                request.getRequestDispatcher("platillo-edit.jsp").forward(request, response);
                break;
                
            case "edit":

                lista = daoCategoria.listartodo();
                request.setAttribute("categorias", lista);

                id = Integer.parseInt(request.getParameter("id"));
                platillo = daoPlatillo.buscar(id);
                request.setAttribute("platillo", platillo);
                request.getRequestDispatcher("platillo-edit.jsp").forward(request, response);
                break;
                
            case "dele":
                id = Integer.parseInt(request.getParameter("id"));
                daoPlatillo.eliminar(id);
                response.sendRedirect("PlatilloServlet");
                break;

            case "view":
                List<Platillo> platillos = daoPlatillo.listartodo();///obtenmos los objetos
                request.setAttribute("platillos", platillos); ////y lo ponemos en la variables
                request.getRequestDispatcher("platillos.jsp").forward(request, response);
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ///trabajaremos con los usadores o objetos  platillo y categorias
        BeanPlatillo daoPlatillo = new BeanPlatillo();///nuevo
        BeanCategoria daoCategoria = new BeanCategoria();
      

        // Obtener los parámetros del formulario
 
        int id =Integer.parseInt(request.getParameter("id"));
        String titulo=request.getParameter("titulo");
        String descripcion=request.getParameter("descripcion") ;   
        int disponible =Integer.parseInt(request.getParameter("disponible"));
        int categoria_id =Integer.parseInt(request.getParameter("categoria_id"));
        ///se gusradara como  objeto
        Categoria cate = daoCategoria.buscar(categoria_id);
        ///colocamos el objeto
        Platillo platillo = new Platillo();
        platillo.setId(id);
        platillo.setTitulo(titulo);
        platillo.setDescripcion(descripcion);
        platillo.setDisponible(disponible);
        platillo.setCategoriaId(cate);

        // Resto del código
        if (id > 0) {
            daoPlatillo.editar(platillo);
        } else {
            daoPlatillo.insertar(platillo);
        }
        response.sendRedirect("PlatilloServlet");
    }

}

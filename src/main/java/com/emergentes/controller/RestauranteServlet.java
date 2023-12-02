
package com.emergentes.controller;

import com.emergentes.bean.BeanRestaurante;
import com.emergentes.entidades.Restaurante;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RestauranteServlet", urlPatterns = {"/RestauranteServlet"})
public class RestauranteServlet extends HttpServlet {



  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       int id;
        BeanRestaurante daoRestaurante = new BeanRestaurante();
        Restaurante c = new Restaurante();

        String action = (request.getParameter("action") != null) ? request.getParameter("action") : "view";///si no esta vacio se vee

        switch (action) {
            case "add":
                request.setAttribute("restaurante", c);
                request.getRequestDispatcher("restaurante-edit.jsp").forward(request, response);
                break;
            case "edit":
                id = Integer.parseInt(request.getParameter("id"));
                c = daoRestaurante.buscar(id);
                request.setAttribute("restaurante", c);
                request.getRequestDispatcher("restaurante-edit.jsp").forward(request, response);
                break;
            case "dele":
                id = Integer.parseInt(request.getParameter("id"));
                daoRestaurante.eliminar(id);
                response.sendRedirect("RestauranteServlet");
                break;

            case "view":
                List<Restaurante> lista = daoRestaurante.listartodo();
                request.setAttribute("restaurantes", lista);
                request.getRequestDispatcher("restaurantes.jsp").forward(request, response);
                break;

        }
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         BeanRestaurante daoRestaurante = new BeanRestaurante();
         int id = Integer.parseInt(request.getParameter("id"));
         String nombre =request.getParameter("nombre");
         
         Restaurante c =new Restaurante();
         c.setId(id);
         c.setNombre(nombre);
         if (id > 0){
             daoRestaurante.editar(c);
         }
         else{
             daoRestaurante.insertar(c);
         }
         response.sendRedirect("RestauranteServlet");
    }



}

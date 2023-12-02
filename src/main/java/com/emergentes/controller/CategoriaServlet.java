package com.emergentes.controller;

import com.emergentes.bean.BeanCategoria;
import com.emergentes.entidades.Categoria;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CategoriaServlet", urlPatterns = {"/CategoriaServlet"})
public class CategoriaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id;
        BeanCategoria daoCategoria = new BeanCategoria();
        Categoria c = new Categoria();

        String action = (request.getParameter("action") != null) ? request.getParameter("action") : "view";///si no esta vacio se vee

        switch (action) {
            case "add":
                request.setAttribute("categoria", c);
                request.getRequestDispatcher("categoria-edit.jsp").forward(request, response);
                break;
            case "edit":
                id = Integer.parseInt(request.getParameter("id"));
                c = daoCategoria.buscar(id);
                request.setAttribute("categoria", c);
                request.getRequestDispatcher("categoria-edit.jsp").forward(request, response);
                break;
            case "dele":
                id = Integer.parseInt(request.getParameter("id"));
                daoCategoria.eliminar(id);
                response.sendRedirect("CategoriaServlet");
                break;

            case "view":
                List<Categoria> lista = daoCategoria.listartodo();
                request.setAttribute("categorias", lista);
                request.getRequestDispatcher("categorias.jsp").forward(request, response);
                break;

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         BeanCategoria daoCategoria = new BeanCategoria();
         int id = Integer.parseInt(request.getParameter("id"));
         String descripcion =request.getParameter("descripcion");
         
         Categoria c =new Categoria();
         c.setId(id);
         c.setDescripcion(descripcion);
         if (id > 0){
             daoCategoria.editar(c);
         }
         else{
             daoCategoria.insertar(c);
         }
         response.sendRedirect("CategoriaServlet");

    }

}


package com.emergentes.controller;

import com.emergentes.bean.BeanPlatillo;
import com.emergentes.bean.BeanPedido;
import com.emergentes.bean.BeanPedido;
import com.emergentes.entidades.Platillo;
import com.emergentes.entidades.Pedido;
import com.emergentes.entidades.Pedido;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PedidoServlet", urlPatterns = {"/PedidoServlet"})
public class PedidoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id;
        BeanPedido daoPedido = new BeanPedido();

        BeanPlatillo daoPlatillo = new BeanPlatillo();/////citamos a los bean del anterior objeto a los usadores
        List<Platillo> lista;////y traemos a la lista, esto pasa cuando trabajamos con las llaves foraneas

        Pedido pedido = new Pedido();

        String action = (request.getParameter("action") != null) ? request.getParameter("action") : "view";/// si no esta vacio se vee ? esta cosa es como una respuesta directa

        switch (action) {
            case "add":
                lista = daoPlatillo.listartodo();
                request.setAttribute("platillos", lista);

                request.setAttribute("pedido", pedido);
                request.getRequestDispatcher("pedido-edit.jsp").forward(request, response);
                break;
            case "edit":
                lista = daoPlatillo.listartodo();
                request.setAttribute("platillos", lista);

                id = Integer.parseInt(request.getParameter("id"));
                pedido = daoPedido.buscar(id);
                request.setAttribute("pedido", pedido);
                request.getRequestDispatcher("pedido-edit.jsp").forward(request, response);
                break;
            case "dele":
                id = Integer.parseInt(request.getParameter("id"));
                daoPedido.eliminar(id);
                response.sendRedirect("PedidoServlet");
                break;

            case "view":
                List<Pedido> pedidos = daoPedido.listartodo();
                request.setAttribute("pedidos", pedidos);
                request.getRequestDispatcher("pedidos.jsp").forward(request, response);
                break;

        }
    }

 @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BeanPedido daoPedido = new BeanPedido();
        BeanPlatillo daoPlatillo = new BeanPlatillo();

        int id = Integer.parseInt(request.getParameter("id"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        // Obtén el parámetro de la solicitud
        String fechaStr = request.getParameter("fecha");

        // Crea un formateador de fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Declara una variable de tipo LocalDate
        LocalDate fecha = null;

        // Intenta parsear la cadena a un objeto LocalDate
        try {
            fecha = LocalDate.parse(fechaStr, formatter);
        } catch (DateTimeParseException e) {
        }

        int platillo_id = Integer.parseInt(request.getParameter("platillo_id"));

        // Obtiene el objeto Platillo usando el BeanPlatillo
        Platillo plate = daoPlatillo.buscar(platillo_id);

        // Crea un objeto Pedido
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setCantidad(cantidad);
        pedido.setFecha(fecha);
        pedido.setPlatilloId(plate);

        // Resto del código
        if (id > 0) {
            daoPedido.editar(pedido);
        } else {
            daoPedido.insertar(pedido);
        }
        response.sendRedirect("PedidoServlet");
    }
}


package com.emergentes.controller;

import com.emergentes.bean.Beancliente;
import com.emergentes.bean.Beanfactura;
import com.emergentes.entidades.Cliente;
import com.emergentes.entidades.Factura;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "facturaservlet", urlPatterns = {"/facturaservlet"})
public class facturaservlet extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id;
        Beanfactura daofactura = new Beanfactura();
        Beancliente daocliente = new Beancliente();/////citamos a los bean del anterior objeto a los usadores
        List<Cliente> lista;////y traemos a la lista, esto pasa cuando trabajamos con las llaves foraneas

        Factura factura = new Factura();

        String action = (request.getParameter("action") != null) ? request.getParameter("action") : "view";/// si no esta vacio se vee ? esta cosa es como una respuesta directa

        switch (action) {
            case "add":
                lista = daocliente.listarcliente();
                request.setAttribute("cliente", lista);

                request.setAttribute("factura", factura);
                request.getRequestDispatcher("factura-edit.jsp").forward(request, response);
                break;
            case "edit":
                lista = daocliente.listarcliente();
                request.setAttribute("cliente", lista);

                id = Integer.parseInt(request.getParameter("id"));
                factura = daofactura.buscar(id);
                request.setAttribute("factura", factura);
                request.getRequestDispatcher("factura-edit.jsp").forward(request, response);
                break;
            case "dele":
                id = Integer.parseInt(request.getParameter("id"));
                daofactura.eliminar(id);
                response.sendRedirect("PedidoServlet");
                break;

            case "view":
                List<Factura> facturas = daofactura.listarfact();
                request.setAttribute("factura", facturas);
                request.getRequestDispatcher("factura.jsp").forward(request, response);
                break;

        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         Beanfactura daofactura = new Beanfactura();
        Beancliente daocliente = new Beancliente();

        int id = Integer.parseInt(request.getParameter("factura_id"));
        int monto = Integer.parseInt(request.getParameter("monto"));
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

        int cliente_id = Integer.parseInt(request.getParameter("cliente_id"));

        // Obtiene el objeto Platillo usando el BeanPlatillo
        Cliente cli = daocliente.buscar(cliente_id);

        // Crea un objeto Pedido
        Factura factura = new Factura();
        factura.setFacturaId(id);
        factura.setMonto(monto);
        factura.setFecha(fecha);
        factura.setClienteId(cli);
        

        // Resto del código
        if (id > 0) {
            daofactura.editar(factura);
        } else {
            daofactura.insertar(factura);
        }
        response.sendRedirect("facturaservlet");
    }
    

}

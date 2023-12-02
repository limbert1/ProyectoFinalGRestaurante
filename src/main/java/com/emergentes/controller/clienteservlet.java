package com.emergentes.controller;

import com.emergentes.bean.BeanPedido;
import com.emergentes.bean.BeanRestaurante;
import com.emergentes.bean.Beancliente;
import com.emergentes.entidades.Cliente;
import com.emergentes.entidades.Pedido;
import com.emergentes.entidades.Restaurante;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "clienteservlet", urlPatterns = {"/clienteservlet"})
public class clienteservlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id;
        Beancliente daocliente = new Beancliente();

        BeanPedido daopedido = new BeanPedido();/////citamos a los bean del anterior objeto a los usadores
        List<Pedido> lista;////y traemos a la lista, esto pasa cuando trabajamos con las llaves foraneas
        
     

        Cliente cliente = new Cliente();

        String action = (request.getParameter("action") != null) ? request.getParameter("action") : "view";/// si no esta vacio se vee ? esta cosa es como una respuesta directa

        switch (action) {
            case "add":
                lista = daopedido.listartodo();
                request.setAttribute("pedido", lista);

                request.setAttribute("cliente", cliente);
                request.getRequestDispatcher("cliente-edit.jsp").forward(request, response);
                break;
            case "edit":
                lista = daopedido.listartodo();
                request.setAttribute("pedido", lista);

                id = Integer.parseInt(request.getParameter("id"));
                cliente = daocliente.buscar(id);
                request.setAttribute("cliente", cliente);
                request.getRequestDispatcher("cliente-edit.jsp").forward(request, response);
                break;
            case "dele":
                id = Integer.parseInt(request.getParameter("id"));
                daocliente.eliminar(id);
                response.sendRedirect("clienteservlet");
                break;

            case "view":
                List<Cliente> clientes = daocliente.listarcliente();
                request.setAttribute("cliente", clientes);
                request.getRequestDispatcher("cliente.jsp").forward(request, response);
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Beancliente daocliente = new Beancliente();
        BeanPedido daoPedido = new BeanPedido();
       

        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String carnet = request.getParameter("carnet");
        int pedido_id = Integer.parseInt(request.getParameter("pedido_id"));

        Pedido plate = daoPedido.buscar(pedido_id);

        //Crea un objeto Pedido
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombre(nombre);
        cliente.setCarnet(carnet);
        cliente.setPedidoId(plate);

        // Resto del código
        if (id > 0) {
            daocliente.editar(cliente);
        } else {
            daocliente.insertar(cliente);
        }
        response.sendRedirect("clienteservlet");
    }

}

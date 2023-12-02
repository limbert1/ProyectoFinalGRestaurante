<%@page import="com.emergentes.entidades.Cliente"%>
<%@page import="java.util.List"%>
<%
    List<Cliente> cliente = (List<Cliente>)request.getAttribute("cliente");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado de clientes</title>
    <style>
            body{
                background-image: url(https://img.freepik.com/foto-gratis/comida-callejera-fondo-espacio-copia-negro_23-2148242522.jpg?w=740&t=st=1701405557~exp=1701406157~hmac=aebb307f42b273aad10f739251f4e76b0065a7892c0ffedbb2062faf40075c34);
                background-size: cover;
            }
            
            h1 {
                text-align: center;
                font-size: 24px;
                color: white;
            }
        </style>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
         <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    </head>
    <body>
         <br><!-- comment -->
        <br><!-- comment -->
        <div class="container">
        <h1>LISTADO DE CLIENTES</h1>
        <p><a href="clienteservlet?action=add"><button  class="btn btn-primary"><i class="fa-solid fa-square-plus"></i>NUEVO</button></a></p>
        <table border="1" class="table table-success table-striped">
            <tr>
                <th>Id</th>
                <th>nombre</th>
                <th>carnet</th>
                <th>nro pedido</th>
                <th></th>
                <th></th>
            </tr>
            
            <%
                for (Cliente item : cliente){
            %>
            <tr>
                <td><%= item.getId() %></td>
                <td><%= item.getNombre() %></td>
                <td><%= item.getCarnet() %></td>
                <td><%= (item.getPedidoId() != null) ? item.getPedidoId().getCantidad() :"<span style='color: red;'>sin registro</span>" %></td>
                
                <td><a href="clienteservlet?action=edit&id=<%= item.getId() %>" ><i class="fa-solid fa-pen-to-square"></i></a></td> 
                <td><a href="clienteservlet?action=dele&id=<%= item.getId() %>" onclick="return(confirm('¿Estás seguro?'))"><i class="fa-solid fa-trash"></i></a></td>
            </tr>
            <%
                }
            %>
        </table>
        </div>
    </body>
</html>

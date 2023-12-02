<%@ page import="com.emergentes.entidades.Restaurante"%>
<%@ page import="java.util.List"%>


<%
    List<Restaurante> restaurantes = (List<Restaurante>)request.getAttribute("restaurantes");
%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
    <body >
          <br><!-- comment -->
        <br><!-- comment -->
    
        <div class="container">
        <h1 style="color: white" >LISTA DE RESTAURANTES</h1>
        <a href="RestauranteServlet?action=add" style="color: white"> <button  class="btn btn-primary"><i class="fa-solid fa-square-plus"></i>NUEVO</button></a>
        
        <table border="1" class="table table-success table-striped">
            <tr>
                <th>Id</th>
                <th>NOMBRE</th>
                <th></th>
                <th></th>
            </tr>
            <%
                for (Restaurante item: restaurantes) {
            %>
                <tr>
                    <td><%= item.getId() %></td>
                    <td><%= item.getNombre() %></td>
                    <td><a href="RestauranteServlet?action=edit&id=<%= item.getId() %>"><i class="fa-solid fa-pen-to-square"></i></a></td>
                    <td><a href="RestauranteServlet?action=dele&id=<%= item.getId() %>" onclick="return(confirm('Estas Seguro?'))"><i class="fa-solid fa-trash"></i></a></td>
                </tr>
                <%
                    }
                %>
        </table>
        </div>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

    </body>
</html>

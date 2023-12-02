<%@page import="com.emergentes.entidades.Cliente"%>
<%@page import="java.util.List"%>
<%@page import="com.emergentes.entidades.Factura"%>
<%
    Factura factura = (Factura) request.getAttribute("factura");
    List<Cliente> clientes = (List<Cliente>) request.getAttribute("cliente");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    </head>
    <body>
        <br><!-- comment -->
        <br><!-- comment -->
        <div class="container">  
        <h1>REGISTRO DE FACTURAS</h1>
      
        <form  action="facturaservlet" method="post">
               <input type="hidden" name="factura_id" value="<%= factura.getFacturaId()%>">
                <div class="mb-3">
                    <label class="form-label" style="color: white">MONTO</label>
                    <input type="text" class="form-control"  name="monto" value="<%= factura.getMonto()%>">            
                </div>
                <div class="mb-3">
                    <label  class="form-label" style="color: white">FECHA</label>
                    <input type="date" name="fecha" value="<%= factura.getFecha()%>" class="form-control"  >
                </div>
                <div class="mb-3">
                    <label  class="form-label" style="color: white">CLIENTE</label>
                    <select name="cliente_id">
                            <% for (Cliente item : clientes) {
                            %>
                            <option value= "<%= item.getId()%>" <%= (item.getId() == factura.getFacturaId()) ? "selected" : ""%>>
                                <%= item.getNombre()%>
                            </option>
                            <%
                                }
                            %>
                        </select>
                   
                </div>
                
                <button type="submit" class="btn btn-primary">GUARDAR</button>
            </form>                 
                
        </div>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    </body>
</html>

<%@page import="com.emergentes.entidades.Categoria"%>
<%
    Categoria cate = (Categoria) request.getAttribute("categoria");
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
             <h1 style="color: white">REGISTRAR CATEGORIA</h1>
            <form action="CategoriaServlet" method="post">
                <div class="mb-3">
                    <label for="descripcion" class="form-label" style="color: white">DESCRIPCION:</label>
                    <input type="hidden" name="id" value="<%= cate.getId()%>">
                    <input type="text" name="descripcion" id="descripcion" value="<%=cate.getDescripcion()%>">
                </div>
                <button type="submit" class="btn btn-primary">GUARDAR</button>
            </form>
        </div>
                 
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

    </body>
</html>

<%-- 
    Document   : login
    Created on : Dec 11, 2017, 12:59:23 PM
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="utils.Constantes" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>
             <c:out value="${prueba}"></c:out>
          <c:out value="${logeo}"></c:out>
            <c:out value="${activar}"></c:out>
        </h2>
        <br>
        <form action="${pageContext.request.contextPath}/login?op" method="get">
        <h1>registrar</h1>
        nombre<input type="text" id="nombre" name="nombre"/>
        contraseña<input type="text" id="pass" name="pass"/>
        correo<input type="text" id="correo" name="correo"/>
        <button value="registrar" name="op">Registrar</button>
        </form>
       
         <form action="${pageContext.request.contextPath}/login?op" method="get">
        <h1>iniciar sesion</h1>
        nombre<input type="text" id="nombre" name="nombre"/>
        contraseña<input type="text" id="pass" name="pass"/>
        <button value="entrar" name="op">Iniicar</button>
        </form>
    </body>
</html>

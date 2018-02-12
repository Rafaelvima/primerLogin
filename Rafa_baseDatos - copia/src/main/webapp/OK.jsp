<%-- 
    Document   : OK
    Created on : Nov 10, 2017, 11:13:18 AM
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
        <h1>Hello World!</h1>
          <c:out value="${prueba}"></c:out>
          <c:out value="${activar}"></c:out>
          <c:out value="${logeo}"></c:out><br>
          <a href="http://localhost:8080/Rafa_baseDatos/secure/alumnos">Ir a alumnos</a><br>
          <a href="http://localhost:8080/Rafa_baseDatos/secure/asignaturas">Ir a asignaturas</a><br>
          <a href="http://localhost:8080/Rafa_baseDatos/secure/notas">Ir a notas</a><br>

    </body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Manfred
  Date: 04/11/2014
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Application de congés</title>
</head>
<body>


<c:choose>
    <c:when test="${not empty currentSessionUser}">
        <a href="./">Index</a>
        <a href="./planning">Planning</a>
        <a href="./newVacation">Demander un congé</a>
        Connecté en tant que : ${currentSessionUser.firstname} ${currentSessionUser.lastname}</c:when>
    <c:otherwise>Non connecté</c:otherwise>
</c:choose>

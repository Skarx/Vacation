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
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" href="//cdn.datatables.net/1.10.3/css/jquery.dataTables.min.css">
    <script src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>
</head>
<body>


<c:choose>
    <c:when test="${not empty currentSessionUser}">
        <a href="./">Index</a>
        <a href="./planning">Planning</a>
        <a href="./newVacation">Demander un congé</a>

        <c:choose>
            <c:when test="${not empty managerSession}"><a href="./validateMgr">Validation congés Manager</a></c:when>
        </c:choose>
        <c:choose>
            <c:when test="${not empty HRSession}"><a href="./validateHR">Validation congés RH</a></c:when>
        </c:choose>

        Connecté en tant que : ${currentSessionUser.firstname} ${currentSessionUser.lastname}</c:when>
    <c:otherwise>Non connecté</c:otherwise>
</c:choose>

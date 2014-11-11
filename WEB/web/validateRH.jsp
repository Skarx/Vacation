<%--
  Created by IntelliJ IDEA.
  User: Manfred
  Date: 09/11/2014
  Time: 21:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h3>Congés validés par un manager</h3>

<table id="planning_table">
    <tr>
        <th>Demandeur</th>
        <th>Date début</th>
        <th>Date fin</th>
        <th>Solde</th>
        <th>Status</th>
        <th>Manager</th>
        <th>Commentaires</th>
    </tr>
    <c:forEach var="vacation" items="${vacations}">
        <tr>
            <td>${vacation.employee}</td>
            <td>${vacation.begdate}</td>
            <td>${vacation.enddate}</td>
            <td></td>
            <td>${vacation.status}</td>
            <td>${vacation.manager}</td>
            <td></td>
        </tr>
    </c:forEach>
</table>


<%@include file="footer.jsp"%>

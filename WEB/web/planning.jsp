<%--
  Created by IntelliJ IDEA.
  User: Manfred
  Date: 06/11/2014
  Time: 21:44
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="messages">
    <p>${message}</p>
</div>

<table>
    <tr>
        <th>Date début</th>
        <th>Date fin</th>
        <th>Status</th>
        <th>Demandeur</th>
        <th>Manager</th>
        <th>RH</th>
        <th>Commentaires</th>
    </tr>
    <tr>
        <c:forEach var="vacation" items="${vacations}">
            <td>${vacation.begdate}</td>
            <td>${vacation.enddate}</td>
            <td>${vacation.status}</td>
            <td>${vacation.employee}</td>
            <td>${vacation.manager}</td>
            <td>${vacation.hr}</td>
            <td></td>
        </c:forEach>
    </tr>
</table>

<%@include file="footer.jsp"%>
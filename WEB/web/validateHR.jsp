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

<table id="planning_table_validate_hr">
    <tr>
        <th>Employé</th>
        <th>Service</th>
        <th>Date début</th>
        <th>Date fin</th>
        <th>Solde</th>
        <th>Nombre de jours</th>
        <th>Manager</th>
        <th>Commentaires</th>
        <th>Validation</th>
    </tr>
    <c:forEach var="vacation" items="${vacations}">
        <tr>
            <td>${vacation.employee}</td>
            <td>${vacation.employee.service}</td>
            <td>${vacation.begdate}</td>
            <td>${vacation.enddate}</td>

            <td>${vacation.manager}</td>
            <td>
                <c:forEach var="comment" items="${vacation.comments}">
                    <c:choose>
                        <c:when test="${not empty comment}">${comment}<br></c:when>
                    </c:choose>
                </c:forEach>
            </td>
            <td>
                <form method="POST">
                    <input type="hidden" name="vacationId" value="${vacation.id}"/>
                    <input type="radio" name="validate" value="accept" /> Valider
                    <input type="radio" name="validate" value="refuse"/> Refuser <br>
                    <input type="text" name="comment" placeholder="raison"/>
                    <input type="submit" value="Envoyer"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<script>
    $(document).ready(function () {
        $('#planning_table_validate_hr').DataTable();
    });
</script>

<%@include file="footer.jsp"%>

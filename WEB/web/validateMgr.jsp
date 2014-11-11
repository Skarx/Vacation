<%--
  Created by IntelliJ IDEA.
  User: Manfred
  Date: 09/11/2014
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h3>Congés de mes collaborateurs en attente de validation</h3>

<table id="planning_table_validate_mgr">
    <tr>
        <th>Demandeur</th>
        <th>Date début</th>
        <th>Date fin</th>
        <th>Solde</th>
        <th>Status</th>
        <th>Commentaires</th>
        <th>Action</th>
    </tr>
    <c:forEach var="vacation" items="${vacations}">
        <tr>
            <td>${vacation.employee}</td>
            <td>${vacation.begdate}</td>
            <td>${vacation.enddate}</td>
            <td></td>
            <td>${vacation.status}</td>
            <td></td>
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
        $('#planning_table_validate_mgr').DataTable();
    });

</script>

<%@include file="footer.jsp"%>

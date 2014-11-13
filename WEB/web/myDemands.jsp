<%--
  Created by IntelliJ IDEA.
  User: Manfred
  Date: 06/11/2014
  Time: 21:44
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="messages">
    <p>${message}</p>
</div>

<table id="my_demands_table" class="display" cellspacing="0" width="100%">
    <tr>
        <th>Manager</th>
        <th>RH</th>
        <th>Date début</th>
        <th>Date fin</th>
        <th>Status</th>
        <th>Commentaires</th>
        <th>Suppression</th>
    </tr>
    <c:forEach var="vacation" items="${vacations}">
        <tr>
            <td>${vacation.manager}</td>
            <td>${vacation.hr}</td>
            <td>${vacation.begdate}</td>
            <td>${vacation.enddate}</td>
            <td>${vacation.status}</td>
            <td>
                <c:forEach var="comment" items="${vacation.comments}">
                    <c:choose>
                        <c:when test="${not empty comment}">${comment.creator} : ${comment.comments}<br></c:when>
                    </c:choose>
                </c:forEach>
            </td>
            <td>
                <c:choose>
                    <c:when test="${((vacation.status == 'pending') || (vacation.status == 'validatedMgr' && vacation.manager == null))}">
                        <form method="POST">
                            <input type="hidden" name="vacationId" value="${vacation.id}"/>
                            <input type="text" name="comment" placeholder="raison"/>
                            <input type="submit" value="Supprimer"/>
                        </form>
                    </c:when>
                    <c:when test="${((vacation.manager != null) && (vacation.status == 'validatedHr'))}">
                        <form method="POST">
                            <input type="hidden" name="vacationId" value="${vacation.id}"/>
                            <input type="text" name="comment" placeholder="raison"/>
                            <input type="submit" value="Demander suppression"/>
                        </form>
                    </c:when>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>

<script type="text/javascript">
    $(document).ready(function() {
        $('#my_demands_table').dataTable();
    } );
</script>

<%@include file="footer.jsp"%>
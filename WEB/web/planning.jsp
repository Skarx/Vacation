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

<div id="sort_vacations">
    <form method="POST" id="sort_form">
        <input type="radio" name="accessPoint" value="service"> Service
        <select name="service" id="radio_service" disabled>
            <c:forEach var="service" items="${services}">
                <option value="${service.name}">${service.name}</option>
            </c:forEach>
        </select><br>

        <input type="radio" name="accessPoint" value="employee"> Employé
        <select name="employee" id="radio_employee" disabled>
            <c:forEach var="employee" items="${employees}">
                <option value="${employee.id}">${employee.firstname} ${employee.lastname}</option>
            </c:forEach>
        </select><br>

        <input type="radio" name="accessPoint" value="associates"> Collaborateurs<br>
        <input type="submit" value="Filtrer">
    </form>
</div>

<table id="planning_table" class="display" cellspacing="0" width="100%">
    <tr>
        <th>Employé</th>
        <th>Service</th>
        <th>Date début</th>
        <th>Date fin</th>
        <th>Status</th>
        <th>Commentaires</th>
    </tr>
    <c:forEach var="vacation" items="${vacations}">
        <tr>
            <td>${vacation.employee}</td>
            <td>${vacation.employee.service}</td>
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
        </tr>
    </c:forEach>
</table>

<script type="text/javascript">
    $(document).ready(function() {
        $("input[name='accessPoint']").change(function(e){
           if($(this).val() == 'service'){
               $("#radio_service").prop("disabled", false);
               $("#radio_employee").prop("disabled", true);
           }else if($(this).val() == 'employee'){
               $("#radio_service").prop("disabled", true);
               $("#radio_employee").prop("disabled", false);
           }else{
               $("#radio_service").prop("disabled", true);
               $("#radio_employee").prop("disabled", true);
            }
        });
        $('#planning_table').dataTable();
    } );
</script>

<%@include file="footer.jsp"%>
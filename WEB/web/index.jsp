<%--
  Created by IntelliJ IDEA.
  User: Hervé
  Date: 23/10/2014
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="messages">
    <c:forEach var="message" items="${messages}">
        <p>${message}</p>
    </c:forEach>
</div>

<div id="login">
<form method="POST" id="login_form">
    <select name="employee">
        <c:forEach var="employee" items="${employees}">
            <option value="${employee.id}">${employee.firstname} ${employee.lastname}</option>
        </c:forEach>
    </select>
    <input type="submit" value="Login">
</form>
</div>

<%@include file="footer.jsp"%>
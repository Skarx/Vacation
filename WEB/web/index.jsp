<%--
  Created by IntelliJ IDEA.
  User: Hervé
  Date: 23/10/2014
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
<select name="employee">
    <c:forEach var="employee" items="${employees}">
        <option value="${employee.name}">${employee}</option>
    </c:forEach>
</select>

<form id="login_form">
    <input type="submit" value="Login">
</form>
</div>

<%@include file="footer.jsp"%>
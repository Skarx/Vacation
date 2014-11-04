<%--
  Created by IntelliJ IDEA.
  User: Manfred
  Date: 04/11/2014
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp"%>

<div>
    <form method="get" action="newVacation">
        <fieldset>
            <label for="begDate">Du</label>
            <input type="date" id="begDate" name="begDate" value=""/>

            <label for="endDate">Au</label>
            <input type="date" id="endDate" name="begDate" value=""/>

            <label for="begTime">Du</label>
            <select id="begTime" name="begTime">
                <option value="">Matin</option>
                <option vlaue="">Journée</option>
            </select>

            <label for="endTime">Du</label>
            <select id="endTime" name="endTime">
                <option value="">Matin</option>
                <option vlaue="">Journée</option>
            </select>

            <label for="comment">Commentaire :</label>
            <textarea id="comment" name="comment"></textarea>
        </fieldset>
        <input type="submit" value="Valider"  />
    </form>
</div>

<%@include file="footer.jsp"%>
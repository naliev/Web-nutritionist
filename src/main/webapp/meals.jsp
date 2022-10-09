<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<div>
    <a href="meals.jsp?action=add"><p>Add meal</p></a>
</div>
<div>
    <table cellspacing="2" border="1" cellpadding="5" width="600">
        <tbody>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Modify</th>
            <th>Delete</th>
        </tr>

        <jsp:useBean id="meals" scope="request" type="java.util.List"/>
        <jsp:useBean id="TimeUtil" class="ru.javawebinar.topjava.util.TimeUtil"/>
        <c:set var="count" value="0" scope="page"/>
        <c:forEach var="meal" items="${meals}">

            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color: <c:out value="${meal.excess eq true ? 'red': 'green'}"/>">

            <td><p>${TimeUtil.getFormattedDate(meal.dateTime)}</p></td>
            <td><p>${meal.description}</p></td>
            <td><p>${meal.calories}</p></td>
            <%--<td><a href="meals.jsp?uuid=<c:out value="${count}"/>&action=update">update</a></td>
            <td><a href="meals.jsp?uuid=<c:out value="${count}"/>&action=delete">delete</a></td>
            --%>
        </tr>
            <c:set var="count" value="${count + 1}" scope="page"/>
        </c:forEach>
    </table>
</div>
</body>
</html>
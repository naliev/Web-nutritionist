<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<head>
    <title>${meal.id == null ? 'Add' : 'Update'} meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>${meal.id == null ? 'Add' : 'Update'} meal</h2>

<form method="POST" action='meals'>
    <jsp:useBean id="TimeUtil" class="ru.javawebinar.topjava.util.TimeUtil"/>
    <input type="hidden" name="id" value="${meal.id}">
    Date and time : <input
        type="text" name="dateTime"
        placeholder="2022-01-30 12:00"
        value="${TimeUtil.getFormattedDate(meal.dateTime)}"/> <br/>
    Description : <input
        type="text" name="description"
        value="${meal.description}"/> <br/>
    Calories : <input type="number" name="calories" min="10" max="10000"
                      value="<c:out value="${meal.calories}" />"/> <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
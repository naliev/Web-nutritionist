<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<fmt:setBundle basename="messages.app"/>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <a href="meals"><fmt:message key="app.title"/></a> | <a href="users"><fmt:message key="user.title"/></a> | <a
        href="index.jsp"><fmt:message key="app.home"/></a>
    <a href="meals"><fmt:message key="app.title"/></a> | <a href="users"><fmt:message key="user.title"/></a> | <a
        href="${pageContext.request.contextPath}"><fmt:message key="app.home"/></a>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
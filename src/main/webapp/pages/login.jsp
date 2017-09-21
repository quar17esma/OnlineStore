<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="Labels"/>
<!DOCTYPE html>
<html>
<head>
    <title>Log in</title>
</head>
<body>
    <form name="loginForm" method="POST" action="controller">
        <input type="hidden" name="action" value="login"/>
        Login:<br/>
        <input type="text" name="login" value=""/>
        <br/>Password:<br/>
        <input type="password" name="password" value=""/>
        <br/>
        <input type="submit" value="Log in"/>
        <%--<br/>--%>
        <%--${errorLoginPassMessage}--%>
        <%--<br/>--%>
        <%--${wrongAction}--%>
        <%--<br/>--%>
        <%--${nullPage}--%>
    </form>
    <hr/>
    <form name="registrationForm" method="POST" action="/register">
        <input type="submit" value="Register">
    </form>
</body>
</html>

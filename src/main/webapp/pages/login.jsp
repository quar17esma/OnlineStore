<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="Labels"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title.login"/></title>
</head>
<body>

<div>
    <c:out value="${successRegistrationMessage}"/>
    <c:out value="${errorLoginPassMessage}"/>
</div>
<div>
    <form name="loginForm" method="POST" action="controller">
        <input type="hidden" name="action" value="login"/>
        <label><fmt:message key="label.login"/></label>
        <br/>
        <input type="text" name="login" value=""/>
        <br/>
        <label><fmt:message key="label.password"/></label>
        <br/>
        <input type="password" name="password" value=""/>
        <br/>
        <fmt:message var="buttonLogin" key="button.login"/>
        <input type="submit" value="${buttonLogin}"/>
        <fmt:message var="buttonReset" key="button.reset"/>
        <input type="reset" value="${buttonReset}">
        <%--<br/>--%>
        <%--${errorLoginPassMessage}--%>
    </form>
    <hr/>
    <form name="registrationForm" method="POST" action="register">
        <fmt:message var="buttonRegisration" key="button.registration"/>
        <input type="submit" value="${buttonRegisration}">
    </form>
</div>

</body>
</html>

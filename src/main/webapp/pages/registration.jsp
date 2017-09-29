<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<c:if test="${pageContext.session.getAttribute('locale') == 'ru_RU'}">
    <fmt:setLocale value="ru_RU"/>
</c:if>
<fmt:setBundle basename="Labels"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title.registration"/></title>
</head>
<body>

<div>
    <c:out value="${errorRegistrationMessage}"/>
</div>

<div>
    <form name="registrationForm" method="POST" action="controller">
        <input type="hidden" name="action" value="registration"/>

        <label><fmt:message key="label.name"/></label>
        <br/>
        <input type="text" name="name" value="${name}"/>
        <br/>

        <label><fmt:message key="label.login"/></label>
        <br/>
        <input type="text" name="login" value="${login}"/>
        <c:out value="${errorBusyEmailMessage}"/>
        <br/>

        <label><fmt:message key="label.password"/></label>
        <br/>
        <input type="password" name="password" value=""/>
        <br/>

        <br/>
        <fmt:message var="buttonRegister" key="button.register"/>
        <input type="submit" value="${buttonRegister}"/>
    </form>
</div>

</body>
</html>

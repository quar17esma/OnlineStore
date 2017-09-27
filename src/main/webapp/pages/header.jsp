<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="en_US"/>
<%--<fmt:setLocale value="ru_RU"/>--%>
<fmt:setBundle basename="Labels"/>
<!DOCTYPE html>
<html>
<head>
    <title>Header</title>
</head>
<body>
<div class="header">
    <h1><fmt:message key="online.store"/></h1>
</div>
<hr/>
<fmt:message key="message.hello"/> ${client.name}!
<br/>
<div>
    <form class="button" name="logoutForm" method="POST" action="controller">
        <input type="hidden" name="action" value="logout"/>
        <fmt:message var="buttonLogout" key="button.logout"/>
        <input type="submit" value="${buttonLogout}"/>
    </form>
    <form class="button" name="myOrderForm" method="POST" action="controller">
        <input type="hidden" name="action" value="my_order"/>
        <fmt:message var="buttonMyOrder" key="button.my.order"/>
        <input type="submit" value="${buttonMyOrder}">
    </form>
    <%--For ADMIN--%>
    <c:if test="${sessionScope.client.user.role == 'ADMIN'}">
        <form class="button" name="addNewGoodForm" method="POST" action="controller">
            <input type="hidden" name="action" value="edit_good"/>
            <fmt:message var="buttonAddGood" key="button.add.good"/>
            <input type="submit" value="${buttonAddGood}">
        </form>
    </c:if>
    <br/>
    <hr/>
</div>
</body>
</html>

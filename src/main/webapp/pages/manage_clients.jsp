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
    <style>
        .clientInfo {
            float: left;
        }
    </style>
    <title><fmt:message key="title.buy.now"/></title>
</head>
<body>

<jsp:include page="/pages/header.jsp"/>

<div>
    <c:out value="${successBlockClient}"/>
</div>

<c:forEach items="${clients}" var="client">

    <div class="clientInfo">
        <label><fmt:message key="label.name"/></label>
        <c:out value="${client.name}"/>
    </div>

    <form class="button" method="POST" action="./block_client">
        <input type="hidden" name="clientId" value="${client.id}">
        <fmt:message var="buttonBlockClient" key="button.block.client"/>
        <input type="submit" value="${buttonBlockClient}">
    </form>
    <br/>

</c:forEach>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:if test="${pageContext.session.getAttribute('locale') == 'ru_RU'}">
    <fmt:setLocale value="ru_RU"/>
</c:if>
<c:if test="${pageContext.session.getAttribute('locale') == 'en_US'}">
    <fmt:setLocale value="en_US"/>
</c:if>
<fmt:setBundle basename="Labels"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title.buy.now"/></title>
</head>
<body>

    <jsp:include page="header.jsp"/>

    <div class="field">
        <label><fmt:message key="good.name"/></label>
        <p><c:out value="${good.name}"/></p>
    </div>
    <br/>
    <div class="field">
        <label><fmt:message key="good.description"/></label>
        <p><c:out value="${good.description}"/></p>
    </div>
    <br/>
    <div class="field">
        <label><fmt:message key="good.price"/></label>
        <p><c:out value="${good.price}"/></p>
    </div>
    <br/>
    <form name="addToOrderForm" action="controller" method="post">
        <input type="hidden" name="action" value="add_to_order">
        <input type="hidden" name="goodId" value="${good.id}">

        <label><fmt:message key="order.quantity"/></label>
        <input type="number" name="ordered_quantity" min="1" max="10" step="1" value="1">

        <fmt:message var="buttonAddToOrder" key="button.add.to.order"/>
        <input class="button" type="submit" value="${buttonAddToOrder}">
    </form>
    <br/>
</body>
</html>

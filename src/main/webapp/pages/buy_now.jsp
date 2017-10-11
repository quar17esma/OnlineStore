<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="en_US"/>
<c:if test="${pageContext.session.getAttribute('locale') == 'ru_RU'}">
    <fmt:setLocale value="ru_RU"/>
</c:if>
<fmt:setBundle basename="Labels"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title.buy.now"/></title>
</head>
<body>

    <jsp:include page="/pages/header.jsp"/>

    <div>
        <c:out value="${successAddToCart}"/>
    </div>

    <c:if test="${good != null}">
        <div class="field">
            <label><fmt:message key="label.name"/></label>
            <c:out value="${good.name}"/>
        </div>
        <br/>
        <div class="field">
            <label><fmt:message key="label.description"/></label>
            <p><c:out value="${good.description}"/></p>
        </div>
        <br/>
        <div class="field">
            <label><fmt:message key="label.price"/></label>
            <ctg:price price="${good.price}"/>
        </div>
        <br/>
        <form name="addToOrderForm" method="POST" action="./add_to_order">
            <input type="hidden" name="goodId" value="${good.id}">
            <input type="hidden" name="ordered_quantity" value="1">

            <fmt:message var="buttonAddToOrder" key="button.add.to.cart"/>
            <input class="button" type="submit" value="${buttonAddToOrder}">
        </form>
        <br/>
    </c:if>

</body>
</html>

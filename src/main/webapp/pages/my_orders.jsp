<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
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
    <title><fmt:message key="title.my.orders"/></title>
</head>
<body>

<div>
    <jsp:include page="header.jsp"/>
</div>

<div>
    <c:out value="${successPayOrder}"/>
    <c:out value="${errorPayOrder}"/>
</div>

<div>
    <c:forEach items="${orders}" var="order">

        <label><fmt:message key="label.ordered.at"/></label>
        <c:out value="${order.orderedAt}"/>

        <c:forEach items="${order.goods}" var="good">
            <div class="field">
                <label><fmt:message key="label.name"/></label>
                <c:out value="${good.name}"/>
            </div>
            <div class="field">
                <label><fmt:message key="label.price"/></label>
                <ctg:price price="${good.price}"/>
            </div>
            <div class="field">
                <label><fmt:message key="label.quantity"/></label>
                <c:out value="${good.quantity}"/>
            </div>
        </c:forEach>

        <c:if test="${order.status != 'PAID'}">
            <div>
                <form name="payOrderForm" method="POST" action="controller">
                    <input type="hidden" name="action" value="pay_order"/>
                    <input type="hidden" name="orderId" value="${order.id}"/>

                    <fmt:message var="buttonPayOrder" key="button.pay.order"/>
                    <input type="submit" value="${buttonPayOrder}">
                </form>
            </div>
        </c:if>

        <hr/>
    </c:forEach>

</div>

</body>
</html>

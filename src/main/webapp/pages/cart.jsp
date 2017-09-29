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
    <title><fmt:message key="title.cart"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>

<div>
    <c:out value="${emptyOrderMessage}"/>
</div>

<c:if test="${not empty order.goods}">
    <div>
        <c:forEach items="${order.goods}" var="good">
            <div class="field">
                <label><fmt:message key="label.name"/></label>
                <c:out value="${good.name}"/>
            </div>
            <div class="field">
                <label><fmt:message key="label.description"/></label>
                <br/>
                <c:out value="${good.description}"/>
            </div>
            <div class="field">
                <label><fmt:message key="label.price"/></label>
                <c:out value="${good.price}"/>
            </div>
            <div class="field">
                <label><fmt:message key="label.quantity"/></label>
                <c:out value="${good.quantity}"/>
            </div>
            <hr/>
        </c:forEach>
    </div>

    <div class="field">
        <form name="sendOrderForm" method="POST" action="controller">
            <input type="hidden" name="action" value="send_order"/>
            <fmt:message var="buttonSendOrder" key="button.send.order"/>
            <input type="submit" value="${buttonSendOrder}">
        </form>
    </div>
</c:if>

</body>
</html>

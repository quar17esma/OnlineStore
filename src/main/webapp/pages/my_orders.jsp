<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="Labels"/>
<!DOCTYPE html>
<html>
<head>
    <title>My orders</title>
</head>
<body>

<div>
    <jsp:include page="header.jsp"/>
</div>

<div>
    <c:forEach items="${orders}" var="order">
        <c:forEach items="${order.goods}" var="good">
            <div class="field">
                <label><fmt:message key="good.name"/></label>
                <c:out value="${good.name}"/>
            </div>
            <div class="field">
                <label><fmt:message key="good.price"/></label>
                <c:out value="${good.price}"/>
            </div>
            <div class="field">
                <label><fmt:message key="order.quantity"/></label>
                <c:out value="${good.quantity}"/>
            </div>
        </c:forEach>
        <div>
            <form name="payOrderForm" method="POST" action="controller">
                <input type="hidden" name="action" value="pay_order"/>
                <input type="submit" value="Pay order">
            </form>
        </div>
        <hr/>
    </c:forEach>

</div>

</body>
</html>

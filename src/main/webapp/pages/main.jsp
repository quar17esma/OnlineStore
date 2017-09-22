<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="Labels"/>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
    <div class="header">
        <h1><fmt:message key="online.store"/></h1>
    </div>
    <hr/>
    <h3>Welcome</h3>
    ${user}, hello!
    <form name="logoutForm" method="POST" action="controller">
        <input type="hidden" name="action" value="logout"/>
        <input type="submit" value="Log out"/>
    </form>
    <form name="myOrderForm" method="POST" action="controller">
        <input type="hidden" name="action" value="my_order"/>
        <input type="submit" value="My order">
    </form>
    <%--For ADMIN--%>
    <c:if test="${pageContext.request.getSession(false).getAttribute('clientId') eq 2}">
        <form name="addNewGoodForm" method="POST" action="controller">
            <input type="hidden" name="action" value="edit_good"/>
            <input type="submit" value="Add good">
        </form>
    </c:if>
    <hr/>

    <c:forEach items="${goods}" var="good">
        <div class="field">
        <label><fmt:message key="good.name"/></label>
        <c:out value="${good.name}"/>
    </div>
        <div class="field">
            <label><fmt:message key="good.description"/></label>
            <p><c:out value="${good.description}"/></p>
        </div>
        <div class="field">
            <label><fmt:message key="good.price"/></label>
            <c:out value="${good.price}"/>
        </div>
        <div class="field">
            <form name="buyNowForm" method="POST" action="controller">
                <input type="hidden" name="action" value="buy_now"/>
                <input type="hidden" name="goodId" value="${good.id}">
                <input type="submit" value="Buy now">
            </form>
        </div><br/>

        <%--For ADMIN--%>
        <c:if test="${pageContext.request.getSession(false).getAttribute('clientId') eq 2}">
            <form name="goToEditGoodForm" method="POST" action="controller">
                <input type="hidden" name="action" value="edit_good"/>
                <input type="hidden" name="goodId" value="${good.id}">
                <input type="submit" value="Edit">
            </form>
            <form name="deleteGoodForm" method="POST" action="controller">
                <input type="hidden" name="action" value="delete_good"/>
                <input type="hidden" name="goodId" value="${good.id}">
                <input type="submit" value="Delete">
            </form>
        </c:if>
        <hr/>
    </c:forEach>
    <br/>
</body>
</html>

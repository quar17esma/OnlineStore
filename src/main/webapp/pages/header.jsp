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
    <style>
        .headerButton {
            float: left;
        }
    </style>
    <title>Header</title>
</head>
<body>
<div class="header">
    <h1><fmt:message key="online.store"/></h1>
<hr/>
<fmt:message key="message.hello"/> ${client.name}!
<br/>
<div>
    <form class="headerButton" name="logoutForm" method="POST" action="./logout">
        <fmt:message var="buttonLogout" key="button.logout"/>
        <input type="submit" value="${buttonLogout}"/>
    </form>
    <form class="headerButton" name="cartForm" method="POST" action="./show_cart">
        <fmt:message var="buttonCart" key="button.cart"/>
        <input type="submit" value="${buttonCart}">
    </form>
    <form class="headerButton" name="myOrdersForm" method="POST" action="./my_orders">
        <fmt:message var="buttonMyOrders" key="button.my.orders"/>
        <input type="submit" value="${buttonMyOrders}">
    </form>
    <form class="headerButton" name="goodsForm" method="POST" action="./show_goods">
        <fmt:message var="buttonGoodsList" key="button.goods.list"/>
        <input type="submit" value="${buttonGoodsList}">
    </form>
    <%--For ADMIN--%>
    <c:if test="${sessionScope.client.user.role == 'ADMIN'}">
        <form class="headerButton" name="addNewGoodForm" method="POST" action="./edit_good">
            <fmt:message var="buttonAddGood" key="button.add.good"/>
            <input type="submit" value="${buttonAddGood}">
        </form>
        <form class="headerButton" name="manageClientsForm" method="POST" action="./manage_clients">
            <fmt:message var="buttonManageClients" key="button.manage.clients"/>
            <input type="submit" value="${buttonManageClients}">
        </form>
    </c:if>
    <br/>
    <hr/>
</div>
</div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<%--<fmt:setLocale value="ru_RU"/>--%>
<fmt:setBundle basename="Labels"/>
<!DOCTYPE html>
<html>
<head>
    <style>
        .button {
            float: left;
        }
    </style>
    <title><fmt:message key="title.main"/></title>
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
    <div>
        <c:out value="${successAddGoodMessage}"/>
        <br/>
        <c:out value="${successDeleteGoodMessage}"/>
        <br/>
    </div>
    <div>
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
                <form class="button" name="buyNowForm" method="POST" action="controller">
                    <input type="hidden" name="action" value="buy_now"/>
                    <input type="hidden" name="goodId" value="${good.id}">
                    <fmt:message var="buyButton" key="button.buy.now"/>
                    <input type="submit" value="${buyButton}">
                </form>
            </div>

            <%--For ADMIN--%>
            <c:if test="${sessionScope.client.user.role == 'ADMIN'}">
                <form class="button" name="goToEditGoodForm" method="POST" action="controller">
                    <input type="hidden" name="action" value="edit_good"/>
                    <input type="hidden" name="goodId" value="${good.id}">
                    <fmt:message var="buttonEdit" key="button.edit"/>
                    <input type="submit" value="${buttonEdit}">
                </form>
                <form class="button" name="deleteGoodForm" method="POST" action="controller">
                    <input type="hidden" name="action" value="delete_good"/>
                    <input type="hidden" name="goodId" value="${good.id}">
                    <fmt:message var="buttonDelete" key="button.delete"/>
                    <input type="submit" value="${buttonDelete}">
                </form>
            </c:if>
            <br/>
            <hr/>
        </c:forEach>
        <br/>
    </div>

</body>
</html>

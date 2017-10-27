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
        .button {
            float: left;
        }
    </style>
    <title><fmt:message key="title.goods"/></title>
</head>
<body>

    <jsp:include page="/pages/header.jsp"/>

    <div>
        <c:out value="${successAddGoodMessage}"/>
        <c:out value="${successDeleteGoodMessage}"/>
        <c:out value="${successSendOrderMessage}"/>
    </div>
    <div>
        <c:forEach items="${goods}" var="good">
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
                <ctg:price price="${good.price}"/>
            </div>
            <div class="field">
                <form class="button" name="buyNowForm" method="POST" action="./buy_now">
                    <input type="hidden" name="goodId" value="${good.id}">
                    <fmt:message var="buyButton" key="button.buy.now"/>
                    <input type="submit" value="${buyButton}">
                </form>
            </div>

            <%--For ADMIN--%>
            <c:if test="${sessionScope.client.user.role == 'ADMIN'}">
                <form class="button" name="goToEditGoodForm" method="POST" action="./edit_good">
                    <input type="hidden" name="goodId" value="${good.id}">
                    <fmt:message var="buttonEdit" key="button.edit"/>
                    <input type="submit" value="${buttonEdit}">
                </form>
                <form class="button" name="deleteGoodForm" method="POST" action="./delete_good">
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

    <div>
        <c:forEach begin="1" end="${pagesQuantity}" varStatus="loop">
            <form class="button" name="goodsPagesForm" method="POST" action="./show_goods">
                <input type="hidden" name="page" value="${loop.count}">
                <input type="submit" value="${loop.count}">
            </form>
        </c:forEach>
    </div>

</body>
</html>

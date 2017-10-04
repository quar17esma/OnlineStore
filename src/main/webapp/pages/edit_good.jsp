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
    <title><fmt:message key="title.edit.good"/></title>
</head>
<body>
<jsp:include page="/pages/header.jsp"/>
<div>
    <c:out value="${errorAddGoodMessage}"/>
</div>
<div>
    <form name="addGoodForm" method="POST" action="./add_good">
        <input type="hidden" name="goodId" value="${good.id}">

        <label><fmt:message key="label.name"/></label>
        <br/>
        <input type="text" name="name" value="${good.name}" required="required"/>
        <br/>

        <label><fmt:message key="label.description"/></label>
        <br/>
        <input type="text" name="description" value="${good.description}" maxlength="1000" required="required"/>
        <br/>

        <label><fmt:message key="label.price"/></label>
        <br/>
        <input type="number" name="price" min="0" max="100000000" step="1" value="${good.price}" required="required"/>
        <br/>

        <label><fmt:message key="label.quantity"/></label>
        <br/>
        <input type="number" name="quantity" min="0" max="100" step="1" value="${good.quantity}"
               required="required">
        <br/>

        <br/>
        <fmt:message var="buttonConfirm" key="button.confirm"/>
        <input type="submit" value="${buttonConfirm}"/>
    </form>
</div>

</body>
</html>

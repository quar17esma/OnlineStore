<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="Labels"/>
<!DOCTYPE html>
<html>
<head>
    <title>Edit good</title>
</head>
<body>
<div>
    <c:out value="${errorAddGoodMessage}"/>
</div>
<div>
    <form name="addGoodForm" method="POST" action="controller">
        <input type="hidden" name="action" value="add_good"/>
        Name:
        <br/>
        <input type="text" name="name" value="${good.name}" required="required"/>
        <br/>
        Description:
        <br/>
        <input type="text" name="description" value="${good.description}" maxlength="1000" required="required"/>
        <br/>
        Price:
        <br/>
        <input type="number" name="price" min="0" max="100000000" step="1" value="${good.price}" required="required"/>
        <br/>
        Quantity:
        <br/>
        <input type="number" name="quantity" min="0" max="100" step="1" value="${good.quantity}"
               required="required">
        <br/>
        <br/>
        <input type="submit" value="Confirm"/>
    </form>
</div>

</body>
</html>

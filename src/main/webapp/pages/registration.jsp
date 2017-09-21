<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="Labels"/>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form name="registrationForm" method="POST" action="/controller">
    <input type="hidden" name="action" value="registration"/>
    Name:<br/>
    <input type="text" name="name" value=""/><br/>
    Login:<br/>
    <input type="text" name="login" value=""/><br/>
    Password:<br/>
    <input type="password" name="password" value=""/>
    <br/>
    ${errorLoginPassMessage}
    <br/>
    ${wrongAction}
    <br/>
    ${nullPage}
    <br/>
    <input type="submit" value="Register"/>
</form>
<hr/>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="ctx" value="${pageContext['request'].contextPath}" />
<html>
<head>
<title>Spring boot + JSP + Jar Web Application</title>
</head>
<body>
	<font color="red">${errorMessage}</font>
	<form method="post">
		Name : <input type="text" name="name" /> Password : <input
			type="password" name="password" /> <input type="submit" />
	</form>
</body>
</html>
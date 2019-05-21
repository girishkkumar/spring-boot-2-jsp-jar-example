<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	Hi : ${name}
	<br />
	<c:choose>
		<c:when test="${not empty employees}">
			Here are the list of your employees:
			<table><tr><th>ID</th><th>First Name</th><th>Last Name</th><th>Description</th></tr>
			<c:forEach var="item" items="${employees}">
			<tr>
		       <td><c:out value="${item.id}" /></td>
				<td><c:out value="${item.firstName}" /></td>
				<td><c:out value="${item.lastName}" /></td>
				<td><c:out value="${item.designation}" /></td>
				</tr>
			</c:forEach>
			</table>
		</c:when>
		<c:when test="${not empty employee}">
	Here is your info <br>
	<table><tr><th>ID</th><th>First Name</th><th>Last Name</th><th>Description</th></tr>
			<c:forEach var="item" items="${employee}">
			<tr>
		       <td><c:out value="${item.id}" /></td>
				<td><c:out value="${item.firstName}" /></td>
				<td><c:out value="${item.lastName}" /></td>
				<td><c:out value="${item.designation}" /></td>
				</tr>
			</c:forEach>
			</table>
		</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>

	<BR />

</body>

</html>
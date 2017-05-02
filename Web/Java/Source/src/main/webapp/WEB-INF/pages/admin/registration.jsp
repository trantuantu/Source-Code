<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Tour - Admin</title>
</head>
<body>
<h1>registration</h1>
<form:form action="registration.do" method = "POST" commandName = "registrationForm">
	<table>
		<tr>
			<td>Registion ID</td>
			<td><form:input path="key" /></td>
		</tr>
		<tr>
			<td>Tour ID</td>
			<td><form:input path="key_tour" /></td>
		</tr>
		<tr>
			<td>Clinet ID</td>
			<td><form:input path="key_user" /></td>
		</tr>
		<tr>
			<td>Guide ID</td>
			<td><form:input path="key_guide" /></td>
		</tr>
		<tr>
			<td>Tongtien</td>
			<td><form:input path="total_money" /></td>
		</tr>
		<tr>
			<td>Tiencoc</td>
			<td><form:input path="deposit" /></td>
		</tr>
		<tr>
			<td>Soluongnguoi</td>
			<td><form:input path="count_people" /></td>
		</tr>
		<tr>
			<td>Ghichu</td>
			<td><form:input path="note" /></td>
		</tr>
		<tr>
			<td>Trangthai</td>
			<td><form:input path="status" /></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" name="action" value="Edit" />
				<input type="submit" name="action" value="Search" />
			</td>
		</tr>
	</table>
</form:form>
<br>
<table border="1">
	<th>Registion ID</th>
	<th>Tour ID</th>
	<th>Clinet ID</th>
	<th>Guide ID</th>
	<th>Tongtien</th>
	<th>Tiencoc</th>
	<th>Soluongnguoi</th>
	<th>Ghichu</th>
	<th>Trangthai</th>
	<c:forEach items="${registrationFormList}" var="registrationForm">
		<tr>
			<td>${registrationForm.key}</td>
			<td>${registrationForm.key_tour}</td>
			<td>${registrationForm.key_user}</td>
			<td>${registrationForm.key_guide}</td>
			<td>${registrationForm.total_money}</td>
			<td>${registrationForm.deposit}</td>
			<td>${registrationForm.count_people}</td>
			<td>${registrationForm.note}</td>
			<td>${registrationForm.status}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
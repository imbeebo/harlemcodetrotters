<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GonqBox - Register new account</title>
<link href="style.css" rel="stylesheet">
</head>
<body>
	<form action="register" method="post">
		<fieldset style="width: 300px">
			<legend> Register </legend>
			<% if(request.getAttribute("login_error") != null) { %>
			<div class="registration-error"><%= request.getAttribute("login_error") %></div>
			<% } %>
			<table>
				<tr>
					<td>Username</td>
					<td><input type="text" name="username" required="required" value="<%= request.getAttribute("username") %>" /></td>
				</tr>
				<tr>
					<td>EMail Address</td>
					<td><input type="text" name="email-address" required="required" value="<%= request.getAttribute("email_address") %>" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="userpass" required="required" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Register" /></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>
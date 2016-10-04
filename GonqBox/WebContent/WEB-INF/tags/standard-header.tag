<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ attribute name="title"  %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><%= jspContext.getAttribute("title") %></title>
		<link href="style.css" rel="stylesheet">
	</head>
	<body>
		<header>
			[Logo] <span class="login-register"><a href="/GonqBox/">[login]</a> <a href="/GonqBox/register">[register]</a></span>
		</header>
		<main>

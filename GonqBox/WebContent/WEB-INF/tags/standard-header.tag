<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ attribute name="title"  %>
<!DOCTYPE html>
<html ondrop='uploadFiles(event)' ondragover='handleDrag(event)'>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><%= jspContext.getAttribute("title") %></title>
		<!--
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.4/css/bootstrap.min.css" integrity="sha384-2hfp1SzUoho7/TsGGGDaFdsuuDL0LX2hnUp6VkX3CUQ2K4K+xjboZdsXyp4oUHZj" crossorigin="anonymous">
		-->
		<link href="style.css" rel="stylesheet">
		<script src='upload.js' async></script>
	</head>
	<body>
		<header>
			[Logo]
			<div class="login-register"><a href="/GonqBox/">[login]</a> <a href="/GonqBox/register">[register]</a></div>
			<div id='uploadList'></div>
		</header>
		<main>

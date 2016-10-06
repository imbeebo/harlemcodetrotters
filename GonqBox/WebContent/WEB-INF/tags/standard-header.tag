<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ attribute name="title"  %>
<!DOCTYPE html>
<html ondrop='dropFiles(event)' ondragover='handleDrag(event)'>
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
			<div class='login-register'><a href='/GonqBox/'>[login]</a> <a href='/GonqBox/register'>[register]</a></div>
			<div class='header-upload'>
				<form action='/GonqBox/upload' method='POST' onsubmit='submitFiles(event)' style='display: inline-block'>
					<input type='file' id='upload-files' multiple>
					<input type='submit' value='upload'>
				</form>
				<div id='uploadList'></div>
			</div>
		</header>
		<main>

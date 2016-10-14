<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ attribute name="title"  %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><%= jspContext.getAttribute("title") %></title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.4/css/bootstrap.min.css" integrity="sha384-2hfp1SzUoho7/TsGGGDaFdsuuDL0LX2hnUp6VkX3CUQ2K4K+xjboZdsXyp4oUHZj" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">		
		<link href="style/bootstrap-overrides.css" rel="stylesheet">
	</head>
	<body>
		<nav class="navbar navbar-light bg-faded">
			<h1 class="navbar-brand m-b-0">GonqBox</h1>
			<%
				if(session.getAttribute("user") == null){
			%>
				<ul class="nav navbar-nav pull-xs-right">
					<li class="nav-item">
						<a type="button" class="btn btn-secondary" href="/GonqBox/">Login</a>
					</li>
					<li class="nav-item">
						<a type="button" class="btn btn-secondary" href="/GonqBox/register">Register</a>
					</li>
				</ul>
			<%
				} else {
			%>
				<ul class="nav navbar-nav pull-xs-right">
					<li class="nav-item">
						<a type="button" class="btn btn-secondary" href="/GonqBox/folder">My Folder</a>
					</li>
				</ul>
			<%
				}
			%>
			
		</nav>
		<div class="container m-t-2">

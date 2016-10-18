<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ attribute name="title"%>
<!DOCTYPE html>
<html ondrop='dropFiles(event)' ondragover='handleDrag(event)'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=jspContext.getAttribute("title")%></title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.4/css/bootstrap.min.css"
	integrity="sha384-2hfp1SzUoho7/TsGGGDaFdsuuDL0LX2hnUp6VkX3CUQ2K4K+xjboZdsXyp4oUHZj"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
<link href="style/bootstrap-overrides.css" rel="stylesheet">
<script src='upload.js' async></script>
</head>
<body>
	<nav class="navbar navbar-light bg-faded">
	<div class="container">
		<a class="navbar-brand m-b-0" href="/GonqBox/">GonqBox</a>
		<%
			if (session.getAttribute("user") == null) {
		%>
		<ul class="nav navbar-nav pull-xs-right">
			<li class="nav-item"><a type="button" class="btn btn-secondary"
				href="/GonqBox/">Login</a></li>
			<li class="nav-item"><a type="button" class="btn btn-secondary"
				href="/GonqBox/register">Register</a></li>
		</ul>
		<%
			} else {
		%>		
		<ul class="nav navbar-nav pull-xs-right m-l-2">
			<li class="nav-item dropdown pull-left">
				<a class="nav-link dropdown-toggle pull-left" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Profile</a>
				<div class="dropdown-menu pull-left">
					<a class="dropdown-item" href="/GonqBox/folder">My Folder</a>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item" href="/GonqBox/logout">Log Out</a>
				</div>
			</li>
		</ul>

		<form class='navbar-form pull-xs-right' action='/GonqBox/upload'
			method='POST' onsubmit='submitFiles(event)'>
			<div class='input-group'>
				<div class='input-group-btn dropdown'>
					<button class='btn btn-secondary dropdown-toggle' type='button'
						id='upload-list-dropdown' data-toggle='dropdown'>
						<span class="caret"></span>
					</button>
					<ul class='dropdown-menu open' id='uploadList'>
					</ul>
				</div>
				<label class='form-control btn btn-secondary'>Select
					files...<input type='file' id='upload-files' multiple
					style='display: none'>
				</label> <span class="input-group-btn"> <input type='submit'
					value='upload' class='btn btn-secondary'>
				</span>
			</div>
		</form>
		<%
			}
		%>
	</div>
	</nav>
	<div class="container m-t-2">
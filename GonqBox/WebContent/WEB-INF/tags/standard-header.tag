<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ attribute name="title" %>

<!DOCTYPE html>
<html ondrop="dropFiles(event)" ondragover="handleDrag(event)">
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
<c:if test="${ empty sessionScope.loc}">
	<c:set var="loc" value="en_CA" scope="session" />
</c:if>
<c:if test="${not empty param.locale}">
			  		<c:set var="loc" value="${param.locale}" scope="session" />
</c:if>
<fmt:setLocale value="${sessionScope.loc}" scope="session" />
<fmt:setBundle basename="ui" var="uitranslations" scope="session" />
</head>
<body>
	<nav class="navbar navbar-light bg-faded navbar-fixed-top ">
		<h1 class="navbar-brand m-b-0">GonqBox</h1>
		<ul class="nav navbar-nav pull-xs-right">
			<%
				if (session.getAttribute("user") == null) {
			%>
			<li class="nav-item"><a type="button" class="btn btn-secondary"
				href="/GonqBox/"><fmt:message
						bundle="${sessionScope.uitranslations}" key="login" /></a></li>
			<li class="nav-item"><a type="button" class="btn btn-secondary"
				href="/GonqBox/register"><fmt:message
						bundle="${sessionScope.uitranslations}" key="register" /></a></li>
			<%
				}
			%>
			<li class="nav-item">
				<div class="dropdown">
					<button class="btn btn-default dropdown-toggle" type="button"
						id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="true">
						<fmt:message bundle="${sessionScope.uitranslations}"
							key="changeLanguage" />
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
						<li><c:url var="engUrl" value="">
								<c:param name="locale" value="en_CA" />
							</c:url> <a href="${engUrl}"><img src="resources/icon_flag_Eng.gif" />
								English</a></li>
						<li><c:url var="frUrl" value="">
								<c:param name="locale" value="fr_CA" />
							</c:url> <a href="${frUrl}"><img src="resources/icon_flag_fr.gif" />
								Français</a></li>
					</ul>
				</div>
			</li>
			<%
				if (session.getAttribute("user") != null) {
			%>
			<li class="nav-item"><a type="button" class="btn btn-secondary"
				href="/GonqBox/folder">My Folder</a></li>
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
					Files...<input type='file' id='upload-files' multiple
					style='display: none'>
				</label> <span class="input-group-btn"> <input type='submit'
					value='upload' class='btn btn-secondary'>
				</span>
			</div>
		</form>
		<%
			} else {
		%>
		</ul>
		<%
			}
		%>
	</nav>
	<div style="height: 60px;"></div>
	<div class="container m-t-2">
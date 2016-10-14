<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="gonqbox" %>
<gonqbox:standard-header title="GonqBox" />

	<%
		String messengerErr = (String)request.getAttribute("login_messenger_err");  
		String messenger = (String)request.getAttribute("login_messenger");  
	%>

	<%
		if(messengerErr != null){
	%>
		<div class="row m-t-2">
			<div class="alert alert-danger" role="alert"><%= messengerErr %></div>
		</div>
	<%
		}
	%>
	
	<%
		if(messenger != null){
	%>
		<div class="row m-t-2">
			<div class="alert alert-success" role="alert"><%= messenger %></div>
		</div>
	<%
		}
	%>

	<div class="row m-t-2">
		<div class="col-md-7">
			<p class="h2">Welcome to GonqBox.</p>
			<p class="h5 m-t-2">GonqBox is a bilingual online file sharing application.</p>
			<p class="h5">GonqBox allows you to manage your files within a virtual directory.</p>
		</div>
		
		<div class="col-md-5">
			<div class="card card-block">
				<h3 class="card-title">Login</h3>
				<form action="loginServlet" method="post">
					<div class="form-group">
						<input type="text" class="form-control" name="username" required="required" placeholder="username">
					</div>
					<div class="form-group">
					    <input type="password" class="form-control" name="password" required="required" placeholder="password">
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-success">Submit</button>
					</div>
				</form>
			</div>
		</div>
	
	</div>
	
<gonqbox:standard-footer />

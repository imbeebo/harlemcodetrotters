<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="gonqbox" %>
<gonqbox:standard-header title="GonqBox - Register new account" />

	<%
		String loginReply = (String)request.getAttribute("login_error");  
		if(loginReply != null){
	%>
		<div class="row m-t-2">
			<div class="alert alert-danger" role="alert"><%= loginReply %></div>
		</div>
	<%
		}
	%>

	<div class="row m-t-2">
		<div class="col-md-7">
			<div class="card card-block">
				<h3 class="card-title">Register</h3>
				<form action="register" method="post">
					<div class="form-group">
						<input type="text" class="form-control" name="username" required="required" placeholder="username">
					</div>
					<div class="form-group">
						<input type="email" class="form-control" name="email-address" required="required" placeholder="email">
					</div>
					<div class="form-group">
					    <input type="password" class="form-control" name="userpass" required="required" placeholder="password">
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-success">Submit</button>
					</div>
				</form>
			</div>
		</div>
	</div>	
	
<gonqbox:standard-footer />

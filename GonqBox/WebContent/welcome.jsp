<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="gonqbox" %>
<gonqbox:standard-header title="Welcome" />

	<h3>Login successful!!!</h3>
	<h4>Hello, <%=session.getAttribute("name")%></h4>

<gonqbox:standard-footer />

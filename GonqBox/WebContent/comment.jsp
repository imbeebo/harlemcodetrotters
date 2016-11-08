<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.List" %>
<%@ page import="gonqbox.models.Comment" %>
<%@ page import="gonqbox.models.User" %>

	<%
	int fID=Integer.parseInt(request.getParameter("fileID")); 
	
	//create list of comments
	@SuppressWarnings("unchecked") List<Comment> comments = (List<Comment>)request.getAttribute("comments");	
	
	//create list of users (in sync with comments)
	@SuppressWarnings("unchecked") List<User> users = (List<User>)request.getAttribute("users");	
	
	%>

	<div class="card-block">
			<% if(comments != null){ %>
				<table class="table table-sm table-hover dataTable nowrap order-column" data-page-length='25'>
					<thead>
						<th>Comment</th>
					</thead>
					
					<tbody id="userFiles">
						<% for(int i = 0; i < comments.size(); i++){ %>
							<tr>
								<td><a href="#"><%= comments.get(i).getUsername() %></a></td>
								<td><a href="#"><%= comments.get(i).getBody() %></a></td>
							</tr>						
						<% } %>
					</tbody>
				</table>
			<% }else{ %>
				There are no comments on this file.
			<% } %>
	</div>
	
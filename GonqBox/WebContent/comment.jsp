<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.List" %>
<%@ page import="gonqbox.models.Comment" %>

	<%
	int fID=Integer.parseInt(request.getParameter("fileID")); 
	
	//create list of comments
	@SuppressWarnings("unchecked") List<Comment> comments = (List<Comment>)request.getAttribute("comments");	
	
	%>
			<!-- display all the comments if there are any -->
	<div class="card-block">
			<% if(comments != null){ %>
				<table class="table table-sm table-hover dataTable nowrap order-column" data-page-length='25'>
					<thead>
						<th>Comment</th>
					</thead>
					<!--for each comment found add it to a new table row  -->
					<tbody id="userFiles">
						<% for(int i = 0; i < comments.size(); i++){ %>
							<tr>
								<td><a href="#"><%= comments.get(i).getBody() %></a></td>
							</tr>						
						<% } %>
					</tbody>
				</table>
			<% }else{ %>
				There are no comments on this file.
			<% } %>
			</div>	
	</div>
	<form action="AddCommentServlet" method="post">
			<div class="form-group">
				<input type="text" class="form-control" name="Comment: " required="required" key="comment" />">
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-success" key="submit" /></button>
			</div>
	</form>
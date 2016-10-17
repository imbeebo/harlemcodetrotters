<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="gonqbox" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<gonqbox:standard-header title="Folder" />

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="gonqbox.models.File" %> <% /*This is an issue we'll repeatedly face...*/ %>
<%@ page%>

	<%
		String folderOwner = (String)request.getAttribute("folder_owner");  
		int folderFileCount = (Integer)request.getAttribute("folder_file_count");  
		int folderSize = (Integer)request.getAttribute("folder_size");
		@SuppressWarnings("unchecked") List<File> files = (List<File>)request.getAttribute("files");		
	%>


	<div class="row m-t-2">
		<div class="card">
			<div class="card-header">
				<h3 class="card-title"><%= folderOwner != null ?  folderOwner+"'s " : "" %>Folder</h3>
			</div>
			<div class="card-block">
				<table class="table table-sm table-hover dataTable nowrap order-column" data-page-length='25'>
					<thead>
						<th><fmt:message bundle="${sessionScope.uitranslations}" key="file" /></th>
						<th><fmt:message bundle="${sessionScope.uitranslations}" key="size" /></th>
						<th><fmt:message bundle="${sessionScope.uitranslations}" key="owner" /></th>
						<th><fmt:message bundle="${sessionScope.uitranslations}" key="uploadDate" /></th>
					</thead>
					<tbody>
						<% for(int i = 0; i < files.size(); i++){ %>
							<tr>
								<td><%= files.get(i).getName() %></td>
								<td><%= files.get(i).getFileSize() %></td>
								<td>TODO</td>
								<td>TODO</td>
							</tr>						
						<% } %>
					</tbody>
				</table>
			</div>
			<div class="card-footer text-muted">
				File count = <%= folderFileCount %>
				Folder size = <%= folderSize %>
		    </div>
		</div>
	</div>
<gonqbox:standard-footer />

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="gonqbox" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<gonqbox:standard-header title="Folder" />

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="gonqbox.models.File" %> <% /*This is an issue we'll repeatedly face...*/ %>


<script>

$(document).ready(function() {
	$("#userFiles").on("click", "tr", function() {
	    var fileID = $(this).children()[0].value;
	    document.getElementById("addCommentFileID").value = fileID;

    	var title= $(this).children()[1].innerText;
	    $.ajax({
            type : "POST",
            url : "comment",
            data : "fileID=" + fileID,
            success : function(data) {
                $(".modal-body").html(data);
            	var fileName = document.getElementById("fileURL").innerText;
            	document.getElementById("myModalLabel").innerText = title;
            }
        });
	});    
});     
</script>
	<%
		String folderOwner = (String)request.getAttribute("folder_owner");  
		int folderFileCount = (Integer)request.getAttribute("folder_file_count");  
		int folderSize = (Integer)request.getAttribute("folder_size");
		@SuppressWarnings("unchecked") List<File> files = (List<File>)request.getAttribute("files");		
	%>

	

	<div class="row m-t-2">
		<div class="card">
			<div class="card-header">
				<h3 class="card-title pull-xs-left"><%= folderOwner != null ?  folderOwner+"'s " : "" %>Folder</h3>
				<% if(true/*user is valid (owner || collaborator) of folder*/) { %>
				<a class="btn btn-outline-success pull-xs-right" href="#" role="button">Add Files</a>
				<% } %>
			</div>
			<div class="card-block">
			<% if(files != null){ %>
				<table class="table table-sm table-hover dataTable nowrap order-column" data-page-length='25'>
					<thead>
						<th><fmt:message bundle="${sessionScope.uitranslations}" key="file" /></th>
						<th><fmt:message bundle="${sessionScope.uitranslations}" key="size" /></th>
						<th><fmt:message bundle="${sessionScope.uitranslations}" key="owner" /></th>
						<th><fmt:message bundle="${sessionScope.uitranslations}" key="uploadDate" /></th>
						<th>Actions</th>
					</thead>
					<tbody id="userFiles">
						<% for(int i = 0; i < files.size(); i++){ %>
							<tr data-toggle="modal" data-target="#myModal"><input type="hidden" value="<%= files.get(i).getFileID() %>" />
								<td><a href="#" id="fileURL"><%= files.get(i).getName() %></a></td>
								<td><%= files.get(i).getFileSize() %></td>
								<td>TODO</td>
								<td>TODO</td>
								<td>TODO</td>
							</tr>						
						<% } %>
					</tbody>
				</table>
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel"></h4>
	      </div>
	      <div class="modal-body">
	        
	      </div>
	      <div class="modal-footer">
			<script>
				$(document).ready(function() {
					$("#addCommentBtn").on("click", function() {
					    var fileID = document.getElementById("addCommentFileID").value;
					    var comment=document.getElementById("userComment").value;
					    document.getElementById("userComment").value = "";

					    if(comment =="" || comment == null) return;
					    
					    $.ajax({
				            type : "POST",
				            url : "AddComment",
				            data : { fileID: fileID, comment: comment },
				            success : function(data) {
				                $(".modal-body").html(data);
				            }
				        });
					});    
				});     
			</script>
			<form id="addCommentForm">
				<input type= "hidden" id="addCommentFileID">
				<div class="form-group">
					<input type="text" class="form-control" id="userComment" />
				</div>
				<div class="form-group">
					<button type="button" name="addCommentBtn" id="addCommentBtn" class="btn btn-success">Comment</button>
				</div>
			</form>
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
			<% }else{ %>
				<fmt:message bundle="${sessionScope.uitranslations}" key="noFilesInDir" />
			<% } %>
			</div>
			<div class="card-footer text-muted">
				<fmt:message bundle="${sessionScope.uitranslations}" key="fileCount" /> = <%= folderFileCount %>
				<fmt:message bundle="${sessionScope.uitranslations}" key="folderSize" /> = <%= folderSize %>
		    </div>
		</div>
	</div>
<gonqbox:standard-footer />

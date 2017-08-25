<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import= " com.google.cloud.ReadChannel" %>
<%@page import= "  com.google.cloud.storage.Acl" %>
<%@page import= "  com.google.cloud.storage.Acl.User" %>
<%@page import= "  com.google.cloud.storage.Blob" %>
<%@page import= "  com.google.cloud.storage.Storage" %>
<%@page import= "  com.google.cloud.storage.BlobId" %>
<%@page import= "  com.google.cloud.storage.StorageOptions" %>

<%@page import= "  com.google.api.client.http.HttpTransport" %>
<%@page import= "  com.google.api.gax.paging.Page" %>

<%@page import= "  com.google.api.services.storage.StorageScopes" %>
<%@page import= "  com.google.api.services.storage.model.Bucket" %>
<%@page import= "  com.google.cloud.storage.Storage.BlobListOption" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Download your file</title>
</head>
<body>

<h2>Below is a list of uploaded files:</h2>
	<% 
	Storage storage = StorageOptions.getDefaultInstance().getService(); 
	Page<Blob> blobs = storage.list("vendor-bucket17");
	for (Blob blob1 : blobs.iterateAll()) 
			{%>
			<%Object object = blob1.getName();%>
			<li><%= object %></li>
		 	<%}
	%> 
	
	<br/>
<form action="Download" method="post" >
	<table border="1">
 
		<tr>
			<td> 
				<label>Please Enter FileName</label>
			</td>
			<td colspan="4">
				<input type="text" name="file" required/>
			</td>
		</tr>
	 	<tr>
			<td>
				<label>Select your Role</label>
			</td>
			<td>
			  <input type="radio" name="role" value="Admin" checked="checked"/>Admin
			</td>
			<td>
			  <input type="radio" name="role" value="All" />All
			</td>
			<td>  
			  <input type="radio" name="role" value="Staff" />Staff
			</td>
			
		</tr>
		<tr>
			<td colspan="4" style="text-align:center;">
			<input type="submit" />
			</td>
		</tr>
	</table>
</form>	
</body>

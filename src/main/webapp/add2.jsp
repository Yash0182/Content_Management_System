<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload Your File Here</title>
</head>
<body>

<form action="upload1" method="post" enctype="multipart/form-data">
	<table border="1">
 
		<tr>
			<td> 
				<label>Please Select File</label>
			</td>
			<td colspan="4">
				<input type="file" name="file" multiple required/>
			</td>
		</tr>
		
		
	 	<tr>
			<td>
				<label>Select The Roles Who can access this files</label>
			</td>
			<td>
			  <input type="radio" name="permission" value="Admin" checked="checked"/>Admin
			</td>
			<td>
			  <input type="radio" name="permission" value="All" />All
			</td>
			<td>  
			  <input type="radio" name="permission" value="Others" />Others
			</td>
		</tr> 
		
		<tr>
			<td colspan="4" style="text-align:center;">
				<input type="submit"/>
			</td>
		</tr>
  	</table>
</form>

</body>
</html>
<link rel="stylesheet" type="text/css"  href="./static/css/common/for_Down.css">
<html>
	<head>
	</head>
	
	<body>
	<div class="login-wrap">
		<div class="login-html">
	<br/>
		<#if (fileNamesSize >= 1)>
		  <h3>Below Are the files that are already uploaded</h3>
		  <#list fileNames as fileName>
				<li>${fileName}</li>
		  </#list>
		  <br/>
		  <br/>
		  
		<#else>
		  <h3>No Files are Uploaded Previously</h3>
		  <br/>
		</#if>
		
		<h3>Specify the File Details Below To Download:</h3>
		
		
			
		<form  action="Download" method="post" >
			<table > 
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
						<label>Please Enter VendorName</label>
					</td>
					<td colspan="4">
						<input type="text" name="vendorname" required/>
					</td>
				</tr>
				
				<tr>
					<td> 
						<label>Provide your id</label>
					</td>
					<td colspan="4">
						<input type="text" name="userid" required/>
					</td>
				</tr>
				
			 	<tr>
					<td>
						<label>Select your Role</label>
					</td>
					<td>
					  <input type="radio" name="permission" value="Admin" checked="checked"/>Admin
					</td>
					<td>  
					  <input type="radio" name="permission" value="Staff" />Staff
					</td>
					<td>  
					  <input type="radio" name="permission" value="Others" />Others
					</td>
					
					
				</tr>
				
				<tr>
					<td colspan="4" style="text-align:center;">
					<input type="submit" />
					</td>
				</tr>
				
			</table>
		</form>	
		
	<a href='welcome'>Home</a>
	</div>
	</div>
	</body>
</html>
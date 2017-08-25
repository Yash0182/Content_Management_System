<link rel="stylesheet" type="text/css"  href="./static/css/common/for_Upload.css">
<html>
	<head>
		<title>Upload</title>
	</head>
	<body>
	<!-- <div class="img1">
			<img src="https://www.google.com/search?tbs=sbi:AMhZZisNR353UAjKqZLmF3IQu7BvUQwXON7k7r_17g4pgM_1R5TeuVqEg6KsIGLRMjePRxPcbSXxtJ-NsuZysYoM2JSkq-Q5knYlaBR1rf2_1RYPoFCS23s9z1kWDz9f2qBo2c6LQ3Wi2C-8RpjB6c_1uvJgZIkH-oISkK1QiFB6ADjr2Bb6cTuZ_13DTY6tMobk5WeRRwts5eiYqzo79c4-hnVYaesp66735mGhi8uC_12unFsAGxomsuYZJNLMUHFztnYCYDy8uDEydm57mYgqZ-CQL-NS4CytK7MMNq3yx37mDhGtvRKuq4V3fl-voMSybvCPOJt9qUVzP3fHn8xVpc4s_1oJ59h8AdsHg"/>
			
		</div> -->
		<div class="login-wrap">
			<div class="login-html">
		<h2>Upload</h2>
		
		<br/>
		<div class="login-form">
		<form action="uploadtest" method="post" enctype="multipart/form-data">
			<table id = "hor-minimalist-b">
			 <tbody>
				<tr>
					<td> 
						<label>Please Select File</label>
					</td>
					<td colspan="4">
						<input type="file" name="file" multiple required/>
					</td>
				</tr>
				<br/>
				
			 	<tr>
					<td>
						<label>Select The Roles Who can access this file</label>
					</td>
					<td>
					  <input type="checkbox"  name="permission" value="Admin" checked="checked"/>Admin
					</td>
					<td>  
					  <input type="checkbox"  name="permission" value="Staff" />Staff
					</td>
					<td>  
					  <input type="checkbox"  name="permission" value="Others" />Others
					</td>
					<td>
					  <input type="checkbox"  name="permission" value="All" />*
					</td>	
				</tr> 
				
				<tr>
					<td> 
						<label>Your Name Please</label>
					</td>
					<td colspan="1">
						<input type="text" name="uploader" required/>
					</td>
				</tr>
		 	
				 <tr>
					<td> 
						<label>Vendor Name</label>
					</td>
					<td>
						<input type="text" name="vendorid" required/>
					</td>
				 </tr>
				 
				 <tr>
					<td> 
						<label>Check User Ids who can access this file</label>
					</td>
					<td colspan="3">
						 
						<select name="userid" multiple size=3 style="width: 300px">
						<#list users as user>
							  <option value="${user}">${user}</option>
						</#list>
						</select>
					</td>
				</tr>
				
				<tr>
					<td> 
						<label>Create New Directory</label>
					</td>
					<td>
						<input type="text" name="directoryName"/>
					</td>
					<td colspan="3">
					Just Input as folder1/folder2/folder3 for three folders hierarchy
					</td>
				</tr>
				
				<tr>
					<td colspan="4" style="text-align:center;">
						<input type="submit"/>
					</td>
				</tr>
			</tbody>
		  </table>
		</form>
		<a href='welcome'>Home</a>
		</div>
		</div>
		</div>
	</body>
</html>
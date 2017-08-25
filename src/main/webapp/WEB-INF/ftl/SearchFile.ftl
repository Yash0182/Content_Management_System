<link rel="stylesheet" type="text/css"  href="./static/css/common/for_FileSearch.css">
<html>
	<head>
	<title>SearchResult</title>
	</head>
	
	<body>
	
		<h3>${message}</h3>
		 
		<#if found == "foundFile" >   <#--foundFile getting from the SearchFile.java -->
			
			<table class="rwd-table" cellspacing="20">
				<tr>
				    <th>File Name</th>
				    <td></td>
				    <th>Uploader</th>
				    <td></td>
				    <th>FileLocation</th>
				    <td></td>
				    
				</tr>
			<#list 0..listSize-1 as i>
		  
		 		<tr>
				    <td data-th="File Name">${fileName[i]}</td>
				    <td></td>
				   
				    <td data-th="Uploader">${uploader[i]}</td>
				    <td></td>
				   
				    <td data-th="FileLocation">${Location[i]}</td>
				    <td></td>
				    
				</tr>
		
			</#list>
		
		</#if>
		
		<div id="bottom">
			<a href='welcome'>Home</a>
		</div>
	</body>
</html>

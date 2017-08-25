
<html>
	<head>
	<title>SearchResult</title>
	</head>
	
	<body>
		<br/>
		<br/>
		<br/>
		<h3>${message}</h3>
		 
		<#if found == "foundFile" >   <#--foundFile getting from the SearchFile.java -->
			
			
			<#list 0..listSize-1 as i>
		  
			  	<h4>FileName: ${fileName[i]}</h4>
				<h4>Uploader Name: ${uploader[i]}</h4>
				<h4>FileLocation: ${Location[i]}</h4>
				<h4>--------------------------------------------------</h4>
			
			</#list>
		
		</#if>
		
		<a href='welcome'>Home</a>
		
	</body>
</html>
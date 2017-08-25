package com.springnew;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.sun.j3d.utils.scenegraph.io.state.javax.media.j3d.GroupState;
import com.sun.jmx.snmp.UserAcl;
import com.google.cloud.storage.Acl.Group;
import com.google.cloud.storage.Acl.Project;
import com.google.cloud.storage.Acl.Project.ProjectRole;
import com.google.cloud.storage.Acl.User;
import com.google.auth.oauth2.UserCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;


@Controller
public class DownloadController {
	
	
		
		//final String[][] contentTypes = {{"xml","text/xml"},{"pdf","application/pdf"},{"doc","text/doc"}};
	    
		@RequestMapping("/Download1")
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{   
			Storage storage = StorageOptions.getDefaultInstance().getService();
			String role = request.getParameter("role");
			String fileName = request.getParameter("file");
			String bucketName= "vendor-bucket15";
			File file = new File("/Users/tkmajdt/Documents/workspace/File1POC1/" + fileName);
			
			
			BlobId blobId = BlobId.of(bucketName, fileName);
			Blob blob = storage.get(blobId);
			System.out.println(blob.getMetadata()); //to get the metadata
			
			
			//System.out.println(storage.createAcl());
			Acl acl = null;
			Acl acl1 = null;
			if(role.equals("Admin"))
		    	{
			    	acl = storage.getAcl(blobId, User.ofAllAuthenticatedUsers());
				    if(acl!=null)
				    {
				    	//download the file
				    	ReadChannel readChannel = blob.reader();
				    	FileOutputStream fileOuputStream = new FileOutputStream(file);
				    	System.out.println(file);
				    	fileOuputStream.getChannel().transferFrom(readChannel, 0, Long.MAX_VALUE);
				    	fileOuputStream.close();
				    	PrintWriter out = response.getWriter();
						response.setContentType("text/html");
						out.println("<html>");
						out.println("<body>");
						out.println("<br/>");
						out.println("<h2> Your file is downloaded successfully in /Users/tkmajdt/Documents/workspace/File1POC1/ </h2>");
						out.println("</body>");
						out.println("</html>");
				    }
				    else
				    {
				    	PrintWriter out = response.getWriter();
						response.setContentType("text/html");
						out.println("<html>");
						out.println("<body>");
						out.println("<br/>");
						out.println("<h2> You are not allowed to view this file </h2>");
						out.println("</body>");
						out.println("</html>");
				    	
				    }
		    	}
			
			else if(role.equals("Staff"))
	    	{
		    	acl = storage.getAcl(blobId, User.ofAllUsers());
		    	
			    if(acl!=null)
			    {
			    	//download the file
			    	ReadChannel readChannel = blob.reader();
			    	FileOutputStream fileOuputStream = new FileOutputStream(file);
			    	System.out.println(file);
			    	fileOuputStream.getChannel().transferFrom(readChannel, 0, Long.MAX_VALUE);
			    	fileOuputStream.close();
			    	PrintWriter out = response.getWriter();
					response.setContentType("text/html");
					out.println("<html>");
					out.println("<body>");
					out.println("<br/>");
					out.println("<h2> Your file is downloaded successfully in /Users/tkmajdt/Documents/workspace/File1POC1/</h2>");
					out.println("</body>");
					out.println("</html>");
			    }
			    else
			    {
			    	PrintWriter out = response.getWriter();
					response.setContentType("text/html");
					out.println("<html>");
					out.println("<body>");
					out.println("<br/>");
					out.println("<h2> You are not allowed to view this file </h2>");
					out.println("</body>");
					out.println("</html>");
			    }
	    	}
			else
			{
				acl = storage.getAcl(blobId, User.ofAllUsers());
				//acl=storage.getAcl(blobId, User.class);
				
				acl1 = storage.getAcl(blobId, User.ofAllAuthenticatedUsers());
				if(acl!=null && acl1!=null)
					{	
					ReadChannel readChannel = blob.reader();
			    	FileOutputStream fileOuputStream = new FileOutputStream(file);
			    	System.out.println(file);
			    	fileOuputStream.getChannel().transferFrom(readChannel, 0, Long.MAX_VALUE);
			    	fileOuputStream.close();
			    	PrintWriter out = response.getWriter();
					response.setContentType("text/html");
					out.println("<html>");
					out.println("<body>");
					out.println("<br/>");
					out.println("<h2> Your file is downloaded successfully in /Users/tkmajdt/Documents/workspace/File1POC1/</h2>");
					out.println("</body>");
					out.println("</html>");
					}
				else
					{
					PrintWriter out = response.getWriter();
					response.setContentType("text/html");
					out.println("<html>");
					out.println("<body>");
					out.println("<br/>");
					out.println("<h2> You are not allowed to view this file </h2>");
					out.println("</body>");
					out.println("</html>");
					}
			}
		System.out.println(acl);
		
	    
	}
}
		
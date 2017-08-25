package com.springnew;

import java.io.BufferedInputStream;
//import com.database.DBConnection; 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
public class DownloadNew {
	
		@RequestMapping("/Download")
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{   
			Storage storage = StorageOptions.getDefaultInstance().getService();
			String role = request.getParameter("permission");
			String vendorName = request.getParameter("vendorname");
			String fileName = request.getParameter("file");
			String userid = request.getParameter("userid");
			String bucketName= "vendor-bucket17";
			File file = new File("/Users/tkmajdt/Documents/workspace/File1POC1/" + fileName);
			boolean permission_grant = false;
			
			//BlobId blobId = BlobId.of(bucketName, vendorName+"/"+fileName);
			//Blob blob = storage.get(blobId);
			//System.out.println(blob.getMetadata()); //to get the metadata
			
			
			//System.out.println(storage.createAcl());
			Acl acl = null;
			Acl acl1 = null;
			try
			{
				 Class.forName("org.sqlite.JDBC");
		    	 Connection connection = null;
				 connection = DriverManager.getConnection("jdbc:sqlite:/Users/tkmajdt/Desktop/vendor.db");
				 
				 //for any other db, so can check whether the resultset entry is true or false
				 //PreparedStatement ps = connection.prepareStatement("Select * from vendorfile1 where filename = ?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE );
				 PreparedStatement ps = connection.prepareStatement("Select * from vendorfile1 where filename = ? and vendorid = ?");
				 ps.setString(1, fileName);
				 ps.setString(2, vendorName);
				 ResultSet rs =   ps.executeQuery();
				 if(!rs.isBeforeFirst())
				 {
					 PrintWriter out = response.getWriter();
						response.setContentType("text/html");
						out.println("<html>");
						out.println("<body>");
						out.println("<br/>");
						out.println("<h2> This File does not exist </h2>");
						out.println("</body>");
						out.println("</html>");
				 }
				
				 else
				 {
				 PreparedStatement ps_roles = connection.prepareStatement("Select * from roles where fileid = ?" );
				 ps_roles.setInt(1,Integer.parseInt(rs.getString("fileid")));
				 System.out.println("fileid "+rs.getString("fileid"));
				 ResultSet rs_roles = ps_roles.executeQuery();
				 PreparedStatement ps_userid = connection.prepareStatement("Select * from userid where fileid = ?" );
				 ps_userid.setInt(1,Integer.parseInt(rs.getString("fileid")));
				 ResultSet rs_userid = ps_userid.executeQuery();
				 
				 //BlobId blobId = BlobId.of(bucketName, vendorName+"/"+fileName);
				 BlobId blobId = BlobId.of(bucketName, rs.getString("fileLocation")+fileName);
				 Blob blob = storage.get(blobId);
					
				 
				  
				if (rs.next())
				 {
					
					 if((vendorName.equals(rs.getString("vendorid"))) || (vendorName==rs.getString("vendorid")))
					 {
						 
						 System.out.println("This is the database vendor  "+rs.getString("vendorid"));
						 
						 while(rs_roles.next())
						 {
							 System.out.println("first "+rs_roles.getString("role_access"));
							 System.out.println("This is selected "+role);
							 if(rs_roles.getString("role_access").equals(role))
							 {
								 permission_grant=true;
							 }
						 }	
						 while(rs_userid.next())
						 {
							 System.out.println("first "+rs_userid.getString("user_access"));
							 System.out.println("This is selected "+userid);
							 if(rs_userid.getString("user_access").equals(userid))
							 {
								 permission_grant=true;
							 }
						 }
						 
						 if(permission_grant)
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
				 }
				
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	    
	}
}

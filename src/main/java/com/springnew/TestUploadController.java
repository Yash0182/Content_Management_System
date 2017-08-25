package com.springnew;

import com.google.cloud.storage.Acl;

import com.google.cloud.storage.Blob;

import com.google.cloud.storage.BlobInfo;

import com.google.cloud.storage.Storage;

import com.google.cloud.storage.StorageOptions;



import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;



import javax.servlet.ServletException;

import javax.servlet.annotation.MultipartConfig;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;





@Controller

public class TestUploadController {
	@RequestMapping("/uploadtest")
	public ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ModelAndView mv = new ModelAndView();
		List<FileItem> fileName = null;
	    Storage storage = StorageOptions.getDefaultInstance().getService();
	    String bucketName = "vendor-bucket17";  
	    
	    ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
	    List<Acl> acls = new ArrayList();
	    Hashtable incoming = new Hashtable();
	    boolean fileCheck = true;
	   
	     
	    try 
			{
	    	 Class.forName("org.sqlite.JDBC");
	    	 
	    	 Connection connection = null;
			 connection = DriverManager.getConnection("jdbc:sqlite:/Users/tkmajdt/Desktop/vendor.db"); 
			 fileName = sfu.parseRequest(request);			//receiving fileName as fileItem from the form
			 
			 Iterator iter = fileName.iterator();
			 List<String> completeSet=new ArrayList<String>();
			 Iterator iter_users = fileName.iterator();
			 List<String> completeSet_users=new ArrayList<String>();
			 
			      
			 while (iter.hasNext()) 				//for getting multiple values of roles
			 {
			        FileItem item = (FileItem) iter.next();

			        if (item.isFormField()) 
			        {
			           String name = item.getFieldName();
			           String value = item.getString();

			        if (name.equals("permission"))
			            completeSet.add(value);
			        }
			 }
			 
			 while (iter_users.hasNext()) 				//for getting multiple values of userids
			 {
			        FileItem item_users = (FileItem) iter_users.next();

			        if (item_users.isFormField()) 
			        {
			           String name = item_users.getFieldName();
			           String value = item_users.getString();

			        if (name.equals("userid"))
			            completeSet_users.add(value);
			        }
			 }
			 
			 for(FileItem f:fileName)
			 		{
				 	incoming.put(f.getFieldName(), f.getString()); //as it is a multipart form request, so need to get param field using this
			 		}
			 //System.out.println("complete set " +completeSet);
			 for(FileItem f:fileName)
			 		{
				 		String owner = (String)incoming.get("uploader");
				 		String vendorid = (String)incoming.get("vendorid");
			 			String filename_database = (String)f.getName();
			 			String userid_database=(String)incoming.get("userid");
			 			String directory = "";
			 			String fileLocation=vendorid+"/";
			 			directory=(String)incoming.get("directoryName");
			 			System.out.println(directory);
			 			if(!directory.equals(""))
			 			{
			 				directory=directory+"/";
			 				fileLocation=fileLocation+directory;
			 				System.out.println("Inside"+fileLocation);
			 			}
			 			System.out.println(directory);
			 			System.out.println(filename_database);
			 			
			 		
				 		
				 		//for checking the file if already uploaded by the vendor
				 		System.out.println(vendorid);
				 		PreparedStatement ps_checkFile = connection.prepareStatement("select filename from vendorfile1 where vendorid=?");
						ps_checkFile.setString(1,vendorid);
				 		ResultSet rs_checkFile = ps_checkFile.executeQuery();
				 		while(rs_checkFile.next())
				 		{
				 			
				 			if(filename_database.equals(rs_checkFile.getString("filename")))
				 			{
				 				
				 				//to update the existing entry of the file 
				 				PreparedStatement ps_vendor = connection.prepareStatement("select fileid from vendorfile1 where filename=? and vendorid=?");
				 				
					        	ps_vendor.setString(1, filename_database);
					        	ps_vendor.setString(2,vendorid);
						        ResultSet rs_fileid = ps_vendor.executeQuery();
						        
						        int file_id=0;
						        
						        if(rs_fileid.next())
						        	{
						        		file_id = rs_fileid.getInt("fileid");
						        	}
						        
						        //deleting the previous entry of the file from the table
						        
						        PreparedStatement ps_updatevendor = connection.prepareStatement("delete from vendorfile1 where fileid=?");
						        ps_updatevendor.setInt(1,file_id);
						        ps_updatevendor.executeUpdate();
						        
						        PreparedStatement ps_updateuser = connection.prepareStatement("delete from userid where fileid=?");
						        ps_updateuser.setInt(1,file_id);
						        ps_updateuser.executeUpdate();
						        
						        PreparedStatement ps_updateroles = connection.prepareStatement("delete from roles where fileid=?");
						        ps_updateroles.setInt(1,file_id);
						        ps_updateroles.executeUpdate();
						        
						        //inserting updated value in the table
						        
						        PreparedStatement ps_vendorfile1 = connection.prepareStatement("Insert into vendorfile1 Values (?,?,?,?,?)");
					        	ps_vendorfile1.setInt(1,file_id);
					        	ps_vendorfile1.setString(2, filename_database);
					        	ps_vendorfile1.setString(3, owner);
					        	ps_vendorfile1.setString(4, vendorid);
					        	ps_vendorfile1.setString(5, fileLocation);
					        	ps_vendorfile1.executeUpdate();
					        
					        	if(completeSet.contains("All"))
					        	{
					        		
					        		PreparedStatement ps_roles_names = connection.prepareStatement("Select role_name from roles_names");
					        		ResultSet rs_role_names = ps_roles_names.executeQuery();
					        		while(rs_role_names.next())
					        		{
						        		PreparedStatement ps_roles = connection.prepareStatement("Insert into roles Values (?,?)");
							        	ps_roles.setInt(1, file_id);
							        	ps_roles.setString(2, rs_role_names.getString("role_name"));
							        	ps_roles.executeUpdate();
					        		}
					        	}
					        	else
					        	{
					        		
							        int i=0;
							        while(i<completeSet.size())
							        {
								        PreparedStatement ps_roles = connection.prepareStatement("Insert into roles Values (?,?)");
							        	ps_roles.setInt(1, file_id);
							        	ps_roles.setString(2, completeSet.get(i));
							        	ps_roles.executeUpdate();
								        i++;
							        }
					        	}
					        	
						        int j=0;
						        while(j<completeSet_users.size())
						        {
						        PreparedStatement ps_userid = connection.prepareStatement("Insert into userid Values (?,?)");
					       		ps_userid.setInt(1, file_id);
					        	ps_userid.setString(2, completeSet_users.get(j));
					        	ps_userid.executeUpdate();
						        j++;
						        }
						        
				 				fileCheck = false;
				 				break;
				 			}
				 		}
			 			
				 		//if file does not already exists then insert
				        if(fileCheck)
				        {
					        PreparedStatement ps_vendorfile1 = connection.prepareStatement("Insert into vendorfile1 (filename,uploader,vendorid,fileLocation) Values (?,?,?,?)");
					        ps_vendorfile1.setString(1, filename_database);
					        ps_vendorfile1.setString(2, owner);
					        ps_vendorfile1.setString(3, vendorid);
					        ps_vendorfile1.setString(4, fileLocation);
					        ps_vendorfile1.executeUpdate();
					        
					        PreparedStatement ps_vendor = connection.prepareStatement("select fileid from vendorfile1 where filename=? and vendorid=?");
					        ps_vendor.setString(1, filename_database);
					        ps_vendor.setString(2, vendorid);
					        ResultSet rs_fileid = ps_vendor.executeQuery();
					        int file_id=0;
					        if(rs_fileid.next())
					        	{
					        		file_id = rs_fileid.getInt("fileid");
					        	}
					        
					        if(completeSet.contains("All"))
				        	{
				        		
				        		PreparedStatement ps_roles_names = connection.prepareStatement("Select role_name from roles_names");
				        		ResultSet rs_role_names = ps_roles_names.executeQuery();
				        		while(rs_role_names.next())
				        		{
					        		PreparedStatement ps_roles = connection.prepareStatement("Insert into roles Values (?,?)");
						        	ps_roles.setInt(1, file_id);
						        	ps_roles.setString(2, rs_role_names.getString("role_name"));
						        	ps_roles.executeUpdate();
				        		}
				        	}
				        	else
				        	{
					        
						        int i=0;
						        
						        while(i<completeSet.size())
						        {
							        PreparedStatement ps_roles = connection.prepareStatement("Insert into roles Values (?,?)");
							        ps_roles.setInt(1, file_id);
							        ps_roles.setString(2, completeSet.get(i));
							        ps_roles.executeUpdate();
							        i++;
						        }
				        	}
					        int j=0;
					        while(j<completeSet_users.size())
					        {
					        PreparedStatement ps_userid = connection.prepareStatement("Insert into userid Values (?,?)");
					        
					        ps_userid.setInt(1, file_id);
					        ps_userid.setString(2, completeSet_users.get(j));
					        ps_userid.executeUpdate();
					        j++;
					        }
					        ps_vendor.close();
					        ps_vendorfile1.close();
					        rs_fileid.close();
					        connection.close();
				        }
				        
				 		
			 		Blob blob =		
			 				
			 						storage.create(
			 						
					                BlobInfo.newBuilder(bucketName, vendorid+"/"+directory+f.getName()).setAcl(acls).build(),
			
					                f.getInputStream());

			 		break;
			 		}
			 
			 
			    mv.setViewName("success");
			} 
	    	catch (Exception e) 
			{
	    		e.printStackTrace();
			}
		
	    return mv;
	    
	}

}


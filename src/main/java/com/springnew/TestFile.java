package com.springnew;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.storage.model.StorageObject;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Project;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.StorageScopes;
import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.StorageObject;

@Controller
public class TestFile
{
	@RequestMapping("/upload1")
		public ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			ModelAndView mv = new ModelAndView();
			List<FileItem> fileName = null;
		    Storage storage = StorageOptions.getDefaultInstance().getService();
		    String bucketName = "vendor-bucket17";  
		    
		    ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
		    List<Acl> acls = new ArrayList();
		    Hashtable incoming = new Hashtable();
			
		    try 
				{
				 fileName = sfu.parseRequest(request);
				 System.out.println(fileName);
				 for(FileItem f:fileName)
				 		{
					 	incoming.put(f.getFieldName(), f.getString()); //as it is a multipart form request, so need to get param field using this
				 		}
				 for(FileItem f:fileName)
				 		{
					 		String role= (String)incoming.get("permission"); //as it is a multipart form request, so need to get using this
					 		
				 			if(role.equals("Admin"))
				 				{
				 				acls.add(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.OWNER));
				 				//acls.add(ProjectRole.VIEWERS,Acl.Role.READER);
				 				}
				 			else if(role.equals("Others"))
					 			{
					 			acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.OWNER));
					 			}
				 			else
				 				{
				 				acls.add(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.OWNER));
				 				acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.OWNER));
				 				}
				 			
				 		Blob blob =
		
				            	storage.create(
		
				                BlobInfo.newBuilder(bucketName, f.getName()).setAcl(acls).build(),
		
				                f.getInputStream());
				    
				 		}
				 
				    mv.setViewName("success.jsp");
				} 
		    catch (FileUploadException e) 
				{
				e.printStackTrace();
				}
			
		    return mv;
		    
		}
	
}




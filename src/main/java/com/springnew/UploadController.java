package com.springnew;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;

@Controller
public class UploadController {

		@RequestMapping("/upload")
		public ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String targetFileStr ="";
			List<FileItem> fileName = null;
		    Storage storage = StorageOptions.getDefaultInstance().getService();

		    String bucketName = "vendor-bucket15";  // "my-new-bucket";

		    // Creates the new bucket
		    Bucket bucket = storage.create(BucketInfo.of(bucketName));
		 System.out.println(request.getParameter("testtext"));
		    ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
			try {
				 fileName = sfu.parseRequest(request);
				 for(FileItem f:fileName)
					{
					 System.out.println(f);
					try {
						f.write (new File("/Users/tkmajdt/Documents/workspace/File1POC1/" + f.getName()));
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					
					targetFileStr = new String(Files.readAllBytes(Paths.get("/Users/tkmajdt/Documents/workspace/File1POC1/" + f.getName())));
					System.out.println("target" +targetFileStr);
					}
			} 
		
			
			catch (FileUploadException e) {
				
				e.printStackTrace();
			}
			
			
			
			
		    
		    BlobId blobId = BlobId.of(bucketName, "yashtest");
		    
		    Blob blob = bucket.create("yashtest", targetFileStr.getBytes("UTF-8"), "text/plain");
		    
		  /* 
		    Blob blob1 = storage.get(blobId);
		    System.out.println("The name of the bucket is " + blob1.getName());
		    if (blob1 != null) {
		      byte[] prevContent = blob.getContent();
		      System.out.println(new String(prevContent, "UTF-8"));
		      WritableByteChannel channel = blob1.writer();
		      channel.write(ByteBuffer.wrap("Updated content".getBytes("UTF-8")));
		      channel.close();
		    
		    }
		    
		    */
		    
		    ModelAndView mv = new ModelAndView();
		    mv.setViewName("success.jsp");
		    return mv;
		    
		}

		

		
	}




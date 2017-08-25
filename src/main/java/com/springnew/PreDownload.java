package com.springnew;

import com.google.cloud.storage.Blob;

import com.google.cloud.storage.BlobInfo;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobListOption;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.api.gax.paging.Page ;

@Controller
public class PreDownload {

	@RequestMapping(value = "/PreDownload", method = RequestMethod.POST)
	protected ModelAndView doGet(@RequestParam ("vendorName") String vendorName) throws ServletException, IOException 
	{
		
		ModelAndView mv = new ModelAndView("down");
		List fileNames = new ArrayList();
		try
		{
			
			
			Storage storage = StorageOptions.getDefaultInstance().getService(); 
			//Page<Blob> blobs = storage.list("vendor-bucket17");   //For listing all files
			
			Page<Blob> blobs1 = storage.list("vendor-bucket17",BlobListOption.prefix(vendorName+"/"));
			
			
			for (Blob blob1 : blobs1.iterateAll()) 
			{
			
				Object object = blob1.getName();
				fileNames.add(object);
			
		 	}
			
			
			//for listing all files
			
			/*for (Blob blob1 : blobs.iterateAll()) 
					{
					Object object = blob1.getName();
					fileNames.add(object);
					
				 	}*/
			 
		}
		
		catch(Exception e)
		{
			e.getMessage();
		}
		
		mv.addObject("fileNames",fileNames);
		mv.addObject("fileNamesSize", fileNames.size());
		return mv;
	}
	
	
	
}


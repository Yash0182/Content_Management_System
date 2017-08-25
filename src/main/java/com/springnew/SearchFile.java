	package com.springnew;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.util.ArrayList;
	import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

	@Controller
	public class SearchFile {
		
		@RequestMapping(value="/Search1", method = RequestMethod.POST)
		
		public ModelAndView searchFile(@RequestParam ("file") String file, @RequestParam ("vendorName") String vendorName)
		{
			ModelAndView mv = new ModelAndView();
			
			try 
			{
	    	 Class.forName("org.sqlite.JDBC");
	    	 
	    	 Connection connection = null;
			 connection = DriverManager.getConnection("jdbc:sqlite:/Users/tkmajdt/Desktop/vendor.db"); 
			 PreparedStatement ps_search = connection.prepareStatement("Select * from vendorfile1 where filename like ? AND vendorid=?");
			 System.out.println("Inside Search");
			 ps_search.setString(1, "%" + file + "%");
			 ps_search.setString(2, vendorName);
			 ResultSet rs_search = ps_search.executeQuery();
			 
			 if(rs_search.next())
			 {
				 mv.setViewName("SearchFile");
				 mv.addObject("message", "Below is the search result matching the file Name: ");
				 mv.addObject("found","foundFile");
				 
				 
				 
				 List l_fileName = new ArrayList();   //for multiple files
				 List l_uploader = new ArrayList();
				 List l_fileLocation = new ArrayList();
				 
				 do
				 {
					l_fileName.add(rs_search.getObject("filename"));
					l_uploader.add(rs_search.getObject("uploader"));
					l_fileLocation.add(rs_search.getObject("fileLocation"));
					 
				 }	 while(rs_search.next());
					 
				 System.out.println(l_fileName);
					 
				// mv.addObject("fileName", rs_search.getObject("filename"));
				 
				 
				 mv.addObject("fileName", l_fileName);
				 mv.addObject("uploader", l_uploader);
				 mv.addObject("Location", l_fileLocation);
				 mv.addObject("listSize",l_fileName.size());
				 
				 //System.out.println(rs_search.getObject("filename"));
				 
				 
				// mv.addObject("uploader", rs_search.getObject("uploader"));
				 //mv.addObject("Location", rs_search.getObject("fileLocation"));
				 
				 System.out.println(l_uploader);
				 System.out.println(l_fileName);
			 
			 }
			 else
			 {
				 mv.setViewName("SearchFile");
				 mv.addObject("message", "No Matching File Found");
				 mv.addObject("found","notFound");
			 }
			 
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return mv;
			
		}

	}




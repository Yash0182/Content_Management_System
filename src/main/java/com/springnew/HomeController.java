package com.springnew;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {
	
		@RequestMapping(value = "/Home", method = RequestMethod.GET)
		protected ModelAndView doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			
			
			List users = new ArrayList();
			
			try
			{
				
				Class.forName("org.sqlite.JDBC");
				//System.out.println("Yash Bansal2");
		    	 Connection connection = null;
				 connection = DriverManager.getConnection("jdbc:sqlite:/Users/tkmajdt/Desktop/vendor.db");
				 PreparedStatement ps = connection.prepareStatement("Select DISTINCT user_access from users" );
				 
				 ResultSet rs =   ps.executeQuery();
				 
				 //ResultSet rs =   ps.executeQuery();
				 //System.out.println("Yash Bansal");
				 
				 while(rs.next())
				 {
					 	users.add(rs.getString("user_access")) ;
					 	
					 	
				 } 
			}
			catch(Exception e)
			{
				e.getMessage();
			}
			
			
			/*PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<html>");
			out.println("<body>");
			
			out.println("<h3>Select the action you wanted to perform</h3>");
			out.println("<br/>");
			
			
			
			
			out.println("</body>");
			out.println("</html>");*/
			//System.out.println("Inside Home Controller");
			ModelAndView mv = new ModelAndView("testftl");
			mv.addObject("users",users);
			
			//System.out.println(users);
			return mv;
			
		}

	}



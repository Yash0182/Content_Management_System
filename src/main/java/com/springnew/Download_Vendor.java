package com.springnew;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Download_Vendor {
	
	@RequestMapping(value="/Download_vendor", method = RequestMethod.GET)
	public ModelAndView enterVendorID()
	{
		ModelAndView mv = new ModelAndView("entervendor");	
		return mv;
	}

}

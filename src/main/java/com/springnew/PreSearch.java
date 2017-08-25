package com.springnew;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PreSearch {
	
	@RequestMapping(value = "/PreSearch", method = RequestMethod.GET)
	public ModelAndView doPost()
	{
		ModelAndView mv = new ModelAndView("search");	
		return mv;
	}
}

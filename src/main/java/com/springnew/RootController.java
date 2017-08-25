package com.springnew;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RootController {
	
	@RequestMapping(value={"/","welcome"}, method=RequestMethod.GET)
	public ModelAndView doGet()
	{
		ModelAndView mv = new ModelAndView("index");
		
		return mv;
	}
}

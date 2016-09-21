package com.vm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/home")
public class HomeController {
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	/**
	 * this method return page
	 * */
	@RequestMapping(value="/info")
	public String Home(){
		logger.debug("home is calling");
		return "home";
	}
	
	/**
	 * this method returns message
	 * */
	@RequestMapping(value="/info2")
	public @ResponseBody String info(){
		logger.debug("home is calling");
		return "home";
	}

}

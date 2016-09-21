/**
 * 
 */
package com.vm.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vm.dao.IStudentDao;
import com.vm.domain.Student;
import com.vm.service.IStudentService;

/**
 * @author Vamsi
 *
 */



@Controller
@RequestMapping(value="/student")
public class StudentController {
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	IStudentService studentService;
	
	@RequestMapping(value="/info")
	public String student(){
		logger.debug("student is calling");
		return "student";
	}
	
	@RequestMapping(value="/list")
	public @ResponseBody List<Student> list(){
		List<Student> list=new ArrayList<Student>();
		try{
			list.addAll(studentService.findAll());
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
}

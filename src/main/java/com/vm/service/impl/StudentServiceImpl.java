/**
 * 
 */
package com.vm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vm.dao.IStudentDao;
import com.vm.domain.Student;
import com.vm.service.IStudentService;

/**
 * @author Vamsi
 *
 */

@Service
public class StudentServiceImpl implements IStudentService{

	@Autowired
	IStudentDao istudentDao;
	
	@Override
	public void save(Student student) {
		istudentDao.save(student);
	}

	 
	@Override
	public void update(Student student) {
		istudentDao.update(student);
	}
 
	@Override
	public void delete(Student student) {
		istudentDao.delete(student);
	}


 
	@Override
	public List<Student> findAll() {
		return istudentDao.findAll();
	}

}

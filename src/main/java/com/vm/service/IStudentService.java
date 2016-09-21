/**
 * 
 */
package com.vm.service;

import java.util.List;

import com.vm.domain.Student;

/**
 * @author Vamsi
 *
 */
public interface IStudentService {

	public void save(Student student);
	public void update(Student student);
	public void delete(Student student);
	public List<Student> findAll();
	
}

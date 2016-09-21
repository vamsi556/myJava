/**
 * 
 */
package com.vm.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vm.dao.IStudentDao;
import com.vm.domain.Student;

/**
 * @author Vamsi
 *
 */

@Repository
@Transactional
public class StudentDaoImpl extends BaseDaoImpl<Student> implements IStudentDao{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void save(Student student) {
		this.sessionFactory.getCurrentSession().save(student);
	}

	 
	@Override
	public void update(Student student) {
		this.sessionFactory.getCurrentSession().update(student);
	}

	 
	@Override
	public void delete(Student student) {
		this.sessionFactory.getCurrentSession().delete(student);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Student> findAll() {
		Session session=this.sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(Student.class);
		return criteria.list();
	}

}

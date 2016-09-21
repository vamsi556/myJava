package com.vm.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.vm.dao.IBaseDao;

public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	private Class<T> entityClass;
	SessionFactory sessionFactory;
	
	@Autowired
	public void setSession(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
		this.setSessionFactory(sessionFactory);
	}

	public BaseDaoImpl() {
		entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public List<T> loadAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}

	public void delete(T domain) {
		getHibernateTemplate().delete(domain);
	}

	public void save(T domain) {
		getHibernateTemplate().saveOrUpdate(domain);

	}

	public void update(T domain) {
		getHibernateTemplate().merge(domain);
	}

	public T get(Serializable id) {
		T o = (T) getHibernateTemplate().get(entityClass, id);
		return o;
	}

	public List<T> getListByCriteria(DetachedCriteria detachedCriteria, int offset, int size) {
		return (List<T>) getHibernateTemplate().findByCriteria(detachedCriteria, offset, size);
	}

	public List<T> getListByCriteria(DetachedCriteria detachedCriteria) {
		return (List<T>) getHibernateTemplate().findByCriteria(detachedCriteria);
	}
}
package com.enviance.rest.spring.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.enviance.rest.util.FilterWrapper;

/**
 * 
 * @author Francisco Franco
 *
 */

public interface BaseDao {
	public List getAll();
	
	public Object getByPrimaryKey(final Serializable id);
	
	public Object findByPrimaryKey(final Serializable id);
	
	public Object findByPrimaryKeyWithFilter(final Serializable id, FilterWrapper filterParams);
	
	public void update(Object object);
	
	public Serializable save(final Object object);
	
	public void saveOrUpdate(Object object);
	
	public void merge(Object object);
	
	public void delete(Object object);
	
	public List findByPropertyValue(String propertyName, Object propertyValue);
	
	public List findByPropertyValue(String propertyName, Object propertyValue, String orderProperty, Boolean isAscending);
	
	public List findPartialMatchByPropertyValue(String propertyName, Object partialPropertyValue);
	
	//public Collection callStoredProcedure(String procName, Object[] params);
	
	public List findByPropertyValueCaseInsensitive(String propertyName, Object propertyValue);
	
	public List findByPropertyValueWithoutDeletedRows(final String propertyName, final Object propertyValue);

}

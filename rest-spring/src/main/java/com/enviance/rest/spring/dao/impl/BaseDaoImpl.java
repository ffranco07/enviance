package com.enviance.rest.spring.dao.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Disjunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.enviance.rest.util.DomainConstants;
import com.enviance.rest.util.FilterWrapper;

public abstract class BaseDaoImpl extends HibernateDaoSupport {
	private static Log log = LogFactory.getLog(BaseDaoImpl.class);
	
	// ================================
	// Protected methods
	// ================================
	
	protected abstract Class getReferenceClass();

	// ================================
	// Public methods
	// ================================
	
	public List getAll() {
		return getHibernateTemplate().loadAll(getReferenceClass());
	}
	
	public Iterator iterateAll() {
		return getAll().iterator();
	}
	
	public Object getByPrimaryKey(final Serializable id) {
		return getHibernateTemplate().get(getReferenceClass(), id);
	}
	
	public Object findByPrimaryKey(final Serializable id) {
		Object obj = null;
		try {
			obj = getHibernateTemplate().load(getReferenceClass(), id);
		} 
		catch (Exception e) {
			obj = null;
		}
		return obj; 
	}
	
	public Object findByPrimaryKeyWithFilter(final Serializable id, FilterWrapper filterParams) {
		Object obj = null;
		try {
			HibernateTemplate ht = getHibernateTemplate();
			// set the filter name
			Filter filter = ht.enableFilter(filterParams.getFilterName());
			// add passed filter parameters
			Map params = filterParams.getFilterParameterMap();
			Iterator it = params.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next().toString();
				filter.setParameter(key, params.get(key));
			}
			// load the object with the filter
			obj = ht.load(getReferenceClass(), id);
		} 
		catch (Exception e) {
			obj = null;
    	}
		return obj; 
	}
	
	public boolean exists(Integer id) {
		return getHibernateTemplate().get(getReferenceClass(), id) == null ? true : false;
	}
	
	public void update(Object object) {
		getHibernateTemplate().update(object);
	}
	
	public Serializable save(final Object object) {
		return getHibernateTemplate().save(object);
	}
	
	public void saveOrUpdate(Object object) {
		getHibernateTemplate().saveOrUpdate(object);
	}
	
	public void merge(Object object) {
		getHibernateTemplate().merge(object);
	}
	
	public void delete(Object object) {
		getHibernateTemplate().delete(object); 
	}
	
	public List findByPropertyValue(String propertyName, Object propertyValue) {
		List list = null;
		try {
			list = findByPropertyValue(propertyName, propertyValue, null, null);
		} 
		catch (Exception e) {
			list = null;
		}
		return list; 
	}
	
	public List findByPropertyValue(final String propertyName, final Object propertyValue, final String orderProperty, final Boolean isAscending) {
		List list = null;
		try {
			list = (List)getHibernateTemplate().execute(new HibernateCallback() {
			    public Object doInHibernate(Session session)
						throws HibernateException {
						Criteria criteria = session.createCriteria(getReferenceClass());
						criteria.add(Restrictions.eq(propertyName, propertyValue));
						if (null != orderProperty) {
							if (isAscending)
								criteria.addOrder(Order.asc(orderProperty));
							else 
								criteria.addOrder(Order.desc(orderProperty));
						}
						return criteria.list();
			    }
				});
		} 
		catch (Exception e) {
			list = null;
		}
		return list; 	
	}
	
	public List findByPropertyValueCaseInsensitive(String propertyName, Object propertyValue) {
		List list = null;
		try {
			list = findByPropertyValueCaseInsensitive(propertyName, propertyValue, null);
		} 
		catch (Exception e) {
			list = null;
		}
		return list; 
	}
	
	public List findByPropertyValueWithoutDeletedRows(final String propertyName, final Object propertyValue) {
		List list = null;
		try {
			list = (List)getHibernateTemplate().execute(new HibernateCallback() {
			    public Object doInHibernate(Session session) throws HibernateException {
						Criteria criteria = session.createCriteria(getReferenceClass());
						criteria.add(Restrictions.eq(propertyName, propertyValue));
						criteria.add(Restrictions.eq("deleteFlag", DomainConstants.FLAG_NO));
						return criteria.list();
			    }
				});
		} 
		catch (Exception e) {
			list = null;
		}
		return list; 	
	}

	public List findByPropertyValueORList(final String propertyName, final List propertyValueList) {
		List list = null;
		try {
			list = (List)getHibernateTemplate().execute(new HibernateCallback() {
			    public Object doInHibernate(Session session) throws HibernateException {
						Criteria criteria = session.createCriteria(getReferenceClass());
						Disjunction or = Restrictions.disjunction();
						for (Object propertyValue : propertyValueList) {
							log.debug("findByPropertyValueORList ... propertyName is: " + propertyName);
							log.debug("findByPropertyValueORList ... propertyValue is: " + propertyValue);
							or.add(Restrictions.eq(propertyName, propertyValue));
						}
						criteria.add(or);
						return criteria.list();
			    }
				});
		} 
		catch (Exception e) {
			list = null;
		}
		return list; 	
	}

	public List findByPropertyValueANDMap(final Map<String, Object> propertyValueMap, final String orderProperty) {
		List list = null;
		try {
			list = (List)getHibernateTemplate().execute(new HibernateCallback() {
			    public Object doInHibernate(Session session) throws HibernateException {
						Criteria criteria = session.createCriteria(getReferenceClass());
						Iterator it = propertyValueMap.keySet().iterator();
						while (it.hasNext()) {
							String propertyName = (String)it.next();
							log.debug("findByPropertyValueMap ... propertyName is: " + propertyName);
							log.debug("findByPropertyValueMap ... propertyValue is: " + propertyValueMap.get(propertyName));
							criteria.add(Restrictions.eq(propertyName, propertyValueMap.get(propertyName)));
							if (orderProperty != null) {
								criteria.addOrder(Order.asc(orderProperty));
							}
						}
						return criteria.list();
			    }
				});
		} 
		catch (Exception e) {
			list = null;
		}
		return list; 	
	}
	
	public List findByPropertyValueCaseInsensitive(final String propertyName, final Object propertyValue, final String orderProperty) {
		List list = null;
		try {
			list = (List)getHibernateTemplate().execute(new HibernateCallback() {
			    public Object doInHibernate(Session session) throws HibernateException {
						Criteria criteria = session.createCriteria(getReferenceClass());
						criteria.add(Restrictions.ilike(propertyName, propertyValue));
						if (null != orderProperty) {
							criteria.addOrder(Order.asc(orderProperty));
						}
						return criteria.list();
			    }
				});
		} 
		catch (Exception e) {
			list = null;
		}
		return list; 	
	}
	
	public List findPartialMatchByPropertyValue(final String propertyName, final Object propertyValue) {
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException {
		    	Criteria criteria = session.createCriteria(getReferenceClass());
					criteria.add(Restrictions.ilike(propertyName, propertyValue.toString(), MatchMode.ANYWHERE));
					return criteria.list();
		    }
			});
	}

	public List findPartialMatchByPropertyValue(final String propertyName, final Object propertyValue, final String orderProperty) {
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException {
		    	Criteria criteria = session.createCriteria(getReferenceClass());
					criteria.add(Restrictions.ilike(propertyName, propertyValue.toString(), MatchMode.START));
					if (null != orderProperty) {
						criteria.addOrder(Order.asc(orderProperty));
					}
					return criteria.list();
		    }
			});
	}

	public List findPartialMatchByPropertyValueANDMap(final Map<String, Object> propertyValueMap, final String likeProperty, final String orderProperty) {
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException {
		    	Criteria criteria = session.createCriteria(getReferenceClass());
					Iterator it = propertyValueMap.keySet().iterator();
						while (it.hasNext()) {
							String propertyName = (String)it.next();
							log.debug("findPartialMatchByPropertyValueANDMap ... propertyName is: " + propertyName);
							log.debug("findPartialMatchByPropertyValueANDMap ... propertyValue is: " + propertyValueMap.get(propertyName));
							if (propertyName.equals(likeProperty)) {
								criteria.add(Restrictions.ilike(propertyName, propertyValueMap.get(propertyName).toString(), MatchMode.START));
							}
							else {
								criteria.add(Restrictions.eq(propertyName, propertyValueMap.get(propertyName)));
							}
						}
						if (null != orderProperty) {
							criteria.addOrder(Order.asc(orderProperty));
						}
						return criteria.list();
		    }
			});
	}
	
	public Collection callStoredProcedure(String procName, Object[] inParams) {
		return callStoredProcedure(procName, inParams, 0);
	}
	
	public Collection callStoredProcedure(final String procName, final Object[] inParams, final int numOutParams) {
		return (Collection)getHibernateTemplate().execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException {
		    	try {
			    	CallableStatement call = session.connection().prepareCall(procName);
						
						int startIndex=1;
						for(int index = 1;index <= numOutParams;index++)
							call.registerOutParameter(startIndex++, Types.INTEGER );
						int cntr = 0;
						for(int index = startIndex;index < inParams.length+startIndex;index++)
							call.setObject(index, inParams[cntr++]);
						call.executeQuery();
						ArrayList list = new ArrayList();
						for(int i = 1;i <= numOutParams;i++)
							list.add(call.getObject(i));
						return list;
		    	} 
					catch(SQLException e) {
						e.printStackTrace();
						throw getHibernateTemplate().getJdbcExceptionTranslator().translate("Failed to call "+procName, null, e);
					}
		    }
			});
	}
	
	public Collection callStoredProcedure(final String procName,final Object[] inParams,final int numOutParams, final int returnType) {
		return (Collection)getHibernateTemplate().execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException {
		    	try {
			    	CallableStatement call = session.connection().prepareCall(procName);
						int startIndex=1;
						for(int index = 1;index <= numOutParams;index++)
							call.registerOutParameter(startIndex++, returnType );
						int cntr = 0;
						for(int index = startIndex;index < inParams.length+startIndex;index++)
							call.setObject(index, inParams[cntr++]);
						call.execute();
						ArrayList list = new ArrayList();
						for(int i = 1;i <= numOutParams;i++)
							list.add(call.getObject(i));
						log.debug("calling call.close() to close CallableStatement");
						call.close();
						return list;
		    	} catch(SQLException e) {
						e.printStackTrace();
						throw getHibernateTemplate().getJdbcExceptionTranslator().translate("Failed to call "+procName, null, e);
					}
		    }
				
			});
	}
    
	//@Autowired @Qualifier("SessionFactory")
	@Autowired
	public void sessionFactoryTemplate (SessionFactory sessionFactory) {
	setSessionFactory(sessionFactory);
	}
}

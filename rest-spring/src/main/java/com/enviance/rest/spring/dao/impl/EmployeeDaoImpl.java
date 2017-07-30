package com.enviance.rest.spring.dao.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.sql.Timestamp;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

//import org.hibernate.type.*;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.enviance.framework.exception.DataStoreException;
import com.enviance.rest.spring.dao.EmployeeDao;
import com.enviance.rest.spring.model.Employee;
import com.enviance.rest.util.DomainConstants;

import com.enviance.dto.EmployeeDTO;
import com.enviance.dto.QueryDTO;
import com.enviance.log.Log;

/**
 * 
 * @author Francisco Franco
 *
 */

@Repository("employeeDao")
public class EmployeeDaoImpl extends BaseDaoImpl implements EmployeeDao, Serializable {

	public Collection<Employee> findEmployees(final String queryStr) throws DataStoreException {
		// DEBUG
		System.out.println("QUERYSTR IS: " + queryStr);
		Log.messageLogger("QUERYSTR IS: " + queryStr);
	 		try {    
				return (Collection<Employee>)getHibernateTemplate().execute(new HibernateCallback() {
						public Object doInHibernate(Session session) throws HibernateException {
							//Query query = session.createSQLQuery(queryStr).addScalar("empNo", new IntegerType()).addScalar("birthDate", new DateType()).addScalar("firstName", new StringType()).addScalar("lastName", new StringType()).addScalar("gender", new StringType()).addScalar("hireDate", new DateType());
							//List<Employee> list = query.setResultTransformer(Transformers.aliasToBean(Employee.class)).list();
							Query query = session.createSQLQuery(queryStr).addEntity(Employee.class);
							List<Employee[]> list = query.list();
							return list;
						}
					});
			}
			catch (Exception e) {
				logger.error(e.toString());
				throw new DataStoreException(DomainConstants.DATA_RETRIEVAL_FAILURE);
			}
	}

	@Override
	protected Class getReferenceClass() {
		return Employee.class;
	}
}

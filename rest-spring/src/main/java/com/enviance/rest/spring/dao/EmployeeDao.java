package com.enviance.rest.spring.dao;

import java.util.Collection;

import com.enviance.framework.exception.DataStoreException;
import com.enviance.rest.spring.model.Employee;

import com.enviance.dto.EmployeeDTO;
import com.enviance.dto.QueryDTO;

/**
 * 
 * @author Francisco Franco
 *
 */

public interface EmployeeDao extends BaseDao {
	public Collection<Employee> findEmployees(String queryStr) throws DataStoreException;
}

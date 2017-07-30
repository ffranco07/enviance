package com.enviance.rest.spring.services;

import java.util.Collection;

import com.enviance.framework.exception.DataStoreException;
import com.enviance.dto.EmployeeDTO;
import com.enviance.dto.QueryDTO;

/**
 * 
 * @author Francisco Franco
 *
 */

public interface EmployeeService {
	public Collection<EmployeeDTO> findEmployees(QueryDTO queryDTO) throws DataStoreException;
}

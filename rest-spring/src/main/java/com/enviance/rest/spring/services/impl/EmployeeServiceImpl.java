package com.enviance.rest.spring.services.impl;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enviance.framework.exception.DataStoreException;
import com.enviance.rest.spring.services.EmployeeService;
import com.enviance.rest.spring.dao.EmployeeDao;
import com.enviance.rest.spring.model.Employee;
import com.enviance.rest.util.DomainConstants;

import com.enviance.dto.DTOFactory;
import com.enviance.dto.DTOConstants;
import com.enviance.dto.QueryDTO;
import com.enviance.dto.EmployeeDTO;
import com.enviance.log.Log;

/**
 * 
 * @author Francisco Franco
 *
 */

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	EmployeeDao employeeDao;

	// ==========================
	// Private methods
	// ==========================

	// Create dynamic database query based on request query param inputs
	private String createQueryString(QueryDTO queryDTO) {
		String queryStr = null;
		// Initalize string builder to create query string
		StringBuilder sb = new StringBuilder();
		// Initialize entry set iterator
		Iterator<Map.Entry<Integer, String>> entries = queryDTO.getQueryMap().entrySet().iterator();
		Map.Entry<Integer, String> entry;
		char[] charArray;
		while (entries.hasNext()) {
			entry = entries.next();
			// DEBUG
			System.out.println("KEY = " + entry.getKey() + ", VALUE = " + entry.getValue());
			Log.messageLogger("KEY = " + entry.getKey() + ", VALUE = " + entry.getValue());
			if (entry.getValue() != null) {
				// Append dynamic query statement based on entry key available
				switch(entry.getKey()) {
				   case DomainConstants.FIRST_NAME:
						 if (sb.length() > 0) 
							 sb.append(" AND " );
						 sb.append("MATCH(first_name) AGAINST (" + "'" + entry.getValue() + "' IN BOOLEAN MODE)");
						 break;
				   case DomainConstants.LAST_NAME:
						 if (sb.length() > 0) 
							 sb.append(" AND " );
						 sb.append("MATCH(last_name) AGAINST (" + "'" + entry.getValue() + "' IN BOOLEAN MODE)");
						 break;
				   case DomainConstants.GENDER:
						 if (sb.length() > 0) 
							 sb.append(" AND " );
						 sb.append("gender='" + entry.getValue() + "'");
						 break;
				   case DomainConstants.NUMBER:
						 if (sb.length() > 0) 
							 sb.append(" AND " );
						 charArray = entry.getValue().toCharArray();
						 if (charArray[0] == '>') {
							 sb.append("emp_no > '" + entry.getValue().substring(1) + "'");
						 }
						 else if (charArray[0] == '<') {
							 sb.append("emp_no < '" + entry.getValue().substring(1) + "'");
						 }
						 else {
							 // No special character in front so no substring
							 sb.append("emp_no = '" + entry.getValue() + "'");
						 }
						 break;
				   case DomainConstants.BIRTH_DATE:
						 if (sb.length() > 0) 
							 sb.append(" AND " );
						 charArray = entry.getValue().toCharArray();
						 if (charArray[0] == '>') {
							 sb.append("birth_date > '" + entry.getValue().substring(1) + "'");
						 }
						 else if (charArray[0] == '<') {
							 sb.append("birth_date < '" + entry.getValue().substring(1) + "'");
						 }
						 else {
							 // No special character in front so no substring
							 sb.append("birth_date = '" + entry.getValue() + "'");
						 }
						 break;
				   case DomainConstants.HIRE_DATE:
						 if (sb.length() > 0) 
							 sb.append(" AND " );
						 charArray = entry.getValue().toCharArray();
						 if (charArray[0] == '>') {
							 sb.append("hire_date > '" + entry.getValue().substring(1) + "'");
						 }
						 else if (charArray[0] == '<') {
							 sb.append("hire_date < '" + entry.getValue().substring(1) + "'");
						 }
						 else {
							 // No special character in front so no substring
							 sb.append("hire_date = '" + entry.getValue() + "'");
						 }
						 break;
				   case DomainConstants.ORDER_BY:
						 if (sb.length() > 0) {
							 sb.append(" ORDER BY " + entry.getValue());
						 }
						 break;
				   case DomainConstants.LIMIT:
						 if (sb.length() > 0) {
							 sb.append(" LIMIT " + entry.getValue());
						 }
						 break;
				   default:
						 break;
				}
			}	
		}
		if (sb.length() > 0) {
			queryStr = "SELECT * FROM employees WHERE " + sb.toString();
		}
		// Slower query
		//String queryStr = "SELECT * FROM employees WHERE " + columnName + " LIKE '%" + searchExp + "%'";
		// Faster query
		//String queryStr = "SELECT * FROM employees WHERE MATCH(" + columnName + ") AGAINST (" + "'" + searchExp + "' IN BOOLEAN MODE) ORDER BY " + columnName;
		return queryStr;
	}
	
	// ==========================
	// Public methods
	// ==========================
	
	// Business method which returns collection of EmployeeDTO
	@Override
	public Collection<EmployeeDTO> findEmployees(QueryDTO queryDTO) throws DataStoreException {
		Collection<EmployeeDTO> employeeDTOCol = null;
		EmployeeDTO employeeDTO = null;
		Employee employee = null;
		long startTime = 0;
		long endTime = 0;
		try {
			// Create dynamic query string
			String queryStr = createQueryString(queryDTO);
			// DEBUG
			System.out.println(queryStr);
			Log.messageLogger(queryStr);
			// Set start time for invocation of query
			startTime = System.currentTimeMillis();
			// Execute native query and return employee entity bean collection
			Collection<Employee> employeeCol = employeeDao.findEmployees(queryStr);
			// Set end time since query is done 
			endTime = System.currentTimeMillis();
			// DEBUG
			System.out.println("EMPLOYEE COLLECTION SIZE IS: " + employeeCol.size());
			Log.messageLogger("EMPLOYEE COLLECTION SIZE IS: " + employeeCol.size());
			// Check if employeeCol is null or empty.  If so, then throw exception
			if (employeeCol == null || employeeCol.isEmpty()) {
				throw new EntityNotFoundException("Employee collection not found in database!");
			}
			// Initialize employeeDTOCol collection
			employeeDTOCol = new ArrayList<EmployeeDTO>();
			// Get iterator from employeeCol
			Iterator itr = employeeCol.iterator();
			// Use while loop to invoke iterator
			while (itr.hasNext()) {
				employee = (Employee)itr.next();
				employeeDTO = (EmployeeDTO)DTOFactory.getDTO(DTOConstants.EMPLOYEE);
				employeeDTO.setNumber(employee.getEmpNo());
				employeeDTO.setFirstName(employee.getFirstName());
				employeeDTO.setLastName(employee.getLastName());
				employeeDTO.setGender(employee.getGender());
				employeeDTO.setBirthDate(employee.getBirthDate());
				employeeDTO.setHireDate(employee.getHireDate());
				employeeDTOCol.add(employeeDTO);
			}
		}
		catch (Exception e) {
			// DEBUG
			System.out.println("EmployeeServiceImpl :: EXCEPTION :: " + e.getMessage());
			throw new DataStoreException(e);
		}
		finally {
			String executeTimeMsg = "QUERY EXECUTE TIME: " + (endTime - startTime) + " MS";
			// DEBUG
			System.out.println(executeTimeMsg);
			Log.messageLogger(executeTimeMsg);
		}
		return employeeDTOCol;
	}
}

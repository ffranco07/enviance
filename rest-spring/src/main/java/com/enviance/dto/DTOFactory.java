package com.enviance.dto;

import java.util.Map;
import java.util.HashMap;

import com.enviance.framework.dto.AbstractDTO;
import com.enviance.framework.exception.DTOTypeNotFoundException;
import com.enviance.rest.util.DomainConstants;

/**
 * This class is a Data Transfer Object (DTO)
 * Factory class which creates DTO objects
 *
 * @author Francisco Franco
 */

public final class DTOFactory {

	// ====================================
	// Constructor
	// ====================================
	
	private DTOFactory() {}
	
	// ====================================
	// Public methods
	// ====================================
	
	// Create AbstractDTO
	public static AbstractDTO getDTO(int dtoType) throws DTOTypeNotFoundException {
		AbstractDTO dto = null;
		switch (dtoType) {
		   case DTOConstants.QUERY:
				 dto = new QueryDTO();
				 dto.setId(DTOConstants.QUERY);
				 break;
		   case DTOConstants.EMPLOYEE:
				 dto = new EmployeeDTO();
				 dto.setId(DTOConstants.EMPLOYEE);
				 break;
		   case DTOConstants.ERROR:
				 dto = new ErrorDTO();
				 dto.setId(DTOConstants.ERROR);
				 break;
		   default:
				 throw new DTOTypeNotFoundException(dtoType);
		}
		return dto;
	}

	// Create QueryDTO
	public static QueryDTO createQueryDTO(String number, String firstName, String lastName, String gender, String birthDate, String hireDate, String orderBy, String limit) throws DTOTypeNotFoundException {
		QueryDTO queryDTO = (QueryDTO)DTOFactory.getDTO(DTOConstants.QUERY);
		Map queryMap = new HashMap<Integer, String>();
		queryMap.put(DomainConstants.NUMBER, number);
		queryMap.put(DomainConstants.FIRST_NAME, firstName);
		queryMap.put(DomainConstants.LAST_NAME, lastName);
		queryMap.put(DomainConstants.GENDER, gender);
		queryMap.put(DomainConstants.BIRTH_DATE, birthDate);
		queryMap.put(DomainConstants.HIRE_DATE, hireDate);
		queryMap.put(DomainConstants.ORDER_BY, orderBy);
		queryMap.put(DomainConstants.LIMIT, limit);
		queryDTO.setQueryMap(queryMap);
		return queryDTO;
	}
	
	// Create ErrorDTO
	public static ErrorDTO createErrorDTO(int status, String message, String description) throws DTOTypeNotFoundException {
		ErrorDTO errorDTO = (ErrorDTO)getDTO(DTOConstants.ERROR);
		errorDTO.setStatus(status);
		errorDTO.setMessage(message);
		errorDTO.setDescription(description);
		return errorDTO;
	}
}

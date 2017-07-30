package com.enviance.rest.validation;

import java.util.Map;

import com.enviance.framework.dto.AbstractDTO;
import com.enviance.framework.exception.ValidationException;
import com.enviance.framework.validation.AbstractValidator;

import com.enviance.rest.util.DomainConstants;
import com.enviance.dto.QueryDTO;
import com.enviance.log.Log;

/**
 * This class is used to validate query
 * DTO request input
 *
 * @author Francisco Franco
 */

public class QueryValidator extends AbstractValidator {
	private static final int NUMBER = DomainConstants.NUMBER;
	private static final int FIRST_NAME = DomainConstants.FIRST_NAME;
	private static final int LAST_NAME = DomainConstants.LAST_NAME;
	private static final int GENDER = DomainConstants.GENDER;
	private static final int BIRTH_DATE = DomainConstants.BIRTH_DATE;
	private static final int HIRE_DATE = DomainConstants.HIRE_DATE;
	private static final int LIMIT = DomainConstants.LIMIT;
	
	// ==========================
	// Constructor
	// ==========================
	
	/**
	 * Creates a new instance of QueryValidator
	 */
	public QueryValidator() {}
	
	// ================================
	// Public methods
	// ================================
	
	@Override
	public void validateDTO(AbstractDTO dto) throws ValidationException {
		String errorText = null;
		QueryDTO queryDTO = (QueryDTO)dto;
		boolean isNull = true;
		Map<Integer, String> queryMap = queryDTO.getQueryMap();
		for (String value : queryMap.values()) {
			if (value != null) {
				isNull = false;
				break;
			}
		}
		if (isNull) {
			errorText = "INVALID QUERY, ALL INPUTS NULL";
			Log.messageLogger(errorText);
			throw new ValidationException(errorText);
		}
		if (queryMap.get(NUMBER) != null && !queryMap.get(NUMBER).matches("\\>?\\<?([0-9]+)"))
			errorText = "INVALID NUMBER FORMAT";
		else if (queryMap.get(FIRST_NAME) != null && !queryMap.get(FIRST_NAME).matches("([a-zA-Z]+\\*?)"))
			errorText = "INVALID FIRST NAME FORMAT";
		else if (queryMap.get(LAST_NAME) != null && !queryMap.get(LAST_NAME).matches("([a-zA-Z]+\\*?)"))
			errorText = "INVALID LAST NAME FORMAT";
		else if (queryMap.get(GENDER) != null && !queryMap.get(GENDER).matches("[mMfF]"))
			errorText = "INVALID GENDER FORMAT, USE M or F case insensitive";
		else if (queryMap.get(BIRTH_DATE) != null && !queryMap.get(BIRTH_DATE).matches("\\>?\\<?([0-9]{4})-([0-9]{2})-([0-9]{2})"))
			errorText = "INVALID BIRTH DATE FORMAT, USE YYYY-MM-DD";
		else if (queryMap.get(HIRE_DATE) != null && !queryMap.get(HIRE_DATE).matches("\\>?\\<?([0-9]{4})-([0-9]{2})-([0-9]{2})"))
			errorText = "INVALID HIRE DATE FORMAT, USE YYYY-MM-DD";
		else if (queryMap.get(LIMIT) != null && !queryMap.get(LIMIT).matches("([0-9]+)"))
			errorText = "INVALID LIMIT FORMAT";
		
		if (errorText != null) {
			Log.messageLogger(errorText);
			throw new ValidationException(errorText);
		}
	}
}

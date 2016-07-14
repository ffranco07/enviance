package com.enviance.framework.validation;

import com.enviance.framework.dto.AbstractDTO;
import com.enviance.framework.exception.ValidationException;

/**
 * This class is an abstract super class for DTO validators
 *
 * @author Francisco Franco
 */

public abstract class AbstractValidator {
	
	// ====================================
	// Constructor
	// ====================================
	
	public AbstractValidator() {}

	// ====================================
	// Public methods
	// ====================================
	
	public abstract void validateDTO(AbstractDTO dto) throws ValidationException;
	
}

package com.enviance.framework.exception;

/**
 * This class is used to set DTO exceptions
 *
 * @author Francisco Franco
 */

public final class DTOTypeNotFoundException extends BaseException {
    
	// ===================================
	// Constructor
	// ===================================
	
	public DTOTypeNotFoundException(int dtoType) {
		super(new Exception("DTO TYPE NOT FOUND: " + dtoType));
	}
}

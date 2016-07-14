package com.enviance.framework.exception;

/**
 * This class is used to set valiation  exceptions
 * 
 * @author Francisco Franco
 */

public final class ValidationException extends BaseException {
    
	// ===================================
	// Constructors
	// ===================================
	
	public ValidationException(Exception e) {
		super(e);
	}

	public ValidationException(String msg) {
		super(new Exception(msg));
	}
}

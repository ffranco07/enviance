package com.enviance.framework.exception;

import javax.persistence.PersistenceException;

/**
 * This class is used to set database / datastore exceptions
 *
 * @author Francisco Franco
 */

public final class DataStoreException extends BaseException {
	public boolean _isRollback = false;
    
	// ===================================
	// Constructors
	// ===================================
	
	public DataStoreException(Exception e) {
		super(e);
		if (e instanceof PersistenceException) {
			_isRollback = true;
		}
	}
	
	public DataStoreException(String msg) {
		super(new Exception(msg));
	}

	public boolean getIsRollback() {
		return _isRollback;
	}
}

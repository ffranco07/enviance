package com.enviance.dto;

import java.util.Map;

import com.enviance.framework.dto.AbstractDTO;

/**
 * This class is a serializable query DTO 
 * class used to store query data
 *
 * @author Francisco Franco
 */

public class QueryDTO extends AbstractDTO {
	// The query parameter map
	private Map<Integer, String> _queryMap;

	// ===================================
	// Constructor
	// ===================================

	/** Creates a new instance of QueryDTO */
	public QueryDTO() {}
	
	// =====================================
	// Public methods
	// =====================================

	public Map<Integer, String> getQueryMap() {
		return _queryMap;
	}
	
	public void setQueryMap(Map<Integer, String> _queryMap) {
		this._queryMap = _queryMap;
	}
	
	@Override
	public String toString() {
		String objStr = "id is: " + getId();
		return objStr;
	}
}

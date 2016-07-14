package com.enviance.framework.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This class is an abstract super class for DTOs
 *
 * @author Francisco Franco
 */

public abstract class AbstractDTO implements Serializable {
	// The id
	private int _id = -9;
	
	// ====================================
	// Constructor
	// ====================================
	
	public AbstractDTO() {}

	// ====================================
	// Public methods
	// ====================================
 
	public abstract String toString();

	@XmlTransient
	public int getId() {
		return _id;
	}
	
	public void setId(int _id) {
		this._id = _id;
	}
}

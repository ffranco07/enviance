package com.enviance.dto;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.enviance.framework.dto.AbstractDTO;
import com.enviance.framework.util.DateAdapter;

//import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * This class is a serializable error DTO 
 * class used to store error data and
 * can be marshalled / unmarshalled via JAXB provider
 *
 * @author Francisco Franco
 */

@XmlRootElement(name = "error")
@XmlType (propOrder={"status", "message", "description"})
//@JsonRootName("error")
public class ErrorDTO extends AbstractDTO {
	
	// The http status code
	private int _status = -9;
	
	// The error message
	private String _message = null;

	// The error message description
	private String _description = null;
 
	// ===================================
	// Constructor
	// ===================================

	/** Creates a new instance of ErrorDTO */
	public ErrorDTO() {}
	
	// =====================================
	// Public methods
	// =====================================
	
	public int getStatus() {
		return _status;
	}
	
	public void setStatus(int _status) {
		this._status = _status;
	}

	public String getMessage() {
		return _message;
	}
	
	public void setMessage(String _message) {
		this._message = _message;
	}

	public String getDescription() {
		return _description;
	}
	
	public void setDescription(String _description) {
		this._description = _description;
	}

	@Override
	public String toString() {
		String objStr = "id is: " + getId() + "\n" + "status is: " + _status + "\n" + "message is: " + _message + "\n" + "description is: " + _description;
		return objStr;
	}
}

package com.enviance.dto;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.enviance.framework.dto.AbstractDTO;
import com.enviance.framework.util.DateAdapter;

//import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * This class is a serializable employee DTO 
 * class used to store employee data and
 * can be marshalled / unmarshalled via JAXB provider
 *
 * @author Francisco Franco
 */

@XmlRootElement(name = "employee")
@XmlType (propOrder={"number", "firstName", "lastName", "gender", "birthDate", "hireDate"})
//@JsonRootName("employee")
public class EmployeeDTO extends AbstractDTO {

	// The employee number
	private int _number = -9;

	// The first name
	private String _firstName = null;
	
	// The last name
	private String _lastName = null;
	
	// The gender
	private String _gender = null;
	
	// The birth date
	private Date _birthDate = null;

	// The hire date
	private Date _hireDate = null;

	// ===================================
	// Constructor
	// ===================================

	/** Creates a new instance of EmployeeDTO */
	public EmployeeDTO() {}
	
	// =====================================
	// Public methods
	// =====================================

	public int getNumber() {
		return _number;
	}
	
	public void setNumber(int _number) {
		this._number = _number;
	}
	
	public String getFirstName() {
		return _firstName;
	}
	
	public void setFirstName(String _firstName) {
		this._firstName = _firstName;
	}

	public String getLastName() {
		return _lastName;
	}
	
	public void setLastName(String _lastName) {
		this._lastName = _lastName;
	}

	public String getGender() {
		return _gender;
	}
	
	public void setGender(String _gender) {
		this._gender = _gender;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getBirthDate() {
		return _birthDate;
	}
	
	public void setBirthDate(Date _birthDate) {
		this._birthDate = _birthDate;
	}
	
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getHireDate() {
		return _hireDate;
	}
	
	public void setHireDate(Date _hireDate) {
		this._hireDate = _hireDate;
	}
	
	@Override
	public String toString() {
		String objStr = "id is: " + getId() + "\n" + "number is: " + _number + "\n" + "firstName is: " + _firstName + "\n" + "lastName is: " + _lastName + "\n" + "gender is: " + _gender + "\n" + "birthDate is: " + _birthDate + "\n" + "hireDate is: " + _hireDate;
		return objStr;
	}
}

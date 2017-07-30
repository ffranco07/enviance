/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enviance.rest.spring;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.GET;
//import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import com.enviance.framework.exception.ValidationException;
import com.enviance.framework.exception.DataStoreException;
import com.enviance.framework.exception.DTOTypeNotFoundException;

import com.enviance.rest.spring.context.SpringApplicationContext;
import com.enviance.rest.spring.services.EmployeeService;
import com.enviance.rest.validation.QueryValidator;
import com.enviance.rest.util.DomainConstants;

import com.enviance.dto.DTOFactory;
import com.enviance.dto.DTOConstants;
import com.enviance.dto.EmployeeDTO;
import com.enviance.dto.QueryDTO;
import com.enviance.dto.ErrorDTO;
import com.enviance.log.Log;

/**
 * This is class exposes a method as a 
 * Spring RESTful web service endpoint
 *
 * @author Francisco Franco
 */

@Controller
@Path("/employees")
public class EmployeeWebService {
	
	private EmployeeService employeeService;

	// ==========================
	// Constructor
	// ==========================
	
	/**
	 * Creates a new instance of EmployeeWebService
	 */
	public EmployeeWebService() {}

	// ==========================
	// Private methods
	// ==========================

	// Validate QueryDTO inputs
	private void validateDTO(QueryDTO queryDTO) throws ValidationException {
		QueryValidator queryValidator = new QueryValidator();
		queryValidator.validateDTO(queryDTO);
	}

	// Wrapper class used to include xml root element name in json response
	private GenericEntity<Collection<EmployeeDTO>> createGenericEmployeeDTOCol(Collection<EmployeeDTO> employeeDTOCol) {
		GenericEntity<Collection<EmployeeDTO>> entity = new GenericEntity<Collection<EmployeeDTO>>(employeeDTOCol) {};
		return entity;
	}

	// Wrapper class used to include xml root element name in json response
	private GenericEntity<ErrorDTO> createGenericErrorDTO(ErrorDTO errorDTO) {
		GenericEntity<ErrorDTO> entity = new GenericEntity<ErrorDTO>(errorDTO) {};
		return entity;
	}

	// Create ErrorDTO with status, message, and description parameters
	private ErrorDTO createErrorDTO(int status, String message, String description) {
		ErrorDTO errorDTO = null;
		try {
			errorDTO = DTOFactory.createErrorDTO(status, message, description);
		}
		catch (DTOTypeNotFoundException dnfe) {
			Log.exceptionLogger(dnfe.toString(), this.getClass().getName(), "createErrorDTO(int, String, String)");
		}
		return errorDTO;
	}
	
	// ==========================
	// Public methods
	// ==========================
	
	@GET
	// Uncomment below to append to Path
	//@Path("/query")
	//@Produces("application/xml")
	@Produces(MediaType.APPLICATION_JSON)
	// Restful web service endpoint which returns rest resopnse as JSON formatted data
	// Parameters here can be String, primitive, or custom class with static valueOf() method
	public Response getEmployees(@QueryParam("number") 
															 String number, 
															 @QueryParam("firstName") 
															 String firstName, 
															 @QueryParam("lastName") 
															 String lastName, 
															 @QueryParam("gender") 
															 String gender, 
															 @QueryParam("birthDate") 
															 String birthDate, 
															 @QueryParam("hireDate") 
															 String hireDate,
															 @QueryParam("orderBy") 
															 String orderBy,
															 @QueryParam("limit") 
															 String limit) {
		Response rsp = null;
		Collection<EmployeeDTO> employeeDTOCol = null;
		QueryDTO queryDTO = null;
		ErrorDTO errorDTO = null;
		System.out.println("HTTP GET :: " + number + " :: " + firstName + " :: " + lastName + " :: " + gender + " :: " + birthDate + " :: " + hireDate);
		Log.messageLogger("HTTP GET :: " + number + " :: " + firstName + " :: " + lastName + " :: " + gender + " :: " + birthDate + " :: " + hireDate);
		try {
			// Create QueryDTO with QueryParam inputs
			queryDTO = DTOFactory.createQueryDTO(number, firstName, lastName, gender, birthDate, hireDate, orderBy, limit);
			// Validate QueryDTO 
			validateDTO(queryDTO);
			employeeService = (EmployeeService)SpringApplicationContext.getBean("employeeService");
			// DEBUG
			System.out.println("employeeService is: " + employeeService);
			Log.messageLogger("employeeService is: " + employeeService);
			// Use Spring service layer to find employee DTO collection
			employeeDTOCol = employeeService.findEmployees(queryDTO);
			// Success return HTTP Status 200
			rsp = Response.status(DomainConstants.OK_CODE).entity(createGenericEmployeeDTOCol(employeeDTOCol)).build();
		}
		catch (ValidationException ve) {
			// DEBUG
			System.out.println("EmployeeWebService :: VALIDATION EXCEPTION :: " + ve.getMessage());
			Log.exceptionLogger(ve.toString(), this.getClass().getName(), "getEmployees() - 1");
			// Exception encountered so return HTTP Status 400
			errorDTO = createErrorDTO(DomainConstants.BAD_REQUEST_CODE, DomainConstants.BAD_REQUEST, ve.getMessage());
			rsp = Response.status(errorDTO.getStatus()).entity(createGenericErrorDTO(errorDTO)).build();
		}
		catch (DataStoreException dse) {
			// DEBUG
			System.out.println("EmployeeWebService :: DATA STORE EXCEPTION :: " + dse.getMessage());
			Log.exceptionLogger(dse.toString(), this.getClass().getName(), "getEmployees() - 2");
			// Exception encountered so return HTTP Status 500
			errorDTO = createErrorDTO(DomainConstants.INTERNAL_SERVER_ERROR_CODE, DomainConstants.INTERNAL_SERVER_ERROR, dse.getMessage());
			rsp = Response.status(errorDTO.getStatus()).entity(createGenericErrorDTO(errorDTO)).build();
		}
		catch (Exception e) {
			// DEBUG
			System.out.println("EmployeeWebService :: EXCEPTION :: " + e.getMessage());
			Log.exceptionLogger(e.toString(), this.getClass().getName(), "getEmployees() - 3");
			// Exception encountered so return HTTP Status 500
			errorDTO = createErrorDTO(DomainConstants.INTERNAL_SERVER_ERROR_CODE, DomainConstants.INTERNAL_SERVER_ERROR, e.getMessage());
			rsp = Response.status(errorDTO.getStatus()).entity(createGenericErrorDTO(errorDTO)).build();
			e.printStackTrace();
		}
		// DEBUG
		//System.out.println("EmployeeWebService :: Response :: " + rsp);
		return rsp;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}
	
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
}

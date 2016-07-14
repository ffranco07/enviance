package com.enviance.rest.ejb.session;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * This class extends the standard JAX-RS javax.ws.rs.core.Application 
 * class which lists all JAX-RS root resources and providers
 *
 * @author Francisco Franco
 */

@ApplicationPath("/")
public class RestApplication extends Application {

	// ==========================
	// Constructor
	// ==========================
	
	/**
	 * Creates a new instance of RestApplication
	 */
	public RestApplication() {}

}

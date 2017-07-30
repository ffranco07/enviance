package com.enviance.rest.util;

import java.text.SimpleDateFormat;

/**
 * This class is used to store domain model identifier constants
 *
 * @author Francisco Franco
 */

public final class DomainConstants {
	// Version id and date constants
	public static final String RELEASE_ID = "1.0.0";
	public static final String RELEASE_DATE = "17-06-2016"; // dd-MM-yyyy
	
	// Simple date formats
	public static final SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("dd-MMM-yyyy");

	// Query param constants
	public static final int FIRST_NAME = 1;
	public static final int LAST_NAME = 2;
	public static final int GENDER = 3;
	public static final int BIRTH_DATE = 4;
	public static final int HIRE_DATE = 5;
	public static final int NUMBER = 6;
	public static final int ORDER_BY = 7;
	public static final int LIMIT = 8;
	
	// HTTP error code constants
	public static final int OK_CODE = 200;
	public static final int BAD_REQUEST_CODE = 400;
	public static final int INTERNAL_SERVER_ERROR_CODE = 500;

	// Error text constants
	public static final String BAD_REQUEST = "BAD_REQUEST";
	public static final String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
	
	// Flags 
	public static final String FLAG_YES = "Y";
	public static final String FLAG_NO = "N";
	
	// Information message constants
	public static final String DATA_COMMIT_FAILURE = "DATA COMMIT FAILURE";
	public static final String DATA_RETRIEVAL_FAILURE = "DATA RETRIEVAL FAILURE";
}

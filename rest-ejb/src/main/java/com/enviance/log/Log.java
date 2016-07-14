package com.enviance.log;

import org.apache.log4j.Logger;

/**
 * This class is used to log server data via log4j
 *
 * @author Francisco Franco
 */

public final class Log {
	
	/** Private Constructor */
	private Log() {}
	
	public static void messageLogger(String message){
		Logger log=Logger.getLogger("com.enviance.logger.message");
		log.debug(message);
	}
	
	public static void exceptionLogger(String exception){
		Logger log = Logger.getLogger("com.enviance.logger.exception");
		log.debug(exception);
	}

	public static void exceptionLogger(String throwStr, String className, String methodName) {
		Logger log = Logger.getLogger("com.enviance.logger.exception");
		log.debug(throwStr + " OCCURRED IN " + className + " AT " + methodName);
	}
	
	public static void serviceLogger(String serviceLog){
		Logger log = Logger.getLogger("com.enviance.logger.service");
		log.debug(serviceLog);
	}
	
	public static void aopLogger(String aopLog) {
		Logger log=Logger.getLogger("com.enviance.logger.aop");
		log.debug(aopLog);
	} 
}

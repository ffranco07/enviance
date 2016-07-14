package com.enviance.framework.util;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * This class is used to format date pattern as xml
 *
 * @author Francisco Franco
 */

public final class DateAdapter extends XmlAdapter<String, Date> { 
	//public static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	// ===================================
	// Constructor
	// ===================================
	
	/** Creates a new instance of DateAdapter */
	public DateAdapter() {}

	// ===================================
	// Public methods
	// ===================================
	
  public Date unmarshal(String date) throws Exception {
    return DATE_FORMAT.parse(date);
  }

  public String marshal(Date date) throws Exception {
    return DATE_FORMAT.format(date);
  }
}

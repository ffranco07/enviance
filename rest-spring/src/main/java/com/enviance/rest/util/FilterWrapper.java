package com.enviance.rest.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class FilterWrapper {
	private String filterName;
	private Map filterParameters = new HashMap();
	private Properties properties;

	public FilterWrapper() {}

	// ====================
	// Public methods
	// ====================
	
	public String getFilterName() {
		return filterName;
	}
	
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	
	public void setFilterParameter(String param, String value) {
		filterParameters.put(param, value);
	}
	
	public void setFilterParameters(Properties mapping) {
		properties = mapping;
		Set keys = mapping.keySet();
		for(Iterator it = keys.iterator();it.hasNext();){
			String tmpKey = it.next().toString();
			filterParameters.put(tmpKey,mapping.get(tmpKey));
		}
	}
	
	public Map getFilterParameterMap() {
		return filterParameters;
	}
	
	public Properties getFilterParameters() {
		return properties;
	}
}

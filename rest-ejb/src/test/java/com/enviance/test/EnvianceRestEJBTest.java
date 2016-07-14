package com.enviance.test;

import com.thoughtworks.selenium.*;

public class EnvianceRestEJBTest extends SeleneseTestCase {
	
	@Override
	public void setUp() throws Exception {
		setUp("https://github.com/", "*chrome");
	}
	
	public void testEnviance_test() throws Exception {
		selenium.open("http://192.168.0.3:8080/rest-ejb/employees?firstName=F*&lastName=L*&orderBy=first_name&limit=500");
		assertTrue(selenium.getText("css=pre").matches("^[\\s\\S]*\"number\":435763[\\s\\S]*$"));
		selenium.open("http://192.168.0.3:8080/rest-ejb/employees?firstName=B*&lastName=L*&orderBy=first_name&limit=500");
		assertTrue(selenium.getText("css=pre").matches("^[\\s\\S]*\"firstName\":\"Bader\"[\\s\\S]*$"));
		selenium.open("http://192.168.0.3:8080/rest-ejb/employees?firstName=Tam*&lastName=L*&orderBy=first_name&limit=500");
		assertTrue(selenium.getText("css=pre").matches("^[\\s\\S]*M[\\s\\S]*$"));
		selenium.open("http://192.168.0.3:8080/rest-ejb/employees?firstName=Francisco&lastName=Franco");
		assertTrue(selenium.getText("css=pre").matches("^[\\s\\S]*Employee collection not found in database[\\s\\S]*$"));
	}
}

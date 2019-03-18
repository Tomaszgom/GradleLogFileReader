package com.logreader;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppMainTest {
	
	@Test
	public void testLoadAppProperties_PropertyAvailable() {
		AppMain.LoadAppProperties();
		assertTrue(AppMain.properties.getProperty("hsqldbJDBCDriver")!=null);
	}

}

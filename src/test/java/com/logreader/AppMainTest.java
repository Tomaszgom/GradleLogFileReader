package com.logreader;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppMainTest {
	
	@Test
	public void testLoadAppProperties_PropertyJDBCDriverAvailable() {
		AppMain.LoadAppProperties();
		assertTrue(AppMain.properties.getProperty("hsqldbJDBCDriver")!=null);
	}
	
	@Test
	public void testLoadAppProperties_PropertyConnectionStringAvailable() {
		AppMain.LoadAppProperties();
		assertTrue(AppMain.properties.getProperty("dbConnectionString")!=null);
	}

}

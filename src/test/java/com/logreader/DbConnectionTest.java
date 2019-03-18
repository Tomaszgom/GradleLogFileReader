package com.logreader;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

public class DbConnectionTest {

	static DbConnection dbConnection = new DbConnection();
	static Connection conn;
	static String testRecId = "testId0001";
	
	static HashMap<String, Event> testEventsMap = new HashMap<String, Event>();  	
	static Event event = new Event();
	static EventEntry eetrySt = new EventEntry();
	static EventEntry eetryFsh = new EventEntry();
	
	@BeforeClass
	public static void LoadAppProperties() {	
		AppMain.LoadAppProperties();
	}
		
	@BeforeClass
	public static void CreateExpectedEventsMap() {	
		eetrySt.setId(testRecId);
		eetryFsh.setId(testRecId);
		event.setId(testRecId);
		eetrySt.setState("STARTED");
		eetryFsh.setState("FINISHED");
		eetrySt.setTimestamp(1491377495212L);
		eetryFsh.setTimestamp(1491377495217L);
		eetrySt.setHost("12345");
		eetryFsh.setHost("12345");
		eetrySt.setType("APPLICATION_LOG");  
		eetryFsh.setType("APPLICATION_LOG");	
		event.setEventStart(eetrySt);
		event.setEventFinish(eetryFsh);   			
		testEventsMap.put(testRecId, event);
	}

	@Test(timeout=20000)
	public void testLoadJDBCDriver() {
		dbConnection.LoadJDBCDriver();
		assertEquals("testId0001,5,APPLICATION_LOG,12345,true",readFromDB());
	}
	
	@Test
	public void testInsertData() {				
		dbConnection.InsertData(testEventsMap);		
		assertEquals("testId0001,5,APPLICATION_LOG,12345,true",readFromDB());
	}
	

	public String readFromDB() {
		
		/* Method reads test record from event table to check side effects
 		 * Test DB instance for testing purposes has not been created/used to keep the project simple 
		 */
		
		dbConnection.LoadJDBCDriver();						
		try {		
			conn = DriverManager.getConnection(AppMain.properties.getProperty("dbConnectionString"), "SA", "");
    		    		
			PreparedStatement prepStmnt = conn.prepareStatement("select * from event where ID='" + testRecId+"'");
			prepStmnt.clearParameters();
	        ResultSet resSet = prepStmnt.executeQuery();
	        	 	        
	        String recStr;
	        while(resSet.next()){
	        	recStr = resSet.getString(1)+","+resSet.getLong(2)+","+resSet.getString(3)+","+ 
	        			resSet.getString(4)+","+resSet.getBoolean(5);
	        	return recStr;     	
	        }	        
	        
	        // Remove test record        
			prepStmnt = conn.prepareStatement("delete from event where ID='" + testRecId+"'");
			prepStmnt.clearParameters();
	        prepStmnt.executeUpdate();
	        
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

}

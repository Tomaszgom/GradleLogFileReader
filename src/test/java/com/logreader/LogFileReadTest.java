package com.logreader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.collection.IsMapContaining;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogFileReadTest {
		
	static LogFileRead fileRead = new LogFileRead();		
	static File file = new File("./src/test/resources/txt/logFile.txt");	
	static HashMap<String, Event> actualEventsMap = new HashMap<String, Event>();
	static HashMap<String, Event> expectedEventsMap = new HashMap<String, Event>();  	
	static Event event = new Event();
	static EventEntry eetrySt = new EventEntry();
	static EventEntry eetryFsh = new EventEntry();
	
	@BeforeClass
	public static void CreateExpectedEventsMap() {	
		actualEventsMap = fileRead.Read(file);
		eetrySt.setId("scsmbstgra");
		eetryFsh.setId("scsmbstgra");
		eetrySt.setState("STARTED");
		eetryFsh.setState("FINISHED");
		eetrySt.setTimestamp(1491377495212L);
		eetryFsh.setTimestamp(1491377495217L);
		eetrySt.setHost("12345");
		eetryFsh.setHost("12345");
		eetrySt.setType("APPLICATION_LOG");  
		eetryFsh.setType("APPLICATION_LOG");	
		event.setId("scsmbstgra");
		event.setEventStart(eetrySt);
		event.setEventFinish(eetryFsh);   	
		expectedEventsMap.put("scsmbstgra", event);
	}

	@Test
	public void testRead_EventMapSize() {	  		
   	    assertThat(expectedEventsMap.size(), is(1));  	 	      	 	       	    
	}
	
	
	@Test
	public void testRead_EventMapValuesEqual() {		
   	    for(Map.Entry<String, Event> expEvents : expectedEventsMap.entrySet()) {  	    	
   	    	assertEquals(expEvents.getValue().getId(), actualEventsMap.get(expEvents.getKey()).getId());
   	    	assertEquals(expEvents.getValue().getDuration(), actualEventsMap.get(expEvents.getKey()).getDuration());
   	    	assertEquals(expEvents.getValue().getType(), actualEventsMap.get(expEvents.getKey()).getType());
   	    	assertEquals(expEvents.getValue().getHost(), actualEventsMap.get(expEvents.getKey()).getHost());
   	    	assertEquals(expEvents.getValue().getAlert(), actualEventsMap.get(expEvents.getKey()).getAlert());
   	    } 	      	 	       	    
	}
	
	@Test
	public void testRead_EventMapHasEntry() {	  			 
   	    assertThat(expectedEventsMap, IsMapContaining.hasEntry("scsmbstgra", event));   	  	  	 
	}
	
	
}

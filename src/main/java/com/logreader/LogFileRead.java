package com.logreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class LogFileRead {
	
	private final static Logger logger = Logger.getLogger(LogFileRead.class.getName());
	
	public HashMap<String, Event> Read(File file) {
		
		logger.setLevel(Level.DEBUG);
		
		EventEntry eventEntry;
		ObjectMapper objectMapper = new ObjectMapper();
    	HashMap<String, Event> eventsMap = new HashMap<String, Event>();
    	
        try (Scanner scanner = 
        		new Scanner(
        				new BufferedReader(
        						new FileReader(file)))){ 
        	
			logger.info("Attempting to open the file.");
        	
			while(scanner.hasNextLine()) {
     			String line = scanner.nextLine();	

     			// Read JSON object, show message if format is incorrect   			
     			eventEntry = null;  
     			try {
     				 eventEntry = objectMapper.readValue(line, EventEntry.class);     								
     			}catch (JsonParseException e) { 
     				JOptionPane.showMessageDialog(null, AppMessages.WrongJSONrecordFormat.getText() + "\n" + e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);  				
     				logger.debug("Unexpected file format. Line could not be parsed to JSON object.");
     				e.printStackTrace();
     				System.exit(0);               
     			}
 								
    			if(eventsMap.containsKey(eventEntry.getId())){   				
    				
    				// If eventMap has entry (start or finish identified by id) then add entry to existing Event depending on the State   				
    				if(eventEntry.getState().equals("STARTED")) {
    					eventsMap.get(eventEntry.getId()).setEventStart(eventEntry);
    				}else{
    					eventsMap.get(eventEntry.getId()).setEventFinish(eventEntry); 					
    				}   
    				
    			}else{
    				
    				// If eventMap has no such entries (identified by id) and new Event with entry depending on the State   				
    				if(eventEntry.getState().equals("STARTED")) {
    					eventsMap.put(eventEntry.getId(), new Event(eventEntry.getId(), eventEntry, null));
    				}else{
    					eventsMap.put(eventEntry.getId(), new Event(eventEntry.getId(), null ,eventEntry));    					
    				}  			
    			}      			
        	 } 
        	         	 
        	 return eventsMap;
        	 
        }catch (IOException e) {
			logger.debug("Opening file attempt failed.");
            e.printStackTrace();
            return null;
        }
	}

}

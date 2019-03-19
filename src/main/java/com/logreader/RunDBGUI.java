package com.logreader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.hsqldb.util.DatabaseManagerSwing;

public class RunDBGUI {
	
	/* Runnable HSQL Database GUI */
	
	static Properties properties = new Properties();
	
	public static void main(String[] args) {

    		LoadAppProperties();
			System.out.println("Launching manager");
			DatabaseManagerSwing.main(new String[] { 
					"--url", properties.getProperty("dbConnectionString"), "--noexit"});
	}

	
    // Load Application properties   
    public static void LoadAppProperties(){    	
    	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    	try(InputStream inputProperties = classloader.getResourceAsStream("application.properties")){    		
    		properties.load(inputProperties);  		
    	}catch (IOException e) {
       		e.printStackTrace();
    	}  	
    }
	
}

package com.logreader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Tomasz Gomoradzki
 * Application is a tool which allows to read Server Events from log files in defined format, 
 * create table and insert data to HSQL Database, storing records in local file.
 *
 */

 
public final class AppMain extends Application {

	private final static Logger logger = Logger.getLogger(AppMain.class.getName());
	
	static public Properties properties = new Properties();
	static LogFileRead fileRead = new LogFileRead();
	static DbConnection dbConnection = new DbConnection();	
	static HashMap<String, Event> eventsMap;
	static File file;
 
	public static void main(String[] args) {
    	logger.info("Launching application.");
	   	LoadAppProperties();		
		Application.launch(args);
	}
	
    @Override
    public void start(final Stage stage) {
    	 
    	logger.info("Loading GUI.");
    	
    	// Setup GUI    	
    	final FileChooser fileChooser = new FileChooser();
        stage.setTitle(AppMessages.AppTitle.getText());
        final Button openButton = new Button("  Select File  ");
        final Button closeButton = new Button("     Close     "); 
        final Label appMessage = new Label(AppMessages.MainLabelMsg.getText());
        		      
        
        // Open File Action       
        openButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                	configureFileChooser(fileChooser);
                    file = fileChooser.showOpenDialog(stage);
                  
                    // Check if file exists                   
                    if(file == null) {
                    	return;
                    }else if(!(file.exists() && !file.isDirectory())) { 
                    	logger.debug("Selected file has not been found.");
                    	JOptionPane.showMessageDialog(null, AppMessages.FileNotExist.getText(),"Error", JOptionPane.ERROR_MESSAGE);  				
                      	return;
                	}
                                  	
                	// Start thread - reading the file               	
                    Thread threadFileRead = new Thread(new Runnable(){
                        @Override
                        public void run() {
                        	logger.info("Thread threadFileRead has been initialized.");	        
                        	eventsMap = fileRead.Read(file); 
                        }
                    });             
                    threadFileRead.start();

                	// Start thread - creating table                   
                    Thread threadCreateEventTable = new Thread(new Runnable(){
                        @Override
                        public void run() { 
                        	logger.info("Thread threadCreateEventTable has been initialized.");         
                            dbConnection.CreateEventTable();
                        }
                    });             
                    threadCreateEventTable.start();
                                  
              		// Current thread will wait for file to be read before starting to read from map and inserting to db                   
            		try {
            			threadFileRead.join();   				
            		}catch(InterruptedException ex) {
                    	logger.debug(ex.toString(), ex);
            	   	 	return;
            		}
            			
            		// Current thread will wait for table to be created before proceeding with inserts to db            		
            		try {
            			threadCreateEventTable.join();   				
            		} catch(InterruptedException ex) {
                    	logger.debug(ex.toString(), ex);
            	   	 	return;
            		}
                	
            		// Insert events to database          		
                    dbConnection.InsertData(eventsMap);
                    
                	logger.info("Data has been successfully loaded.");	                             	
                  	Alert alert = new Alert(AlertType.INFORMATION, "Data has been successfully loaded.", ButtonType.OK);
                  	alert.showAndWait();
                  	System.exit(0);               	
                }
            
            });
        
        
        // Close Action
        closeButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                	logger.info("Application closed.");
                	System.exit(0);
                }
            });
 
 
        // GUI Setup       
        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(appMessage, 1, 1);
        GridPane.setConstraints(openButton, 0, 5);
        GridPane.setConstraints(closeButton, 2, 5);
        inputGridPane.setHgap(8);
        inputGridPane.setVgap(8);
        inputGridPane.getChildren().addAll(openButton, closeButton, appMessage);
        
        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(24, 24, 24, 24));
        stage.setScene(new Scene(rootGroup));
        stage.show();
    }
 
    private static void configureFileChooser(final FileChooser fileChooser) {    	
        fileChooser.setTitle("Open File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All files", "*.*"));            
    }
    
    // Load Application properties   
    public static void LoadAppProperties(){    	
    	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    	try(InputStream inputProperties = classloader.getResourceAsStream("application.properties")){    		
    		properties.load(inputProperties);  		
    	}catch (IOException e) {
    		logger.debug("Application properties could not be loaded.",e);
    	}  	
    }
 

}


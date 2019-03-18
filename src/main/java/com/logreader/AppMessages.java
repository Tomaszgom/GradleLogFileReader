package com.logreader;

public enum AppMessages {
	
	/* Enumerator storing Application messages codes and messages */
	
	UnidentifiedError(0, "Unidentified error."),
	
	FileNotExist(1, "Input Log File does not exist in given directory. Make sure that the path is correct and try again."),
	
	WrongJSONrecordFormat(3,"The file has unexpected format."),
	
	AppTitle(100,"Log File Reader "),
	
	MainLabelMsg(102, "Program will read Log file and save Event records to the database.\n"
    		+ "Once the file is selected, please wait for the confirmation message. \n"
    		+ "Depending on the file size this may take a while. \n\n"
    		+ "Click Select File to continue.");

	private final int code;
	private final String text;

	private AppMessages(int code, String text) {
	    this.code = code;
	    this.text = text;
	}

	public String getText() {
	     return text;
	}

	public int getCode() {
	     return code;
	}

	@Override
	public String toString() {
	    return code + ": " + text;
	}
}

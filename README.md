# Gradle Log File Reader by Tomasz Gomoradzki

Application is a tool which allows to read Server Events from log files in defined format, create table and insert data to HSQL Database, storing records in local file.


Instruction on how to run the application from CMD Command Line (for Windows):

1. Clone the project from Githib, for example by using git command in console:
	git clone https://github.com/tomaszgom/gradle-logfile-reader

Open Command Line CMD and type each of the commands below from 2 to 5, followed by hitting enter. Commands will build and test with Gradle and run application. Once launched simple user interface with message will be displayed. This part of the below paths "C:\project directory\" should be replaced with appropriate project directory of local machine. 

2.	CD C:\project directory\gradle-logfile-reader
3.	gradle clean build
4.	CD C:\ project directory \gradle-logfile-reader\build\libs
5.	java -jar LogReader-1.0-SNAPSHOT.jar

The SQL Database output folder name is hsqldb and sits in project root folder.

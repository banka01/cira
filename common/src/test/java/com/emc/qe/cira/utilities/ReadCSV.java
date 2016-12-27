package com.emc.qe.cira.utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class ReadCSV {

	public static void main(String[] args) throws IOException {
		 
		String basepath ="C:\\backupdata\\Project_SE\\CI_Results\\Reporting\\Results\\";
		CSVReader reader = new CSVReader(new FileReader(basepath+"Tests_Execution_Status.csv"));
		String [] nextLine;
	     while ((nextLine = reader.readNext()) != null) {
	        // nextLine[] is an array of values from the line
	        System.out.println(nextLine[0] + nextLine[1] + "etc...");
	     }
	     reader.close();
}

	
}
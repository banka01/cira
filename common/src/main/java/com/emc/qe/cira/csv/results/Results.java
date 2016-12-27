package com.emc.qe.cira.csv.results;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;
import ch.qos.logback.core.net.SyslogOutputStream;





public class Results {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CIResults results = new CIResults("SE_CI","LINUX","622");
		//CIResults results = new CIResults("SE_CD_14_15");
		
		try {
			System.out.println(results.retrieveResults());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(results.getFile().getAbsolutePath().toString());
		Map<String, Object> summary = results.getResultSummary();
		
				
		
		List<Map<String,String>> newData = results.getData();		
		
		System.out.println(newData);
		
		
		
	
		System.out.println("********done********");
		

		
		

	}

}

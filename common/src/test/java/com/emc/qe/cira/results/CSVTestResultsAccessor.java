package com.emc.qe.cira.results;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;



import au.com.bytecode.opencsv.CSVReader;

public class CSVTestResultsAccessor implements TestResultsAccessor {

	@Override
	public TestResult retrieveTestResults(String param) {
		TestResult result = null;
		CSVReader csvReader = null;
		Properties testResultsLocationConfig = new Properties();
		try {
			testResultsLocationConfig.load(CIResultsAction.class.getResourceAsStream("resultsLocation.properties"));
			String location = (String) testResultsLocationConfig.get("location");
			//csvReader = new CSVReader(new FileReader(new File(location + "\\Tests_Execution_Status.csv")));
			csvReader = new CSVReader(new FileReader(new File(location + "\\"+param)));
			List<String[]> csvContents = csvReader.readAll();
			result = parseResult(csvContents);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				csvReader.close();
			} catch (IOException e) {

			}
		}
		return result;
	}

	private TestResult parseResult(List<String[]> csvContents) {

		TestResult testResult = new TestResult();

		String[] headers = csvContents.get(0);
		Properties headerProperties = new Properties();
		try {
			headerProperties.load(CIResultsAction.class.getResourceAsStream("headers.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TestResultRow> resultRows = new ArrayList<TestResultRow>();
		for (int index = 1; index < csvContents.size(); index++) {
			String[] row = csvContents.get(index);
			TestResultRow resultRow = new TestResultRow();
			List<NameValuePair> rowEntries = new ArrayList<NameValuePair>();
			//for (int rowIndex = headers.length - 1; rowIndex >= 0; rowIndex--) {
			for (int rowIndex = 0;rowIndex <=headers.length - 1; rowIndex++) {

				try {
					//System.out.println(row[rowIndex]);
					NameValuePair nameValuePair = new NameValuePair(
							headerProperties.getProperty(headers[rowIndex].trim()), row[rowIndex].trim());
					rowEntries.add(nameValuePair);
					//System.out.println(nameValuePair.getName());
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			resultRow.setResult(rowEntries);
			resultRows.add(resultRow);
			
			
		}
		testResult.setRows(resultRows);
		//System.out.println(testResult);
		return testResult;
	}

}

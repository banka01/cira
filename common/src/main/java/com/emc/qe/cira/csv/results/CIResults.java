package com.emc.qe.cira.csv.results;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public class CIResults {
	
	private static final Logger logger = LoggerFactory.getLogger(CIResults.class);
	
	private String name;

	private String osType;
	private String qeProduct;
	private String ciType;
	private String buildNumber;
	private String cdCycle;
	private File   file = null;
	private String fileName;
	
	private List<Map<String, String>> data;

	private Map<String, Object> resultSummary = new LinkedHashMap<String, Object>();

	

	public CIResults(String ciType,String os,String buildNo)
	{
		this.ciType = ciType;
		this.osType = os;
		this.buildNumber = buildNo;
		name="CI";
		cdCycle="N/A";
	}

//	public CIResults(String cdCycle) {
//		// TODO Auto-generated constructor stub
//		name="CD";
//		this.cdCycle = cdCycle;
//	}

	public CIResults(String fileName) {
		// TODO Auto-generated constructor stub
		this.fileName = fileName;
	}

	public Map<String, Object> getResultSummary() {
		return resultSummary;
	}

	public void setResultSummary(Map<String, Object> resultSummary) {
		this.resultSummary = resultSummary;
	}
	
	
	private TestResultsAccessor testResultAccessor = new CSVTestResultsAccessor();

	public String retrieveResults() throws Exception {

		data = new ArrayList<Map<String, String>>();
		String fileName  = this.fileName;
		TestResult result = testResultAccessor.retrieveTestResults(fileName);

		for (TestResultRow row : result.getRows()) {
			Map<String, String> jsonRow = new LinkedHashMap<String, String>();

			for (NameValuePair nameValuePair : row.getResult()) {
				jsonRow.put(nameValuePair.getName(), nameValuePair.getValue().toString());
			}
			data.add(jsonRow);
			
		}

		prepareSummary(result);
		return "success";
	}
	
	public String retrieveResults(String fileName) throws Exception {

		data = new ArrayList<Map<String, String>>();
		
		TestResult result = testResultAccessor.retrieveTestResults(fileName);

		for (TestResultRow row : result.getRows()) {
			Map<String, String> jsonRow = new LinkedHashMap<String, String>();

			for (NameValuePair nameValuePair : row.getResult()) {
				jsonRow.put(nameValuePair.getName(), nameValuePair.getValue().toString());
			}
			data.add(jsonRow);
			
		}

		prepareSummary(result);
		return "success";
	}

	private String getFileName() {
		// TODO Auto-generated method stub
		
			if(this.file !=null)
				return this.file.getAbsolutePath();
			
			File newestFile =null;
			Properties testResultsLocationConfig = new Properties();
		
			try {
				testResultsLocationConfig.load(CIResults.class.getResourceAsStream("resultsLocation.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(Marker.ANY_MARKER,"{}",e);
				e.printStackTrace();
			}
			
			
			String location;
			String release;
								
			Path filePath;
			
			if(name.equals("CI")){
				
				location  = (String) testResultsLocationConfig.get("CI_LOCATION");
				release  = (String) testResultsLocationConfig.get("release");
				filePath = Paths.get(location,release,this.osType,this.ciType,this.buildNumber);
			}
			else{
				location  = (String) testResultsLocationConfig.get("CD_LOCATION");
				release  = (String) testResultsLocationConfig.get("release");
				filePath = Paths.get(location,release,this.cdCycle);
			}
			
			
			logger.info("path:{}",filePath);
			
			File file = new File(filePath.toString());
				
			if(file.isDirectory())
			{
				FileFilter filter  = new WildcardFileFilter("*.csv");
				File[] allFiles = file.listFiles(filter);
				if (allFiles.length > 0) {
			        /** The newest file comes first **/
			        Arrays.sort(allFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			        newestFile = allFiles[0];
			    }
				
				logger.info("file is:{}",newestFile);
			}
			else
			{
				newestFile = file;
			}
		
		this.file = newestFile;
		return newestFile.getAbsolutePath();
	}

	private void prepareSummary(TestResult result) {
		prepareOverallStatus(result);
		prepareTotalTest(result);
		preparePassedTest(result);
		prepareFailedTest(result);
		preparedSkippedTest(result);
		prepareTotalTime(result);
	}

	private void preparedSkippedTest(TestResult result) {
		int skippedTests = 0;

		for (TestResultRow resultRow : result.getRows()) {
			for (NameValuePair nameValuePair : resultRow.getResult()) {
				if (nameValuePair.getName().equals("result")) {
					if (nameValuePair.getValue().equals("NOT_RUN")) {
						skippedTests++;
					}
				}
			}
		}
		resultSummary.put("skippedtest", String.valueOf(skippedTests));

	}

	private void prepareFailedTest(TestResult result) {
		int failedTests = 0;

		for (TestResultRow resultRow : result.getRows()) {
			for (NameValuePair nameValuePair : resultRow.getResult()) {
				if (nameValuePair.getName().equals("result")) {
					if (nameValuePair.getValue().equals("FAIL")) {
						failedTests++;
					}
				}
			}
		}
		resultSummary.put("failedtest", String.valueOf(failedTests));
	}

	private void preparePassedTest(TestResult result) {
		int passedTests = 0;

		for (TestResultRow resultRow : result.getRows()) {
			for (NameValuePair nameValuePair : resultRow.getResult()) {
				if (nameValuePair.getName().equals("result")) {
					if (nameValuePair.getValue().equals("PASS")) {
						passedTests++;
					}
				}
			}
		}
		resultSummary.put("passedtest", String.valueOf(passedTests));

	}

	private void prepareTotalTest(TestResult result) {
		resultSummary.put("totaltest", String.valueOf(result.getRows().size()));
	}

	private void prepareTotalTime(TestResult result) {
		long totalTime = 0;
		for (TestResultRow resultRow : result.getRows()) {
			String startTime = null;
			String endTime = null;
			for (NameValuePair nameValuePair : resultRow.getResult()) {
				if (nameValuePair.getName().equals("start_time")) {
					startTime = (String) nameValuePair.getValue();
				}
				if (nameValuePair.getName().equals("end_time")) {
					endTime = (String) nameValuePair.getValue();
				}
			}
			Date startTimeAsDate = CIResultsUtil.parseDate(startTime);
			Date endTimeAsDate = CIResultsUtil.parseDate(endTime);
			long testDuration = endTimeAsDate.getTime() - startTimeAsDate.getTime();
			totalTime+=testDuration;
		}
		
		Map<TimeUnit, Long> totalDurationMap = CIResultsUtil.parseDate(totalTime);
		Map<String, String> totalTimeJsonMap = new TreeMap<String, String>();
		for(Entry<TimeUnit, Long> totalDurationMapEntry : totalDurationMap.entrySet()) {
			if(totalDurationMapEntry.getValue() > 0) {
				totalTimeJsonMap.put(totalDurationMapEntry.getKey().toString(), totalDurationMapEntry.getValue().toString());
			}
		}
		resultSummary.put("totaltime", totalTimeJsonMap);
	}

	private void prepareOverallStatus(TestResult result) {
		int failedTests = 0;

		for (TestResultRow resultRow : result.getRows()) {
			for (NameValuePair nameValuePair : resultRow.getResult()) {
				if (nameValuePair.getName().equals("result")) {
					if (nameValuePair.getValue().equals("FAIL")) {
						failedTests++;
					}
				}
			}
		}

		int totalTests = result.getRows().size();

		float failPercentage = ((float) failedTests / (float) totalTests) * 100;
		if (failPercentage > 1) {
			resultSummary.put("overallstatus", "Fail");
		} else {
			resultSummary.put("overallstatus", "Pass");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQeProduct() {
		return qeProduct;
	}

	public List<Map<String, String>> getData() {
		return data;
	}

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

	public void setQeProduct(String qeProduct) {
		this.qeProduct = qeProduct;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	
	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

}
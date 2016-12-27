package com.emc.qe.cira.results;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class CIResultsAction {
	private String name;

	private String osType;
	private String qeProduct;

	private List<Map<String, String>> data;

	private Map<String, Object> resultSummary = new HashMap<String, Object>();

	public Map<String, Object> getResultSummary() {
		return resultSummary;
	}

	public void setResultSummary(Map<String, Object> resultSummary) {
		this.resultSummary = resultSummary;
	}

	private TestResultsAccessor testResultAccessor = new CSVTestResultsAccessor();

	public String retrieveResults() throws Exception {

		data = new ArrayList<Map<String, String>>();

		TestResult result = testResultAccessor.retrieveTestResults("ARTS_report_X8.4.0.217_559.csv");

		for (TestResultRow row : result.getRows()) {
			//Map<String, String> jsonRow = new TreeMap<String, String>();
			Map<String, String> jsonRow = new LinkedHashMap<String, String>();

			for (NameValuePair nameValuePair : row.getResult()) {
				//System.out.println(nameValuePair.getName());
				jsonRow.put(nameValuePair.getName(), nameValuePair.getValue().toString());
			}
			data.add(jsonRow);
			System.out.println(jsonRow);
		}
		

		prepareSummary(result);
		return "success";
	}

	private void prepareSummary(TestResult result) {
		prepareOverallStatus(result);
		prepareTotalTime(result);
		prepareTotalTest(result);
		preparePassedTest(result);
		prepareFailedTest(result);
		preparedSkippedTest(result);
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

}
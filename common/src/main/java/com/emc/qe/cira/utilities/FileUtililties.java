package com.emc.qe.cira.utilities;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import com.emc.qe.cira.csv.results.CIResults;

public class FileUtililties {

	private static final Logger logger = LoggerFactory.getLogger(FileUtililties.class);
	
	
	/**
	 * @param mode="CI"
	 * @param osType = "LINUX or WINDOWS"
	 * @param ciType ="SE_CI or SE_NIGHTLY"
	 * @param buildNo ="valid build no"
	 * @return absolute path of the file or null
	 * @throws FileNotFoundException 
	 */
	public static String getFileName(String mode,String osType,String ciType ,String buildNo) throws FileNotFoundException{

		File newestFile =null;
		Properties testResultsLocationConfig = new Properties();

		try {
			testResultsLocationConfig.load(CIResults.class.getResourceAsStream("resultsLocation.properties"));
		} catch (IOException e) {
			logger.error(Marker.ANY_MARKER,"{}",e);
			e.printStackTrace();
		}

		String location;
		String release;

		Path filePath = null;

		if(mode.equals("CI")){

			location  = (String) testResultsLocationConfig.get("CI_LOCATION");
			release  = (String) testResultsLocationConfig.get("release");
			filePath = Paths.get(location,release,osType,ciType,buildNo);
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
				logger.info("file is:{}",newestFile.getAbsolutePath());
			}
			else{
				logger.info("No file present:{}",file.getAbsolutePath());
				throw new FileNotFoundException("Empty Directory or no csv file");
			}

		}
		else if(file.isFile()){
			newestFile = file;
			logger.info("file is:{}",newestFile.getAbsolutePath());
		}else{
			logger.error(" no file or directory:{}",newestFile);
			throw new FileNotFoundException("File not found");
		}
		
		
		return newestFile.getAbsolutePath();
	}


	public static String getFileName(String cdCycle) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		File newestFile =null;
		Properties testResultsLocationConfig = new Properties();

		try {
			testResultsLocationConfig.load(CIResults.class.getResourceAsStream("resultsLocation.properties"));
		} catch (IOException e) {
			logger.error(Marker.ANY_MARKER,"{}",e);
			e.printStackTrace();
		}

		String location;
		
		Path filePath = null;
		
		location  = (String) testResultsLocationConfig.get("CD_LOCATION");
		String release  = (String) testResultsLocationConfig.get("release");
		filePath = Paths.get(location,release,cdCycle);
		

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
				logger.info("file is:{}",newestFile.getAbsolutePath());
			}
			else{
				logger.info("No file present:{}",file.getAbsolutePath());
				throw new FileNotFoundException("Empty Directory or no csv file");
			}

		}
		else if(file.isFile()){
			newestFile = file;
			logger.info("file is:{}",newestFile.getAbsolutePath());
		}else{
			logger.error(" no file or directory:{}",newestFile);
			throw new FileNotFoundException("File not found");
		}
		
		
		
		return newestFile.getAbsolutePath();
	}
	
}

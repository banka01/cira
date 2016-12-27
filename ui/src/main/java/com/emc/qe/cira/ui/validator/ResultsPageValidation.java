/**
 * 
 */
package com.emc.qe.cira.ui.validator;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emc.qe.cira.ui.design.ResultsDashBoard;
import com.emc.qe.cira.ui.design.ResultsDashBoardResultsType;
import com.emc.qe.cira.utilities.FileUtililties;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

/**
 * @author bankar
 *
 */
public class ResultsPageValidation{
	
	private static final Logger logger = LoggerFactory.getLogger(ResultsPageValidation.class);
	
	public static String fileName = null;
	
	public static Boolean validateFileName(String mode,String osType,String ciType,String buildNo) throws FileNotFoundException{
		
		Boolean  flag = false;
			
		String resultsType = ResultsDashBoardResultsType.valueOf(ciType.toUpperCase()).getValue();
			
		fileName = FileUtililties.getFileName("CI",osType,resultsType,buildNo);
		if(fileName != null){
				flag =  true;
			}
		
		
		return flag;
		
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static Boolean validateFileName(String string, String cdCycle) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		Boolean  flag = false;
		fileName = FileUtililties.getFileName(cdCycle);
		if(fileName != null){
				flag =  true;
			}
		
		
		return flag;
		
	}

}

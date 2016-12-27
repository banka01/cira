package com.emc.qe.cira.utilities;

import java.io.IOException;
import java.io.InputStream;

public class Connect {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String cmd="C:\\project_automation\\learn\\putty\\plink root@dlqa1155 -pw remember ls";
		
	    System.out.println(cmd);
	    
	    Process process=Runtime.getRuntime().exec(cmd);
	    Integer pid;
	    try {
	    	
	    	InputStream in = process.getInputStream();
		    for (int i = 0; i < in.available(); i++)
		    {
		    	   System.out.println("" + in.read());
		    }
			pid = process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    process.destroy();
	    
	}

}

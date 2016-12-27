package com.emc.qe.cira.results;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import ch.qos.logback.core.net.SyslogOutputStream;





public class Results {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CIResultsAction results = new CIResultsAction();
		
		try {
			System.out.println(results.retrieveResults());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		List<Map<String, String>>data1 = results.getData();
		
		
		System.out.println("---------------------------\n");
//		Map<String,String>testMap = new LinkedHashMap();//<String,String>();
//		
//		testMap.put("a","apple");
//		testMap.put("d","dog");
//		testMap.put("c","cat");
//		testMap.put("b","boy");
//		System.out.println(testMap);
		
		System.out.println(data1.iterator().next().keySet());
		
		for(Map<String,String> m : data1)
		{
			System.out.println(m.toString());
			for(Map.Entry<String,String> entry:m.entrySet())
			{
				
				System.out.println(entry.getKey()+":"+entry.getValue().toString());
				
			}
		}
//		String[] datatest = data1.iterator().next().values().toArray(new String[0]);
//		for(String e:datatest)
//		{
//			System.out.println(e);
//		}
		
		
//		
	}

}

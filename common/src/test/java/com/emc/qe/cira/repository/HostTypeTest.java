package com.emc.qe.cira.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.emc.qe.cira.config.CiraDbConfig;
import com.emc.qe.cira.model.Host;
import com.emc.qe.cira.model.HostType;



@ContextConfiguration(classes=CiraDbConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class HostTypeTest {
	private static final Logger logger = LoggerFactory.getLogger(HostTypeTest.class);
	
	
	@Autowired private HostTypeRepository hostTypeRepository;
	
	
	@Test public void
	whenFindAll_givenDataInData_shouldReturnAllRows() {
		
	List<String> host = null; //= hostTypeRepository.findByHostOs("LINUX");
			
			
			
			host = hostTypeRepository.findUniqueHostOs();
			
			host.forEach(System.out::println);
			
				
			
				
		
	}


	
	
//	public  List<HostType>  getName()
//	{
//		List<HostType> host = hostTypeRepository.findByHostOs("LINUX");
//		
//		host.forEach((HostType h)->System.out.println(h.getHostName()));
//		
//		return host;
//		
//	}
	
	
}

package com.emc.qe.cira.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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

@ContextConfiguration(classes=CiraDbConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class HostRepositoryIT {
	private static final Logger logger = LoggerFactory.getLogger(HostRepositoryIT.class);
	
	
	@Autowired private HostRepository hostRepository;
	
	@Test public void
	whenFindAll_givenDataInData_shouldReturnAllRows() {
		List<Host> hosts = hostRepository.findAll();
		
		logger.info("Hosts: {}", hosts);
		assertThat(hosts, is(equalTo(1)));
		
	}
	
	@Test public void
	whenFindByHostName_givenDataInData_shouldReturnAllRows() {
		Host host = hostRepository.findByHostName("abcdHost");
//		host.setHostOs("windows");
//		hostRepository.save(host);
		logger.info("Single Host: {}", host);
		assertThat(host, is(notNullValue()));
		
	}
}

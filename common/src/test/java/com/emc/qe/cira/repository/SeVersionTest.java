package com.emc.qe.cira.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.emc.qe.cira.config.CiraDbConfig;
import com.emc.qe.cira.model.SeVersion;


@ContextConfiguration(classes=CiraDbConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SeVersionTest {

	@Autowired SeVersionRepository seVersion;
	@Test
	public void test() {
		
		
		
		List<SeVersion> version = seVersion.findAll();
		
		System.out.println(version.toString());
		
		List<String> v1 = seVersion.findSeVersion();
		
		v1.stream()
		.forEach(System.out::println);
//		fail("Not yet implemented");
	}

}

package com.emc.qe.cira.repository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.emc.qe.cira.config.CiraDbConfig;
import com.emc.qe.cira.model.JenkinsSEJob;

@ContextConfiguration(classes=CiraDbConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)

public class JenkinsSEJobTest {

	private static final Logger logger = LoggerFactory.getLogger(JenkinsSEJob.class);
	
	@Autowired private JenkinsSEJobRepository jsejob;
//	@Test public void
//	whenFindByJobType_givenDataJobType_returnAllJobs(){
//		
//		List<JenkinsSEJob> obj = jsejob.findByJobType("CI");
//		obj.forEach(e->System.out.println(e.getJobName()+":"+e.getBuildNo()));
//		
//		
//		List<JenkinsSEJob> obj1 = jsejob.findByJobTypeOrderByBuildNoDesc("CI");
//		obj1.forEach(e->System.out.println(e.getJobName()+":"+e.getBuildNo()));
//		
//		
//		
//	}
	@Test public void
	whenCreateEntry()
	{
			
		JenkinsSEJob  o1 = new JenkinsSEJob();
		o1.setId(8);
		o1.setHostOs("LINUX");
		o1.setBuildNo("t8.4.0.276-2350");
		o1.setJobName("se-ci-linux-840-nightly");
		o1.setJobType("Nightly");
		
		JenkinsSEJob  o2 = new JenkinsSEJob();
		o2.setId(9);
		o2.setHostOs("LINUX");
		o2.setBuildNo("t8.4.0.274-2350");
		o2.setJobName("se-ci-linux-840-nightly");
		o2.setJobType("Nightly");
		
		
		
		
		
		jsejob.save(o1);
		jsejob.save(o2);
		
	}
}

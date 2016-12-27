package com.emc.qe.cira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emc.qe.cira.model.JenkinsSEJob;

public interface JenkinsSEJobRepository extends JpaRepository<JenkinsSEJob,Long> {

	List<JenkinsSEJob> findByJobType(String param);
	
	// query to retrieve rows based on JobType arranged by descending Job No.
	List<JenkinsSEJob> findByJobTypeOrderByBuildNoDesc(String param);
	
	// find hostOS based on job Type
	List<JenkinsSEJob> findHostOsByJobType(String param);

	List<JenkinsSEJob> findBuildNoByHostOs(String name);

	List<JenkinsSEJob> findBuildNoByHostOsOrderByBuildNoDesc(String name);


	List<JenkinsSEJob> findBuildNoByJobTypeAndHostOsOrderByBuildNoDesc(String ciType, String osType);
	
}


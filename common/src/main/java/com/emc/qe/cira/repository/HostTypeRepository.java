package com.emc.qe.cira.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.emc.qe.cira.model.HostType;

//@Repository
public interface HostTypeRepository extends JpaRepository<HostType,Long> {

	List<HostType> findByHostOs(String name);
		
	
	@Query("SELECT t.hostName FROM HostType t where t.hostOs=?1")
	List<String> findHostNameByHostOs(String name);
	
	@Query("SELECT DISTINCT h.hostOs FROM HostType h")
	List<String> findUniqueHostOs();
	
	
	
}

package com.emc.qe.cira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emc.qe.cira.model.Host;

public interface HostRepository extends JpaRepository<Host, Long> {

	Host findByHostName(String name);
		
}

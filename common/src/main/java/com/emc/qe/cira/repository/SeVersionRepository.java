/**
 * 
 */
package com.emc.qe.cira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emc.qe.cira.model.SeVersion;

/**
 * @author bankar
 *
 */
public interface SeVersionRepository extends JpaRepository<SeVersion, Long> {

	@Query("Select s.version from SeVersion s")
	List<String> findSeVersion();

}

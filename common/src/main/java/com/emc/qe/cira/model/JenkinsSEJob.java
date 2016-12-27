package com.emc.qe.cira.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;

/**
 * SeTestJobDetails generated by hbm2java
 */
@Entity
@Table(name = "se_test_job_details", schema = "public")
public class JenkinsSEJob implements java.io.Serializable {

	private long id;
	private String jobName;
	private String hostOs;
	private String buildNo;
	private String jobType;

	public JenkinsSEJob() {
	}

	public JenkinsSEJob(long id) {
		this.id = id;
	}

	public JenkinsSEJob(long id, String jobName, String hostOs, String buildNo, String jobType) {
		this.id = id;
		this.jobName = jobName;
		this.hostOs = hostOs;
		this.buildNo = buildNo;
		this.jobType = jobType;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "job_name", length = 100)
	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Column(name = "host_os", length = 10)
	public String getHostOs() {
		return this.hostOs;
	}

	public void setHostOs(String hostOs) {
		this.hostOs = hostOs;
	}

	@Column(name = "build_no", length = 30)
	public String getBuildNo() {
		return this.buildNo;
	}

	public void setBuildNo(String buildNo) {
		this.buildNo = buildNo;
	}

	@Column(name = "job_type", length = 10)
	public String getJobType() {
		return this.jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	@Override
	public String toString() {
		return "JenkinsSEJob [id=" + id + ", jobName=" + jobName + ", hostOs=" + hostOs + ", buildNo=" + buildNo
				+ ", jobType=" + jobType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buildNo == null) ? 0 : buildNo.hashCode());
		result = prime * result + ((hostOs == null) ? 0 : hostOs.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((jobName == null) ? 0 : jobName.hashCode());
		result = prime * result + ((jobType == null) ? 0 : jobType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JenkinsSEJob other = (JenkinsSEJob) obj;
		if (buildNo == null) {
			if (other.buildNo != null)
				return false;
		} else if (!buildNo.equals(other.buildNo))
			return false;
		if (hostOs == null) {
			if (other.hostOs != null)
				return false;
		} else if (!hostOs.equals(other.hostOs))
			return false;
		if (id != other.id)
			return false;
		if (jobName == null) {
			if (other.jobName != null)
				return false;
		} else if (!jobName.equals(other.jobName))
			return false;
		if (jobType == null) {
			if (other.jobType != null)
				return false;
		} else if (!jobType.equals(other.jobType))
			return false;
		return true;
	}
		

}

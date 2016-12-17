package com.emc.qe.cira.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "results", schema = "public")
public class Results implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private Integer testId;
	private String testName;
	private String iteration;
	private String dispatchedOn;
	private String user;
	private String cycle;
	private String hostName;
	private Integer sid;
	private String result;
	private String runId;
	private Date startTime;
	private Date endTime;
	private String outfile;
	private String comments;
	private String buildNo;
	private String testType;

	@Column(name = "test_id")
	public Integer getTestId() {
		return this.testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	@Column(name = "test_name", length = 100)
	public String getTestName() {
		return this.testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	@Column(name = "iteration", length = 30)
	public String getIteration() {
		return this.iteration;
	}

	public void setIteration(String iteration) {
		this.iteration = iteration;
	}

	@Column(name = "dispatched_on", length = 30)
	public String getDispatchedOn() {
		return this.dispatchedOn;
	}

	public void setDispatchedOn(String dispatchedOn) {
		this.dispatchedOn = dispatchedOn;
	}

	@Column(name = "user", length = 30)
	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Column(name = "cycle", length = 5)
	public String getCycle() {
		return this.cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	@Column(name = "host_name", length = 30)
	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	@Column(name = "sid")
	public Integer getSid() {
		return this.sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	@Column(name = "result", length = 30)
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "run_id")
	public String getRunId() {
		return this.runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}

	@Column(name = "start_time", length = 29)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", length = 29)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "outfile")
	public String getOutfile() {
		return this.outfile;
	}

	public void setOutfile(String outfile) {
		this.outfile = outfile;
	}

	@Column(name = "comments", length = 100)
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name = "build_no", length = 30)
	public String getBuildNo() {
		return this.buildNo;
	}

	public void setBuildNo(String buildNo) {
		this.buildNo = buildNo;
	}

	@Column(name = "test_type", length = 30)
	public String getTestType() {
		return this.testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	@Id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buildNo == null) ? 0 : buildNo.hashCode());
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
		Results other = (Results) obj;
		if (buildNo == null) {
			if (other.buildNo != null)
				return false;
		} else if (!buildNo.equals(other.buildNo))
			return false;
		return true;
	}

}

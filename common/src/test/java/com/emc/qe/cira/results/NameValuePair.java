package com.emc.qe.cira.results;

public class NameValuePair {

	private String name;
	private Object value;
	public NameValuePair(String property, Object value) {
		this.name = property;
		this.value = value;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}

package com.emc.qe.cira.ui.design;

public enum ResultsDashBoardResultsType {

	CI("SE_CI"),NIGHTLY("SE_NIGHTLY");
	private String value;
	private ResultsDashBoardResultsType(String  value)
	{
		this.value =value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}

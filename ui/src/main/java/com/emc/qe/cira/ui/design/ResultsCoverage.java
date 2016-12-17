package com.emc.qe.cira.ui.design;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringView(name = ResultsCoverage.VIEW_NAME)
public class ResultsCoverage extends ResultsCoverageDesign implements View {

	public static final String VIEW_NAME = "ResultsCoverage";
    
	
	public ResultsCoverage()
	{
		
		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}

package com.emc.qe.cira.ui.test.design;


import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;

@UIScope
@SpringView(name = ResultsDashBoard.VIEW_NAME)
public class ResultsDashBoard extends ResultDashBoardDesign implements View {
	
	public static final String VIEW_NAME = "ResultsDashBoard";
    protected Button resultsButton = new Button();
    
    @PostConstruct
    void init(){
    	
    	resultsButton.setCaption("welcome");
		statusLabel.setVisible(false);
		results.setVisible(false);
		ciResults.setValue(false);
		//addComponent(resultsButton);
    }
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}

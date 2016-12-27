package com.emc.qe.cira.ui.design;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.emc.qe.cira.ui.CiraUI;

import com.vaadin.annotations.Push;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@SpringComponent
@Push
public class MainPage extends MainPageDesign  {
	
	//protected Panel panel = new Panel();
	@Autowired private InstallSE seobj;
	@Autowired private ResultsCoverage resultObj;
	@Autowired private ResultsDashBoard resultsDashObj;
	@Autowired
    SpringViewProvider viewProvider;
	private CiraUI ciraUI;
	
	//Can't use @PostConstruct as UI will be null
	public void init()
	{   
		menuLayout.setSpacing(false);
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		System.out.println(basepath);
		logo.setIcon(new FileResource(new File(basepath+"/WEB-INF/images/header.png")));
		Navigator navigator = new Navigator(ciraUI,panel);
		
		navigator.addProvider(viewProvider);
		
//		navigator.addView(InstallSE.VIEW_NAME,InstallSE.class);
//		navigator.addView(ResultsDashBoard.VIEW_NAME,ResultsDashBoard.class);
//		navigator.addView(ResultsCoverage.VIEW_NAME, ResultsCoverage.class);
		
		if (navigator.getState().isEmpty()) {
	        navigator.navigateTo(InstallSE.VIEW_NAME);
	    }
		
		installSE.addClickListener(event->{
			//panel.setContent(seobj);
			
			doNavigate(InstallSE.VIEW_NAME);
			//this.addComponent(seobj);
		});
		
		resultsDashBoard.addClickListener(event->
		{
			doNavigate(ResultsDashBoard.VIEW_NAME);
			//doNavigate(ResultsDash.VIEW_NAME);
			//this.addComponent(resultDBObj);
			//panel.setContent(new ResultsDashBoard());
			
		});
		coverage.addClickListener(event->{
			doNavigate(ResultsCoverage.VIEW_NAME);
			//this.addComponent(resultObj);
		});
		
		
		//this.addComponent(panel);
		
	}

	private void doNavigate(String viewName) {
		// TODO Auto-generated method stub
		
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}

	public void setUIInstance(CiraUI ciraUI) {
		this.ciraUI = ciraUI;
	}

	
	
	

	
	
	
}

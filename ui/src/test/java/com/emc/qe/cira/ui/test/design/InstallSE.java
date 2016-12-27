/**
 * 
 */
package com.emc.qe.cira.ui.test.design;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.emc.qe.cira.repository.HostRepository;
import com.emc.qe.cira.repository.HostTypeRepository;
import com.emc.qe.cira.repository.SeVersionRepository;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * @author bankar
 *
 */
@UIScope
@SpringView(name = InstallSE.VIEW_NAME)
public class InstallSE extends VerticalLayout implements View{

	private static final Logger logger = LoggerFactory.getLogger(InstallSE.class);
	public static final String VIEW_NAME = "InstallSE";
	final Label status = new Label("Not Started");
	
	@Autowired 
	private HostRepository hostRepository;
	
	@Autowired 
	private HostTypeRepository hostTypeRepository;
	
	@Autowired 
	private SeVersionRepository seVersionRepository;
	
	@PostConstruct
	void init(){
		
		setSpacing(true);
		setDefaultComponentAlignment(Alignment.TOP_LEFT);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}

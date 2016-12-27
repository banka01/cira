/**
 * 
 */
package com.emc.qe.cira.ui.design;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emc.qe.cira.model.Host;
import com.emc.qe.cira.ui.CiraUI;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author bankar
 *
 */
@UIScope
@SpringComponent
public class HostDetails extends VerticalLayout{
	 
	
	private static final Logger logger = LoggerFactory.getLogger(HostDetails.class);
		
	private Label label;// = new Label("Inside new form");
	private Grid grid;
	private BeanItemContainer<Host> hostDetailsBean;
	
	@PostConstruct
	void init()
	{
		grid = new Grid();
		setSpacing(true);
			
		addComponent(grid);
	}
	
	public void updateGrid(Host host)
	{
		hostDetailsBean = new BeanItemContainer<Host>(Host.class);
		grid.setVisible(true);
		
		hostDetailsBean.addBean(host);
	
		logger.info("type is :{}",hostDetailsBean.getBeanType());
				
		
		grid.setContainerDataSource(hostDetailsBean);
		//grid.addColumn("abc");
	}
	

}

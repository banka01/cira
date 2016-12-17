/**
 * 
 */
package com.emc.qe.cira.ui.design;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.emc.qe.cira.model.Host;
import com.emc.qe.cira.model.HostType;
import com.emc.qe.cira.model.SeVersion;
import com.emc.qe.cira.repository.HostRepository;
import com.emc.qe.cira.repository.HostTypeRepository;
import com.emc.qe.cira.repository.SeVersionRepository;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

import ch.qos.logback.core.net.SyslogOutputStream;

/**
 * @author bankar
 *
 */
@UIScope
@SpringView(name = InstallSE.VIEW_NAME)
public class InstallSE extends InstallSEDesign implements View{
	
	//protected Panel panel = new Panel();
	private static final Logger logger = LoggerFactory.getLogger(InstallSE.class);
	
	protected Label l1 = new Label();
	public static final String VIEW_NAME = "InstallSE";
	
	
	@Autowired private HostRepository hostRepository;
	
	@Autowired private HostTypeRepository hostTypeRepository;
	
	@Autowired private SeVersionRepository seVersionRepository;
	


	@PostConstruct
	void init() 
	{
		data_grid.setColumns("NAME","SE_VERSION","HOST_OS","SE_VERSION","IP_ADDRESS");
		OsComboBoxPopulate();
		//dataGridPopulate();
		submit.setEnabled(true);
		//populateSEVersionUpgradeList();
	    
		//
		host_list.setNullSelectionAllowed(false);
	    
		
		install_se_cb.addValueChangeListener(event->
					 {
						 Boolean value = install_se_cb.getValue();
						 panel.setVisible(false);
						 data_grid.setVisible(false);
						 
						 if(value == true)
						 {
							 seVersion_list.setVisible(true);
							 populateSEVersionUpgradeList();
						 }
						 else
						 {
							 seVersion_list.setVisible(false);
							 
						 }
							
							//seVersion_list.setVisible(value);
							host_details_cb.setValue(!value);
							if(!submit.isEnabled())
							{
								submit.setEnabled(true);
							}
							//spopulateSEVersionUpgradeList(value);
					 });
		host_details_cb.addValueChangeListener(event->
					{
						
						Boolean value = host_details_cb.getValue();
						install_se_cb.setValue(!value);
						
						
					});
		
		submit.addClickListener(event->	
		{
							
			panel.setVisible(true);
			if(host_details_cb.getValue() == true)
			{
				data_grid.setVisible(true);
			}
			
			
			dataGridPopulate();
			
			String os = host_list.getValue().toString();
			l1.setCaption("inside".concat(os));
			
			host_input.setValidationVisible(true);
			try
			{
				
				this.addComponent(l1);	
			}catch(InvalidValueException e)
			{
				Notification.show(e.getMessage());
				host_input.setValidationVisible(true);
			}
					
			
						
			
		});
				
	}

	
	

	private void populateSEVersionUpgradeList() {
		// TODO Auto-generated method stub
		seVersion_list.removeAllItems();
		
		List<String> version = seVersionRepository.findSeVersion();
		logger.info("SE version:{}",version);
		
		seVersion_list.addItems(version);
		
		seVersion_list.setValue(seVersion_list.getItemIds().iterator().next());
		
		
	}


	private void dataGridPopulate() {
	// TODO Auto-generated method stub
		//data_grid.setVisible(false);
		
		
		if(host_details_cb.getValue() == true)
		{
			logger.info("grid populated");
			logger.info("host is:{}",host_list.getValue().toString());
		}
		else
		{
			logger.info("grid not populated");
		}
		
		
		
}
@SuppressWarnings("deprecation")
private void OsComboBoxPopulate()  
{
				
		os_combobox.removeAllItems();
		
		List<String> hostOs = hostTypeRepository.findUniqueHostOs();
				
		logger.info("Unique host selected is:{}",hostOs);
		
		hostOs.forEach((os)-> os_combobox.addItem(os)); // adding os name to combo box
				
		os_combobox.setNullSelectionAllowed(false);
		os_combobox.setValue(os_combobox.getItemIds().iterator().next());
				
		logger.info("OS in combox box:{}",os_combobox.getValue());
				
		String selOs = os_combobox.getValue().toString();
		
		populateHostName(selOs);
		
		os_combobox.addListener(new Property.ValueChangeListener() {

	        @Override
	        public void valueChange(ValueChangeEvent event) {
	        	host_list.removeAllItems();
	        	
	        	logger.info("OS in combox box:{}",os_combobox.getValue());
	        	populateHostName(os_combobox.getValue().toString());
	        
	        }   
	    });   
		
}
/**
 * This method is used to populate the comboBox
 * @param selOs the OS name for which the host name to be displayed
 */
private void populateHostName(String selOs) {
		
	List<String> osName = hostTypeRepository.findHostNameByHostOs(selOs);
	
	host_list.addItems(osName);
	
	host_list.setValue(host_list.getItemIds().iterator().next());
	
	logger.info("OS:[{}],Host:{},ListBox:{}",selOs,osName,host_list.getItemIds().toString());
	
}


@Override
public void enter(ViewChangeEvent event) {
	// TODO Auto-generated method stub
	
}
	

}

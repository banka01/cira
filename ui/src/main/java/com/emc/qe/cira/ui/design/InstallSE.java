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
import com.emc.qe.cira.repository.HostRepository;
import com.emc.qe.cira.repository.HostTypeRepository;
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
	
//	public InstallSE() 
//	{
//		
//		OsComboBoxPopulate();
//		dataGridPopulate();
//		submit.setEnabled(true);
//	    
//		//
//		host_list.setNullSelectionAllowed(false);
//	    
//		
//		install_se_cb.addValueChangeListener(event->
//					 {
//						 Boolean value = install_se_cb.getValue();
//							
//							seVersion_list.setVisible(value);
//							host_details_cb.setValue(!value);
//							if(!submit.isEnabled())
//							{
//								submit.setEnabled(true);
//							}
//					 });
//		host_details_cb.addValueChangeListener(event->
//					{
//						
//						Boolean value = host_details_cb.getValue();
//						install_se_cb.setValue(!value);
//						
//					});
//		
//		submit.addClickListener(event->	
//		{
//			
//				
//				Collection<String> list = Collections.emptyList();
//						//TODO FIX host.getHostDetails("DLQA4213");
//				
//				
//				
//				System.out.println(list.toString());
//				
//				
//				
//			panel.setVisible(true);
//			data_grid.setVisible(true);
//			
//			
//			
//			String os = host_list.getValue().toString();
//			l1.setCaption("inside".concat(os));
//			
//			host_input.setValidationVisible(true);
//			try
//			{
//				
//				this.addComponent(l1);	
//			}catch(InvalidValueException e)
//			{
//				Notification.show(e.getMessage());
//				host_input.setValidationVisible(true);
//			}
//					
//			
//						
//			
//		});
//				
//	}

	@PostConstruct
	void init() 
	{
		
		OsComboBoxPopulate();
		dataGridPopulate();
		submit.setEnabled(true);
	    
		//
		host_list.setNullSelectionAllowed(false);
	    
		
		install_se_cb.addValueChangeListener(event->
					 {
						 Boolean value = install_se_cb.getValue();
							
							seVersion_list.setVisible(value);
							host_details_cb.setValue(!value);
							if(!submit.isEnabled())
							{
								submit.setEnabled(true);
							}
					 });
		host_details_cb.addValueChangeListener(event->
					{
						
						Boolean value = host_details_cb.getValue();
						install_se_cb.setValue(!value);
						
					});
		
		submit.addClickListener(event->	
		{
			
				
				Collection<String> list = Collections.emptyList();
						//TODO FIX host.getHostDetails("DLQA4213");
				
				
				
				System.out.println(list.toString());
				
				
				
			panel.setVisible(true);
			data_grid.setVisible(true);
			
			
			
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

	
	private void dataGridPopulate() {
	// TODO Auto-generated method stub
		data_grid.setVisible(false);
		data_grid.setColumns("NAME","SE_VERSION","HOST_OS","OS_VERSION","IP_ADDRESS");
		
		
}
@SuppressWarnings("deprecation")
private void OsComboBoxPopulate()  
{
		// TODO Auto-generated method stub
		
		os_combobox.removeAllItems();
		
		List<String> hostOs = hostTypeRepository.findUniqueHostOs();
				
		logger.info("Unique host selected is:{}",hostOs);
		
		hostOs.forEach((os)-> os_combobox.addItem(os)); // adding os name to combo box
				
		os_combobox.setNullSelectionAllowed(false);
		os_combobox.setValue(os_combobox.getItemIds().iterator().next());
				
		logger.info("OS in combox box:{}",os_combobox.getValue());
		
		
		String selOs = os_combobox.getValue().toString();
		
		populateHostName(selOs);
		
//		TODO FIX osList = listOSName(os_combobox.getValue().toString());
		
		
//		for(String name:osList)
//		{
//			host_list.addItem(name);
//		}
//		host_list.select(host_list.getItemIds().iterator().next());
		
		os_combobox.addListener(new Property.ValueChangeListener() {

	        @Override
	        public void valueChange(ValueChangeEvent event) {
	        	host_list.removeAllItems();
	        	
	        	populateHostName(os_combobox.getValue().toString());
	        	logger.info("OS in combox box:{}",os_combobox.getValue());
	           
	        }   
	    });   
		
}


private void populateHostName(String selOs) {
	// TODO Auto-generated method stub
	
	List<String> osName = hostTypeRepository.findHostNameByHostOs(selOs);
	logger.info("os selected is:{}",osName);
	
	
}


@Override
public void enter(ViewChangeEvent event) {
	// TODO Auto-generated method stub
	
}
	

}

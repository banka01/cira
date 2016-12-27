/**
 * 
 */
package com.emc.qe.cira.ui.design;

import com.emc.qe.cira.ui.utils.*;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.emc.qe.cira.model.Host;
import com.emc.qe.cira.repository.HostRepository;
import com.emc.qe.cira.repository.HostTypeRepository;
import com.emc.qe.cira.repository.SeVersionRepository;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.UI;
import com.vaadin.ui.Label;

/**
 * @author bankar
 *
 */
@UIScope
@SpringView(name = InstallSE.VIEW_NAME)
public class InstallSE extends InstallSEDesign implements View{
		
	private static final Logger logger = LoggerFactory.getLogger(InstallSE.class);
	
	final Label status = new Label("Not Started");
	
	public static final String VIEW_NAME = "InstallSE";
	
	protected Grid  grid = new Grid();
		
	@Autowired 
	private HostRepository hostRepository;
	
	@Autowired 
	private HostTypeRepository hostTypeRepository;
	
	@Autowired 
	private SeVersionRepository seVersionRepository;
	

	@PostConstruct
	void init() 
	{
		
		status.setVisible(false);
		installProgress.setEnabled(false);
		OsComboBoxPopulate();
		
		submit.setEnabled(true);
		
		hostList.setNullSelectionAllowed(false);
	    		
		hostGrid.setVisible(false);
		
		installSE.addValueChangeListener(event->
					 {
						 Boolean value = installSE.getValue();
						 
						 hostGrid.setVisible(false);
						 
						 if(value == true)
						 {
							 versionList.setVisible(true);
							 populateSEVersionUpgradeList();
							 status.setSizeUndefined();
							 
							 addComponent(status);
							 
						 }
						 else
						 {
							 versionList.setVisible(false);
//							 panel.setVisible(false);
//							 data_grid.setVisible(false);
//							 
						 }
												
						hostDetail.setValue(!value);
						if(!submit.isEnabled())
						{
							submit.setEnabled(true);
						}
							
					 });
		hostDetail.addValueChangeListener(event->
					{
						
						Boolean value = hostDetail.getValue();
						installSE.setValue(!value);
						
						
					});
		
		submit.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				String hostName = hostList.getValue().toString();
				
				if(hostDetail.getValue())
				{	
					installProgress.setVisible(false);
					status.setVisible(false);
					dataGridPopulate(hostName);
				}
				if(installSE.getValue())
				{
					String version = versionList.getValue().toString();
					installProgress.setVisible(true);
					
					status.setVisible(true);
									
					
					Install obj;
					try {
						obj = new Install(hostName,version,installProgress,status);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
					obj.start();
					
					UI.getCurrent().setPollInterval(200);
					
					installProgress.setEnabled(true);
					status.setValue("running...");
					
					logger.info("versio nis :{}",version);
					
										
					
				}
							
				
			}
			
		});
						
	}


	private void populateSEVersionUpgradeList() {
		
		versionList.removeAllItems();
		
		List<String> version = seVersionRepository.findSeVersion();
		logger.info("SE version:{}",version);
		
		versionList.addItems(version);
		
		versionList.setValue(versionList.getItemIds().iterator().next());
		
		
	}

	

	private void dataGridPopulate(String hostName) 
	{
		
		hostGrid.setSelectionMode(SelectionMode.NONE);
		
		Host host = hostRepository.findByHostName(hostName);

		final BeanItemContainer<Host> hostDetailsBean = new BeanItemContainer<Host>(Host.class);
				
		hostDetailsBean.addBean(host);
	
		logger.info("type is :{}",hostDetailsBean.getBeanType());

		hostGrid.setContainerDataSource(hostDetailsBean);
		hostGrid.setSizeFull();
		hostGrid.setCaption("Host Details");
		hostGrid.setHeightMode(HeightMode.ROW);
		
		List<Column> cols = hostGrid.getColumns();
		
		// hiding the column "Id";
		cols.stream()
			.filter(e->e.getHeaderCaption().equals("Id"))
			.forEach(e->e.setHidden(true));
		
				
		logger.info("grid populated");
		logger.info("host is:{}",hostName);
				
		hostGrid.setSelectionMode(SelectionMode.SINGLE);
		hostGrid.setHeightByRows(4);
		hostGrid.setVisible(true);
		
		for(Column column:cols)
		{
			String name = column.getHeaderCaption();
			hostGrid.getDefaultHeaderRow().getCell(column.getPropertyId()).setHtml("<b>"+name+"</b>");
		}
		

	}
@SuppressWarnings("deprecation")
private void OsComboBoxPopulate()  
{
				
		osComboBox.removeAllItems();
		
		List<String> hostOs = hostTypeRepository.findUniqueHostOs();
				
		logger.info("Unique host selected is:{}",hostOs);
		
		hostOs.forEach((os)-> osComboBox.addItem(os)); // adding os name to combo box
				
		osComboBox.setNullSelectionAllowed(false);
		osComboBox.setValue(osComboBox.getItemIds().iterator().next());
				
		logger.info("OS in combox box:{}",osComboBox.getValue());
				
		String selOs = osComboBox.getValue().toString();
		
		populateHostName(selOs);
		
		osComboBox.addListener(new Property.ValueChangeListener() {

	        @Override
	        public void valueChange(ValueChangeEvent event) {
	        	hostList.removeAllItems();
	        	
	        	logger.info("OS in combox box:{}",osComboBox.getValue());
	        	populateHostName(osComboBox.getValue().toString());
	        
	        }   
	    });   
		
}
/**
 * This method is used to populate the comboBox
 * @param selOs the OS name for which the host name to be displayed
 */
private void populateHostName(String selOs) {
		
	List<String> osName = hostTypeRepository.findHostNameByHostOs(selOs);
	
	hostList.addItems(osName);
	
	hostList.setValue(hostList.getItemIds().iterator().next());
	
	logger.info("OS:[{}],Host:{},ListBox:{}",selOs,osName,hostList.getItemIds().toString());
	
}


@Override
public void enter(ViewChangeEvent event) {
	// TODO Auto-generated method stub
	
}
	

}

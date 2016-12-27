/**
 * 
 */
package com.emc.qe.cira.ui.design;

import static java.util.stream.Collectors.*;

import java.io.File;
import java.io.ObjectInputStream.GetField;
import java.nio.file.FileSystem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.emc.qe.cira.csv.results.CIResults;
import com.emc.qe.cira.model.JenkinsSEJob;
import com.emc.qe.cira.repository.HostTypeRepository;
import com.emc.qe.cira.repository.JenkinsSEJobRepository;
import com.emc.qe.cira.ui.validator.ResultsPageValidation;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.CellReference;
import com.vaadin.ui.Grid.CellStyleGenerator;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.button.*;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import au.com.bytecode.opencsv.CSVReader;
import ch.qos.logback.core.Layout;

/**
 * @author bankar
 *
 */
@UIScope
@SpringView(name = ResultsDashBoard.VIEW_NAME)
public class ResultsDashBoard extends VerticalLayout implements View{
	
	final static  String VIEW_NAME = "ResultsDashBoard";
	protected FormLayout formLayout;
	protected HorizontalLayout layoutHorizontal;
	protected CheckBox resultsTypeCiCheckBox;
	protected CheckBox resultsTypeCdCheckBox;
	protected OptionGroup ciTypeOptionGroup;
	protected ComboBox cdCycleComboBox;
	protected ComboBox osTypeComboBox;
	protected ComboBox buildNumberComboBox;
	protected Grid resultsGrid;
	final protected GridLayout gridLayout  = new GridLayout(2,1);
	protected  Button button;
	
		
	@Autowired 
	private JenkinsSEJobRepository jenkinsSeRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ResultsDashBoard.class);
	
	
	@PostConstruct
	void init()
	{
		
		setSpacing(true);
		setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
							
		setFormContents();
		
		resultsTypeCiCheckBox.addValueChangeListener(event->{
			
			changeVisibility_afterCIChecked(resultsTypeCiCheckBox.getValue());
			//resultsTypeCdCheckBox.setValue(!resultsTypeCiCheckBox.getValue());
		});
		
				
		
	// event fired when checkbox value changes			
		resultsTypeCdCheckBox.addValueChangeListener(event->{
			
			changeVisibility_afterCDChecked(resultsTypeCdCheckBox.getValue());
			
		});
			
		if(resultsTypeCiCheckBox.getValue())
		{
			logger.info("ci cb value:{}",resultsTypeCiCheckBox.getValue());
			ciTypeOptionGroup.addValueChangeListener(new ValueChangeListener() {
				
				@Override
				public void valueChange(ValueChangeEvent event) {
					// TODO Auto-generated method stub
					
					String citype = ciTypeOptionGroup.getValue().toString();
					logger.info("ci type changed to:{}",citype);
					if(!citype.equals("N/A"))
						updateOsTypeComboBox(citype);
						//updateBuildNumberComboBox(citype,osTypeComboBox.getValue().toString());
					
				}
			});
		}
		
	
		
		button.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
											
				try{
					Boolean flag;
					if(resultsTypeCiCheckBox.getValue()){
						
						String osType = osTypeComboBox.getValue().toString();
						String ciType = ciTypeOptionGroup.getValue().toString();
						String buildNo= buildNumberComboBox.getValue().toString();
						flag = ResultsPageValidation.validateFileName("CI",osType,ciType,buildNo);
					}else{
						
						String cdCycle = cdCycleComboBox.getValue().toString();
						flag = ResultsPageValidation.validateFileName("CD_CYCLE",cdCycle);
					}
					if(flag)
					{
						validateParameters();
						resultsGrid.setVisible(true);
						setGridData(ResultsPageValidation.fileName);
					}
					else{
						throw new Exception("file not found");
					}
				}catch(Exception e){
					logger.error("welcome{}",e.getMessage());
					gridLayout.setVisible(false);
					resultsGrid.setVisible(false);
					
					Notification notification = new Notification("File Not Found","No result for selected CI/CD Cycle",Type.WARNING_MESSAGE);
					notification.setPosition(Position.MIDDLE_CENTER);
					notification.setDelayMsec(-1);
					notification.show(Page.getCurrent());
					return;
				}
				
				
			}

			private void validateParameters() {
				// TODO Auto-generated method stub
				if(resultsTypeCiCheckBox.getValue())
				{
					String ciType = ciTypeOptionGroup.getValue().toString();
					String osType = osTypeComboBox.getValue().toString();
					String buildNo= buildNumberComboBox.getValue().toString();
					
					String filepath = "C:\\learn\\java\\"+ciType+"\\"+osType+"\\"+buildNo;
					
					File f  = new File(filepath);
					if(!f.isDirectory()){
						
					}
					
				}
				
				
			}
		});
		
		
	ciTypeOptionGroup.setImmediate(true);
	
	
	}
	
		
	protected void setGridData(String fileName) {
		
		
		CIResults resultsTest = new CIResults(fileName);
		//generateFilePath();
		String ciType = null;
		String osType;
		String buildNo;
	
		
		 
		IndexedContainer container = new IndexedContainer();
		
		try {
			System.out.println(resultsTest.retrieveResults());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Map<String, String>>results = resultsTest.getData();
				
		Set<String> keys = results.iterator().next().keySet();
		
		addContainerItemProperty(container,keys);
		
		logger.info("headers:{}",keys);
		
		for(Map<String,String> data:results)
		{
			logger.info("data row:{}",data);
			Object itemId = container.addItem();
			Item item = container.getItem(itemId);
			for(Map.Entry<String,String> entry:data.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				item.getItemProperty(key).setValue(value);
			}
		}
			
		resultsGrid.setContainerDataSource(container);	
		
		
		
		
//		resultsGrid.getDefaultHeaderRow().getCell(runID.getPropertyId()).setHtml("<b>RUN ID</b>");
		
		List<Grid.Column> column = resultsGrid.getColumns();
		
		for(Grid.Column col:column)
		{
			String name = col.getHeaderCaption();
			resultsGrid.getDefaultHeaderRow().getCell(col.getPropertyId()).setHtml("<b>"+name+"</b>");
		}
		
		displaySummaryResults(resultsTest.getResultSummary());
		
		
	}


	private void displaySummaryResults(Map<String, Object> resultSummary) {
		// TODO Auto-generated method stub
		
		String color=null;
		Map<String,Object> results = resultSummary;
		
		//GridLayout gridLayout = new GridLayout(2,1);
		gridLayout.removeAllComponents();
		for(String keys : results.keySet())
		{
			if(keys.equals("failedtest"))
			{
				color="red";
			}
			String tmpKey = "<Font color=%(color)<b>"+keys+"</b></Font>";
			String tmpValue ="<font color=green><b>"+results.get(keys).toString()+"</b></font>";
			gridLayout.addComponent(new Label(tmpKey,ContentMode.HTML));
			gridLayout.addComponent(new Label(tmpValue,ContentMode.HTML));
			
		}
		gridLayout.setVisible(true);
		//addComponent(gridLayout);
	}


	private void addContainerItemProperty(IndexedContainer container, Set<String> keys) {
		// TODO Auto-generated method stub
		for(String header:keys)
		{
			container.addContainerProperty(header, String.class,null);//
		}
		
		
	}


	private void generateFilePath() {
		// TODO Auto-generated method stub
		
		String resultsType = resultsTypeCiCheckBox.getCaption();
		String osName = osTypeComboBox.getValue().toString();
		ciTypeOptionGroup.getItem("CI");
		
		
		
	}


	private void changeVisibility_afterCDChecked(Boolean value) {
		
		resultsTypeCiCheckBox.setValue(!value);
		if(value){
			ciTypeOptionGroup.setItemEnabled("N/A",true);
			ciTypeOptionGroup.setItemEnabled("CI",false);
			ciTypeOptionGroup.setItemEnabled("Nightly",false);
			
			//ciTypeOptionGroup.setEnabled(false);
			
			osTypeComboBox.removeAllItems();
			osTypeComboBox.setEnabled(!value);
			
			buildNumberComboBox.removeAllItems();
			buildNumberComboBox.setEnabled(!value);
			
			cdCycleComboBox.addItem("SE_CD_14_15");
			cdCycleComboBox.setEnabled(value);
			
			ciTypeOptionGroup.setValue("N/A");
		
		}
				
			
		
						
		
		
	}


	private void changeVisibility_afterCIChecked(Boolean value) {
		
		// TODO Auto-generated method stub
		
		resultsTypeCdCheckBox.setValue(!value);
		if(value)
		{
			ciTypeOptionGroup.setItemEnabled("N/A",false);
			ciTypeOptionGroup.setItemEnabled("CI",true);
			ciTypeOptionGroup.setItemEnabled("Nightly",true);
			ciTypeOptionGroup.setValue("CI");
			ciTypeOptionGroup.setEnabled(value);
			
			
			String citype = ciTypeOptionGroup.getValue().toString();
			updateOsTypeComboBox(citype);
			osTypeComboBox.setEnabled(value);
			
			updateBuildNumberComboBox(citype,osTypeComboBox.getValue().toString());
			buildNumberComboBox.setEnabled(value);
			
			
			cdCycleComboBox.setEnabled(!value);
			
			
		}
		
	}

	

	private void updateBuildNumberComboBox(String ciType, String osType) {
		// TODO Auto-generated method stub
		
		buildNumberComboBox.removeAllItems();
		List<JenkinsSEJob> buildNo = jenkinsSeRepository.findBuildNoByJobTypeAndHostOsOrderByBuildNoDesc(ciType,osType);
		
		System.out.println(buildNo.toString());
		buildNo.forEach(e->buildNumberComboBox.addItem(e.getBuildNo()));
		
		buildNumberComboBox.setValue(buildNumberComboBox.getItemIds().iterator().next());
		
		
		
		
		
			
	}


	private void setFormContents() {
		
		formLayout = new FormLayout();
		formLayout.setSpacing(true);
		layoutHorizontal = new HorizontalLayout();
		layoutHorizontal.setCaption("Results Type");
		layoutHorizontal.setStyleName(ValoTheme.LABEL_BOLD);
		layoutHorizontal.setSpacing(false);
	
		
		resultsTypeCiCheckBox = new CheckBox("CI");
		resultsTypeCiCheckBox.setWidth("147px");
		resultsTypeCiCheckBox.setValue(true);
		
		resultsTypeCdCheckBox = new CheckBox("CD Results Type");
		resultsTypeCdCheckBox.setWidth("178px");
		
		layoutHorizontal.addComponents(resultsTypeCiCheckBox,resultsTypeCdCheckBox);
		formLayout.addComponent(layoutHorizontal);
				
		ciTypeOptionGroup = new OptionGroup("CI Type");
		ciTypeOptionGroup.setStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		ciTypeOptionGroup.addItems("CI","Nightly","N/A");
		
		ciTypeOptionGroup.setVisible(true);
		ciTypeOptionGroup.setEnabled(true);
		ciTypeOptionGroup.setImmediate(true);
		ciTypeOptionGroup.setValue("CI");
		ciTypeOptionGroup.setItemEnabled("N/A",false);
		
		formLayout.addComponent(ciTypeOptionGroup);
		
		osTypeComboBox = new ComboBox("Select OS");
		osTypeComboBox.setStyleName(ValoTheme.COMBOBOX_ALIGN_CENTER);
		osTypeComboBox.setInputPrompt("Select OS");
		osTypeComboBox.setNullSelectionAllowed(false);
		osTypeComboBox.setVisible(true);
		osTypeComboBox.setEnabled(true);
		
		
		updateOsTypeComboBox(ciTypeOptionGroup.getValue().toString());
		
		
		
		
		formLayout.addComponent(osTypeComboBox);
		
		buildNumberComboBox = new ComboBox("Select Build");
		buildNumberComboBox.setStyleName(ValoTheme.COMBOBOX_ALIGN_CENTER);
		buildNumberComboBox.setFilteringMode(FilteringMode.CONTAINS);
		buildNumberComboBox.setInputPrompt("Select Build No");
		buildNumberComboBox.setNullSelectionAllowed(false);
		buildNumberComboBox.setVisible(true);
		buildNumberComboBox.setEnabled(true);
		updateBuildNumberComboBox("CI",osTypeComboBox.getValue().toString());
		formLayout.addComponent(buildNumberComboBox);
		
		cdCycleComboBox = new ComboBox("Select CD Cycle");
		cdCycleComboBox.setStyleName(ValoTheme.COMBOBOX_ALIGN_CENTER);
		cdCycleComboBox.setFilteringMode(FilteringMode.CONTAINS);
		cdCycleComboBox.setInputPrompt("Select CD Cycle");
		cdCycleComboBox.setNullSelectionAllowed(false);
		cdCycleComboBox.setVisible(true);
		cdCycleComboBox.setEnabled(false);
		
		formLayout.addComponent(cdCycleComboBox);
		
		button = new Button("Submit");
		button.setStyleName(ValoTheme.BUTTON_PRIMARY);
		formLayout.addComponent(button);
		
		
		addComponent(formLayout);
		
		
		gridLayout.setCaptionAsHtml(true);
		gridLayout.setCaption("<b><u><h1>Results Status</h1></u></b>");
		
		gridLayout.setVisible(false);
		addComponent(gridLayout);
		
		resultsGrid = new Grid();
		resultsGrid.setVisible(false);
		resultsGrid.setWidth("100%");
		resultsGrid.setCaptionAsHtml(true);
		resultsGrid.setCaption("<h1><b>Results DATA</b></h1>");
		resultsGrid.setSelectionMode(SelectionMode.SINGLE);
		
		addComponent(resultsGrid);
		
		
	}

	private void updateOsTypeComboBox(String citype) {
				
		
		List<JenkinsSEJob> job = jenkinsSeRepository.findHostOsByJobType(citype);
		
		
		osTypeComboBox.removeAllItems();
		
		job.forEach(e->logger.info("os:{}",e.getHostOs()));
		job.forEach(os->osTypeComboBox.addItem(os.getHostOs()));
		
		osTypeComboBox.setValue(osTypeComboBox.getItemIds().iterator().next());
		
	}


	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
		
	}
	

}

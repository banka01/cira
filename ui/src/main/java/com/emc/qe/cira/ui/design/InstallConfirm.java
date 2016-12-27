package com.emc.qe.cira.ui.design;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.Sizeable;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;


public class InstallConfirm extends Window{
	
	Window mywindow; // window for displaying confirmation
	Window mainWindow;
	private VerticalLayout layout;
	Label l;
	private static final Logger logger = LoggerFactory.getLogger(InstallConfirm.class);
	
	public InstallConfirm(String hostName,String version)
	{
				
		super("Install Confirmation");
		center();
			
		setupLayout();
		addLabel(hostName,version);
		addActionButton();
		
		setSizeUndefined();
		
		setModal(true);
		setClosable(false);
		setResizable(false);
				
	}

	private void addActionButton() {
		HorizontalLayout buttonLayout = new HorizontalLayout();
		
		buttonLayout.setSpacing(true);
		
		Button okButton = new Button("OK");
		Button cancelButton = new Button("Cancel");
		
		
		okButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		cancelButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		okButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
					
			close();
				
				
			}
		});
		
		cancelButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				
				close();
			}
		});
		
		buttonLayout.addComponents(okButton,cancelButton);
		buttonLayout.setComponentAlignment(okButton,Alignment.BOTTOM_LEFT);
		buttonLayout.setComponentAlignment(cancelButton,Alignment.BOTTOM_RIGHT);
		layout.addComponent(buttonLayout);
		
	}

	protected void dostuff() {
		// TODO Auto-generated method stub
		
		logger.info("pressed ok\n");
		
	}

	private void addLabel(String hostName,String version) {
		// TODO Auto-generated method stub
		Label label1 = new Label();
		label1.setCaption("Do you want to continue Installation");
		label1.addStyleName(ValoTheme.LABEL_BOLD);
		
		Label label2 = new Label();
		String text = "Host is " + hostName + " Version is " + version;
		label2.setCaption(text);
		layout.addComponents(label1,label2);
		
	}

	private void setupLayout() {
		// TODO Auto-generated method stub
		layout = new VerticalLayout();
		layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		layout.setMargin(true);
		layout.setSizeUndefined();
		setContent(layout);
		
	}
	
}

package com.emc.qe.cira.ui.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.emc.qe.cira.utilities.Connection;
import com.jcraft.jsch.JSchException;
import com.vaadin.annotations.Push;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
@Push
public class Install extends Thread{

	volatile double current = 0.0;
	private String hostName;
	private String version;
	ProgressBar progress;
	Label status;
	Label content;
	Connection conn;
	int cmdStatus;
	
	private static final Logger logger = LoggerFactory.getLogger(Install.class);
	
	
	public Install(String host,String version,ProgressBar installProgress,Label status) throws Exception
	{
		this.hostName=host;
		this.version=version;
		this.progress= installProgress;
		this.status=status;
		
		conn = new Connection(host,"abcd");
		try {
			conn.connect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			Notification notification = new Notification("Could not Connect to Host","No ssh installed or issue withe Host",Type.ERROR_MESSAGE);
			notification.setPosition(Position.MIDDLE_CENTER);
			notification.setDelayMsec(-1);
			notification.show(Page.getCurrent());
			throw new Exception("Connect Error");
			
		}
		
			
	}
	
	@Override

	public void run()
	{		
		
		conn.start();
		cmdStatus = conn.getStatus();
		status.setStyleName(ValoTheme.LABEL_BOLD);
		status.setContentMode(ContentMode.HTML);
					
		while(cmdStatus < 0){
			
			current+=0.03;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			UI.getCurrent().access(new Runnable() {
				
				@Override
				public void run() {
					
					// TODO Auto-generated method stub
					
					progress.setValue(new Float(current));
					status.setValue("Installing..."+
							(current*100)+"%");
											
					cmdStatus=conn.getStatus();
					
				}
			});
		}
		
		UI.getCurrent().access(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				progress.setValue(new Float(current));
				if(cmdStatus > 0)
				{
					progress.setValue(new Float(current));
					status.setValue("Failed "+ 
									(current*100)+"%"+
									 conn.getError().toString()+
									"<Font color=red><b>FAILED</b></Font>"	
							);
										
				}
				else{
					current=1.0;
					status.setValue("Completed "+(current*100)+"%"+
									conn.getOutput()+
									"<Font color=green>Successful</Font>"
							);
									
				}
				
			}
		});
		
		
//		InstallConfirm sub = new InstallConfirm(hostName,version);
//		
//		
//		UI.getCurrent().addWindow(sub);
		
	}

		
		 	
}

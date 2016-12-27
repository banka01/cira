package com.emc.qe.cira.utilities;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SystemPropertyUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import ch.qos.logback.core.net.SyslogOutputStream;

public class Connection extends Thread {
	
	private static final Logger logger = LoggerFactory.getLogger(Connection.class);
	
	public static int value = 0;
	private JSch jsch = new JSch();
	private Session session;
	private Channel channel;
	private String hostName;
	private String userName;
	private String passWord;
	private Integer port;
	private int status=-1;
	static Properties prop;
	
	private OutputStream err=null;
	private ByteArrayOutputStream cmdOutput=null;
	private String output;
	private String cmd;
	
	public OutputStream getError()
	{
		return err;
		
	}
	
	public String getOutput()
	{
		return output;
	}
	final String script = "ls ";//"perl /toc/perlexec/se_installer_ni.pl -kit ";
	
	static{
		 prop = new Properties();
		InputStream in = Connection.class.getClassLoader().getResourceAsStream("cira-host.properties");
		try {
			
			prop.load(in);
			
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Connection(String hostName,String command)
	{
	
		this.hostName = hostName;
		this.userName = prop.getProperty("host.username");
		this.passWord = prop.getProperty("host.password");
		this.cmd = command;
		this.port = 22;
		
	}
	public Connection(String host,String user,String password) 
	{
		
		this.hostName = host;
		this.userName = user;
		this.passWord = password;
	}
	public void connect() throws JSchException
	{
		
		Properties config = new Properties();
	    config.put("StrictHostKeyChecking", "no");
	    
	    this.session = jsch.getSession(userName,hostName,port);
	    this.session.setPassword(passWord);
	    this.session.setConfig(config);
	    logger.info("Establishing Connection");
	    this.session.connect();
	    logger.info("Connected");
	}

	@Override
	public void run()
	{
		
		
		err = new ByteArrayOutputStream();
		cmdOutput = new ByteArrayOutputStream();
	    System.setErr(new PrintStream(err));
	    
		try {
			this.channel = session.openChannel("exec");
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    String cmd = script.concat(this.cmd);
	    logger.info("running command:{}",cmd);
	    
	    ((ChannelExec)this.channel).setCommand(cmd);
	    
	    this.channel.setInputStream(null);
	    
	    ((ChannelExec)this.channel).setErrStream(System.err);
	    
	   
	    InputStream in = null;
		try {
			in = channel.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
				
		try {
			this.channel.connect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			logger.error("Connect error:{}",hostName);
			e.printStackTrace();
		}
		
		// reading from the remote machine.    	    
	    byte[] buffer=new byte[1024];
	    int len;
	    while(true)
	    {	    	
	    	try {
				while((len=in.read(buffer,0,1024))>-1){
								
					output=buffer.toString();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	if(this.channel.isClosed()){
	    		try {
					if(in.available()>0) continue;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    logger.info("exit status:{}"+this.channel.getExitStatus());
	              
          this.status= this.channel.getExitStatus();
          break;
          
	    }
	    
		    
	    logger.error(err.toString());
	    logger.info(output);
	    
	    disconnect();
	   
	    System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		
	}
	public int runCommand(String version) throws JSchException, IOException
	{
		
		this.channel = session.openChannel("exec");
	    
	    String cmd = script.concat(version);
	    logger.info("running command:{}",cmd);
	    
	    ((ChannelExec)this.channel).setCommand(cmd);
	    
	    this.channel.setInputStream(null);
	    
	    ((ChannelExec)this.channel).setErrStream(System.err);
	    	 
	    InputStream in = channel.getInputStream();
	    
	    this.channel.connect();
	    	    
	    byte[] tmp=new byte[1024];
	      while(true)
	      {
	        while(in.available()>0)
	        {
	          int i=in.read(tmp, 0, 1024);
	          if(i<0)break;
	          System.out.print(new String(tmp, 0, i));
	        }
	        if(this.channel.isClosed())
	        {
	          if(in.available()>0) continue; 
	          System.out.println("exit-status: "+this.channel.getExitStatus());
	          
	          status= this.channel.getExitStatus();
	          break;
	          
	        }
	        try
	        {
	        	Thread.sleep(1000);
	        }catch(Exception ee){}
	      }
	      
	      return status;
	}
	
	public void disconnect()
	{
		this.channel.disconnect();
	    this.session.disconnect();
	}
	public int installSolutionEnabler(String version) {
		// TODO Auto-generated method stub
		Integer status = null;
		try {
			status = runCommand(version);
		} catch (JSchException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
		
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}

package com.emc.qe.cira.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


public class RemoteConnection {

	public static void main(String args[]) throws IOException, JSchException {
				
	       
		String sshHost = "dlqa1155.lss.emc.com";
		String sshUser = "root";
		String sshPassword = "remember";
		Integer port = 22;
		
		String localHost = "127.0.0.1";
		
		String targetHost = "dlqa1156.lss.emc.com";
		String targetUser = "root";
		String targetPassword = "remember";
		Integer targetPort = 22;
		
	    JSch jsch = new JSch();
	    
	    Properties config = new Properties();
	    config.put("StrictHostKeyChecking", "no");
	    
	            
	    Session session = jsch.getSession(sshUser,sshHost,port);
	    session.setPassword(sshPassword);
	    session.setConfig(config);
	    System.out.println("Establishing Connection");
	    session.connect();
	    System.out.println("connected");

//	    Integer port1 = session.setPortForwardingL(0,targetHost,targetPort);
//	     
//	    System.out.println(port1);
//	    
//	    Session targetSession = jsch.getSession(targetUser, localHost,port1);
//	    targetSession.setPassword(targetPassword);
//	    targetSession.setConfig(config);
//	    targetSession.connect();
//	    System.out.println("connected");
	
	   // Channel channel = targetSession.openChannel("exec");
	    Channel channel = session.openChannel("exec");
	    //channel.setOutputStream(System.out);
	    ((ChannelExec)channel).setCommand("pwd");
	    
	    channel.setInputStream(null);
	    ((ChannelExec)channel).setErrStream(System.err);
	    
	 // Get the input stream
	    InputStream in = channel.getInputStream();
	    // Connect
	    channel.connect();
	    
	    
	    byte[] tmp=new byte[1024];
	      while(true){
	        while(in.available()>0){
	          int i=in.read(tmp, 0, 1024);
	          if(i<0)break;
	          System.out.print(new String(tmp, 0, i));
	        }
	        if(channel.isClosed()){
	          if(in.available()>0) continue; 
	          System.out.println("exit-status: "+channel.getExitStatus());
	          break;
	        }
	        try{Thread.sleep(1000);}catch(Exception ee){}
	      }
	      channel.disconnect();
	      session.disconnect();
	    


	    
	   
	    }
}


package com.emc.qe.cira.utilities;

import com.jcraft.jsch.JSchException;

public class MainThread {

	public static void main(String[] args) throws JSchException {
		
		System.out.println("Calling Main");
		
//		
		
		Connection c1;// = new Connection("dlqa1155");
		
		//c1.connect();
		
		A a1 = new A();// = new A(c1);
		a1.start();
		//c1.start();
		
		
		
		

	}

}

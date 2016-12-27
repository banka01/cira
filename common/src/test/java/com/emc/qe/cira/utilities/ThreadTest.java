package com.emc.qe.cira.utilities;

import java.awt.List;

import com.jcraft.jsch.JSchException;
class B extends Thread
{
	public   int status;
	public void run()
	{
		for(int i=0;i<10;i++)
		{			
			System.out.println("print in B "+i);
			System.out.println("Thread is: "+Thread.currentThread().getId());
			status=i;
			
		}
	}
}

class A extends Thread{
	
	Connection conn;
	public static int value=0;
	int count;
	public A(Connection c1) {
		// TODO Auto-generated constructor stub
		conn=c1;
		try {
			conn.connect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public A() {
		// TODO Auto-generated constructor stub
		
		conn = new Connection("dlqa1155","abcd");
		try {
			conn.connect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run()
	{
		count=0;
		
		//b=new B();
		
		//b.start();
		
		conn.start();
		while(count<10)
		{
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//if(b.isAlive())
			if(conn.isAlive())
			{
				count=conn.getStatus();
				//System.out.println("B is alive. "+ b.status);
				System.out.println("Conn is alive. "+ count);
				System.out.println("Thread is: "+Thread.currentThread().getId());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn.getStatus() < 0)
			{
				//value=b.status;
				value=conn.getStatus();
				System.out.println(conn.getError());
				System.out.println("B is final. "+ value);
							
				//Thread.currentThread().interrupt();
			}
			else
			{
				if(conn.getStatus() >0 || conn.getStatus() == 0)
				{
					count=11;
					System.out.println("conn is final. "+ conn.getStatus());
				}
			}
		}
		
		
		conn.disconnect();
		

		
		
	}
}






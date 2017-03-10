package com.mobius.searchCalls.util;


import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.hadoop.fs.FileSystem;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public  class WaitBox {

	private static Logger logger = LogManager.getLogger(WaitBox.class); 

	private	AtomicBoolean block = new AtomicBoolean(false);
	private	ReentrantLock reenLock  = new ReentrantLock();
	private	Condition currentDateCondition = reenLock.newCondition();
	private Condition waitingCondition = reenLock.newCondition();
	
	private FileSystem fileSys;
	private static WaitBox wb;
	
	
	
	
	
	private  WaitBox(FileSystem fileSys){
		this.fileSys = fileSys;
	}
	
	public static WaitBox getWaitBox(FileSystem fileSys){
		if(wb == null){
			synchronized (WaitBox.class) {
				if(wb == null)wb = new WaitBox(fileSys);		
			}
		}
		return wb;
	}
	
	
	
	
	public void notifyFraudDect(){
		try{
			if(block.get()){
				
				
				if(block.get()){
					
					block.set(false);

					currentDateCondition.signalAll();
					
				}
			}
		}finally{
			reenLock.unlock();
		}
	}
	
	public boolean  isBlock(){
		return block.get();
	}
	
	
	public void idleForFDBlock(){
		try{
			reenLock.lock();
			
			waitingCondition.await();
			
			
		} catch (InterruptedException e) {
			System.out.println("");
			
		}finally{
			reenLock.unlock();
		}
	}
	
}

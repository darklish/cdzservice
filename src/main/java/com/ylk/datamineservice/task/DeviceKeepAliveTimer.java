package com.ylk.datamineservice.task;

import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.Timer;

import com.ylk.datamineservice.event.DeviceOfflineEvent;

public class DeviceKeepAliveTimer {
	public Timer timer = new Timer();
	
	
	class DeviceKeepAliveTask implements TimerTask{
		String deviceNo;
    	public DeviceKeepAliveTask(String deviceNo){
    		this.deviceNo = deviceNo; 
    	}
		@Override
		public void run(Timeout timeout) throws Exception {
			//设备离线
			System.out.println("DEVICEnO:{}"+deviceNo);
		}
		
	}
}

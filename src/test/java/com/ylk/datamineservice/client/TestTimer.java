package com.ylk.datamineservice.client;

import java.util.concurrent.TimeUnit;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

public class TestTimer {
	public static void main(String[] args) {
		HashedWheelTimer hashTimer = new HashedWheelTimer();
		TestTask task = new TestTask();
		
		hashTimer.newTimeout(task, 10000, TimeUnit.MILLISECONDS);
		Timeout timeout = hashTimer.newTimeout(task, 10000, TimeUnit.MILLISECONDS);
		try {
			task.run(timeout);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static class TestTask implements TimerTask{

		@Override
		public void run(Timeout timeout) throws Exception {
			System.out.println("time out");
		}
		
	}

}

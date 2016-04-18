package com.ylk.datamineservice.client;

import java.util.Date;
import java.util.TimerTask;
public class CommonTask extends TimerTask {
 private String name = "";
 public CommonTask(){ }
 public CommonTask(String name){
  this.name = name;
 }
 @Override
 public void run() {
  System.out.println("我是任务  " + this.name

                     + " 被执行了，执行时间为 " + new Date(this.scheduledExecutionTime()));
 }
 @Override
 public boolean cancel() {
  System.out.println("我是任务 " + this.name

                      + " 被取消了，取消时间为 " + new Date());
  return super.cancel();
 }
}




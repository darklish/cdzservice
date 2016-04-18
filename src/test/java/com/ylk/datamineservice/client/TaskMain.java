package com.ylk.datamineservice.client;

import java.util.Date;
import java.util.Timer;
public class TaskMain {
 /**
  * @param args
  */
 public static void main(String[] args){
  Date crtTime = new Date();
  long crt = crtTime.getTime();
  Timer timer = new Timer();
  
  /*// 在指定时间执行
  CommonTask task1 = new CommonTask("【任务一】"); 
  timer.schedule(task1, new Date(crt + 1000));
  
  // 俩秒后执行
  CommonTask task2 = new CommonTask("【任务二】"); 
  timer.schedule(task2, 2000);
  
  // 指定时间开始执行，后重复一秒执行一次
  CommonTask task3 = new CommonTask("【任务三】"); 
  timer.schedule(task3, new Date(crt + 3000), 1000);
  
  // 四秒后开始执行，后重复一秒执行一次
  CommonTask task4 = new CommonTask("【任务四】"); 
  timer.schedule(task4, 4000,1000);
  
  // 指定时间开始执行，后重复一秒执行一次
  CommonTask task5 = new CommonTask("【任务五】"); 
  timer.scheduleAtFixedRate(task5, new Date(crt + 5000), 1000);
  
  // 六秒后开始执行，后重复一秒执行一次
  CommonTask task6 = new CommonTask("【任务六】"); 
  timer.scheduleAtFixedRate(task6, 6000,1000);*/
  
  // 取消任务
  CommonTask task7 = new CommonTask("【任务七】"); 
  timer.schedule(task7, new Date(crt + 7000));
  task7.cancel();
  CommonTask task8 = new CommonTask("【任务八】"); 
  timer.schedule(task8, 8000);
  task8.cancel();
  // 从此计时器的任务队列中移除所有已取消的任务。
  timer.purge();
  
  // schedule和scheduleAtFixedRate 
 /* CommonTask task9 = new CommonTask("【任务九】"); 
  timer.schedule(task9, new Date(crt - 10 * 1000),1000);
  CommonTask task10 = new CommonTask("【任务十】"); 
  timer.scheduleAtFixedRate(task10, new Date(crt - 10 * 1000),1000);*/
  
  // 终止此计时器，丢弃所有当前已安排的任务。
  //timer.cancel();
 }
}
package com.ylk.datamineservice.event;

public class DeviceOfflineEvent {
	private String cdzNo;
	public DeviceOfflineEvent() {
		
	}
	public DeviceOfflineEvent(String cdzNo) {
		this.cdzNo = cdzNo;
	}
	public String getCdzNo() {
		return cdzNo;
	}
	public void setCdzNo(String cdzNo) {
		this.cdzNo = cdzNo;
	}
	
	

}

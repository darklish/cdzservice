package com.ylk.datamineservice.event;

public class CommonFrameEvent {
	private String cdzNo;
	
	public CommonFrameEvent(String cdzNo) {
		this.cdzNo = cdzNo;
	}

	public String getCdzNo() {
		return cdzNo;
	}

	public void setCdzNo(String cdzNo) {
		this.cdzNo = cdzNo;
	}
}

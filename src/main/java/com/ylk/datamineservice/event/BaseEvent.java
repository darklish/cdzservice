package com.ylk.datamineservice.event;

import io.netty.channel.ChannelHandlerContext;

import com.ylk.datamineservice.msg.ReqBaseMsg;

public abstract class BaseEvent {
	protected ReqBaseMsg baseMsg;
	public ChannelHandlerContext handlerContext;
	
	public BaseEvent(ReqBaseMsg baseMsg,ChannelHandlerContext handlerContext) {
		this.baseMsg = baseMsg;
		this.handlerContext = handlerContext;
		
	}
	// 唯一确定消息的源地址
	public String getMessageId() {
		return baseMsg.getMsgKey();
	}
	public ReqBaseMsg getBaseMsg() {
		return baseMsg;
	}
	
	
}

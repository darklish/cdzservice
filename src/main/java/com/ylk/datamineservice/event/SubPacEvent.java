package com.ylk.datamineservice.event;

import io.netty.channel.ChannelHandlerContext;

import com.ylk.datamineservice.msg.ReqBaseMsg;

public class SubPacEvent extends BaseEvent {
	
	public SubPacEvent(ReqBaseMsg baseMsg,ChannelHandlerContext handlerContext) {
		super(baseMsg, handlerContext);
	}
	


}

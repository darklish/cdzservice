package com.ylk.datamineservice.event;

import io.netty.channel.ChannelHandlerContext;

import com.ylk.datamineservice.msg.ReqBaseMsg;

public class CompletePacEvent extends BaseEvent {

	public CompletePacEvent(ReqBaseMsg baseMsg,ChannelHandlerContext handlerContext) {
		super(baseMsg,handlerContext);
	}

}

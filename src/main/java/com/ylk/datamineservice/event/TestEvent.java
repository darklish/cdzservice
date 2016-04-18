package com.ylk.datamineservice.event;

import io.netty.channel.ChannelHandlerContext;

public class TestEvent {
	private final int message;
	public ChannelHandlerContext handlerContext;
    public TestEvent(int message,ChannelHandlerContext handlerContext) {        
        this.message = message;
        this.handlerContext = handlerContext;
        System.out.println("event message:"+message);
    }
    public int getMessage() {
        return message;
    }
}

package com.ylk.datamineservice.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylk.datamineservice.event.TestEvent;
import com.ylk.datamineservice.service.impl.MessageServiceImpl;
@Service
public class TCPServerHandler extends SimpleChannelInboundHandler {
	
	@Resource
	private MessageServiceImpl messageService;
	
	public TCPServerHandler() {
		
	}
	
	public TCPServerHandler (MessageServiceImpl msgService) {
		this.messageService = msgService;
	}
	

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object arg1)
			throws Exception {
		System.out.println(arg1);
		
		
		messageService.getEventbus().post(new TestEvent(1,ctx));
		
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
	}

	
	
	

}

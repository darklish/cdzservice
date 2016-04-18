package com.ylk.datamineservice.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println(msg);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf encoded = ctx.alloc().buffer(4);
		encoded.writeInt(4);
		ctx.writeAndFlush(encoded);
		super.channelActive(ctx);
	}
	
	

}

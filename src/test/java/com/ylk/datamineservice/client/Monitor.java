package com.ylk.datamineservice.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Monitor {
	
	private final EventLoopGroup group;
	
	private Bootstrap bootStrap;
	
	public Monitor() {
		group = new NioEventLoopGroup();
		bootStrap = new Bootstrap();
		bootStrap.group(group).channel(NioDatagramChannel.class)
		.option(ChannelOption.SO_BROADCAST, true)
		.handler(new ChannelInitializer<DatagramChannel>(){
			@Override
			protected void initChannel(DatagramChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast(new LoggingHandler(LogLevel.DEBUG))
				.addLast("handler", new MonitorHandler());
			}
         }).localAddress(8082);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Monitor monitor = new Monitor();
		Channel ch = monitor.bind();
		try {
			ch.closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Channel bind() {
		return bootStrap.bind().syncUninterruptibly().channel();
		
	}

}

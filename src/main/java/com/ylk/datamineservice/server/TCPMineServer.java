package com.ylk.datamineservice.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylk.datamineservice.handler.TCPServerHandler;
import com.ylk.datamineservice.service.MessageService;
import com.ylk.datamineservice.service.impl.MessageServiceImpl;


public class TCPMineServer {
	Logger logger = LoggerFactory.getLogger(TCPMineServer.class);
	private int port = 8082;
	//客户端的Keepalive时间间隔是5分钟
	private int keepAliveTimeoutSeconds = 300;
	ServerBootstrap b ;
	private ChannelFuture cf;
	
	private boolean printNetworkLog = false;
	
	public static final int DEFAULT_POOL_SIZE = Runtime.getRuntime().availableProcessors();
	
	@Resource
	private MessageServiceImpl messageService;
	/**
	 * 初始化TCPServer 
	 */
	@PostConstruct
	public void init(){
		logger.info("启动 Message TCP Server...");
		b = new ServerBootstrap();
        try {
        	//接收TCP连接线程，最大为8个
            EventLoopGroup boss = new NioEventLoopGroup( Math.min(DEFAULT_POOL_SIZE, 8));
            //处理网络调用线程，为CPU个数，我们的操作大部分为异步过程
            //所以CPU个数个工作线程应该已经够了
            EventLoopGroup worker = new NioEventLoopGroup(DEFAULT_POOL_SIZE);
            b.group(boss, worker)
            // 在程序中设置backlog和sotime并不是十分必要，使用操作系统的缺省参数会更好
            //.option(ChannelOption.SO_BACKLOG, 2500)
            //.option(ChannelOption.SO_TIMEOUT, 5000)
             .childOption(ChannelOption.TCP_NODELAY, true)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
						System.out.println(messageService.hashCode());
			        	pipeline.addLast("readtimeout", new IdleStateHandler(0,0,keepAliveTimeoutSeconds))
			        	.addLast("decode", new ByteArrayDecoder())
			        	.addLast("handler", new TCPServerHandler(messageService));
				}
             });
            
            cf = b.bind(port).sync();
            logger.info("启动Message TCPServer 完成，在以下地址监听：" + "*:" + port);
        }catch(Exception e){
        	logger.error("启动Message TCPServer失败",e);
        }
	}
	
	
	// ===================== java :( ==================
	public void setPort(int port) {
		this.port = port;
	}


	public MessageService getMessageService() {
		return messageService;
	}


	public void setMessageService(MessageServiceImpl messageService) {
		this.messageService = messageService;
	}
	
	

}

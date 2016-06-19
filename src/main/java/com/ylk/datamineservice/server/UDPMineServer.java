package com.ylk.datamineservice.server;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.HashedWheelTimer;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ylk.datamineservice.frame.BroadCastFrame;
import com.ylk.datamineservice.handler.UDPServerHandler;
import com.ylk.datamineservice.handler.coder.BroadCastEncoder;
import com.ylk.datamineservice.handler.coder.PacketDecoder;

@Service
@Scope("singleton")
public class UDPMineServer {
	Logger logger = LoggerFactory.getLogger(TCPMineServer.class);
	private int port = 8082;
	
	private int modulePort = 8093;
	Bootstrap b ;
	private ChannelFuture cf;
	
	private boolean printNetworkLog = false;
	
	public static final int DEFAULT_POOL_SIZE = Runtime.getRuntime().availableProcessors();
	
	@Resource
	private UDPServerHandler uDPServerHandler;
	
	
	
	private Timer timer;
	
	
	private int keepaliveTimer = 3000;//服务端定时发送心跳广播信息,毫秒
	/**
	 * 初始化TCPServer
	 */
	@PostConstruct
	public void init(){
		timer = new Timer();
		logger.info("启动UDP Server");
		b = new Bootstrap();
        try {
            b.group(new NioEventLoopGroup(4))
             .channel(NioDatagramChannel.class)
             .option(ChannelOption.SO_BROADCAST, true)
             .handler(new ChannelInitializer<DatagramChannel>(){
				@Override
				protected void initChannel(DatagramChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast(new LoggingHandler(LogLevel.INFO))
					.addLast("decoder", new PacketDecoder())
					.addLast("broadcast",new BroadCastEncoder(new InetSocketAddress("255.255.255.255",modulePort)))
		        	.addLast("handler", uDPServerHandler);
				}
             });
            cf = b.bind(port).sync();
            logger.info("启动UDP Server完成，在以下地址监听：" + "*:" + "8082");
        }catch(Exception e){
        	logger.error("启动UDP Server失败",e);
        }
        uDPServerHandler.dealAllCdz();
		//timer.schedule(new ServerState(), 10000, keepaliveTimer);
	}
	
	public class ServerState extends TimerTask{
		@Override
		public void run() {
			logger.info("服务器发送心跳信息!");
			BroadCastFrame frame = new BroadCastFrame();
			cf.channel().writeAndFlush(frame);
		}
	}
	
	// ===================== java :( ==================
	public void setPort(int port) {
		this.port = port;
	}


}

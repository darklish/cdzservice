package com.ylk.datamineservice.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;


public class MsgTCPClient {

    private final String host;
    private final int port;
    private String uid;
    private String token;

    public MsgTCPClient(String host, int port, String uid, String token) {
        this.host = host;
        this.port = port;
        this.uid = uid;
        this.token = token;
    }

    public static void main(String[] args){
        Bootstrap b = new Bootstrap();
        try {
            b.group(new NioEventLoopGroup())
             .channel(NioSocketChannel.class)
             .handler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
			        ChannelPipeline pipeline = ch.pipeline();
			        
			        pipeline.addLast(new IdleStateHandler(0, 0, 1 * 60));
			        pipeline.addLast(new ClientHandler());
				}
             });

            // Make a new connection.
            try {
				ChannelFuture f = b.connect("127.0.0.1", 8082).sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

            
        } finally {
//            b.shutdown();
        }
    }
    
    
    
    
}

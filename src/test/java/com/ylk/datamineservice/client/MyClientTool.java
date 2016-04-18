package com.ylk.datamineservice.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import com.ylk.datamineservice.util.BCDUtil;
import com.ylk.datamineservice.util.ByteUtil;

/**
 * Stat Server客户端工具
 */
public class MyClientTool {
	
	private int port = 8082 ;
	private String server = "127.0.0.1";

    public MyClientTool(int port) {
        this.port = port;
    }
    public void run() throws Exception {
    	DatagramSocket sendSocket = new DatagramSocket();  
    	 

    	ByteBuffer buf = ByteBuffer.allocate(26);
    	
		
		buf.put(BCDUtil.hexStringToBytes("88"));
		buf.put(BCDUtil.hexStringToBytes("10"));
		buf.put(BCDUtil.hexStringToBytes("01"));
		buf.put(BCDUtil.hexStringToBytes("F0"));
		buf.put(BCDUtil.hexStringToBytes("01"));
		buf.put(BCDUtil.hexStringToBytes("00"));
		buf.put(BCDUtil.hexStringToBytes("06"));
		buf.put(ByteUtil.getIntBytes(12346789, 0));
		byte[] bb = ByteUtil.getIntBytes(123456, 0);
		buf.put(bb[0]);
		buf.put(bb[1]);
		
		buf.put(BCDUtil.hexStringToBytes("88"));
		buf.put(BCDUtil.hexStringToBytes("10"));
		buf.put(BCDUtil.hexStringToBytes("01"));
		buf.put(BCDUtil.hexStringToBytes("F0"));
		buf.put(BCDUtil.hexStringToBytes("01"));
		buf.put(BCDUtil.hexStringToBytes("01"));
		buf.put(BCDUtil.hexStringToBytes("02"));
		buf.put(bb[2]);
		buf.put(bb[3]);
		
		byte[] bytes = buf.array();

		// transfer bytes from this buffer into the given destination array
 
        // 确定发送方的IP地址及端口号，地址为本地机器地址  
        int port = 8082;  
        InetAddress ip = InetAddress.getLocalHost();  
 
        DatagramPacket sendPacket = new DatagramPacket(bytes,bytes.length,ip,port);
 
        // 通过套接字发送数据：  
        sendSocket.send(sendPacket);
 
        sendSocket.close();
        /*Bootstrap b = new Bootstrap();
        try {
            b.group(new NioEventLoopGroup())
            .channel(NioDatagramChannel.class)
            .handler(new MsgClientHandler());;

            Channel ch = b.bind(0).sync().channel();
            sendPacket(ch);
            if (!ch.closeFuture().await(5000)) {
                System.err.println("等待服务器返回包超时");
            }
        } finally {
            //b.shutdown();
        }*/
    }

    private void sendPacket(Channel ch){
    	System.out.println("发送心跳数据包");
		ByteBuffer buf = ByteBuffer.allocate(50);
		String data = "11";
		
		buf.put(BCDUtil.hexStringToBytes("88"));
		buf.put(BCDUtil.hexStringToBytes("10"));
		buf.put(BCDUtil.hexStringToBytes("01"));
		buf.put(BCDUtil.hexStringToBytes("F0"));
		buf.put(BCDUtil.hexStringToBytes("01"));
		buf.put(BCDUtil.hexStringToBytes("00"));
		buf.put(BCDUtil.hexStringToBytes("06"));
		buf.put(BCDUtil.str2Bcd("11"));
		buf.put(BCDUtil.str2Bcd("22"));
		buf.put(BCDUtil.str2Bcd("33"));
		buf.put(BCDUtil.str2Bcd("44"));
		buf.put(BCDUtil.str2Bcd("55"));
		buf.put(BCDUtil.str2Bcd("66"));
		ByteBuf bf = ByteBufAllocator.DEFAULT.buffer();
		bf.setBytes(0, buf);
		
        System.out.println("buf.limit():"+ buf.limit());
        /*ch.writeAndFlush(new DatagramPacket(bf,
                new InetSocketAddress(server, port)));*/
	}
    public class MsgClientHandler extends ChannelInboundHandlerAdapter{

//      @Override
//      public void messageReceived(
//              ChannelHandlerContext ctx, DatagramPacket msg)
//              throws Exception {
//          Packet p = PacketCodec.decode(msg.data().nioBuffer());
//          System.out.println("收到回复包：" + p.getContent());
//      }
      
      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msgobj) throws Exception {
    	  System.out.println(1111);
      }

      @Override
      public void exceptionCaught(
              ChannelHandlerContext ctx, Throwable cause)
              throws Exception {
          cause.printStackTrace();
          ctx.close();
      }
  }


	public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8082;
        }
        new MyClientTool(port).run();
    }
    
    
    
}

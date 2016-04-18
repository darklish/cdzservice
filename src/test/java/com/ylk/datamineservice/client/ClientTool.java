package com.ylk.datamineservice.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import com.ylk.datamineservice.util.BCDUtil;
import com.ylk.datamineservice.util.ByteUtil;


/**
 * Stat Server客户端工具
 * @author sanli
 */
public class ClientTool {
	
	private int port = 8082 ;
	private String server = "127.0.0.1";

    public ClientTool(int port) {
    	
    	
        this.port = port;
    }

    public void run() throws Exception {
        Bootstrap b = new Bootstrap();
        try {
            b.group(new NioEventLoopGroup())
            .channel(NioDatagramChannel.class)
            .handler(new MsgClientHandler());

            Channel ch = b.bind(0).sync().channel();
            sendRegPacket(ch);
        } finally {
            //b.shutdown();
        }
    }


	private void sendRegPacket(Channel ch) {
        
        ByteBuf bb = Unpooled.buffer();
        //卡号验证请求
        /*bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("21"));
        bb.writeBytes(BCDUtil.hexStringToBytes("00"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(ByteUtil.getIntBytes(150000001, 0));
        byte[] bb2 = ByteUtil.getIntBytes(123456, 0);
        bb.writeByte(bb2[0]);
        bb.writeByte(bb2[1]);
		
        bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("21"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("02"));
        bb.writeByte(bb2[2]);
        bb.writeByte(bb2[3]);*/
        
        //请求充电开始
        bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("02"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("21"));
        bb.writeBytes(BCDUtil.hexStringToBytes("00"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(ByteUtil.getIntBytes(150000001, 0));
        bb.writeByte((byte)0x01);
        byte[] bb2 = ByteUtil.getIntBytes(100, 0);
        bb.writeByte(bb2[0]);
        byte[] bb3 = ByteUtil.getIntBytes(10000, 0);
        
		
        bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("02"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("21"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeByte(bb2[1]);
        bb.writeByte(bb2[2]);
        bb.writeByte(bb2[3]);
        bb.writeByte(bb3[0]);
        bb.writeByte(bb3[1]);
        bb.writeByte(bb3[2]);
        bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("02"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("21"));
        bb.writeBytes(BCDUtil.hexStringToBytes("02"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeByte(bb3[3]);
        //请求充电结束
        /*bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("03"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("21"));
        bb.writeBytes(BCDUtil.hexStringToBytes("00"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(ByteUtil.getIntBytes(150000001, 0));
        bb.writeByte((byte)0x01);
        byte[] bb2 = ByteUtil.getIntBytes(100, 0);
        bb.writeByte(bb2[0]);
        
		
        bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("03"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("21"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("03"));
        bb.writeByte(bb2[1]);
        bb.writeByte(bb2[2]);
        bb.writeByte(bb2[3]);*/
        //请求充电暂停
        /*bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("04"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("20"));
        bb.writeBytes(BCDUtil.hexStringToBytes("00"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(ByteUtil.getIntBytes(123456789, 0));
        bb.writeByte((byte)0x01);
        byte[] bb2 = ByteUtil.getFloatBytes(1000, 0);
        bb.writeByte(bb2[0]);
        
		
        bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("04"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("20"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("03"));
        bb.writeByte(bb2[1]);
        bb.writeByte(bb2[2]);
        bb.writeByte(bb2[3]);*/
        
        // 请求冻结IC卡
        /*bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("05"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("20"));
        bb.writeBytes(BCDUtil.hexStringToBytes("00"));
        bb.writeBytes(BCDUtil.hexStringToBytes("05"));
        bb.writeBytes(ByteUtil.getIntBytes(151228020, 0));
        bb.writeByte((byte)0x01);*/
        // 上传上次离线数据
        /*bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("00"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(ByteUtil.getIntBytes(150000001, 0));
        bb.writeByte((byte)0x01);
        bb.writeByte((byte)0x01);
        bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(ByteUtil.getIntBytes(60, 0));//60分钟
        byte[] bb2 = ByteUtil.getIntBytes(5, 0);//使用电度
        bb.writeByte(bb2[0]);
        bb.writeByte(bb2[1]);
        bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("02"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeByte(bb2[2]);
        bb.writeByte(bb2[3]);
        bb.writeBytes(ByteUtil.getIntBytes(40, 0));*///结算费用40元
        //请求更新设备时间
        /*bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("32"));
        bb.writeBytes(BCDUtil.hexStringToBytes("20"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("00"));
        bb.writeBytes(BCDUtil.hexStringToBytes("00"));*/
        
        ch.writeAndFlush(new DatagramPacket(
                bb,
                new InetSocketAddress(server, port)));
	}

	public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8082;
        }
        new ClientTool(port).run();
    }
    
    
    public class MsgClientHandler extends ChannelInboundHandlerAdapter{

        /*public void messageReceived(
                ChannelHandlerContext ctx, DatagramPacket msg)
                throws Exception {
        	DatagramPacket msg = (DatagramPacket) msgobj;
            System.out.println("收到回复包：" + p.getContent());
        }*/
        
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msgobj) throws Exception {
			DatagramPacket msg = (DatagramPacket) msgobj;
			ByteBuffer in = msg.content().nioBuffer();
			byte[] barray = new byte[in.remaining()];
			in.get(barray, 0, barray.length);
			System.out.println("接收数据data:{}"+BCDUtil.bytesToHexString(barray));
        }

    }
    
    
}

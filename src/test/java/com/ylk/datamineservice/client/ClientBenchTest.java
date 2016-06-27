package com.ylk.datamineservice.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

import com.ylk.datamineservice.util.BCDUtil;
import com.ylk.datamineservice.util.ByteUtil;

/**
 * Stat Server客户端工具
 * @author sanli
 */
public class ClientBenchTest {
	
	private int port = 8082 ;
	private String server = "127.0.0.1";

    public ClientBenchTest(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        Bootstrap b = new Bootstrap();
        try {
            b.group(new NioEventLoopGroup())
            .channel(NioDatagramChannel.class);
            //.handler(new MsgClientHandler());
            Channel ch = b.bind(0).sync().channel();
            sendRegPacket(ch);
        } finally {
            //b.shutdown();
        }
    }


	private void sendRegPacket(Channel ch) {
		
        ByteBuf bb = Unpooled.buffer();
        //卡号验证请求
        bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("0A"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("00"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(ByteUtil.getIntBytes(123456789, 0));
        byte[] bb2 = ByteUtil.getIntBytes(123456, 0);
        bb.writeByte(bb2[0]);
        bb.writeByte(bb2[1]);
		
        bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("0A"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("02"));
        bb.writeByte(bb2[2]);
        bb.writeByte(bb2[3]);
        
        //请求充电开始
        /*bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("02"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("20"));
        bb.writeBytes(BCDUtil.hexStringToBytes("00"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(ByteUtil.getIntBytes(12346789, 0));
        bb.writeByte((byte)0x01);
        byte[] bb2 = ByteUtil.getFloatBytes(100, 0);
        bb.writeByte(bb2[0]);
        
		
        bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("02"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("20"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("03"));
        bb.writeByte(bb2[1]);
        bb.writeByte(bb2[2]);
        bb.writeByte(bb2[3]);*/
        //请求充电结束
        /*bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("03"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("20"));
        bb.writeBytes(BCDUtil.hexStringToBytes("00"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(ByteUtil.getIntBytes(123456789, 0));
        bb.writeByte((byte)0x01);
        byte[] bb2 = ByteUtil.getFloatBytes(100, 0);
        bb.writeByte(bb2[0]);
        
		
        bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("03"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("20"));
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
        bb.writeBytes(ByteUtil.getIntBytes(123456789, 0));
        bb.writeByte((byte)0x01);*/
        // 上传上次离线数据
        /*bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("20"));
        bb.writeBytes(BCDUtil.hexStringToBytes("00"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(ByteUtil.getIntBytes(12346789, 0));
        bb.writeByte((byte)0x01);
        bb.writeByte((byte)0x01);
        bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("20"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(ByteUtil.getFloatBytes(60, 0));//60分钟
        byte[] bb2 = ByteUtil.getFloatBytes(5, 0);//使用电度
        bb.writeByte(bb2[0]);
        bb.writeByte(bb2[1]);
        bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeBytes(BCDUtil.hexStringToBytes("20"));
        bb.writeBytes(BCDUtil.hexStringToBytes("02"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeByte(bb2[2]);
        bb.writeByte(bb2[3]);
        bb.writeBytes(ByteUtil.getFloatBytes(40, 0));*///结算费用40元
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
    
    
    
}

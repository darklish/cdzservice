package com.ylk.datamineservice.handler.coder;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ylk.datamineservice.frame.BroadCastFrame;
import com.ylk.datamineservice.util.BCDUtil;
import com.ylk.datamineservice.util.PackCodecUtil;

public class BroadCastEncoder extends MessageToMessageEncoder<BroadCastFrame> {
	private InetSocketAddress address;
	static Logger logger = LoggerFactory.getLogger(PackCodecUtil.class);
	
	public BroadCastEncoder(InetSocketAddress address) {
		this.address = address;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, BroadCastFrame msg,
			List<Object> out) throws Exception {
		ByteBuffer buf = ByteBuffer.allocate(13);
		PackCodecUtil.encode(msg, buf);
		logger.info("心跳返回数据data:{}",BCDUtil.bytesToHexString(buf.array()));
		out.add(new DatagramPacket(Unpooled.wrappedBuffer(buf), address));
		ctx.writeAndFlush(out);
		
	}

}

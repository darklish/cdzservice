package com.ylk.datamineservice.handler.coder;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageCodec;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.frame.BroadCastFrame;
import com.ylk.datamineservice.frame.ResBaseFrame;
import com.ylk.datamineservice.util.BCDUtil;
import com.ylk.datamineservice.util.ConstUtil;
import com.ylk.datamineservice.util.FrameUtil;
import com.ylk.datamineservice.util.PackCodecUtil;

public class PacketDecoder extends MessageToMessageCodec<DatagramPacket,ResBaseFrame>{
	Logger logger = LoggerFactory.getLogger(PacketDecoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, ResBaseFrame msg,
			List<Object> out) throws Exception {
		ByteBuffer buf = ByteBuffer.allocate(13);
		PackCodecUtil.encode(msg, buf);
		out.add(new DatagramPacket(Unpooled.wrappedBuffer(buf), msg.getRemoteAddress()));
		ctx.writeAndFlush(out);
		
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, DatagramPacket msg,
			List<Object> out) throws Exception {
		ByteBuffer in = msg.content().nioBuffer();
		byte[] barray = new byte[in.remaining()];
		in.get(barray, 0, barray.length);
		
		logger.info("接收数据data:{}",BCDUtil.bytesToHexString(barray));
		
		//数据少于1帧长度
		if(barray.length < ConstUtil.FRAME_LEN) {
			logger.info("数据解码，数组帧长度小于固定长度:{}",ConstUtil.FRAME_LEN);
			//return;
		}
		//数据
		if(barray.length%13 != 0) {
			logger.warn("UDP包数据不是帧长度整数倍!");
		}
		
		for (int i = 0; i < barray.length; i++) {
			byte[] head = Arrays.copyOfRange(barray,i,i+1);
			//看首字节是否为88H
			int headInt = BCDUtil.bytesToHex(head);
			if (headInt == ConstUtil.FH_VAL) {
				if (i+13 <= barray.length) {
					byte[] msgBytes = Arrays.copyOfRange(barray, i+1, i+13);
					BaseFrame event = FrameUtil.decodeEvent(msgBytes);
					if(event != null) {
						event.setRemoteAddress(msg.sender());
						out.add(event);
						i = i+12;
					}
					else {
						i=i+12;
					}
					
				}
				else {
					byte[] msgBytes = Arrays.copyOfRange(barray, i+1, barray.length);
					BaseFrame event = FrameUtil.decodeEvent(msgBytes);
					event.setRemoteAddress(msg.sender());
					out.add(event);
					i = i+12;
					logger.warn("解析过程中帧个数异常!");
					break;
				}
			}
			
		}
		
	}


}

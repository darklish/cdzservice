package com.ylk.datamineservice.handler.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import com.ylk.datamineservice.util.RCUtil;


public class RcDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) throws Exception {
		byte[] array = new byte[msg.readableBytes()];
        msg.getBytes(0, array);
        out.add(RCUtil.decoderRc(array));
	}


}

package com.ylk.datamineservice.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class BytBufTest {
	
	public static void  main(String[] args) {
		ByteBuf bb = ByteBufAllocator.DEFAULT.buffer();
		bb.writeByte(4);
		System.out.println(bb.readableBytes());
		byte[] a = new byte[bb.readableBytes()];
		bb.getBytes(0,a);
		System.out.println(bb.readableBytes());
		System.out.println(a);
	}

}

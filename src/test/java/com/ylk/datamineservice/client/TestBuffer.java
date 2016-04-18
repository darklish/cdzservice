package com.ylk.datamineservice.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.ByteBuffer;

public class TestBuffer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ByteBuffer buf = ByteBuffer.allocate(40);
		System.out.println("pos:" +buf.position() + ",limit:" + buf.limit() +",cap:" + buf.capacity());
		buf.clear();
		System.out.println("pos:" +buf.position() + ",limit:" + buf.limit() +",cap:" + buf.capacity());
		buf.put((byte)240);
		buf.putFloat(2.0F);
		buf.putFloat(3.0F);
		buf.flip();
		byte[] arr = new byte[10];
		buf.get(arr, 0, 8);
		/*System.out.println("pos:" +buf.position() + ",limit:" + buf.limit() +",cap:" + buf.capacity());
		while(buf.hasRemaining()) {
			if (buf.limit() - buf.position() >=6) {
				byte[] arr = new byte[6];
				buf.get(arr);
				System.out.println("pos:" +buf.position() + ",limit:" + buf.limit() +",cap:" + buf.capacity());
			}
			else {
				byte[] arr = new byte[buf.limit() - buf.position()];
				buf.get(arr);
				System.out.println("pos:" +buf.position() + ",limit:" + buf.limit() +",cap:" + buf.capacity());
			}
		}*/
		
		/*ByteBuf bf= ByteBufAllocator.DEFAULT.buffer();
		bf.writeDouble(2.0);
		bf.writeDouble(2.0);
		bf.writeInt(1);
		System.out.println(bf.readDouble());
		System.out.println(bf.readDouble());
		System.out.println(bf.readInt());*/
	}

}

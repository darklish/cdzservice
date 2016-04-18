package com.ylk.datamineservice.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.Callable;

import com.ylk.datamineservice.util.BCDUtil;
import com.ylk.datamineservice.util.ByteUtil;

public class ClientTask implements Callable {
	private CdzMsg cdz;
	private DatagramSocket dataSocket;
	private DatagramPacket dataPacket;

	public ClientTask() {

	}

	public ClientTask(CdzMsg cdz) {
		this.cdz = cdz;
	}

	public Object call() throws Exception {
		byte[] sendDataByte = new byte[1000];
		ByteBuf bb = Unpooled.buffer();
		bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeByte((byte)cdz.deviceNo);
        bb.writeBytes(BCDUtil.hexStringToBytes("00"));
        bb.writeBytes(BCDUtil.hexStringToBytes("06"));
        bb.writeBytes(ByteUtil.getIntBytes(cdz.deviceNo, 0));
        byte[] bb2 = ByteUtil.getIntBytes(123456, 0);
        bb.writeByte(bb2[0]);
        bb.writeByte(bb2[1]);
		
        bb.writeBytes(BCDUtil.hexStringToBytes("88"));
        bb.writeBytes(BCDUtil.hexStringToBytes("10"));
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("F0"));
        bb.writeByte((byte)cdz.deviceNo);
        bb.writeBytes(BCDUtil.hexStringToBytes("01"));
        bb.writeBytes(BCDUtil.hexStringToBytes("02"));
        bb.writeByte(bb2[2]);
        bb.writeByte(bb2[3]);
        
		for (int i = 0; i < 10; i++) {
			try {
				dataSocket = new DatagramSocket();
				dataSocket.setSoTimeout(15000);

				InetAddress address = InetAddress.getByName("127.0.0.1");
				int port = 8082;

				System.out.println("TARGET_ADDRESS: [" + address.toString()
						+ "]");
				dataPacket = new DatagramPacket(bb.array(),
						bb.readableBytes(), address, port);

				long start = System.currentTimeMillis();
				dataSocket.send(dataPacket);
				DatagramPacket pac = new DatagramPacket(sendDataByte,
						sendDataByte.length);
				dataSocket.receive(pac);
				System.out.println("SUCCESS recive response:"
						+ new String(pac.getData()).trim() + ", used time:"
						+ (System.currentTimeMillis() - start));
				System.out
						.println("==========================================");
			} catch (SocketException se) {
				System.err.println("TIMOUT exception:" + se.getMessage());
			} catch (IOException ie) {
				ie.printStackTrace();
			}
			Thread.sleep(10000);

		}
		return null;

	}
}

package com.ylk.datamineservice.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UdpBenchClient{
	private static final int PORT = 40001;
	private DatagramSocket dataSocket;
	private DatagramPacket dataPacket;
	private byte sendDataByte[];
	private String udpMsg;

	public UdpBenchClient() {
		Init();
	}

	public void Init() {
		
		ExecutorService exec = Executors.newFixedThreadPool(100);
		for (int i = 1;i< 100; i++) {
			CdzMsg msg = new CdzMsg(i);
			ClientTask task = new ClientTask(msg);
			exec.submit(task);
		}
	}

	public static void main(String args[]) {
		new UdpBenchClient();
	}
}

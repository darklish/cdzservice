package com.ylk.datamineservice.util;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ylk.datamineservice.frame.BroadCastFrame;
import com.ylk.datamineservice.frame.ResBaseFrame;

public class PackCodecUtil {
	static Logger logger = LoggerFactory.getLogger(PackCodecUtil.class);
	
	public static void encode(ResBaseFrame resFrame,ByteBuffer buf){
		buf.put((byte)resFrame.getpHead());
		buf.put((byte)resFrame.getPid());
		buf.put((byte)resFrame.getFrameType());
		buf.put(resFrame.getDst());
		buf.put((byte)0xF0);
		buf.put((byte)resFrame.getFrameNo());
		buf.put((byte)resFrame.getDataLenth());
		buf.put(resFrame.getData());
		for (int i = 0; i < FrameUtil.MAX_FRAME_LEN - resFrame.getData().length; i++) {
			buf.put((byte)0);
		}
		buf.flip();
		logger.info("返回数据data:{}",BCDUtil.bytesToHexString(buf.array()));
	}
	
	public static void encode(BroadCastFrame resFrame,ByteBuffer buf){
		buf.put((byte)resFrame.getpHead());
		buf.put((byte)resFrame.getPid());
		buf.put((byte)resFrame.getFrameType());
		buf.put((byte)00);
		buf.put((byte)0xF0);
		buf.put((byte)resFrame.getFrameNo());
		buf.put((byte)resFrame.getDataLenth());
		buf.put(resFrame.getData());
		for (int i = 0; i < FrameUtil.MAX_FRAME_LEN - resFrame.getData().length; i++) {
			buf.put((byte)0);
		}
		buf.flip();
	}

}

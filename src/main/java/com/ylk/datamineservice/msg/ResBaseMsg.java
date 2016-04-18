package com.ylk.datamineservice.msg;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.frame.ResBaseFrame;
import com.ylk.datamineservice.util.FrameUtil;

public abstract class ResBaseMsg {
	protected ByteBuffer dataBuf = ByteBuffer.allocate(FrameUtil.MAX_DATA_LEN).order(ByteOrder.LITTLE_ENDIAN);
	protected int fType;
	public List<ResBaseFrame> getResBaseFrames(BaseFrame baseFrame) {
		initBuffer();
		List<ResBaseFrame> resList = new ArrayList<ResBaseFrame>();
		int i = 0;
		while(dataBuf.hasRemaining()) {
			if (dataBuf.limit() - dataBuf.position() >=FrameUtil.MAX_FRAME_LEN) {
				ResBaseFrame resBaseFrame = new ResBaseFrame(baseFrame);
				resBaseFrame.setFrameType(fType);
				resBaseFrame.setFrameNo(i);
				resBaseFrame.setDataLenth(FrameUtil.MAX_FRAME_LEN);
				byte[] arr = new byte[FrameUtil.MAX_FRAME_LEN];
				dataBuf.get(arr);
				resBaseFrame.setData(arr);
				resList.add(resBaseFrame);
			}
			else {
				ResBaseFrame resBaseFrame = new ResBaseFrame(baseFrame);
				resBaseFrame.setFrameType(fType);
				byte[] arr = new byte[dataBuf.limit() - dataBuf.position()];
				resBaseFrame.setDataLenth(dataBuf.limit() - dataBuf.position());
				dataBuf.get(arr);
				resBaseFrame.setFrameNo(i);
				resBaseFrame.setData(arr);
				resList.add(resBaseFrame);
			}
			i++;
		}
		
		return resList;
	}
	public abstract void initBuffer();

}

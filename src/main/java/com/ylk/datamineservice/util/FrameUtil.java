package com.ylk.datamineservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.frame.ReqBeginChargeFrame;
import com.ylk.datamineservice.frame.ReqEndChargeFrame;
import com.ylk.datamineservice.frame.ReqFrozenIcFrame;
import com.ylk.datamineservice.frame.ReqIcAddFrame;
import com.ylk.datamineservice.frame.ReqIcStateFrame;
import com.ylk.datamineservice.frame.ReqOfflineDataFrame;
import com.ylk.datamineservice.frame.ReqPauseChargeFrame;
import com.ylk.datamineservice.frame.ReqStartChargeFrame;
import com.ylk.datamineservice.frame.ReqTimeCheckFrame;
import com.ylk.datamineservice.frame.ResKeepAliveFrame;

public class FrameUtil {
	public static final int MAX_DATA_LEN = 100;
	public static final int MAX_FRAME_LEN = 6;
	static Logger  logger = LoggerFactory.getLogger(FrameUtil.class);
	public static BaseFrame decodeEvent(byte[] data) {
		BaseFrame frame = null;
		// 固定13字节的数组解析
		byte[] decoderBytes = data;//RCUtil.decoderRc(data);
		byte pid = decoderBytes[0];
		if (BCDUtil.byteToHex(pid) == ConstUtil.PID_VAL) {
			byte ftByte = decoderBytes[1];
			int ft = BCDUtil.byteToHex(ftByte);
			switch(ft){
			case FrameTypeUtil.REQ_IC_STATE:
				frame = new ReqIcStateFrame();
				frame.setSrcBytes(decoderBytes);
				frame = frame.decoder();
				logger.info("接收IC卡状态帧:"+frame.toString());
				break;
			case FrameTypeUtil.REQ_START_CHARGE:
				frame = new ReqStartChargeFrame();
				frame.setSrcBytes(decoderBytes);
				frame = frame.decoder();
				logger.info("接收开始充电帧:"+frame.toString());
				break;
			case FrameTypeUtil.REQ_END_CHARGE:
				frame = new ReqEndChargeFrame();
				frame.setSrcBytes(decoderBytes);
				frame = frame.decoder();
				logger.info("接收结束充电帧:"+frame.toString());
				break;
				
			case FrameTypeUtil.REQ_PAUSE_CHARGE:
				frame = new ReqPauseChargeFrame();
				frame.setSrcBytes(decoderBytes);
				frame = frame.decoder();
				logger.info("接收暂停充电帧:"+frame.toString());
				break;
			case FrameTypeUtil.REQ_FROZEN_IC:
				frame = new ReqFrozenIcFrame();
				frame.setSrcBytes(decoderBytes);
				frame = frame.decoder();
				logger.info("接收冻结IC帧:"+frame.toString());
				break;
			case FrameTypeUtil.REQ_OFFLINE_DATA:
				frame = new ReqOfflineDataFrame();
				frame.setSrcBytes(decoderBytes);
				frame = frame.decoder();
				logger.info("接收离线数据帧:"+frame.toString());
				break;
			case FrameTypeUtil.REQ_IC_ADD:
				frame = new ReqIcAddFrame();
				frame.setSrcBytes(decoderBytes);
				frame = frame.decoder();
				logger.info("接收增加IC帧:"+frame.toString());
				break;
			case FrameTypeUtil.REQ_TIME_CHECK:
				frame = new ReqTimeCheckFrame();
				frame.setSrcBytes(decoderBytes);
				frame = frame.decoder();
				logger.info("接收时间校验帧:"+frame.toString());
				break;
			case FrameTypeUtil.RES_DEVICE_STATE:
				frame = new ResKeepAliveFrame();
				frame.setSrcBytes(decoderBytes);
				frame = frame.decoder();
				logger.info("接收设备消息:"+frame.toString());
				break;
			case FrameTypeUtil.REQ_BEGIN_CHARGE:
				frame = new ReqBeginChargeFrame();
				frame.setSrcBytes(decoderBytes);
				frame = frame.decoder();
				logger.info("接收请求开始充电消息:"+frame.toString());
				break;
			}
			
		}
		return frame;
	}

}

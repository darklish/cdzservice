package com.ylk.datamineservice.service;

import java.util.List;

import com.ylk.datamineservice.model.CdzInfo;
import com.ylk.datamineservice.msg.ReqBaseMsg;
import com.ylk.datamineservice.msg.ReqBeginChargeMsg;
import com.ylk.datamineservice.msg.ReqEndChargeMsg;
import com.ylk.datamineservice.msg.ReqFrozenIcMsg;
import com.ylk.datamineservice.msg.ReqICStateMsg;
import com.ylk.datamineservice.msg.ResKeepAliveMsg;
import com.ylk.datamineservice.msg.ReqOfflineDataMsg;
import com.ylk.datamineservice.msg.ReqPauseChargeMsg;
import com.ylk.datamineservice.msg.ReqStartChargeMsg;
import com.ylk.datamineservice.msg.ReqTimeCheckMsg;
import com.ylk.datamineservice.msg.ResBeginChargeMsg;
import com.ylk.datamineservice.msg.ResEndChargeMsg;
import com.ylk.datamineservice.msg.ResFrozenIcMsg;
import com.ylk.datamineservice.msg.ResIcStateMsg;
import com.ylk.datamineservice.msg.ResOfflineDataMsg;
import com.ylk.datamineservice.msg.ResPauseChargeMsg;
import com.ylk.datamineservice.msg.ResStartChargeMsg;
import com.ylk.datamineservice.msg.ResTimeCheckMsg;

public interface BusinessComService {


	ResOfflineDataMsg offLineDataUpload(ReqOfflineDataMsg msg);

	ResPauseChargeMsg pauseCharge(ReqPauseChargeMsg msg);

	ResEndChargeMsg endCharge(ReqEndChargeMsg msg);

	ResStartChargeMsg startCharge(ReqStartChargeMsg msg);

	ResIcStateMsg checkICCard(ReqICStateMsg msg);

	void deviceOffLine(String cdzNo);

	ResTimeCheckMsg reqTimeCheck(ReqTimeCheckMsg msg);

	ResFrozenIcMsg frozenIc(ReqFrozenIcMsg msg);

	void keepAlive(ResKeepAliveMsg msg);

	ResBeginChargeMsg beginCharge(ReqBeginChargeMsg msg);

	boolean commonCheckMsg(ReqBaseMsg bm);

	List<CdzInfo> listAllDevices();

	void onLineDevicee(String cdzNo);

	boolean sysCommonConfigCheck();

}

package com.ylk.datamineservice.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ylk.datamineservice.mapper.StreamInfoMapper;
import com.ylk.datamineservice.model.AreaInfo;
import com.ylk.datamineservice.model.AreaInfoExample;
import com.ylk.datamineservice.model.CardInfo;
import com.ylk.datamineservice.model.CardInfoExample;
import com.ylk.datamineservice.model.CdzInfo;
import com.ylk.datamineservice.model.CdzInfoExample;
import com.ylk.datamineservice.model.ChargeLog;
import com.ylk.datamineservice.model.StreamInfo;
import com.ylk.datamineservice.msg.ReqBaseMsg;
import com.ylk.datamineservice.msg.ReqBeginChargeMsg;
import com.ylk.datamineservice.msg.ReqEndChargeMsg;
import com.ylk.datamineservice.msg.ReqFrozenIcMsg;
import com.ylk.datamineservice.msg.ReqICStateMsg;
import com.ylk.datamineservice.msg.ReqOfflineDataMsg;
import com.ylk.datamineservice.msg.ReqPauseChargeMsg;
import com.ylk.datamineservice.msg.ReqStartChargeMsg;
import com.ylk.datamineservice.msg.ReqTimeCheckMsg;
import com.ylk.datamineservice.msg.ResBeginChargeMsg;
import com.ylk.datamineservice.msg.ResEndChargeMsg;
import com.ylk.datamineservice.msg.ResFrozenIcMsg;
import com.ylk.datamineservice.msg.ResIcStateMsg;
import com.ylk.datamineservice.msg.ResKeepAliveMsg;
import com.ylk.datamineservice.msg.ResOfflineDataMsg;
import com.ylk.datamineservice.msg.ResPauseChargeMsg;
import com.ylk.datamineservice.msg.ResStartChargeMsg;
import com.ylk.datamineservice.msg.ResTimeCheckMsg;
import com.ylk.datamineservice.service.AreaInfoService;
import com.ylk.datamineservice.service.BusinessComService;
import com.ylk.datamineservice.service.CardInfoService;
import com.ylk.datamineservice.service.CdzInfoService;
import com.ylk.datamineservice.service.ChargeLogService;
import com.ylk.datamineservice.util.CardStateUtil;
import com.ylk.datamineservice.util.CdzStateUtil;
import com.ylk.datamineservice.util.ChargeDataTypeUtil;
import com.ylk.datamineservice.util.ChargeTypeUtil;
import com.ylk.datamineservice.util.ChargeUtil;
import com.ylk.datamineservice.util.ConstUtil;
import com.ylk.datamineservice.util.FrozenUtil;
import com.ylk.datamineservice.util.MethodUtil;
import com.ylk.datamineservice.util.MyDateUtil;

@Service
public class BusinessComServiceImpl implements BusinessComService {
	Logger logger = LoggerFactory.getLogger(BusinessComServiceImpl.class);
	@Resource
	private CardInfoService cardInfoService;
	
	@Resource
	private CdzInfoService cdzInfoService;
	
	@Resource
	private ChargeLogService chargeLogService;
	
	@Resource
	private AreaInfoService areaInfoService;
	
	@Resource
	private StreamInfoMapper streamInfoMapper;

	public ResOfflineDataMsg offLineDataUpload(ReqOfflineDataMsg msg) {
		Date endTime = new Date();
		ResOfflineDataMsg retMsg = new ResOfflineDataMsg();
		retMsg.setUploadCode(ChargeUtil.CHARGE_UPLOAD_FAIL);
		
		AreaInfo areaInfo = areaInfoService.getAreaInfo();
		CardInfo cardInfo = cardInfoService.checkCardNo(msg.getCardNo());
		
		if (cardInfo == null) {
			logger.error("请求离线数据上传,卡信息错误:cardNo:{}",msg.getCardNo());
			retMsg.setUploadCode(ChargeUtil.CHARGE_UPLOAD_FAIL);
			return retMsg;
		}
		int oldFun = cardInfo.getMoney();
		
		// 根据，充电桩号，卡号，查询开始请求的电表电度信息
		
		ChargeLog startChargeLog = chargeLogService.getStartChargeByCdzAndCardNo(Integer.valueOf(msg.getCdzNo()),msg.getCardNo());
		if (startChargeLog == null) {
			logger.info("离线数据上传,无开始充电信息,冻结卡,等待后续处理,cdzNo:{},cardNo:{}",msg.getCdzNo(),msg.getCardNo());
			cardInfo.setState(CardStateUtil.DB_CARD_STATE_FROZEN);
			cardInfoService.updateCardInfo(cardInfo);
			return retMsg;
		}
		
		int result = msg.getChargeResult();// 充电结果,暂时不考虑,如果正在充电,是否无需处理,等充电完成后统一做处理
		
		//根据充电方式计算金额
		int config = msg.getChargeConfig();// 无论充电配置如何,最终都已电度为准计算
		int cardNo = msg.getCardNo();
		float quantity = msg.getChargeQuantity();
		float chargeTime = msg.getChargeTime();
		
		int qua = msg.getMeterQua() - startChargeLog.getBeginMeterQua();
		// 根据单价信息，消耗电量信息，卡信息判断是否是有效
		
		if (qua < 0 ) {
			logger.error("离线数据上传,消耗电量为负，锁定卡,cardNo:{},开始电量:{},结束电量:{}"
					,msg.getCardNo(),startChargeLog.getBeginMeterQua(),msg.getMeterQua());
		}
		int consumFen = (int)Math.ceil(areaInfo.getElectricityprice() * qua/100d);
		
		if (consumFen > cardInfo.getMoney()) {
			// 金额不足，锁定卡
			logger.info("离线数据上传,金额不足情况下，锁定卡,cardNo:{},消耗电量:{},单价:{},卡余额:{}"
					,msg.getCardNo(),qua,areaInfo.getElectricityprice(),cardInfo.getMoney());
			cardInfo.setMoney(cardInfo.getMoney().intValue() - consumFen);
			cardInfo.setState(CardStateUtil.DB_CARD_STATE_FROZEN);
		}
		else {
			cardInfo.setMoney(cardInfo.getMoney().intValue() - consumFen);
			cardInfo.setState(CardStateUtil.DB_CARD_STATE_NORMAL);
		}
		cardInfoService.updateCardInfo(cardInfo);
		
		// 增加流水记录
		StreamInfo sInfo = new StreamInfo();
		sInfo.setCardno(msg.getCardNo()+"");
		sInfo.setCdzno(msg.getCdzNo());
		sInfo.setMeterquastart(startChargeLog.getBeginMeterQua());
		sInfo.setMeterquaend(msg.getMeterQua());
		sInfo.setChangetype(3);
		sInfo.setCreatetime(new Date());
		sInfo.setDegree(qua);
		sInfo.setStarttime(startChargeLog.getBeginTime());
		sInfo.setEndtime(endTime);
		sInfo.setCreateby(msg.getDeviceNo());
		sInfo.setMemo("离线数据上传");
		sInfo.setDeleted(0);
		sInfo.setState(0);
		sInfo.setFundold(oldFun);
		sInfo.setFundchange(new BigDecimal(Float.toString(consumFen)).intValue());
		sInfo.setFundnew(cardInfo.getMoney());
		sInfo.setPrice(areaInfo.getElectricityprice());
		streamInfoMapper.insert(sInfo);
		
		
		// 累加充电桩电度
		List<CdzInfo> infoList = cdzInfoService.getCdzByNo(msg.getCdzNo());
		CdzInfo cdzInfo = infoList.get(0);
		cdzInfo.setTotaldegree(cdzInfo.getTotaldegree() + qua);
		cdzInfoService.updateCdzInfo(cdzInfo);
		
		// 更新充电记录
		startChargeLog.setAmount(new BigDecimal(Float.toString(consumFen)).intValue());
		startChargeLog.setCardNo(msg.getCardNo());
		startChargeLog.setCdzId(Integer.valueOf(msg.getCdzNo()));
		startChargeLog.setEndMeterQua(msg.getMeterQua());
		startChargeLog.setRealQua(qua);
		startChargeLog.setEndTime(endTime);
		startChargeLog.setState(1);
		startChargeLog.setMemo("离线数据上传");
		chargeLogService.updateChargeLog(startChargeLog);
		// 更新开始充电状态
		retMsg.setUploadCode(ChargeUtil.CHARGE_UPLOAD_SUCCESS);
		retMsg.setBalance((int)Math.ceil(cardInfo.getMoney()/100d));
		
		return retMsg;
		
	}

	@Transactional
	public ResPauseChargeMsg pauseCharge(ReqPauseChargeMsg msg) {
		Date endTime = new Date();
		ResPauseChargeMsg retMsg = new ResPauseChargeMsg();
		// 获取单价信息和卡信息
		AreaInfo areaInfo = areaInfoService.getAreaInfo();
		CardInfo cardInfo = cardInfoService.checkCardNo(msg.getCardNo());
		if (cardInfo == null) {
			retMsg.setPauseCode(ChargeUtil.PAUSE_CHARGE_FORBID);
			return retMsg;
		}
		
		//判断错误结束码
		if (msg.getEndCode() != ChargeUtil.PAUSE_CHARGE_CODE) {
			logger.info("充电暂停,结束码错误,END_CHARGE_CODE:{}",msg.getEndCode());
			retMsg.setPauseCode(ChargeUtil.PAUSE_CHARGE_FORBID);
			return retMsg;
		}
		
		// 根据，充电桩号，卡号，查询开始请求的电表电度信息
		ChargeLog startChargeLog = chargeLogService.getStartChargeByCdzAndCardNo(Integer.valueOf(msg.getCdzNo()),msg.getCardNo());
		if (startChargeLog == null) {
			logger.info("充电暂停,开始记录错误,冻结卡,等待后续处理,cdzNo:{},cardNo:{}",msg.getCdzNo(),msg.getCardNo());
			cardInfo.setState(CardStateUtil.CARD_FROZEN);
			cardInfoService.updateCardInfo(cardInfo);
			return retMsg;
		}
		
		int qua = msg.getMeterQua() - startChargeLog.getBeginMeterQua();
		if (qua < 0 ) {
			logger.info("充电暂停,消耗电量为负，锁定卡,cardNo:{},开始电量:{},结束电量:{}"
					,msg.getCardNo(),startChargeLog.getBeginMeterQua(),msg.getMeterQua());
		}
		int consumFen = (int)Math.ceil(areaInfo.getElectricityprice() * qua/100d);
		if (consumFen > cardInfo.getMoney()) {
			// 金额不足，锁定卡
			logger.info("充电结束,金额不足情况下，锁定卡,cardNo:{},消耗电量:{},单价:{},卡余额:{}"
					,msg.getCardNo(),qua,areaInfo.getElectricityprice(),cardInfo.getMoney());
			cardInfo.setMoney(cardInfo.getMoney().intValue() - consumFen);
			cardInfo.setState(CardStateUtil.CARD_FROZEN);
		}
		else {
			cardInfo.setMoney(cardInfo.getMoney().intValue() - consumFen);
			cardInfo.setState(CardStateUtil.DB_CARD_STATE_NORMAL);
		}
		cardInfoService.updateCardInfo(cardInfo);
		
		// 增加流水记录
		StreamInfo sInfo = new StreamInfo();
		sInfo.setCardno(msg.getCardNo()+"");
		sInfo.setCdzno(msg.getCdzNo());
		sInfo.setMeterquastart(startChargeLog.getBeginMeterQua());
		sInfo.setMeterquaend(msg.getMeterQua());
		sInfo.setChangetype(3);
		sInfo.setCreatetime(new Date());
		sInfo.setDegree(qua);
		sInfo.setStarttime(startChargeLog.getBeginTime());
		sInfo.setEndtime(endTime);
		sInfo.setCreateby(msg.getDeviceNo());
		sInfo.setMemo("暂停充电");
		sInfo.setDeleted(0);
		sInfo.setFundchange(new BigDecimal(Float.toString(consumFen)).intValue());
		sInfo.setState(0);
		sInfo.setFundold(cardInfo.getMoney());
		sInfo.setFundnew(cardInfo.getMoney().intValue() - consumFen);
		sInfo.setPrice(areaInfo.getElectricityprice());
		streamInfoMapper.insert(sInfo);
		
		
		// 累加充电桩电度
		List<CdzInfo> infoList = cdzInfoService.getCdzByNo(msg.getCdzNo());
		CdzInfo cdzInfo = infoList.get(0);
		cdzInfo.setTotaldegree(cdzInfo.getTotaldegree() + consumFen);
		cdzInfoService.updateCdzInfo(cdzInfo);
		
		// 更新充电记录
		startChargeLog.setAmount(new BigDecimal(Float.toString(qua)).intValue());
		startChargeLog.setCardNo(msg.getCardNo());
		startChargeLog.setCdzId(Integer.valueOf(msg.getCdzNo()));
		startChargeLog.setEndMeterQua(msg.getMeterQua());
		startChargeLog.setRealQua(consumFen);
		startChargeLog.setEndTime(endTime);
		startChargeLog.setState(1);
		startChargeLog.setMemo("暂停充电");
		chargeLogService.updateChargeLog(startChargeLog);
		// 更新开始充电状态
		retMsg.setPauseCode(ChargeUtil.PAUSE_CHARGE_ALLOW);
		return retMsg;
	
	}

	@Transactional
	public ResEndChargeMsg endCharge(ReqEndChargeMsg msg) {
		Date endTime = new Date();
		ResEndChargeMsg retMsg = new ResEndChargeMsg();
		//retMsg.setCosum(61234);
		StreamInfo sInfo = new StreamInfo();
		AreaInfo areaInfo = areaInfoService.getAreaInfo();
		CardInfo cardInfo = cardInfoService.checkCardNo(msg.getCardNo());
		
		if (cardInfo == null) {
			retMsg.setDealCode(ChargeUtil.END_CHARGE_FORBID);
			return retMsg;
		}
		int oldFun = cardInfo.getMoney();
		
		
		//判断错误结束码
		if (msg.getEndCode() != ChargeUtil.END_CHARGE_CODE) {
			logger.info("充电结束,结束码错误,END_CHARGE_CODE:{}",msg.getEndCode());
			retMsg.setDealCode(ChargeUtil.END_CHARGE_FORBID);
			return retMsg;
		}
		
		List<CdzInfo> infoList = cdzInfoService.getCdzByNo(msg.getCdzNo());
		CdzInfo cdzInfo = infoList.get(0);
		
		
		
		// 根据，充电桩号，卡号，查询开始请求的电表电度信息
		
		
		ChargeLog startChargeLog = chargeLogService.getStartChargeByCdzAndCardNo(Integer.valueOf(msg.getCdzNo()),msg.getCardNo());
		if (startChargeLog == null) {
			logger.info("充电结束,开始记录错误,冻结卡,等待后续处理,cdzNo:{},cardNo:{}",msg.getCdzNo(),msg.getCardNo());
			cardInfo.setState(CardStateUtil.DB_CARD_STATE_FROZEN);
			cardInfoService.updateCardInfo(cardInfo);
			return retMsg;
		}
		
		int qua = msg.getMeterQua() - startChargeLog.getBeginMeterQua();
		if (qua < 0 ) {
			logger.info("充电结束,消耗电量为负，锁定卡,cardNo:{},开始电量:{},结束电量:{}"
					,msg.getCardNo(),startChargeLog.getBeginMeterQua(),msg.getMeterQua());
		}
		// 电表消耗，直接保留
		int consumFen = (int)Math.ceil(areaInfo.getElectricityprice() * qua/100d);
		if (consumFen > cardInfo.getMoney()) {
			// 金额不足，锁定卡
			logger.info("充电结束,金额不足情况下，锁定卡,cardNo:{},消耗电量:{},单价:{},卡余额:{}"
					,msg.getCardNo(),qua,areaInfo.getElectricityprice(),cardInfo.getMoney());
			cardInfo.setMoney(cardInfo.getMoney().intValue() - consumFen);
			cardInfo.setState(CardStateUtil.DB_CARD_STATE_FROZEN);
		}
		else {
			logger.info("充电结束,cardNo:{},消耗电量:{},单价:{},卡余额:{}"
					,msg.getCardNo(),qua,areaInfo.getElectricityprice(),cardInfo.getMoney());
			cardInfo.setMoney(cardInfo.getMoney().intValue() - consumFen);
			retMsg.setCosum(consumFen);
			cardInfo.setState(CardStateUtil.DB_CARD_STATE_NORMAL);
		}
		cardInfoService.updateCardInfo(cardInfo);
		
		// 增加流水记录
		
		sInfo.setCardno(msg.getCardNo()+"");
		sInfo.setCdzno(msg.getCdzNo());
		sInfo.setMeterquastart(startChargeLog.getBeginMeterQua());
		sInfo.setMeterquaend(msg.getMeterQua());
		sInfo.setChangetype(3);
		sInfo.setCreatetime(new Date());
		sInfo.setStarttime(startChargeLog.getBeginTime());
		sInfo.setEndtime(endTime);
		sInfo.setDegree(qua);
		sInfo.setCreateby(msg.getDeviceNo());
		sInfo.setMemo("结束充电");
		sInfo.setDeleted(0);
		sInfo.setState(0);
		sInfo.setFundchange(new BigDecimal(Float.toString(consumFen)).intValue());
		sInfo.setFundold(oldFun);
		sInfo.setFundnew(cardInfo.getMoney());
		sInfo.setPrice(areaInfo.getElectricityprice());
		streamInfoMapper.insert(sInfo);
		
		
		// 累加充电桩电度
		
		cdzInfo.setTotaldegree(cdzInfo.getTotaldegree() + qua);
		cdzInfoService.updateCdzInfo(cdzInfo);
		
		// 更新充电记录
		startChargeLog.setAmount(new BigDecimal(Float.toString(consumFen)).intValue());
		startChargeLog.setCardNo(msg.getCardNo());
		startChargeLog.setCdzId(Integer.valueOf(msg.getCdzNo()));
		startChargeLog.setEndMeterQua(msg.getMeterQua());
		startChargeLog.setRealQua(qua);
		startChargeLog.setEndTime(endTime);
		startChargeLog.setState(1);
		startChargeLog.setMemo("结束充电");
		chargeLogService.updateChargeLog(startChargeLog);
		// 更新开始充电状态
		retMsg.setDealCode(ChargeUtil.END_CHARGE_ALLOW);
		logger.info("充电结束:"+retMsg.toString());
		return retMsg;
		
	}

	/**
	 * 开始充电请求完成，将卡状态变为正在使用状态
	 */
	@Transactional
	public ResStartChargeMsg startCharge(ReqStartChargeMsg msg) {

		ResStartChargeMsg retMsg = new ResStartChargeMsg();
		try {
		// 卡号校验及获取卡信息
			CardInfo cardInfo = cardInfoService.checkCardNo(msg.getCardNo());
			if (cardInfo == null) {
				retMsg.setChargeCode(ChargeUtil.CHARGE_FORBID_CON_ERR);
				return retMsg;
			}
			// 获取单价信息开始
			AreaInfo areaInfo = areaInfoService.getAreaInfo();
			
			
			retMsg.setChargeCode(ChargeUtil.CHARGE_FORBID_DATA_ERR);
			ChargeLog chargeLog = new ChargeLog();
			
			// 固定电度处理开始
			if (msg.getChargeConfig() == ChargeTypeUtil.FIX_DEGREE) {
				int degree = msg.getChargeData();
				chargeLog.setAmount(degree);
				chargeLog.setChargeType(ChargeTypeUtil.DB_FIX_DEGREE);
				int amount = degree * areaInfo.getElectricityprice();
				if (amount > cardInfo.getMoney().intValue()) {
					logger.info("充电开始固定电度充电,卡号:{},充电电度:{},余额:{},当前单价:{} 余额不足!",
							cardInfo.getCardno(),degree,cardInfo.getMoney(),areaInfo.getElectricityprice());
					retMsg.setChargeCode(ChargeUtil.CHARGE_FORBID_NOT_FUNDS);
				}
				else {
					logger.info("充电开始固定电度充电,卡号:{},充电电度:{},余额:{},当前单价:{} 开始充电",
							cardInfo.getCardno(),degree,cardInfo.getMoney(),areaInfo.getElectricityprice());
					retMsg.setChargeCode(ChargeUtil.CHARGE_ALLOW);
				}
			}
			// 固定电度处理开始结束
			
			// 固定时间处理
			if (msg.getChargeConfig() == ChargeTypeUtil.FIX_TIME) {
				int timeLong = msg.getChargeData();
				logger.info("充电开始固定时间充电,卡号:{},余额:{},当前单价:{},充电时长:{}分钟,开始充电!",
						cardInfo.getCardno(),cardInfo.getMoney(),areaInfo.getElectricityprice());
				chargeLog.setChargeTime(timeLong);
				chargeLog.setChargeType(ChargeTypeUtil.DB_FIX_TIME);
				retMsg.setChargeCode(ChargeUtil.CHARGE_ALLOW);
			}
			
			// 固定金额处理
			if(msg.getChargeConfig() == ChargeTypeUtil.FIX_MONEY) {
				chargeLog.setAmount(msg.getChargeData());
				chargeLog.setChargeType(ChargeTypeUtil.DB_FIX_MONEY);
				if (cardInfo.getMoney() < msg.getChargeData()) {
					logger.info("充电开始固定金额充电,卡号:{},余额:{},充电金额:{} 余额不足!",
							cardInfo.getCardno(),cardInfo.getMoney(),msg.getChargeData()*100);
					retMsg.setChargeCode(ChargeUtil.CHARGE_FORBID_NOT_FUNDS);
				}
				else {
					logger.info("充电开始固定金额充电,卡号:{},余额:{},充电金额:{} 开始充电!",
							cardInfo.getCardno(),cardInfo.getMoney(),msg.getChargeData()*100);
					retMsg.setChargeCode(ChargeUtil.CHARGE_ALLOW);
				}
			}
			//直接充满处理
			if(msg.getChargeConfig() == ChargeTypeUtil.FIX_FULL) {
				chargeLog.setChargeType(ChargeTypeUtil.DB_FIX_FULL);
				if (cardInfo.getMoney() < ConstUtil.LEAST_BALANCE) {
					logger.info("充电开始直接充满充电,卡号:{},余额:{},账户余额:{} 小于10元!",
							cardInfo.getCardno(),cardInfo.getMoney(),cardInfo.getMoney());
					retMsg.setChargeCode(ChargeUtil.CHARGE_FORBID_NOT_FUNDS);
				}
				else {
					logger.info("充电开始直接充满充电,卡号:{},余额:{},账户余额:{} 开始充电!",
							cardInfo.getCardno(),cardInfo.getMoney(),cardInfo.getMoney());
					retMsg.setChargeCode(ChargeUtil.CHARGE_ALLOW);
				}
			}
			
			if(retMsg.getChargeCode() == ChargeUtil.CHARGE_FORBID_DATA_ERR) {
				logger.error("无法识别的充电方式,type:{}"+msg.getChargeConfig() );
			}
			else if (retMsg.getChargeCode() == ChargeUtil.CHARGE_ALLOW){
				// 允许充电，将卡状态变为充电状态，同时插入开始充电记录
				cardInfo.setState(CardStateUtil.DB_CARD_STATE_CHARGE);
				cardInfoService.updateCardInfo(cardInfo);
				chargeLog.setCreateDate(new Date());
				chargeLog.setBeginTime(new Date());
				chargeLog.setState(0);
				chargeLog.setCardNo(Integer.valueOf(cardInfo.getCardno()));
				chargeLog.setCdzId(Integer.valueOf(msg.getCdzNo()));
				chargeLog.setBeginMeterQua(msg.getMeterQua());
				chargeLogService.saveLog(chargeLog);
			}
		}
		catch(Exception e) {
			logger.error("开始充电错误"+e);
			retMsg.setChargeCode(ChargeUtil.CHARGE_FORBID_CON_ERR);
			return retMsg;
		}
		return retMsg;
	}
	
	@Transactional
	public ResIcStateMsg checkICCard(ReqICStateMsg msg) {
		ResIcStateMsg retMsg = new ResIcStateMsg();
		retMsg.setBalance(0);
		
		// 获取单价信息开始
		AreaInfo areaInfo = areaInfoService.getAndCHeckAreaInfo();
		if (areaInfo == null) {
			logger.error("无单价配置信息");
			retMsg.setCardState(CardStateUtil.CARD_FROZEN);
			retMsg.setPrice(0);
			return retMsg;
		}
		
		// 卡号校验及获取卡信息
		CardInfo cardInfo = cardInfoService.checkCardNo(msg.getCardNo());
		if (cardInfo == null) {
			logger.info("无此卡信息，卡号:{}",msg.getCardNo());
			retMsg.setCardState(0xf0);
			return retMsg;
		}

		retMsg.setPrice(areaInfo.getElectricityprice());
		
		if (!cardInfo.getCardpwd().equals(MethodUtil.MD5(msg.getPwd()+""))) {
			retMsg.setCardState(CardStateUtil.ERROR_PWD);
			return retMsg;
		}
		
		if (cardInfo.getState() == null) {
			retMsg.setCardState(CardStateUtil.CARD_UNKNOW);
			return retMsg;
		}
		
		if(cardInfo.getState() == CardStateUtil.DB_CARD_STATE_NORMAL) {
			retMsg.setBalance(cardInfo.getMoney().intValue());
			retMsg.setCardState(CardStateUtil.CARD_NORMAL);
			return retMsg;
		}
		else if (cardInfo.getState() == CardStateUtil.DB_CARD_STATE_FROZEN) {
			retMsg.setCardState(CardStateUtil.CARD_FROZEN);
			return retMsg;
		}
		else if(cardInfo.getState() == CardStateUtil.DB_CARD_STATE_CHARGE) {
			retMsg.setCardState(CardStateUtil.CARD_FROZEN);
			return retMsg;
		}
		else if(cardInfo.getState() == CardStateUtil.DB_CARD_STATE_LOSS) {
			retMsg.setCardState(CardStateUtil.CARD_LOSS);
			return retMsg;
		}
		if(cardInfo.getState() == CardStateUtil.DB_CARD_STATE_UNINIT) {
			retMsg.setBalance(cardInfo.getMoney().intValue());
			retMsg.setCardState(CardStateUtil.CARD_UNKNOW);
			return retMsg;
		}
		else {
			retMsg.setCardState(CardStateUtil.CARD_UNKNOW);
			return retMsg;
		}
	}
	

	public void deviceOffLine(String cdzNo) {
		cdzInfoService.offlineCdz(cdzNo);
	}

	public ResTimeCheckMsg reqTimeCheck(ReqTimeCheckMsg msg) {
		return MyDateUtil.getResTimeCheckMsg();
	}

	public ResFrozenIcMsg frozenIc(ReqFrozenIcMsg msg) {
		ResFrozenIcMsg retMsg = new ResFrozenIcMsg();
		try {
			
			// 卡号校验及获取卡信息
			CardInfo cardInfo = cardInfoService.checkCardNo(msg.getCardNo());
			if (cardInfo == null) {
				logger.info("无此卡信息，卡号:{}",msg.getCardNo());
				retMsg.setFrozenCode(FrozenUtil.FROZEN_FORBID);
				return retMsg;
			}
			cardInfo.setState(CardStateUtil.DB_CARD_STATE_FROZEN);
			cardInfoService.updateCardInfo(cardInfo);
			retMsg.setFrozenCode(FrozenUtil.FROZEN_ALLOW);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return retMsg;
	}

	public void keepAlive(ResKeepAliveMsg msg) {
		String cdzNo = msg.getCdzNo();
		
		List<CdzInfo> infoList = cdzInfoService.getCdzByNo(cdzNo);
		if (infoList == null || infoList.size() > 1) {
			logger.error("设备心跳，查询充电桩数量错误:cdzno:{}", cdzNo);
		}
		CdzInfo info = infoList.get(0);
		int runState = msg.getRunState();
		int workState = msg.getWorkState();
		logger.info("设备心跳，充电桩返回信息:runstate:{},workstate:{}",runState,workState);
		if (runState == CdzStateUtil.DEVICE_NORMAL) {
			info.setRunstate(CdzStateUtil.CDZ_NORMAL);
		}
		else if (runState == CdzStateUtil.DEVICE_FALT) {
			info.setRunstate(CdzStateUtil.CDZ_FALT);
		}
		else if (runState == CdzStateUtil.DEVICE_LOCK) {
			info.setRunstate(CdzStateUtil.CDZ_LOCK);
		}
		else {
			info.setRunstate(CdzStateUtil.CDZ_OFFLINE);
		}
		
		if (workState == CdzStateUtil.DEVICE_WORK_WAIT) {
			info.setWorkstate(CdzStateUtil.CDZ_WORK_WAIT);
		}
		else if (workState == CdzStateUtil.DEVICE_WORK_CONFIG) {
			info.setWorkstate(CdzStateUtil.CDZ_WORK_CONFIG);
		}
		else if (workState == CdzStateUtil.DEVICE_WORK_CHARGE) {
			info.setWorkstate(CdzStateUtil.CDZ_WORK_CHARGE);
		}
		else if (workState == CdzStateUtil.DEVICE_WORK_OVER) {
			info.setWorkstate(CdzStateUtil.CDZ_WORK_OVER);
		}
		else if (workState == CdzStateUtil.DEVICE_WORK_PAUSE) {
			info.setWorkstate(CdzStateUtil.CDZ_WORK_PAUSE);
		}
		else if (workState == CdzStateUtil.DEVICE_WORK_UPLOAD) {
			info.setWorkstate(CdzStateUtil.CDZ_WORK_UPLOAD);
		}
		else {
			info.setWorkstate(CdzStateUtil.CDZ_OFFLINE);
		}
		cdzInfoService.updateCdzInfo(info);
	}

	@Override
	public ResBeginChargeMsg beginCharge(ReqBeginChargeMsg msg) {
		return new ResBeginChargeMsg();
	}

	@Override
	public boolean commonCheckMsg(ReqBaseMsg bm) {
		String cdzNo = bm.getCdzNo();
		List<CdzInfo> infoList = cdzInfoService.getCdzByNo(cdzNo);
		if (infoList == null || infoList.size()==0 || infoList.size() > 1) {
			logger.error("帧共同检测，查询充电桩数量错误:cdzno:{}", cdzNo);
			return false;
		}
		
		return true;
	}

	@Override
	public List<CdzInfo> listAllDevices() {
		List<CdzInfo> infoList = cdzInfoService.listAll();
		if (infoList == null || infoList.size() == 0) {
			logger.error("无充电站信息");
		}
		return infoList;
	}

	@Override
	public void onLineDevicee(String cdzNo) {
		List<CdzInfo> infoList = cdzInfoService.getCdzByNo(cdzNo);
		if (infoList == null || infoList.size() == 0 || infoList.size() > 1) {
			logger.error("接收到设备处理消息，设备在线，未找到对应设备错误:cdzno:{}",cdzNo);
			return;
		}
		CdzInfo cInfo = infoList.get(0);
		if (cInfo.getRunstate() == null) {
			cInfo.setRunstate(CdzStateUtil.CDZ_NORMAL);
		}
		else if (cInfo.getRunstate() == CdzStateUtil.CDZ_NORMAL) {
			return;
		}
		cdzInfoService.updateCdzInfo(cInfo);
	}

}

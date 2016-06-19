package com.ylk.datamineservice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylk.datamineservice.mapper.ChargeLogMapper;
import com.ylk.datamineservice.model.ChargeLog;
import com.ylk.datamineservice.model.ChargeLogExample;
import com.ylk.datamineservice.service.ChargeLogService;
import com.ylk.datamineservice.util.ChargeDataTypeUtil;

@Service
public class ChargeLogServiceImpl implements ChargeLogService {
	Logger logger = LoggerFactory.getLogger(ChargeLogServiceImpl.class);
	@Resource
	private ChargeLogMapper chargeLogMapper;

	@Override
	public void saveLog(ChargeLog chargeLog) {
		chargeLogMapper.insert(chargeLog);
	}
	
	public void updateChargeLog(ChargeLog chargeLog) {
		chargeLogMapper.updateByPrimaryKey(chargeLog);
	}

	@Override
	public ChargeLog getStartChargeByCdzAndCardNo(int cdzId, String gunType, int cardNo) {
		ChargeLogExample ex = new ChargeLogExample();
		ex.setOrderByClause("id desc");
		ex.createCriteria().andStateEqualTo(0).andCdzIdEqualTo(cdzId).andCardNoEqualTo(cardNo).andGunTypeEqualTo(gunType);
		List<ChargeLog> logs = chargeLogMapper.selectByExample(ex);
		if (logs == null || logs.size() == 0 || logs.size() > 1) {
			logger.warn("查询开始充电信息错误数据:cdzId:{},cardNo:{}",cdzId,cardNo);
			return null;
		}
		else {
			return logs.get(0);
		}
	}

	@Override
	public void updateChargeLogComplete(ChargeLog startChargeLog) {
		startChargeLog.setState(1);
		chargeLogMapper.updateByPrimaryKey(startChargeLog);
	}

}

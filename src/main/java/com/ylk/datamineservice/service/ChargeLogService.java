package com.ylk.datamineservice.service;

import com.ylk.datamineservice.model.ChargeLog;

public interface ChargeLogService {

	void saveLog(ChargeLog chargeLog);

	ChargeLog getStartChargeByCdzAndCardNo(int deviceNo, int cardNo);

	void updateChargeLogComplete(ChargeLog startChargeLog);

	void updateChargeLog(ChargeLog startChargeLog);

}

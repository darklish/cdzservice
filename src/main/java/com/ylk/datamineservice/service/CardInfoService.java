package com.ylk.datamineservice.service;

import com.ylk.datamineservice.model.CardInfo;


public interface CardInfoService {

	void frozenCard(String cardNo);

	CardInfo checkCardNo(int cardNo);

	CardInfo getCardInfo(int cardNo);

	void updateCardInfo(CardInfo cardInfo);


}

package com.ylk.datamineservice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylk.datamineservice.mapper.CardInfoMapper;
import com.ylk.datamineservice.model.CardInfo;
import com.ylk.datamineservice.model.CardInfoExample;
import com.ylk.datamineservice.service.CardInfoService;
import com.ylk.datamineservice.util.CardStateUtil;

@Service
public class CardInfoServiceImpl implements CardInfoService {
	Logger logger = LoggerFactory.getLogger(CardInfoServiceImpl.class);
	@Resource
	private CardInfoMapper cardInfoMapper;
	@Override
	public void frozenCard(String cardNo) {
		CardInfoExample example = new CardInfoExample();
		example.createCriteria().andDeletedEqualTo(0).andCardnoEqualTo(cardNo);
		List<CardInfo> infos = cardInfoMapper.selectByExample(example);
		if (infos != null && infos.size() > 1) {
			CardInfo info = infos.get(0);
			info.setState(CardStateUtil.DB_CARD_STATE_FROZEN);
			cardInfoMapper.updateByPrimaryKey(info);
		}
		
	}
	@Override
	public CardInfo checkCardNo(int cardNo) {
		CardInfoExample example = new CardInfoExample();
		example.createCriteria().andDeletedEqualTo(0).andCardnoEqualTo(cardNo+"");
		List<CardInfo> infos = cardInfoMapper.selectByExample(example);
		if (infos == null || infos.size() == 0) {
			return null;
		}
		else {
			return infos.get(0);
		}
	}
	
	@Override
	public CardInfo getCardInfo(int cardNo) {
		CardInfoExample example = new CardInfoExample();
		example.createCriteria().andDeletedEqualTo(0).andCardnoEqualTo(cardNo+"");
		List<CardInfo> infos = cardInfoMapper.selectByExample(example);
			return infos.get(0);
	}
	@Override
	public void updateCardInfo(CardInfo cardInfo) {
		cardInfoMapper.updateByPrimaryKey(cardInfo);	
	}

}

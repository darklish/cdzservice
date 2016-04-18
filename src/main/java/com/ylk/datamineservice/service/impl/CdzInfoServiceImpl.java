package com.ylk.datamineservice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylk.datamineservice.mapper.CdzInfoMapper;
import com.ylk.datamineservice.model.CdzInfo;
import com.ylk.datamineservice.model.CdzInfoExample;
import com.ylk.datamineservice.service.CdzInfoService;
import com.ylk.datamineservice.util.CdzStateUtil;

@Service
public class CdzInfoServiceImpl implements CdzInfoService {
	@Resource
	private CdzInfoMapper cdzInfoMapper;
	
	public List<CdzInfo> listAll() {
		CdzInfoExample example = new CdzInfoExample();
		example.createCriteria().andDeletedEqualTo(0);
		return cdzInfoMapper.selectByExample(example);
	}

	@Override
	public void offlineCdz(String cdzNo) {
		CdzInfoExample ex = new CdzInfoExample();
		ex.createCriteria().andCdznoEqualTo(cdzNo).andDeletedEqualTo(0);
		List<CdzInfo> infos = cdzInfoMapper.selectByExample(ex);
		if (infos != null && infos.size() > 0) {
			CdzInfo cInfo = infos.get(0);
			cInfo.setRunstate(CdzStateUtil.CDZ_OFFLINE);
			cdzInfoMapper.updateByPrimaryKey(cInfo);
		}
	}

	@Override
	public List<CdzInfo> getCdzByNo(String cdzNo) {
		CdzInfoExample example = new CdzInfoExample();
		example.createCriteria().andDeletedEqualTo(0).andCdznoEqualTo(cdzNo);
		return cdzInfoMapper.selectByExample(example);
	}

	@Override
	public void updateCdzInfo(CdzInfo info) {
		cdzInfoMapper.updateByPrimaryKey(info);
	}
	

}

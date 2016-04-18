package com.ylk.datamineservice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylk.datamineservice.mapper.AreaInfoMapper;
import com.ylk.datamineservice.model.AreaInfo;
import com.ylk.datamineservice.model.AreaInfoExample;
import com.ylk.datamineservice.service.AreaInfoService;

@Service
public class AreaInfoServiceImpl implements AreaInfoService {
	Logger logger = LoggerFactory.getLogger(AreaInfoServiceImpl.class);
	@Resource
	private AreaInfoMapper areaInfoMapper;

	@Override
	public AreaInfo getAndCHeckAreaInfo() {
		AreaInfoExample pExample = new AreaInfoExample();
		pExample.setOrderByClause(" id desc");
		pExample.createCriteria().andStateEqualTo(0);
		List<AreaInfo> infos = areaInfoMapper.selectByExample(pExample);
		if (infos == null || infos.size() > 1) {
			logger.warn("区域配置信息为空，或者多条!");
		}
		
		if (infos == null || infos.size() == 0) {
			return null;
		}
		else {
			return infos.get(0);
		}
		
	}

	@Override
	public AreaInfo getAreaInfo() {
		AreaInfoExample pExample = new AreaInfoExample();
		pExample.setOrderByClause(" id desc");
		pExample.createCriteria().andStateEqualTo(0);
		List<AreaInfo> infos = areaInfoMapper.selectByExample(pExample);
		return infos.get(0);
	}

}

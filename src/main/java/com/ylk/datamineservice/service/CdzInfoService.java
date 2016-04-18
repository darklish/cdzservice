package com.ylk.datamineservice.service;

import java.util.List;

import com.ylk.datamineservice.model.CdzInfo;

public interface CdzInfoService {

	List<CdzInfo> listAll();

	void offlineCdz(String cdzNo);

	List<CdzInfo> getCdzByNo(String cdzNo);

	void updateCdzInfo(CdzInfo info);

}

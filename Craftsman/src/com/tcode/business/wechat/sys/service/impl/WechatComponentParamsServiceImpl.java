package com.tcode.business.wechat.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.dao.WechatComponentParamsDao;
import com.tcode.business.wechat.sys.model.WechatComponentParams;
import com.tcode.business.wechat.sys.service.WechatComponentParamsService;
import com.tcode.core.util.Utils;

@Component("componentParamsService")
public class WechatComponentParamsServiceImpl implements WechatComponentParamsService {
	
	private WechatComponentParamsDao wechatComponentParamsDao;

	@Override
	public void insert(WechatComponentParams wechatComponentParams) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatComponentParams.setCreateTime(currentTime);
		wechatComponentParams.setUpdateTime(currentTime);
		wechatComponentParamsDao.save(wechatComponentParams);
	}

	@Override
	public void update(WechatComponentParams wechatComponentParams) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatComponentParams.setUpdateTime(currentTime);
		wechatComponentParamsDao.edit(wechatComponentParams);
	}

	@Override
	public void delete(WechatComponentParams wechatComponentParams) throws Exception {
		wechatComponentParamsDao.remove(wechatComponentParams);
	}

	@Override
	public List<WechatComponentParams> getAll() throws Exception {
		return wechatComponentParamsDao.loadAll();
	}

	
	public WechatComponentParamsDao getWechatComponentParamsDao() {
		return wechatComponentParamsDao;
	}

	@Resource
	public void setWechatComponentParamsDao(WechatComponentParamsDao wechatComponentParamsDao) {
		this.wechatComponentParamsDao = wechatComponentParamsDao;
	}
	
}

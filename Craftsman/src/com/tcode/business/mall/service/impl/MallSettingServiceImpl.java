package com.tcode.business.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallSettingDao;
import com.tcode.business.mall.model.MallSetting;
import com.tcode.business.mall.service.MallSettingService;

@Component("mallSettingService")
public class MallSettingServiceImpl implements MallSettingService {
	
	private MallSettingDao mallSettingDao;

	@Override
	public MallSetting getById(String appId) throws Exception {
		return mallSettingDao.loadById(appId);
	}

	@Override
	public void insert(MallSetting setting) throws Exception {
		mallSettingDao.save(setting);
	}

	@Override
	public void update(MallSetting setting) throws Exception {
		mallSettingDao.edit(setting);
	}

	@Override
	public void delete(MallSetting setting) throws Exception {
		mallSettingDao.remove(setting);
	}

	public MallSettingDao getMallSettingDao() {
		return mallSettingDao;
	}
	@Resource
	public void setMallSettingDao(MallSettingDao mallSettingDao) {
		this.mallSettingDao = mallSettingDao;
	}

}

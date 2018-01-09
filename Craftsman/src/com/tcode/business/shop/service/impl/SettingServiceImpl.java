package com.tcode.business.shop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.shop.dao.SettingDao;
import com.tcode.business.shop.model.Setting;
import com.tcode.business.shop.service.SettingService;
import com.tcode.core.util.Utils;

@Component("settingService")
public class SettingServiceImpl implements SettingService {
	
	private SettingDao settingDao;

	@Override
	public Setting getById(String deptCode) throws Exception {
		Setting setting = settingDao.loadById(deptCode);
		if(Utils.isEmpty(setting))
			setting = settingDao.loadById("XXXX");
		return setting;
	}

	@Override
	public void insert(Setting setting) throws Exception {
		settingDao.save(setting);
	}

	@Override
	public void update(Setting setting) throws Exception {
		settingDao.edit(setting);
	}

	@Override
	public void delete(Setting setting) throws Exception {
		settingDao.remove(setting);
	}

	public SettingDao getSettingDao() {
		return settingDao;
	}
	@Resource
	public void setSettingDao(SettingDao settingDao) {
		this.settingDao = settingDao;
	}

	

	
}

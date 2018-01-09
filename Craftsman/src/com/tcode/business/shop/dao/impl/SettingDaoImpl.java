package com.tcode.business.shop.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.tcode.business.shop.dao.SettingDao;
import com.tcode.business.shop.model.Setting;
import com.tcode.common.dao.BaseDao;

@Component("settingDao")
public class SettingDaoImpl extends BaseDao<Setting, Serializable> implements SettingDao {

	@Override
	public Setting loadById(String deptCode) throws Exception {
		return super.loadById(Setting.class, deptCode);
	}
}

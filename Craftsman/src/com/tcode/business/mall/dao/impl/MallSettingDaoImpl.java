package com.tcode.business.mall.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallSettingDao;
import com.tcode.business.mall.model.MallSetting;
import com.tcode.common.dao.BaseDao;

@Component("mallSettingDao")
public class MallSettingDaoImpl extends BaseDao<MallSetting, Serializable> implements MallSettingDao {

	@Override
	public MallSetting loadById(String appId) throws Exception {
		return (MallSetting)super.loadEntity("from MallSetting s where s.appId = ?", appId);
	}

}

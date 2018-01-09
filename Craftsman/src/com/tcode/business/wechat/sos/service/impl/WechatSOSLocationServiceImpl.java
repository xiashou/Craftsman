package com.tcode.business.wechat.sos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sos.dao.WechatSOSLocationDao;
import com.tcode.business.wechat.sos.model.WechatSOSLocation;
import com.tcode.business.wechat.sos.service.WechatSOSLocationService;
import com.tcode.business.wechat.sos.vo.WechatSOSLocationVo;
import com.tcode.core.util.Utils;

@Component("wechatSOSLocationService")
public class WechatSOSLocationServiceImpl implements WechatSOSLocationService {
	
	private WechatSOSLocationDao wechatSOSLocationDao;

	@Override
	public void insert(WechatSOSLocation wechatSOSLocation) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatSOSLocation.setCreateTime(currentTime);
		wechatSOSLocation.setUpdateTime(currentTime);
		wechatSOSLocationDao.save(wechatSOSLocation);

	}

	@Override
	public void update(WechatSOSLocation wechatSOSLocation) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatSOSLocation.setUpdateTime(currentTime);
		wechatSOSLocationDao.edit(wechatSOSLocation);

	}

	@Override
	public List<WechatSOSLocation> getByTemplateCodeAndOpenIdForApp(String openId, String appId, String deptCode)
			throws Exception {
		return wechatSOSLocationDao.loadByTemplateCodeAndOpenIdForApp(openId, appId, deptCode);
	}

	@Override
	public List<WechatSOSLocation> getListPage(WechatSOSLocationVo wechatSOSLocationVo, int start, int limit)
			throws Exception {
			return wechatSOSLocationDao.loadListPage(wechatSOSLocationVo, start, limit);
	}

	@Override
	public Integer getListCount(WechatSOSLocationVo wechatSOSLocationVo) throws Exception {
		return wechatSOSLocationDao.loadListCount(wechatSOSLocationVo);
	}

	public WechatSOSLocationDao getWechatSOSLocationDao() {
		return wechatSOSLocationDao;
	}

	@Resource
	public void setWechatSOSLocationDao(WechatSOSLocationDao wechatSOSLocationDao) {
		this.wechatSOSLocationDao = wechatSOSLocationDao;
	}

}

package com.tcode.business.wechat.act.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.WechatActivityNumDao;
import com.tcode.business.wechat.act.model.WechatActivityNum;
import com.tcode.business.wechat.act.service.WechatActivityNumService;
import com.tcode.business.wechat.act.vo.WechatActivityNumVo;
import com.tcode.core.util.Utils;

@Component("wechatActivityNumService")
public class WechatActivityNumServiceImpl implements WechatActivityNumService {

	private WechatActivityNumDao wechatActivityNumDao;
	
	@Override
	public void insert(WechatActivityNum wechatActivityNum) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatActivityNum.setCreateTime(currentTime);
		wechatActivityNum.setUpdateTime(currentTime);
		wechatActivityNumDao.save(wechatActivityNum);
	}

	@Override
	public void update(WechatActivityNum wechatActivityNum) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatActivityNum.setUpdateTime(currentTime);
		wechatActivityNumDao.edit(wechatActivityNum);
	}

	@Override
	public void delete(WechatActivityNum wechatActivityNum) throws Exception {
		wechatActivityNumDao.remove(wechatActivityNum);
	}

	@Override
	public WechatActivityNum getById(Integer id) throws Exception {
		return wechatActivityNumDao.loadById(id);
	}

	@Override
	public List<WechatActivityNum> getAll() throws Exception {
		return wechatActivityNumDao.loadAll();
	}

	@Override
	public List<WechatActivityNum> getListPage(WechatActivityNumVo wechatActivityNumVo, int start, int limit)
			throws Exception {
		return wechatActivityNumDao.loadListPage(wechatActivityNumVo, start, limit);
	}

	@Override
	public Integer getListCount(WechatActivityNumVo wechatActivityNumVo) throws Exception {
		return wechatActivityNumDao.loadListCount(wechatActivityNumVo);
	}

	@Override
	public List<WechatActivityNum> getByActivityCode(String activityCode) throws Exception {
		return wechatActivityNumDao.loadByActivityCode(activityCode);
	}

	@Override
	public List<WechatActivityNum> getByActivityCode(String activityCode, String openId, String appId)
			throws Exception {
		return wechatActivityNumDao.loadByActivityCode(activityCode, openId, appId);
	}

	public WechatActivityNumDao getWechatActivityNumDao() {
		return wechatActivityNumDao;
	}

	@Resource
	public void setWechatActivityNumDao(WechatActivityNumDao wechatActivityNumDao) {
		this.wechatActivityNumDao = wechatActivityNumDao;
	}

}

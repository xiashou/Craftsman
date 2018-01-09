package com.tcode.business.wechat.act.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.WechatActivityLotteryPartDao;
import com.tcode.business.wechat.act.model.WechatActivityLotteryPart;
import com.tcode.business.wechat.act.service.WechatActivityLotteryPartService;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryPartVo;
import com.tcode.core.util.Utils;

@Component("wechatActivityLotteryPartService")
public class WechatActivityLotteryPartServiceImpl implements WechatActivityLotteryPartService {
	
	private WechatActivityLotteryPartDao wechatActivityLotteryPartDao;

	@Override
	public void insert(WechatActivityLotteryPart wechatActivityLotteryPart) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatActivityLotteryPart.setCreateTime(currentTime);
		wechatActivityLotteryPart.setUpdateTime(currentTime);
		wechatActivityLotteryPartDao.save(wechatActivityLotteryPart);
	}

	@Override
	public void update(WechatActivityLotteryPart wechatActivityLotteryPart) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatActivityLotteryPart.setUpdateTime(currentTime);
		wechatActivityLotteryPartDao.edit(wechatActivityLotteryPart);
	}

	@Override
	public void delete(WechatActivityLotteryPart wechatActivityLotteryPart) throws Exception {
		wechatActivityLotteryPartDao.remove(wechatActivityLotteryPart);
	}

	@Override
	public WechatActivityLotteryPart getById(Integer id) throws Exception {
		return wechatActivityLotteryPartDao.loadById(id);
	}

	@Override
	public List<WechatActivityLotteryPart> getAll() throws Exception {
		return wechatActivityLotteryPartDao.loadAll();
	}

	@Override
	public List<WechatActivityLotteryPart> getListPage(WechatActivityLotteryPartVo wechatActivityLotteryPartVo,
			int start, int limit) throws Exception {
		return wechatActivityLotteryPartDao.loadListPage(wechatActivityLotteryPartVo, start, limit);
	}

	@Override
	public Integer getListCount(WechatActivityLotteryPartVo wechatActivityLotteryPartVo) throws Exception {
		return wechatActivityLotteryPartDao.loadListCount(wechatActivityLotteryPartVo);
	}

	@Override
	public List<WechatActivityLotteryPart> getByActivityCode(String activityCode) throws Exception {
		return wechatActivityLotteryPartDao.loadByActivityCode(activityCode);
	}
	
	@Override
	public List<WechatActivityLotteryPart> getByActivityCode(String activityCode, String openId, String appId)
			throws Exception {
		return wechatActivityLotteryPartDao.loadByActivityCode(activityCode, openId, appId);
	}

	public WechatActivityLotteryPartDao getWechatActivityLotteryPartDao() {
		return wechatActivityLotteryPartDao;
	}

	@Resource
	public void setWechatActivityLotteryPartDao(WechatActivityLotteryPartDao wechatActivityLotteryPartDao) {
		this.wechatActivityLotteryPartDao = wechatActivityLotteryPartDao;
	}
}

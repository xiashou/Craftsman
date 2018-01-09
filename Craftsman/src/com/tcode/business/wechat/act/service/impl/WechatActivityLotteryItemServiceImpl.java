package com.tcode.business.wechat.act.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.WechatActivityLotteryItemDao;
import com.tcode.business.wechat.act.model.WechatActivityLotteryItem;
import com.tcode.business.wechat.act.service.WechatActivityLotteryItemService;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryItemVo;
import com.tcode.core.util.Utils;

@Component("wechatActivityLotteryItemService")
public class WechatActivityLotteryItemServiceImpl implements WechatActivityLotteryItemService {
	
	private WechatActivityLotteryItemDao wechatActivityLotteryItemDao;

	@Override
	public void insert(WechatActivityLotteryItem wechatActivityLotteryItem) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatActivityLotteryItem.setCreateTime(currentTime);
		wechatActivityLotteryItem.setUpdateTime(currentTime);
		wechatActivityLotteryItemDao.save(wechatActivityLotteryItem);
	}

	@Override
	public void update(WechatActivityLotteryItem wechatActivityLotteryItem) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatActivityLotteryItem.setUpdateTime(currentTime);
		wechatActivityLotteryItemDao.edit(wechatActivityLotteryItem);
	}

	@Override
	public void delete(WechatActivityLotteryItem wechatActivityLotteryItem) throws Exception {
		wechatActivityLotteryItemDao.remove(wechatActivityLotteryItem);
	}

	@Override
	public WechatActivityLotteryItem getById(Integer id) throws Exception {
		return wechatActivityLotteryItemDao.loadById(id);
	}

	@Override
	public List<WechatActivityLotteryItem> getAll() throws Exception {
		return wechatActivityLotteryItemDao.loadAll();
	}

	@Override
	public List<WechatActivityLotteryItem> getListPage(WechatActivityLotteryItemVo wechatActivityLotteryItemVo,
			int start, int limit) throws Exception {
		return wechatActivityLotteryItemDao.loadListPage(wechatActivityLotteryItemVo, start, limit);
	}

	@Override
	public Integer getListCount(WechatActivityLotteryItemVo wechatActivityLotteryItemVo) throws Exception {
		return wechatActivityLotteryItemDao.loadListCount(wechatActivityLotteryItemVo);
	}

	@Override
	public List<WechatActivityLotteryItem> getByActivityCode(String activityCode) throws Exception {
		return wechatActivityLotteryItemDao.loadByActivityCode(activityCode);
	}

	public WechatActivityLotteryItemDao getWechatActivityLotteryItemDao() {
		return wechatActivityLotteryItemDao;
	}

	@Resource
	public void setWechatActivityLotteryItemDao(WechatActivityLotteryItemDao wechatActivityLotteryItemDao) {
		this.wechatActivityLotteryItemDao = wechatActivityLotteryItemDao;
	}

}

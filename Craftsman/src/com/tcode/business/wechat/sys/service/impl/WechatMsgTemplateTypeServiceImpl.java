package com.tcode.business.wechat.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.dao.WechatMsgTemplateTypeDao;
import com.tcode.business.wechat.sys.model.WechatMsgTemplateType;
import com.tcode.business.wechat.sys.service.WechatMsgTemplateTypeService;
import com.tcode.business.wechat.sys.vo.WechatMsgTemplateTypeVo;
import com.tcode.core.util.Utils;

@Component("wechatMsgTemplateTypeService")
public class WechatMsgTemplateTypeServiceImpl implements WechatMsgTemplateTypeService {
	
	private WechatMsgTemplateTypeDao wechatMsgTemplateTypeDao;

	@Override
	public void insert(WechatMsgTemplateType wechatMsgTemplateType) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatMsgTemplateType.setCreateTime(currentTime);
		wechatMsgTemplateType.setUpdateTime(currentTime);
		wechatMsgTemplateTypeDao.save(wechatMsgTemplateType);
	}

	@Override
	public void update(WechatMsgTemplateType wechatMsgTemplateType) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatMsgTemplateType.setUpdateTime(currentTime);
		wechatMsgTemplateTypeDao.edit(wechatMsgTemplateType);
	}

	@Override
	public void delete(WechatMsgTemplateType wechatMsgTemplateType) throws Exception {
		wechatMsgTemplateTypeDao.remove(wechatMsgTemplateType);
	}

	@Override
	public List<WechatMsgTemplateType> getAll() throws Exception {
		return wechatMsgTemplateTypeDao.loadAll();
	}
	
	@Override
	public List<WechatMsgTemplateType> getListPage(WechatMsgTemplateTypeVo wechatMsgTemplateTypeVo, int start, int limit)
			throws Exception {
		return wechatMsgTemplateTypeDao.loadListPage(wechatMsgTemplateTypeVo, start, limit);
	}
	
	@Override
	public Integer getListCount(WechatMsgTemplateTypeVo wechatMsgTemplateTypeVo) throws Exception {
		return wechatMsgTemplateTypeDao.loadListCount(wechatMsgTemplateTypeVo);
	}
	
	@Override
	public List<WechatMsgTemplateType> getByTemplateNo(String templateNo) throws Exception {
		return wechatMsgTemplateTypeDao.loadByTemplateNo(templateNo);
	}
	
	public WechatMsgTemplateTypeDao getWechatMsgTemplateTypeDao() {
		return wechatMsgTemplateTypeDao;
	}

	@Resource
	public void setWechatMsgTemplateTypeDao(WechatMsgTemplateTypeDao wechatMsgTemplateTypeDao) {
		this.wechatMsgTemplateTypeDao = wechatMsgTemplateTypeDao;
	}
	

}

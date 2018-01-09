package com.tcode.business.wechat.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.dao.WechatMsgTemplateDao;
import com.tcode.business.wechat.sys.model.WechatMsgTemplate;
import com.tcode.business.wechat.sys.service.WechatMsgTemplateService;
import com.tcode.business.wechat.sys.vo.WechatMsgTemplateVo;
import com.tcode.core.util.Utils;

@Component("wechatMsgTemplateService")
public class WechatMsgTemplateServiceImpl implements WechatMsgTemplateService {
	
	private WechatMsgTemplateDao wechatMsgTemplateDao;

	@Override
	public void insert(WechatMsgTemplate wechatMsgTemplate) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatMsgTemplate.setCreateTime(currentTime);
		wechatMsgTemplate.setUpdateTime(currentTime);
		wechatMsgTemplateDao.save(wechatMsgTemplate);
	}

	@Override
	public void update(WechatMsgTemplate wechatMsgTemplate) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatMsgTemplate.setUpdateTime(currentTime);
		wechatMsgTemplateDao.edit(wechatMsgTemplate);
	}

	@Override
	public void delete(WechatMsgTemplate wechatMsgTemplate) throws Exception {
		wechatMsgTemplateDao.remove(wechatMsgTemplate);
	}

	@Override
	public List<WechatMsgTemplate> getAll() throws Exception {
		return wechatMsgTemplateDao.loadAll();
	}
	
	@Override
	public List<WechatMsgTemplate> getListPage(WechatMsgTemplateVo wechatMsgTemplateVo, int start, int limit)
			throws Exception {
		return wechatMsgTemplateDao.loadListPage(wechatMsgTemplateVo, start, limit);
	}
	
	@Override
	public Integer getListCount(WechatMsgTemplateVo wechatMsgTemplateVo) throws Exception {
		return wechatMsgTemplateDao.loadListCount(wechatMsgTemplateVo);
	}
	
	@Override
	public List<WechatMsgTemplate> getBydepNoAndTemplateNo(String depNo, String templateNo) throws Exception {
		return wechatMsgTemplateDao.loadBydepNoAndTemplateNo(depNo, templateNo);
	}
	
	@Override
	public List<WechatMsgTemplate> getByCompanyIdAndTemplateNo(String companyId, String templateNo) throws Exception {
		return wechatMsgTemplateDao.loadByCompanyIdAndTemplateNo(companyId, templateNo);
	}
	
	@Override
	public List<WechatMsgTemplate> getBydepNo(String depNo) throws Exception {
		return wechatMsgTemplateDao.loadBydepNo(depNo);
	}

	
	public WechatMsgTemplateDao getWechatMsgTemplateDao() {
		return wechatMsgTemplateDao;
	}

	@Resource
	public void setWechatMsgTemplateDao(WechatMsgTemplateDao wechatMsgTemplateDao) {
		this.wechatMsgTemplateDao = wechatMsgTemplateDao;
	}
	

}

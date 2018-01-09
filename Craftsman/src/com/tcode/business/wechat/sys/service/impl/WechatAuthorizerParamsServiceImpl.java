package com.tcode.business.wechat.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.dao.WechatAuthorizerParamsDao;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.service.WechatAuthorizerParamsService;
import com.tcode.business.wechat.sys.vo.WechatAuthorizerParamsVo;
import com.tcode.core.util.Utils;

@Component("wechatAuthorizerParamsService")
public class WechatAuthorizerParamsServiceImpl implements WechatAuthorizerParamsService {
	
	private WechatAuthorizerParamsDao wechatAuthorizerParamsDao;

	@Override
	public void insert(WechatAuthorizerParams wechatAuthorizerParams) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatAuthorizerParams.setCreateTime(currentTime);
		wechatAuthorizerParams.setUpdateTime(currentTime);
		wechatAuthorizerParamsDao.save(wechatAuthorizerParams);
	}

	@Override
	public void update(WechatAuthorizerParams wechatAuthorizerParams) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatAuthorizerParams.setUpdateTime(currentTime);
		wechatAuthorizerParamsDao.edit(wechatAuthorizerParams);
	}

	@Override
	public void delete(WechatAuthorizerParams wechatAuthorizerParams) throws Exception {
		wechatAuthorizerParamsDao.remove(wechatAuthorizerParams);
	}
	
	@Override
	public WechatAuthorizerParams getById(Integer id) throws Exception {
		return wechatAuthorizerParamsDao.loadById(id);
	}

	@Override
	public List<WechatAuthorizerParams> getAll() throws Exception {
		return wechatAuthorizerParamsDao.loadAll();
	}
	
	@Override
	public List<WechatAuthorizerParams> getListPage(WechatAuthorizerParamsVo wechatAuthorizerParamsVo, int start,
			int limit) throws Exception {
		return wechatAuthorizerParamsDao.loadListPage(wechatAuthorizerParamsVo, start, limit);
	}
	
	@Override
	public Integer getListCount(WechatAuthorizerParamsVo wechatAuthorizerParamsVo) throws Exception {
		return wechatAuthorizerParamsDao.loadListCount(wechatAuthorizerParamsVo);
	}
	
	@Override
	public List<WechatAuthorizerParams> getBySId(String sid) throws Exception {
		return wechatAuthorizerParamsDao.loadBySId(sid);
	}
	
	@Override
	public List<WechatAuthorizerParams> getByAuthorizerStatus(int authorizerStatus) throws Exception {
		return wechatAuthorizerParamsDao.loadByAuthorizerStatus(authorizerStatus);
	}
	
	@Override
	public List<WechatAuthorizerParams> getByAuthorizerAppId(String authorizerAppId) throws Exception {
		return wechatAuthorizerParamsDao.loadByAuthorizerAppId(authorizerAppId);
	}
	
	@Override
	public List<WechatAuthorizerParams> getByDeptCode(String deptCode) throws Exception {
		return wechatAuthorizerParamsDao.loadByDeptCode(deptCode);
	}
	
	@Override
	public List<WechatAuthorizerParams> getByCompanyId(String companyId) throws Exception {
		return wechatAuthorizerParamsDao.loadByCompanyId(companyId);
	}
	
	@Override
	public List<WechatAuthorizerParams> loadBySid(String sid) throws Exception {
		return wechatAuthorizerParamsDao.loadBySId(sid);
	}

	
	public WechatAuthorizerParamsDao getWechatAuthorizerParamsDao() {
		return wechatAuthorizerParamsDao;
	}

	@Resource
	public void setWechatAuthorizerParamsDao(WechatAuthorizerParamsDao wechatAuthorizerParamsDao) {
		this.wechatAuthorizerParamsDao = wechatAuthorizerParamsDao;
	}
	
}

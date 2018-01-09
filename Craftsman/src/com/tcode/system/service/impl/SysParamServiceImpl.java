package com.tcode.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.core.util.Utils;
import com.tcode.system.dao.SysParamDao;
import com.tcode.system.model.SysParam;
import com.tcode.system.service.SysParamService;

@Component("sysParamService")
public class SysParamServiceImpl implements SysParamService {

	private SysParamDao sysParamDao;
	
	@Override
	public List<SysParam> getAll() throws Exception {
		return sysParamDao.loadAll();
	}
	
	@Override
	public Map<String, String> getSysParamMap() throws Exception {
		Map<String, String> sysParamMap = new HashMap<String, String>();
		List<SysParam> sysParamList = sysParamDao.loadAll();
		if(!Utils.isEmpty(sysParamList)){
			for(SysParam sysParam : sysParamList){
				sysParamMap.put(sysParam.getParamKey(), sysParam.getParamValue());
			}
		}
		return sysParamMap;
	}

	@Override
	public SysParam getParamById(Integer id) throws Exception {
		return sysParamDao.loadById(id);
	}

	@Override
	public void insert(SysParam param) throws Exception {
		sysParamDao.save(param);
	}

	@Override
	public void update(SysParam param) throws Exception {
		sysParamDao.edit(param);
	}

	@Override
	public void delete(SysParam param) throws Exception {
		sysParamDao.remove(param);
	}

	@Override
	public List<SysParam> getListPage(SysParam param, int start, int limit) throws Exception {
		return sysParamDao.loadListPage(param, start, limit);
	}

	@Override
	public Integer getListCount(SysParam param) throws Exception {
		return sysParamDao.loadListCount(param);
	}

	public SysParamDao getSysParamDao() {
		return sysParamDao;
	}
	@Resource
	public void setSysParamDao(SysParamDao sysParamDao) {
		this.sysParamDao = sysParamDao;
	}

	@Override
	public SysParam getParamByKey(String paramKey) throws Exception {
		return sysParamDao.loadByKey(paramKey);
	}
}

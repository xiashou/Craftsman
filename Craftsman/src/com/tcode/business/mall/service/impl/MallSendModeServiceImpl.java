package com.tcode.business.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallSendModeDao;
import com.tcode.business.mall.model.MallSendMode;
import com.tcode.business.mall.service.MallSendModeService;

@Component("mallSendModeService")
public class MallSendModeServiceImpl implements MallSendModeService {
	
	private MallSendModeDao mallSendModeDao;

	@Override
	public List<MallSendMode> getAll() throws Exception {
		return mallSendModeDao.loadAll();
	}

	@Override
	public MallSendMode getById(Integer id) throws Exception {
		return mallSendModeDao.loadById(id);
	}
	
	@Override
	public List<MallSendMode> getListByAppId(String appId) throws Exception {
		return mallSendModeDao.loadByAppId(appId);
	}

	@Override
	public void insert(MallSendMode sendMode) throws Exception {
		mallSendModeDao.save(sendMode);
	}

	@Override
	public void update(MallSendMode sendMode) throws Exception {
		mallSendModeDao.edit(sendMode);
	}

	@Override
	public void delete(MallSendMode sendMode) throws Exception {
		mallSendModeDao.remove(sendMode);
	}

	@Override
	public List<MallSendMode> getListPage(MallSendMode sendMode, int start, int limit) throws Exception {
		return mallSendModeDao.loadListPage(sendMode, start, limit);
	}

	@Override
	public Integer getListCount(MallSendMode sendMode) throws Exception {
		return mallSendModeDao.loadListCount(sendMode);
	}

	public MallSendModeDao getMallSendModeDao() {
		return mallSendModeDao;
	}
	@Resource
	public void setMallSendModeDao(MallSendModeDao mallSendModeDao) {
		this.mallSendModeDao = mallSendModeDao;
	}

}

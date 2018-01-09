package com.tcode.business.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallAddressDao;
import com.tcode.business.mall.model.MallAddress;
import com.tcode.business.mall.service.MallAddressService;

@Component("mallAddressService")
public class MallAddressServiceImpl implements MallAddressService {
	
	private MallAddressDao mallAddressDao;

	@Override
	public MallAddress getById(Integer id) throws Exception {
		return mallAddressDao.loadById(id);
	}

	@Override
	public List<MallAddress> getListByMemId(Integer memId) throws Exception {
		return mallAddressDao.loadByMemId(memId);
	}
	
	@Override
	public void updateAddressDefault(Integer memId, Integer id) throws Exception {
		mallAddressDao.cancelDefalue(memId);
		MallAddress add = mallAddressDao.loadById(id);
		add.setIsDefault(true);
		mallAddressDao.edit(add);
	}

	@Override
	public void insert(MallAddress address) throws Exception {
		mallAddressDao.save(address);
	}

	@Override
	public void update(MallAddress address) throws Exception {
		mallAddressDao.edit(address);
	}

	@Override
	public void delete(MallAddress address) throws Exception {
		mallAddressDao.remove(address);
	}

	public MallAddressDao getMallAddressDao() {
		return mallAddressDao;
	}
	@Resource
	public void setMallAddressDao(MallAddressDao mallAddressDao) {
		this.mallAddressDao = mallAddressDao;
	}

	
}

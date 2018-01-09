package com.tcode.business.mall.service;

import java.util.List;

import com.tcode.business.mall.model.MallAddress;

public interface MallAddressService {

	public MallAddress getById(Integer id) throws Exception;
	
	public List<MallAddress> getListByMemId(Integer memId) throws Exception;
	
	public void updateAddressDefault(Integer memId, Integer id) throws Exception;
	
	public void insert(MallAddress address) throws Exception;
	public void update(MallAddress address) throws Exception;
	public void delete(MallAddress address) throws Exception;
	
}

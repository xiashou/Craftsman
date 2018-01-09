package com.tcode.business.mall.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallAddressDao;
import com.tcode.business.mall.model.MallAddress;
import com.tcode.common.dao.BaseDao;

@Component("mallAddressDao")
public class MallAddressDaoImpl extends BaseDao<MallAddress, Serializable> implements MallAddressDao {

	@Override
	public MallAddress loadById(Integer id) throws Exception {
		return super.loadById(MallAddress.class, id);
	}

	@Override
	public List<MallAddress> loadByMemId(Integer memId) throws Exception {
		return super.loadList("from MallAddress a where a.memId = ? order by a.createTime desc", memId);
	}
	
	public void cancelDefalue(Integer memId) throws Exception {
		super.executeHql("update MallAddress a set a.isDefault = 0 where a.memId = ?", memId);
	}

}

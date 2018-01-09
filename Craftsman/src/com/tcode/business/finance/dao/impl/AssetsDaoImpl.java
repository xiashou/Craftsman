package com.tcode.business.finance.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.finance.dao.AssetsDao;
import com.tcode.business.finance.model.Assets;
import com.tcode.common.dao.BaseDao;

@Component("assetsDao")
public class AssetsDaoImpl extends BaseDao<Assets, Serializable> implements AssetsDao {

	@Override
	public Assets loadById(Integer id) throws Exception {
		return super.loadById(Assets.class, id);
	}

	@Override
	public List<Assets> loadByDept(String deptCode) throws Exception {
		return super.loadList("from Assets a where a.deptCode = ? order by id ", deptCode);
	}
	
	@Override
	public void editPriceById(Integer id, Double price) throws Exception {
		super.executeHql("update Assets a set a.price = a.price + ? where a.id = ?", price, id);
	}

	@Override
	public void editPriceByCode(String deptCode, String code, Double price) throws Exception {
		super.executeHql("update Assets a set a.price = a.price + ? where a.deptCode = ? and a.code = ?", price, deptCode, code);
	}
}

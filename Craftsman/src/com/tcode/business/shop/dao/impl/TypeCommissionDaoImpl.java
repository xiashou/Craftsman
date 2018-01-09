package com.tcode.business.shop.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.shop.dao.TypeCommissionDao;
import com.tcode.business.shop.model.TypeCommission;
import com.tcode.common.dao.BaseDao;

@Component("typeCommissionDao")
public class TypeCommissionDaoImpl extends BaseDao<TypeCommission, Serializable> implements TypeCommissionDao {

	@Override
	public TypeCommission loadById(Integer typeId, String deptCode) throws Exception {
		return (TypeCommission) super.loadEntity("from TypeCommission c where c.deptCode = ? and c.typeId = ?", deptCode, typeId);
	}

	@Override
	public List<TypeCommission> loadByDept(String deptCode) throws Exception {
		return super.loadList("from TypeCommission c where c.deptCode = ? ", deptCode);
	}

	@Override
	public void removeByDept(String deptCode) throws Exception {
		super.bulkUpdate("delete from TypeCommission c where c.deptCode = ? ", deptCode);
	}

}

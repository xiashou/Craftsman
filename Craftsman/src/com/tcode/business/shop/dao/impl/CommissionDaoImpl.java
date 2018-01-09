package com.tcode.business.shop.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.shop.dao.CommissionDao;
import com.tcode.business.shop.model.Commission;
import com.tcode.common.dao.BaseDao;

@Component("commissionDao")
public class CommissionDaoImpl extends BaseDao<Commission, Serializable> implements CommissionDao {

	@Override
	public Commission loadById(String goodsId, String deptCode) throws Exception {
		return (Commission) super.loadEntity("from Commission c where c.deptCode = ? and c.goodsId = ?", deptCode, goodsId);
	}

	@Override
	public List<Commission> loadByDeptType(String deptCode, String type) throws Exception {
		return super.loadList("from Commission c where c.deptCode = ? and c.goodsId like '" + type + "%'", deptCode);
	}

	@Override
	public void removeByType(String deptCode, String type) throws Exception {
		super.bulkUpdate("delete from Commission c where c.deptCode = ? and c.goodsId like '" + type + "%'", deptCode);
	}

}

package com.tcode.business.goods.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsHourRemindDao;
import com.tcode.business.goods.model.GoodsHourRemind;
import com.tcode.common.dao.BaseDao;

@Component("goodsHourRemindDao")
public class GoodsHourRemindDaoImpl extends BaseDao<GoodsHourRemind, Serializable> implements GoodsHourRemindDao {

	@Override
	public List<GoodsHourRemind> loadAll(String deptCode) throws Exception {
		return super.loadList("from GoodsHourRemind r where r.deptCode = ?", deptCode);
	}

	@Override
	public GoodsHourRemind loadById(Integer id) throws Exception {
		return super.loadById(GoodsHourRemind.class, id);
	}

	

}

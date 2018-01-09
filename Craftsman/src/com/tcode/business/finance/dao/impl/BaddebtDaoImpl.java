package com.tcode.business.finance.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.finance.dao.BaddebtDao;
import com.tcode.business.finance.model.Baddebt;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("baddebtDao")
public class BaddebtDaoImpl extends BaseDao<Baddebt, Serializable> implements BaddebtDao {

	@Override
	public Baddebt loadById(Integer id) throws Exception {
		return super.loadById(Baddebt.class, id);
	}

	@Override
	public List<Baddebt> loadByDept(String deptCode) throws Exception {
		return super.loadList("from Baddebt b where b.deptCode = ?", deptCode);
	}

	@Override
	public List<Baddebt> loadByDeptPage(Baddebt debt, Integer start, Integer limit) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from Baddebt b where b.deptCode = ? ");
		if(!Utils.isEmpty(debt.getMemId()))
			hql.append(" and b.memId = " + debt.getMemId());
		hql.append(" order by b.createTime desc");
		return super.loadListForPage(hql.toString(), start, limit, debt.getDeptCode());
	}

	@Override
	public Integer loadByDeptCount(Baddebt debt) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from Baddebt b where b.deptCode = ? ");
		if(!Utils.isEmpty(debt.getMemId()))
			hql.append(" and b.memId = " + debt.getMemId());
		Long count = super.loadListCount(hql.toString(), debt.getDeptCode());
		return count.intValue();
	}

}

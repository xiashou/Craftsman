package com.tcode.business.finance.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.finance.dao.ExpenditureDao;
import com.tcode.business.finance.model.Expenditure;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("expenditureDao")
public class ExpenditureDaoImpl extends BaseDao<Expenditure, Serializable> implements ExpenditureDao {

	@Override
	public Expenditure loadById(Integer id) throws Exception {
		return super.loadById(Expenditure.class, id);
	}

	@Override
	public List<Expenditure> loadByDept(String deptCode) throws Exception {
		return super.loadList("from Expenditure e where e.deptCode = ? order by e.createTime desc", deptCode);
	}

	@Override
	public List<Expenditure> loadByDeptPage(Expenditure expend, Integer start, Integer limit) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from Expenditure e where e.deptCode = ? ");
		if(!Utils.isEmpty(expend) && !Utils.isEmpty(expend.getAssetsId()))
			hql.append(" and e.assetsId = " + expend.getAssetsId());
		if(!Utils.isEmpty(expend) && !Utils.isEmpty(expend.getExptypeId()))
			hql.append(" and e.exptypeId = " + expend.getExptypeId());
		if(!Utils.isEmpty(expend) && !Utils.isEmpty(expend.getStartDate()))
			hql.append(" and e.createTime >= '" + expend.getStartDate() + "'");
		if(!Utils.isEmpty(expend) && !Utils.isEmpty(expend.getEndDate()))
			hql.append(" and e.createTime <= '" + expend.getEndDate() + "'");
		hql.append(" order by e.createTime desc");
		return super.loadListForPage(hql.toString(), start, limit, expend.getDeptCode());
	}

	@Override
	public Integer loadByDeptCount(Expenditure expend) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from Expenditure e where e.deptCode = ? ");
		if(!Utils.isEmpty(expend) && !Utils.isEmpty(expend.getAssetsId()))
			hql.append(" and e.assetsId = " + expend.getAssetsId());
		if(!Utils.isEmpty(expend) && !Utils.isEmpty(expend.getExptypeId()))
			hql.append(" and e.exptypeId = " + expend.getExptypeId());
		if(!Utils.isEmpty(expend) && !Utils.isEmpty(expend.getStartDate()))
			hql.append(" and e.createTime >= '" + expend.getStartDate() + "'");
		if(!Utils.isEmpty(expend) && !Utils.isEmpty(expend.getEndDate()))
			hql.append(" and e.createTime <= '" + expend.getEndDate() + "'");
		Long count = super.loadListCount(hql.toString(), expend.getDeptCode());
		return count.intValue();
	}

}

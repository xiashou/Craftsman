package com.tcode.business.finance.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.finance.dao.PayableDao;
import com.tcode.business.finance.model.Payable;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("payableDao")
public class PayableDaoImpl extends BaseDao<Payable, Serializable> implements PayableDao {

	@Override
	public Payable loadById(String deptCode, Integer supplierId) throws Exception {
		List<Payable> list = super.loadList("from Payable p where p.deptCode = ? and p.supplierId = ?", deptCode, supplierId);
		return (!Utils.isEmpty(list) ? list.get(0) : null);
	}

	@Override
	public List<Payable> loadByDept(String deptCode) throws Exception {
		return super.loadList("from Payable p where p.deptCode = ? order by p.lastUpdate desc", deptCode);
	}

	@Override
	public List<Payable> loadByDeptPage(Payable payable, Integer start, Integer limit) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from Payable p where p.deptCode = ? ");
		if(!Utils.isEmpty(payable.getSupplierId()))
			hql.append(" and p.supplierId = " + payable.getSupplierId());
		if(Utils.isEmpty(payable.getStatus()))
			hql.append(" and p.status = 0 ");
		else
			hql.append(" and p.status = " + payable.getStatus());
		hql.append(" order by p.lastUpdate desc");
		return super.loadListForPage(hql.toString(), start, limit, payable.getDeptCode());
	}

	@Override
	public Integer loadByDeptCount(Payable payable) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from Payable p where p.deptCode = ? ");
		if(!Utils.isEmpty(payable.getSupplierId()))
			hql.append(" and p.supplierId = " + payable.getSupplierId());
		Long count = super.loadListCount(hql.toString(), payable.getDeptCode());
		return count.intValue();
	}
	
	@Override
	public List<Double> loadSumByBoss(String companyId, String date, String deptCode) throws Exception {
		StringBuffer hql = new StringBuffer();
		List<Double> result = new ArrayList<Double>();
		hql.append("select sum(r.payable), sum(r.repayment), sum(r.payable) - sum(r.repayment) from Payable r where r.deptCode in (select d.deptCode from SysDept d where d.companyId = '"+companyId+"')");
		if(!Utils.isEmpty(deptCode))
			hql.append(" and r.deptCode = '" + deptCode + "'");
		List list = super.loadList(hql.toString());
		if(!Utils.isEmpty(list)){
			for(int i = 0; i < list.size(); i++){
				Object[] object = (Object[]) list.get(i);
				result.add(Double.parseDouble(Utils.isEmpty(object[0]) ? "0" : object[0].toString()));
				result.add(Double.parseDouble(Utils.isEmpty(object[1]) ? "0" : object[1].toString()));
				result.add(Double.parseDouble(Utils.isEmpty(object[2]) ? "0" : object[2].toString()));
			}
		}
		return result;
	}
	
	@Override
	public List<Payable> loadByBossPage(String companyId, String deptCode, Integer start, Integer limit) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from Payable r where r.deptCode in (select d.deptCode from SysDept d where d.companyId = '" + companyId + "') ");
		if(!Utils.isEmpty(deptCode))
			hql.append(" and r.deptCode = '" + deptCode + "'");
		hql.append(" order by r.lastUpdate desc");
		return super.loadListForPage(hql.toString(), start, limit);
	}

}

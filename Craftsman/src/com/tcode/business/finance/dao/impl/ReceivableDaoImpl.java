package com.tcode.business.finance.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.finance.dao.ReceivableDao;
import com.tcode.business.finance.model.Receivable;
import com.tcode.business.order.model.OrderHead;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("receivableDao")
public class ReceivableDaoImpl extends BaseDao<Receivable, Serializable> implements ReceivableDao {

	@Override
	public Receivable loadById(String deptCode, Integer memId, Integer carId) throws Exception {
		List<Receivable> list = super.loadList("from Receivable r where r.deptCode = ? and r.memId = ? and r.carId = ?", deptCode, memId, carId);
		return !Utils.isEmpty(list) ? list.get(0) : null;
	}

	@Override
	public List<Receivable> loadByDept(String deptCode) throws Exception {
		return super.loadList("from Receivable r where r.deptCode = ? order by r.lastUpdate desc", deptCode);
	}
	
	@Override
	public List<Receivable> loadByDeptPage(Receivable receivable, Integer start, Integer limit) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from Receivable r where r.deptCode = ? ");
		if(!Utils.isEmpty(receivable.getMemId()))
			hql.append(" and r.memId = " + receivable.getMemId());
		if(Utils.isEmpty(receivable.getStatus()))
			hql.append(" and r.status = 0 ");
		else
			hql.append(" and r.status = " + receivable.getStatus());
		hql.append(" order by r.lastUpdate desc");
		return super.loadListForPage(hql.toString(), start, limit, receivable.getDeptCode());
	}
	
	@Override
	public Integer loadByDeptCount(Receivable receivable) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from Receivable r where r.deptCode = ? ");
		if(!Utils.isEmpty(receivable.getMemId()))
			hql.append(" and r.memId = " + receivable.getMemId());
		Long count = super.loadListCount(hql.toString(), receivable.getDeptCode());
		return count.intValue();
	}
	
	@Override
	public List<Double> loadSumByBoss(String companyId, String date, String deptCode) throws Exception {
		StringBuffer hql = new StringBuffer();
		List<Double> result = new ArrayList<Double>();
		hql.append("select sum(r.billPrice), sum(r.repayment), sum(r.billPrice) - sum(r.repayment) from Receivable r where r.deptCode in (select d.deptCode from SysDept d where d.companyId = '"+companyId+"')");
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
	public List<Receivable> loadByBossPage(String companyId, String deptCode, Integer start, Integer limit) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from Receivable r where r.deptCode in (select d.deptCode from SysDept d where d.companyId = '" + companyId + "') ");
		if(!Utils.isEmpty(deptCode))
			hql.append(" and r.deptCode = '" + deptCode + "'");
		hql.append(" order by r.lastUpdate desc");
		return super.loadListForPage(hql.toString(), start, limit);
	}
	
	@Override
	public Integer loadCountByDept(OrderHead orderHead) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) from Receivable r where r.deptCode = '" + orderHead.getDeptCode() + "' ");
		if(!Utils.isEmpty(orderHead.getStartDate()))
			hql.append(" and r.lastUpdate >= '" + orderHead.getStartDate() + "'");
		if(!Utils.isEmpty(orderHead.getEndDate()))
			hql.append(" and r.lastUpdate <= '" + orderHead.getEndDate() + "'");
		Long count = (Long)super.loadUniqueResult(hql.toString());
		return count.intValue();
	}
	
}

package com.tcode.business.finance.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.finance.dao.RepaymentDao;
import com.tcode.business.finance.model.Repayment;
import com.tcode.business.order.model.OrderHead;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("repaymentDao")
public class RepaymentDaoImpl extends BaseDao<Repayment, Serializable> implements RepaymentDao {

	@Override
	public Repayment loadById(Integer id) throws Exception {
		return super.loadById(Repayment.class, id);
	}

	@Override
	public List<Repayment> loadByReceId(String deptCode, Integer memId, Integer carId) throws Exception {
		return super.loadList("from Repayment r where r.deptCode = ? and r.memId = ? and r.carId = ? order by r.createTime desc", deptCode, memId, carId);
	}
	
	@Override
	public List<Repayment> loadBySupplierId(String deptCode, Integer supplierId) throws Exception {
		return super.loadList("from Repayment r where r.deptCode = ? and r.supplierId = ?", deptCode, supplierId);
	}

	@Override
	public Double loadSumByPay(OrderHead orderHead) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("select sum(r.repayment) from Repayment r where r.deptCode = '" + orderHead.getDeptCode() + 
				"' and r.payType = '" + orderHead.getOrderType() + "' and r.supplierId is null ");
		if(!Utils.isEmpty(orderHead.getStartDate()))
			hql.append(" and r.createTime >= '" + orderHead.getStartDate() + " 00:00:00'");
		if(!Utils.isEmpty(orderHead.getEndDate()))
			hql.append(" and r.createTime <= '" + orderHead.getEndDate() + " 23:59:59'");
		return (Double) super.loadUniqueResult(hql.toString());
	}
}

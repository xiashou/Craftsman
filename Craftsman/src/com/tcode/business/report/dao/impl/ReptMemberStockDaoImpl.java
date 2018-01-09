package com.tcode.business.report.dao.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.report.dao.ReptMemberStockDao;
import com.tcode.business.report.model.ReptMemberStock;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("reptMemberStockDao")
public class ReptMemberStockDaoImpl extends BaseDao<ReptMemberStock, Serializable> implements ReptMemberStockDao {

	@Override
	public ReptMemberStock loadById(Integer id) throws Exception {
		return super.loadById(ReptMemberStock.class, id);
	}

	@Override
	public List<ReptMemberStock> loadByDept(String deptCode) throws Exception {
		return super.loadList("from ReptMemberStock rr where rr.deptCode = ?", deptCode);
	}

	@Override
	public List<ReptMemberStock> loadByMemId(Integer memId) throws Exception {
		return super.loadList("from ReptMemberStock rr where rr.memId = ?", memId);
	}

	@Override
	public void addRecord(String deptCode, Integer memId, String orderNo, String goodsId, String goodsName, Double number) throws Exception {
		ReptMemberStock memberStock = new ReptMemberStock(deptCode, memId, orderNo, goodsId, goodsName, number);
		memberStock.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		super.save(memberStock);
	}

	@Override
	public List<ReptMemberStock> loadListPage(ReptMemberStock memberStock, int start, int limit) throws Exception {
		List<ReptMemberStock> list = null;
		DetachedCriteria criteria = connectionCriteria(memberStock);
		criteria.addOrder(Order.asc("desc"));
		list = (List<ReptMemberStock>) super.loadListForPage(criteria, start, limit);
		return list;
	}

	@Override
	public Integer loadListCount(ReptMemberStock memberStock) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(memberStock);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(ReptMemberStock memberStock){
		DetachedCriteria criteria = DetachedCriteria.forClass(ReptMemberStock.class);  
		if(!Utils.isEmpty(memberStock)){
			if(!Utils.isEmpty(memberStock.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", memberStock.getDeptCode()));
		}
		return criteria;
	}

}

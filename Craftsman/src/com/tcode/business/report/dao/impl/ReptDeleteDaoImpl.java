package com.tcode.business.report.dao.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.report.dao.ReptDeleteDao;
import com.tcode.business.report.model.ReptDelete;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("reptDeleteDao")
public class ReptDeleteDaoImpl extends BaseDao<ReptDelete, Serializable> implements ReptDeleteDao {

	@Override
	public ReptDelete loadById(Integer id) throws Exception {
		return super.loadById(ReptDelete.class, id);
	}

	@Override
	public List<ReptDelete> loadByDept(String deptCode) throws Exception {
		return super.loadList("from ReptDelete rr where rr.deptCode = ?", deptCode);
	}

	@Override
	public List<ReptDelete> loadByMemId(Integer memId) throws Exception {
		return super.loadList("from ReptDelete rr where rr.memId = ?", memId);
	}
	
	public ReptDelete loadByOrderNo(String orderNo) throws Exception {
		List<ReptDelete> list = super.loadList("from ReptDelete d where d.orderNo = ?", orderNo);
		return (!Utils.isEmpty(list)) ? list.get(0) : null;
	}

	@Override
	public void addRecord(String deptCode, String orderNo, String userId) throws Exception {
		ReptDelete reptDelete = new ReptDelete(deptCode, orderNo, userId);
		reptDelete.setDeleteTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		super.save(reptDelete);
	}

	@Override
	public List<ReptDelete> loadListPage(ReptDelete reptDelete, int start, int limit) throws Exception {
		List<ReptDelete> list = null;
		DetachedCriteria criteria = connectionCriteria(reptDelete);
		criteria.addOrder(Order.asc("id"));
		list = (List<ReptDelete>) super.loadListForPage(criteria, start, limit);
		return list;
	}

	@Override
	public Integer loadListCount(ReptDelete memberStock) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(memberStock);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(ReptDelete memberStock){
		DetachedCriteria criteria = DetachedCriteria.forClass(ReptDelete.class);  
		if(!Utils.isEmpty(memberStock)){
			if(!Utils.isEmpty(memberStock.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", memberStock.getDeptCode()));
		}
		return criteria;
	}

}

package com.tcode.business.report.dao.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.report.dao.ReptGoodsDao;
import com.tcode.business.report.model.ReptGoods;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("reptGoodsDao")
public class ReptGoodsDaoImpl extends BaseDao<ReptGoods, Serializable> implements ReptGoodsDao {

	@Override
	public ReptGoods loadById(Integer id) throws Exception {
		return super.loadById(ReptGoods.class, id);
	}

	@Override
	public List<ReptGoods> loadByDept(String deptCode) throws Exception {
		return super.loadList("from ReptGoods rg where rg.deptCode = ?", deptCode);
	}

	@Override
	public List<ReptGoods> loadByGoodsId(String goodsId) throws Exception {
		return super.loadList("from ReptGoods rg where rg.goodsId = ?", goodsId);
	}

	@Override
	public void addRecord(String deptCode, String orderNo, String type, String goodsId, String goodsName, Double number) throws Exception {
		ReptGoods memberStock = new ReptGoods(deptCode, orderNo, type, goodsId, goodsName, number);
		memberStock.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		super.save(memberStock);
	}

	@Override
	public List<ReptGoods> loadListPage(ReptGoods reptGoods, int start, int limit) throws Exception {
		List<ReptGoods> list = null;
		DetachedCriteria criteria = connectionCriteria(reptGoods);
		criteria.addOrder(Order.asc("desc"));
		list = (List<ReptGoods>) super.loadListForPage(criteria, start, limit);
		return list;
	}

	@Override
	public Integer loadListCount(ReptGoods reptGoods) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(reptGoods);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(ReptGoods reptGoods){
		DetachedCriteria criteria = DetachedCriteria.forClass(ReptGoods.class);  
		if(!Utils.isEmpty(reptGoods)){
			if(!Utils.isEmpty(reptGoods.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", reptGoods.getDeptCode()));
		}
		return criteria;
	}

}

package com.tcode.business.goods.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsMoveHeadDao;
import com.tcode.business.goods.model.GoodsMoveHead;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("goodsMoveHeadDao")
public class GoodsMoveHeadDaoImpl extends BaseDao<GoodsMoveHead, Serializable> implements GoodsMoveHeadDao {

	@Override
	public GoodsMoveHead loadById(Integer id) throws Exception {
		return super.loadById(GoodsMoveHead.class, id);
	}

	@Override
	public List<GoodsMoveHead> loadByMoveIn(String moveIn) throws Exception {
		return super.loadList("from GoodsMoveHead h where h.deptIn = ? and h.status = 0", moveIn);
	}

	@Override
	public List<GoodsMoveHead> loadByMoveOut(String moveOut) throws Exception {
		return super.loadList("from GoodsMoveHead h where h.deptOut = ?", moveOut);
	}

	@Override	
	public List<GoodsMoveHead> loadListByPage(GoodsMoveHead moveHead, int start, int limit) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("select h.deptIn, h.deptOut,i.goodsId, i.goodsName,i.number,i.inPrice,h.status,h.creator,h.createTime,h.updator,h.updateTime,h.total ");
		hql.append("from GoodsMoveHead h, GoodsMoveItem i where h.id = i.moveId ");
		if(!Utils.isEmpty(moveHead.getDept()))
			hql.append(" and (h.deptIn = '" + moveHead.getDept() + "' or h.deptOut = '" + moveHead.getDept() + "') ");
		if(!Utils.isEmpty(moveHead.getDeptIn()))
			hql.append(" and h.deptIn = '" + moveHead.getDeptIn() + "' ");
		if(!Utils.isEmpty(moveHead.getDeptOut()))
			hql.append(" and h.deptOut = '" + moveHead.getDeptOut() + "' ");
		if(!Utils.isEmpty(moveHead.getGoodsName()))
			hql.append(" and i.goodsName like '%" + moveHead.getGoodsName() + "%' ");
		hql.append(" order by h.createTime desc ");
		List<GoodsMoveHead> headList = new ArrayList<GoodsMoveHead>();
		List list = super.loadListForPage(hql.toString(), start, limit);
		GoodsMoveHead head = null;
		for(int i = 0; i < list.size(); i++){
			head = new GoodsMoveHead();
			Object[] object = (Object[]) list.get(i);
			head.setDeptIn(Utils.isEmpty(object[0]) ? "" : object[0].toString());
			head.setDeptOut(Utils.isEmpty(object[1]) ? "" : object[1].toString());
			head.setGoodsId(Utils.isEmpty(object[2]) ? "" : object[2].toString());
			head.setGoodsName(Utils.isEmpty(object[3]) ? "" : object[3].toString());
			head.setNumber(Utils.isEmpty(object[4]) ? 0.0 : Double.parseDouble(object[4].toString()));
			head.setInPrice(Utils.isEmpty(object[5]) ? 0.0 : Double.parseDouble(object[5].toString()));
			head.setStatus(Utils.isEmpty(object[6]) ? 0 : Integer.parseInt(object[6].toString()));
			head.setCreator(Utils.isEmpty(object[7]) ? "" : object[7].toString());
			head.setCreateTime(Utils.isEmpty(object[8]) ? "" : object[8].toString());
			head.setUpdator(Utils.isEmpty(object[9]) ? "" : object[9].toString());
			head.setUpdateTime(Utils.isEmpty(object[10]) ? "" : object[10].toString());
			head.setTotal(Utils.isEmpty(object[11]) ? 0.0 : Double.parseDouble(object[11].toString()));
			headList.add(head);
		}
		return headList;
	}
	
	
	@Override
	public Integer loadListByCount(GoodsMoveHead moveHead) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from GoodsMoveHead h, GoodsMoveItem i where h.id = i.moveId ");
		if(!Utils.isEmpty(moveHead.getDept()))
			hql.append(" and (h.deptIn = '" + moveHead.getDept() + "' or h.deptOut = '" + moveHead.getDept() + "') ");
		if(!Utils.isEmpty(moveHead.getDeptIn()))
			hql.append(" and h.deptIn = '" + moveHead.getDeptIn() + "' ");
		if(!Utils.isEmpty(moveHead.getDeptOut()))
			hql.append(" and h.deptOut = '" + moveHead.getDeptOut() + "' ");
		Long count = super.loadListCount(hql.toString());
		return count.intValue();
	}
}

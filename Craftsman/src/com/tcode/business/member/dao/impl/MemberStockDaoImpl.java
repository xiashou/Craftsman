package com.tcode.business.member.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.member.dao.MemberStockDao;
import com.tcode.business.member.model.MemberStock;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("memberStockDao")
public class MemberStockDaoImpl extends BaseDao<MemberStock, Serializable> implements MemberStockDao {

	@Override
	public List<MemberStock> loadByMemId(Integer memId) throws Exception {
		List<MemberStock> msList = null;
		List list = super.loadList("select ms.goodsId, ms.memId, ms.goodsName, sum(ms.number) as number from MemberStock ms "
				+ "where ms.memId = ? and ms.number > 0 group by ms.goodsId, ms.memId, ms.goodsName", memId);
		if(!Utils.isEmpty(list)){
			msList = new ArrayList<MemberStock>();
			MemberStock stock = null;
			for(int i = 0; i < list.size(); i++) {
				stock = new MemberStock();
				Object[] object = (Object[]) list.get(i);
				stock.setGoodsId(object[0].toString());
				stock.setMemId(Integer.parseInt(object[1].toString()));
				stock.setGoodsName(Utils.isEmpty(object[2]) ? "" : object[2].toString());
				stock.setNumber(Double.parseDouble(object[3].toString()));
				msList.add(stock);
			}
		}
		return msList;
	}
	
	@Override
	public List<MemberStock> loadListByDept(String companyId, Integer memId) throws Exception {
		List<MemberStock> msList = null;
		String hql = "select ms.goodsId, ms.memId, ms.goodsName, ms.goodsType, ms.typeId, ms.number, m.name, m.sex, m.mobile, ms.endDate, ms.source "
				+ "from MemberStock ms, Member m where ms.memId = m.memId and m.companyId = ? and ms.number > 0 ";
		if(!Utils.isEmpty(memId))
			hql += " and ms.memId = " + memId;
		List list = super.loadList(hql, companyId);
		if(!Utils.isEmpty(list)){
			msList = new ArrayList<MemberStock>();
			MemberStock stock = null;
			for(int i = 0; i < list.size(); i++) {
				stock = new MemberStock();
				Object[] object = (Object[]) list.get(i);
				stock.setGoodsId(object[0].toString());
				stock.setMemId(Integer.parseInt(object[1].toString()));
				stock.setGoodsName(Utils.isEmpty(object[2]) ? "" : object[2].toString());
				stock.setGoodsType(Integer.parseInt(object[3].toString()));
				stock.setTypeId(Utils.isEmpty(object[4]) ? "" : object[4].toString());
				stock.setNumber(Double.parseDouble(object[5].toString()));
				stock.setName(object[6].toString());
				stock.setSex(object[7].toString());
				stock.setMobile(object[8].toString());
				stock.setEndDate(Utils.isEmpty(object[9]) ? "" : object[9].toString());
				stock.setSource(Utils.isEmpty(object[10]) ? "" : object[10].toString());
				msList.add(stock);
			}
		}
		return msList;
	}
	
	@Override
	public Integer loadListCountByDept(String companyId, Integer memId) throws Exception {
		String hql = "select count(ms.memId) "
				+ "from MemberStock ms, Member m where ms.memId = m.memId and m.companyId = '" + companyId + "' and ms.number > 0 ";
		if(!Utils.isEmpty(memId))
			hql += " and ms.memId = " + memId;
		Long count = (Long) super.loadUniqueResult(hql);
		return count.intValue();
	}
	
	@Override
	public List<MemberStock> loadDetailByMemId(Integer memId) throws Exception {
		return super.loadList("from MemberStock ms where ms.memId = ? and ms.number > 0 " , memId);
	}

	@Override
	public MemberStock loadById(Integer memId, String goodsId, String endDate, String source) throws Exception {
		return (MemberStock) super.loadEntity("from MemberStock ms where ms.memId = ? and ms.goodsId = ? and ms.endDate = ? and ms.source = ? ", 
				memId, goodsId, endDate, source);
	}

	@Override
	public void editStockNumber(Integer memId, String goodsId, String endDate, Double number, String source) throws Exception {
		super.executeHql("update MemberStock ms set ms.number = ms.number + ? where ms.memId = ? and ms.goodsId = ? and ms.endDate = ? and ms.source = ? ", 
				number, memId, goodsId, endDate, source);
	}

	@Override
	public double loadGoodsNum(Integer memId, String goodsId) throws Exception {
		double count = (double) super.loadUniqueResult("select sum(ms.number) "
				+ "from MemberStock ms where ms.memId = " + memId + " and ms.goodsId = '" + goodsId + "'");
		return count;
	}
	
	@Override
	public void editStockByProc(MemberStock memStock) throws Exception {
		super.executeSql("exec pro_memstock_update ?, ?, ?, ?, ?, ?, ?, ?", memStock.getMemId(), memStock.getGoodsId(), Utils.isEmpty(memStock.getEndDate()) ? "" : memStock.getEndDate(), 
				Utils.isEmpty(memStock.getSource()) ? "" : memStock.getSource(), memStock.getGoodsType(), memStock.getTypeId(), memStock.getGoodsName(), memStock.getNumber());
	}
}

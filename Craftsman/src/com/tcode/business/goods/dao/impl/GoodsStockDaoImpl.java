package com.tcode.business.goods.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsStockDao;
import com.tcode.business.goods.model.GoodsStock;
import com.tcode.business.order.model.OrderHead;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("goodsStockDao")
public class GoodsStockDaoImpl extends BaseDao<GoodsStock, Serializable> implements GoodsStockDao {

	@Override
	public GoodsStock loadById(String goodsId, String deptCode) throws Exception {
		return (GoodsStock) super.loadUniqueResult("from GoodsStock s where s.goodsId = '" + goodsId + "' and s.deptCode = '" + deptCode + "'");
	}
	@Override
	public List<GoodsStock> loadByGoodsId(String goodsId) throws Exception {
		return super.loadList("from GoodsStock s where s.goodsId = ? ", goodsId);
	}
	@Override
	public List<GoodsStock> loadByDept(String deptCode) throws Exception {
		return super.loadList("from GoodsStock s where s.deptCode = ? ", deptCode);
	}
	@Override
	public List<GoodsStock> loadByKeyword(String deptCode, String keyword) throws Exception {
		return super.loadList("from GoodsStock s join GoodsMaterial m on s.goodsId = m.id where s.deptCode = ? "
				+ "and (m.name like '%" + keyword + "%' or m.shorthand like '%" + keyword + "%')", deptCode);
	}
	@Override
	public void editByGoodsId(String deptCode, String goodsId, Double number) throws Exception {
		super.executeSql("if not exists (select * from god_stock where dept_code = ? and goods_id = ?) "
				+ "insert into god_stock(dept_code, goods_id, number) values(?, ?, ?) else "
				+ "update god_stock set number = number - ? where dept_code = ? and goods_id = ?", deptCode, goodsId, deptCode, goodsId, number*-1, number, deptCode, goodsId);
//		super.executeHql("update GoodsStock s set s.number = s.number - ? where s.deptCode = ? and s.memId = ? and s.goodsId = ?", number, deptCode, memId, goodsId);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<GoodsStock> loadByDeptPage(GoodsStock stock, int start, int limit) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("select m.code, m.name, t.typeName, s.number, m.price, m.inPrice, m.spec, m.color, m.size, m.unit, m.suitModel, s.goodsId ");
		hql.append("from GoodsStock s, GoodsMaterial m, GoodsMaterialType t where s.goodsId = m.id and m.typeId = t.id ");
		if(!Utils.isEmpty(stock.getDeptCode()))
			hql.append("and s.deptCode = '" + stock.getDeptCode() + "'");
		if(!Utils.isEmpty(stock.getCode()))
			hql.append(" and m.code like '%" + stock.getCode() + "%' ");
		if(!Utils.isEmpty(stock.getName()))
			hql.append(" and (m.name like '%" + stock.getName() + "%' or m.shorthand like '%" + stock.getName() + "%' or m.code like '%" + stock.getName() + "%')");
		if(!Utils.isEmpty(stock.getTypeId()))
			hql.append(" and m.typeId = " + stock.getTypeId());
		if(!Utils.isEmpty(stock.getCompanyId()))
			hql.append(" and m.deptCode = '" + stock.getCompanyId() + "'");
		hql.append(" order by s.number");
		List<GoodsStock> stockList = new ArrayList<GoodsStock>();
		List list = super.loadListForPage(hql.toString(), start, limit);
		GoodsStock goodsStock = null;
		for(int i = 0; i < list.size(); i++){
			goodsStock = new GoodsStock();
			Object[] object = (Object[]) list.get(i);
			goodsStock.setCode(Utils.isEmpty(object[0]) ? "" : object[0].toString());
			goodsStock.setName(Utils.isEmpty(object[1]) ? "" : object[1].toString());
			goodsStock.setTypeName(Utils.isEmpty(object[2]) ? "" : object[2].toString());
			goodsStock.setNumber(Double.valueOf(Utils.isEmpty(object[3]) ? "0" : object[3].toString()));
			goodsStock.setPrice(Double.valueOf(Utils.isEmpty(object[4]) ? "0" : object[4].toString()));
			goodsStock.setInPrice(Double.valueOf(Utils.isEmpty(object[5]) ? "0" : object[5].toString()));
			goodsStock.setSpec(Utils.isEmpty(object[6]) ? "" : object[6].toString());
			goodsStock.setColor(Utils.isEmpty(object[7]) ? "" : object[7].toString());
			goodsStock.setSize(Utils.isEmpty(object[8]) ? "" : object[8].toString());
			goodsStock.setUnit(Utils.isEmpty(object[9]) ? "" : object[9].toString());
			goodsStock.setSuitModel(Utils.isEmpty(object[10]) ? "" : object[10].toString());
			goodsStock.setGoodsId(Utils.isEmpty(object[11]) ? "" : object[11].toString());
			stockList.add(goodsStock);
		}
		return stockList;
	}
	
	@Override
	public Integer loadByDeptPageCount(GoodsStock stock) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from GoodsStock s, GoodsMaterial m, GoodsMaterialType t where s.goodsId = m.id and m.typeId = t.id ");
		hql.append("and s.deptCode = ?");
		if(!Utils.isEmpty(stock.getCode()))
			hql.append(" and m.code like '%" + stock.getCode() + "%' ");
		if(!Utils.isEmpty(stock.getName()))
			hql.append(" and m.name like '%" + stock.getName() + "%'");
		if(!Utils.isEmpty(stock.getTypeId()))
			hql.append(" and m.typeId = " + stock.getTypeId());
		Long count = super.loadListCount(hql.toString(), stock.getDeptCode());
		return count.intValue();
	}
	
	@Override
	public Integer loadByBossCount(String companyId, String deptCode) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from GoodsStock s where s.deptCode in (select d.deptCode from SysDept d where d.companyId = '" + companyId + "') ");
		if(!Utils.isEmpty(deptCode))
			hql.append(" and s.deptCode = '" + deptCode + "'");
		Long count = super.loadListCount(hql.toString());
		return count.intValue();
	}
	
	@Override
	public Integer loadByBossCost(String companyId, String deptCode) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select goods_id, isnull(number*(select in_price from god_material where id = god_stock.goods_id), 0) from god_stock "
				+ "where dept_code in (select dept_code from sys_dept where company_id = ?) ");
		if(!Utils.isEmpty(deptCode))
			sql.append(" and dept_code = '" + deptCode + "'");
		Long count = super.loadListSqlCount(sql.toString(), companyId);
		return count.intValue();
	}
	
	@Override
	public List<GoodsStock> loadByBossType(String companyId, String deptCode) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select type_id, type_name, sum(a.bb) from (select b.type_id, isnull(a.number*b.in_price, 0) as bb "
				+ "from god_stock a, god_material b where a.goods_id = b.id and a.dept_code in "
				+ "(select dept_code from sys_dept where company_id = '" + companyId + "'");
		if(!Utils.isEmpty(deptCode))
			sql.append(" and dept_code = '" + deptCode + "'");
		sql.append(" )) a, god_material_type b where a.type_id = b.id group by type_id, type_name");
		List<GoodsStock> stockList = new ArrayList<GoodsStock>();
		List list = super.loadListForSqlPage(sql.toString(), 0, 100);
		GoodsStock goodsStock = null;
		for(int i = 0; i < list.size(); i++){
			goodsStock = new GoodsStock();
			Object[] object = (Object[]) list.get(i);
			goodsStock.setTypeId(Integer.parseInt((Utils.isEmpty(object[0]) ? "" : object[0].toString())));
			goodsStock.setTypeName(Utils.isEmpty(object[1]) ? "" : object[1].toString());
			goodsStock.setInPrice(Double.parseDouble(Utils.isEmpty(object[2]) ? "" : object[2].toString()));
			stockList.add(goodsStock);
		}
		return stockList;
	}
	
}

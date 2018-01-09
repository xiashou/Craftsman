package com.tcode.business.order.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.order.dao.OrderHeadDao;
import com.tcode.business.order.model.OrderHead;
import com.tcode.business.order.model.OrderItem;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("orderHeadDao")
public class OrderHeadDaoImpl extends BaseDao<OrderHead, Serializable> implements OrderHeadDao {

	@Override
	public OrderHead loadHeadByOrderNo(String orderNo) throws Exception {
		return (OrderHead)super.loadEntity("from OrderHead h where h.orderId = ?", orderNo);
	}
	
	@Override
	public Integer loadUnfinishedOrderCount(String deptCode) throws Exception {
		Long count = (Long)super.loadUniqueResult("select count(o.orderId) from OrderHead o where o.deptCode = '" + deptCode + "' and o.status >= 2");
		return count.intValue();
	}
	
	@Override
	public List<OrderItem> loadRechargeListByDeptPage(OrderItem item, String deptCode, Integer start, Integer limit) throws Exception {
		List<OrderItem> orderList = new ArrayList<OrderItem>();
		StringBuffer hql = new StringBuffer();
		hql.append("select h.orderId,h.orderType,h.saleDate,h.memId,h.carId,h.carNumber,h.status,h.remark,m.mobile,m.name,m.sex from OrderHead h, Member m "
				+ "where h.memId = m.memId and h.orderType = 4 and h.status > 0 and h.deptCode = '" + deptCode + "'");
		hql.append(" order by h.saleDate desc");
		
		List list = super.loadListForPage(hql.toString(), start, limit);
		OrderItem order;
		for(int i = 0; i < list.size(); i++){
			order = new OrderItem();
			Object[] object = (Object[]) list.get(i);
			order.setOrderId(object[0].toString());
			order.setOrderType(Integer.parseInt(object[1].toString()));
			order.setSaleDate(object[2].toString());
			order.setMemId(Integer.parseInt(object[3].toString()));
			order.setCarId(Integer.parseInt(object[4].toString()));
			order.setCarNumber(object[5].toString());
			order.setStatus(Integer.parseInt(object[6].toString()));
			order.setRemark(Utils.isEmpty(object[7]) ? "" :object[7].toString());
			order.setMobile(Utils.isEmpty(object[8]) ? "" : object[8].toString());
			order.setName(object[9].toString());
			order.setSex(object[10].toString());
			
			orderList.add(order);
		}
		return orderList;
	}
	
	@Override
	public Integer loadRechargeListCountByDept(OrderItem item, String deptCode) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from OrderHead h, Member m "
				+ "where h.memId = m.memId and h.orderType = 4 and h.status > 0 and h.deptCode = '" + deptCode + "'");
		Long count = super.loadListCount(hql.toString());
		return count.intValue();
	}
	
	@Override
	public void editOrderStatus(String orderId, Integer status) throws Exception {
		super.executeHql("update OrderHead h set h.status = ? where h.orderId = ?", status, orderId);
	}
	
	@Override
	public List<OrderHead> loadBillListByMemIdPage(Integer memId, String deptCode, Integer start, Integer limit) throws Exception {
		return super.loadListForPage("from OrderHead h where h.memId = ? and h.deptCode = ? and h.pbill > 0 and status = 1 order by h.saleDate desc", start, limit, memId, deptCode);
	}
	
	@Override
	public Integer loadBillListCountByMemId(Integer memId, String deptCode) throws Exception {
		Long count = super.loadListCount("from OrderHead h where h.memId = ? and h.deptCode = ? and h.pbill > 0 and status = 1", memId, deptCode);
		return count.intValue();
	}
	
	@Override
	public List<OrderHead> loadCommissionList(OrderHead orderHead) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from OrderHead h where h.deptCode = ? and h.status = 1 ");
		if(!Utils.isEmpty(orderHead.getStartDate()))
			hql.append(" and h.saleDate >= '" + orderHead.getStartDate() + "'");
		if(!Utils.isEmpty(orderHead.getEndDate()))
			hql.append(" and h.saleDate <= '" + orderHead.getEndDate() + "'");
		if(!Utils.isEmpty(orderHead.getOrderType()))
			hql.append(" and h.orderType = '" + orderHead.getOrderType() + "'");
		return super.loadList(hql.toString(), orderHead.getDeptCode());
	}
	
	@Override
	public List<OrderHead> loadWaterListPage(OrderHead orderHead, Integer start, Integer limit) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from OrderHead h where h.deptCode = ? and h.status = 1 ");
		if(!Utils.isEmpty(orderHead.getStartDate()))
			hql.append(" and h.saleDate >= '" + orderHead.getStartDate() + "'");
		if(!Utils.isEmpty(orderHead.getEndDate()))
			hql.append(" and h.saleDate <= '" + orderHead.getEndDate() + "'");
		if(!Utils.isEmpty(orderHead.getOrderType()))
			hql.append(" and h.orderType ='" + orderHead.getOrderType() + "'");
		if(!Utils.isEmpty(orderHead.getMemId()))
			hql.append(" and h.memId = " + orderHead.getMemId());
		if(!Utils.isEmpty(orderHead.getStartPrice()))
			hql.append(" and h.aprice >= " + orderHead.getStartPrice());
		if(!Utils.isEmpty(orderHead.getEndPrice()))
			hql.append(" and h.aprice <= " + orderHead.getEndPrice());
		if(!Utils.isEmpty(orderHead.getPalipay()) && orderHead.getPalipay() > 0.0)
			hql.append(" and h.palipay > 0 ");
		if(!Utils.isEmpty(orderHead.getPbalance()) && orderHead.getPbalance() > 0.0)
			hql.append(" and h.pbalance > 0 ");
		if(!Utils.isEmpty(orderHead.getPbill()) && orderHead.getPbill() > 0.0)
			hql.append(" and h.pbill > 0 ");
		if(!Utils.isEmpty(orderHead.getPcard()) && orderHead.getPcard() > 0.0)
			hql.append(" and h.pcard > 0 ");
		if(!Utils.isEmpty(orderHead.getPcash()) && orderHead.getPcash() > 0.0)
			hql.append(" and h.pcash > 0 ");
		if(!Utils.isEmpty(orderHead.getPtransfer()) && orderHead.getPtransfer() > 0.0)
			hql.append(" and h.ptransfer > 0 ");
		if(!Utils.isEmpty(orderHead.getPwechat()) && orderHead.getPwechat() > 0.0)
			hql.append(" and h.pwechat > 0 ");
		if(!Utils.isEmpty(orderHead.getOrderId()))
			hql.append(" and h.orderId ='" + orderHead.getOrderId() + "'");
		hql.append(" order by h.saleDate desc");
		return super.loadListForPage(hql.toString(), start, limit, orderHead.getDeptCode());
	}
	
	@Override
	public Integer loadWaterListCount(OrderHead orderHead) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from OrderHead h where h.deptCode = ? and h.status = 1 ");
		if(!Utils.isEmpty(orderHead.getStartDate()))
			hql.append(" and h.saleDate >= '" + orderHead.getStartDate() + "'");
		if(!Utils.isEmpty(orderHead.getEndDate()))
			hql.append(" and h.saleDate <= '" + orderHead.getEndDate() + "'");
		if(!Utils.isEmpty(orderHead.getOrderType()))
			hql.append(" and h.orderType ='" + orderHead.getOrderType() + "'");
		if(!Utils.isEmpty(orderHead.getMemId()))
			hql.append(" and h.memId = " + orderHead.getMemId());
		if(!Utils.isEmpty(orderHead.getStartPrice()))
			hql.append(" and h.aprice >= " + orderHead.getStartPrice());
		if(!Utils.isEmpty(orderHead.getEndPrice()))
			hql.append(" and h.aprice <= " + orderHead.getEndPrice());
		if(!Utils.isEmpty(orderHead.getPalipay()) && orderHead.getPalipay() > 0.0)
			hql.append(" and h.palipay > 0 ");
		if(!Utils.isEmpty(orderHead.getPbalance()) && orderHead.getPbalance() > 0.0)
			hql.append(" and h.pbalance > 0 ");
		if(!Utils.isEmpty(orderHead.getPbill()) && orderHead.getPbill() > 0.0)
			hql.append(" and h.pbill > 0 ");
		if(!Utils.isEmpty(orderHead.getPcard()) && orderHead.getPcard() > 0.0)
			hql.append(" and h.pcard > 0 ");
		if(!Utils.isEmpty(orderHead.getPcash()) && orderHead.getPcash() > 0.0)
			hql.append(" and h.pcash > 0 ");
		if(!Utils.isEmpty(orderHead.getPtransfer()) && orderHead.getPtransfer() > 0.0)
			hql.append(" and h.ptransfer > 0 ");
		if(!Utils.isEmpty(orderHead.getPwechat()) && orderHead.getPwechat() > 0.0)
			hql.append(" and h.pwechat > 0 ");
		if(!Utils.isEmpty(orderHead.getOrderId()))
			hql.append(" and h.orderId ='" + orderHead.getOrderId() + "'");
		Long count = super.loadListCount(hql.toString(), orderHead.getDeptCode());
		return count.intValue();
	}
	
	
	@Override
	public List<OrderHead> loadDeleteListPage(OrderHead orderHead, Integer start, Integer limit) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from OrderHead h where h.deptCode = ? and h.status = 0 ");
		if(!Utils.isEmpty(orderHead.getStartDate()))
			hql.append(" and h.saleDate >= '" + orderHead.getStartDate() + "'");
		if(!Utils.isEmpty(orderHead.getEndDate()))
			hql.append(" and h.saleDate <= '" + orderHead.getEndDate() + "'");
		if(!Utils.isEmpty(orderHead.getOrderType()))
			hql.append(" and h.orderType ='" + orderHead.getOrderType() + "'");
		if(!Utils.isEmpty(orderHead.getMemId()))
			hql.append(" and h.memId = " + orderHead.getMemId());
		hql.append(" order by h.saleDate desc");
		return super.loadListForPage(hql.toString(), start, limit, orderHead.getDeptCode());
	}
	
	@Override
	public Integer loadDeleteListCount(OrderHead orderHead) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from OrderHead h where h.deptCode = ? and h.status = 0 ");
		if(!Utils.isEmpty(orderHead.getStartDate()))
			hql.append(" and h.saleDate >= '" + orderHead.getStartDate() + "'");
		if(!Utils.isEmpty(orderHead.getEndDate()))
			hql.append(" and h.saleDate <= '" + orderHead.getEndDate() + "'");
		if(!Utils.isEmpty(orderHead.getOrderType()))
			hql.append(" and h.orderType ='" + orderHead.getOrderType() + "'");
		if(!Utils.isEmpty(orderHead.getMemId()))
			hql.append(" and h.memId = " + orderHead.getMemId());
		Long count = super.loadListCount(hql.toString(), orderHead.getDeptCode());
		return count.intValue();
	}
	
	@Override
	public Double loadDayTurnover(String companyId, String date, String deptCode) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("select sum(o.aprice)-sum(o.pbalance)-sum(o.pbill) from OrderHead o, SysDept d where o.deptCode = d.deptCode and o.status = 1 ");
		if(!Utils.isEmpty(companyId))
			hql.append(" and d.companyId = '" + companyId + "' and d.deptType = 3");
		if(!Utils.isEmpty(date))
			hql.append(" and o.saleDate like '" + date + "%'");
		if(!Utils.isEmpty(deptCode))
			hql.append(" and o.deptCode = '" + deptCode + "'");
		return (Double) super.loadUniqueResult(hql.toString());
	}
	
	@Override
	public List<OrderHead> loadSalesGroupType(String companyId, String date, String deptCode) throws Exception {
		List<OrderHead> orderList = new ArrayList<OrderHead>();
		StringBuffer hql = new StringBuffer();
		hql.append("select o.orderType, sum(o.aprice)-sum(o.pbalance)-sum(o.pbill) from OrderHead o, SysDept d where o.deptCode = d.deptCode and o.status = 1 ");
		if(!Utils.isEmpty(companyId))
			hql.append(" and d.companyId = '" + companyId + "' and d.deptType = 3");
		if(!Utils.isEmpty(date))
			hql.append(" and o.saleDate like '" + date + "%'");
		if(!Utils.isEmpty(deptCode))
			hql.append(" and o.deptCode = '" + deptCode + "'");
		hql.append(" group by o.orderType");
		List list = super.loadList(hql.toString());
		OrderHead order;
		for(int i = 0; i < list.size(); i++){
			order = new OrderHead();
			Object[] object = (Object[]) list.get(i);
			order.setOrderType(object[0].toString());
			order.setAprice(Double.parseDouble(Utils.isEmpty(object[1]) ? "0" : object[1].toString()));
			orderList.add(order);
		}
		return orderList;
	}
	
	@Override
	public List<OrderHead> loadListGroupPay(OrderHead orderHead) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(poffset) as poffset,sum(pdeposit) as pdeposit,sum(pbalance) as pbalance,sum(pcash) as pcash,sum(pcard) as pcard,"
				+ "sum(ptransfer) as ptransfer,sum(pwechat) as pwechat,sum(palipay) as palipay,sum(pbill) as pbill from order_head h "
				+ "where h.dept_code = ? and h.status = 1 ");
		if(!Utils.isEmpty(orderHead.getStartDate()))
			sql.append(" and h.sale_date >= '" + orderHead.getStartDate() + "'");
		if(!Utils.isEmpty(orderHead.getEndDate()))
			sql.append(" and h.sale_date <= '" + orderHead.getEndDate() + "'");
		
		return super.loadListBySql(sql.toString(), OrderHead.class, orderHead.getDeptCode());
	}
	
	@Override
	public OrderHead loadPreOrderByMemIdAndKilo(Integer memId, Integer kilo) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from OrderHead h where h.memId = ? and h.mainten < ? order by h.createTime desc");
		List<OrderHead> list = super.loadList(hql.toString(), memId, Utils.isEmpty(kilo)?1000000:kilo);
		if(list.size() > 0)
			return list.get(0);
		else
			return null;
	}
	
	@Override
	public Double loadStoreSumOprice(OrderHead orderHead) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(s.number*m.price) from GoodsStock s, GoodsMaterial m "
				+ "where s.goodsId = m.id and s.deptCode = '" + orderHead.getDeptCode() + "' ");
		return (Double) super.loadUniqueResult(sql.toString());
	}
	
	@Override
	public Double loadStoreSumAprice(OrderHead orderHead) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(s.number*m.inPrice) from GoodsStock s, GoodsMaterial m "
				+ "where s.goodsId = m.id and s.deptCode = '" + orderHead.getDeptCode() + "' ");
		return (Double) super.loadUniqueResult(sql.toString());
	}
	
	@Override
	public Integer loadStoreTypeCount(OrderHead orderHead) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("from GoodsStock s where s.deptCode = '" + orderHead.getDeptCode() + "' ");
		Long count = super.loadListCount(sql.toString());
		return count.intValue();
	}
	
	@Override
	public Integer loadGoodsCount(OrderHead orderHead) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("from GoodsMaterial s where s.deptCode = '" + orderHead.getDeptName() + "' ");
		Long count = super.loadListCount(sql.toString());
		return count.intValue();
	}
	
	@Override
	public Integer loadCarsCount(OrderHead orderHead) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(distinct h.carNumber) from OrderHead h where h.deptCode = '" + orderHead.getDeptCode() + "' and h.status = 1 ");
		if(!Utils.isEmpty(orderHead.getStartDate()))
			sql.append(" and h.saleDate >= '" + orderHead.getStartDate() + "'");
		if(!Utils.isEmpty(orderHead.getEndDate()))
			sql.append(" and h.saleDate <= '" + orderHead.getEndDate() + "'");
		Long count = (Long) super.loadUniqueResult(sql.toString());
		return count.intValue();
	}
}

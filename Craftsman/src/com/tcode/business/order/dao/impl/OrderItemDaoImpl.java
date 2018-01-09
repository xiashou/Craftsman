package com.tcode.business.order.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.order.dao.OrderItemDao;
import com.tcode.business.order.model.OrderItem;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("orderItemDao")
public class OrderItemDaoImpl extends BaseDao<OrderItem, Serializable> implements OrderItemDao {

	@Override
	public List<OrderItem> loadListByMemIdPage(Integer memId, int start, int limit) throws Exception {
		List<OrderItem> orderList = new ArrayList<OrderItem>();
		List list = super.loadListForPage("select h.orderId,i.itemNo,h.saleDate,h.memId,h.vipNo,h.carId,h.carNumber,i.goodsId,i.goodsName,i.number,i.price "
				+ "from OrderItem i, OrderHead h where i.orderId = h.orderId and h.memId = " + memId + " and h.status = 1 order by h.saleDate desc", start, limit);
		OrderItem order;
		for(int i = 0; i < list.size(); i++){
			order = new OrderItem();
			Object[] object = (Object[]) list.get(i);
			order.setOrderId(object[0].toString());
			order.setItemNo(Integer.parseInt(object[1].toString()));
			order.setSaleDate(object[2].toString());
			order.setMemId(Integer.parseInt(object[3].toString()));
			order.setVipNo(object[4].toString());
			order.setCarId(Integer.parseInt(object[5].toString()));
			order.setCarNumber(object[6].toString());
			order.setGoodsId(object[7].toString());
			order.setGoodsName(Utils.isEmpty(object[8]) ? "" : object[8].toString());
			order.setNumber(Double.parseDouble(object[9].toString()));
			order.setPrice(Double.parseDouble(Utils.isEmpty(object[10]) ? "0.0" : object[10].toString()));
			
			orderList.add(order);
		}
		return orderList;
	}
	
	@Override
	public Integer loadListCountByMemIdPage(Integer memId) throws Exception {
		Long count = super.loadListCount("from OrderItem i, OrderHead h where i.orderId = h.orderId and h.memId = ? and h.status = 1", memId);
		return count.intValue();
	}
	
	@Override
	public List<OrderItem> loadListByGoodsIdPage(OrderItem item, int start, int limit) throws Exception {
		List<OrderItem> orderList = new ArrayList<OrderItem>();
		StringBuffer hql = new StringBuffer();
		hql.append("select h.orderId,i.itemNo,h.saleDate,h.memId,h.vipNo,h.carId,h.carNumber,i.goodsId,i.goodsName,i.number,i.price "
				+ "from OrderItem i, OrderHead h where i.orderId = h.orderId and h.deptCode = ? and h.status = 1 ");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getGoodsId()))
			hql.append(" and i.goodsId = '" + item.getGoodsId() + "'");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getStartDate()))
			hql.append(" and h.saleDate >= '" + item.getStartDate() + "'");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getEndDate()))
			hql.append(" and h.saleDate <= '" + item.getEndDate() + "'");
		
		hql.append(" order by h.saleDate desc");
		List list = super.loadListForPage(hql.toString(), start, limit, item.getDeptCode());
		OrderItem order;
		for(int i = 0; i < list.size(); i++) {
			order = new OrderItem();
			Object[] object = (Object[]) list.get(i);
			order.setOrderId(object[0].toString());
			order.setItemNo(Integer.parseInt(object[1].toString()));
			order.setSaleDate(object[2].toString());
			order.setMemId(Integer.parseInt(object[3].toString()));
			order.setVipNo(object[4].toString());
			order.setCarId(Integer.parseInt(object[5].toString()));
			order.setCarNumber(object[6].toString());
			order.setGoodsId(object[7].toString());
			order.setGoodsName(Utils.isEmpty(object[8]) ? "" : object[8].toString());
			order.setNumber(Double.parseDouble(object[9].toString()));
			order.setPrice(Double.parseDouble(Utils.isEmpty(object[10]) ? "0.0" : object[10].toString()));
			
			orderList.add(order);
		}
		return orderList;
	}
	
	@Override
	public Integer loadListCountByGoodsIdPage(OrderItem item) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from OrderItem i, OrderHead h where i.orderId = h.orderId and h.deptCode = ?");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getGoodsId()))
			hql.append(" and i.goodsId = '" + item.getGoodsId() + "'");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getStartDate()))
			hql.append(" and h.saleDate >= '" + item.getStartDate() + "'");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getEndDate()))
			hql.append(" and h.saleDate <= '" + item.getEndDate() + "'");
		Long count = super.loadListCount(hql.toString(), item.getDeptCode());
		return count.intValue();
	}
	
	@Override
	public List<OrderItem> loadLastListByDept(String deptCode, Integer limit) throws Exception {
		List<OrderItem> orderList = new ArrayList<OrderItem>();
		List list = super.loadListForPage("select h.orderId,h.saleDate,h.memId,h.carId,h.carNumber,i.goodsId,i.goodsName,i.number,i.price "
				+ "from OrderItem i, OrderHead h where i.orderId = h.orderId and h.deptCode = '" + deptCode + "' and status = 1 order by h.saleDate desc", 0, limit);
		OrderItem order;
		for(int i = 0; i < list.size(); i++){
			order = new OrderItem();
			Object[] object = (Object[]) list.get(i);
			order.setOrderId(object[0].toString());
			order.setSaleDate(object[1].toString());
			order.setMemId(Integer.parseInt(object[2].toString()));
			order.setCarId(Integer.parseInt(object[3].toString()));
			order.setCarNumber(object[4].toString());
			order.setGoodsId(object[5].toString());
			order.setGoodsName(Utils.isEmpty(object[6]) ? "" : object[6].toString());
			order.setNumber(Double.parseDouble(object[7].toString()));
			order.setPrice(Double.parseDouble(Utils.isEmpty(object[8]) ? "0.0" : object[8].toString()));
			
			orderList.add(order);
		}
		return orderList;
	}
	
	@Override
	public List<OrderItem> loadIncompleteListByDept(String deptCode) throws Exception {
		List<OrderItem> orderList = new ArrayList<OrderItem>();
		List list = super.loadList("select h.orderId,h.saleDate,h.memId,h.carId,h.carNumber,i.goodsId,i.goodsName,h.pdeposit,h.oprice,h.status "
				+ "from OrderItem i, OrderHead h where i.orderId = h.orderId and h.status >= 2 and i.itemNo = 10 and h.deptCode = '" + deptCode + "' order by h.saleDate asc");
		OrderItem order;
		for(int i = 0; i < list.size(); i++){
			order = new OrderItem();
			Object[] object = (Object[]) list.get(i);
			order.setOrderId(object[0].toString());
			order.setSaleDate(object[1].toString());
			order.setMemId(Integer.parseInt(object[2].toString()));
			order.setCarId(Integer.parseInt(object[3].toString()));
			order.setCarNumber(object[4].toString());
			order.setGoodsId(object[5].toString());
			order.setGoodsName(Utils.isEmpty(object[6]) ? "" : object[6].toString());
			order.setPrice(Double.parseDouble(Utils.isEmpty(object[7]) ? "0" : object[7].toString()));	//定金
			order.setOprice(Double.parseDouble(Utils.isEmpty(object[8]) ? "0" : object[8].toString())); //应收
			order.setStatus(Integer.parseInt(object[9].toString()));
			
			orderList.add(order);
		}
		return orderList;
	}
	
	@Override
	public List<OrderItem> loadListByDeptPage(OrderItem item, String deptCode, Integer start, Integer limit) throws Exception {
		List<OrderItem> orderList = new ArrayList<OrderItem>();
		StringBuffer hql = new StringBuffer();
//		hql.append("select h.orderId,h.orderType,i.itemNo,h.saleDate,h.memId,h.carId,h.carNumber,i.goodsId,i.goodsName,i.performer,"
//				+ "i.goodsType,i.discount,i.number,i.price,h.status,h.remark from OrderItem i, OrderHead h "
//				+ "where i.orderId = h.orderId and h.deptCode = '" + deptCode + "'");
		
		hql.append("select h.order_Id,h.order_Type,i.item_No,h.sale_Date,h.mem_Id,h.car_Id,h.car_Number,i.goods_Id,i.goods_Name,i.performer,"
				+ "i.goods_Type,i.discount,i.number,i.price,h.status,h.remark,h.aprice,h.mainten from Order_Item i right join Order_Head h on i.order_Id = h.order_Id "
				+ "where h.dept_Code = '" + deptCode + "'");
		
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getStatus()))
			hql.append(" and h.status >= " + item.getStatus());
		else
			hql.append(" and h.status > 0 and h.status <> 2 ");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getOrderType()))
			hql.append(" and h.order_Type = " + item.getOrderType());
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getOrderId()))
			hql.append(" and h.order_id = '" + item.getOrderId() + "'");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getCarNumber()))
			hql.append(" and h.car_Number like '%" + item.getCarNumber() + "%'");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getStartDate()))
			hql.append(" and h.sale_Date >= '" + item.getStartDate() + "'");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getEndDate()))
			hql.append(" and h.sale_Date <= '" + item.getEndDate() + "'");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getMemId()))
			hql.append(" and h.mem_Id = '" + item.getMemId() + "'");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getGoodsName()))
			hql.append(" and i.goods_Name like '%" + item.getGoodsName() + "%'");
		
		hql.append(" order by i.order_Id desc, i.item_No asc");
		
//		List list = super.loadListForPage(hql.toString(), start, limit);
		List list = super.loadListForSqlPage(hql.toString(), start, limit);
		OrderItem order;
		for(int i = 0; i < list.size(); i++){
			order = new OrderItem();
			Object[] object = (Object[]) list.get(i);
			order.setOrderId(object[0].toString());
			order.setOrderType(Integer.parseInt(object[1].toString()));
			order.setItemNo(Integer.parseInt(Utils.isEmpty(object[2]) ? "10" : object[2].toString()));
			order.setSaleDate(object[3].toString());
			order.setMemId(Integer.parseInt(object[4].toString()));
			order.setCarId(Integer.parseInt(object[5].toString()));
			order.setCarNumber(object[6].toString());
			order.setGoodsId(Utils.isEmpty(object[7]) ? "" : object[7].toString());
			order.setGoodsName(Utils.isEmpty(object[8]) ? "" : object[8].toString());
			order.setPerformer(Utils.isEmpty(object[9]) ? "" : object[9].toString());
			order.setGoodsType(Integer.parseInt(Utils.isEmpty(object[10]) ? "7" : object[10].toString()));
			order.setDiscount(Double.parseDouble(Utils.isEmpty(object[11]) ? "0" : object[11].toString()));
			order.setNumber(Double.parseDouble(Utils.isEmpty(object[12]) ? "0" : object[12].toString()));
			order.setPrice(Double.parseDouble(Utils.isEmpty(object[13]) ? "0" : object[13].toString()));
			order.setStatus(Integer.parseInt(object[14].toString()));
			order.setRemark(Utils.isEmpty(object[15]) ? "" :object[15].toString());
			order.setAprice(Double.parseDouble(Utils.isEmpty(object[16]) ? "" :object[16].toString()));
			order.setMainten(Integer.parseInt(Utils.isEmpty(object[17]) ? "0" : object[17].toString()));
//			order.setMobile(Utils.isEmpty(object[16]) ? "" : object[16].toString());
			
			orderList.add(order);
		}
		return orderList;
	}
	
	@Override
	public Integer loadListCountByDept(OrderItem item, String deptCode) throws Exception {
		StringBuffer hql = new StringBuffer();
		
//		hql.append("from OrderItem i, OrderHead h where i.orderId = h.orderId and h.deptCode = '" + deptCode + "'");
		
		hql.append("from Order_Item i right join Order_Head h on i.order_Id = h.order_Id "
				+ "where h.dept_Code = '" + deptCode + "'");
		
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getStatus()))
			hql.append(" and h.status >= " + item.getStatus());
		else
			hql.append(" and h.status > 0 and h.status <> 2 ");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getOrderType()))
			hql.append(" and h.order_Type = " + item.getOrderType());
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getOrderId()))
			hql.append(" and h.order_id = '" + item.getOrderId() + "'");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getCarNumber()))
			hql.append(" and h.car_Number like '%" + item.getCarNumber() + "%'");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getStartDate()))
			hql.append(" and h.sale_Date >= '" + item.getStartDate() + "'");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getEndDate()))
			hql.append(" and h.sale_Date <= '" + item.getEndDate() + "'");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getMemId()))
			hql.append(" and h.mem_Id = '" + item.getMemId() + "'");
		if(!Utils.isEmpty(item) && !Utils.isEmpty(item.getGoodsName()))
			hql.append(" and i.goods_Name like '%" + item.getGoodsName() + "%'");
		
		Long count = super.loadListSqlCount(hql.toString());
		return count.intValue();
	}
	
	@Override
	public List<OrderItem> loadOrderByKeyword(String deptCode, Integer memId, String keyword) throws Exception {
		List<OrderItem> orderList = new ArrayList<OrderItem>();
		List list = super.loadList("select h.orderId,h.saleDate,h.memId,h.carId,h.carNumber,i.goodsId,i.goodsName,i.number,i.price "
				+ "from OrderItem i, OrderHead h, GoodsMaterial m where i.orderId = h.orderId and i.goodsId = m.id and i.goodsType = 2 and h.deptCode = '" + deptCode + "' and "
				+ "h.memId = '" + memId + "' and (i.goodsName like '%" + keyword + "%' or i.goodsId like '%" + keyword + "%' or m.shorthand like '%" + keyword + "%') order by h.saleDate desc");
		OrderItem order;
		for(int i = 0; i < list.size(); i++){
			order = new OrderItem();
			Object[] object = (Object[]) list.get(i);
			order.setOrderId(object[0].toString());
			order.setSaleDate(object[1].toString());
			order.setMemId(Integer.parseInt(object[2].toString()));
			order.setCarId(Integer.parseInt(object[3].toString()));
			order.setCarNumber(object[4].toString());
			order.setGoodsId(object[5].toString());
			order.setGoodsName(object[6].toString());
			order.setNumber(Double.parseDouble(object[7].toString()));
			order.setPrice(Double.parseDouble(object[8].toString()));
			
			orderList.add(order);
		}
		return orderList;
	}
	
	@Override
	public List<OrderItem> loadOrderByKeywords(String deptCode, Integer memId, String keyword) throws Exception {
		List<OrderItem> orderList = new ArrayList<OrderItem>();
		List list = super.loadList("select h.orderId,h.saleDate,h.memId,h.carId,h.carNumber,i.goodsId,i.goodsName,i.number,i.price "
				+ "from OrderItem i, OrderHead h where i.orderId = h.orderId and h.status = 1 and "
				+ "h.memId = '" + memId + "' and (i.goodsName like '%" + keyword + "%' or i.goodsId like '%" + keyword + "%') order by h.saleDate desc");
		OrderItem order;
		for(int i = 0; i < list.size(); i++){
			order = new OrderItem();
			Object[] object = (Object[]) list.get(i);
			order.setOrderId(object[0].toString());
			order.setSaleDate(object[1].toString());
			order.setMemId(Integer.parseInt(object[2].toString()));
			order.setCarId(Integer.parseInt(object[3].toString()));
			order.setCarNumber(object[4].toString());
			order.setGoodsId(object[5].toString());
			order.setGoodsName(object[6].toString());
			order.setNumber(Double.parseDouble(object[7].toString()));
			order.setPrice(Double.parseDouble(object[8].toString()));
			
			orderList.add(order);
		}
		return orderList;
	}

	@Override
	public List<OrderItem> loadItemByOrderNo(String orderNo) throws Exception {
		return super.loadList("from OrderItem i where i.orderId = ? order by i.itemNo", orderNo);
	}
	
	@Override
	public List<OrderItem> loadProjectListByOrderNo(String orderNo) throws Exception {
		return super.loadList("from OrderItem i where i.orderId = ? and i.goodsType in (1,3,5) order by i.itemNo", orderNo);
	}
	
	@Override
	public List<OrderItem> loadProductListByOrderNo(String orderNo) throws Exception {
		return super.loadList("from OrderItem i where i.orderId = ? and i.goodsType in (2,4) order by i.itemNo", orderNo);
	}
	
	@Override
	public List<OrderItem> loadCommissionList(OrderItem orderItem) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select i.order_id as orderId, i.item_no as itemNo, i.goods_id as goodsId, i.goods_type as goodsType, i.type_id as typeId, i.goods_name as goodsName, i.unit_price as unitPrice, ");
		sql.append("CAST(i.number as decimal) as number, i.price, c.commission as source, i.performer, i.seller, i.middleman, h.sale_date as saleDate, h.status ");
		sql.append("from order_item i inner join order_head h on h.order_id = i.order_id left join shop_commission c on (c.goods_id = i.goods_id and c.dept_code = h.dept_code) ");
		sql.append("where h.dept_code = ? and h.status = 1 ");
		
		if(!Utils.isEmpty(orderItem.getStartDate()))
			sql.append(" and h.sale_date >= '" + orderItem.getStartDate() + "'");
		if(!Utils.isEmpty(orderItem.getEndDate()))
			sql.append(" and h.sale_date <= '" + orderItem.getEndDate() + "'");
		if(!Utils.isEmpty(orderItem.getGoodsName()))
			sql.append(" and i.goods_name like '%" + orderItem.getGoodsName() + "%'");
		if(!Utils.isEmpty(orderItem.getOrderType()))
			sql.append(" and h.order_type = '" + orderItem.getOrderType() + "'");
		return super.loadListBySql(sql.toString(), OrderItem.class, orderItem.getDeptCode());
	}
	
	@Override
	public void removeByOrderNo(String orderNo) throws Exception {
		super.executeHql("delete from OrderItem i where i.orderId = ?", orderNo);
	}
	
	@Override
	public List<OrderItem> loadOutGoodsListPage(OrderItem orderItem, Integer start, Integer limit) throws Exception {
		List<OrderItem> orderList = new ArrayList<OrderItem>();
		StringBuffer hql = new StringBuffer();
		hql.append("select i.typeId, i.goodsId, i.goodsName, SUM(i.number), SUM(i.price) from OrderItem i, OrderHead h where i.orderId = h.orderId and h.deptCode = ? and h.status = 1 and i.goodsType = 2 ");
		if(!Utils.isEmpty(orderItem.getStartDate()))
			hql.append(" and h.saleDate >= '" + orderItem.getStartDate() + "'");
		if(!Utils.isEmpty(orderItem.getEndDate()))
			hql.append(" and h.saleDate <= '" + orderItem.getEndDate() + "'");
		if(!Utils.isEmpty(orderItem.getGoodsType()))
			hql.append(" and i.typeId = " + orderItem.getGoodsType());
		if(!Utils.isEmpty(orderItem.getGoodsName()))
			hql.append(" and i.goodsName like '%" + orderItem.getGoodsName() + "%'");
		hql.append(" group by i.typeId, i.goodsId, i.goodsName");
		
		List list = super.loadListForPage(hql.toString(), start, limit, orderItem.getDeptCode());
		
		OrderItem order;
		for(int i = 0; i < list.size(); i++){
			order = new OrderItem();
			Object[] object = (Object[]) list.get(i);
			order.setTypeId(Integer.parseInt(Utils.isEmpty(object[0]) ? "0" : object[0].toString()));
			order.setGoodsId(object[1].toString());
			order.setGoodsName(Utils.isEmpty(object[2]) ? "" : object[2].toString());
			order.setNumber(Double.parseDouble(object[3].toString()));
			order.setPrice(Double.parseDouble(Utils.isEmpty(object[4]) ? "0" : object[4].toString()));
			
			orderList.add(order);
		}
		return orderList;
	}
	
	@Override
	public Integer loadOutGoodsListCount(OrderItem orderItem) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("select distinct i.goodsId from OrderItem i, OrderHead h where i.orderId = h.orderId and h.deptCode = ? and i.goodsType = 2 ");
		if(!Utils.isEmpty(orderItem.getStartDate()))
			hql.append(" and h.saleDate >= '" + orderItem.getStartDate() + "'");
		if(!Utils.isEmpty(orderItem.getEndDate()))
			hql.append(" and h.saleDate <= '" + orderItem.getEndDate() + "'");
		if(!Utils.isEmpty(orderItem.getGoodsType()))
			hql.append(" and i.typeId = " + orderItem.getGoodsType());
		if(!Utils.isEmpty(orderItem.getGoodsName()))
			hql.append(" and i.goodsName like '%" + orderItem.getGoodsName() + "%' ");
		
		return super.loadList(hql.toString(), orderItem.getDeptCode()).size();
	}
	
	@Override
	public List<OrderItem> loadListGroupProject(OrderItem orderItem) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select i.type_id as typeId, t.type_name as typeName, count(i.goods_id) as itemNo, sum(i.price) as price from order_item i, order_head h, god_hour_type t "
				+ "where h.order_id = i.order_id and h.dept_code = t.dept_code and i.type_id = t.id and h.dept_code = ? and h.status = 1 ");
		if(!Utils.isEmpty(orderItem.getStartDate()))
			sql.append(" and h.sale_date >= '" + orderItem.getStartDate() + "'");
		if(!Utils.isEmpty(orderItem.getEndDate()))
			sql.append(" and h.sale_date <= '" + orderItem.getEndDate() + "'");
		sql.append(" group by i.type_id, t.type_name");
		
		return super.loadListBySql(sql.toString(), OrderItem.class, orderItem.getDeptCode());
	}
	
	@Override
	public List<OrderItem> loadListGroupOrder(OrderItem orderItem) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select h.order_type as typeName, count(h.order_id) as itemNo, sum(h.aprice) as price from order_head h "
				+ "where h.dept_code = ? and h.status = 1 ");
		if(!Utils.isEmpty(orderItem.getStartDate()))
			sql.append(" and h.sale_date >= '" + orderItem.getStartDate() + "'");
		if(!Utils.isEmpty(orderItem.getEndDate()))
			sql.append(" and h.sale_date <= '" + orderItem.getEndDate() + "'");
		sql.append(" group by h.order_type");
		
		return super.loadListBySql(sql.toString(), OrderItem.class, orderItem.getDeptCode());
	}
	
	@Override
	public void editCommissioner(String orderId, String itemNo, String performer, String seller, String middleman) throws Exception {
		super.executeHql("update OrderItem i set i.performer = ?, i.seller = ?, i.middleman = ? where i.orderId = ? and i.itemNo = ?",performer,seller,middleman,orderId,itemNo);
	}
	
	
}

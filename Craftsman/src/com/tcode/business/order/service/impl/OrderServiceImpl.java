package com.tcode.business.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tcode.business.finance.dao.AssetsDao;
import com.tcode.business.finance.dao.ReceivableDao;
import com.tcode.business.finance.model.Receivable;
import com.tcode.business.goods.dao.GoodsMaterialDao;
import com.tcode.business.goods.dao.GoodsPackageDao;
import com.tcode.business.goods.dao.GoodsPackageDetailDao;
import com.tcode.business.goods.dao.GoodsStockDao;
import com.tcode.business.goods.model.GoodsMaterial;
import com.tcode.business.goods.model.GoodsPackage;
import com.tcode.business.goods.model.GoodsPackageDetail;
import com.tcode.business.member.dao.MemberCarDao;
import com.tcode.business.member.dao.MemberDao;
import com.tcode.business.member.dao.MemberStockDao;
import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberStock;
import com.tcode.business.order.dao.OrderHeadDao;
import com.tcode.business.order.dao.OrderItemDao;
import com.tcode.business.order.model.OrderHead;
import com.tcode.business.order.model.OrderItem;
import com.tcode.business.order.service.OrderService;
import com.tcode.business.order.util.OrderUtil;
import com.tcode.business.report.dao.ReptDeleteDao;
import com.tcode.business.report.dao.ReptGoodsDao;
import com.tcode.business.report.dao.ReptMemberStockDao;
import com.tcode.business.report.dao.ReptRechargeDao;
import com.tcode.business.report.model.ReptDelete;
import com.tcode.business.shop.dao.TypeCommissionDao;
import com.tcode.business.shop.model.TypeCommission;
import com.tcode.core.util.Utils;
import com.tcode.system.dao.SysUserDao;
import com.tcode.system.model.SysDept;
import com.tcode.system.model.SysUser;
import com.tcode.system.service.SysDeptService;

@Component("orderService")
public class OrderServiceImpl implements OrderService {
	
	private OrderHeadDao orderHeadDao;
	private OrderItemDao orderItemDao;
	private GoodsStockDao goodsStockDao;
	private MemberDao memberDao;
	private MemberCarDao memberCarDao;
	private MemberStockDao memberStockDao;
	private GoodsMaterialDao goodsMaterialDao;
	private GoodsPackageDao goodsPackageDao;
	private GoodsPackageDetailDao goodsPackageDetailDao;
	private ReptGoodsDao reptGoodsDao;
	private ReptDeleteDao reptDeleteDao;
	private ReptMemberStockDao reptMemberStockDao;
	private ReptRechargeDao reptRechargeDao;
	private AssetsDao assetsDao;
	private ReceivableDao receivableDao;
	private SysUserDao sysUserDao;
	private SysDeptService sysDeptService;
	private TypeCommissionDao typeCommissionDao;
	
	/**
	 * 创建订单主方法，添加事务处理
	 */
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public String insertOrder(OrderHead orderHead, List<OrderItem> itemList) throws Exception {
		String now = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		orderHead.setCreateTime(now);
		//积分抵现后，扣减积分
		if(orderHead.getPoffset() > 0)
			memberDao.editMemberPoint(orderHead.getMemId(), orderHead.getPoffset() * -1);
		//余额支付时，扣减余额
		if(!Utils.isEmpty(orderHead.getPbalance()) && orderHead.getPbalance() > 0)
			memberDao.editMemberBalance(orderHead.getMemId(), orderHead.getPbalance() * -1);
		//消费增加积分,消费完成时才增加积分
		if(orderHead.getPoint() > 0 && orderHead.getStatus() == 1)
			memberDao.editMemberPoint(orderHead.getMemId(), orderHead.getPoint());
		//添加一笔挂账
		if(!Utils.isEmpty(orderHead.getPbill()) && orderHead.getPbill() > 0) {
			Receivable receivable = receivableDao.loadById(orderHead.getDeptCode(), orderHead.getMemId(), orderHead.getCarId());
			if(!Utils.isEmpty(receivable)){
				receivable.setBillPrice(receivable.getBillPrice() + orderHead.getPbill());
				receivable.setStatus(0);
				receivable.setLastUpdate(now);
			} else {
				receivable = new Receivable(orderHead.getDeptCode(), orderHead.getMemId(), orderHead.getCarId(), orderHead.getPbill());
				receivableDao.save(receivable);
			}
		}
		
		//此处逻辑已增加数据库触发器
		//店铺资金增加
//		if(!Utils.isEmpty(orderHead.getPwechat()) && orderHead.getPwechat() > 0)
//			assetsDao.editPriceByCode(orderHead.getDeptCode(), "W", orderHead.getPwechat());
//		if(!Utils.isEmpty(orderHead.getPalipay()) && orderHead.getPalipay() > 0)
//			assetsDao.editPriceByCode(orderHead.getDeptCode(), "A", orderHead.getPalipay());
//		if(!Utils.isEmpty(orderHead.getPcard()) && orderHead.getPcard() > 0)
//			assetsDao.editPriceByCode(orderHead.getDeptCode(), "B", orderHead.getPcard());
//		if(!Utils.isEmpty(orderHead.getPtransfer()) && orderHead.getPtransfer() > 0)
//			assetsDao.editPriceByCode(orderHead.getDeptCode(), "B", orderHead.getPtransfer());
//		if(!Utils.isEmpty(orderHead.getPcash()) && orderHead.getPcash() > 0)
//			assetsDao.editPriceByCode(orderHead.getDeptCode(), "C", orderHead.getPcash());
		
		//保存订单头表
		orderHeadDao.save(orderHead);
		int itemNo = 10;
		for(OrderItem item : itemList) {
			item.setOrderId(orderHead.getOrderId());
			item.setItemNo(itemNo);
			orderItemDao.save(item);
			//实物类商品扣减库存
			if((item.getGoodsType() == 2 || item.getGoodsType() == 4) && orderHead.getStatus() == 1) {
				goodsStockDao.editByGoodsId(orderHead.getDeptCode(), item.getGoodsId(), item.getNumber());
				//商品流水记录
				reptGoodsDao.addRecord(orderHead.getDeptCode(), orderHead.getOrderId(), "S", item.getGoodsId(), item.getGoodsName(), item.getNumber());
			//套餐类商品增加会员库存
			} else if(item.getGoodsType() == 6) {
				List<GoodsPackageDetail> pList = goodsPackageDetailDao.loadAll(item.getGoodsId());
				if(!Utils.isEmpty(pList)) {
					for(GoodsPackageDetail detail : pList) {
						//增加会员库存
						MemberStock exist = memberStockDao.loadById(orderHead.getMemId(), detail.getGoodsId(), Utils.dateApart(item.getExpire()), detail.getGpId());
						if(!Utils.isEmpty(exist))
							memberStockDao.editStockNumber(orderHead.getMemId(), detail.getGoodsId(), Utils.dateApart(item.getExpire()), detail.getNumber(), detail.getGpId());
						else {
							MemberStock memberStock = new MemberStock();
							memberStock.setMemId(orderHead.getMemId());
							memberStock.setGoodsId(detail.getGoodsId());
							memberStock.setTypeId(detail.getTypeId());
							memberStock.setGoodsName(detail.getGoodsName());
							memberStock.setGoodsType(detail.getGoodsType());
							memberStock.setEndDate(Utils.dateApart(item.getExpire()));
							memberStock.setNumber(detail.getNumber());
							memberStock.setSource(detail.getGpId());
							memberStockDao.save(memberStock);
						}
						//实物类商品取消扣减库存，在会员消费时才扣库存
						//实物类商品扣减库存
//						if(detail.getGoodsType() == 2) {
//							goodsStockDao.editByGoodsId(orderHead.getDeptCode(), detail.getGoodsId(), detail.getNumber());
//							//商品流水记录
//							reptGoodsDao.addRecord(orderHead.getDeptCode(), orderHead.getOrderId(), "S", detail.getGoodsId(), detail.getGoodsName(), detail.getNumber());
//						}
						//记录一笔会员库存流水
						reptMemberStockDao.addRecord(orderHead.getDeptCode(), orderHead.getMemId(), orderHead.getOrderId(), detail.getGoodsId(), detail.getGoodsName(), detail.getNumber());
					}
				}
			}
			//扣次项
			if(item.getIsDeduction() >= 1 && orderHead.getStatus() == 1) {
				memberStockDao.editStockNumber(orderHead.getMemId(), item.getGoodsId(), item.getEndDate(), item.getNumber() * -1, Utils.isEmpty(item.getSource()) ? "" : item.getSource());
				//记录一笔会员库存流水
				reptMemberStockDao.addRecord(orderHead.getDeptCode(), orderHead.getMemId(), orderHead.getOrderId(), item.getGoodsId(), item.getGoodsName(), item.getNumber() * -1);
			}
			itemNo += 10;
		}
		return orderHead.getOrderId();
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public String insertReturnOrder(OrderHead orderHead, List<OrderItem> itemList) throws Exception {
		orderHead.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		//退货扣减积分
		if(orderHead.getPoint() < 0)
			memberDao.editMemberPoint(orderHead.getMemId(), orderHead.getPoint());
		orderHeadDao.save(orderHead);
		int itemNo = 10;
		for(OrderItem item : itemList){
			item.setOrderId(orderHead.getOrderId());
			item.setItemNo(itemNo);
			goodsStockDao.editByGoodsId(orderHead.getDeptCode(), item.getGoodsId(), item.getNumber());
			//商品流水记录
			reptGoodsDao.addRecord(orderHead.getDeptCode(), orderHead.getOrderId(), "R", item.getGoodsId(), item.getGoodsName(), item.getNumber());
			orderItemDao.save(item);
			itemNo += 10;
		}
		return orderHead.getOrderId();
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public String insertRechargeOrder(OrderHead orderHead) throws Exception {
		orderHead.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		//添加一笔挂账
		if(!Utils.isEmpty(orderHead.getPbill()) && orderHead.getPbill() > 0) {
			Receivable receivable = receivableDao.loadById(orderHead.getDeptCode(), orderHead.getMemId(), orderHead.getCarId());
			if(!Utils.isEmpty(receivable)){
				receivable.setBillPrice(receivable.getBillPrice() + orderHead.getPbill());
				receivable.setStatus(0);
				receivable.setLastUpdate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
			} else {
				receivable = new Receivable(orderHead.getDeptCode(), orderHead.getMemId(), orderHead.getCarId(), orderHead.getPbill());
				receivableDao.save(receivable);
			}
		}
		memberDao.editMemberBalance(orderHead.getMemId(), orderHead.getAprice());
		orderHeadDao.save(orderHead);
		return orderHead.getOrderId();
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public String insertQuickOrder(OrderHead orderHead, OrderItem orderItem) throws Exception {
		orderHead.setSaleDate(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()));
		orderHead.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		orderHead.setCarNumber(orderHead.getCarNumber().toUpperCase());
		orderHeadDao.save(orderHead);
		orderItem.setOrderId(orderHead.getOrderId());
		orderItem.setItemNo(10);
		orderItemDao.save(orderItem);
		return orderHead.getOrderId();
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void deleteOrderByNo(String orderNo) throws Exception {
		OrderHead orderHead = orderHeadDao.loadHeadByOrderNo(orderNo);
		List<OrderItem> itemList = orderItemDao.loadItemByOrderNo(orderNo);
		
		//未完成订单不涉及库存
		if(orderHead.getStatus() == 1) {
			for(OrderItem item : itemList) {
				if(item.getGoodsType() == 2 || item.getGoodsType() == 4) {	//实物类商品回到门店库存
					goodsStockDao.editByGoodsId(orderHead.getDeptCode(), item.getGoodsId(), item.getNumber() * -1);
					//商品流水记录
					reptGoodsDao.addRecord(orderHead.getDeptCode(), orderHead.getOrderId(), "D", item.getGoodsId(), item.getGoodsName(), item.getNumber()* -1);
				} else if(item.getGoodsType() == 1 && item.getIsDeduction() == 1) {	//扣次服务，退回会员库存
					memberStockDao.editStockNumber(orderHead.getMemId(), item.getGoodsId(), Utils.isEmpty(item.getEndDate()) ? "" : item.getEndDate(), item.getNumber(), item.getSource());
					//记录一笔会员库存流水
					reptMemberStockDao.addRecord(orderHead.getDeptCode(), orderHead.getMemId(), orderHead.getOrderId(), item.getGoodsId(), item.getGoodsName(), item.getNumber());
				} else if(item.getGoodsType() == 6) {	//套餐类商品还原会员库存
					GoodsPackage pack = goodsPackageDao.loadById(item.getGoodsId());
					List<GoodsPackageDetail> pList = goodsPackageDetailDao.loadAll(item.getGoodsId());
					if(!Utils.isEmpty(pList)) {
						for(GoodsPackageDetail detail : pList) {
							//改变会员库存
							MemberStock exist = memberStockDao.loadById(orderHead.getMemId(), detail.getGoodsId(), Utils.dateApart(orderHead.getSaleDate(), Utils.isEmpty(pack.getExpire())?0:pack.getExpire()), detail.getGpId());
							if(!Utils.isEmpty(exist))
								memberStockDao.editStockNumber(orderHead.getMemId(), detail.getGoodsId(), Utils.dateApart(orderHead.getSaleDate(), Utils.isEmpty(pack.getExpire())?0:pack.getExpire()), detail.getNumber() * -1, detail.getGpId());
							//实物类商品回到库存
							if(detail.getGoodsType() == 2) {
								goodsStockDao.editByGoodsId(orderHead.getDeptCode(), detail.getGoodsId(), detail.getNumber() * -1);	
								//商品流水记录
								reptGoodsDao.addRecord(orderHead.getDeptCode(), orderHead.getOrderId(), "D", detail.getGoodsId(), detail.getGoodsName(), detail.getNumber() * -1);							
							}
							//记录一笔会员库存流水
							reptMemberStockDao.addRecord(orderHead.getDeptCode(), orderHead.getMemId(), orderHead.getOrderId(), detail.getGoodsId(), detail.getGoodsName(), detail.getNumber() * -1);
						}
					}
				}
			}
		}
		
		//如有挂账记录则对应扣减
		if(!Utils.isEmpty(orderHead.getPbill()) && orderHead.getPbill() > 0) {
			Receivable receivable = receivableDao.loadById(orderHead.getDeptCode(), orderHead.getMemId(), orderHead.getCarId());
			if(!Utils.isEmpty(receivable)){
				receivable.setBillPrice(receivable.getBillPrice() - orderHead.getPbill());
				receivable.setLastUpdate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
			}
		}
		
		//充值订单还原余额并删除充值记录
		if(orderHead.getOrderType().equals("4")){
			memberDao.editMemberBalance(orderHead.getMemId(), orderHead.getAprice() * -1);
			reptRechargeDao.removeByOrder(orderHead.getOrderId());
		}
		
		if(!Utils.isEmpty(orderHead.getPbalance()) && orderHead.getPbalance() > 0)		//还原会员余额
			memberDao.editMemberBalance(orderHead.getMemId(), orderHead.getPbalance());
		if(!Utils.isEmpty(orderHead.getPoint()) && orderHead.getPoint() > 0)	//还原会员积分
			memberDao.editMemberPoint(orderHead.getMemId(), orderHead.getPoint() * -1);
		if(!Utils.isEmpty(orderHead.getPdeposit()) && orderHead.getPdeposit() > 0)		//定金转至余额
			memberDao.editMemberBalance(orderHead.getMemId(), orderHead.getPdeposit());
		
		//店铺资金减少
		if(!Utils.isEmpty(orderHead.getPwechat()) && orderHead.getPwechat() > 0)
			assetsDao.editPriceByCode(orderHead.getDeptCode(), "W", orderHead.getPwechat() * -1);
		if(!Utils.isEmpty(orderHead.getPalipay()) && orderHead.getPalipay() > 0)
			assetsDao.editPriceByCode(orderHead.getDeptCode(), "A", orderHead.getPalipay() * -1);
		if(!Utils.isEmpty(orderHead.getPcard()) && orderHead.getPcard() > 0)
			assetsDao.editPriceByCode(orderHead.getDeptCode(), "B", orderHead.getPcard() * -1);
		if(!Utils.isEmpty(orderHead.getPtransfer()) && orderHead.getPtransfer() > 0)
			assetsDao.editPriceByCode(orderHead.getDeptCode(), "B", orderHead.getPtransfer() * -1);
		if(!Utils.isEmpty(orderHead.getPcash()) && orderHead.getPcash() > 0)
			assetsDao.editPriceByCode(orderHead.getDeptCode(), "C", orderHead.getPcash() * -1);
		
		orderHead.setStatus(0);		//逻辑删除
		orderHeadDao.edit(orderHead);
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void deletePhysicsOrderByNo(String orderNo) throws Exception {
		OrderHead orderHead = orderHeadDao.loadHeadByOrderNo(orderNo);
		orderItemDao.removeByOrderNo(orderNo);
		orderHeadDao.remove(orderHead);
	}

	@Override
	public List<OrderItem> getListByMemIdPage(Integer memId, Integer start, Integer limit) throws Exception {
		List<OrderItem> orderList = orderItemDao.loadListByMemIdPage(memId, start, limit);
		for(OrderItem order : orderList) {
			order.setSaleDate(order.getSaleDate().substring(0, 10));
			if(!Utils.isEmpty(order.getMemId())){
				Member member = memberDao.loadById(order.getMemId());
				order.setName(Utils.isEmpty(member) ? "散客" : member.getName());
			}
		}
		return orderList;
	}
	
	@Override
	public Integer getListCountByMemIdPage(Integer memId) throws Exception {
		return orderItemDao.loadListCountByMemIdPage(memId);
	}
	
	@Override
	public List<OrderItem> getListByGoodsIdPage(OrderItem item, Integer start, Integer limit) throws Exception {
		List<OrderItem> orderList = orderItemDao.loadListByGoodsIdPage(item, start, limit);
		for(OrderItem order : orderList) {
			order.setSaleDate(order.getSaleDate().substring(0, 10));
			if(!Utils.isEmpty(order.getMemId())){
				Member member = memberDao.loadById(order.getMemId());
				order.setName(Utils.isEmpty(member) ? "散客" : member.getName());
			}
		}
		return orderList;
	}
	
	@Override
	public Integer getListCountByGoodsIdPage(OrderItem item) throws Exception {
		return orderItemDao.loadListCountByGoodsIdPage(item);
	}
	
	@Override
	public List<OrderItem> getLastListByDept(String deptCode, Integer limit) throws Exception {
		return orderItemDao.loadLastListByDept(deptCode, limit);
	}
	
	@Override
	public List<OrderItem> getIncompleteListByDept(String deptCode) throws Exception {
		List<OrderItem> orderList = orderItemDao.loadIncompleteListByDept(deptCode);
		for(OrderItem order : orderList) {
			if(!Utils.isEmpty(order.getMemId())){
				Member member = memberDao.loadById(order.getMemId());
				if(!Utils.isEmpty(member)){
					order.setMobile(member.getMobile());
					order.setName(member.getName() + (member.getSex().equals("1") ? "先生" : (member.getSex().equals("2") ? "女士" : "")));
				}
			}
		}
		return orderList;
	}
	
	@Override
	public List<OrderItem> getListByKeyword(String deptCode, Integer memId, String keyword) throws Exception {
		return orderItemDao.loadOrderByKeyword(deptCode, memId, keyword);
	}
	
	@Override
	public List<OrderItem> getListByKeywords(String deptCode, Integer memId, String keyword) throws Exception {
		return orderItemDao.loadOrderByKeywords(deptCode, memId, keyword);
	}
	
	@Override
	public List<OrderItem> getListByDeptPage(OrderItem item, String deptCode, Integer start, Integer limit) throws Exception {
		List<OrderItem> list = orderItemDao.loadListByDeptPage(item, deptCode, start, limit);
		for(OrderItem  orderItem : list) {
			if(orderItem.getMemId() != 0){
				Member member = memberDao.loadById(orderItem.getMemId());
				if(!Utils.isEmpty(member)){
					orderItem.setName(member.getName());
					orderItem.setMobile(member.getMobile());
				}
			}
		}
		return list;
	}
	
	@Override
	public Integer getListCountByDept(OrderItem item, String deptCode) throws Exception {
		return orderItemDao.loadListCountByDept(item, deptCode);
	}
	
	@Override
	public List<OrderItem> getRechargeListByDeptPage(OrderItem item, String deptCode, Integer start, Integer limit) throws Exception {
		return orderHeadDao.loadRechargeListByDeptPage(item, deptCode, start, limit);
	}
	
	@Override
	public Integer getRechargeListCountByDept(OrderItem item, String deptCode) throws Exception {
		return orderHeadDao.loadRechargeListCountByDept(item, deptCode);
	}
	
	@Override
	public Boolean insertOrderHead(OrderHead orderHead) throws Exception {
		orderHeadDao.save(orderHead);
		return true;
	}

	@Override
	public Boolean insertOrderItem(OrderItem orderItem) throws Exception {
		orderItemDao.save(orderItem);
		return true;
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public Boolean updateOrderHead(OrderHead orderHead) throws Exception {
		//余额支付时，扣减余额
		if(!Utils.isEmpty(orderHead.getPbalance()) && orderHead.getPbalance() > 0)
			memberDao.editMemberBalance(orderHead.getMemId(), orderHead.getPbalance() * -1);
		//消费增加积分
		if(orderHead.getPoint() > 0)
			memberDao.editMemberPoint(orderHead.getMemId(), orderHead.getPoint());
		//定金大于应收时，多余部分自动转余额
		if(orderHead.getPdeposit() > orderHead.getOprice())
			memberDao.editMemberBalance(orderHead.getMemId(), (orderHead.getPdeposit() - orderHead.getOprice()));
		orderHeadDao.edit(orderHead);
		return true;
	}

	@Override
	public OrderHead getHeadByOrderNo(String orderNo) throws Exception {
		return orderHeadDao.loadHeadByOrderNo(orderNo);
	}
	
	@Override
	public List<OrderItem> getItemByOrderNo(String orderNo) throws Exception {
		return orderItemDao.loadItemByOrderNo(orderNo);
	}
	
	@Override
	public Integer getUnfinishedOrderCount(String deptCode) throws Exception {
		Integer count = orderHeadDao.loadUnfinishedOrderCount(deptCode);
		return Utils.isEmpty(count) ? 0 : count;
	}
	
	@Override
	public List<OrderItem> getTypeListByOrderNo(String orderNo, Integer type) throws Exception {
		if(type == 1)
			return orderItemDao.loadProjectListByOrderNo(orderNo);
		else
			return orderItemDao.loadProductListByOrderNo(orderNo);
	}
	
	@Override
	public List<OrderItem> getOutGoodsListPage(OrderItem orderItem, Integer start, Integer limit) throws Exception {
		List<OrderItem> list = orderItemDao.loadOutGoodsListPage(orderItem, start, limit);
		for(OrderItem item : list) {
			if(!Utils.isEmpty(item.getGoodsId())) {
				GoodsMaterial goods = goodsMaterialDao.loadById(item.getGoodsId());
				if(!Utils.isEmpty(goods)) {
					item.setSpec(goods.getSpec());
					item.setColor(goods.getColor());
					item.setName(goods.getSize());		//尺码
					item.setMobile(goods.getUnit());	//单位
					item.setAprice(goods.getInPrice()); //成本价
				}
			}
		}
		return list;
	}
	
	@Override
	public Integer getOutGoodsListCount(OrderItem orderItem) throws Exception {
		return orderItemDao.loadOutGoodsListCount(orderItem);
	}
	
	@Override
	public List<OrderItem> getCommissionList(OrderItem orderItem) throws Exception {
		List<OrderItem> itemList = orderItemDao.loadCommissionList(orderItem);
		for(OrderItem item : itemList) {
			if(item.getGoodsType() == 2 || item.getGoodsType() == 4){
				GoodsMaterial mate = goodsMaterialDao.loadById(item.getGoodsId());
				if(!Utils.isEmpty(mate) && !Utils.isEmpty(mate.getInPrice()))
					item.setPrice(item.getPrice() - mate.getInPrice());
			} else if(item.getGoodsType() == 1 || item.getGoodsType() == 3){
				if(Utils.isEmpty(item.getSource())){
					if(!Utils.isEmpty(item.getTypeId())){
						TypeCommission typeComm = typeCommissionDao.loadById(item.getTypeId(), orderItem.getDeptCode());
						if(!Utils.isEmpty(typeComm))
							item.setSource(typeComm.getCommission());
					}
				}
			}
		}
		return itemList;
	}
	
	@Override
	public List<OrderHead> getCommissionList(OrderHead orderHead) throws Exception {
		return orderHeadDao.loadCommissionList(orderHead);
	}
	
	@Override
	public List<OrderHead> getCommissionSalesList(OrderHead orderHead) throws Exception {
		List<OrderHead> headList = orderHeadDao.loadCommissionList(orderHead);
		if(!Utils.isEmpty(headList)){
			for(OrderHead order : headList){
				if(!Utils.isEmpty(order.getMemId())){
					Member member = memberDao.loadById(order.getMemId());
					if(!Utils.isEmpty(member)){
						order.setName(member.getSales());	//设置销售顾问
					}
				}
			}
		}
		return headList;
	}
	
	@Override
	public void updateOrderStatus(String orderNo, Integer status) throws Exception {
		orderHeadDao.editOrderStatus(orderNo, status);
	}
	
	@Override
	public List<OrderHead> getBillListByMemIdPage(Integer memId, String deptCode, Integer start, Integer limit) throws Exception {
		List<OrderHead> list = orderHeadDao.loadBillListByMemIdPage(memId, deptCode, start, limit);
		for(OrderHead orderHead : list) {
			if(!Utils.isEmpty(orderHead.getMemId())) {
				Member member = memberDao.loadById(orderHead.getMemId());
				if(!Utils.isEmpty(member)) {
					orderHead.setName(member.getName());
					orderHead.setMobile(member.getMobile());
				}
			}
		}
		return list;
	}
	
	@Override
	public Integer getBillListCountByMemId(Integer memId, String deptCode) throws Exception {
		return orderHeadDao.loadBillListCountByMemId(memId, deptCode);
	}
	
	@Override
	public List<OrderHead> getWaterListPage(OrderHead orderHead, Integer start, Integer limit) throws Exception {
		List<OrderHead> list = orderHeadDao.loadWaterListPage(orderHead, start, limit);
		if(!Utils.isEmpty(list)) {
			for(OrderHead order : list) {
				//获取门店数据
				String deptCode = order.getDeptCode();
				SysDept sysDept = sysDeptService.getByDeptCode(deptCode);
				order.setDeptName(sysDept.getDeptName());
				
				if(!Utils.isEmpty(order.getMemId())) {
					Member member = memberDao.loadById(order.getMemId());
					if(!Utils.isEmpty(member)) {
						order.setName(member.getName());
						order.setMobile(member.getMobile());
					}
				}
				List<OrderItem> itemList = orderItemDao.loadItemByOrderNo(order.getOrderId());
				order.setOrderItemList(itemList);
				if(!Utils.isEmpty(itemList)) {
					Double cost = 0.0, profit = 0.0;
					for(OrderItem item : itemList) {
						if(item.getGoodsType() == 2 || item.getGoodsType() == 4) {
							GoodsMaterial goods = goodsMaterialDao.loadById(item.getGoodsId());
							if(!Utils.isEmpty(goods)) {
								cost += !Utils.isEmpty(goods.getInPrice()) ? goods.getInPrice() * item.getNumber() : 0;
								profit += ((!Utils.isEmpty(item.getPrice()) ? item.getPrice() : 0) - (!Utils.isEmpty(goods.getInPrice()) ? goods.getInPrice() * item.getNumber() : 0));
							}
						} else if(item.getGoodsType() == 1 || item.getGoodsType() == 3) {
							cost += Utils.isEmpty(item.getUnitPrice()) ? 0.0 : item.getUnitPrice();
							profit += Utils.isEmpty(item.getPrice()) ? 0.0 : item.getPrice();
						}
					}
					order.setCost(cost);
					order.setProfit(profit - (order.getOprice() - order.getAprice()));
				}
				order.setDisprice(order.getOprice() - order.getAprice());
//				if(!Utils.isEmpty(order.getPalipay()) && order.getPalipay() > 0)
//					payType.append("支付宝,");
//				if(!Utils.isEmpty(order.getPbalance()) && order.getPbalance() > 0)
//					payType.append("余额,");
//				if(!Utils.isEmpty(order.getPbill()) && order.getPbill() > 0)
//					payType.append("挂账,");
//				if(!Utils.isEmpty(order.getPcard()) && order.getPcard() > 0)
//					payType.append("刷卡,");
//				if(!Utils.isEmpty(order.getPcash()) && order.getPcash() > 0)
//					payType.append("现金,");
//				if(!Utils.isEmpty(order.getPtransfer()) && order.getPtransfer() > 0)
//					payType.append("转账,");
//				if(!Utils.isEmpty(order.getPwechat()) && order.getPwechat() > 0)
//					payType.append("微信,");
//				order.setPayType(payType.toString());
				order.setPayType(OrderUtil.initPayType(order));
			}
		}
		return list;
	}
	
	@Override
	public Integer getWaterListCount(OrderHead orderHead) throws Exception {
		return orderHeadDao.loadWaterListCount(orderHead);
	}
	
	@Override
	public List<OrderHead> getDeleteListPage(OrderHead head, Integer start, Integer limit) throws Exception {
		List<OrderHead> list = orderHeadDao.loadDeleteListPage(head, start, limit);
		for(OrderHead orderHead : list) {
			if(!Utils.isEmpty(orderHead.getMemId())) {
				Member member = memberDao.loadById(orderHead.getMemId());
				if(!Utils.isEmpty(member)) {
					orderHead.setName(member.getName());
					orderHead.setMobile(member.getMobile());
				}
			}
			ReptDelete reptDelete = reptDeleteDao.loadByOrderNo(orderHead.getOrderId());
			if(!Utils.isEmpty(reptDelete)){
				orderHead.setDeleteTime(reptDelete.getDeleteTime());
				SysUser sysUser = sysUserDao.loadById(Integer.parseInt(reptDelete.getDeleteUser()));
				if(!Utils.isEmpty(sysUser)) {
					orderHead.setDeleteUser(sysUser.getRealName());
				}
			}
		}
		return list;
	}
	
	@Override
	public Integer getDeleteListCount(OrderHead orderHead) throws Exception {
		return orderHeadDao.loadDeleteListCount(orderHead);
	}
	
	@Override
	public Double getDayTurnover(String companyId, String date, String deptCode) throws Exception {
		return orderHeadDao.loadDayTurnover(companyId, date, deptCode);
	}
	
	@Override
	public List<OrderHead> getSalesGroupType(String companyId, String date, String deptCode) throws Exception {
		List<OrderHead> orderList = orderHeadDao.loadSalesGroupType(companyId, date, deptCode);
		if(!Utils.isEmpty(orderList)){
			for(OrderHead order : orderList){
				if(!Utils.isEmpty(order) && !Utils.isEmpty(order.getOrderType())){
					if(order.getOrderType().equals("1"))
						order.setOrderType("项目消费");
					else if(order.getOrderType().equals("2"))
						order.setOrderType("套餐购买");
					else if(order.getOrderType().equals("3"))
						order.setOrderType("客户退货");
					else if(order.getOrderType().equals("4"))
						order.setOrderType("会员充值");
					else if(order.getOrderType().equals("5"))
						order.setOrderType("保险理赔");
					else if(order.getOrderType().equals("6"))
						order.setOrderType("散客开单");
					else
						order.setOrderType("套餐扣次");
				}
			}
		}
		return orderList;
	}
	
	@Override
	public List<OrderItem> getListGroupProject(OrderItem orderItem) throws Exception {
		return orderItemDao.loadListGroupProject(orderItem);
	}
	
	@Override
	public List<OrderItem> getListGroupOrder(OrderItem orderItem) throws Exception {
		return orderItemDao.loadListGroupOrder(orderItem);
	}
	
	@Override
	public List<OrderHead> getListGroupPay(OrderHead orderHead) throws Exception {
		return orderHeadDao.loadListGroupPay(orderHead);
	}
	
	@Override
	public Double getStoreSumOprice(OrderHead orderHead) throws Exception {
		Double price = orderHeadDao.loadStoreSumOprice(orderHead);
		return Utils.isEmpty(price) ? 0.0 : price;
	}
	
	@Override
	public Double getStoreSumAprice(OrderHead orderHead) throws Exception {
		Double price = orderHeadDao.loadStoreSumAprice(orderHead);
		return Utils.isEmpty(price) ? 0.0 : price;
	}
	
	@Override
	public Integer getStoreTypeCount(OrderHead orderHead) throws Exception {
		return orderHeadDao.loadStoreTypeCount(orderHead);
	}
	
	@Override
	public Integer getGoodsCount(OrderHead orderHead) throws Exception {
		return orderHeadDao.loadGoodsCount(orderHead);
	}
	
	@Override
	public Integer getCarsCount(OrderHead orderHead) throws Exception {
		return orderHeadDao.loadCarsCount(orderHead);
	}
	
	@Override
	public void updateCommissioner(String orderId, String itemNo, String performer, String seller, String middleman) throws Exception {
		orderItemDao.editCommissioner(orderId, itemNo, performer, seller, middleman);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	public OrderHeadDao getOrderHeadDao() {
		return orderHeadDao;
	}
	@Resource
	public void setOrderHeadDao(OrderHeadDao orderHeadDao) {
		this.orderHeadDao = orderHeadDao;
	}
	public OrderItemDao getOrderItemDao() {
		return orderItemDao;
	}
	@Resource
	public void setOrderItemDao(OrderItemDao orderItemDao) {
		this.orderItemDao = orderItemDao;
	}
	public GoodsStockDao getGoodsStockDao() {
		return goodsStockDao;
	}
	@Resource
	public void setGoodsStockDao(GoodsStockDao goodsStockDao) {
		this.goodsStockDao = goodsStockDao;
	}
	public MemberDao getMemberDao() {
		return memberDao;
	}
	@Resource
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	public MemberStockDao getMemberStockDao() {
		return memberStockDao;
	}
	@Resource
	public void setMemberStockDao(MemberStockDao memberStockDao) {
		this.memberStockDao = memberStockDao;
	}
	public MemberCarDao getMemberCarDao() {
		return memberCarDao;
	}
	@Resource
	public void setMemberCarDao(MemberCarDao memberCarDao) {
		this.memberCarDao = memberCarDao;
	}
	public GoodsPackageDetailDao getGoodsPackageDetailDao() {
		return goodsPackageDetailDao;
	}
	public GoodsPackageDao getGoodsPackageDao() {
		return goodsPackageDao;
	}
	@Resource
	public void setGoodsPackageDao(GoodsPackageDao goodsPackageDao) {
		this.goodsPackageDao = goodsPackageDao;
	}
	@Resource
	public void setGoodsPackageDetailDao(GoodsPackageDetailDao goodsPackageDetailDao) {
		this.goodsPackageDetailDao = goodsPackageDetailDao;
	}
	public ReptMemberStockDao getReptMemberStockDao() {
		return reptMemberStockDao;
	}
	@Resource
	public void setReptMemberStockDao(ReptMemberStockDao reptMemberStockDao) {
		this.reptMemberStockDao = reptMemberStockDao;
	}
	public ReptGoodsDao getReptGoodsDao() {
		return reptGoodsDao;
	}
	@Resource
	public void setReptGoodsDao(ReptGoodsDao reptGoodsDao) {
		this.reptGoodsDao = reptGoodsDao;
	}
	public GoodsMaterialDao getGoodsMaterialDao() {
		return goodsMaterialDao;
	}
	@Resource
	public void setGoodsMaterialDao(GoodsMaterialDao goodsMaterialDao) {
		this.goodsMaterialDao = goodsMaterialDao;
	}
	public AssetsDao getAssetsDao() {
		return assetsDao;
	}
	@Resource
	public void setAssetsDao(AssetsDao assetsDao) {
		this.assetsDao = assetsDao;
	}
	public ReceivableDao getReceivableDao() {
		return receivableDao;
	}
	@Resource
	public void setReceivableDao(ReceivableDao receivableDao) {
		this.receivableDao = receivableDao;
	}
	public ReptDeleteDao getReptDeleteDao() {
		return reptDeleteDao;
	}
	@Resource
	public void setReptDeleteDao(ReptDeleteDao reptDeleteDao) {
		this.reptDeleteDao = reptDeleteDao;
	}
	public SysUserDao getSysUserDao() {
		return sysUserDao;
	}
	@Resource
	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}
	public ReptRechargeDao getReptRechargeDao() {
		return reptRechargeDao;
	}
	@Resource
	public void setReptRechargeDao(ReptRechargeDao reptRechargeDao) {
		this.reptRechargeDao = reptRechargeDao;
	}
	public SysDeptService getSysDeptService() {
		return sysDeptService;
	}
	@Resource
	public void setSysDeptService(SysDeptService sysDeptService) {
		this.sysDeptService = sysDeptService;
	}
	public TypeCommissionDao getTypeCommissionDao() {
		return typeCommissionDao;
	}
	@Resource
	public void setTypeCommissionDao(TypeCommissionDao typeCommissionDao) {
		this.typeCommissionDao = typeCommissionDao;
	}

}

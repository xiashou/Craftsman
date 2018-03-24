package com.tcode.business.order.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.service.GoodsStockService;
import com.tcode.business.inte.jxcs.service.RegisterService;
import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.member.model.MemberStock;
import com.tcode.business.member.service.MemberCarService;
import com.tcode.business.member.service.MemberService;
import com.tcode.business.member.service.MemberStockService;
import com.tcode.business.order.model.OrderHead;
import com.tcode.business.order.model.OrderItem;
import com.tcode.business.order.service.OrderService;
import com.tcode.business.report.service.ReptDeleteService;
import com.tcode.business.report.service.ReptGoodsService;
import com.tcode.business.shop.model.Setting;
import com.tcode.business.shop.service.EmployeeService;
import com.tcode.business.shop.service.RechargeService;
import com.tcode.business.shop.service.SettingService;
import com.tcode.common.action.BaseAction;
import com.tcode.common.idgenerator.IDHelper;
import com.tcode.common.message.MsgUtil;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("orderAction")
public class OrderAction extends BaseAction {

	private static final long serialVersionUID = 7149369086462280668L;
	private static Logger log = Logger.getLogger("SLog");
	
	private OrderService orderService;
	private GoodsStockService goodsStockService;
	private MemberStockService memberStockService;
	private MemberService memberService;
	private MemberCarService memberCarService;
	private EmployeeService employeeService;
	private RechargeService rechargeService;
	private ReptDeleteService reptDeleteService;
	private ReptGoodsService reptGoodsService;
	private SettingService settingService;
	private RegisterService jx_registerService;
	
	private OrderHead orderHead;
	private OrderItem orderItem;
	private Member member;
	private MemberCar car;
	private Setting setting;
	
	private List<OrderItem> orderList;
	private List<OrderHead> hList;
	
	private String projectString;
	private String productString;
	private String packageString;
	private String giveProjectString;
	private String giveProductString;
	private String giveCouponsString;
	
	private String orderNo;
	private Integer memId;
	private String goodsId;
	private String keyword;
	private Integer rechargeId;
	private String detailItem;
	
	public String initNormalOrder() {
		return SUCCESS;
	}
	
	/**
	 * 创建普通订单
	 * @return
	 */
	public String submitOrder() {
		try {
			if(!Utils.isEmpty(orderHead)){
				//有传订单号的情况下，先删除订单，订单号不变继续操作
				if(!Utils.isEmpty(orderHead.getOrderId())){
					orderService.deletePhysicsOrderByNo(orderHead.getOrderId());
				} else 
					orderHead.setOrderId(IDHelper.getOrderNo());
				orderHead.setDeptCode(this.getDept().getDeptCode());
				List<OrderItem> itemList = new ArrayList<OrderItem>();
				JSONArray projectArray = JSONArray.fromObject(projectString);
				JSONArray productArray = JSONArray.fromObject(productString);
				//服务项目列表
				for (Object object : projectArray) {
					JSONObject json = (JSONObject) object;
					OrderItem orderItem = (OrderItem) JSONObject.toBean(json, OrderItem.class);
					itemList.add(orderItem);
				}
				//商品列表
				for (Object object : productArray) {
					JSONObject json = (JSONObject) object;
					OrderItem orderItem = (OrderItem) JSONObject.toBean(json, OrderItem.class);
					itemList.add(orderItem);
				}
				//赠送列表
				List<MemberStock> msList = new ArrayList<MemberStock>();
				//赠送服务
				if(!Utils.isEmpty(giveProjectString)){
					JSONArray giveProjectArray = JSONArray.fromObject(giveProjectString);
					for (Object object : giveProjectArray) {
						JSONObject json = (JSONObject) object;
						MemberStock memberStock = (MemberStock) JSONObject.toBean(json, MemberStock.class);
						memberStock.setMemId(orderHead.getMemId());
						memberStock.setSource("");
						memberStock.setGoodsType(3);
						memberStock.setDeptCode(this.getDept().getDeptCode());
						msList.add(memberStock);
					}
				}
				//赠送商品
				if(!Utils.isEmpty(giveProductString)){
					JSONArray giveProductArray = JSONArray.fromObject(giveProductString);
					for (Object object : giveProductArray) {
						JSONObject json = (JSONObject) object;
						MemberStock memberStock = (MemberStock) JSONObject.toBean(json, MemberStock.class);
						memberStock.setMemId(orderHead.getMemId());
						memberStock.setGoodsType(4);
						memberStock.setSource("");
						memberStock.setDeptCode(this.getDept().getDeptCode());
						msList.add(memberStock);
						//实物商品记录一笔流水
						reptGoodsService.insertRecord(this.getDept().getDeptCode(), orderHead.getOrderId(), "G", memberStock.getGoodsId(), memberStock.getGoodsName(), memberStock.getNumber());
					}
				}
				//赠送卡券
				if(!Utils.isEmpty(giveCouponsString)){
					JSONArray giveCouponsArray = JSONArray.fromObject(giveCouponsString);
					for (Object object : giveCouponsArray) {
						JSONObject json = (JSONObject) object;
						MemberStock memberStock = new MemberStock();
						memberStock.setMemId(orderHead.getMemId());
						memberStock.setGoodsType(5);
						memberStock.setTypeId("C");	//卡券商品类型为C
						memberStock.setSource("");
						memberStock.setDeptCode(this.getDept().getDeptCode());
						memberStock.setGoodsId(json.getString("id"));
						memberStock.setGoodsName(json.getString("name"));
						memberStock.setNumber(1.0);
						msList.add(memberStock);
					}
				}
				memberStockService.updateMoreStockNumber(msList);	//处理赠送
				orderNo = orderService.insertOrder(orderHead, itemList);
				if(Utils.isEmpty(orderNo))
					this.setResult(false, "消费记录生成失败，请联系管理员，稍后再试！");
				else {
					this.setResult(true, orderNo);
					if(orderHead.getStatus() == 2)
//						MsgUtil.sendConsumerMsg(4, orderNo);	//发送下单成功通知短信
						MsgUtil.sendConsumerMsg(this.getRequest(), 4, orderNo);	//发送下单成功通知短信
					else {
						if("5".equals(orderHead.getOrderType()))
							MsgUtil.sendConsumerMsg(5, orderNo);	//发送违章处理通知短信
						else
							MsgUtil.sendConsumerMsg(this.getRequest(), 1, orderNo);	//发送消费完成通知短信
//							MsgUtil.sendConsumerMsg(1, orderNo);	
					}
					jx_registerService.sendRepairInfo(orderHead, itemList);	//系统对接
				}
			}
		} catch(Exception e) {
			System.out.println("error!!!");
			this.setResult(false, "消费记录生成失败，请联系管理员，稍后再试！");
			log.error("##" + this.getDept().getDeptCode());
			log.error("OH:" + projectString);
			log.error("OI:" + productString);
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 套餐订单
	 * @return
	 */
	public String submitPackageOrder() {
		try {
			if(!Utils.isEmpty(orderHead)){
				orderHead.setOrderId(IDHelper.getOrderNo());
				List<OrderItem> itemList = new ArrayList<OrderItem>();
				JSONArray packageArray = JSONArray.fromObject(packageString);
				//套餐列表
				for (Object object : packageArray) {
					JSONObject json = (JSONObject) object;
					OrderItem orderItem = new OrderItem();
					orderItem.setGoodsId(json.getString("id"));
					orderItem.setGoodsName(json.getString("name"));
					orderItem.setUnitPrice(json.getDouble("price"));
					orderItem.setPrice(json.getDouble("price"));
					orderItem.setNumber(1.0);
					orderItem.setGoodsType(6);	//套餐类型
					orderItem.setIsDeduction(0);
					orderItem.setEndDate(json.getString("endDate"));
					orderItem.setExpire(json.getInt("expire"));
					itemList.add(orderItem);
				}
				//赠送列表
				List<MemberStock> msList = new ArrayList<MemberStock>();
				//赠送服务
				if(!Utils.isEmpty(giveProjectString)){
					JSONArray giveProjectArray = JSONArray.fromObject(giveProjectString);
					for (Object object : giveProjectArray) {
						JSONObject json = (JSONObject) object;
						MemberStock memberStock = (MemberStock) JSONObject.toBean(json, MemberStock.class);
						memberStock.setMemId(orderHead.getMemId());
//						memberStock.setSource(orderHead.getOrderId());
						memberStock.setGoodsType(3);
						msList.add(memberStock);
					}
				}
				//赠送商品
				if(!Utils.isEmpty(giveProductString)){
					JSONArray giveProductArray = JSONArray.fromObject(giveProductString);
					for (Object object : giveProductArray) {
						JSONObject json = (JSONObject) object;
						MemberStock memberStock = (MemberStock) JSONObject.toBean(json, MemberStock.class);
						memberStock.setMemId(orderHead.getMemId());
						memberStock.setGoodsType(4);
//						memberStock.setSource(orderHead.getOrderId());
						memberStock.setDeptCode(this.getDept().getDeptCode());	//此处加上部门，扣减库存时用到
						msList.add(memberStock);
						//实物商品记录一笔流水
						reptGoodsService.insertRecord(this.getDept().getDeptCode(), orderHead.getOrderId(), "G", memberStock.getGoodsId(), memberStock.getGoodsName(), memberStock.getNumber());
					}
				}
				//赠送卡券
				if(!Utils.isEmpty(giveCouponsString)){
					JSONArray giveCouponsArray = JSONArray.fromObject(giveCouponsString);
					for (Object object : giveCouponsArray) {
						JSONObject json = (JSONObject) object;
						MemberStock memberStock = new MemberStock();
						memberStock.setMemId(orderHead.getMemId());
						memberStock.setGoodsType(5);
//						memberStock.setSource(orderHead.getOrderId());
						memberStock.setGoodsId(json.getString("id"));
						memberStock.setGoodsName(json.getString("name"));
						memberStock.setNumber(1.0);
						msList.add(memberStock);
					}
				}
				orderHead.setDeptCode(this.getDept().getDeptCode());
				memberStockService.updateMoreStockNumber(msList);	//处理赠送
				orderNo = orderService.insertOrder(orderHead, itemList);
				if(Utils.isEmpty(orderNo))
					this.setResult(false, "消费记录生成失败，请联系管理员，稍后再试！");
				else{
					//MsgUtil.sendConsumerMsg(2, orderNo);	//发送通知短信
					MsgUtil.sendConsumerMsg(this.getRequest(), 1, orderNo);
					this.setResult(true, orderNo);
				}
			}
		} catch(Exception e) {
			this.setResult(false, "消费记录生成失败，请联系管理员，稍后再试！");
			log.error("##" + this.getDept().getDeptCode() + Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 退货订单
	 * @return
	 */
	public String submitReturnOrder() {
		try {
			if(!Utils.isEmpty(orderHead)){
				orderHead.setDeptCode(this.getDept().getDeptCode());
				List<OrderItem> itemList = new ArrayList<OrderItem>();
				JSONArray productArray = JSONArray.fromObject(productString);
				//商品列表
				for (Object object : productArray) {
					JSONObject json = (JSONObject) object;
					OrderItem orderItem = (OrderItem) JSONObject.toBean(json, OrderItem.class);
					itemList.add(orderItem);
				}
				orderHead.setOrderId(IDHelper.getOrderNo());
				orderNo = orderService.insertReturnOrder(orderHead, itemList);
				if(Utils.isEmpty(orderNo))
					this.setResult(false, "消费记录生成失败，请联系管理员，稍后再试！");
				else
					this.setResult(true, orderNo);
			}
		} catch(Exception e) {
			this.setResult(false, "消费记录生成失败，请联系管理员，稍后再试！");
			log.error("##" + this.getDept().getDeptCode() + Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 充值订单
	 * @return
	 */
	public String submitRechargeOrder() {
		try {
			if(!Utils.isEmpty(orderHead)){
				orderHead.setOrderId(IDHelper.getOrderNo());
				orderHead.setDeptCode(this.getDept().getDeptCode());
				//处理充值套餐
				if(!Utils.isEmpty(rechargeId))
					rechargeService.updateMemberStockByRecharge(orderHead, rechargeId, detailItem);
				orderNo = orderService.insertRechargeOrder(orderHead);
				if(Utils.isEmpty(orderNo))
					this.setResult(false, "消费记录生成失败，请联系管理员，稍后再试！");
				else {
					//MsgUtil.sendConsumerMsg(3, orderNo);	//发送通知短信		
					MsgUtil.sendConsumerMsg(this.getRequest(), 1, orderNo);
					this.setResult(true, orderNo);
				}
			}
		} catch(Exception e) {
			this.setResult(false, "消费记录生成失败，请联系管理员，稍后再试！");
			log.error("##" + this.getDept().getDeptCode() + Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除订单(逻辑删除)
	 * @return
	 */
	public String deleteOrderByNo() {
		try {
			if(!Utils.isEmpty(orderNo)){
				orderService.deleteOrderByNo(orderNo);
				reptDeleteService.insertRecord(this.getDept().getDeptCode(), orderNo, this.getUser().getUserId() + "");
				this.setResult(true, "删除成功!");
			}
		} catch(Exception e) {
			this.setResult(false, "订单删除失败，请联系管理员，稍后再试！");
			log.error("##" + this.getDept().getDeptCode() + Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 快速开单
	 * @return
	 */
	public String submitQuickOrder() {
		try {
			if(!Utils.isEmpty(orderHead) && !Utils.isEmpty(orderItem)){
				orderHead.setOrderId(IDHelper.getOrderNo());
				orderHead.setDeptCode(this.getDept().getDeptCode());
				orderHead.setStatus(1);
				orderHead.setCreator(this.getUser().getRealName());
				orderItem.setGoodsType(1);
				orderItem.setNumber(1.0);
				orderItem.setIsDeduction(0);
				orderNo = orderService.insertQuickOrder(orderHead, orderItem);
				if(Utils.isEmpty(orderNo))
					this.setResult(false, "消费记录生成失败，请联系管理员，稍后再试！");
				else
					this.setResult(true, orderNo);
			}
		} catch(Exception e) {
			this.setResult(false, "消费记录生成失败，请联系管理员，稍后再试！");
			log.error("##" + this.getDept().getDeptCode());
			log.error("OH:" + orderHead);
			log.error("OI:" + orderItem);
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据订单ID查询表头
	 * @return
	 */
	public String queryOrderHeadById() {
		try {
			if(!Utils.isEmpty(orderNo))
				orderHead = orderService.getHeadByOrderNo(orderNo);
		} catch(Exception e) {
			this.setResult(false, "获取订单信息失败！");
			log.error("##" + this.getDept().getDeptCode() + Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据ID取订单项目列表
	 * @return
	 */
	public String queryOrderProjectByOrderId(){
		try {
			if(!Utils.isEmpty(orderNo)) {
				orderList = orderService.getTypeListByOrderNo(orderNo, 1);
				this.setResult(true, "");
			}
		} catch(Exception e) {
			this.setResult(false, "订单项目获取失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据ID取订单商品列表
	 * @return
	 */
	public String queryOrderProductByOrderId(){
		try {
			if(!Utils.isEmpty(orderNo)) {
				orderList = orderService.getTypeListByOrderNo(orderNo, 2);
				this.setResult(true, "");
			}
		} catch(Exception e) {
			this.setResult(false, "订单商品获取失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 分页查询会员历史消费记录
	 * @return
	 */
	public String queryOrderByMemIdPage(){
		try {
			if(!Utils.isEmpty(memId)){
				this.setTotalCount(orderService.getListCountByMemIdPage(memId));
				orderList = orderService.getListByMemIdPage(memId, this.getStart(), this.getLimit());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 分页查询商品历史消费记录
	 * @return
	 */
	public String queryOrderByGoodsIdPage(){
		try {
			if(!Utils.isEmpty(orderItem)){
				orderItem.setDeptCode(this.getDept().getDeptCode());
				this.setTotalCount(orderService.getListCountByGoodsIdPage(orderItem));
				orderList = orderService.getListByGoodsIdPage(orderItem, this.getStart(), this.getLimit());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 分页查询店铺销售数据
	 * @return
	 */
	public String queryOrderListByDept(){
		try {
			this.setTotalCount(orderService.getListCountByDept(orderItem, this.getDept().getDeptCode()));
			orderList = orderService.getListByDeptPage(orderItem, this.getDept().getDeptCode(), this.getStart(), this.getLimit());
			for(OrderItem item : orderList) {
				if(!Utils.isEmpty(item.getPerformer()))
					item.setPerformerName(employeeService.getNamesByIds(item.getPerformer()));
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 分页查询店铺充值订单数据
	 * @return
	 */
	public String queryRechargeOrderListByDept(){
		try {
			this.setTotalCount(orderService.getRechargeListCountByDept(orderItem, this.getDept().getDeptCode()));
			orderList = orderService.getRechargeListByDeptPage(orderItem, this.getDept().getDeptCode(), this.getStart(), this.getLimit());
			for(OrderItem item : orderList) {
				if(!Utils.isEmpty(item.getPerformer()))
					item.setPerformerName(employeeService.getNamesByIds(item.getPerformer()));
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询店铺最后几条营业数据
	 * @return
	 */
	public String queryLastOrderByDept(){
		try {
			orderList = orderService.getLastListByDept(this.getDept().getDeptCode(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询店铺未完成订单
	 * @return
	 */
	public String queryIncompleteOrderByDept(){
		try {
			orderList = orderService.getIncompleteListByDept(this.getDept().getDeptCode());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据ID取订单行项目
	 * @return
	 */
	public String queryOrderItemByOrderId(){
		try {
			if(!Utils.isEmpty(orderNo)) {
				orderList = orderService.getItemByOrderNo(orderNo);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据关键字查询会员购买产品记录
	 * @return
	 */
	public String queryOrderByKeyword() {
		try {
			if(!Utils.isEmpty(keyword) && !Utils.isEmpty(memId)){
				orderList = orderService.getListByKeyword(this.getDept().getDeptCode(), memId, keyword);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据条件查询施工提成订单
	 * @return
	 */
	public String queryBuildCommOrder() {
		try {
			if(Utils.isEmpty(orderItem))
				orderItem = new OrderItem();
			orderItem.setDeptCode(this.getDept().getDeptCode());
			orderList = orderService.getCommissionList(orderItem);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据条件查询接单提成订单
	 * @return
	 */
	public String queryTakeCommHead() {
		try {
			if(Utils.isEmpty(orderHead))
				orderHead = new OrderHead();
			orderHead.setDeptCode(this.getDept().getDeptCode());
			hList = orderService.getCommissionList(orderHead);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据条件查询销售顾问提成
	 * @return
	 */
	public String querySalesCommHead() {
		try {
			if(Utils.isEmpty(orderHead))
				orderHead = new OrderHead();
			orderHead.setDeptCode(this.getDept().getDeptCode());
			hList = orderService.getCommissionSalesList(orderHead);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 通知车主取车
	 * @return
	 */
	public String noticeTakeOrderCar() {
		try {
			if(!Utils.isEmpty(orderNo)) {
				orderService.updateOrderStatus(orderNo, 3);
//				MsgUtil.sendConsumerMsg(11, orderNo);	//发送通知车主取车短信
				MsgUtil.sendConsumerMsg(getRequest(), 11, orderNo);
				this.setResult(true, "通知成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "通知发生错误！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 出库记录报表
	 * @return
	 */
	public String queryOutGoodsOrderList() {
		try {
			if(Utils.isEmpty(orderItem))
				orderItem = new OrderItem();
			orderItem.setDeptCode(this.getDept().getDeptCode());
			this.setTotalCount(orderService.getOutGoodsListCount(orderItem));
			orderList = orderService.getOutGoodsListPage(orderItem, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询会员历史挂账记录
	 * @return
	 */
	public String queryBillHeadListByMemIdPage() {
		try {
			if(!Utils.isEmpty(memId)){
				this.setTotalCount(orderService.getBillListCountByMemId(memId, this.getDept().getDeptCode()));
				hList = orderService.getBillListByMemIdPage(memId, this.getDept().getDeptCode(), this.getStart(), this.getLimit());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 分页查询营业流水
	 * @return
	 */
	public String queryWaterHeadList() {
		try {
			if(Utils.isEmpty(orderHead))
				orderHead = new OrderHead();
			orderHead.setDeptCode(this.getDept().getDeptCode());
			this.setTotalCount(orderService.getWaterListCount(orderHead));
			hList = orderService.getWaterListPage(orderHead, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 分页查询被删除订单
	 * @return
	 */
	public String queryDeleteHeadList() {
		try {
			if(Utils.isEmpty(orderHead))
				orderHead = new OrderHead();
			orderHead.setDeptCode(this.getDept().getDeptCode());
			this.setTotalCount(orderService.getDeleteListCount(orderHead));
			hList = orderService.getDeleteListPage(orderHead, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询打印订单1
	 * @return
	 */
	public String OrderPrint1() {
		try {
			if(!Utils.isEmpty(orderNo)) {
				orderHead = orderService.getHeadByOrderNo(orderNo);
				orderList = orderService.getItemByOrderNo(orderNo);
				member = memberService.getMemberById(orderHead.getMemId());
				car = memberCarService.getMemberCarById(orderHead.getCarId());
				setting = settingService.getById(this.getDept().getDeptCode());
				for(OrderItem item : orderList) {
					if(!Utils.isEmpty(item.getPerformer()))
						item.setPerformerName(employeeService.getNamesByIds(item.getPerformer()));
				}
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询打印订单2
	 * @return
	 */
	public String OrderPrint2() {
		try {
			if(!Utils.isEmpty(orderNo)) {
				orderHead = orderService.getHeadByOrderNo(orderNo);
				orderList = orderService.getItemByOrderNo(orderNo);
				member = memberService.getMemberById(orderHead.getMemId());
				car = memberCarService.getMemberCarById(orderHead.getCarId());
				setting = settingService.getById(this.getDept().getDeptCode());
				for(OrderItem item : orderList){
					item.setPerformerName(employeeService.getNamesByIds(item.getPerformer()));
				}
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询打印订单3
	 * @return
	 */
	public String OrderPrint3() {
		try {
			if(!Utils.isEmpty(orderNo)) {
				orderHead = orderService.getHeadByOrderNo(orderNo);
				if(orderHead.getOrderType().equals("7")){
					
				}
				orderList = orderService.getItemByOrderNo(orderNo);
				member = memberService.getMemberById(orderHead.getMemId());
				car = memberCarService.getMemberCarById(orderHead.getCarId());
				setting = settingService.getById(this.getDept().getDeptCode());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询打印订单4
	 * @return
	 */
	public String OrderPrint4() {
		try {
			if(!Utils.isEmpty(orderNo)) {
				orderHead = orderService.getHeadByOrderNo(orderNo);
				orderList = orderService.getItemByOrderNo(orderNo);
				member = memberService.getMemberById(orderHead.getMemId());
				car = memberCarService.getMemberCarById(orderHead.getCarId());
				setting = settingService.getById(this.getDept().getDeptCode());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String formatDouble(double s){
    	DecimalFormat fmt = new DecimalFormat("\u00A4##0.00");
    	return fmt.format(s);
	}
	
	public OrderService getOrderService() {
		return orderService;
	}
	@Resource
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public GoodsStockService getGoodsStockService() {
		return goodsStockService;
	}
	@Resource
	public void setGoodsStockService(GoodsStockService goodsStockService) {
		this.goodsStockService = goodsStockService;
	}
	public MemberStockService getMemberStockService() {
		return memberStockService;
	}
	@Resource
	public void setMemberStockService(MemberStockService memberStockService) {
		this.memberStockService = memberStockService;
	}
	public MemberService getMemberService() {
		return memberService;
	}
	@Resource
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	public MemberCarService getMemberCarService() {
		return memberCarService;
	}
	@Resource
	public void setMemberCarService(MemberCarService memberCarService) {
		this.memberCarService = memberCarService;
	}
	public EmployeeService getEmployeeService() {
		return employeeService;
	}
	@Resource
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public RechargeService getRechargeService() {
		return rechargeService;
	}
	@Resource
	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}
	public ReptDeleteService getReptDeleteService() {
		return reptDeleteService;
	}
	@Resource
	public void setReptDeleteService(ReptDeleteService reptDeleteService) {
		this.reptDeleteService = reptDeleteService;
	}
	public ReptGoodsService getReptGoodsService() {
		return reptGoodsService;
	}
	@Resource
	public void setReptGoodsService(ReptGoodsService reptGoodsService) {
		this.reptGoodsService = reptGoodsService;
	}
	public SettingService getSettingService() {
		return settingService;
	}
	@Resource
	public void setSettingService(SettingService settingService) {
		this.settingService = settingService;
	}
	public RegisterService getJx_registerService() {
		return jx_registerService;
	}
	@Resource
	public void setJx_registerService(RegisterService jx_registerService) {
		this.jx_registerService = jx_registerService;
	}

	
	
	
	
	
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public OrderHead getOrderHead() {
		return orderHead;
	}
	public void setOrderHead(OrderHead orderHead) {
		this.orderHead = orderHead;
	}
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	public String getProjectString() {
		return projectString;
	}
	public void setProjectString(String projectString) {
		this.projectString = projectString;
	}
	public String getProductString() {
		return productString;
	}
	public void setProductString(String productString) {
		this.productString = productString;
	}
	public String getPackageString() {
		return packageString;
	}
	public void setPackageString(String packageString) {
		this.packageString = packageString;
	}
	public String getGiveProjectString() {
		return giveProjectString;
	}
	public void setGiveProjectString(String giveProjectString) {
		this.giveProjectString = giveProjectString;
	}
	public String getGiveProductString() {
		return giveProductString;
	}
	public void setGiveProductString(String giveProductString) {
		this.giveProductString = giveProductString;
	}
	public String getGiveCouponsString() {
		return giveCouponsString;
	}
	public void setGiveCouponsString(String giveCouponsString) {
		this.giveCouponsString = giveCouponsString;
	}
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	public List<OrderItem> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<OrderItem> orderList) {
		this.orderList = orderList;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public MemberCar getCar() {
		return car;
	}
	public void setCar(MemberCar car) {
		this.car = car;
	}
	public Integer getRechargeId() {
		return rechargeId;
	}
	public void setRechargeId(Integer rechargeId) {
		this.rechargeId = rechargeId;
	}
	public String getDetailItem() {
		return detailItem;
	}
	public void setDetailItem(String detailItem) {
		this.detailItem = detailItem;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public List<OrderHead> gethList() {
		return hList;
	}
	public void sethList(List<OrderHead> hList) {
		this.hList = hList;
	}
	public Setting getSetting() {
		return setting;
	}
	public void setSetting(Setting setting) {
		this.setting = setting;
	}
	
}

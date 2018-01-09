package com.tcode.business.finance.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.finance.service.ReceivableService;
import com.tcode.business.finance.service.RepaymentService;
import com.tcode.business.order.model.OrderHead;
import com.tcode.business.order.model.OrderItem;
import com.tcode.business.order.service.OrderService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("overallAction")
public class OverallAction extends BaseAction {

	private static final long serialVersionUID = -2718821849680822669L;
	private static Logger log = Logger.getLogger("SLog");
	
	private OrderService orderService;
	private ReceivableService receivableService;
	private RepaymentService repaymentService;
	
	private OrderItem orderItem;
	private OrderHead orderHead;
	
	private List<OrderItem> itemList;
	private List<OrderHead> headList;
	
	/**
	 * 大项统计
	 * @return
	 */
	public String queryOverallProject() {
		try {
			if(Utils.isEmpty(orderItem))
				orderItem = new OrderItem();
			orderItem.setDeptCode(this.getDept().getDeptCode());
			itemList = orderService.getListGroupProject(orderItem);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 订单类型统计
	 * @return
	 */
	public String queryOverallOrder() {
		try {
			if(Utils.isEmpty(orderItem))
				orderItem = new OrderItem();
			orderItem.setDeptCode(this.getDept().getDeptCode());
			itemList = orderService.getListGroupOrder(orderItem);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 支付方式统计
	 * @return
	 */
	public String queryOverallPay() {
		try {
			if(Utils.isEmpty(orderHead))
				orderHead = new OrderHead();
			orderHead.setDeptCode(this.getDept().getDeptCode());
			headList = orderService.getListGroupPay(orderHead);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 挂账数据
	 * @return
	 */
	public String queryOverallBill() {
		try {
			if(Utils.isEmpty(orderHead))
				orderHead = new OrderHead();
			headList = new ArrayList<OrderHead>();
			orderHead.setDeptCode(this.getDept().getDeptCode());
			orderHead.setOrderType("C");
			double sCash = repaymentService.getSumByPay(orderHead);
			if(!Utils.isEmpty(sCash)){
				OrderHead rCash = new OrderHead();
				rCash.setRemark("现金还款");
				rCash.setPayType(sCash + "");
				headList.add(rCash);
			}
			orderHead.setOrderType("B");
			sCash = repaymentService.getSumByPay(orderHead);
			if(!Utils.isEmpty(sCash)){
				OrderHead rCash = new OrderHead();
				rCash.setRemark("银行卡还款");
				rCash.setPayType(sCash + "");
				headList.add(rCash);
			}
			orderHead.setOrderType("W");
			sCash = repaymentService.getSumByPay(orderHead);
			if(!Utils.isEmpty(sCash)){
				OrderHead rCash = new OrderHead();
				rCash.setRemark("微信还款");
				rCash.setPayType(sCash + "");
				headList.add(rCash);
			}
			orderHead.setOrderType("A");
			sCash = repaymentService.getSumByPay(orderHead);
			if(!Utils.isEmpty(sCash)){
				OrderHead rCash = new OrderHead();
				rCash.setRemark("支付宝还款");
				rCash.setPayType(sCash + "");
				headList.add(rCash);
			}
				
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 库存数据
	 * @return
	 */
	public String queryOverallStore() {
		try {
			if(Utils.isEmpty(orderHead))
				orderHead = new OrderHead();
			headList = new ArrayList<OrderHead>();
			orderHead.setDeptCode(this.getDept().getDeptCode());
			orderHead.setDeptName(this.getDept().getCompanyId());
			Double price = orderService.getStoreSumOprice(orderHead);
			if(!Utils.isEmpty(price)){
				OrderHead rCount = new OrderHead();
				rCount.setRemark("总零售价");
				rCount.setPayType(price + "");
				headList.add(rCount);
			}
			price = orderService.getStoreSumAprice(orderHead);
			if(!Utils.isEmpty(price)){
				OrderHead rCount = new OrderHead();
				rCount.setRemark("总成本价");
				rCount.setPayType(price + "");
				headList.add(rCount);
			}
			Integer count = orderService.getStoreTypeCount(orderHead);
			if(!Utils.isEmpty(count)){
				OrderHead rCount = new OrderHead();
				rCount.setRemark("总品类数");
				rCount.setPayType(count + "");
				headList.add(rCount);
			}
			count = orderService.getGoodsCount(orderHead);
			if(!Utils.isEmpty(count)){
				OrderHead rCount = new OrderHead();
				rCount.setRemark("商品资料总数");
				rCount.setPayType(count + "");
				headList.add(rCount);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 其他数据
	 * @return
	 */
	public String queryOverallOther() {
		try {
			if(Utils.isEmpty(orderHead))
				orderHead = new OrderHead();
			headList = new ArrayList<OrderHead>();
			orderHead.setDeptCode(this.getDept().getDeptCode());
			int count = receivableService.getCountByDept(orderHead);
			if(!Utils.isEmpty(count)){
				OrderHead rCount = new OrderHead();
				rCount.setRemark("挂账笔数");
				rCount.setPayType(count + "");
				headList.add(rCount);
			}
			count = orderService.getCarsCount(orderHead);
			if(!Utils.isEmpty(count)){
				OrderHead rCount = new OrderHead();
				rCount.setRemark("车辆进店台数");
				rCount.setPayType(count + "");
				headList.add(rCount);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	

	public OrderService getOrderService() {
		return orderService;
	}
	@Resource
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public ReceivableService getReceivableService() {
		return receivableService;
	}
	@Resource
	public void setReceivableService(ReceivableService receivableService) {
		this.receivableService = receivableService;
	}
	public RepaymentService getRepaymentService() {
		return repaymentService;
	}
	@Resource
	public void setRepaymentService(RepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}
	
	
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	public List<OrderItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<OrderItem> itemList) {
		this.itemList = itemList;
	}
	public OrderHead getOrderHead() {
		return orderHead;
	}
	public void setOrderHead(OrderHead orderHead) {
		this.orderHead = orderHead;
	}
	public List<OrderHead> getHeadList() {
		return headList;
	}
	public void setHeadList(List<OrderHead> headList) {
		this.headList = headList;
	}
	

}

package com.tcode.business.mall.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.mall.model.MallOrderHead;
import com.tcode.business.mall.service.MallOrderService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("mallOrderAction")
public class MallOrderAction extends BaseAction {

	private static final long serialVersionUID = -6305635188246416201L;
	private static Logger log = Logger.getLogger("SLog");
	
	private MallOrderService mallOrderService;
	
	private List<MallOrderHead> orderList;
	
	private MallOrderHead order;
	
	/**
	 * 完成订单
	 * @return
	 */
	public String completeMallOrder() {
		try {
			if(!Utils.isEmpty(order) && !Utils.isEmpty(order.getOrderId())){
				MallOrderHead orderHead = mallOrderService.getOrderHeadById(order.getOrderId());
				if(!Utils.isEmpty(orderHead)){
					if(!Utils.isEmpty(order.getRemark()))
						orderHead.setRemark(order.getRemark());
					if(!Utils.isEmpty(order.getConsignee()))
						orderHead.setConsignee(order.getConsignee());
					if(!Utils.isEmpty(order.getContact()))
						orderHead.setContact(order.getContact());
					if(!Utils.isEmpty(order.getAddress()))
						orderHead.setAddress(order.getAddress());
					if(!Utils.isEmpty(order.getPayType()))
						orderHead.setPayType(order.getPayType());
					if(!Utils.isEmpty(order.getStatus()))
						orderHead.setStatus(order.getStatus());
					mallOrderService.updateHead(orderHead);
					this.setResult(true, "成功！");
				} else
					this.setResult(false, "失败！");
			} else
				this.setResult(false, "失败！");
		} catch(Exception e) {
			this.setResult(false, "失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 分页查询订单
	 * @return
	 */
	public String queryMallOrderPendingPage() {
		try {
			WechatAuthorizerParams wechatApp = this.getWechatApp();
			if(Utils.isEmpty(order))
				order = new MallOrderHead();
			order.setAppId(wechatApp.getAuthorizerAppId());
			this.setTotalCount(mallOrderService.getOrderListCount(order));
			orderList = mallOrderService.getOrderListPage(order, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 发货
	 * @return
	 */
	public String sendMallOrderGoods() {
		try {
			if(!Utils.isEmpty(order) && !Utils.isEmpty(order.getOrderId())){
				MallOrderHead exist = mallOrderService.getOrderHeadById(order.getOrderId());
				if(!Utils.isEmpty(exist)){
					if(!Utils.isEmpty(order.getExpressNo()))
						exist.setExpressNo(order.getExpressNo());
					exist.setStatus(3);
					mallOrderService.updateHead(exist);
					this.setResult(true, "成功！");
				} else
					this.setResult(false, "订单不存在！");
			} else
				this.setResult(false, "失败！");
		} catch(Exception e) {
			this.setResult(false, "失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	/**
	 * 删除订单
	 * @return
	 */
	public String deleteMallOrder() {
		try {
			if(!Utils.isEmpty(order) && !Utils.isEmpty(order.getOrderId())){
				mallOrderService.deleteHead(order);
				this.setResult(true, "删除成功！");
			} else
				this.setResult(false, "失败！");
		} catch(Exception e) {
			this.setResult(false, "失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	

	public MallOrderService getMallOrderService() {
		return mallOrderService;
	}
	@Resource
	public void setMallOrderService(MallOrderService mallOrderService) {
		this.mallOrderService = mallOrderService;
	}

	public List<MallOrderHead> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<MallOrderHead> orderList) {
		this.orderList = orderList;
	}

	public MallOrderHead getOrder() {
		return order;
	}

	public void setOrder(MallOrderHead order) {
		this.order = order;
	}
	
	

}

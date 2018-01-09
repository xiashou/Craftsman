package com.tcode.open.wechat.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.member.model.MemberStock;
import com.tcode.business.member.service.MemberCarService;
import com.tcode.business.member.service.MemberStockService;
import com.tcode.business.order.model.OrderHead;
import com.tcode.business.order.model.OrderItem;
import com.tcode.business.order.service.OrderService;
import com.tcode.common.action.BaseAction;
import com.tcode.open.wechat.util.CenterUtil;
import com.tcode.open.wechat.vo.CenterVo;


/**
 *个人中心
 * @author supeng
 *
 */
@Scope("prototype")
@Component("CenterAction")
public class CenterAction extends BaseAction {
	
	private OrderService orderService;
	private MemberStockService memberStockService;
	private MemberCarService memberCarService;
	
	private OrderHead orderHead;
	private OrderItem orderItem;
	private List<OrderItem> orderItemList;
	private List<OrderHead> orderHeadList;
	private List<MemberCar> memberCarList;
	private CenterVo centerVo;
	private Member member;
	private List<MemberStock> memberStockList;
	
	private Integer carId;
	
	/**
	 * 初始化个人中心
	 * @return
	 */
	public String initCenter() {
		HttpServletRequest request = getRequest();
		Member member = (Member) request.getSession().getAttribute("member");
		try {
			centerVo = CenterUtil.initCenterInfo(member, carId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询抬头订单信息
	 * @return
	 */
	public String queryCenterForHeadOrderInfo() {
		try {
			this.setTotalCount(orderService.getWaterListCount(orderHead));
			orderHeadList = orderService.getWaterListPage(orderHead, this.getStart(), this.getLimit());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 根据订单号查询行项目订单信息
	 * @return
	 */
	public String queryCenterForItemOrderInfo() {
		try {
			if(orderItem != null) {
				orderItemList = orderService.getItemByOrderNo(orderItem.getOrderId());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 根据会员ID查询套餐信息
	 * @return
	 */
	public String queryCenterForMemberStockInfo() {
		try {
			if(member != null) {
				Integer memId = member.getMemId();
				memberStockList = memberStockService.getListDetailByMemId(memId);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 根据会员ID查询车辆信息
	 * @return
	 */
	public String queryCenterForMemberCarInfo() {
		try {
			if(member != null) {
				Integer memId = member.getMemId();
				memberCarList = memberCarService.getMemberCarByMemberId(memId);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 根据会员ID、门店编码查询会员消费记录
	 * @return
	 */
	public String queryCenterForSaleRecord() {
		try {
			if(member != null) {
				Integer memId = member.getMemId();
				String deptCode = member.getDeptCode();
				String keyword = orderItem.getGoodsId();
				orderItemList = orderService.getListByKeywords(deptCode, memId, keyword);
//				for(OrderItem orderItem : orderItemList) {
//					orderItem.setSaleDate(Utils.changeDateFormat(orderItem.getSaleDate(), "yyyy/MM/dd HH:mm", "yyyy年MM月dd日 HH:mm"));
//				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public CenterVo getCenterVo() {
		return centerVo;
	}

	public void setCenterVo(CenterVo centerVo) {
		this.centerVo = centerVo;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public List<OrderHead> getOrderHeadList() {
		return orderHeadList;
	}

	public void setOrderHeadList(List<OrderHead> orderHeadList) {
		this.orderHeadList = orderHeadList;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public OrderHead getOrderHead() {
		return orderHead;
	}

	public void setOrderHead(OrderHead orderHead) {
		this.orderHead = orderHead;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public List<MemberStock> getMemberStockList() {
		return memberStockList;
	}

	public void setMemberStockList(List<MemberStock> memberStockList) {
		this.memberStockList = memberStockList;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	@Resource
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public MemberStockService getMemberStockService() {
		return memberStockService;
	}

	@Resource
	public void setMemberStockService(MemberStockService memberStockService) {
		this.memberStockService = memberStockService;
	}

	public MemberCarService getMemberCarService() {
		return memberCarService;
	}

	@Resource
	public void setMemberCarService(MemberCarService memberCarService) {
		this.memberCarService = memberCarService;
	}

	public List<MemberCar> getMemberCarList() {
		return memberCarList;
	}

	public void setMemberCarList(List<MemberCar> memberCarList) {
		this.memberCarList = memberCarList;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	
	
}

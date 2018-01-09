package com.tcode.business.shop.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.member.service.MemberBookService;
import com.tcode.business.member.service.MemberCarService;
import com.tcode.business.member.service.MemberService;
import com.tcode.business.member.service.MemberVisitService;
import com.tcode.business.order.service.OrderService;
import com.tcode.business.shop.model.IndexStatistics;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("statisticsAction")
public class StatisticsAction extends BaseAction {

	private static final long serialVersionUID = 8206852408865695361L;
	private static Logger log = Logger.getLogger("SLog");
	
	private OrderService orderService;
	private MemberService memberService;
	private MemberCarService memberCarService;
	private MemberVisitService memberVisitService;
	private MemberBookService memberBookService;
	
	private List<IndexStatistics> statisList;
	
	/**
	 * 首页信息记录
	 * @return
	 * @throws Exception
	 */
	public String queryIndexStatistics() throws Exception {
		try {
			statisList = new ArrayList<IndexStatistics>();
			
			String deptCode = this.getDept().getDeptCode();
			String companyId = this.getDept().getCompanyId();
			
			if(!Utils.isEmpty(deptCode)){
				
				IndexStatistics statis1 = new IndexStatistics(1, "未完成订单数", orderService.getUnfinishedOrderCount(deptCode) + "");
				IndexStatistics statis2 = new IndexStatistics(2, "总会员数", memberService.getMemberCountByDept(companyId) + "");
				IndexStatistics statis3 = new IndexStatistics(3, "总车辆数", memberCarService.getCarCountByDept(companyId) + "");
				IndexStatistics statis4 = new IndexStatistics(4, "微信会员数", "0");
				IndexStatistics statis5 = new IndexStatistics(5, "会员总余额", memberService.getMemberBalanceSumByDept(companyId) + "");
				IndexStatistics statis6 = new IndexStatistics(6, "会员总积分", memberService.getMemberPointSumByDept(companyId) + "");
				IndexStatistics statis7 = new IndexStatistics(7, "客户预约", memberBookService.getBookCountByDept(deptCode) + "");
				IndexStatistics statis8 = new IndexStatistics(8, "待定", "0");
				IndexStatistics statis9 = new IndexStatistics(9, "待定", "0");
				
				statisList.add(statis1);
				statisList.add(statis2);
				statisList.add(statis3);
				statisList.add(statis4);
				statisList.add(statis5);
				statisList.add(statis6);
				statisList.add(statis7);
				statisList.add(statis8);
				statisList.add(statis9);
			}
			
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 首页回访记录
	 * @return
	 * @throws Exception
	 */
	public String queryIndexVisit() throws Exception {
		try {
			statisList = new ArrayList<IndexStatistics>();
			
			String deptCode = this.getDept().getDeptCode();
			
			if(!Utils.isEmpty(deptCode)){
				
				IndexStatistics statis1 = new IndexStatistics(1, "保养到期", memberVisitService.getVisitCount(deptCode, 1) + "");
				IndexStatistics statis2 = new IndexStatistics(2, "保险到期", memberVisitService.getVisitCount(deptCode, 2) + "");
				IndexStatistics statis3 = new IndexStatistics(3, "年检到期", memberVisitService.getVisitCount(deptCode, 3) + "");
				IndexStatistics statis4 = new IndexStatistics(4, "客户流失", memberVisitService.getVisitCount(deptCode, 4) + "");
				IndexStatistics statis5 = new IndexStatistics(5, "剩余服务到期", memberVisitService.getVisitCount(deptCode, 5) + "");
				IndexStatistics statis6 = new IndexStatistics(6, "余额不足", memberVisitService.getVisitCount(deptCode, 6) + "");
				
				statisList.add(statis1);
				statisList.add(statis2);
				statisList.add(statis3);
				statisList.add(statis4);
				statisList.add(statis5);
				statisList.add(statis6);
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
	public MemberVisitService getMemberVisitService() {
		return memberVisitService;
	}
	@Resource
	public void setMemberVisitService(MemberVisitService memberVisitService) {
		this.memberVisitService = memberVisitService;
	}
	public MemberBookService getMemberBookService() {
		return memberBookService;
	}
	@Resource
	public void setMemberBookService(MemberBookService memberBookService) {
		this.memberBookService = memberBookService;
	}

	public List<IndexStatistics> getStatisList() {
		return statisList;
	}
	public void setStatisList(List<IndexStatistics> statisList) {
		this.statisList = statisList;
	}
	
}

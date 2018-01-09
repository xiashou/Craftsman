package com.tcode.system.action;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.finance.model.Payable;
import com.tcode.business.finance.model.Receivable;
import com.tcode.business.finance.service.PayableService;
import com.tcode.business.finance.service.ReceivableService;
import com.tcode.business.goods.model.GoodsStock;
import com.tcode.business.goods.service.GoodsStockService;
import com.tcode.business.member.model.Member;
import com.tcode.business.member.service.MemberCarService;
import com.tcode.business.member.service.MemberService;
import com.tcode.business.order.model.OrderHead;
import com.tcode.business.order.service.OrderService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysDept;
import com.tcode.system.model.SysUser;
import com.tcode.system.service.SysDeptService;

@Scope("prototype")
@Component("bossAction")
public class BossAction extends BaseAction {

	private static final long serialVersionUID = 9178187522062726007L;

	private static Logger log = Logger.getLogger("SLog");
	
	private OrderService orderService;
	private MemberService memberService;
	private SysDeptService sysDeptService;
	private ReceivableService receivableService;
	private PayableService payableService;
	private MemberCarService memberCarService;
	private GoodsStockService goodsStockService;
	
	private String companyId;
	private String date;
	private String deptCode;
	private String keyword;
	
	private List<SysDept> deptList;
	private List<OrderHead> orderList;
	private List<Member> memberList;
	private List<Receivable> receList;
	private List<Payable> payList;
	private List<GoodsStock> stockList;
	private List<Double> rList;
	private Double price;
	private Integer gownth;
	
	/**
	 * 后台首页
	 * @return
	 * @throws Exception
	 */
	public String initBoss() throws Exception {
		SysUser user = (SysUser) this.getRequest().getSession().getAttribute(SESSION_MAGR);
		if(Utils.isEmpty(user))
			return LOGIN;
		else {
			String today = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			price = orderService.getDayTurnover(user.getDeptId(), today, deptCode);
			gownth = memberService.getDayGrowth(user.getDeptId(), today, deptCode);
			return SUCCESS;
		}
	}
	
	/**
	 * 营业额统计
	 * @return
	 */
	public String queryDayTurnover() {
		try {
			price = orderService.getDayTurnover(companyId, date, deptCode);
			this.setResult(true, "");
		} catch(Exception e) {
			this.setResult(false, "");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 会员增长量统计
	 * @return
	 */
	public String queryDayGownth() {
		try {
			gownth = memberService.getDayGrowth(companyId, date, deptCode);
			this.setResult(true, "");
		} catch(Exception e) {
			this.setResult(false, "");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 订单各类型销售额
	 * @return
	 */
	public String querySalesGroupType() {
		try {
			orderList = orderService.getSalesGroupType(companyId, date, deptCode);
			this.setResult(true, "");
		} catch(Exception e) {
			this.setResult(false, "");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 应收账款
	 * @return
	 */
	public String breceivable() {
		try {
			SysUser user = (SysUser) this.getRequest().getSession().getAttribute(SESSION_MAGR);
			if(!Utils.isEmpty(user)){
				deptList = sysDeptService.getByCompanyId(user.getDeptId());
				rList = receivableService.getSumByBoss(user.getDeptId(), date, deptCode);
				receList = receivableService.getListByBossPage(Utils.isEmpty(companyId) ? user.getDeptId() : companyId, deptCode, 0, 20);
				this.setResult(true, "");
			}
		} catch(Exception e) {
			this.setResult(false, "");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 应付账款
	 * @return
	 */
	public String bpayable() {
		try {
			SysUser user = (SysUser) this.getRequest().getSession().getAttribute(SESSION_MAGR);
			if(!Utils.isEmpty(user)){
				deptList = sysDeptService.getByCompanyId(user.getDeptId());
				rList = payableService.getSumByBoss(user.getDeptId(), date, deptCode);
				payList = payableService.getListByBossPage(Utils.isEmpty(companyId) ? user.getDeptId() : companyId, deptCode, 0, 20);
				this.setResult(true, "");
			}
		} catch(Exception e) {
			this.setResult(false, "");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 会员
	 * @return
	 */
	public String bmember() {
		try {
			SysUser user = (SysUser) this.getRequest().getSession().getAttribute(SESSION_MAGR);
			if(Utils.isEmpty(user))
				return LOGIN;
			else {
				if(Utils.isEmpty(rList))
					rList = new ArrayList<Double>();
				String today = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
				System.out.println();
				rList.add(memberService.getDayGrowth(user.getDeptId(), today, deptCode).doubleValue());
				rList.add(memberService.getDayGrowth(user.getDeptId(), today.substring(0, 7), deptCode).doubleValue());
				rList.add(memberService.getMemberCountByDept(user.getDeptId()).doubleValue());
				rList.add(memberCarService.getCarCountByDept(user.getDeptId()).doubleValue());
				rList.add(memberService.getMemberPointSumByDept(user.getDeptId()).doubleValue());
				rList.add(memberService.getMemberBalanceSumByDept(user.getDeptId()).doubleValue());
				for(int i = 1; i <= 12; i++){
					rList.add(memberService.getDayGrowth(user.getDeptId(), today.substring(0, 5) + (i<10?"0"+i:i), deptCode).doubleValue());
				}
				return SUCCESS;
			}
		} catch(Exception e) {
			this.setResult(false, "");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 销售页面
	 * @return
	 */
	public String bsales() {
		try {
			SysUser user = (SysUser) this.getRequest().getSession().getAttribute(SESSION_MAGR);
			if(!Utils.isEmpty(user))
				deptList = sysDeptService.getByCompanyId(user.getDeptId());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 库存
	 * @return
	 */
	public String bstore() {
		try {
			SysUser user = (SysUser) this.getRequest().getSession().getAttribute(SESSION_MAGR);
			if(Utils.isEmpty(user))
				return LOGIN;
			else {
				deptList = sysDeptService.getByCompanyId(user.getDeptId());
				if(Utils.isEmpty(rList))
					rList = new ArrayList<Double>();
				rList.add(goodsStockService.getByBossCount(user.getDeptId(), deptCode).doubleValue());
				rList.add(goodsStockService.getByBossCost(user.getDeptId(), deptCode).doubleValue());
				stockList = goodsStockService.getByBossType(user.getDeptId(), deptCode);
				this.setResult(true, "");
			}
		} catch(Exception e) {
			this.setResult(false, "");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查找库存
	 * @return
	 */
	public String bsearchStore() {
		try {
			SysUser user = (SysUser) this.getRequest().getSession().getAttribute(SESSION_MAGR);
			if(Utils.isEmpty(user))
				return LOGIN;
			else {
				deptList = sysDeptService.getByCompanyId(user.getDeptId());
				if(!Utils.isEmpty(keyword)){
					GoodsStock stock = new GoodsStock();
					stock.setCompanyId(user.getDeptId());
					stock.setName(keyword);
					if(!Utils.isEmpty(deptCode))
						stock.setDeptCode(deptCode);
					stockList = goodsStockService.getListByDeptPage(stock, 0, 100);
					this.setResult(true, "");
				}
			}
		} catch(Exception e) {
			this.setResult(false, "");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查找会员
	 * @return
	 */
	public String bsearchMember() {
		try {
			SysUser user = (SysUser) this.getRequest().getSession().getAttribute(SESSION_MAGR);
			if(Utils.isEmpty(user))
				return LOGIN;
			else {
				if(!Utils.isEmpty(keyword))
					memberList = memberService.getMemberByKeywordPage(user.getDeptId(), keyword, 0, 100);
				this.setResult(true, "");
			}
		} catch(Exception e) {
			this.setResult(false, "");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	
	
	public String formatDouble(double s){
		//\u00A4 人民币符号
		DecimalFormat fmt = new DecimalFormat("##0.00");
		return fmt.format(s);
	}
	
	public Integer formatInteger(double s){
		return (int)s;
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
	public SysDeptService getSysDeptService() {
		return sysDeptService;
	}
	@Resource
	public void setSysDeptService(SysDeptService sysDeptService) {
		this.sysDeptService = sysDeptService;
	}
	public ReceivableService getReceivableService() {
		return receivableService;
	}
	@Resource
	public void setReceivableService(ReceivableService receivableService) {
		this.receivableService = receivableService;
	}
	public PayableService getPayableService() {
		return payableService;
	}
	@Resource
	public void setPayableService(PayableService payableService) {
		this.payableService = payableService;
	}
	public MemberCarService getMemberCarService() {
		return memberCarService;
	}
	@Resource
	public void setMemberCarService(MemberCarService memberCarService) {
		this.memberCarService = memberCarService;
	}
	public GoodsStockService getGoodsStockService() {
		return goodsStockService;
	}
	@Resource
	public void setGoodsStockService(GoodsStockService goodsStockService) {
		this.goodsStockService = goodsStockService;
	}

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public Integer getGownth() {
		return gownth;
	}
	public void setGownth(Integer gownth) {
		this.gownth = gownth;
	}
	public List<SysDept> getDeptList() {
		return deptList;
	}
	public void setDeptList(List<SysDept> deptList) {
		this.deptList = deptList;
	}
	public List<OrderHead> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<OrderHead> orderList) {
		this.orderList = orderList;
	}
	public List<Receivable> getReceList() {
		return receList;
	}
	public void setReceList(List<Receivable> receList) {
		this.receList = receList;
	}
	public List<Double> getrList() {
		return rList;
	}
	public void setrList(List<Double> rList) {
		this.rList = rList;
	}
	public List<Payable> getPayList() {
		return payList;
	}
	public void setPayList(List<Payable> payList) {
		this.payList = payList;
	}
	public List<GoodsStock> getStockList() {
		return stockList;
	}
	public void setStockList(List<GoodsStock> stockList) {
		this.stockList = stockList;
	}
	public List<Member> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	

}

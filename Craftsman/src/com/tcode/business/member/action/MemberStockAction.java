package com.tcode.business.member.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.member.model.MemberStock;
import com.tcode.business.member.service.MemberStockService;
import com.tcode.business.report.model.ReptMemberStock;
import com.tcode.business.report.service.ReptMemberStockService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("memberStockAction")
public class MemberStockAction extends BaseAction {
	
	private static final long serialVersionUID = 9039149520883214548L;
	private static Logger log = Logger.getLogger("SLog");
	
	private MemberStockService memberStockService;
	private ReptMemberStockService reptMemberStockService;
	
	private List<MemberStock> msList;
	private MemberStock memberStock;
	private String jsonStr;
	
	private Integer memId;
	private Integer type;
	
	/**
	 * 根据ID查询会员库存
	 * @return
	 */
	public String queryMemberStockByMemId() {
		try {
			if(!Utils.isEmpty(memId)){
				msList = memberStockService.getListByMemId(memId, this.getDept().getCompanyId());
				this.setResult(true, "");
			}
		} catch(Exception e){
			this.setResult(false, Utils.getErrorMessage(e));
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询会员库存明细
	 * @return
	 */
	public String queryMemberStockDetailByMemId() {
		try {
			if(!Utils.isEmpty(memId)){
				msList = memberStockService.getListDetailByMemId(memId);
				this.setResult(true, "");
			}
		} catch(Exception e){
			this.setResult(false, Utils.getErrorMessage(e));
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询门店会员库存
	 * @return
	 */
	public String queryMemberStockByDept() {
		try {
			this.setTotalCount(memberStockService.getListCountByDept(this.getDept().getCompanyId(), memId));
			msList = memberStockService.getListByDept(this.getDept().getCompanyId(), memId);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改会员库存
	 * @return
	 */
	public String modifyMemberStock() {
		try {
			if(!Utils.isEmpty(memberStock) && !Utils.isEmpty(memberStock.getMemId()) && !Utils.isEmpty(memberStock.getGoodsId())){
				MemberStock exist = memberStockService.getById(memberStock.getMemId(), memberStock.getGoodsId(), memberStock.getEndDate(), memberStock.getSource());
				ReptMemberStock rept = new ReptMemberStock(this.getDept().getDeptCode(), memberStock.getMemId(), "", 
						memberStock.getGoodsId(), memberStock.getGoodsName(), memberStock.getNumber() - exist.getNumber());
				rept.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:dd").format(new Date()));
				reptMemberStockService.insert(rept);
				memberStockService.update(memberStock);
				this.setResult(true, "修改成功！");
			} else
				this.setResult(false, "参数缺失！");
		} catch(Exception e) {
			this.setResult(false, Utils.getErrorMessage(e));
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 补发会员库存
	 * @return
	 */
	public String reissueMemberStock(){
		try {
			if(!Utils.isEmpty(jsonStr) && !Utils.isEmpty(memId)){
				JSONArray array = JSONArray.fromObject(jsonStr);
				this.msList = new ArrayList<MemberStock>();
				MemberStock stock = null;
				for (Object object : array) {
					JSONObject json = (JSONObject) object;
					stock = (MemberStock) JSONObject.toBean(json, MemberStock.class);
					if(!Utils.isEmpty(stock)){
						stock.setMemId(memId);
						if(!Utils.isEmpty(type) && type == 1)
							stock.setSource("2");
						this.msList.add(stock);
					}
				}
				memberStockService.updateMoreStockByProc(msList);
				this.setResult(true, "成功" + (type == 1?"补发 ":"赠送 ") + msList.size() + " 条记录！");
			}
		} catch(Exception e) {
			this.setResult(false, Utils.getErrorMessage(e));
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	
	public MemberStockService getMemberStockService() {
		return memberStockService;
	}
	@Resource
	public void setMemberStockService(MemberStockService memberStockService) {
		this.memberStockService = memberStockService;
	}
	public ReptMemberStockService getReptMemberStockService() {
		return reptMemberStockService;
	}
	@Resource
	public void setReptMemberStockService(ReptMemberStockService reptMemberStockService) {
		this.reptMemberStockService = reptMemberStockService;
	}
	public List<MemberStock> getMsList() {
		return msList;
	}
	public void setMsList(List<MemberStock> msList) {
		this.msList = msList;
	}
	public MemberStock getMemberStock() {
		return memberStock;
	}
	public void setMemberStock(MemberStock memberStock) {
		this.memberStock = memberStock;
	}
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

}

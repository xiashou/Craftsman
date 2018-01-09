package com.tcode.business.shop.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.order.service.OrderService;
import com.tcode.business.shop.model.CommParam;
import com.tcode.business.shop.model.Commission;
import com.tcode.business.shop.model.TypeCommission;
import com.tcode.business.shop.service.CommParamService;
import com.tcode.business.shop.service.CommissionService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("commissionAction")
public class CommissionAction extends BaseAction {

	private static final long serialVersionUID = 4641275376087256114L;
	private static Logger log = Logger.getLogger("SLog");
	
	private CommissionService commissionService;
	private CommParamService commParamService;
	private OrderService orderService;
	
	private List<Commission> cList;
	private List<TypeCommission> tcList;
	private Commission commission;
	private CommParam commParam;
	private String type;
	private Integer typeId;
	
	private String jsonStr;
	
	/**
	 * 查找工时类提成设置
	 * @return
	 */
	public String queryHourCommission() {
		try {
			cList = commissionService.getListHourByDept(this.getDept().getDeptCode());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查找工时类提成设置
	 * @return
	 */
	public String queryMaterialCommission() {
		try {
			cList = commissionService.getListMateByDept(this.getDept().getDeptCode(), typeId);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 批量保存提成设置
	 * @return
	 */
	public String saveCommission() {
		try {
			if(!Utils.isEmpty(jsonStr)){
				JSONArray array = JSONArray.fromObject(jsonStr);
				for (Object object : array) {
					JSONObject json = (JSONObject) object;
					Commission commission = (Commission) JSONObject.toBean(json, Commission.class);
					if(!Utils.isEmpty(commission.getGoodsId())){
						commission.setDeptCode(this.getDept().getDeptCode());
						Commission exist = commissionService.getCommissionById(commission.getGoodsId(), this.getDept().getDeptCode());
						if(!Utils.isEmpty(exist))
							commissionService.update(commission);
						else
							commissionService.insert(commission);
					}
				}
				this.setResult(true, "保存成功！");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 保存提成相关人员
	 * @return
	 */
	public String saveCommissioner() {
		try {
			if(!Utils.isEmpty(jsonStr)){
				JSONArray array = JSONArray.fromObject(jsonStr);
				for (Object object : array) {
					JSONObject json = (JSONObject) object;
					orderService.updateCommissioner(json.get("orderId").toString(), json.get("itemNo").toString(), Utils.isEmpty(json.get("performer"))?"":json.get("performer").toString(), 
							Utils.isEmpty(json.get("seller"))?"":json.get("seller").toString(), Utils.isEmpty(json.get("middleman"))?"":json.get("middleman").toString());
					this.setResult(true, "保存成功！");
				}
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询门店所有大类提成
	 * @return
	 */
	public String queryTypeCommission() {
		try {
			tcList = commissionService.getTypeCommByDept(this.getDept().getDeptCode());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 批量保存大类提成设置
	 * @return
	 */
	public String saveTypeCommission() {
		try {
			if(!Utils.isEmpty(jsonStr)){
				JSONArray array = JSONArray.fromObject(jsonStr);
				for (Object object : array) {
					JSONObject json = (JSONObject) object;
					TypeCommission commission = (TypeCommission) JSONObject.toBean(json, TypeCommission.class);
					if(!Utils.isEmpty(commission.getTypeId())){
						commission.setDeptCode(this.getDept().getDeptCode());
						TypeCommission exist = commissionService.getTypeCommById(commission.getTypeId(), this.getDept().getDeptCode());
						if(!Utils.isEmpty(exist))
							commissionService.updateType(commission);
						else
							commissionService.insertType(commission);
					}
				}
				this.setResult(true, "保存成功！");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询部门所有参数
	 * @return
	 */
	public String queryCommParamById() {
		try {
			commParam = commParamService.getById(this.getDept().getDeptCode());
			this.setResult(true, "");
		} catch(Exception e) {
			this.setResult(false, "查询失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改参数配置
	 * @return
	 */
	public String updateCommParan() {
		try {
			if(!Utils.isEmpty(commParam)){
				commParam.setDeptCode(this.getDept().getDeptCode());
				commParamService.update(commParam);
				this.setResult(true, "修改成功！");
			}
		} catch (Exception e){
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertCommission() {
		try {
			if(!Utils.isEmpty(commission)){
				commissionService.insert(commission);
				this.setResult(true, "新建成功！");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String updateCommission() {
		try {
			if(!Utils.isEmpty(commission)){
				commissionService.update(commission);
				this.setResult(true, "修改成功！");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteCommission() {
		try {
			if(!Utils.isEmpty(commission)){
				commissionService.delete(commission);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public CommissionService getCommissionService() {
		return commissionService;
	}
	@Resource
	public void setCommissionService(CommissionService commissionService) {
		this.commissionService = commissionService;
	}
	public CommParamService getCommParamService() {
		return commParamService;
	}
	@Resource
	public void setCommParamService(CommParamService commParamService) {
		this.commParamService = commParamService;
	}

	public OrderService getOrderService() {
		return orderService;
	}
	@Resource
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public List<Commission> getcList() {
		return cList;
	}
	public void setcList(List<Commission> cList) {
		this.cList = cList;
	}
	public Commission getCommission() {
		return commission;
	}
	public void setCommission(Commission commission) {
		this.commission = commission;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	public List<TypeCommission> getTcList() {
		return tcList;
	}
	public void setTcList(List<TypeCommission> tcList) {
		this.tcList = tcList;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public CommParam getCommParam() {
		return commParam;
	}
	public void setCommParam(CommParam commParam) {
		this.commParam = commParam;
	}
	
}

package com.tcode.business.member.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.member.model.MemberGradePrice;
import com.tcode.business.member.service.MemberGradePriceService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("gradePriceAction")
public class MemberGradePriceAction extends BaseAction {

	private static final long serialVersionUID = -1499589575227318293L;
	private static Logger log = Logger.getLogger("SLog");
	
	private MemberGradePriceService gradePriceService;
	
	private List<MemberGradePrice> gradePriceList;
	private MemberGradePrice gradePrice;
	
	private String jsonStr;
	
	/**
	 * 根据ID查找
	 * @return
	 */
	public String queryGradePriceById() {
		try {
			if(!Utils.isEmpty(gradePrice)){
				gradePrice = gradePriceService.getGradePriceById(gradePrice.getGoodsId(), gradePrice.getGradeId(), this.getDept().getCompanyId());
				gradePriceList = new ArrayList<MemberGradePrice>();
				gradePriceList.add(gradePrice);
				this.setResult(true, "");
			}
		} catch(Exception e) {
			this.setResult(false, "等级价格出错！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询门店所有工时商品
	 * @return
	 */
	public String queryGradePriceByDeptCode() {
		try {
			if(!Utils.isEmpty(gradePrice) && !Utils.isEmpty(gradePrice.getGradeId()))
				gradePriceList = gradePriceService.getListByDeptCode(super.getDept().getCompanyId(), this.getDept().getDeptCode(), gradePrice.getGradeId());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 保存会员价格
	 * @return
	 */
	public String saveGradePrice() {
		try {
			if(!Utils.isEmpty(jsonStr)){
				JSONArray array = JSONArray.fromObject(jsonStr);
				for (Object object : array) {
					JSONObject json = (JSONObject) object;
					MemberGradePrice gradePrice = (MemberGradePrice) JSONObject.toBean(json, MemberGradePrice.class);
					if(!Utils.isEmpty(gradePrice.getGoodsId()) && !Utils.isEmpty(gradePrice.getGradeId())){
						gradePrice.setDeptCode(this.getDept().getCompanyId());
						MemberGradePrice exist = gradePriceService.getGradePriceById(gradePrice.getGoodsId(), gradePrice.getGradeId(), this.getDept().getCompanyId());
						if(!Utils.isEmpty(exist))
							gradePriceService.update(gradePrice);
						else
							gradePriceService.insert(gradePrice);
					}
				}
				this.setResult(true, "保存成功！");
			}
		} catch(Exception e) {
			this.setResult(false, Utils.getErrorMessage(e));
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertGradePrice() {
		try {
			if(!Utils.isEmpty(gradePrice)){
				gradePriceService.insert(gradePrice);
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
	public String updateGradePrice() {
		try {
			if(!Utils.isEmpty(gradePrice)){
				gradePriceService.update(gradePrice);
				this.setResult(true, "修改成功!");
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
	public String deleteGradePrice() {
		try {
			if(!Utils.isEmpty(gradePrice)){
				gradePriceService.delete(gradePrice);
				this.setResult(true, "删除成功!");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public MemberGradePriceService getGradePriceService() {
		return gradePriceService;
	}
	@Resource
	public void setGradePriceService(MemberGradePriceService gradePriceService) {
		this.gradePriceService = gradePriceService;
	}

	public List<MemberGradePrice> getGradePriceList() {
		return gradePriceList;
	}

	public void setGradePriceList(List<MemberGradePrice> gradePriceList) {
		this.gradePriceList = gradePriceList;
	}

	public MemberGradePrice getGradePrice() {
		return gradePrice;
	}

	public void setGradePrice(MemberGradePrice gradePrice) {
		this.gradePrice = gradePrice;
	}
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

}

package com.tcode.business.analysis.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.analysis.model.SalesAnalysis;
import com.tcode.business.analysis.service.SalesAnalysisService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("analysisAction")
public class AnalysisAction extends BaseAction {

	private static final long serialVersionUID = 2344263545420002494L;
	private static Logger log = Logger.getLogger("SLog");

	private SalesAnalysisService salesAnalysisService;
	
	private List<SalesAnalysis> saList;
	
	private Integer goodsType;
	private String salesDate;
	private String createTime;
	private Integer type;
	
	/**
	 * 查询销售分析数据
	 * @return
	 */
	public String querySalesAnalysis() {
		try {
			if(!Utils.isEmpty(goodsType) && !Utils.isEmpty(salesDate))
				saList = salesAnalysisService.getSalesAnalysisList(this.getDept().getDeptCode(), goodsType, salesDate, type);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询会员增长分析数据
	 * @return
	 */
	public String queryMemberAnalysis() {
		try {
			if(!Utils.isEmpty(createTime))
				saList = salesAnalysisService.getMemberAnalysisList(this.getDept().getDeptCode(), createTime);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	

	public SalesAnalysisService getSalesAnalysisService() {
		return salesAnalysisService;
	}
	@Resource
	public void setSalesAnalysisService(SalesAnalysisService salesAnalysisService) {
		this.salesAnalysisService = salesAnalysisService;
	}
	public List<SalesAnalysis> getSaList() {
		return saList;
	}
	public void setSaList(List<SalesAnalysis> saList) {
		this.saList = saList;
	}
	public Integer getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}
	public String getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	
}

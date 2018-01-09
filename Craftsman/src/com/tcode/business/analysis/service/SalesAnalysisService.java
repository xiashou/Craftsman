package com.tcode.business.analysis.service;

import java.util.List;

import com.tcode.business.analysis.model.SalesAnalysis;

public interface SalesAnalysisService {

	/**
	 * 查询销售统计报表
	 * @param deptCode
	 * @param goodsType
	 * @param salesDate
	 * @return
	 * @throws Exception
	 */
	public List<SalesAnalysis> getSalesAnalysisList(String deptCode, Integer goodsType, String salesDate, Integer type) throws Exception;
	
	/**
	 * 会员增长报表
	 * @param deptCode
	 * @param createTime
	 * @return
	 * @throws Exception
	 */
	public List<SalesAnalysis> getMemberAnalysisList(String deptCode, String createTime) throws Exception;
	
}

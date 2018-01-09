package com.tcode.business.analysis.dao;

import java.util.List;

import com.tcode.business.analysis.model.SalesAnalysis;

public interface SalesAnalysisDao {

	/**
	 * 项目消费图表数据
	 * @param deptCode
	 * @param goodsType
	 * @param salesDate
	 * @return
	 * @throws Exception
	 */
	public List<SalesAnalysis> loadSalesAnalysis(String deptCode, Integer goodsType, String salesDate, Integer type) throws Exception;
	
	/**
	 * 会员增长图表
	 * @param deptCode
	 * @param createTime
	 * @return
	 * @throws Exception
	 */
	public List<SalesAnalysis> loadMemberAnalysis(String deptCode, String createTime) throws Exception;
}

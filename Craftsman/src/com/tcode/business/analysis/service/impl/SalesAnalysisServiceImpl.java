package com.tcode.business.analysis.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.analysis.dao.SalesAnalysisDao;
import com.tcode.business.analysis.model.SalesAnalysis;
import com.tcode.business.analysis.service.SalesAnalysisService;
import com.tcode.business.goods.dao.GoodsHourTypeDao;
import com.tcode.business.goods.dao.GoodsMaterialTypeDao;
import com.tcode.business.goods.model.GoodsHourType;
import com.tcode.business.goods.model.GoodsMaterialType;
import com.tcode.core.util.Utils;

@Component("salesAnalysisService")
public class SalesAnalysisServiceImpl implements SalesAnalysisService {
	
	private SalesAnalysisDao salesAnalysisDao;
	private GoodsMaterialTypeDao goodsMaterialTypeDao;
	private GoodsHourTypeDao goodsHourTypeDao;

	@Override
	public List<SalesAnalysis> getSalesAnalysisList(String deptCode, Integer goodsType, String salesDate, Integer type) throws Exception {
		List<SalesAnalysis> saList = salesAnalysisDao.loadSalesAnalysis(deptCode, goodsType, salesDate, type);
		if(type == 2){
			for(SalesAnalysis analysis : saList){
				if(!Utils.isEmpty(analysis.getDays())){
					if(goodsType == 2) {
						GoodsMaterialType gtype = goodsMaterialTypeDao.loadById(Integer.parseInt(analysis.getDays()));
						if(!Utils.isEmpty(gtype))
							analysis.setDays(gtype.getTypeName());
					} else {
						GoodsHourType gtype = goodsHourTypeDao.loadById(Integer.parseInt(analysis.getDays()));
						if(!Utils.isEmpty(gtype))
							analysis.setDays(gtype.getTypeName());
					}
				}
			}
		}
		return saList;
	}
	
	@Override
	public List<SalesAnalysis> getMemberAnalysisList(String deptCode, String createTime) throws Exception {
		return salesAnalysisDao.loadMemberAnalysis(deptCode, createTime);
	}

	public SalesAnalysisDao getSalesAnalysisDao() {
		return salesAnalysisDao;
	}
	@Resource
	public void setSalesAnalysisDao(SalesAnalysisDao salesAnalysisDao) {
		this.salesAnalysisDao = salesAnalysisDao;
	}

	public GoodsMaterialTypeDao getGoodsMaterialTypeDao() {
		return goodsMaterialTypeDao;
	}
	@Resource
	public void setGoodsMaterialTypeDao(GoodsMaterialTypeDao goodsMaterialTypeDao) {
		this.goodsMaterialTypeDao = goodsMaterialTypeDao;
	}

	public GoodsHourTypeDao getGoodsHourTypeDao() {
		return goodsHourTypeDao;
	}
	@Resource
	public void setGoodsHourTypeDao(GoodsHourTypeDao goodsHourTypeDao) {
		this.goodsHourTypeDao = goodsHourTypeDao;
	}
	
	

}

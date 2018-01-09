package com.tcode.business.analysis.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.analysis.dao.SalesAnalysisDao;
import com.tcode.business.analysis.model.SalesAnalysis;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("salesAnalysisDao")
public class SalesAnalysisDaoImpl extends BaseDao<SalesAnalysis, Serializable> implements SalesAnalysisDao {

	@Override
	public List<SalesAnalysis> loadSalesAnalysis(String deptCode, Integer goodsType, String salesDate, Integer type) throws Exception {
		StringBuffer sql = new StringBuffer();
		
		if(type == 1)
			sql.append("select days=convert(char(10), sale_date, 120), price=sum(i.price) ");
		else 
			sql.append("select days=cast(i.[type_id] as varchar), price=sum(i.price) ");
		
		sql.append("from order_head h, order_item i where h.order_id = i.order_id ");
		sql.append("and h.dept_code = ? and h.status = 1 ");
						
		if(!Utils.isEmpty(salesDate))
			sql.append(" and h.sale_date like '" + salesDate + "%'");
		if(!Utils.isEmpty(goodsType)) {
			sql.append(" and i.goods_type = " + goodsType);
			if(type == 1)
				sql.append(" group by convert(char(10), sale_date, 120) ");
			else
				sql.append(" group by i.[type_id] ");
		}
		return super.loadListBySql(sql.toString(), SalesAnalysis.class, deptCode);
	}

	
	@Override
	public List<SalesAnalysis> loadMemberAnalysis(String deptCode, String createTime) throws Exception {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT days=convert(char(10), m.create_time, 120), count=count(m.id) ");
		
		sql.append("from mem_member m where m.dept_code = ? ");
						
		if(!Utils.isEmpty(createTime))
			sql.append(" and m.create_time like '" + createTime + "%'");
		
		sql.append(" group by convert(char(10), create_time, 120) ");
		return super.loadListBySql(sql.toString(), SalesAnalysis.class, deptCode);
	}
}

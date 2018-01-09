package com.tcode.business.goods.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsHourTypeDao;
import com.tcode.business.goods.model.GoodsHourType;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("goodsHourTypeDao")
public class GoodsHourTypeDaoImpl extends BaseDao<GoodsHourType, Serializable> implements GoodsHourTypeDao {

	@Override
	public GoodsHourType loadById(Integer id) throws Exception {
		return super.loadById(GoodsHourType.class, id);
	}
	
	@Override
	public List<GoodsHourType> loadTypeByDeptCode(String deptCode) throws Exception {
		return super.loadList("from GoodsHourType g where g.deptCode = ? order by g.sortNo", deptCode);
	}
	
	@Override
	public GoodsHourType loadTypeByName(String deptCode, String typeName) throws Exception {
		List<GoodsHourType> list = super.loadList("from GoodsHourType g where g.deptCode = ? and g.typeName = ?", deptCode, typeName);
		return Utils.isEmpty(list) ? null : list.get(0);
	}
	
	@Override
	public Integer loadListCount(GoodsHourType hourType) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(hourType);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(GoodsHourType hourType){
		DetachedCriteria criteria = DetachedCriteria.forClass(GoodsHourType.class);  
		if(hourType != null){
			if(hourType.getId() != null)
				criteria.add(Restrictions.eq("id", hourType.getId()));
			if(!Utils.isEmpty(hourType.getTypeName()))
				criteria.add(Restrictions.eq("typeName", hourType.getTypeName()));
			if(!Utils.isEmpty(hourType.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", hourType.getDeptCode()));
		}
		return criteria;
	}

	@Override
	public List<GoodsHourType> loadAll() throws Exception {
		return super.loadList("from GoodsHourType b order by b.sortNo asc");
	}
}

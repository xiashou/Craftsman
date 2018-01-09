package com.tcode.business.goods.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsMaterialTypeDao;
import com.tcode.business.goods.model.GoodsMaterialType;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("goodsMaterialTypeDao")
public class GoodsMaterialTypeDaoImpl extends BaseDao<GoodsMaterialType, Serializable> implements GoodsMaterialTypeDao {

	@Override
	public GoodsMaterialType loadById(Integer id) throws Exception {
		return super.loadById(GoodsMaterialType.class, id);
	}
	
	@Override
	public List<GoodsMaterialType> loadTypeByDeptCode(String deptCode) throws Exception {
		return super.loadList("from GoodsMaterialType g where g.deptCode = ? order by g.sortNo", deptCode);
	}
	
	@Override
	public Integer loadListCount(GoodsMaterialType materialType) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(materialType);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(GoodsMaterialType materialType){
		DetachedCriteria criteria = DetachedCriteria.forClass(GoodsMaterialType.class);  
		if(materialType != null){
			if(materialType.getId() != null)
				criteria.add(Restrictions.eq("id", materialType.getId()));
			if(!Utils.isEmpty(materialType.getTypeName()))
				criteria.add(Restrictions.eq("typeName", materialType.getTypeName()));
			if(!Utils.isEmpty(materialType.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", materialType.getDeptCode()));
		}
		return criteria;
	}

	@Override
	public List<GoodsMaterialType> loadAll() throws Exception {
		return super.loadList("from GoodsMaterialType b order by b.sortNo asc");
	}
}

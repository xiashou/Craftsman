package com.tcode.business.basic.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.basic.dao.BaseAreaDao;
import com.tcode.business.basic.model.BaseArea;
import com.tcode.common.dao.BaseDao;

@Component("baseAreaDao")
public class BaseAreaDaoImpl extends BaseDao<BaseArea, Serializable> implements BaseAreaDao {

	@Override
	public BaseArea loadById(String areaId) throws Exception {
		return super.loadById(BaseArea.class, areaId);
	}
	
	@Override
	public List<BaseArea> loadAllProvince() throws Exception {
		return super.loadList("from BaseArea a where a.areaId < ? order by a.areaId asc", 100);
	}
	
	@Override
	public List<BaseArea> loadCityById(String areaId) throws Exception {
		return super.loadList("from BaseArea a where a.areaId like '" + areaId + "%' and a.areaId > ?", areaId);
	}
	
	@Override
	public BaseArea loadByMemId(Integer memId) throws Exception {
		return (BaseArea) super.loadEntity("from BaseArea a where a.areaId = (select d.areaId from SysDept d where d.id = (select m.deptCode from Member m where m.memId = ?))", memId);
	}

	@Override
	public List<BaseArea> loadListPage(BaseArea area, int start, int limit) throws Exception {
		List<BaseArea> areaList = null;
		DetachedCriteria criteria = connectionCriteria(area);
		criteria.addOrder(Order.asc("areaId"));
		areaList = (List<BaseArea>) super.loadListForPage(criteria, start, limit);
		return areaList;
	}

	@Override
	public Integer loadListCount(BaseArea area) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(area);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(BaseArea area){
		DetachedCriteria criteria = DetachedCriteria.forClass(BaseArea.class);  
		if(area != null){
			if(area.getAreaId() != null)
				criteria.add(Restrictions.eq("areaId", area.getAreaId()));
		}
		return criteria;
	}

}

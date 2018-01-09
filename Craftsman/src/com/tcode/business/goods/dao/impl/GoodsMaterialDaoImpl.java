package com.tcode.business.goods.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsMaterialDao;
import com.tcode.business.goods.model.GoodsMaterial;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("goodsMaterialDao")
public class GoodsMaterialDaoImpl extends BaseDao<GoodsMaterial, Serializable> implements GoodsMaterialDao {

	@Override
	public List<GoodsMaterial> loadByDept(String deptCode) throws Exception {
		return super.loadList("from GoodsMaterial g where g.deptCode = ?", deptCode);
	}

	@Override
	public List<GoodsMaterial> loadByType(Integer typeId) throws Exception {
		return super.loadList("from GoodsMaterial g where g.typeId = ?", typeId);
	}
	
	@Override
	public GoodsMaterial loadById(String id) throws Exception {
		return super.loadById(GoodsMaterial.class, id);
//		List<GoodsMaterial> list = super.loadList("from GoodsMaterial g where g.deptCode = ? and g.id = ?", deptCode, id);
//		return Utils.isEmpty(list) ? null : list.get(0);
	}
	
	@Override
	public GoodsMaterial loadByName(String deptCode, String name) throws Exception {
		List<GoodsMaterial> list = super.loadList("from GoodsMaterial g where g.deptCode = ? and g.name = ?", deptCode, name);
		return Utils.isEmpty(list) ? null : list.get(0);
	}
	
	@Override
	public GoodsMaterial loadByCode(String deptCode, String code) throws Exception {
		List<GoodsMaterial> list = super.loadList("from GoodsMaterial g where g.deptCode = ? and g.code = ?", deptCode, code);
		return Utils.isEmpty(list) ? null : list.get(0);
	}
	
	@Override
	public GoodsMaterial loadByIdOrCode(String deptCode, String idOrCode) throws Exception {
		List<GoodsMaterial> list = super.loadList("from GoodsMaterial g where g.deptCode = ? and (g.id = ? or g.code = ?)", deptCode, idOrCode, idOrCode);
		return Utils.isEmpty(list) ? null : list.get(0);
	}
	
	@Override
	public List<GoodsMaterial> loadByKeyword(String deptCode, String keyword) throws Exception {
		return super.loadList("from GoodsMaterial g where g.deptCode = ? and (g.name like ? or g.code like ? or g.shorthand like ?) ", 
				deptCode, '%' + keyword + '%', '%' + keyword + '%', '%' + keyword + '%');
	}
	
	@Override
	public List<GoodsMaterial> loadByTypeAndKeyword(String deptCode, Integer typeId, String keyword) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from GoodsMaterial g where g.deptCode = ? ");
		if(!Utils.isEmpty(typeId))
			hql.append("and g.typeId = " + typeId);
		if(!Utils.isEmpty(keyword))
			hql.append(" and (g.name like '%" + keyword + "%' or g.code like '%" + keyword + "%' or g.shorthand like '%" + keyword + "%') ");
		return super.loadList(hql.toString(), deptCode);
	}
	
	@Override
	public List<GoodsMaterial> loadListPage(GoodsMaterial goodsMaterial, int start, int limit) throws Exception {
		List<GoodsMaterial> list = null;
		DetachedCriteria criteria = connectionCriteria(goodsMaterial);
		criteria.addOrder(Order.asc("id"));
		list = (List<GoodsMaterial>) super.loadListForPage(criteria, start, limit);
		return list;
	}

	@Override
	public Integer loadListCount(GoodsMaterial goodsMaterial) throws Exception {
		int totalCount = 0;
		DetachedCriteria criteria = connectionCriteria(goodsMaterial);
		totalCount = loadListCount(criteria);
		return totalCount;
	}

	public DetachedCriteria connectionCriteria(GoodsMaterial goodsMaterial) {
		DetachedCriteria criteria = DetachedCriteria.forClass(GoodsMaterial.class);
		if (goodsMaterial != null) {
			if (!Utils.isEmpty(goodsMaterial.getId()))
				criteria.add(Restrictions.eq("id", goodsMaterial.getId()));
			if (!Utils.isEmpty(goodsMaterial.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", goodsMaterial.getDeptCode()));
			if (goodsMaterial.getTypeId() > 0)
				criteria.add(Restrictions.eq("typeId", goodsMaterial.getTypeId()));
			if (!Utils.isEmpty(goodsMaterial.getName()))
				criteria.add(Restrictions.eq("name", goodsMaterial.getName()));
			if (!Utils.isEmpty(goodsMaterial.getCode()))
				criteria.add(Restrictions.eq("code", goodsMaterial.getCode()));
		}
		return criteria;
	}

	@Override
	public void removeByType(GoodsMaterial goodsMaterial) throws Exception {
		super.executeSql("delete from god_material where type_id = ?", goodsMaterial.getTypeId());
	}
}

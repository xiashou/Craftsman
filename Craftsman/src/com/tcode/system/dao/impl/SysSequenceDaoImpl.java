package com.tcode.system.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.common.dao.BaseDao;
import com.tcode.system.dao.SysSequenceDao;
import com.tcode.system.model.SysSequence;

@Component("sysSequenceDao")
public class SysSequenceDaoImpl extends BaseDao<SysSequence, Serializable> implements SysSequenceDao {


	@Override
	public SysSequence loadById(Integer id) throws Exception {
		return super.loadById(SysSequence.class, id);
	}

	@Override
	public List<SysSequence> loadListPage(SysSequence sequence, int start, int limit) throws Exception {
		List<SysSequence> list = null;
		DetachedCriteria criteria = connectionCriteria(sequence);
		criteria.addOrder(Order.asc("id"));
		list = (List<SysSequence>) super.loadListForPage(criteria, start, limit);
		return list;
	}
	
	@Override
	public SysSequence loadByName(String fieldName) throws Exception {
		List<SysSequence> sequenceList = super.loadList("from SysSequence s where s.fieldName = ?", fieldName);
		return sequenceList.size() > 0 ? sequenceList.get(0) : null;
	}

	@Override
	public Integer loadListCount(SysSequence sequence) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(sequence);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(SysSequence sequence){
		DetachedCriteria criteria = DetachedCriteria.forClass(SysSequence.class);  
		if(sequence != null){
			if(sequence.getId() != null)
				criteria.add(Restrictions.eq("id", sequence.getId()));
		}
		return criteria;
	}

}

package com.tcode.business.member.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.member.dao.MemberGradeDao;
import com.tcode.business.member.model.MemberGrade;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("memberGradeDao")
public class MemberGradeDaoImpl extends BaseDao<MemberGrade, Serializable> implements MemberGradeDao {

	@Override
	public List<MemberGrade> loadAll(String deptCode) throws Exception {
		return super.loadList("from MemberGrade g where g.deptCode = ?", deptCode);
	}
	@Override
	public MemberGrade loadById(Integer id) throws Exception {
		return super.loadById(MemberGrade.class, id);
	}
	
	@Override
	public MemberGrade loadByGrade(String deptCode, Integer grade) throws Exception {
		return (MemberGrade) super.loadEntity("from MemberGrade g where g.deptCode = ? and g.grade = ?", deptCode, grade);
	}
	
	@Override
	public List<MemberGrade> loadListPage(MemberGrade car, int start, int limit) throws Exception {
		List<MemberGrade> list = null;
		DetachedCriteria criteria = connectionCriteria(car);
		criteria.addOrder(Order.desc("id"));
		list = (List<MemberGrade>) super.loadListForPage(criteria, start, limit);
		return list;
	}

	@Override
	public Integer loadListCount(MemberGrade grade) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(grade);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(MemberGrade grade){
		DetachedCriteria criteria = DetachedCriteria.forClass(MemberGrade.class);  
		if(grade != null){
			if(!Utils.isEmpty(grade.getId()))
				criteria.add(Restrictions.eq("id", grade.getId()));
		}
		return criteria;
	}

}

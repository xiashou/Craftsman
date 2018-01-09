package com.tcode.business.member.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.member.dao.MemberBookDao;
import com.tcode.business.member.model.MemberBook;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("memberBookDao")
public class MemberBookDaoImpl extends BaseDao<MemberBook, Serializable> implements MemberBookDao {

	@Override
	public MemberBook loadById(Integer id) throws Exception {
		return super.loadById(MemberBook.class, id);
	}
	
	@Override
	public Integer loadBookCountByDept(String deptCode) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(MemberBook.class);  
		criteria.add(Restrictions.eq("deptCode", deptCode));
		criteria.add(Restrictions.eq("status", 1));
		int totalCount = loadListCount(criteria); 
		return totalCount;
	}

	@Override
	public List<MemberBook> loadListByPage(MemberBook book, int start, int limit) throws Exception {
		List<MemberBook> visitList = null;
		DetachedCriteria criteria = connectionCriteria(book);
		criteria.addOrder(Order.asc("status"));
		visitList = (List<MemberBook>) super.loadListForPage(criteria, start, limit);
		return visitList;
	}

	@Override
	public Integer loadListCount(MemberBook book) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(book);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(MemberBook book){
		DetachedCriteria criteria = DetachedCriteria.forClass(MemberBook.class);  
		if(book != null){
			if(!Utils.isEmpty(book.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", book.getDeptCode()));
			if(!Utils.isEmpty(book.getPhone()))
				criteria.add(Restrictions.like("phone", book.getPhone(), MatchMode.ANYWHERE));
			if(!Utils.isEmpty(book.getCarNumber()))
				criteria.add(Restrictions.like("carNumber", book.getCarNumber(), MatchMode.ANYWHERE));
		}
		return criteria;
	}

}

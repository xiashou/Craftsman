package com.tcode.business.member.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.member.dao.MemberDao;
import com.tcode.business.member.model.Member;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("memberDao")
public class MemberDaoImpl extends BaseDao<Member, Serializable> implements MemberDao {
	
	@Override
	public void saveOrUpdate(Member member) throws Exception {
		super.saveOrUpdate(member);
	}

	@Override
	public Member loadById(Integer id) throws Exception {
		return super.loadById(Member.class, id);
	}
	
	@Override
	public Member loadByMobile(String companyId, String mobile) throws Exception {
		List<Member> list = super.loadList("from Member m where m.companyId = ? and m.mobile = ?", companyId, mobile);
		return Utils.isEmpty(list) ? null : list.get(0);
	}
	
	@Override
	public Member loadByMobile(String mobile) throws Exception {
		List<Member> list = super.loadList("from Member m where m.mobile = ?", mobile);
		return Utils.isEmpty(list) ? null : list.get(0);
	}
	
	@Override
	public Member loadByMobileAndDeptCode(String deptCode, String mobile) throws Exception {
		List<Member> list = super.loadList("from Member m where m.deptCode = ? and m.mobile = ? ", deptCode, mobile);
		return Utils.isEmpty(list) ? null : list.get(0);
	}
	
	@Override
	public Member loadByMobileAndCompanyId(String companyId, String mobile) throws Exception {
		List<Member> list = super.loadList("from Member m where m.companyId = ? and m.mobile = ? ", companyId, mobile);
		return Utils.isEmpty(list) ? null : list.get(0);
	}
	
	@Override
	public List<Member> loadByVipNo(String vipNo) throws Exception {
		return super.loadList("from Member m where m.vipNo = ?", vipNo);
	}
	
	@Override
	public void editMemberPoint(Integer memId, Integer point) throws Exception {
		super.executeHql("update Member m set m.point = m.point + ? where m.memId = ?", point, memId);
	}
	
	@Override
	public void editMemberBalance(Integer memId, Double balance) throws Exception {
		super.executeHql("update Member m set m.balance = m.balance + ? where m.memId = ?", balance, memId);
	}
	
	@Override
	public void editMemberGrade(Integer memId, Integer grade) throws Exception {
		super.executeHql("update Member m set m.grade = ? where m.memId = ?", grade, memId);
	}
	
	@Override
	public void editMemberAlbum(Integer memId, Integer imageId) throws Exception {
		super.executeHql("update Member m set m.album = isnull(m.album, '') + ? where m.memId = ?", imageId + ",", memId);
	}
	
	@Override
	public void removeMemberAlbum(Integer memId, Integer imageId) throws Exception {
		super.executeHql("update Member m set m.album = replace(m.album, ?, '') where m.memId = ?", imageId + ",", memId);
	}
	
	@Override
	public List<Member> loadByKeywordPage(String companyId, String keyword, int start, int limit) throws Exception {
		List<Member> mList = new ArrayList<Member>();
		List list = super.loadListForPage("select m.id, m.vipNo, m.name, m.mobile, c.carShort, c.carCode, c.carNumber, c.id from Member m, MemberCar c "
				+ "where m.id = c.memberId and m.companyId = '" + companyId + "' and (m.mobile like '%" + keyword + "' or c.carNumber like '%" + keyword + "%')", start, limit);
		Member member;
		for(int i = 0; i < list.size(); i++){
			member = new Member();
			Object[] object = (Object[]) list.get(i);
			member.setMemId(Integer.valueOf(object[0].toString()));
			member.setVipNo(object[1].toString());
			member.setName(object[2].toString());
			member.setMobile(object[3].toString());
			member.setCarShort(object[4].toString());
			member.setCarCode(object[5].toString());
			member.setCarNumber(object[6].toString());
			member.setCarId(Integer.valueOf(object[7].toString()));
			member.setFullCarNumber(member.getCarShort() + member.getCarCode() + member.getCarNumber());
			mList.add(member);
		}
		return mList;
	}
	
	@Override
	public Integer loadByKeywordCount(String companyId, String keyword) throws Exception {
		Long count =  super.loadListCount("from Member m, MemberCar c "
				+ "where m.id = c.memberId and m.companyId = '" + companyId + "' and (m.mobile like '%" + keyword + "%' or c.carNumber like '%" + keyword + "%')");
		return count.intValue();
	}
	
	@Override
	public List<Member> loadListByIdsPage(Integer[] ids, int start, int limit) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(Member.class);
		criteria.add(Restrictions.in("id", ids));
		List<Member> memberList = (List<Member>) super.loadListForPage(criteria, start, limit);
		return memberList;
	}

	@Override
	public List<Member> loadListPage(Member member, int start, int limit) throws Exception {
		List<Member> memberList = null;
		DetachedCriteria criteria = connectionCriteria(member);
		criteria.addOrder(Order.desc("id"));
		memberList = (List<Member>) super.loadListForPage(criteria, start, limit);
		return memberList;
	}

	@Override
	public Integer loadListCount(Member member) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(member);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	@Override
	public Integer loadMemberCountByDept(String companyId) throws Exception {
		Long count = (Long) super.loadUniqueResult("select count(m.memId) from Member m where m.companyId = '" + companyId + "'");
		return count.intValue();
	}
	
	public Integer loadMemberCountByDept(String companyId, String date) throws Exception {
		Long count = (Long) super.loadUniqueResult("select count(m.memId) from Member m where m.companyId = '" + companyId + "'");
		return count.intValue();
	}
	
	@Override
	public Double loadMemberBalanceSumByDept(String companyId) throws Exception {
		return (Double) super.loadUniqueResult("select sum(m.balance) from Member m where m.companyId = '" + companyId + "'");
	}
	
	@Override
	public Integer loadMemberPointSumByDept(String companyId) throws Exception {
		Long sumPoint = (Long) super.loadUniqueResult("select sum(m.point) from Member m where m.companyId = '" + companyId + "'");
		return Utils.isEmpty(sumPoint) ? 0 : sumPoint.intValue();
	}
	
	@Override
	public Integer removeByIds(String ids) throws Exception {
		return super.bulkUpdate("delete from Member m where m.memId in (" + ids + ")");
	}
	
	@Override
	public List<Member> loadListByMMdd(String MMdd) throws Exception {
		return super.loadList("from Member m where m.birthday like '%" + MMdd + "'");
	}
	
	@Override
	public List<Member> loadByAppIdOpenId(String appId, String openId) throws Exception {
		return super.loadList("from Member m where m.appId = ? and m.wechatNo = ?", appId, openId);
	}
	
	public DetachedCriteria connectionCriteria(Member member){
		DetachedCriteria criteria = DetachedCriteria.forClass(Member.class);  
		if(member != null){
			if(!Utils.isEmpty(member.getMemId()))
				criteria.add(Restrictions.eq("id", member.getMemId()));
			if(!Utils.isEmpty(member.getCompanyId()))
				criteria.add(Restrictions.eq("companyId", member.getCompanyId()));
			if(!Utils.isEmpty(member.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", member.getDeptCode()));
			if(!Utils.isEmpty(member.getMobile()))
				criteria.add(Restrictions.like("mobile", member.getMobile(), MatchMode.ANYWHERE));
			if(!Utils.isEmpty(member.getName()))
				criteria.add(Restrictions.like("name", member.getName(), MatchMode.ANYWHERE));
			if(!Utils.isEmpty(member.getGrade()))
				criteria.add(Restrictions.eq("grade", member.getGrade()));
			if(!Utils.isEmpty(member.getSex()))
				criteria.add(Restrictions.eq("sex", member.getSex()));
			if(!Utils.isEmpty(member.getSales()))
				criteria.add(Restrictions.eq("sales", member.getSales()));
			if(!Utils.isEmpty(member.getSource()))
				criteria.add(Restrictions.eq("source", member.getSource()));
			if(!Utils.isEmpty(member.getStartBalance()))
				criteria.add(Restrictions.ge("balance", member.getStartBalance()));
			if(!Utils.isEmpty(member.getEndBalance()))
				criteria.add(Restrictions.le("balance", member.getEndBalance()));
			if(!Utils.isEmpty(member.getStartPoint()))
				criteria.add(Restrictions.ge("point", member.getStartPoint()));
			if(!Utils.isEmpty(member.getEndPoint()))
				criteria.add(Restrictions.le("point", member.getEndPoint()));
			if(!Utils.isEmpty(member.getStartBirth()))
				criteria.add(Restrictions.ge("birthday", member.getStartBirth()));
			if(!Utils.isEmpty(member.getEndBirth()))
				criteria.add(Restrictions.le("birthday", member.getEndBirth()));
			if(!Utils.isEmpty(member.getStartCreate()))
				criteria.add(Restrictions.ge("createTime", member.getStartCreate()));
			if(!Utils.isEmpty(member.getEndCreate()))
				criteria.add(Restrictions.le("createTime", member.getEndCreate()));
			if(!Utils.isEmpty(member.getCarBrand()))
				criteria.add(Restrictions.sqlRestriction(" this_.id in (select member_id from mem_cars where car_brand = " + member.getCarBrand() + ")"));
				
		}
		return criteria;
	}
	
	@Override
	public Integer loadDayGrowth(String companyId, String date, String deptCode) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("select count(m.id) from Member m where m.companyId = '" + companyId + "' ");
		if(!Utils.isEmpty(date))
			hql.append(" and m.createTime like '" + date + "%'");
		if(!Utils.isEmpty(deptCode))
			hql.append(" and m.deptCode = '" + deptCode + "'");
		Long count = (Long) super.loadUniqueResult(hql.toString());
		return count.intValue();
	}
	
}

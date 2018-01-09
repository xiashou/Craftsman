package com.tcode.business.member.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.member.dao.MemberVisitDao;
import com.tcode.business.member.model.MemberVisit;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("memberVisitDao")
public class MemberVisitDaoImpl extends BaseDao<MemberVisit, Serializable> implements MemberVisitDao {
	
	@Override
	public MemberVisit loadById(Integer id) throws Exception {
		return super.loadById(MemberVisit.class, id);
	}
	
	@Override
	public List<MemberVisit> loadVisitListByPage(MemberVisit visit, int start, int limit) throws Exception {
		List<MemberVisit> visitList = null;
		DetachedCriteria criteria = connectionCriteria(visit);
		criteria.addOrder(Order.desc("id"));
		visitList = (List<MemberVisit>) super.loadListForPage(criteria, start, limit);
		return visitList;
	}
	
	@Override
	public Integer loadListCount(MemberVisit visit) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(visit);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(MemberVisit visit){
		DetachedCriteria criteria = DetachedCriteria.forClass(MemberVisit.class);  
		if(visit != null){
			if(!Utils.isEmpty(visit.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", visit.getDeptCode()));
			if(!Utils.isEmpty(visit.getTypeId()))
				criteria.add(Restrictions.eq("typeId", visit.getTypeId()));
			if(!Utils.isEmpty(visit.getMemId()))
				criteria.add(Restrictions.eq("memId", visit.getMemId()));
			criteria.add(Restrictions.or(Restrictions.isNull("visitor"), Restrictions.isNotNull("result")));
		}
		return criteria;
	}

	@Override
	public List<MemberVisit> loadVisitList1(String startDate, String endDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select m.dept_code as deptCode, c.member_id as memId, 1 as typeId, c.id as carId, c.car_maintain as endDate from mem_cars c, mem_member m "
				+ "where c.member_id = m.id and c.car_maintain <> '' and c.car_maintain < '" + endDate + "' "
				+ "and c.car_maintain >= '" + startDate + "' and c.id not in (select car_id from mem_visit where [type_id] = 1 and car_id = c.id and visitor is null)");
		return super.loadListBySql(sql.toString(), MemberVisit.class);
	}
	
	@Override
	public List<MemberVisit> loadVisitList2(String startDate, String endDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select m.dept_code as deptCode, c.member_id as memId, 2 as typeId, c.id as carId, c.car_insurance as endDate from mem_cars c, mem_member m "
				+ "where c.member_id = m.id and c.car_insurance <> '' and c.car_insurance < '" + endDate + "' "
				+ "and c.car_insurance >= '" + startDate + "' and c.id not in (select car_id from mem_visit where [type_id] = 2 and car_id = c.id and visitor is null)");
		return super.loadListBySql(sql.toString(), MemberVisit.class);
	}
	
	@Override
	public List<MemberVisit> loadVisitList3(String startDate, String endDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select m.dept_code as deptCode, c.member_id as memId, 3 as typeId, c.id as carId, c.car_inspection as endDate from mem_cars c, mem_member m "
				+ "where c.member_id = m.id and c.car_inspection <> '' and c.car_inspection < '" + endDate + "' "
				+ "and c.car_inspection >= '" + startDate + "' and c.id not in (select car_id from mem_visit where [type_id] = 3 and car_id = c.id and visitor is null)");
		return super.loadListBySql(sql.toString(), MemberVisit.class);
	}
	
	@Override
	public List<MemberVisit> loadVisitList4(String lastDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct h.mem_id as memId, h.dept_code as deptCode, 4 as typeId, max(h.sale_date) as lastSale from order_head h "
				+ "where sale_date < '" + lastDate + "' and h.mem_id not in (select mem_id from mem_visit where [type_id] = 4 and mem_id = h.mem_id and visitor is null) "
						+ "group by h.mem_id,h.dept_code");
		return super.loadListBySql(sql.toString(), MemberVisit.class);
	}
	
	@Override
	public List<MemberVisit> loadVisitList5(String startDate, String endDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select m.id as memId, m.dept_code as deptCode, 5 as typeId, s.goods_id as goodsId, s.end_date as endDate from mem_member m, mem_stock s "
				+ "where m.id = s.mem_id and s.end_date <= '" + endDate + "' and s.end_date <> '' and s.end_date >= '" + startDate + "' and m.id not in "
						+ "(select mem_id from mem_visit where [type_id] = 5 and mem_id = m.id and goods_id = s.goods_id and visitor is null)");
		return super.loadListBySql(sql.toString(), MemberVisit.class);
	}
	
	@Override
	public List<MemberVisit> loadVisitList6() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select m.id as memId, m.dept_code as deptCode, 6 as typeId, m.balance from mem_member m "
				+ "where m.balance > 0 and m.balance <= 20 and m.id not in (select mem_id from mem_visit where [type_id] = 6 and visitor is null)");
		return super.loadListBySql(sql.toString(), MemberVisit.class);
	}
	
	@Override
	public Integer loadVisitCount(String deptCode, Integer typeId) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from MemberVisit v where v.deptCode = ? and v.typeId = ? and v.visitor is null");
		Long count = super.loadListCount(hql.toString(), deptCode, typeId);
		return count.intValue();
	}
	
	@Override
	public void removeOldVisit(String oldDate) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("delete from MemberVisit v where v.createTime < ?");
		super.executeHql(hql.toString(), oldDate);
	}
	
	@Override
	public void editVisit1(String now, String endDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update mem_visit set visitor = 'sys', visit_time = '" + now + "' from mem_visit, mem_cars "
				+ "where mem_visit.car_id = mem_cars.id and mem_visit.[type_id] = 1 and mem_cars.car_maintain >= '" + endDate + "'");
		super.executeSql(sql.toString());
	}
	
	@Override
	public void editVisit2(String now, String endDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update mem_visit set visitor = 'sys', visit_time = '" + now + "' from mem_visit, mem_cars "
				+ "where mem_visit.car_id = mem_cars.id and mem_visit.[type_id] = 2 and mem_cars.car_insurance >= '" + endDate + "'");
		super.executeSql(sql.toString());
	}
	
	@Override
	public void editVisit3(String now, String endDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update mem_visit set visitor = 'sys', visit_time = '" + now + "' from mem_visit, mem_cars "
				+ "where mem_visit.car_id = mem_cars.id and mem_visit.[type_id] = 3 and mem_cars.car_inspection >= '" + endDate + "'");
		super.executeSql(sql.toString());
	}
	
	@Override
	public void editVisit4(String now, String endDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update mem_visit set visitor = 'sys', visit_time = '" + now + "' from mem_visit, order_head "
				+ "where mem_visit.mem_id = order_head.mem_id and mem_visit.[type_id] = 4 and order_head.sale_date >= '" + endDate + "'");
		super.executeSql(sql.toString());
	}
	
	@Override
	public void editVisit5(String now) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update mem_visit set visitor = 'sys', visit_time = '" + now + "' from mem_visit, mem_stock "
				+ "where mem_visit.mem_id = mem_stock.mem_id and mem_visit.goods_id = mem_stock.goods_id and mem_visit.[type_id] = 5 and mem_stock.number <= 0");
		super.executeSql(sql.toString());
	}
	
	@Override
	public void editVisit6(String now) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update mem_visit set visitor = 'sys', visit_time = '" + now + "' from mem_visit, mem_member "
				+ "where mem_visit.mem_id = mem_member.id and mem_visit.[type_id] = 6 and mem_member.balance > 20");
		super.executeSql(sql.toString());
	}

}

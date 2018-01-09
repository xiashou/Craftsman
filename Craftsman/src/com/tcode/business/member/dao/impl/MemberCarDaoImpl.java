package com.tcode.business.member.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.member.dao.MemberCarDao;
import com.tcode.business.member.model.MemberCar;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("memberCarDao")
public class MemberCarDaoImpl extends BaseDao<MemberCar, Serializable> implements MemberCarDao {

	@Override
	public MemberCar loadById(Integer id) throws Exception {
		return super.loadById(MemberCar.class, id);
	}
	
	@Override
	public List<MemberCar> loadByMemberId(Integer memberId) throws Exception {
		return super.loadList("from MemberCar c where c.memberId = ? order by c.id desc", memberId);
	}
	
	@Override
	public MemberCar loadByCarNumber(String companyId, String carShort, String code, String number) throws Exception {
		List<MemberCar> list = super.loadList("select c from MemberCar c, Member m where c.memberId = m.memId and m.companyId = ? "
				+ "and c.carShort = ? and c.carCode = ? and c.carNumber = ?", companyId, carShort, code, number);
		return Utils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public List<MemberCar> loadListPage(MemberCar car, int start, int limit) throws Exception {
		List<MemberCar> list = null;
		DetachedCriteria criteria = connectionCriteria(car);
		criteria.addOrder(Order.desc("id"));
		list = (List<MemberCar>) super.loadListForPage(criteria, start, limit);
		return list;
	}

	@Override
	public Integer loadListCount(MemberCar car) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(car);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	@Override
	public Integer loadCarCountByDept(String deptCode) throws Exception {
		Long count = (Long) super.loadUniqueResult("select count(c.id) from MemberCar c, Member m where m.memId = c.memberId and m.companyId = '" + deptCode + "'");
		return count.intValue();
	}
	
	@Override
	public Integer removeByMemIds(String ids) throws Exception {
		return super.bulkUpdate("delete from MemberCar c where c.memberId in (" + ids + ")");
	}
	
	public DetachedCriteria connectionCriteria(MemberCar car){
		DetachedCriteria criteria = DetachedCriteria.forClass(MemberCar.class);  
		if(car != null){
			if(!Utils.isEmpty(car.getId()))
				criteria.add(Restrictions.eq("id", car.getId()));
		}
		return criteria;
	}

	@Override
	public List<MemberCar> loadListByApartNextTime(int apartTime, int type) throws Exception {
		//type 类型 7-保险 6-保养 9-年检
		String apartNextTime = Utils.dateApart(apartTime, "yyyy/MM/dd");
		if(type == 6)
			return super.loadList("select w from MemberCar w where "
					+ "convert(datetime,w.carMaintain,111)  between getdate() and convert(datetime,'" + apartNextTime + "',111) ");//保养时间
		else if(type == 9) 
			return super.loadList("select w from MemberCar w where "
					+ "convert(datetime,w.carInspection,111) between getdate() and convert(datetime,'" + apartNextTime + "',111)");//年检时间
		else if(type == 7)
			return super.loadList("select w from MemberCar w where "
					+ "convert(datetime,w.carInsurance,111) between getdate() and convert(datetime,'" + apartNextTime + "',111) )"); //保险时间
		else 
			return null;
	}
}

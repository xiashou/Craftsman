package com.tcode.business.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsHourDao;
import com.tcode.business.goods.dao.GoodsMaterialDao;
import com.tcode.business.goods.model.GoodsHour;
import com.tcode.business.goods.model.GoodsMaterial;
import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.member.model.MemberVisit;
import com.tcode.business.member.dao.MemberCarDao;
import com.tcode.business.member.dao.MemberDao;
import com.tcode.business.member.dao.MemberVisitDao;
import com.tcode.business.member.service.MemberVisitService;
import com.tcode.core.util.Utils;

@Component("memberVisitService")
public class MemberVisitServiceImpl implements MemberVisitService {
	
	private MemberVisitDao memberVisitDao;
	private MemberDao memberDao;
	private MemberCarDao memberCarDao;
	private GoodsHourDao goodsHourDao;
	private GoodsMaterialDao goodsMaterialDao;
	
	@Override
	public MemberVisit getById(Integer id) throws Exception {
		return memberVisitDao.loadById(id);
	}
	
	@Override
	public List<MemberVisit> getVisitListByPage(MemberVisit visit, int start, int limit) throws Exception {
		List<MemberVisit> visitList = memberVisitDao.loadVisitListByPage(visit, start, limit);
		for(MemberVisit memberVisit : visitList){
			if(!Utils.isEmpty(memberVisit.getMemId())){
				Member member = memberDao.loadById(memberVisit.getMemId());
				if(!Utils.isEmpty(member)){
					memberVisit.setName(member.getName());
					memberVisit.setMobile(member.getMobile());
				}
			}
			if(!Utils.isEmpty(memberVisit.getCarId())){
				MemberCar car = memberCarDao.loadById(memberVisit.getCarId());
				if(!Utils.isEmpty(car))
					memberVisit.setCarNumber(car.getCarShort() + car.getCarCode() + car.getCarNumber());
			}
			if(!Utils.isEmpty(memberVisit.getGoodsId())){
				GoodsHour hour = goodsHourDao.loadById(memberVisit.getGoodsId());
				if(!Utils.isEmpty(hour)) {
					memberVisit.setGoodsName(hour.getName());
				} else {
					GoodsMaterial mater = goodsMaterialDao.loadById(memberVisit.getGoodsId());
					if(!Utils.isEmpty(mater))
						memberVisit.setGoodsName(mater.getName());
				}
			}
		}
		return visitList;
	}

	@Override
	public Integer getVisitListCount(MemberVisit visit) throws Exception {
		return memberVisitDao.loadListCount(visit);
	}

	@Override
	public List<MemberVisit> getVisitList1(String startDate, String endDate) throws Exception {
		return memberVisitDao.loadVisitList1(startDate, endDate);
	}
	
	@Override
	public List<MemberVisit> getVisitList2(String startDate, String endDate) throws Exception {
		return memberVisitDao.loadVisitList2(startDate, endDate);
	}
	
	@Override
	public List<MemberVisit> getVisitList3(String startDate, String endDate) throws Exception {
		return memberVisitDao.loadVisitList3(startDate, endDate);
	}
	
	@Override
	public List<MemberVisit> getVisitList4( String lastDate) throws Exception {
		return memberVisitDao.loadVisitList4(lastDate);
	}
	
	@Override
	public List<MemberVisit> getVisitList5(String startDate, String endDate) throws Exception {
		return memberVisitDao.loadVisitList5(startDate, endDate);
	}
	
	@Override
	public List<MemberVisit> getVisitList6() throws Exception {
		return memberVisitDao.loadVisitList6();
	}
	
	@Override
	public Integer getVisitCount(String deptCode, Integer typeId) throws Exception{
		return memberVisitDao.loadVisitCount(deptCode, typeId);
	}
	
	@Override
	public void insert(MemberVisit visit) throws Exception {
		memberVisitDao.save(visit);
	}
	@Override
	public void update(MemberVisit visit) throws Exception {
		memberVisitDao.edit(visit);
	}
	@Override
	public void delete(MemberVisit visit) throws Exception {
		memberVisitDao.remove(visit);
	}
	
	@Override
	public void updateVisit1(String now, String endDate) throws Exception {
		memberVisitDao.editVisit1(now, endDate);
	}

	@Override
	public void updateVisit2(String now, String endDate) throws Exception {
		memberVisitDao.editVisit2(now, endDate);
	}

	@Override
	public void updateVisit3(String now, String endDate) throws Exception {
		memberVisitDao.editVisit3(now, endDate);
	}

	@Override
	public void updateVisit4(String now, String endDate) throws Exception {
		memberVisitDao.editVisit4(now, endDate);
	}

	@Override
	public void updateVisit5(String now) throws Exception {
		memberVisitDao.editVisit5(now);
	}

	@Override
	public void updateVisit6(String now) throws Exception {
		memberVisitDao.editVisit6(now);
	}

	@Override
	public void deleteOldVisit(String oldDate) throws Exception {
		memberVisitDao.removeOldVisit(oldDate);
	}
	
	
	

	public MemberVisitDao getMemberVisitDao() {
		return memberVisitDao;
	}
	@Resource
	public void setMemberVisitDao(MemberVisitDao memberVisitDao) {
		this.memberVisitDao = memberVisitDao;
	}
	public MemberDao getMemberDao() {
		return memberDao;
	}
	@Resource
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	public MemberCarDao getMemberCarDao() {
		return memberCarDao;
	}
	@Resource
	public void setMemberCarDao(MemberCarDao memberCarDao) {
		this.memberCarDao = memberCarDao;
	}
	public GoodsHourDao getGoodsHourDao() {
		return goodsHourDao;
	}
	@Resource
	public void setGoodsHourDao(GoodsHourDao goodsHourDao) {
		this.goodsHourDao = goodsHourDao;
	}
	public GoodsMaterialDao getGoodsMaterialDao() {
		return goodsMaterialDao;
	}
	@Resource
	public void setGoodsMaterialDao(GoodsMaterialDao goodsMaterialDao) {
		this.goodsMaterialDao = goodsMaterialDao;
	}

}

package com.tcode.business.finance.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.finance.dao.ReceivableDao;
import com.tcode.business.finance.model.Receivable;
import com.tcode.business.finance.service.ReceivableService;
import com.tcode.business.member.dao.MemberCarDao;
import com.tcode.business.member.dao.MemberDao;
import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.order.model.OrderHead;
import com.tcode.core.util.Utils;

@Component("receivableService")
public class ReceivableServiceImpl implements ReceivableService {
	
	private ReceivableDao receivableDao;
	private MemberDao memberDao;
	private MemberCarDao memberCarDao;

	@Override
	public Receivable getById(String deptCode, Integer memId, Integer carId) throws Exception {
		return receivableDao.loadById(deptCode, memId, carId);
	}

	@Override
	public List<Receivable> getListByDept(String deptCode) throws Exception {
		return receivableDao.loadByDept(deptCode);
	}
	
	@Override
	public List<Receivable> getListByDeptPage(Receivable receiv, Integer start, Integer limit) throws Exception {
		List<Receivable> list = receivableDao.loadByDeptPage(receiv, start, limit);
		for(Receivable receivable : list) {
			Member member = memberDao.loadById(receivable.getMemId());
			if(!Utils.isEmpty(member)) {
				receivable.setName(member.getName());
				receivable.setMobile(member.getMobile());
				receivable.setVipNo(member.getVipNo());
			}
			MemberCar car = memberCarDao.loadById(receivable.getCarId());
			if(!Utils.isEmpty(car)) {
				receivable.setCarNumber(car.getCarShort() + car.getCarCode() + car.getCarNumber());
			}
			if(Utils.isEmpty(receivable.getRepayment()))
				receivable.setRepayment(0.0);
			receivable.setResidual(receivable.getBillPrice() - receivable.getRepayment());
		}
		return list;
	}
	
	@Override
	public Integer getListByDeptCount(Receivable receivable) throws Exception {
		return receivableDao.loadByDeptCount(receivable);
	}

	@Override
	public void insert(Receivable receivable) throws Exception {
		receivableDao.save(receivable);
	}

	@Override
	public void update(Receivable receivable) throws Exception {
		receivableDao.edit(receivable);
	}

	@Override
	public void delete(Receivable receivable) throws Exception {
		receivableDao.remove(receivable);
	}
	
	@Override
	public List<Double> getSumByBoss(String companyId, String date, String deptCode) throws Exception {
		return receivableDao.loadSumByBoss(companyId, date, deptCode);
	}
	
	@Override
	public List<Receivable> getListByBossPage(String companyId, String deptCode, Integer start, Integer limit) throws Exception {
		List<Receivable> list = receivableDao.loadByBossPage(companyId, deptCode, start, limit);
		for(Receivable receivable : list) {
			Member member = memberDao.loadById(receivable.getMemId());
			if(!Utils.isEmpty(member)) {
				receivable.setName(member.getName());
				receivable.setMobile(member.getMobile());
				receivable.setVipNo(member.getVipNo());
			}
			MemberCar car = memberCarDao.loadById(receivable.getCarId());
			if(!Utils.isEmpty(car)) {
				receivable.setCarNumber(car.getCarShort() + car.getCarCode() + car.getCarNumber());
			}
			if(Utils.isEmpty(receivable.getRepayment()))
				receivable.setRepayment(0.0);
			receivable.setResidual(receivable.getBillPrice() - receivable.getRepayment());
		}
		return list;
	}

	@Override
	public Integer getCountByDept(OrderHead orderHead) throws Exception {
		return receivableDao.loadCountByDept(orderHead);
	}

	public ReceivableDao getReceivableDao() {
		return receivableDao;
	}
	@Resource
	public void setReceivableDao(ReceivableDao receivableDao) {
		this.receivableDao = receivableDao;
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
	
}

package com.tcode.business.report.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.member.dao.MemberDao;
import com.tcode.business.member.model.Member;
import com.tcode.business.report.dao.ReptRechargeDao;
import com.tcode.business.report.model.ReptRecharge;
import com.tcode.business.report.service.ReptRechargeService;
import com.tcode.core.util.Utils;

@Component("reptRechargeService")
public class ReptRechargeServiceImpl implements ReptRechargeService {
	
	private ReptRechargeDao reptRechargeDao;
	private MemberDao memberDao;

	@Override
	public ReptRecharge getReptRechargeById(Integer id) throws Exception {
		return reptRechargeDao.loadById(id);
	}

	@Override
	public List<ReptRecharge> getListByDept(String deptCode) throws Exception {
		return reptRechargeDao.loadByDept(deptCode);
	}

	@Override
	public List<ReptRecharge> getListByMemId(Integer memId) throws Exception {
		return reptRechargeDao.loadByMemId(memId);
	}
	
	@Override
	public List<ReptRecharge> getByOrder(String orderId, String goodsId) throws Exception {
		return reptRechargeDao.loadByOrder(orderId, goodsId);
	}

	@Override
	public List<ReptRecharge> getListPage(ReptRecharge recharge, int start, int limit) throws Exception {
		List<ReptRecharge> list = reptRechargeDao.loadListPage(recharge, start, limit);
		for(ReptRecharge report : list){
			Member member = memberDao.loadById(report.getMemId());
			if(!Utils.isEmpty(member)){
				report.setName(member.getName());
				report.setSex(member.getSex());
				report.setMobile(member.getMobile());
			}
		}
		return list;
	}

	@Override
	public Integer getListCount(ReptRecharge recharge) throws Exception {
		return reptRechargeDao.loadListCount(recharge);
	}

	@Override
	public void insert(ReptRecharge recharge) throws Exception {
		reptRechargeDao.save(recharge);
	}

	@Override
	public void update(ReptRecharge recharge) throws Exception {
		reptRechargeDao.edit(recharge);
	}

	@Override
	public void delete(ReptRecharge recharge) throws Exception {
		reptRechargeDao.remove(recharge);
	}

	public ReptRechargeDao getReptRechargeDao() {
		return reptRechargeDao;
	}
	@Resource
	public void setReptRechargeDao(ReptRechargeDao reptRechargeDao) {
		this.reptRechargeDao = reptRechargeDao;
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}
	@Resource
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

}

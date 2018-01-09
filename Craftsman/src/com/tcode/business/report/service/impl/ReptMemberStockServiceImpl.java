package com.tcode.business.report.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.member.dao.MemberDao;
import com.tcode.business.member.model.Member;
import com.tcode.business.report.dao.ReptMemberStockDao;
import com.tcode.business.report.model.ReptMemberStock;
import com.tcode.business.report.service.ReptMemberStockService;
import com.tcode.core.util.Utils;

@Component("reptMemberStockService")
public class ReptMemberStockServiceImpl implements ReptMemberStockService {
	
	private ReptMemberStockDao reptMemberStockDao;
	private MemberDao memberDao;

	@Override
	public ReptMemberStock getReptMemberStockById(Integer id) throws Exception {
		return reptMemberStockDao.loadById(id);
	}

	@Override
	public List<ReptMemberStock> getListByDept(String deptCode) throws Exception {
		return reptMemberStockDao.loadByDept(deptCode);
	}

	@Override
	public List<ReptMemberStock> getListByMemId(Integer memId) throws Exception {
		return reptMemberStockDao.loadByMemId(memId);
	}

	@Override
	public List<ReptMemberStock> getListPage(ReptMemberStock recharge, int start, int limit) throws Exception {
		List<ReptMemberStock> list = reptMemberStockDao.loadListPage(recharge, start, limit);
		for(ReptMemberStock report : list){
			Member member = memberDao.loadById(report.getMemId());
			if(!Utils.isEmpty(member)){
				report.setName(member.getName());
				report.setSex(member.getSex());
			}
		}
		return list;
	}

	@Override
	public Integer getListCount(ReptMemberStock memberStock) throws Exception {
		return reptMemberStockDao.loadListCount(memberStock);
	}

	@Override
	public void insert(ReptMemberStock memberStock) throws Exception {
		reptMemberStockDao.save(memberStock);
	}

	@Override
	public void update(ReptMemberStock memberStock) throws Exception {
		reptMemberStockDao.edit(memberStock);
	}

	@Override
	public void delete(ReptMemberStock memberStock) throws Exception {
		reptMemberStockDao.remove(memberStock);
	}

	

	public ReptMemberStockDao getReptMemberStockDao() {
		return reptMemberStockDao;
	}
	@Resource
	public void setReptMemberStockDao(ReptMemberStockDao reptMemberStockDao) {
		this.reptMemberStockDao = reptMemberStockDao;
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}
	@Resource
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

}

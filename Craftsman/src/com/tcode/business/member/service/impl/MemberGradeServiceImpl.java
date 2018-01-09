package com.tcode.business.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.member.dao.MemberGradeDao;
import com.tcode.business.member.model.MemberGrade;
import com.tcode.business.member.service.MemberGradeService;

@Component("memberGradeService")
public class MemberGradeServiceImpl implements MemberGradeService {
	
	private MemberGradeDao memberGradeDao;
	
	@Override
	public List<MemberGrade> getMemberGradeByDeptCode(String deptCode) throws Exception {
		return memberGradeDao.loadAll(deptCode);
	}
	@Override
	public MemberGrade getMemberGradeById(Integer id) throws Exception {
		return memberGradeDao.loadById(id);
	}
	@Override
	public MemberGrade getMemberGradeByGrade(String deptCode, Integer grade) throws Exception {
		return memberGradeDao.loadByGrade(deptCode, grade);
	}
	@Override
	public void insert(MemberGrade grade) throws Exception {
		memberGradeDao.save(grade);
	}
	@Override
	public void update(MemberGrade grade) throws Exception {
		memberGradeDao.edit(grade);
	}
	@Override
	public void delete(MemberGrade grade) throws Exception {
		memberGradeDao.remove(grade);
	}
	@Override
	public List<MemberGrade> getListPage(MemberGrade grade, int start, int limit) throws Exception {
		return memberGradeDao.loadListPage(grade, start, limit);
	}
	@Override
	public Integer getListCount(MemberGrade grade) throws Exception {
		return memberGradeDao.loadListCount(grade);
	}

	
	public MemberGradeDao getMemberGradeDao() {
		return memberGradeDao;
	}
	@Resource
	public void setMemberGradeDao(MemberGradeDao memberGradeDao) {
		this.memberGradeDao = memberGradeDao;
	}
}

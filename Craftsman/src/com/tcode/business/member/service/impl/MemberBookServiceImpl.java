package com.tcode.business.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.member.dao.MemberBookDao;
import com.tcode.business.member.dao.MemberBookTipsDao;
import com.tcode.business.member.model.MemberBook;
import com.tcode.business.member.model.MemberBookTips;
import com.tcode.business.member.service.MemberBookService;
import com.tcode.core.util.Utils;

@Component("memberBookService")
public class MemberBookServiceImpl implements MemberBookService {
	
	private MemberBookDao memberBookDao;
	private MemberBookTipsDao memberBookTipsDao;

	@Override
	public MemberBook getById(Integer id) throws Exception {
		return memberBookDao.loadById(id);
	}
	@Override
	public List<MemberBook> getListByPage(MemberBook book, int start, int limit) throws Exception {
		return memberBookDao.loadListByPage(book, start, limit);
	}
	@Override
	public Integer getListCount(MemberBook book) throws Exception {
		return memberBookDao.loadListCount(book);
	}
	@Override
	public Integer getBookCountByDept(String deptCode) throws Exception {
		return memberBookDao.loadBookCountByDept(deptCode);
	}
	@Override
	public void insert(MemberBook book) throws Exception {
		memberBookDao.save(book);
	}
	@Override
	public void update(MemberBook book) throws Exception {
		memberBookDao.edit(book);
	}
	@Override
	public void delete(MemberBook book) throws Exception {
		memberBookDao.remove(book);
	}
	@Override
	public MemberBookTips getTipsByDept(String deptCode) throws Exception {
		MemberBookTips tips = memberBookTipsDao.loadById(deptCode);
		if(Utils.isEmpty(tips))
			tips = memberBookTipsDao.loadById("XXXX");
		return tips;
	}
	@Override
	public void insertTips(MemberBookTips bookTips) throws Exception {
		memberBookTipsDao.save(bookTips);
	}
	@Override
	public void updateTips(MemberBookTips bookTips) throws Exception {
		memberBookTipsDao.edit(bookTips);
	}
	@Override
	public void deleteTips(MemberBookTips bookTips) throws Exception {
		memberBookTipsDao.remove(bookTips);
	}
	
	
	

	public MemberBookDao getMemberBookDao() {
		return memberBookDao;
	}
	@Resource
	public void setMemberBookDao(MemberBookDao memberBookDao) {
		this.memberBookDao = memberBookDao;
	}
	public MemberBookTipsDao getMemberBookTipsDao() {
		return memberBookTipsDao;
	}
	@Resource
	public void setMemberBookTipsDao(MemberBookTipsDao memberBookTipsDao) {
		this.memberBookTipsDao = memberBookTipsDao;
	}

}

package com.tcode.business.member.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.tcode.business.member.dao.MemberBookTipsDao;
import com.tcode.business.member.model.MemberBookTips;
import com.tcode.common.dao.BaseDao;

@Component("memberBookTipsDao")
public class MemberBookTipsDaoImpl extends BaseDao<MemberBookTips, Serializable> implements MemberBookTipsDao {

	@Override
	public MemberBookTips loadById(String deptCode) throws Exception {
		return super.loadById(MemberBookTips.class, deptCode);
	}

}

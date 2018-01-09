package com.tcode.business.member.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.member.dao.MemberGradePriceDao;
import com.tcode.business.member.model.MemberGradePrice;
import com.tcode.common.dao.BaseDao;

@Component("gradePriceDao")
public class MemberGradePriceDaoImpl extends BaseDao<MemberGradePrice, Serializable> implements MemberGradePriceDao {

	@Override
	public MemberGradePrice loadById(String goodsId, Integer gradeId, String deptCode) throws Exception {
		return (MemberGradePrice)super.loadEntity("from MemberGradePrice gp where gp.goodsId = ? and gp.gradeId = ? and gp.deptCode = ?", goodsId, gradeId, deptCode);
	}

	@Override
	public List<MemberGradePrice> loadByDeptCode(String deptCode) throws Exception {
		return super.loadList("from MemberGradePrice gp where gp.deptCode = ?", deptCode);
	}
	
	@Override
	public void removeByDeptGrade(String deptCode, Integer grade) throws Exception {
		super.bulkUpdate("delete from MemberGradePrice gp where gp.deptCode = ? and gp.gradeId = ?", deptCode, grade);
	}


}

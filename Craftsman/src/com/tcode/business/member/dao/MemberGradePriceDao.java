package com.tcode.business.member.dao;

import java.util.List;

import com.tcode.business.member.model.MemberGradePrice;

public interface MemberGradePriceDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return MemberGradePrice
	 * @throws Exception
	 */
	public MemberGradePrice loadById(String goodsId, Integer gradeId, String deptCode) throws Exception;
	
	/**
	 * 根据手机号码查找
	 * @param id
	 * @return MemberGradePrice
	 * @throws Exception
	 */
	public List<MemberGradePrice> loadByDeptCode(String deptCode) throws Exception;
	
	/**
	 * 根据门店和等级删除等级价格信息
	 * @param deptCode
	 * @param grade
	 * @throws Exception
	 */
	public void removeByDeptGrade(String deptCode, Integer grade) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MemberGradePrice gradePrice) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MemberGradePrice gradePrice) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MemberGradePrice gradePrice) throws Exception;
}

package com.tcode.business.member.service;

import java.util.List;

import com.tcode.business.member.model.MemberGradePrice;

public interface MemberGradePriceService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return MemberGradePrice
	 * @throws Exception
	 */
	public MemberGradePrice getGradePriceById(String goodsId, Integer gradeId, String deptCode) throws Exception;
	
	/**
	 * 根据门店代码查询
	 * @param deptCode
	 * @return MemberGradePrice
	 * @throws Exception
	 */
	public List<MemberGradePrice> getListByDeptCode(String companyId, String deptCode, Integer gradeId) throws Exception ;
	
	/**
	 * 根据门店和等级删除等级价格数据
	 * @param deptCode
	 * @param grade
	 * @throws Exception
	 */
	public void deleteByDeptGrade(String deptCode, Integer grade) throws Exception;
	
	/**
	 * 添加
	 * @param MemberGradePrice
	 * @throws Exception
	 */
	public void insert(MemberGradePrice gradePrice) throws Exception;
	
	/**
	 * 修改
	 * @param MemberGradePrice
	 * @throws Exception
	 */
	public void update(MemberGradePrice gradePrice) throws Exception;
	
	/**
	 * 删除
	 * @param MemberGradePrice
	 * @throws Exception
	 */
	public void delete(MemberGradePrice gradePrice) throws Exception;
	
}

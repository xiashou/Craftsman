package com.tcode.business.member.service;

import java.util.List;

import com.tcode.business.member.model.MemberGrade;

public interface MemberGradeService {

	/**
	 * 查找所有
	 * @return List<MemberGrade>
	 * @throws Exception
	 */
	public List<MemberGrade> getMemberGradeByDeptCode(String deptCode) throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return MemberGrade
	 * @throws Exception
	 */
	public MemberGrade getMemberGradeById(Integer id) throws Exception;
	
	/**
	 * 根据门店与等级查找
	 * @param deptCode, grade
	 * @return MemberGrade
	 * @throws Exception
	 */
	public MemberGrade getMemberGradeByGrade(String deptCode, Integer grade) throws Exception;
	
	/**
	 * 添加
	 * @param MemberGrade
	 * @throws Exception
	 */
	public void insert(MemberGrade grade) throws Exception;
	
	/**
	 * 修改
	 * @param MemberGrade
	 * @throws Exception
	 */
	public void update(MemberGrade grade) throws Exception;
	
	/**
	 * 删除
	 * @param MemberGrade
	 * @throws Exception
	 */
	public void delete(MemberGrade grade) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param button, start, limit
	 * @return List<MemberCar>
	 * @throws Exception
	 */
	public List<MemberGrade> getListPage(MemberGrade grade, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param button
	 * @return List<MemberGrade>
	 * @throws Exception
	 */
	public Integer getListCount(MemberGrade grade) throws Exception;
}

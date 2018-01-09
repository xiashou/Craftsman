package com.tcode.business.member.dao;

import java.util.List;

import com.tcode.business.member.model.MemberGrade;

public interface MemberGradeDao {

	/**
	 * 按部门查询所有
	 * @return
	 * @throws Exception
	 */
	public List<MemberGrade> loadAll(String deptCode) throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return MemberGrade
	 * @throws Exception
	 */
	public MemberGrade loadById(Integer id) throws Exception;
	
	/**
	 * 根据门店与等级查找
	 * @param deptCode, grade
	 * @return MemberGrade
	 * @throws Exception
	 */
	public MemberGrade loadByGrade(String deptCode, Integer grade) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MemberGrade grade) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MemberGrade grade) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MemberGrade grade) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param param, start,limit
	 * @return List<MemberGrade>
	 * @throws Exception
	 */
	public List<MemberGrade> loadListPage(MemberGrade grade, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param param
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(MemberGrade grade) throws Exception;
}

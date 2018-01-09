package com.tcode.business.member.dao;

import java.util.List;

import com.tcode.business.member.model.MemberBook;

public interface MemberBookDao {

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MemberBook loadById(Integer id) throws Exception;
	
	/**
	 * 分页查找预约列表
	 * @return
	 * @throws Exception
	 */
	public List<MemberBook> loadListByPage(MemberBook book, int start, int limit) throws Exception;
	
	/**
	 * 查总数
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCount(MemberBook book) throws Exception;
	
	/**
	 * 查预约总数
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public Integer loadBookCountByDept(String deptCode) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MemberBook book) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MemberBook book) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MemberBook book) throws Exception;
}

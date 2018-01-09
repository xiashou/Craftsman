package com.tcode.business.member.service;

import java.util.List;

import com.tcode.business.member.model.MemberBook;
import com.tcode.business.member.model.MemberBookTips;

public interface MemberBookService {

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MemberBook getById(Integer id) throws Exception;
	
	/**
	 * 分页查找预约列表
	 * @return
	 * @throws Exception
	 */
	public List<MemberBook> getListByPage(MemberBook book, int start, int limit) throws Exception;
	
	/**
	 * 查总数
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public Integer getListCount(MemberBook book) throws Exception;
	
	/**
	 * 查预约总数
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public Integer getBookCountByDept(String deptCode) throws Exception;
	
	/**
	 * 添加
	 * @param MemberBook
	 * @throws Exception
	 */
	public void insert(MemberBook book) throws Exception;
	
	/**
	 * 修改
	 * @param MemberBook
	 * @throws Exception
	 */
	public void update(MemberBook book) throws Exception;
	
	/**
	 * 删除
	 * @param MemberBook
	 * @throws Exception
	 */
	public void delete(MemberBook book) throws Exception;
	
	/**
	 * 根据部门查询
	 */
	public MemberBookTips getTipsByDept(String deptCode) throws Exception;
	/**
	 * 添加
	 * @param MemberBookTips
	 * @throws Exception
	 */
	public void insertTips(MemberBookTips bookTips) throws Exception;
	
	/**
	 * 修改
	 * @param MemberBookTips
	 * @throws Exception
	 */
	public void updateTips(MemberBookTips bookTips) throws Exception;
	
	/**
	 * 删除
	 * @param MemberBookTips
	 * @throws Exception
	 */
	public void deleteTips(MemberBookTips bookTips) throws Exception;


}

package com.tcode.business.member.dao;

import com.tcode.business.member.model.MemberBookTips;

public interface MemberBookTipsDao {

	/**
	 * 根据ID查询
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public MemberBookTips loadById(String deptCode) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MemberBookTips bookTips) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MemberBookTips bookTips) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MemberBookTips bookTips) throws Exception;
}

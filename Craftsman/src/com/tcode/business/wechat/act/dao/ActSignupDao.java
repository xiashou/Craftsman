package com.tcode.business.wechat.act.dao;

import java.util.List;

import com.tcode.business.wechat.act.model.ActSignup;

public interface ActSignupDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return ActSignup
	 * @throws Exception
	 */
	public ActSignup loadById(Integer id) throws Exception;
	
	/**
	 * 根据id查找
	 * @param appId
	 * @return List<ActSignup>
	 * @throws Exception
	 */
	public List<ActSignup> loadByAppId(String appId) throws Exception;
	
	/**
	 * 根据openId查询
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public List<ActSignup> loadByOpenId(String openId) throws Exception;
	
	/**
	 * 修改报名人数
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public void editSignNumber(Integer actId, Integer number) throws Exception;
	
	/**
	 * 修改阅读人数
	 * @param actId
	 * @param number
	 * @throws Exception
	 */
	public void editReadNumber(Integer actId, Integer number) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(ActSignup signup) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(ActSignup signup) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(ActSignup signup) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param ActSignup, start, limit
	 * @return List<ActSignup>
	 * @throws Exception
	 */
	public List<ActSignup> loadListPage(ActSignup signup, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param ActSignup
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(ActSignup signup) throws Exception;
}

package com.tcode.business.wechat.act.service;

import java.util.List;

import com.tcode.business.wechat.act.model.ActSignup;

public interface ActSignupService {

	
	/**
	 * 根据ID查找
	 * @param id
	 * @return ActSignup
	 * @throws Exception
	 */
	public ActSignup getById(Integer id) throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return List<ActSignup>
	 * @throws Exception
	 */
	public List<ActSignup> getListByAppId(String appId) throws Exception;
	
	/**
	 * 根据openId查询
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public List<ActSignup> getListByOpenId(String openId) throws Exception;
	
	/**
	 * 修改报名人数
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public void updateSignNumber(Integer actId, Integer number) throws Exception;
	
	/**
	 * 修改阅读人数
	 * @param actId
	 * @param number
	 * @throws Exception
	 */
	public void updateReadNumber(Integer actId, Integer number) throws Exception;
	
	/**
	 * 添加
	 * @param ActSignup
	 * @throws Exception
	 */
	public void insert(ActSignup signup) throws Exception;
	
	/**
	 * 修改
	 * @param ActSignup
	 * @throws Exception
	 */
	public void update(ActSignup signup) throws Exception;
	
	/**
	 * 删除
	 * @param ActSignup
	 * @throws Exception
	 */
	public void delete(ActSignup signup) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param signup, start, limit
	 * @return List<ActSignup>
	 * @throws Exception
	 */
	public List<ActSignup> getListPage(ActSignup signup, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param signup
	 * @return List<ActSignup>
	 * @throws Exception
	 */
	public Integer getListCount(ActSignup signup) throws Exception;
	
}

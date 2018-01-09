package com.tcode.business.msg.service;

import java.util.List;

import com.tcode.business.msg.model.MsgCharging;

public interface MsgChargingService {
	
	/**
	 * 添加
	 * @param msgCharging
	 * @throws Exception
	 */
	public void insert(MsgCharging msgCharging) throws Exception;
	
	/**
	 * 修改
	 * @param msgCharging
	 * @throws Exception
	 */
	public void update(MsgCharging msgCharging) throws Exception;
	
	/**
	 * 删除
	 * @param msgCharging
	 * @throws Exception
	 */
	public void delete(MsgCharging msgCharging) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<MsgCharging> getAll() throws Exception;
	
	/**
	 * 根据店柜编码获取短信计数计费信息
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<MsgCharging> getByDeptCode(String deptCode) throws Exception;

}

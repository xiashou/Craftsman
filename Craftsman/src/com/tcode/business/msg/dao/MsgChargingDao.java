package com.tcode.business.msg.dao;

import java.util.List;

import com.tcode.business.msg.model.MsgCharging;

public interface MsgChargingDao {
	
	/**
	 * 添加
	 * @param msgCharging
	 * @throws Exception
	 */
	public void save(MsgCharging msgCharging) throws Exception;
	
	/**
	 * 修改
	 * @param msgCharging
	 * @throws Exception
	 */
	public void edit(MsgCharging msgCharging) throws Exception;
	
	/**
	 * 删除
	 * @param msgCharging
	 * @throws Exception
	 */
	public void remove(MsgCharging msgCharging) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<MsgCharging> loadAll() throws Exception;
	
	/**
	 * 根据店柜编码获取短信计数计费信息
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<MsgCharging> loadByDeptCode(String deptCode) throws Exception;

}

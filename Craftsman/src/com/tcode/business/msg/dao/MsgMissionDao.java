package com.tcode.business.msg.dao;

import java.util.List;

import com.tcode.business.msg.model.MsgMission;

public interface MsgMissionDao {
	
	/**
	 * 添加
	 * @param msgMission
	 * @throws Exception
	 */
	public void save(MsgMission msgMission) throws Exception;
	
	/**
	 * 修改
	 * @param msgMission
	 * @throws Exception
	 */
	public void edit(MsgMission msgMission) throws Exception;
	
	/**
	 * 删除
	 * @param msgMission
	 * @throws Exception
	 */
	public void remove(MsgMission msgMission) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<MsgMission> loadAll() throws Exception;

}

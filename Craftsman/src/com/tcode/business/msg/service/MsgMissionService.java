package com.tcode.business.msg.service;

import java.util.List;

import com.tcode.business.msg.model.MsgMission;

public interface MsgMissionService {

	/**
	 * 添加
	 * @param msgMission
	 * @throws Exception
	 */
	public void insert(MsgMission msgMission) throws Exception;
	
	/**
	 * 修改
	 * @param msgMission
	 * @throws Exception
	 */
	public void update(MsgMission msgMission) throws Exception;
	
	/**
	 * 删除
	 * @param msgMission
	 * @throws Exception
	 */
	public void delete(MsgMission msgMission) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<MsgMission> getAll() throws Exception;
	
}

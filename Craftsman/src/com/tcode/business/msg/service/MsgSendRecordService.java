package com.tcode.business.msg.service;

import java.util.List;

import com.tcode.business.msg.model.MsgSendRecord;
import com.tcode.business.msg.vo.MsgSendRecordVo;

public interface MsgSendRecordService {
	
	/**
	 * 添加
	 * @param msgSendRecord
	 * @throws Exception
	 */
	public void insert(MsgSendRecord msgSendRecord) throws Exception;
	
	/**
	 * 修改
	 * @param msgSendRecord
	 * @throws Exception
	 */
	public void update(MsgSendRecord msgSendRecord) throws Exception;
	
	/**
	 * 根据任务ID查询短信记录
	 * @param missionID 任务ID
	 * @return
	 * @throws Exception
	 */
	public List<MsgSendRecord> getByMissionID(int missionID) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param msgSendRecordVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<MsgSendRecord> getListPage(MsgSendRecordVo msgSendRecordVo, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param msgSendRecordVo
	 * @return
	 * @throws Exception
	 */
	public Integer getListCount(MsgSendRecordVo msgSendRecordVo) throws Exception;

}

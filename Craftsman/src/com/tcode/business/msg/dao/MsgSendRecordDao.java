package com.tcode.business.msg.dao;

import java.util.List;

import com.tcode.business.msg.model.MsgSendRecord;
import com.tcode.business.msg.vo.MsgSendRecordVo;

public interface MsgSendRecordDao {
	
	/**
	 * 添加
	 * @param msgSendRecord
	 * @throws Exception
	 */
	public void save(MsgSendRecord msgSendRecord) throws Exception;
	
	/**
	 * 修改
	 * @param msgSendRecord
	 * @throws Exception
	 */
	public void edit(MsgSendRecord msgSendRecord) throws Exception;
	
	/**
	 * 根据任务ID查询短信记录
	 * @param missionID 任务ID
	 * @return
	 * @throws Exception
	 */
	public List<MsgSendRecord> loadByMissionID(int missionID) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param msgSendRecordVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<MsgSendRecord> loadListPage(MsgSendRecordVo msgSendRecordVo, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param msgSendRecordVo
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCount(MsgSendRecordVo msgSendRecordVo) throws Exception;
	
}

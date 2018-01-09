package com.tcode.business.wechat.sys.service;

import java.util.List;

import com.tcode.business.wechat.sys.model.WechatSendRecord;
import com.tcode.business.wechat.sys.vo.WechatSendRecordVo;

public interface WechatSendRecordService {
	
	/**
	 * 添加
	 * @param wechatSendRecord
	 * @throws Exception
	 */
	public void insert(WechatSendRecord wechatSendRecord) throws Exception;
	
	/**
	 * 修改
	 * @param wechatSendRecord
	 * @throws Exception
	 */
	public void update(WechatSendRecord wechatSendRecord) throws Exception;
	
	/**
	 * 根据微信模板编码、openId、appId获取模板消息发送记录
	 * @param templateCode
	 * @param openId
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<WechatSendRecord> getByTemplateCodeAndOpenIdForApp(String templateCode, String openId, String appId, String deptCode) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param wechatSendRecordVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatSendRecord> getListPage(WechatSendRecordVo wechatSendRecordVo, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param wechatSendRecordVo
	 * @return
	 * @throws Exception
	 */
	public Integer getListCount(WechatSendRecordVo wechatSendRecordVo) throws Exception;


}

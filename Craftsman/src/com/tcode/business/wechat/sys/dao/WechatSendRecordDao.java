package com.tcode.business.wechat.sys.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.model.WechatSendRecord;
import com.tcode.business.wechat.sys.vo.WechatSendRecordVo;

public interface WechatSendRecordDao {
	
	/**
	 * 添加
	 * @param wechatSendRecord
	 * @throws Exception
	 */
	public void save(WechatSendRecord wechatSendRecord) throws Exception;
	
	/**
	 * 修改
	 * @param wechatSendRecord
	 * @throws Exception
	 */
	public void edit(WechatSendRecord wechatSendRecord) throws Exception;
	
	/**
	 * 根据微信模板编码、openId、appId获取模板消息发送记录
	 * @param templateCode
	 * @param openId
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<WechatSendRecord> loadByTemplateCodeAndOpenIdForApp(String templateCode, String openId, String appId, String deptCode) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param wechatSendRecordVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatSendRecord> loadListPage(WechatSendRecordVo wechatSendRecordVo, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param wechatSendRecordVo
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCount(WechatSendRecordVo wechatSendRecordVo) throws Exception;

}

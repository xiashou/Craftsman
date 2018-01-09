package com.tcode.business.wechat.sos.service;

import java.util.List;

import com.tcode.business.wechat.sos.model.WechatSOSLocation;
import com.tcode.business.wechat.sos.vo.WechatSOSLocationVo;

public interface WechatSOSLocationService {
	
	/**
	 * 添加
	 * @param wechatSOSLocation
	 * @throws Exception
	 */
	public void insert(WechatSOSLocation wechatSOSLocation) throws Exception;
	
	/**
	 * 修改
	 * @param wechatSOSLocation
	 * @throws Exception
	 */
	public void update(WechatSOSLocation wechatSOSLocation) throws Exception;
	
	/**
	 * 根据openid appid deptcode获取位置信息
	 * @param openId
	 * @param appId
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<WechatSOSLocation> getByTemplateCodeAndOpenIdForApp(String openId, String appId, String deptCode) throws Exception;

	/**
	 * 根据条件分页查找
	 * @param wechatSOSLocationVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatSOSLocation> getListPage(WechatSOSLocationVo wechatSOSLocationVo, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param wechatSOSLocationVo
	 * @return
	 * @throws Exception
	 */
	public Integer getListCount(WechatSOSLocationVo wechatSOSLocationVo) throws Exception;

}

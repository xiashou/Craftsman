package com.tcode.business.wechat.sys.service;

import java.util.List;

import com.tcode.business.wechat.sys.model.WechatMsgTemplate;
import com.tcode.business.wechat.sys.vo.WechatMsgTemplateVo;

public interface WechatMsgTemplateService {

	/**
	 * 添加
	 * 
	 * @param wechatMsgTemplate
	 * @throws Exception
	 */
	public void insert(WechatMsgTemplate wechatMsgTemplate) throws Exception;

	/**
	 * 修改
	 * 
	 * @param wechatMsgTemplate
	 * @throws Exception
	 */
	public void update(WechatMsgTemplate wechatMsgTemplate) throws Exception;

	/**
	 * 删除
	 * 
	 * @param wechatMsgTemplate
	 * @throws Exception
	 */
	public void delete(WechatMsgTemplate wechatMsgTemplate) throws Exception;

	/**
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplate> getAll() throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param wechatMsgTemplateVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplate> getListPage(WechatMsgTemplateVo wechatMsgTemplateVo, int start, int limit) throws Exception;
	
	/**
	 * 
	 * 根据条件查找条目数
	 * @param wechatMsgTemplateVo
	 * @return
	 * @throws Exception
	 */
	public Integer getListCount(WechatMsgTemplateVo wechatMsgTemplateVo) throws Exception;
	
	/**
	 * 根据门店编码和模板编码得到模板信息
	 * @param depNo
	 * @param templateNo
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplate> getBydepNoAndTemplateNo(String depNo, String templateNo) throws Exception;
	
	/**
	 * 根据公司ID和模板编码得到模板信息
	 * @param companyId
	 * @param templateNo
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplate> getByCompanyIdAndTemplateNo(String companyId, String templateNo) throws Exception;
	
	/**
	 * 根据门店编码得到模板信息
	 * @param depNo
	 * @param templateNo
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplate> getBydepNo(String depNo) throws Exception;

}

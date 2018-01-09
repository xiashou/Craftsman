package com.tcode.business.wechat.sys.service;

import java.util.List;

import com.tcode.business.wechat.sys.model.WechatMsgTemplateType;
import com.tcode.business.wechat.sys.vo.WechatMsgTemplateTypeVo;


public interface WechatMsgTemplateTypeService {

	/**
	 * 添加
	 * 
	 * @param wechatMsgTemplateType
	 * @throws Exception
	 */
	public void insert(WechatMsgTemplateType wechatMsgTemplateType) throws Exception;

	/**
	 * 修改
	 * 
	 * @param wechatMsgTemplateType
	 * @throws Exception
	 */
	public void update(WechatMsgTemplateType wechatMsgTemplateType) throws Exception;

	/**
	 * 删除
	 * 
	 * @param wechatMsgTemplateType
	 * @throws Exception
	 */
	public void delete(WechatMsgTemplateType wechatMsgTemplateType) throws Exception;

	/**
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplateType> getAll() throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param wechatMsgTemplateTypeVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplateType> getListPage(WechatMsgTemplateTypeVo wechatMsgTemplateTypeVo, int start, int limit) throws Exception;
	
	/**
	 * 
	 * 根据条件查找条目数
	 * @param wechatMsgTemplateTypeVo
	 * @return
	 * @throws Exception
	 */
	public Integer getListCount(WechatMsgTemplateTypeVo wechatMsgTemplateTypeVo) throws Exception;
	
	/**
	 * 根据模板编码得到模板信息
	 * @param templateNo
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplateType> getByTemplateNo(String templateNo) throws Exception;
}

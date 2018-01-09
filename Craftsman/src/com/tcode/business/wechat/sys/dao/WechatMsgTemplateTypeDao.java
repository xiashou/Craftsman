package com.tcode.business.wechat.sys.dao;

import java.util.List;

import com.tcode.business.wechat.sys.model.WechatMsgTemplateType;
import com.tcode.business.wechat.sys.vo.WechatMsgTemplateTypeVo;


public interface WechatMsgTemplateTypeDao {
	
	/**
	 * 添加
	 * @param wechatMsgTemplateType
	 * @throws Exception
	 */
	public void save(WechatMsgTemplateType wechatMsgTemplateType) throws Exception;
	
	/**
	 * 修改
	 * @param wechatMsgTemplateType
	 * @throws Exception
	 */
	public void edit(WechatMsgTemplateType wechatMsgTemplateType) throws Exception;
	
	/**
	 * 删除
	 * @param wechatMsgTemplateType
	 * @throws Exception
	 */
	public void remove(WechatMsgTemplateType wechatMsgTemplateType) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplateType> loadAll() throws Exception;
	

	/**
	 * 根据条件分页查找
	 * @param wechatMsgTemplateTypeVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplateType> loadListPage(WechatMsgTemplateTypeVo wechatMsgTemplateTypeVo, int start, int limit) throws Exception;
	
	/**
	 * 
	 * 根据条件查找条目数
	 * @param wechatMsgTemplateTypeVo
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCount(WechatMsgTemplateTypeVo wechatMsgTemplateTypeVo) throws Exception;
	
	/**
	 * 根据模板编码得到模板信息
	 * @param templateNo
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplateType> loadByTemplateNo(String templateNo) throws Exception;

}

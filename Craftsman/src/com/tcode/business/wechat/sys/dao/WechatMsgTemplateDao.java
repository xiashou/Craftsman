package com.tcode.business.wechat.sys.dao;

import java.util.List;

import com.tcode.business.wechat.sys.model.WechatMsgTemplate;
import com.tcode.business.wechat.sys.vo.WechatMsgTemplateVo;

public interface WechatMsgTemplateDao {
	
	/**
	 * 添加
	 * @param wechatMsgTemplate
	 * @throws Exception
	 */
	public void save(WechatMsgTemplate wechatMsgTemplate) throws Exception;
	
	/**
	 * 修改
	 * @param wechatMsgTemplate
	 * @throws Exception
	 */
	public void edit(WechatMsgTemplate wechatMsgTemplate) throws Exception;
	
	/**
	 * 删除
	 * @param wechatMsgTemplate
	 * @throws Exception
	 */
	public void remove(WechatMsgTemplate wechatMsgTemplate) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplate> loadAll() throws Exception;
	

	/**
	 * 根据条件分页查找
	 * @param wechatMsgTemplateVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplate> loadListPage(WechatMsgTemplateVo wechatMsgTemplateVo, int start, int limit) throws Exception;
	
	/**
	 * 
	 * 根据条件查找条目数
	 * @param wechatMsgTemplateVo
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCount(WechatMsgTemplateVo wechatMsgTemplateVo) throws Exception;
	
	/**
	 * 根据门店编码和模板编码得到模板信息
	 * @param deptCode
	 * @param templateNo
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplate> loadBydepNoAndTemplateNo(String deptCode, String templateNo) throws Exception;
	
	/**
	 * 根据门店编码和模板编码得到模板信息
	 * @param companyId
	 * @param templateNo
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplate> loadByCompanyIdAndTemplateNo(String companyId, String templateNo) throws Exception;
	
	/**
	 * 根据门店编码
	 * @param deptCode
	 * @param templateNo
	 * @return
	 * @throws Exception
	 */
	public List<WechatMsgTemplate> loadBydepNo(String deptCode) throws Exception;

}

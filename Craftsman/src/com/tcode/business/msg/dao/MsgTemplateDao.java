package com.tcode.business.msg.dao;

import java.util.List;

import com.tcode.business.msg.model.MsgTemplate;

public interface MsgTemplateDao {
	
	/**
	 * 添加
	 * @param msgTemplate
	 * @throws Exception
	 */
	public void save(MsgTemplate msgTemplate) throws Exception;
	
	/**
	 * 修改
	 * @param msgTemplate
	 * @throws Exception
	 */
	public void edit(MsgTemplate msgTemplate) throws Exception;
	
	/**
	 * 根据模板类型ID获取模板信息
	 * @param deptCode 门店代码
	 * @param templateTypeID 模板类型ID
	 * @return
	 * @throws Exception
	 */
	public List<MsgTemplate> loadByTemplateTypeNo(String deptCode, int templateTypeNo) throws Exception;

}

package com.tcode.business.msg.service;

import java.util.List;

import com.tcode.business.msg.model.MsgTemplate;

public interface MsgTemplateService {
	
	/**
	 * 添加
	 * @param msgTemplate
	 * @throws Exception
	 */
	public void insert(MsgTemplate msgTemplate) throws Exception;
	
	/**
	 * 修改
	 * @param msgTemplate
	 * @throws Exception
	 */
	public void update(MsgTemplate msgTemplate) throws Exception;
	
	/**
	 * 根据模板类型ID获取模板信息
	 * @param deptCode 门店代码
	 * @param templateTypeNo 模板类型编码
	 * @return
	 * @throws Exception
	 */
	public List<MsgTemplate> getByTemplateTypeNo(String deptCode, int templateTypeNo) throws Exception;

}

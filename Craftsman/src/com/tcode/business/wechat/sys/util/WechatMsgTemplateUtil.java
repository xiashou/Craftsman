package com.tcode.business.wechat.sys.util;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.model.WechatMsgTemplate;
import com.tcode.business.wechat.sys.model.WechatMsgTemplateType;
import com.tcode.business.wechat.sys.service.WechatMsgTemplateService;
import com.tcode.business.wechat.sys.service.WechatMsgTemplateTypeService;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysDept;

@Component
public class WechatMsgTemplateUtil {
	
	private static WechatMsgTemplateService wechatMsgTemplateService;
	private static WechatMsgTemplateTypeService wechatMsgTemplateTypeService;
	
	/**
	 * 根据门店编码和模板编码获取模板ID
	 * @param deptCode
	 * @param templateNo
	 * @return
	 * @throws Exception
	 */
	public static String getTemplateIdByDeptCodeAdnTemplateNo(String deptCode, String templateNo) throws Exception {
		String templateId = null;
		List<WechatMsgTemplate> wechatMsgTemplateList = wechatMsgTemplateService.getBydepNoAndTemplateNo(deptCode, templateNo);
		
		if(wechatMsgTemplateList.size() == 1) {
			WechatMsgTemplate wechatMsgTemplate = wechatMsgTemplateList.get(0);
			templateId = wechatMsgTemplate.getTemplateId();
		}
		
		return templateId;
	}
	
	/**
	 * 根据公司ID和模板编码获取模板ID
	 * @param companyId
	 * @param templateNo
	 * @return
	 * @throws Exception
	 */
	public static String getTemplateIdByCompanyIdAdnTemplateNo(String companyId, String templateNo) throws Exception {
		String templateId = null;
		List<WechatMsgTemplate> wechatMsgTemplateList = wechatMsgTemplateService.getByCompanyIdAndTemplateNo(companyId, templateNo);
		
		for(WechatMsgTemplate WechatMsgTemplate : wechatMsgTemplateList) {
			WechatMsgTemplate wechatMsgTemplate = wechatMsgTemplateList.get(0);
			templateId = wechatMsgTemplate.getTemplateId();
			if(!Utils.isEmpty(templateId)) return templateId;
		}
		
		return templateId;
	}

	/**
	 * 根据微信模板消息类型初始化对应店铺微信模板信息
	 * @throws Exception
	 */
	public static void initWechatMsgTemplate(SysDept sysDept) throws Exception {
		String deptCode = sysDept.getDeptCode();
		String deptName = sysDept.getDeptName();
		String companyId = sysDept.getCompanyId();
		String companyName = "";
		
		List<WechatMsgTemplate> wechatMsgTemplateList = wechatMsgTemplateService.getBydepNo(deptCode);
		List<WechatMsgTemplateType> wechatMsgTemplateTypeList = wechatMsgTemplateTypeService.getAll();
		
		for(WechatMsgTemplateType wechatMsgTemplateType : wechatMsgTemplateTypeList) {
			String templateNo = wechatMsgTemplateType.getTemplateNo();
			boolean flag = false;
			for(WechatMsgTemplate wechatMsgTemplate : wechatMsgTemplateList) {
				String templateNos = wechatMsgTemplate.getTemplateNo();
				deptCode = wechatMsgTemplate.getDeptCode();
				deptName = wechatMsgTemplate.getDeptName();
				companyName = wechatMsgTemplate.getCompanyName();
				if(templateNos.equalsIgnoreCase(templateNo)) flag = true;
			}
			
			if(!flag) {//不存在该编号模板,则为该门店新增一个对应模板信息
				WechatMsgTemplate wechatMsgTemplate = new WechatMsgTemplate();
				wechatMsgTemplate.setCreateBy("sys");
				wechatMsgTemplate.setUpdateBy("sys");
				wechatMsgTemplate.setCompanyId(companyId);
				wechatMsgTemplate.setCompanyName(companyName);
				wechatMsgTemplate.setDeptCode(deptCode);
				wechatMsgTemplate.setDeptName(deptName);
				wechatMsgTemplate.setTemplateNo(templateNo);
				wechatMsgTemplate.setTemplateTitle(wechatMsgTemplateType.getTemplateTitle());
				wechatMsgTemplateService.insert(wechatMsgTemplate);
			}
		}
		
	}
	
	public WechatMsgTemplateService getWechatMsgTemplateService() {
		return wechatMsgTemplateService;
	}

	@Resource
	public void setWechatMsgTemplateService(WechatMsgTemplateService wechatMsgTemplateService) {
		this.wechatMsgTemplateService = wechatMsgTemplateService;
	}

	public WechatMsgTemplateTypeService getWechatMsgTemplateTypeService() {
		return wechatMsgTemplateTypeService;
	}

	@Resource
	public void setWechatMsgTemplateTypeService(WechatMsgTemplateTypeService wechatMsgTemplateTypeService) {
		WechatMsgTemplateUtil.wechatMsgTemplateTypeService = wechatMsgTemplateTypeService;
	}
	
}

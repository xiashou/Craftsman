package com.tcode.open.wechat.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.member.model.Member;
import com.tcode.business.shop.model.Setting;
import com.tcode.business.shop.service.SettingService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

/**
 * 联系方式
 * @author supeng
 *
 */
@Scope("prototype")
@Component("ContactAction")
public class ContactAction extends BaseAction {
	
	private static Logger log = Logger.getLogger("SLog");
	private SettingService settingService;
	
	private Setting settingInfo;

	/**
	 * 初始化门店联系信息
	 * @return
	 */
	public String initContactInfo() {
		HttpServletRequest request = getRequest();
		Member member = (Member) request.getSession().getAttribute("member");
		String deptCode = member.getDeptCode();//门店编码
		
		//根据门店编码获取联系信息
		try {
			settingInfo = settingService.getById(deptCode);
			this.setResult(true, "");
		} catch(Exception e) {
			this.setResult(false, "查询失败！");
			log.error(Utils.getErrorMessage(e));
		}
		
		return SUCCESS;
	}
	
	
	public SettingService getSettingService() {
		return settingService;
	}
	
	@Resource
	public void setSettingService(SettingService settingService) {
		this.settingService = settingService;
	}

	public Setting getSettingInfo() {
		return settingInfo;
	}

	public void setSettingInfo(Setting settingInfo) {
		this.settingInfo = settingInfo;
	}

}

package com.tcode.business.mall.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.mall.model.MallSetting;
import com.tcode.business.mall.service.MallSettingService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("mallSettingAction")
public class MallSettingAction extends BaseAction {

	private static final long serialVersionUID = 335619606425463046L;
	private static Logger log = Logger.getLogger("SLog");
	
	private MallSettingService mallSettingService;
	
	private MallSetting setting;
	
	public String queryMallSettingByAppId() {
		try {
			WechatAuthorizerParams wechatApp = this.getWechatApp();
			if(!Utils.isEmpty(wechatApp)) {
				setting = mallSettingService.getById(wechatApp.getAuthorizerAppId());
				if(Utils.isEmpty(setting)){
					setting = new MallSetting();
					setting.setAppId(wechatApp.getAuthorizerAppId());
				}
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertMallSetting() {
		try {
			if(!Utils.isEmpty(setting)){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					mallSettingService.insert(setting);
					this.setResult(true, "添加成功！");
				} else
					this.setResult(true, "请先联系管理员配置公众号！");
			}
		} catch(Exception e) {
			this.setResult(true, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String updateMallSetting() {
		try {
			if(!Utils.isEmpty(setting) && !Utils.isEmpty(setting.getAppId())){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					mallSettingService.update(setting);
					this.setResult(true, "修改成功！");
				} else
					this.setResult(true, "请先联系管理员配置公众号！");
			}
		} catch (Exception e){
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteMallSetting() {
		try {
			if(!Utils.isEmpty(setting) && !Utils.isEmpty(setting.getAppId())){
				mallSettingService.delete(setting);
				this.setResult(true, "删除成功！");
			}
		} catch (Exception e){
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public MallSettingService getMallSettingService() {
		return mallSettingService;
	}
	@Resource
	public void setMallSettingService(MallSettingService mallSettingService) {
		this.mallSettingService = mallSettingService;
	}
	public MallSetting getSetting() {
		return setting;
	}
	public void setSetting(MallSetting setting) {
		this.setting = setting;
	}
	
}

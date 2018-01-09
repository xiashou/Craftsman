package com.tcode.business.wechat.act.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.model.ActStatement;
import com.tcode.business.wechat.act.service.ActStatementService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("actStatementAction")
public class ActStatementAction extends BaseAction {

	private static final long serialVersionUID = 8639934261192089430L;
	private static Logger log = Logger.getLogger("SLog");
	
	private ActStatementService actStatementService;

	private ActStatement statement;
	
	/**
	 * 活动设置
	 * @return
	 */
	public String queryActStatementByAppId() {
		try {
			WechatAuthorizerParams wechatApp = this.getWechatApp();
			if(!Utils.isEmpty(wechatApp)) {
				statement = actStatementService.getById(wechatApp.getAuthorizerAppId());
				if(Utils.isEmpty(statement)){
					statement = new ActStatement();
					statement.setAppId(wechatApp.getAuthorizerAppId());
				}
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String updateActStatement() {
		try {
			if(!Utils.isEmpty(statement) && !Utils.isEmpty(statement.getAppId())){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					actStatementService.update(statement);
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

	public ActStatementService getActStatementService() {
		return actStatementService;
	}
	@Resource
	public void setActStatementService(ActStatementService actStatementService) {
		this.actStatementService = actStatementService;
	}

	public ActStatement getStatement() {
		return statement;
	}

	public void setStatement(ActStatement statement) {
		this.statement = statement;
	}
	
	
}

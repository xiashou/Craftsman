package com.tcode.business.mall.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.mall.model.MallSendMode;
import com.tcode.business.mall.service.MallSendModeService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;


@Scope("prototype")
@Component("mallSendModeAction")
public class MallSendModeAction extends BaseAction {
	
	private static final long serialVersionUID = 335619606425463046L;
	private static Logger log = Logger.getLogger("SLog");

	private MallSendModeService mallSendModeService;
	
	private List<MallSendMode> sendModeList;
	private MallSendMode sendMode;
	
	
	/**
	 * 分页查找配送方式信息
	 * @return
	 */
	public String queryMallSendModeByPage() {
		try {
			if(Utils.isEmpty(sendMode))
				sendMode = new MallSendMode();
			WechatAuthorizerParams wechatApp = this.getWechatApp();
			if(!Utils.isEmpty(wechatApp)) {
				sendMode.setAppId(wechatApp.getAuthorizerAppId());
				this.setTotalCount(mallSendModeService.getListCount(sendMode));
				sendModeList = mallSendModeService.getListPage(sendMode, this.getStart(), this.getLimit());
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
	public String insertMallSendMode() {
		try {
			if(!Utils.isEmpty(sendMode)){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					sendMode.setAppId(wechatApp.getAuthorizerAppId());
					mallSendModeService.insert(sendMode);
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
	public String updateMallSendMode() {
		try {
			if(!Utils.isEmpty(sendMode) && !Utils.isEmpty(sendMode.getId())){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					sendMode.setAppId(wechatApp.getAuthorizerAppId());
					mallSendModeService.update(sendMode);
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
	public String deleteMallSendMode() {
		try {
			if(!Utils.isEmpty(sendMode) && !Utils.isEmpty(sendMode.getId())){
				mallSendModeService.delete(sendMode);
				this.setResult(true, "删除成功！");
			}
		} catch (Exception e){
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	public MallSendModeService getMallSendModeService() {
		return mallSendModeService;
	}
	@Resource
	public void setMallSendModeService(MallSendModeService mallSendModeService) {
		this.mallSendModeService = mallSendModeService;
	}
	public List<MallSendMode> getSendModeList() {
		return sendModeList;
	}
	public void setSendModeList(List<MallSendMode> sendModeList) {
		this.sendModeList = sendModeList;
	}
	public MallSendMode getSendMode() {
		return sendMode;
	}
	public void setSendMode(MallSendMode sendMode) {
		this.sendMode = sendMode;
	}
	
	
}

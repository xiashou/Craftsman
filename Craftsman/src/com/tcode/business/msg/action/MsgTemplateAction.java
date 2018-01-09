package com.tcode.business.msg.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.msg.model.MsgTemplate;
import com.tcode.business.msg.service.MsgTemplateService;
import com.tcode.common.action.BaseAction;
import com.tcode.common.message.MsgUtil;
import com.tcode.core.util.Utils;

/**
 * 短信模板
 * @author supeng
 *
 */
@Scope("prototype")
@Component("MsgTemplateAction")
public class MsgTemplateAction extends BaseAction{
	
	private static Logger log = Logger.getLogger("SLog");
	private MsgTemplateService msgTemplateService;
	private List<MsgTemplate> msgTemplateList;
	private int templateTypeNo;
	private MsgTemplate msgTemplate;
	
	/**
	 * 根据模板类型编码获取模板信息
	 * @return
	 */
	public String queryMsgTemplateByTemplateTypeNo() {
		try {
			if(!Utils.isEmpty(templateTypeNo)){
//				MsgUtil.sendConsumerMsg(templateTypeNo, "1000000199");
				msgTemplateList = msgTemplateService.getByTemplateTypeNo(this.getDept().getDeptCode(), templateTypeNo);
				this.setResult(true, "");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 新增或修改模板信息
	 * @return
	 */
	public String insertOrUpdateMsgTemplate() {
		try {
			if(!Utils.isEmpty(msgTemplate)){
				int templateTypeNo = msgTemplate.getTemplateTypeNo();
				msgTemplateList = msgTemplateService.getByTemplateTypeNo(this.getDept().getDeptCode(), templateTypeNo);
				if(msgTemplateList.size() == 1) {//存在对应门店、类型的模板内容，进行修改
					MsgTemplate msgTemplateU = msgTemplateList.get(0);
					msgTemplateU.setContent(msgTemplate.getContent());
					msgTemplateU.setRemainingDays(msgTemplate.getRemainingDays());
					msgTemplateU.setSendRate(msgTemplate.getSendRate());
					msgTemplateService.update(msgTemplateU);
					this.setResult(true, "修改成功");
				}else {//不存在进行新增
					msgTemplate.setDeptCode(this.getDept().getDeptCode());
					msgTemplateService.insert(msgTemplate);
					this.setResult(true, "添加成功！");
				}
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	
	
	public MsgTemplateService getMsgTemplateService() {
		return msgTemplateService;
	}

	@Resource
	public void setMsgTemplateService(MsgTemplateService msgTemplateService) {
		this.msgTemplateService = msgTemplateService;
	}

	public List<MsgTemplate> getMsgTemplateList() {
		return msgTemplateList;
	}

	public void setMsgTemplateList(List<MsgTemplate> msgTemplateList) {
		this.msgTemplateList = msgTemplateList;
	}

	public int getTemplateTypeNo() {
		return templateTypeNo;
	}

	public void setTemplateTypeNo(int templateTypeNo) {
		this.templateTypeNo = templateTypeNo;
	}

	public MsgTemplate getMsgTemplate() {
		return msgTemplate;
	}

	public void setMsgTemplate(MsgTemplate msgTemplate) {
		this.msgTemplate = msgTemplate;
	}
	
}

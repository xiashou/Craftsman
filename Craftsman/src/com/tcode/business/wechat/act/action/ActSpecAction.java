package com.tcode.business.wechat.act.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.model.ActSpec;
import com.tcode.business.wechat.act.service.ActSpecService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("actSpecAction")
public class ActSpecAction extends BaseAction {

	private static final long serialVersionUID = 8493478560001204739L;
	private static Logger log = Logger.getLogger("SLog");

	private ActSpecService actSpecService;
	
	private List<ActSpec> specList;
	
	private ActSpec spec;
	private Integer actId;
	
	
	public String queryActSpecListByActId() {
		try {
			if(!Utils.isEmpty(actId)){
				specList = actSpecService.getListByActId(actId);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String insertActSpec() {
		try {
			if(!Utils.isEmpty(spec) && !Utils.isEmpty(spec.getActId())){
				actSpecService.insert(spec);
				this.setResult(true, "添加成功！");
			} else
				this.setResult(false, "参数缺失！");
		} catch(Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String updateActSpec() {
		try {
			if(!Utils.isEmpty(spec) && !Utils.isEmpty(spec.getId())){
				actSpecService.update(spec);
				this.setResult(true, "编辑成功！");
			} else
				this.setResult(false, "参数缺失！");
		} catch(Exception e) {
			this.setResult(false, "编辑失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String deleteActSpec() {
		try {
			if(!Utils.isEmpty(spec) && !Utils.isEmpty(spec.getId())){
				actSpecService.delete(spec);
				this.setResult(true, "删除成功！");
			} else
				this.setResult(false, "参数缺失！");
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	

	public ActSpecService getActSpecService() {
		return actSpecService;
	}
	@Resource
	public void setActSpecService(ActSpecService actSpecService) {
		this.actSpecService = actSpecService;
	}

	public List<ActSpec> getSpecList() {
		return specList;
	}

	public void setSpecList(List<ActSpec> specList) {
		this.specList = specList;
	}

	public ActSpec getSpec() {
		return spec;
	}

	public void setSpec(ActSpec spec) {
		this.spec = spec;
	}

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}
	
}

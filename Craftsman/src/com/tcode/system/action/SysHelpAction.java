package com.tcode.system.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysHelp;
import com.tcode.system.service.SysHelpService;

@Scope("prototype")
@Component("sysHelpAction")
public class SysHelpAction extends BaseAction {

	private static final long serialVersionUID = 1323762215809070551L;
	private static Logger log = Logger.getLogger("SLog");
	
	private SysHelpService sysHelpService;
	
	private SysHelp sysHelp;
	private String id;
	
	
	/**
	 * 根据ID查找操作指引
	 * @return
	 */
	public String querySysHelpById() {
		try {
			if(!Utils.isEmpty(id)){
				sysHelp = sysHelpService.getSysHelpById(id);
				if(Utils.isEmpty(sysHelp))
					sysHelp = new SysHelp();
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据ID查找操作指引
	 * @return
	 */
	public String insertSysHelp() {
		try {
			if(!Utils.isEmpty(sysHelp)){
				sysHelpService.update(sysHelp);
				this.setResult(true, "");
			}
		} catch(Exception e) {
			this.setResult(false, "保存失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}



	public SysHelpService getSysHelpService() {
		return sysHelpService;
	}
	@Resource
	public void setSysHelpService(SysHelpService sysHelpService) {
		this.sysHelpService = sysHelpService;
	}

	public SysHelp getSysHelp() {
		return sysHelp;
	}

	public void setSysHelp(SysHelp sysHelp) {
		this.sysHelp = sysHelp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}

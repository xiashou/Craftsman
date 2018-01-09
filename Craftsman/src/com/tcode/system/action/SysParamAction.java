package com.tcode.system.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysParam;
import com.tcode.system.service.SysParamService;

@Scope("prototype")
@Component("sysParamAction")
public class SysParamAction extends BaseAction {

	private static final long serialVersionUID = 6582854975822171645L;
	private static Logger log = Logger.getLogger("SLog");
	
	private SysParamService sysParamService;
	
	private List<SysParam> paramList;
	private SysParam param;
	
	/**
	 * 分页查询系统区域
	 * @return
	 */
	public String querySysParamPage() {
		try {
			this.setTotalCount(sysParamService.getListCount(param));
			paramList = sysParamService.getListPage(param, this.getStart(), this.getLimit());
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertSysParam() {
		try {
			//根据key查询是否有重复记录
			SysParam result = sysParamService.getParamByKey(param.getParamKey().trim());
			if(result == null){
				sysParamService.insert(param);
				this.setResult(true, "添加成功！");
			}else{
				this.setResult(false, "添加记录已存在！");
			}
		} catch(Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 编辑
	 * @return
	 */
	public String updateSysParam() {
		try {
			sysParamService.update(param);
			this.setResult(true, "修改成功！");
		} catch(Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteSysParam() {
		try {
			if(!Utils.isEmpty(param) && !Utils.isEmpty(param.getId())){
				sysParamService.delete(param);
				this.setResult(true, "删除成功！");
			} else
				this.setResult(false, "ID不能为空！");
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}


	public SysParamService getSysParamService() {
		return sysParamService;
	}
	@Resource
	public void setSysParamService(SysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}

	public List<SysParam> getParamList() {
		return paramList;
	}

	public void setParamList(List<SysParam> paramList) {
		this.paramList = paramList;
	}

	public SysParam getParam() {
		return param;
	}

	public void setParam(SysParam param) {
		this.param = param;
	}
}

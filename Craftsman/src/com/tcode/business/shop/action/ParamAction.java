package com.tcode.business.shop.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.shop.model.Param;
import com.tcode.business.shop.service.ParamService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("paramAction")
public class ParamAction extends BaseAction {

	private static final long serialVersionUID = 8206852408865695361L;
	private static Logger log = Logger.getLogger("SLog");
	
	private ParamService paramService;
	
	private Param param;
	
	/**
	 * 查询部门所有参数
	 * @return
	 */
	public String queryParamById() {
		try {
			param = paramService.getById(this.getDept().getDeptCode());
			this.setResult(true, "");
		} catch(Exception e) {
			this.setResult(false, "查询失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加参数配置
	 * @return
	 */
	public String insertParam() {
		try {
			if(!Utils.isEmpty(param)){
				param.setDeptCode(this.getDept().getDeptCode());
				paramService.insert(param);
				this.setResult(true, "添加成功！");
			}
		} catch (Exception e){
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改参数配置
	 * @return
	 */
	public String updateParam() {
		try {
			if(!Utils.isEmpty(param)){
				param.setDeptCode(this.getDept().getDeptCode());
				paramService.update(param);
				this.setResult(true, "修改成功！");
			}
		} catch (Exception e){
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除参数配置
	 * @return
	 */
	public String deleteParam() {
		try {
			if(!Utils.isEmpty(param) && !Utils.isEmpty(param.getDeptCode())){
				paramService.delete(param);
				this.setResult(true, "删除成功！");
			}
		} catch (Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public ParamService getParamService() {
		return paramService;
	}
	@Resource
	public void setParamService(ParamService paramService) {
		this.paramService = paramService;
	}
	public Param getParam() {
		return param;
	}
	public void setParam(Param param) {
		this.param = param;
	}
	
}

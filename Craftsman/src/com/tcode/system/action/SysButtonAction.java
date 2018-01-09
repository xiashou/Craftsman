package com.tcode.system.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysButton;
import com.tcode.system.service.SysButtonService;

@Scope("prototype")
@Component("sysButtonAction")
public class SysButtonAction extends BaseAction {

	private static final long serialVersionUID = 6582854975822171645L;
	private static Logger log = Logger.getLogger("SLog");
	
	private SysButtonService sysButtonService;
	
	private List<SysButton> buttonList;
	private SysButton button;
	
	/**
	 * 分页查询系统区域
	 * @return
	 */
	public String querySysButtonPage() {
		try {
			this.setTotalCount(sysButtonService.getListCount(button));
			buttonList = sysButtonService.getListPage(button, this.getStart(), this.getLimit());
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertSysButton() {
		try {
			sysButtonService.insert(button);
			this.setResult(true, "添加成功！");
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
	public String updateSysButton() {
		try {
			sysButtonService.update(button);
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
	public String deleteSysButton() {
		try {
			if(!Utils.isEmpty(button) && !Utils.isEmpty(button.getBtnId())){
				sysButtonService.delete(button);
				this.setResult(true, "删除成功！");
			} else
				this.setResult(false, "ID不能为空！");
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	
	public SysButtonService getSysButtonService() {
		return sysButtonService;
	}
	@Resource
	public void setSysButtonService(SysButtonService sysButtonService) {
		this.sysButtonService = sysButtonService;
	}

	public List<SysButton> getButtonList() {
		return buttonList;
	}

	public void setButtonList(List<SysButton> buttonList) {
		this.buttonList = buttonList;
	}

	public SysButton getButton() {
		return button;
	}

	public void setButton(SysButton button) {
		this.button = button;
	}
	
}

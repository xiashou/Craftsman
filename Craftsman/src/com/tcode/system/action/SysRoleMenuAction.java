package com.tcode.system.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysMenu;
import com.tcode.system.model.SysRoleMenu;
import com.tcode.system.service.SysMenuService;
import com.tcode.system.service.SysRoleMenuService;

@Scope("prototype")
@Component("sysRoleMenuAction")
public class SysRoleMenuAction extends BaseAction {

	private static final long serialVersionUID = 2761981864362222416L;
	private static Logger log = Logger.getLogger("SLog");
	
	private SysRoleMenuService roleMenuService;
	private SysMenuService sysMenuService;
	
	private List<SysMenu> treeList;
	
	private Integer roleId;
	private String[] ids;
	
	/**
	 * 用户角色与菜单绑定
	 * @return
	 */
	public String bindSysRoleMenu() {
		try {
			if(Utils.isEmpty(ids) || Utils.isEmpty(roleId) || ids.length == 0)
				this.setResult(false, "保存失败，缺失参数！");
			else {
				int count = 0;
				if(roleMenuService.removeByRoleId(roleId)){
					for(int i = 0; i < ids.length; i++){
						//菜单权限,直接insert
						if(ids[i].indexOf("_") == -1){
							SysRoleMenu roleMenu = new SysRoleMenu();
							roleMenu.setRoleId(roleId);
							roleMenu.setMenuId(ids[i]);
							roleMenuService.insert(roleMenu);
							count++;
						//按钮权限,把按钮组装成字符串,update
						} else {
							String[] menuBtn = ids[i].split("_");
							SysRoleMenu roleMenu = roleMenuService.getByRoleIdAndMenuId(roleId, menuBtn[0]);
							if(Utils.isEmpty(roleMenu.getButtons()))
								roleMenu.setButtons(menuBtn[1]);
							else
								roleMenu.setButtons(roleMenu.getButtons()+ "," + menuBtn[1]);
							roleMenuService.update(roleMenu);
							count++;
						}
					}
				} else
					this.setResult(false, "保存权限失败！");
				if(count == ids.length)
					this.setResult(true, "权限保存成功！");
				else if(count > 0)
					this.setResult(true, "部分权限保存成功，请检查并重新保存！");
			}
		} catch(Exception e) {
			this.setResult(false, "保存权限失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}


	public SysRoleMenuService getRoleMenuService() {
		return roleMenuService;
	}
	@Resource
	public void setRoleMenuService(SysRoleMenuService roleMenuService) {
		this.roleMenuService = roleMenuService;
	}
	public SysMenuService getSysMenuService() {
		return sysMenuService;
	}
	@Resource
	public void setSysMenuService(SysMenuService sysMenuService) {
		this.sysMenuService = sysMenuService;
	}
	public List<SysMenu> getTreeList() {
		return treeList;
	}
	public void setTreeList(List<SysMenu> treeList) {
		this.treeList = treeList;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	
}

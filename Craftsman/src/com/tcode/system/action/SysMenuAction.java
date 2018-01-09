package com.tcode.system.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysMenu;
import com.tcode.system.service.SysMenuService;
import com.tcode.system.service.SysRoleMenuService;

@Scope("prototype")
@Component("sysMenuAction")
public class SysMenuAction extends BaseAction implements SessionAware {

	private static final long serialVersionUID = 2761981864362222416L;
	private static Logger log = Logger.getLogger("SLog");
	
	private SysMenuService sysMenuService;
	private SysRoleMenuService roleMenuService;
	private Map<String, Object> session;

	private SysMenu menu;
	private String parentId;
	private Integer roleId;
	private String menuType;
	
	private List<SysMenu> treeList;
	private List<SysMenu> menuList;
	
	/**
	 * 分页查询菜单
	 * @return
	 * @throws Exception
	 */
	public String queryMenuPage() {
		try {
			this.setTotalCount(sysMenuService.getListCount(menu));
			menuList = sysMenuService.getListPage(menu, this.getStart(), this.getLimit());
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 获取普通树结构菜单
	 * @return
	 * @throws Exception
	 */
	public String queryNormalTree() {
		try {
			treeList = sysMenuService.getMenuByParentId(parentId);
			for(int i = 0; i < treeList.size(); i++) {
				treeList.get(i).setText(treeList.get(i).getMenuName());
				List<SysMenu> childList = sysMenuService.getMenuByParentId(treeList.get(i).getId());
				sysMenuService.completeMenuTree(childList);
				treeList.get(i).setChildren(childList);
			}
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 获取带权限结构（按钮）的树结构菜单
	 * @return
	 * @throws Exception
	 */
	public String queryAuthorizationTree() {
		try {
			treeList = sysMenuService.getMenuByParentId("0");
			sysMenuService.completeAuthorizationTree(this.getRole().getRoleId(), roleId, (Utils.isEmpty(menuType) ? "1" : menuType), treeList);
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 获取当前角色的所有树结构菜单
	 * @return
	 * @throws Exception
	 */
	public String queryRoleTree() {
		try {
			treeList = sysMenuService.getMenuByParentIdAndRoleId(parentId, roleId, this.getRole().getRoleType());
			sysMenuService.completeRoleTree(roleId, treeList, this.getRole().getRoleType());
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 获取系统代理的所有菜单
	 * @return
	 * @throws Exception
	 */
	public String queryAgentTree() {
		try {
			treeList = sysMenuService.getMenuByAgent(parentId);
			sysMenuService.completeAgentTree(parentId, treeList);
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据ID查找菜单
	 * @return
	 */
	public String queryMenuById() {
		try {
			if(!Utils.isEmpty(menu) && !Utils.isEmpty(menu.getId()))
				menu = sysMenuService.getMenuById(menu.getId());
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加菜单
	 * @return
	 * @throws Exception
	 */
	public String insertSysMenu() {
		try {
			sysMenuService.insert(menu);
			this.setResult(true, "添加成功！");
		} catch(Exception e) {
			this.setResult(false, "添加失败！" + Utils.getErrorMessage(e));
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 编辑菜单
	 * @return
	 * @throws Exception
	 */
	public String updateSysMenu() {
		try {
			sysMenuService.update(menu);
			this.setResult(true, "修改成功！");
		} catch(Exception e) {
			this.setResult(false, "修改失败！" + Utils.getErrorMessage(e));
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除菜单
	 * @return
	 * @throws Exception
	 */
	public String deleteSysMenu() {
		try {
			menu = sysMenuService.getMenuById(menu.getId());
			if(!menu.getLeaf())
				this.setResult(false, "该菜单含有子菜单，请先删除子菜单！");
			else {
				sysMenuService.delete(menu);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！" + Utils.getErrorMessage(e));
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	
	
	public SysMenuService getSysMenuService() {
		return sysMenuService;
	}
	@Resource
	public void setSysMenuService(SysMenuService sysMenuService) {
		this.sysMenuService = sysMenuService;
	}
	public SysRoleMenuService getRoleMenuService() {
		return roleMenuService;
	}
	@Resource
	public void setRoleMenuService(SysRoleMenuService roleMenuService) {
		this.roleMenuService = roleMenuService;
	}
	public SysMenu getMenu() {
		return menu;
	}
	public void setMenu(SysMenu menu) {
		this.menu = menu;
	}
	public List<SysMenu> getTreeList() {
		return treeList;
	}
	public void setTreeList(List<SysMenu> treeList) {
		this.treeList = treeList;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public List<SysMenu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<SysMenu> menuList) {
		this.menuList = menuList;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session; 
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	
}

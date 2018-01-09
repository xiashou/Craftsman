package com.tcode.system.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysRole;
import com.tcode.system.model.SysUser;
import com.tcode.system.service.SysRoleMenuService;
import com.tcode.system.service.SysRoleService;
import com.tcode.system.service.SysUserService;

@Scope("prototype")
@Component("sysRoleAction")
public class SysRoleAction extends BaseAction {

	private static final long serialVersionUID = 2761981864362222416L;
	private static Logger log = Logger.getLogger("SLog");
	
	private SysRoleService sysRoleService;
	private SysRoleMenuService roleMenuService;
	private SysUserService userService;
	private Integer roleId;

	private List<SysRole> roleList;
	private List<SysRole> treeList;
	private SysRole sysRole;
	
	/**
	 * 分页查询角色信息
	 * @return
	 */
	public String queryRolePage() {
		try {
			if(Utils.isEmpty(sysRole)) {
				sysRole = new SysRole();
				sysRole.setDeptId(this.getDept().getId());
			}
			this.setTotalCount(sysRoleService.getListCount(sysRole));
			roleList = sysRoleService.getListPage(sysRole, this.getStart(), this.getLimit());
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询所有角色
	 * @return
	 */
	public String queryAllRole() {
		try {
			roleList = sysRoleService.getAll();
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询所有角色简略信息
	 * @return
	 */
	public String queryAllSimpleRole() {
		try {
			roleList = sysRoleService.getAllRoleSimple();
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询角色菜单
	 * @return
	 */
	public String queryMenuRole() {
		try {
			roleList = sysRoleService.getUnlockRole(roleId);
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加角色信息
	 * @return
	 */
	public String insertSysRole() {
		try {
			sysRoleService.insert(sysRole);
			this.setResult(true, "添加成功！");
		} catch (Exception e) {
			this.setResult(false, "添加失败！" + Utils.getErrorMessage(e));
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改角色信息
	 * @return
	 */
	public String updateSysRole() {
		try {
			if(sysRole.getRoleId() != null && !"".equals(sysRole.getRoleId())){
				sysRoleService.update(sysRole);
				this.setResult(true, "修改成功！");
			} else
				this.setResult(false, "ID为空！");
		} catch(Exception e) {
			this.setResult(false, "修改失败！" + Utils.getErrorMessage(e));
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除角色信息
	 * 超级管理员角色不能被删除
	 * 有关联用户的角色不能被删除
	 * 删除角色同时删除该角色的菜单权限
	 * @return
	 */
	public String deleteSysRole() {
		try {
			sysRole = sysRoleService.getRoleById(sysRole.getRoleId());
			List<SysUser> userList = userService.getUserByRoleId(sysRole.getRoleId());
			if(!Utils.isEmpty(userList)){
				this.setResult(false, "删除失败，请先删除该角色关联的用户！");
				return SUCCESS;
			}
			sysRoleService.delete(sysRole);
			roleMenuService.removeByRoleId(sysRole.getRoleId());
			this.setResult(true, "删除成功！");
		} catch(Exception e) {
			this.setResult(false, "删除失败！" + Utils.getErrorMessage(e));
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public SysRoleService getSysRoleService() {
		return sysRoleService;
	}
	@Resource
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}
	public SysRoleMenuService getRoleMenuService() {
		return roleMenuService;
	}
	@Resource
	public void setRoleMenuService(SysRoleMenuService roleMenuService) {
		this.roleMenuService = roleMenuService;
	}
	public SysUserService getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(SysUserService userService) {
		this.userService = userService;
	}
	public List<SysRole> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}
	public SysRole getSysRole() {
		return sysRole;
	}
	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public List<SysRole> getTreeList() {
		return treeList;
	}
	public void setTreeList(List<SysRole> treeList) {
		this.treeList = treeList;
	}
}

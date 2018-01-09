package com.tcode.system.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.common.action.BaseAction;
import com.tcode.core.util.MD5;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysUser;
import com.tcode.system.service.SysUserService;

@Scope("prototype")
@Component("sysUserAction")
public class SysUserAction extends BaseAction {

	private static final long serialVersionUID = 2761981864362222416L;
	private static Logger log = Logger.getLogger("SLog");

	private SysUserService sysUserService;

	private List<SysUser> userList;
	private SysUser sysUser;
	private String newPassword;

	/**
	 * 分页查询系统用户
	 * @return
	 */
	public String queryUserPage() {
		try {
			this.setTotalCount(sysUserService.getListCount(sysUser));
			userList = sysUserService.getListPage(sysUser, this.getStart(), this.getLimit());
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	/**
	 * 添加系统用户 
	 * 添加前先判断用户名是否存在 
	 * @return
	 */
	public String insertSysUser() {
		try {
			if (sysUser != null) {
				SysUser haveUser = new SysUser();
				haveUser = sysUserService.getUserByName(sysUser.getUserName());
				if (haveUser != null) {
					this.setResult(false, "用户名已存在！");
					return SUCCESS;
				}
				sysUserService.insert(sysUser);
				this.setResult(true, "添加成功！");
			} else
				this.setResult(false, "用户信息为空！");
		} catch (Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	/**
	 * 修改系统用户
	 * 
	 * @return
	 */
	public String updateSysUser() {
		try {
			if (!Utils.isEmpty(sysUser.getUserId())) {
				sysUser.setPassword(MD5.crypt(sysUser.getPassword()));
				sysUserService.update(sysUser);
				this.setResult(true, "修改成功！");
			} else
				this.setResult(false, "ID为空！");
		} catch(Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	/**
	 * 删除系统用户 
	 * 超级用户不能被删除
	 * @return
	 */
	public String deleteSysUser() {
		try {
			sysUser = sysUserService.getUserById(sysUser.getUserId());
			sysUserService.delete(sysUser);
			this.setResult(true, "删除成功！");
		} catch (Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	/**
	 * 系统用户权限绑定
	 * 
	 * @return
	 * @throws Exception
	 */
	public String bindSysUserRole() {
		try {
			if (!Utils.isEmpty(sysUser.getUserId()) && !Utils.isEmpty(sysUser.getRoleId())) {
				Integer roleId = sysUser.getRoleId();
				sysUser = sysUserService.getUserById(sysUser.getUserId());
				sysUser.setRoleId(roleId);
				sysUserService.update(sysUser);
				this.setResult(true, "绑定成功！");
			} else
				this.setResult(false, "ID为空！");
		} catch(Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 系统用户修改密码
	 * @return
	 * @throws Exception
	 */
	public String changeSysUserPassword() {
		try {
			SysUser user = (SysUser) this.getRequest().getSession().getAttribute(SESSION_USER);
			if(!Utils.isEmpty(user) && !Utils.isEmpty(sysUser) && !Utils.isEmpty(newPassword)){
				if(MD5.crypt(sysUser.getPassword()).equals(user.getPassword())){
					user.setPassword(MD5.crypt(newPassword));
					sysUserService.update(user);
					this.setResult(false, "修改成功！");
				} else
					this.setResult(false, "密码不正确！");
			}
		} catch(Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public SysUserService getSysUserService() {
		return sysUserService;
	}
	@Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	public List<SysUser> getUserList() {
		return userList;
	}
	public void setUserList(List<SysUser> userList) {
		this.userList = userList;
	}
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}

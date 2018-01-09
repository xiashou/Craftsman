package com.tcode.system.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.common.action.BaseAction;
import com.tcode.core.util.MD5;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysAgent;
import com.tcode.system.model.SysRole;
import com.tcode.system.service.SysAgentService;
import com.tcode.system.service.SysRoleService;

@Scope("prototype")
@Component("sysAgentAction")
public class SysAgentAction extends BaseAction {

	private static final long serialVersionUID = 6558223936815428415L;
	private static Logger log = Logger.getLogger("SLog");

	private SysAgentService sysAgentService;
	private SysRoleService sysRoleService;
	
	private List<SysAgent> agentList;
	private SysAgent sysAgent;
	
	/**
	 * 代理登录
	 * @return
	 */
	public String agentLogin() {
		try {
			SysAgent loginAgent = null;
			if(!Utils.isEmpty(sysAgent) && !Utils.isEmpty(sysAgent.getAgentName()))
				loginAgent = sysAgentService.getAgentByName(sysAgent.getAgentName());
			if(Utils.isEmpty(loginAgent)){
				this.setResult(false, "该代理用户不存在！");
				return SUCCESS;
			}
			if(!loginAgent.getPassword().equals(MD5.crypt(sysAgent.getPassword()))){
				this.setResult(false, "密码错误！");
				return SUCCESS;
			}
			if(loginAgent.getLocked()){
				this.setResult(false, "该代理用户已被锁定，请联系管理员！");
				return SUCCESS;
			}
			if(Utils.isEmpty(loginAgent.getRoleId())){
				this.setResult(false, "该用户没有绑定任何角色！");
				return SUCCESS;
			}
			//获取用户角色信息， 放入session 
			SysRole sysRole = sysRoleService.getRoleById(loginAgent.getRoleId());
			if(Utils.isEmpty(sysRole)){
				this.setResult(false, "用户角色不存在，请联系管理员！");
				return SUCCESS;
			} else if(!Utils.isEmpty(sysRole) && sysRole.getLocked()){
				this.setResult(false, "角色已锁定！");
				return SUCCESS;
			}
			loginAgent.setLastLogin(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
			sysAgentService.update(loginAgent);
			
			super.getRequest().getSession().setAttribute(SESSION_AGENT, loginAgent);
			super.getRequest().getSession().setAttribute(SESSION_ROLE, sysRole);
			
			this.setResult(true, "");
		} catch(Exception e) {
			this.setResult(false, "系统调试中！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 跳转到后台首页
	 * @return
	 * @throws Exception
	 */
	public String initAgentIndex() throws Exception {
		SysAgent agent = (SysAgent) super.getRequest().getSession().getAttribute(SESSION_AGENT);
		if(agent == null)
			return LOGIN;
		else
			return SUCCESS;
	}
	
	/**
	 * 代理注销
	 * @return
	 * @throws Exception
	 */
	public String agentLogout() throws Exception {
		super.getRequest().getSession().removeAttribute(SESSION_AGENT);
		return SUCCESS;
	}
	
	/**
	 * 添加一个代理用户
	 * @return
	 */
	public String queryAllSysAgent() {
		try {
			agentList = sysAgentService.getAll();
			this.setResult(true, "添加成功！");
		} catch (Exception e) {
			this.setResult(false, "查询失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加一个代理用户
	 * @return
	 */
	public String insertSysAgent() {
		try {
			if (!Utils.isEmpty(sysAgent)) {
				SysAgent haveUser = sysAgentService.getAgentByName(sysAgent.getAgentName());
				if (!Utils.isEmpty(haveUser)) {
					this.setResult(false, "代理用户已存在！");
					return SUCCESS;
				}
				sysAgent.setRoleId(101);
				sysAgent.setPassword(MD5.crypt(sysAgent.getPassword()));
				sysAgentService.insert(sysAgent);
				this.setResult(true, "添加成功！");
			} else
				this.setResult(false, "代理信息为空！");
		} catch (Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改一个代理用户
	 * @return
	 */
	public String updateSysAgent() {
		try {
			if (!Utils.isEmpty(sysAgent.getAgentId())) {
				sysAgent.setPassword(MD5.crypt(sysAgent.getPassword()));
				sysAgent.setRoleId(101);
				sysAgentService.update(sysAgent);
				this.setResult(true, "修改成功！");
			} else
				this.setResult(false, "ID为空！");
		} catch (Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除代理用户
	 * @return
	 */
	public String deleteSysAgent() {
		try {
			if (!Utils.isEmpty(sysAgent) && !Utils.isEmpty(sysAgent.getAgentId())) {
				sysAgent = sysAgentService.getAgentById(sysAgent.getAgentId());
				sysAgentService.delete(sysAgent);
				this.setResult(true, "删除成功！");
			} else
				this.setResult(false, "删除失败！");
		} catch (Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	public SysAgentService getSysAgentService() {
		return sysAgentService;
	}
	@Resource
	public void setSysAgentService(SysAgentService sysAgentService) {
		this.sysAgentService = sysAgentService;
	}
	public SysRoleService getSysRoleService() {
		return sysRoleService;
	}
	@Resource
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}
	public List<SysAgent> getAgentList() {
		return agentList;
	}
	public void setAgentList(List<SysAgent> agentList) {
		this.agentList = agentList;
	}
	public SysAgent getSysAgent() {
		return sysAgent;
	}
	public void setSysAgent(SysAgent sysAgent) {
		this.sysAgent = sysAgent;
	}
	
}

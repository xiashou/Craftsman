package com.tcode.system.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.tcode.business.basic.model.BaseArea;
import com.tcode.business.basic.service.BaseAreaService;
import com.tcode.business.shop.model.Setting;
import com.tcode.business.shop.service.SettingService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.service.WechatAuthorizerParamsService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.MD5;
import com.tcode.core.util.Utils;
import com.tcode.core.util.WeatherUtils;
import com.tcode.system.model.SysDept;
import com.tcode.system.model.SysRole;
import com.tcode.system.model.SysUser;
import com.tcode.system.service.SysDeptService;
import com.tcode.system.service.SysRoleService;
import com.tcode.system.service.SysUserService;

@Scope("prototype")
@Component("loginAction")
public class LoginAction extends BaseAction implements SessionAware {

	private static final long serialVersionUID = 2761981864362222416L;
	
	private Map<String, Object> session;
	private SysUserService sysUserService;
	private SysRoleService sysRoleService;
	private SysDeptService sysDeptService;
	private SettingService settingService;
	private BaseAreaService baseAreaService;
	private WechatAuthorizerParamsService wechatAuthorizerParamsService;

	private SysUser sysUser;
	private String weatherInfo;
	
	/**
	 * 跳转到登录
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 后台用户登录主方法
	 * @return
	 * @throws Exception
	 */
	public String userLogin() {
		
		try {
			SysUser loginUser = null;
			if(sysUser != null && !"".equals(sysUser.getUserName()))
				loginUser = sysUserService.getUserByName(sysUser.getUserName());
			if(loginUser == null){
				this.setResult(false, "该用户不存在！");
				return SUCCESS;
			}
			if(!loginUser.getPassword().equals(MD5.crypt(sysUser.getPassword()))){
				this.setResult(false, "密码错误！");
				return SUCCESS;
			}
			if(loginUser.getLocked()){
				this.setResult(false, "该用户已被锁定，请联系管理员！");
				return SUCCESS;
			}
			if(Utils.isEmpty(loginUser.getRoleId())){
				this.setResult(false, "该用户没有绑定任何角色！");
				return SUCCESS;
			}
			//获取用户角色信息， 放入session 
			SysRole sysRole = sysRoleService.getRoleById(loginUser.getRoleId());
			if(Utils.isEmpty(sysRole)){
				this.setResult(false, "用户角色不存在，请联系管理员！");
				return SUCCESS;
			} else if(!Utils.isEmpty(sysRole) && sysRole.getLocked()){
				this.setResult(false, "角色已锁定！");
				return SUCCESS;
			}
			SysDept sysDept = sysDeptService.getById(loginUser.getDeptId());
			Setting setting = settingService.getById(sysDept.getDeptCode());
			loginUser.setLastLogin(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			sysUserService.update(loginUser);
			if(!Utils.isEmpty(sysDept.getAreaId()) && !"root".equals(sysDept.getAreaId())) {
				BaseArea baseArea = baseAreaService.getAreaById(sysDept.getAreaId());
				session.put(SESSION_AREA, baseArea);
			}
			List<WechatAuthorizerParams> wechatParamsList = wechatAuthorizerParamsService.getByDeptCode(sysDept.getDeptCode());
			if(!Utils.isEmpty(wechatParamsList))
				session.put(SESSION_WECHATAPP, wechatParamsList.get(0));
			session.put(SESSION_USER, loginUser);
			session.put(SESSION_ROLE, sysRole);
			session.put(SESSION_DEPT, sysDept);
			session.put(SESSION_SETTING, setting);
			
			this.setResult(true, "");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/**
	 * 管理员登录门店专用方法
	 * @return
	 * @throws Exception
	 */
	public String sysLogin() {
		try {
			SysUser loginUser = null;
			SysDept sysDept = null;
			if(sysUser != null && !"".equals(sysUser.getUserName()))
				sysDept = sysDeptService.getByDeptCode(sysUser.getUserName());
			if(sysDept == null){
				this.setResult(false, "该门店不存在！");
				return SUCCESS;
			}
			loginUser = sysUserService.getUserByName("developer");
			if(!loginUser.getPassword().equals(MD5.crypt(sysUser.getPassword()))){
				this.setResult(false, "密码错误！");
				return SUCCESS;
			}
			if(Utils.isEmpty(loginUser.getRoleId())){
				this.setResult(false, "该用户没有绑定任何角色！");
				return SUCCESS;
			}
			//获取用户角色信息， 放入session 
			SysRole sysRole = sysRoleService.getRoleById(loginUser.getRoleId());
			if(Utils.isEmpty(sysRole)){
				this.setResult(false, "用户角色不存在，请联系管理员！");
				return SUCCESS;
			} else if(!Utils.isEmpty(sysRole) && sysRole.getLocked()){
				this.setResult(false, "角色已锁定！");
				return SUCCESS;
			}
			Setting setting = settingService.getById(sysDept.getDeptCode());
			if(!Utils.isEmpty(sysDept.getAreaId()) && !"root".equals(sysDept.getAreaId())) {
				BaseArea baseArea = baseAreaService.getAreaById(sysDept.getAreaId());
				session.put(SESSION_AREA, baseArea);
			}
			List<WechatAuthorizerParams> wechatParamsList = wechatAuthorizerParamsService.getByDeptCode(sysDept.getDeptCode());
			if(!Utils.isEmpty(wechatParamsList))
				session.put(SESSION_WECHATAPP, wechatParamsList.get(0));
			session.put(SESSION_USER, loginUser);
			session.put(SESSION_ROLE, sysRole);
			session.put(SESSION_DEPT, sysDept);
			session.put(SESSION_SETTING, setting);
			
			this.setResult(true, "");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 注销
	 * @return
	 * @throws Exception
	 */
	public String logout() throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.remove(SESSION_ROLE);
		session.remove(SESSION_USER);
		session.remove(SESSION_DEPT);
		session.remove(SESSION_SETTING);
		return SUCCESS;
	}
	
	/**
	 * 跳转到后台首页
	 * @return
	 * @throws Exception
	 */
	public String initIndex() throws Exception {
		SysUser user = (SysUser) session.get(SESSION_USER);
		if(user == null)
			return LOGIN;
		else
			return SUCCESS;
	}
	
	/**
	 * 查询天气
	 * @return
	 * @throws Exception
	 */
	public String queryWeatherInfo() throws Exception {
		BaseArea area = (BaseArea) session.get("SESSION_AREA");
		if(!Utils.isEmpty(area))
			weatherInfo = WeatherUtils.weatherInfo(area.getAreaName());
		else
			weatherInfo = "";
		return SUCCESS;
	}
	
	
	/**
	 * 老板端登录主方法
	 * @return
	 * @throws Exception
	 */
	public String bossLogin() {
		try {
			SysUser loginUser = null;
			if(sysUser != null && !"".equals(sysUser.getUserName()))
				loginUser = sysUserService.getMgrByName(sysUser.getUserName());
			if(loginUser == null){
				this.setResult(false, "该用户不存在！");
				return SUCCESS;
			}
			if(!loginUser.getPassword().equals(MD5.crypt(sysUser.getPassword()))){
				this.setResult(false, "密码错误！");
				return SUCCESS;
			}
			if(loginUser.getLocked()){
				this.setResult(false, "该用户已被锁定，请联系管理员！");
				return SUCCESS;
			}
			if(Utils.isEmpty(loginUser.getRoleId())){
				this.setResult(false, "该用户没有绑定任何角色！");
				return SUCCESS;
			}
			//获取用户角色信息， 放入session 
			SysRole sysRole = sysRoleService.getRoleById(loginUser.getRoleId());
			if(Utils.isEmpty(sysRole)){
				this.setResult(false, "用户角色不存在，请联系管理员！");
				return SUCCESS;
			} else if(!Utils.isEmpty(sysRole) && sysRole.getLocked()){
				this.setResult(false, "角色已锁定！");
				return SUCCESS;
			}
//			SysDept sysDept = sysDeptService.getById(loginUser.getDeptId());
			loginUser.setLastLogin(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			sysUserService.update(loginUser);
			session.put(SESSION_MAGR, loginUser);
//			session.put(SESSION_ROLE, sysRole);
//			session.put(SESSION_DEPT, sysDept);
			
			this.setResult(true, "");
			return SUCCESS;
		} catch(Exception e) {
			e.printStackTrace();
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
	public SysRoleService getSysRoleService() {
		return sysRoleService;
	}
	@Resource
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}
	public SysDeptService getSysDeptService() {
		return sysDeptService;
	}
	@Resource
	public void setSysDeptService(SysDeptService sysDeptService) {
		this.sysDeptService = sysDeptService;
	}
	public SettingService getSettingService() {
		return settingService;
	}
	@Resource
	public void setSettingService(SettingService settingService) {
		this.settingService = settingService;
	}
	public BaseAreaService getBaseAreaService() {
		return baseAreaService;
	}
	@Resource
	public void setBaseAreaService(BaseAreaService baseAreaService) {
		this.baseAreaService = baseAreaService;
	}
	public WechatAuthorizerParamsService getWechatAuthorizerParamsService() {
		return wechatAuthorizerParamsService;
	}
	@Resource
	public void setWechatAuthorizerParamsService(
			WechatAuthorizerParamsService wechatAuthorizerParamsService) {
		this.wechatAuthorizerParamsService = wechatAuthorizerParamsService;
	}

	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session; 
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public String getWeatherInfo() {
		return weatherInfo;
	}
	public void setWeatherInfo(String weatherInfo) {
		this.weatherInfo = weatherInfo;
	}
}

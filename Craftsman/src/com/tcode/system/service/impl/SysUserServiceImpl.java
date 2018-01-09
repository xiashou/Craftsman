package com.tcode.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.core.util.MD5;
import com.tcode.system.dao.SysUserDao;
import com.tcode.system.model.SysUser;
import com.tcode.system.service.SysUserService;

/**
 * 系统用户service
 * @author Xiashou
 * @since 2015/04/09
 */
@Component("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	
	private SysUserDao sysUserDao;

	@Override
	public List<SysUser> getAll() throws Exception {
		return sysUserDao.loadAll();
	}
	
	@Override
	public SysUser getUserByName(String userName) throws Exception {
		return sysUserDao.loadByName(userName);
	}
	
	@Override
	public SysUser getMgrByName(String userName) throws Exception {
		return sysUserDao.loadByMgrName(userName);
	}

	@Override
	public SysUser getUserById(Integer userId) throws Exception {
		return sysUserDao.loadById(userId);
	}
	
	@Override
	public List<SysUser> getUserByRoleId(Integer roleId) throws Exception {
		return sysUserDao.loadByRoleId(roleId);
	}

	@Override
	public void insert(SysUser sysUser) throws Exception {
		sysUser.setPassword(MD5.crypt(sysUser.getPassword()));
		sysUserDao.save(sysUser);
	}

	@Override
	public void update(SysUser user) throws Exception {
		sysUserDao.edit(user);
	}

	@Override
	public void delete(SysUser user) throws Exception {
		sysUserDao.remove(user);
	}

	@Override
	public List<SysUser> getListPage(SysUser user, int start, int limit) throws Exception {
		return sysUserDao.loadListPage(user, start, limit);
	}

	@Override
	public Integer getListCount(SysUser user) throws Exception {
		return sysUserDao.loadListCount(user);
	}
	
	
	public SysUserDao getSysUserDao() {
		return sysUserDao;
	}
	@Resource
	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

}

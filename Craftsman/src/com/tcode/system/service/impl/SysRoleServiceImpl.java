package com.tcode.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.system.dao.SysRoleDao;
import com.tcode.system.model.SysRole;
import com.tcode.system.service.SysRoleService;

/**
 * 系统角色service
 * @author Xiashou
 * @since 2016/05/31
 */
@Component("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
	
	private SysRoleDao sysRoleDao;

	@Override
	public List<SysRole> getAll() throws Exception {
		return sysRoleDao.loadAll();
	}
	
	@Override
	public List<SysRole> getAllRoleSimple() throws Exception {
		return sysRoleDao.loadAllSimple();
	}
	
	@Override
	public List<SysRole> getUnlockRole(Integer roleId) throws Exception {
		List<SysRole> roleList = sysRoleDao.loadUnlockRole();
		for(SysRole role : roleList){
			role.setLeaf(true);
			role.setChecked(false);
			role.setText(role.getRoleName());
//			if(roleId.indexOf(role.getRoleName()) >= 0)
//				role.setChecked(true);
		}
		return roleList;
	}
	
	@Override
	public SysRole getRoleById(Integer roleId) throws Exception {
		return sysRoleDao.loadById(roleId);
	}
	
	@Override
	public List<SysRole> getNormalRoleById(Integer roleId) throws Exception {
		return sysRoleDao.loadNormalById(roleId);
	}

	@Override
	public void insert(SysRole role) throws Exception {
		sysRoleDao.save(role);
	}

	@Override
	public void update(SysRole role) throws Exception {
		sysRoleDao.edit(role);
	}

	@Override
	public void delete(SysRole role) throws Exception {
		sysRoleDao.remove(role);
	}
	
	@Override
	public List<SysRole> getListPage(SysRole role, int start, int limit) throws Exception {
		return sysRoleDao.loadListPage(role, start, limit);
	}

	@Override
	public Integer getListCount(SysRole role) throws Exception {
		return sysRoleDao.loadListCount(role);
	}
	
	
	
	public SysRoleDao getSysRoleDao() {
		return sysRoleDao;
	}
	@Resource
	public void setSysRoleDao(SysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
	}

}

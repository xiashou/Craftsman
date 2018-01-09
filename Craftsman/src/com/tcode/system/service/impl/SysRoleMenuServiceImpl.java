package com.tcode.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.tcode.system.dao.SysRoleMenuDao;
import com.tcode.system.model.SysRoleMenu;
import com.tcode.system.service.SysRoleMenuService;

/**
 * 系统角色菜单关联service
 * @author xiashou
 * @since 2015/04/09
 */
@Component("sysRoleMenuService")
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
	
	private static Logger log = Logger.getLogger("SLog");
	
	private SysRoleMenuDao sysRoleMenuDao;

	@Override
	public List<SysRoleMenu> getAll() throws Exception {
		return sysRoleMenuDao.loadAll();
	}
	
	@Override
	public List<SysRoleMenu> getByMenuId(String menuId) throws Exception {
		return sysRoleMenuDao.loadByMenuId(menuId);
	}

	@Override
	public List<SysRoleMenu> getByRoleId(Integer roleId) throws Exception {
		return sysRoleMenuDao.loadByRoleId(roleId);
	}
	
	@Override
	public SysRoleMenu getByRoleIdAndMenuId(Integer roleId, String menuId) throws Exception {
		return sysRoleMenuDao.loadByRoleIdAndMenuId(roleId, menuId);
	}
	
	@Override
	public boolean insert(SysRoleMenu roleMenu) {
		try{
			sysRoleMenuDao.save(roleMenu);
		} catch(Exception e){
			log.error(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean update(SysRoleMenu roleMenu) {
		try{
			sysRoleMenuDao.edit(roleMenu);
		} catch(Exception e){
			log.error(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(SysRoleMenu roleMenu) {
		try{
			sysRoleMenuDao.remove(roleMenu);
		} catch(Exception e){
			log.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	@Override
	public boolean removeByRoleId(Integer roleId) {
		try{
			sysRoleMenuDao.deleteByList(this.getByRoleId(roleId));
		} catch(Exception e){
			log.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	
	
	
	
	public SysRoleMenuDao getSysRoleMenuDao() {
		return sysRoleMenuDao;
	}
	@Resource
	public void setSysRoleMenuDao(SysRoleMenuDao sysRoleMenuDao) {
		this.sysRoleMenuDao = sysRoleMenuDao;
	}

}

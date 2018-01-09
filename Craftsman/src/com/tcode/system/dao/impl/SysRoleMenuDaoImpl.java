package com.tcode.system.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.common.dao.BaseDao;
import com.tcode.system.dao.SysRoleMenuDao;
import com.tcode.system.model.SysRoleMenu;

@Component("sysRoleMenuDao")
public class SysRoleMenuDaoImpl extends BaseDao<SysRoleMenu, Serializable> implements SysRoleMenuDao {

	@Override
	public List<SysRoleMenu> loadAll() throws Exception {
		return super.loadAll();
	}
	
	@Override
	public List<SysRoleMenu> loadByRoleId(final Integer roleId) throws Exception {
		return super.loadList("from SysRoleMenu rm where rm.roleId = ?", roleId);
	}
	
	@Override
	public List<SysRoleMenu> loadByMenuId(String menuId) throws Exception {
		String hql = "from SysRoleMenu rm where rm.menuId = ?";
		return (List<SysRoleMenu>) super.loadList(hql, menuId);
	}
	
	@Override
	public SysRoleMenu loadByRoleIdAndMenuId(Integer roleId, String menuId) throws Exception {
		List<SysRoleMenu> roleMenuList = super.loadList("from SysRoleMenu rm where rm.roleId = ? and rm.menuId = ?", roleId, menuId);
		return roleMenuList.size() > 0 ? roleMenuList.get(0) : null;
	}

	@Override
	public void deleteByList(List<SysRoleMenu> roleMenuList) throws Exception {
		super.getHibernateTemplate().deleteAll(roleMenuList);
	}

}

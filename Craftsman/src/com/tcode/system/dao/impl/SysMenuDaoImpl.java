package com.tcode.system.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.common.dao.BaseDao;
import com.tcode.system.dao.SysMenuDao;
import com.tcode.system.model.SysMenu;

@Component("sysMenuDao")
public class SysMenuDaoImpl extends BaseDao<SysMenu, Serializable> implements SysMenuDao {

	@Override
	public List<SysMenu> loadAll() throws Exception {
		return super.loadAll();
	}
	
	@Override
	public SysMenu loadById(String id) throws Exception {
		return super.loadById(SysMenu.class, id);
	}

	@Override
	public List<SysMenu> loadByParentId(String id) throws Exception {
		return super.loadList("from SysMenu m where m.parentId = ? order by m.sortNo", id);
	}
	
	@Override
	public List<SysMenu> loadByMenuType(String parentId, String menuType) throws Exception {
		return super.loadList("from SysMenu m where m.parentId = ? and m.menuType = ? order by m.sortNo", parentId, menuType);
	}
	
	@Override
	public List<SysMenu> loadByParentIdAndRoleId(String parentId, Integer roleId, String menuType) throws Exception {
		if(roleId < 10)
			return this.loadByMenuType(parentId, menuType);
		else
			return super.loadList("select distinct m from SysMenu m, SysRoleMenu rm "
				+ "where m.id = rm.menuId and m.parentId = ? and m.menuType = ? and rm.roleId = ? order by m.sortNo", parentId, menuType, roleId);
	}
	
	@Override
	public List<SysMenu> loadByAgent(String parentId) throws Exception {
		return super.loadList("from SysMenu m where m.parentId = ? and m.menuType = '0' and m.id < '010104' order by m.sortNo", parentId);
	}
	
	@Override
	public List<SysMenu> loadListPage(SysMenu menu, int start, int limit) throws Exception {
		List<SysMenu> menuList = null;
		DetachedCriteria criteria = connectionCriteria(menu);  
		criteria.addOrder(Order.asc("id"));
		menuList = (List<SysMenu>) super.loadListForPage(criteria, start, limit);
		return menuList;
	}
	
	@Override
	public Integer loadListCount(SysMenu menu) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(menu);  
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	@Override
	public String loadMaxIdByParentId(String parentId) throws Exception {
		String hql = "select max(m.id) from SysMenu m where m.parentId = '" + parentId + "'";
		String maxId = (String) super.loadUniqueResult(hql);
		return maxId;
	}
	
	@Override
	public String loadParentIdById(String id) throws Exception {
		String hql = "select m.parentId from SysMenu m where m.id = '" + id + "'";
		return (String) super.loadUniqueResult(hql);
	}
	
	public DetachedCriteria connectionCriteria(SysMenu menu){
		DetachedCriteria criteria = DetachedCriteria.forClass(SysMenu.class);  
		//添加查询条件
		if(menu != null){
			if(menu.getParentId() != null)
				criteria.add(Restrictions.like("id", menu.getParentId(), MatchMode.START));
		}
		return criteria;
	}
}

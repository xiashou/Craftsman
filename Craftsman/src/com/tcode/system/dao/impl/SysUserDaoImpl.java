package com.tcode.system.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;
import com.tcode.system.dao.SysUserDao;
import com.tcode.system.model.SysUser;

@Component("sysUserDao")
public class SysUserDaoImpl extends BaseDao<SysUser, Serializable> implements SysUserDao {

	@Override
	public List<SysUser> loadAll() throws Exception {
		return super.loadAll();
	}
	
	@Override
	public SysUser loadById(Integer userId) throws Exception {
		return super.loadById(SysUser.class, userId);
	}
	
	@Override
	public SysUser loadByName(String userName) throws Exception {
		List<SysUser> userList = super.loadList("from SysUser u where u.userName = ?", userName);
		return userList.size() > 0 ? userList.get(0) : null;
	}
	
	@Override
	public SysUser loadByMgrName(String userName) throws Exception {
		List<SysUser> userList = super.loadList("select u from SysUser u, SysDept d where u.deptId = d.id and u.userName = ? and d.deptType = 1", userName);
		return userList.size() > 0 ? userList.get(0) : null;
	}
	
	@Override
	public List<SysUser> loadByRoleId(Integer roleId) throws Exception {
		return super.loadList("from SysUser u where u.roleId = ?", roleId);
	}

	@Override
	public List<SysUser> loadListPage(SysUser user, int start, int limit) throws Exception {
		List<SysUser> userList = null;
		DetachedCriteria criteria = connectionCriteria(user);
		criteria.addOrder(Order.asc("userId"));
		userList = (List<SysUser>) super.loadListForPage(criteria, start, limit);
		return userList;
	}

	@Override
	public Integer loadListCount(SysUser user) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(user);  
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(SysUser user){
		DetachedCriteria criteria = DetachedCriteria.forClass(SysUser.class);  
		if(user != null){
			if(user.getUserId() != null)
				criteria.add(Restrictions.eq("userId", user.getUserId()));
			if(!Utils.isEmpty(user.getDeptId()))
				criteria.add(Restrictions.like("deptId", user.getDeptId(), MatchMode.START));
			//代理登录时有areaId参数
			if(!Utils.isEmpty(user.getAreaId()))
				criteria.add(Restrictions.sqlRestriction("this_.dept_id in (select id from sys_dept where area_id like '" + user.getAreaId() + "%')"));
		}	
		return criteria;
	}

}

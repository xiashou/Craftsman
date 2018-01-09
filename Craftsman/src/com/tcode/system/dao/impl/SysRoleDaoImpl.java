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
import com.tcode.system.dao.SysRoleDao;
import com.tcode.system.model.SysRole;

@Component("sysRoleDao")
public class SysRoleDaoImpl extends BaseDao<SysRole, Serializable> implements SysRoleDao {

	@Override
	public List<SysRole> loadAll() throws Exception {
		return super.loadAll();
	}
	
	@Override
	public SysRole loadById(Integer roleId) throws Exception {
		return super.loadById(SysRole.class, roleId);
	}
	
	@Override
	public List<SysRole> loadAllSimple() throws Exception {
		return super.loadList("select new SysRole(r.roleId, r.roleName) from SysRole r");
	}
	
	@Override
	public List<SysRole> loadUnlockRole() throws Exception {
		return super.loadList("from SysRole r where r.locked = 0");
	}
	
	@Override
	public List<SysRole> loadNormalById(final Integer roleId) throws Exception {
//		return (List<SysRole>) getHibernateTemplate().execute(new HibernateCallback() {
//		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
//		    	Query query = session.createQuery("from SysRole r where r.locked = '0' and r.id = :roleId"); 
////		    	query.setParameterList("roleIds", roleIds.indexOf(",") > 0 ? roleIds.trim().split(",") : new String[]{roleIds});
//		    	query.setParameter("roleId", roleId);
//	            return query.list();
//	        }
//	    });
		return super.loadList("from SysRole r where r.locked = '0' and r.roleId = ?", roleId);
	}
	
	@Override
	public List<SysRole> loadListPage(SysRole role, int start, int limit) throws Exception {
		List<SysRole> roleList = null;
		DetachedCriteria criteria = connectionCriteria(role);
		criteria.addOrder(Order.asc("roleId"));
		roleList = (List<SysRole>) super.loadListForPage(criteria, start, limit);
		return roleList;
	}
	
	@Override
	public Integer loadListCount(SysRole role) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(role);  
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(SysRole role){
		DetachedCriteria criteria = DetachedCriteria.forClass(SysRole.class);  
		if(role != null){
			if(role.getRoleId() != null)
				criteria.add(Restrictions.eq("roleId", role.getRoleId()));
			if(!Utils.isEmpty(role.getDeptId()))
				criteria.add(Restrictions.like("deptId", role.getDeptId(), MatchMode.START));
			if(!Utils.isEmpty(role.getAreaId()))
				criteria.add(Restrictions.sqlRestriction("this_.dept_id in (select id from sys_dept where area_id like '" + role.getAreaId() + "%')"));
		}
		return criteria;
	}

}

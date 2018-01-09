package com.tcode.system.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;
import com.tcode.system.dao.SysButtonDao;
import com.tcode.system.model.SysButton;

@Component("sysButtonDao")
public class SysButtonDaoImpl extends BaseDao<SysButton, Serializable> implements SysButtonDao {

	@Override
	public SysButton loadById(Integer btnId) throws Exception {
		return super.loadById(SysButton.class, btnId);
	}
	
	@Override
	public List<SysButton> loadByMenuId(String id) throws Exception {
		return super.loadList("from SysButton b where b.menuId = ?", id);
	}
	
	/**
	 * 根据菜单ID和角色ID查找按钮
	 * 调用自定义函数F_Split分割按钮字符串
	 */
	@Override
	public List<SysButton> loadByMenuIdAndRoleId(String id, Integer roleId) throws Exception {
//		if(roleId < 10)
//			return this.loadByMenuId(id);
//		else
//			return super.loadListBySql("select btn_id as btnId, btn_key as btnKey, btn_name as btnName, menu_id as menuId "
//				+ "from sys_button where menu_id = ? and btn_key in "
//				+ "(select * from F_Split((select buttons from sys_role_menu where menu_id = ? and role_id = ?),','));", 
//				SysButton.class, id, id, roleId);
		return this.loadByMenuId(id);
	}

	@Override
	public List<SysButton> loadListPage(SysButton button, int start, int limit) throws Exception {
		List<SysButton> roleList = null;
		DetachedCriteria criteria = connectionCriteria(button);
		criteria.addOrder(Order.asc("btnId"));
		roleList = (List<SysButton>) super.loadListForPage(criteria, start, limit);
		return roleList;
	}

	@Override
	public Integer loadListCount(SysButton button) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(button);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(SysButton button){
		DetachedCriteria criteria = DetachedCriteria.forClass(SysButton.class);  
		if(button != null){
			if(button.getBtnId() != null)
				criteria.add(Restrictions.eq("btnId", button.getBtnId()));
			if(!Utils.isEmpty(button.getMenuId()))
				criteria.add(Restrictions.eq("menuId", button.getMenuId()));
		}
		return criteria;
	}

}

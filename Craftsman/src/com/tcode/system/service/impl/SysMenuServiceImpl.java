package com.tcode.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.core.util.Utils;
import com.tcode.system.dao.SysButtonDao;
import com.tcode.system.dao.SysMenuDao;
import com.tcode.system.dao.SysRoleMenuDao;
import com.tcode.system.model.SysButton;
import com.tcode.system.model.SysMenu;
import com.tcode.system.model.SysRoleMenu;
import com.tcode.system.service.SysMenuService;

/**
 * 系统菜单service
 * @author Xiashou
 * @since 2016/05/29
 */
@Component("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
	
	private SysMenuDao sysMenuDao;
	private SysRoleMenuDao roleMenuDao;
	private SysButtonDao sysButtonDao;

	@Override
	public List<SysMenu> getAll() throws Exception {
		return sysMenuDao.loadAll();
	}

	@Override
	public SysMenu getMenuById(String id) throws Exception {
		return sysMenuDao.loadById(id);
	}

	@Override
	public List<SysMenu> getMenuByParentId(String parentId) throws Exception {
		return sysMenuDao.loadByParentId(parentId);
	}
	
	@Override
	public List<SysMenu> getMenuByMenuType(String parentId, String menuType) throws Exception {
		return sysMenuDao.loadByMenuType(parentId, menuType);
	}
	
	@Override
	public List<SysMenu> getMenuByParentIdAndRoleId(String parentId, Integer roleId, String menuType) throws Exception {
		return sysMenuDao.loadByParentIdAndRoleId(parentId, roleId, menuType);
	}
	
	@Override
	public List<SysMenu> getMenuByAgent(String parentId) throws Exception {
		return sysMenuDao.loadByAgent(parentId);
	}

	/**
	 * 添加菜单时需根据父ID生成ID
	 * 如上级菜单为叶子菜单，则ID为父ID+"01"
	 * 如上级菜单为非叶子菜单，见方法 getMenuId()
	 */
	@Override
	public void insert(SysMenu menu) throws Exception {
		StringBuffer id = new StringBuffer();
		SysMenu parentMenu = sysMenuDao.loadById(menu.getParentId());
		if(parentMenu.getLeaf()){
			id.append(parentMenu.getId()).append("01");
			parentMenu.setLeaf(false);
			sysMenuDao.edit(parentMenu);
		} else
			id.append(getMenuId(menu.getParentId()));
		menu.setId(id.toString());
		sysMenuDao.save(menu);
	}
	
	/**
	 * 更新菜单
	 * 先判断是否有更改父菜单，没有更改父菜单则直接修改，
	 * 更改过父菜单，则先删除原菜单，在重新生成新菜单
	 * @return 
	 */
	@Override
	public void update(SysMenu menu) throws Exception {
		SysMenu oldMenu = sysMenuDao.loadById(menu.getId());
		if(menu.getParentId().equals(oldMenu.getParentId())){
			sysMenuDao.edit(menu);
		} else {
			List<SysMenu> menuList = sysMenuDao.loadByParentId(oldMenu.getParentId());
			if(menuList.size() == 1){
				SysMenu parentMenu = sysMenuDao.loadById(oldMenu.getParentId());
				parentMenu.setLeaf(true);
				sysMenuDao.edit(parentMenu);
			}
			sysMenuDao.remove(oldMenu);
			this.insert(menu);
		}
	}

	/**
	 * 删除菜单时，若其父菜单下只有一个子菜单，
	 * 则先将父菜单设为叶子节点菜单，再删除子菜单
	 */
	@Override
	public void delete(SysMenu menu) throws Exception {
		int count = this.getListCount(menu);
		if(count == 2){
			SysMenu pMenu = new SysMenu();
			pMenu = this.getMenuById(menu.getParentId());
			pMenu.setLeaf(true);
			this.update(pMenu);
			sysMenuDao.remove(menu);
		} else 
			sysMenuDao.remove(menu);
	}
	
	/**
	 * 根据传入的菜单List 递归完成其下所有子菜单
	 * 用于菜单资源管理，构建树
	 * 传入List为顶级菜单时， 则构建全部树菜单
	 */
	@Override
	public void completeMenuTree(List<SysMenu> menuList) throws Exception {
		for(SysMenu menu : menuList){
			menu.setText(menu.getMenuName());
			if(!menu.getLeaf()){
				List<SysMenu> childMenu = getMenuByParentId(menu.getId());
				menu.setChildren(childMenu);
				completeMenuTree(childMenu);
			}
		}
	}
	
	/**
	 * 递归完成全部菜单包括菜单所包含的按钮，且该菜单带有checkbox选择框
	 * 根据传入角色ID，选中该角色拥有的所有菜单
	 */
	@Override
	public void completeAuthorizationTree(Integer curRoleId, Integer roleId, String menuType, List<SysMenu> menuList) throws Exception {
		for(SysMenu menu : menuList){
			menu.setText(menu.getMenuName());
			menu.setChecked(false);
			menu.setExpanded(true);
			SysRoleMenu roleMenu = roleMenuDao.loadByRoleIdAndMenuId(roleId, menu.getId());
			//顶层菜单默认选中
			if(roleMenu != null || "0".equals(menu.getParentId()))
				menu.setChecked(true);
			//非叶子节点则递归查询
			if(!menu.getLeaf()){
//				List<SysMenu> childMenu = getMenuByParentIdAndRoleId(menu.getId(), curRoleId, menuType);
//				List<SysMenu> childMenu = getMenuByMenuType(menu.getId(), menuType);
				List<SysMenu> childMenu = null;
				if("0".equals(menuType))
					childMenu = getMenuByParentIdAndRoleId(menu.getId(), curRoleId, menuType);
				else
					childMenu = getMenuByMenuType(menu.getId(), menuType);
				menu.setChildren(childMenu);
				completeAuthorizationTree(curRoleId, roleId, menuType, childMenu);
			//叶子节点则查询相应按钮，有按钮则插入按钮，并根据权限选中
			} else {
				List<SysButton> buttonList = sysButtonDao.loadByMenuIdAndRoleId(menu.getId(), curRoleId);
				if(!Utils.isEmpty(buttonList)){
					List<SysMenu> childBtn = new ArrayList<SysMenu>();
					for(SysButton button : buttonList){
						SysMenu btnMenu = new SysMenu();
						if(!Utils.isEmpty(roleMenu) && !Utils.isEmpty(roleMenu.getButtons())){
							if(roleMenu.getButtons().indexOf(button.getBtnKey()) > -1)
								btnMenu.setChecked(true);
							else
								btnMenu.setChecked(false);
						} else
							btnMenu.setChecked(false);
						btnMenu.setLeaf(true);
						btnMenu.setExpanded(true);
						btnMenu.setChildren(null);
						//按钮的菜单ID为 菜单的ID+下划线+按钮的key(重要)
						btnMenu.setId(menu.getId() + "_" + button.getBtnKey());
						btnMenu.setText(button.getBtnName());
						btnMenu.setMenuName(button.getBtnName());
						childBtn.add(btnMenu);
					}
					menu.setChildren(childBtn);
					menu.setLeaf(false);
				}
			}
		}
	}
	
	/**
	 * 递归完成该角色拥有的所有菜单
	 * 用于后台首页显示
	 * 每个菜单的按钮为该角色拥有的按钮
	 */
	@Override
	public void completeRoleTree(Integer roleId, List<SysMenu> menuList, String menuType) throws Exception {
		for(SysMenu menu : menuList){
			menu.setText(menu.getMenuName());
			if(!menu.getLeaf()){
				List<SysMenu> childMenu = getMenuByParentIdAndRoleId(menu.getId(), roleId, menuType);
				menu.setChildren(childMenu);
				completeRoleTree(roleId, childMenu, menuType);
			} else {
				SysRoleMenu roleMenu = roleMenuDao.loadByRoleIdAndMenuId(roleId, menu.getId());
				if(!Utils.isEmpty(roleMenu))
					menu.setButtons(roleMenu.getButtons());
			}
		}
	}
	
	@Override
	public void completeAgentTree(String parentId, List<SysMenu> menuList) throws Exception {
		for(SysMenu menu : menuList){
			menu.setText(menu.getMenuName());
			if(!menu.getLeaf()){
				List<SysMenu> childMenu = getMenuByAgent(parentId);
				menu.setChildren(childMenu);
				completeAgentTree(menu.getId(), childMenu);
			}
		}
	}
	
	/**
	 * 根据父ID查询最大子ID，然后转整 +1(不足两位补0)，生成新的ID
	 * @param parentId
	 * @return 新ID
	 * @throws Exception
	 */
	public String getMenuId(String parentId) throws Exception {
		String maxIdStr = sysMenuDao.loadMaxIdByParentId(parentId);
		int maxId = Integer.parseInt(maxIdStr.substring(maxIdStr.length() - 2)) + 1;
		if(maxId < 10)
			return parentId + "0" + maxId;
		else 
			return parentId + maxId;
	}
	
	@Override
	public List<SysMenu> getListPage(SysMenu menu, int start, int limit) throws Exception {
		return sysMenuDao.loadListPage(menu, start, limit);
	}
	
	@Override
	public Integer getListCount(SysMenu menu) throws Exception{
		return sysMenuDao.loadListCount(menu);
	}

	public SysMenuDao getSysMenuDao() {
		return sysMenuDao;
	}
	@Resource
	public void setSysMenuDao(SysMenuDao sysMenuDao) {
		this.sysMenuDao = sysMenuDao;
	}
	public SysRoleMenuDao getRoleMenuDao() {
		return roleMenuDao;
	}
	@Resource
	public void setRoleMenuDao(SysRoleMenuDao roleMenuDao) {
		this.roleMenuDao = roleMenuDao;
	}
	public SysButtonDao getSysButtonDao() {
		return sysButtonDao;
	}
	@Resource
	public void setSysButtonDao(SysButtonDao sysButtonDao) {
		this.sysButtonDao = sysButtonDao;
	}

}

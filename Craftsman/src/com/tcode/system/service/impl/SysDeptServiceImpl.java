package com.tcode.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tcode.business.finance.dao.AssetsDao;
import com.tcode.business.finance.model.Assets;
import com.tcode.business.shop.dao.SettingDao;
import com.tcode.business.shop.model.Setting;
import com.tcode.common.idgenerator.IdGenerator;
import com.tcode.core.util.Utils;
import com.tcode.system.dao.SysDeptDao;
import com.tcode.system.model.SysDept;
import com.tcode.system.model.SysRole;
import com.tcode.system.service.SysDeptService;

/**
 * 系统组织架构service
 * @author Xiashou
 * @since 2016/05/29
 */
@Component("sysDeptService")
public class SysDeptServiceImpl implements SysDeptService {
	
	private SysDeptDao sysDeptDao;
	private AssetsDao assetsDao;
	private SettingDao settingDao;

	@Override
	public List<SysDept> getAll() throws Exception {
		return sysDeptDao.loadAll();
	}
	
	@Override
	public List<SysDept> getAllSimple() throws Exception {
		return sysDeptDao.loadAllSimple();
	}
	
	@Override
	public SysDept getById(String id) throws Exception {
		return sysDeptDao.loadById(id);
	}
	
	@Override
	public SysDept getByDeptCode(String deptCode) throws Exception {
		return sysDeptDao.loadByDeptCode(deptCode);
	}
	
	@Override
	public List<SysDept> getByCompanyId(String companyId) throws Exception {
		List<SysDept> list = sysDeptDao.loadByCompanyId(companyId);
		SysDept sysDept = null;
		for (int i = 0; i < list.size(); i++) {
			sysDept = list.get(i);
			Setting setting = settingDao.loadById(sysDept.getDeptCode());
			if(!Utils.isEmpty(setting)){
				sysDept.setPhoneNumber(Utils.isEmpty(setting.getPhone()) ? "" : setting.getPhone());
				sysDept.setAddress(Utils.isEmpty(setting.getAddress()) ? "" : setting.getAddress());
			}
		}
		return list;
	}
	
	@Override
	public SysDept getCompanyById(String parentId) throws Exception {
		SysDept parent = sysDeptDao.loadById(parentId);
		if(parent.getDeptType() == 1)
			return parent;
		else
			return getCompanyById(parent.getParentId());
	}
	
	@Override
	public List<SysDept> getByParentId(String id) throws Exception {
		List<SysDept> list = sysDeptDao.loadByParentId(id);
		for(int i = 0; i < list.size(); i++){
			list.get(i).setText(list.get(i).getDeptName());
			List<SysDept> childList = sysDeptDao.loadByParentId(list.get(i).getId());
			if(!Utils.isEmpty(childList)){
				this.completeDeptTree(childList);
				list.get(i).setChildren(childList);
				list.get(i).setLeaf(false);
				list.get(i).setExpanded(true);
			} else {
				list.get(i).setChildren(null);
				list.get(i).setLeaf(true);
				list.get(i).setExpanded(false);
			}
		}
		return list;
	}
	
	@Override
	public List<SysDept> getByParentIdAndArea(String id, String areaId) throws Exception {
		List<SysDept> list = sysDeptDao.loadByParentIdAndArea(id, areaId);
		for(int i = 0; i < list.size(); i++){
			list.get(i).setText(list.get(i).getDeptName());
			List<SysDept> childList = sysDeptDao.loadByParentIdAndArea(list.get(i).getId(), areaId);
			if(!Utils.isEmpty(childList)){
				this.completeDeptTree(childList);
				list.get(i).setChildren(childList);
				list.get(i).setLeaf(false);
				list.get(i).setExpanded(true);
			} else {
				list.get(i).setChildren(null);
				list.get(i).setLeaf(true);
				list.get(i).setExpanded(false);
			}
		}
		return list;
	}
	
	@Override
	public void completeDeptTree(List<SysDept> deptList) throws Exception {
		for(SysDept dept : deptList){
			dept.setText(dept.getDeptName());
			List<SysDept> childDept = sysDeptDao.loadByParentId(dept.getId());
			if(!Utils.isEmpty(childDept)){
				dept.setChildren(childDept);
				dept.setLeaf(false);
				dept.setExpanded(true);
				completeDeptTree(childDept);
			} else {
				dept.setLeaf(true);
				dept.setExpanded(false);
			}
		}
	}
	
	public List<SysDept> completeRoleTree(List<SysRole> roleList) throws Exception {
		List<SysDept> deptList = sysDeptDao.loadByParentId("1");
		for(SysDept dept : deptList){
			dept.setText(dept.getDeptName());
			List<SysDept> childDept = sysDeptDao.loadByParentId(dept.getId());
			if(!Utils.isEmpty(childDept)){
				dept.setChildren(childDept);
				dept.setLeaf(false);
				dept.setExpanded(true);
				completeDeptTree(childDept);
			} else {
				dept.setLeaf(true);
				dept.setExpanded(false);
			}
		}
		return null;
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void insert(SysDept dept) throws Exception {
		StringBuffer id = new StringBuffer();
		List<SysDept> parentList = sysDeptDao.loadByParentId(dept.getParentId());
		if(Utils.isEmpty(parentList))
			id.append(dept.getParentId()).append("000001");
		else
			id.append(IdGenerator.getDeptIdGenerator(dept.getParentId()));
		dept.setId(id.toString());
		dept.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		if(dept.getDeptType() != 1) 
			dept.setCompanyId(this.getCompanyById(dept.getParentId()).getId());
		else
			dept.setCompanyId(id.toString());
		sysDeptDao.save(dept);
		
		//初始资产
		if(dept.getDeptType() == 3){
			Assets wechatAssets = new Assets(dept.getDeptCode(), "W", "微信", 0.0, 0);
			Assets alipayAssets = new Assets(dept.getDeptCode(), "A", "支付宝", 0.0, 0);
			Assets bankAssets = new Assets(dept.getDeptCode(), "B", "银行卡", 0.0, 0);
			Assets cashAssets = new Assets(dept.getDeptCode(), "C", "现金", 0.0, 0);
			assetsDao.save(wechatAssets);
			assetsDao.save(alipayAssets);
			assetsDao.save(bankAssets);
			assetsDao.save(cashAssets);
		}
	}
	
	@Override
	public String getMaxIdByParentId(String parentId) throws Exception {
		return sysDeptDao.loadMaxIdByParentId(parentId);
	}
	
	@Override
	public void update(SysDept dept) throws Exception {
		sysDeptDao.remove(dept);
		StringBuffer id = new StringBuffer();
		List<SysDept> parentList = sysDeptDao.loadByParentId(dept.getParentId());
		if(Utils.isEmpty(parentList))
			id.append(dept.getParentId()).append("000001");
		else
			id.append(IdGenerator.getDeptIdGenerator(dept.getParentId()));
		dept.setId(id.toString());
		dept.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		if(dept.getDeptType() != 1) 
			dept.setCompanyId(this.getCompanyById(dept.getParentId()).getId());
		else
			dept.setCompanyId(id.toString());
		sysDeptDao.save(dept);
	}
	@Override
	public void delete(SysDept dept) throws Exception {
		sysDeptDao.remove(dept);
	}
	@Override
	public List<SysDept> getListPage(SysDept area, int start, int limit) throws Exception {
		return sysDeptDao.loadListPage(area, start, limit);
	}
	@Override
	public Integer getListCount(SysDept area) throws Exception {
		return sysDeptDao.loadListCount(area);
	}
	
	public SysDeptDao getSysDeptDao() {
		return sysDeptDao;
	}
	@Resource
	public void setSysDeptDao(SysDeptDao sysDeptDao) {
		this.sysDeptDao = sysDeptDao;
	}
	public AssetsDao getAssetsDao() {
		return assetsDao;
	}
	@Resource
	public void setAssetsDao(AssetsDao assetsDao) {
		this.assetsDao = assetsDao;
	}

	public SettingDao getSettingDao() {
		return settingDao;
	}
	@Resource
	public void setSettingDao(SettingDao settingDao) {
		this.settingDao = settingDao;
	}

}

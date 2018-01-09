package com.tcode.system.service;

import java.util.List;

import com.tcode.system.model.SysDept;

public interface SysDeptService {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<SysDept> getAll() throws Exception;
	
	/**
	 * 查询所有简略信息
	 * @return
	 * @throws Exception
	 */
	public List<SysDept> getAllSimple() throws Exception;
	
	/**
	 * 根据ID查询
	 * @return
	 * @throws Exception
	 */
	public SysDept getById(String id) throws Exception;
	
	/**
	 * 根据ParentId模糊查询
	 * @return
	 * @throws Exception
	 */
	public List<SysDept> getByParentId(String id) throws Exception;

	/**
	 * 根据ParentId和areaId模糊查询
	 * @param id
	 * @param areaId
	 * @return
	 * @throws Exception
	 */
	public List<SysDept> getByParentIdAndArea(String id, String areaId) throws Exception;
	
	/**
	 * 根据companyId查询
	 * @return
	 * @throws Exception
	 */
	public List<SysDept> getByCompanyId(String companyId) throws Exception;
	
	/**
	 * 根据部门编码查询
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public SysDept getByDeptCode(String deptCode) throws Exception;
	
	/**
	 * 根据id递归查找上级公司id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SysDept getCompanyById(String id) throws Exception;
	
	/**
	 * 根据ParentId查询最大ID
	 * @return
	 * @throws Exception
	 */
	public String getMaxIdByParentId(String parentId) throws Exception;
	
	/**
	 * 递归完成常规区域
	 * @param areaList
	 * @throws Exception
	 */
	public void completeDeptTree(List<SysDept> deptList) throws Exception;
	
	/**
	 * 添加
	 * @param SysDept
	 * @throws Exception
	 */
	public void insert(SysDept dept) throws Exception;
	
	/**
	 * 修改
	 * @param SysDept
	 * @throws Exception
	 */
	public void update(SysDept dept) throws Exception;
	
	/**
	 * 删除
	 * @param SysDept
	 * @throws Exception
	 */
	public void delete(SysDept dept) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param menu, start,limit
	 * @return List<SysDept>
	 * @throws Exception
	 */
	public List<SysDept> getListPage(SysDept dept, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param area
	 * @return List<SysDept>
	 * @throws Exception
	 */
	public Integer getListCount(SysDept dept) throws Exception;
}

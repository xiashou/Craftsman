package com.tcode.system.dao;

import java.util.List;

import com.tcode.system.model.SysDept;

public interface SysDeptDao {

	/**
	 * 查询所有
	 * @return List<SysDept>
	 * @throws Exception
	 */
	public List<SysDept> loadAll() throws Exception;
	
	/**
	 * 查询所有部门简略信息
	 * @return List<SysDept>
	 * @throws Exception
	 */
	public List<SysDept> loadAllSimple() throws Exception;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return SysDept
	 * @throws Exception
	 */
	public SysDept loadById(String id) throws Exception;
	
	/**
	 * 根据parentId查找
	 * @param id
	 * @return SysDept
	 * @throws Exception
	 */
	public List<SysDept> loadByParentId(String id) throws Exception;
	
	/**
	 * 根据parentId和areaId模糊查找
	 * @param id
	 * @param areaId
	 * @return
	 * @throws Exception
	 */
	public List<SysDept> loadByParentIdAndArea(String id, String areaId) throws Exception;
	
	/**
	 * 根据companyId查找
	 * @param companyId
	 * @return SysDept
	 * @throws Exception
	 */
	public List<SysDept> loadByCompanyId(String companyId) throws Exception;
	
	/**
	 * 根据部门代码查询部门
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public SysDept loadByDeptCode(String deptCode) throws Exception;
	
	/**
	 * 根据parentId查找最大子ID
	 * @param parentId
	 * @return SysDept
	 * @throws Exception
	 */
	public String loadMaxIdByParentId(String parentId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(SysDept dept) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(SysDept dept) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(SysDept dept) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param dept, start,limit
	 * @return List<SysDept>
	 * @throws Exception
	 */
	public List<SysDept> loadListPage(SysDept dept, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param dept
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(SysDept dept) throws Exception;
	
}

package com.tcode.system.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysDept;
import com.tcode.system.service.SysDeptService;

@Scope("prototype")
@Component("sysDeptAction")
public class SysDeptAction extends BaseAction {

	private static final long serialVersionUID = 3670338550996532718L;
	private static Logger log = Logger.getLogger("SLog");
	
	private SysDeptService sysDeptService;
	
	private SysDept dept;
	private String parentId;
	private String areaId;
	private List<SysDept> deptList;
	private List<SysDept> treeList;
	
	/**
	 * 获取普通树结构区域
	 * @return
	 * @throws Exception
	 */
	public String queryDeptTree() {
		try {
			if(Utils.isEmpty(treeList))
				treeList = new ArrayList<SysDept>();
			dept = sysDeptService.getById(parentId);
			dept.setText(dept.getDeptName());
			dept.setExpanded(true);
			if(!Utils.isEmpty(areaId))
				dept.setChildren(sysDeptService.getByParentIdAndArea(dept.getId(), areaId));
			else
				dept.setChildren(sysDeptService.getByParentId(dept.getId()));
			treeList.add(dept);
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询所有部门简略信息
	 * @return
	 */
	public String queryAllDeptSimple() {
		try {
			deptList = sysDeptService.getAllSimple();
		} catch(Exception e){
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 分页查询系统区域
	 * @return
	 */
	public String querySysDeptPage() {
		try {
			this.setTotalCount(sysDeptService.getListCount(dept));
			deptList = sysDeptService.getListPage(dept, this.getStart(), this.getLimit());
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 获取公司下面所有门店（删除当前店铺）
	 * @return
	 * @throws Exception
	 */
	public String querySysDeptByCompany() {
		try {
			SysDept dept = (SysDept) super.getRequest().getSession().getAttribute(SESSION_DEPT);
			deptList = sysDeptService.getByCompanyId(dept.getCompanyId());
			//删除当前店铺
			for (Iterator<SysDept> it = deptList.iterator(); it.hasNext();) {
				SysDept sysDept = it.next();
				if (sysDept.getId().equals(dept.getId())) {
		        	it.remove();
				}
			}
			this.setResult(true, "");
		} catch (Exception e) {
			this.setResult(false, "查询出错！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 获取公司下面所有门店
	 * @return
	 * @throws Exception
	 */
	public String queryAllSysDeptByCompany() {
		try {
			SysDept dept = (SysDept) super.getRequest().getSession().getAttribute(SESSION_DEPT);
			deptList = sysDeptService.getByCompanyId(dept.getCompanyId());
			this.setResult(true, "");
		} catch (Exception e) {
			this.setResult(false, "查询出错！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertSysDept() {
		try {
			if(!Utils.isEmpty(dept)){
				SysDept parent = sysDeptService.getById(dept.getParentId());
				if(parent.getDeptType() != 3) {
					sysDeptService.insert(dept);
					this.setResult(true, "添加成功！");
				} else 
					this.setResult(false, "上级组织类型不能为店铺！");
			}
		} catch(Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 编辑
	 * @return
	 */
	public String updateSysDept() {
		try {
			sysDeptService.update(dept);
			this.setResult(true, "修改成功！");
		} catch(Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteSysDept() {
		try {
			if(!Utils.isEmpty(dept) && !Utils.isEmpty(dept.getId())){
				sysDeptService.delete(dept);
				this.setResult(true, "删除成功！");
			} else
				this.setResult(false, "ID不能为空！");
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	

	
	public SysDeptService getSysDeptService() {
		return sysDeptService;
	}
	@Resource
	public void setSysDeptService(SysDeptService sysDeptService) {
		this.sysDeptService = sysDeptService;
	}
	public SysDept getDept() {
		return dept;
	}
	public void setDept(SysDept dept) {
		this.dept = dept;
	}
	public List<SysDept> getDeptList() {
		return deptList;
	}
	public void setDeptList(List<SysDept> deptList) {
		this.deptList = deptList;
	}
	public List<SysDept> getTreeList() {
		return treeList;
	}
	public void setTreeList(List<SysDept> treeList) {
		this.treeList = treeList;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

}

package com.tcode.business.shop.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.order.model.OrderItem;
import com.tcode.business.shop.model.Employee;
import com.tcode.business.shop.service.EmployeeService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("employeeAction")
public class EmployeeAction extends BaseAction {

	private static final long serialVersionUID = 8206852408865695361L;
	private static Logger log = Logger.getLogger("SLog");
	
	private EmployeeService employeeService;
	
	private List<Employee> employeeList;
	private Employee employee;
	private OrderItem orderItem;
	
	
	
	/**
	 * 查询部门所有员工和员工组
	 * @return
	 */
	public String queryAllEmployeeByDept() {
		try {
			employeeList = employeeService.getListByDept(this.getDept().getDeptCode());
			this.setResult(true, "");
		} catch(Exception e) {
			this.setResult(false, "查询失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	/**
	 * 查询部门所有员工
	 * @return
	 */
	public String queryEmployeeByDept() {
		try {
			employeeList = employeeService.getListByGroup(this.getDept().getDeptCode(), false);
			this.setResult(true, "");
		} catch(Exception e) {
			this.setResult(false, "查询失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询部门所有员工组
	 * @return
	 */
	public String queryEmployeeGroupByDept() {
		try {
			employeeList = employeeService.getListByGroup(this.getDept().getDeptCode(), true);
			this.setResult(true, "");
		} catch(Exception e) {
			this.setResult(false, "查询失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 计算员工提成
	 * @return
	 */
	public String queryEmployeeCommission() {
		try {
			if(Utils.isEmpty(orderItem))
				orderItem = new OrderItem();
			orderItem.setDeptCode(this.getDept().getDeptCode());
			employeeList = employeeService.getCommissionList(orderItem);
			this.setResult(true, "");
		} catch(Exception e) {
			this.setResult(false, "查询失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加区域信息
	 * @return
	 */
	public String insertEmployee() {
		try {
			if(!Utils.isEmpty(employee)){
				employee.setDeptCode(this.getDept().getDeptCode());
				employee.setLastDate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
				employeeService.insert(employee);
				this.setResult(true, "添加成功！");
			}
		} catch (Exception e){
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改区域信息
	 * @return
	 */
	public String updateEmployee() {
		try {
			if(!Utils.isEmpty(employee) && !Utils.isEmpty(employee.getId())){
				employee.setDeptCode(this.getDept().getDeptCode());
				employee.setLastDate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
				employeeService.update(employee);
				this.setResult(true, "修改成功！");
			}
		} catch (Exception e){
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteEmployee() {
		try {
			if(!Utils.isEmpty(employee) && !Utils.isEmpty(employee.getId())){
				employeeService.delete(employee);
				this.setResult(true, "删除成功！");
			}
		} catch (Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public EmployeeService getEmployeeService() {
		return employeeService;
	}
	@Resource
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	public List<Employee> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
}

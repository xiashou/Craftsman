package com.tcode.business.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsMaterialDao;
import com.tcode.business.goods.model.GoodsMaterial;
import com.tcode.business.member.dao.MemberDao;
import com.tcode.business.member.model.Member;
import com.tcode.business.order.dao.OrderHeadDao;
import com.tcode.business.order.dao.OrderItemDao;
import com.tcode.business.order.model.OrderHead;
import com.tcode.business.order.model.OrderItem;
import com.tcode.business.shop.dao.CommParamDao;
import com.tcode.business.shop.dao.EmployeeDao;
import com.tcode.business.shop.dao.TypeCommissionDao;
import com.tcode.business.shop.model.CommParam;
import com.tcode.business.shop.model.Employee;
import com.tcode.business.shop.model.TypeCommission;
import com.tcode.business.shop.service.EmployeeService;
import com.tcode.core.util.Utils;

@Component("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeDao employeeDao;
	private OrderHeadDao orderHeadDao;
	private OrderItemDao orderItemDao;
	private MemberDao memberDao;
	private GoodsMaterialDao goodsMaterialDao;
	private TypeCommissionDao typeCommissionDao;
	private CommParamDao commParamDao;
	

	@Override
	public Employee getById(Integer id) throws Exception {
		return employeeDao.loadById(id);
	}
	
	@Override
	public List<Employee> getListByDept(String deptCode) throws Exception {
		return employeeDao.loadByDept(deptCode);
	}
	
	@Override
	public List<Employee> getListByGroup(String deptCode, Boolean group) throws Exception {
		return employeeDao.loadByGroup(deptCode, group);
	}
	
	@Override
	public List<Employee> getCommissionList(OrderItem orderItem) throws Exception {
		List<OrderItem> itemList = orderItemDao.loadCommissionList(orderItem);	//销售数据
		CommParam param = commParamDao.loadById(orderItem.getDeptCode());	//提成参数
		List<Employee> empList = employeeDao.loadByGroup(orderItem.getDeptCode(), false);	//员工
		List<Employee> empGroupList = employeeDao.loadByGroup(orderItem.getDeptCode(), true);	//组
		OrderHead orderHead = new OrderHead();
		if(!Utils.isEmpty(orderItem.getStartDate()))
			orderHead.setStartDate(orderItem.getStartDate());
		if(!Utils.isEmpty(orderItem.getEndDate()))
			orderHead.setEndDate(orderItem.getEndDate());
		orderHead.setDeptCode(orderItem.getDeptCode());
		List<OrderHead> headList = orderHeadDao.loadCommissionList(orderHead);
		
		for(OrderItem item : itemList) {
			//计算提成比例
			if(item.getGoodsType() == 2 || item.getGoodsType() == 4){
				GoodsMaterial mate = goodsMaterialDao.loadById(item.getGoodsId());
				if(!Utils.isEmpty(mate) && !Utils.isEmpty(mate.getInPrice()))
					item.setPrice(item.getPrice() - mate.getInPrice());
			} else if(item.getGoodsType() == 1 || item.getGoodsType() == 3){
				if(Utils.isEmpty(item.getSource())){
					if(!Utils.isEmpty(item.getTypeId())){
						TypeCommission typeComm = typeCommissionDao.loadById(item.getTypeId(), orderItem.getDeptCode());
						if(!Utils.isEmpty(typeComm))
							item.setSource(typeComm.getCommission());
					}
				}
			}
			//计算提成金额
			if(!Utils.isEmpty(item.getSource()) && item.getSource().indexOf("%") > 0)
				item.setDiscount(Double.parseDouble(item.getSource().replace("%", "")) * item.getPrice() * 0.01 );
			else
				item.setDiscount(Double.parseDouble(Utils.isEmpty(item.getSource())?"0":item.getSource()));
			
			//组长提成金额
			Double grcomm = Utils.isEmpty(param.getParam6()) ? 0 : Double.parseDouble(param.getParam6().indexOf("%")>0?Double.parseDouble(param.getParam6().replace("%", ""))*0.01*item.getDiscount() + "":param.getParam6());
			
			//把组换成组成员
			for(Employee group : empGroupList){
				if(!Utils.isEmpty(item.getPerformer()) && (item.getPerformer().indexOf(","+group.getId()+",") > 0 || item.getPerformer().indexOf(group.getId()+",") == 0)){
					item.setPerformer(item.getPerformer().replace(group.getId()+",", group.getGroupMember().replace(" ", "")));
					//组长提成金额
					for(Employee emp : empList){
						if(group.getGroupLeader().intValue() == emp.getId().intValue()){
							emp.setComm6((Utils.isEmpty(emp.getComm6()) ? 0.0 : emp.getComm6()) + grcomm);
						}
					}
				}
				if(!Utils.isEmpty(item.getSeller()) && (item.getSeller().indexOf(","+group.getId()+",") > 0 || item.getSeller().indexOf(group.getId()+",") == 0)){
					item.setSeller(item.getSeller().replace(group.getId()+",", group.getGroupMember().replace(" ", "")));
					//组长提成金额
					for(Employee emp : empList){
						if(group.getGroupLeader().intValue() == emp.getId().intValue()){
							emp.setComm6((Utils.isEmpty(emp.getComm6()) ? 0.0 : emp.getComm6()) + grcomm);
						}
					}
				}
				if(!Utils.isEmpty(item.getMiddleman()) && (item.getMiddleman().indexOf(","+group.getId()+",") > 0 || item.getMiddleman().indexOf(group.getId()+",") == 0)){
					item.setMiddleman(item.getMiddleman().replace(group.getId()+",", group.getGroupMember().replace(" ", "")));
					//组长提成金额
					for(Employee emp : empList){
						if(group.getGroupLeader().intValue() == emp.getId().intValue()){
							emp.setComm6((Utils.isEmpty(emp.getComm6()) ? 0.0 : emp.getComm6()) + grcomm);
						}
					}
				}
			}
		}
		
		for(OrderItem item : itemList) {
			
			//实际提成金额
			Double pcomm = Utils.isEmpty(param.getParam1()) ? 0 : Double.parseDouble(param.getParam1().indexOf("%")>0?Double.parseDouble(param.getParam1().replace("%", ""))*0.01*item.getDiscount() + "":param.getParam1());
			Double scomm = Utils.isEmpty(param.getParam2()) ? 0 : Double.parseDouble(param.getParam2().indexOf("%")>0?Double.parseDouble(param.getParam2().replace("%", ""))*0.01*item.getDiscount() + "":param.getParam2());
			Double mcomm = Utils.isEmpty(param.getParam3()) ? 0 : Double.parseDouble(param.getParam3().indexOf("%")>0?Double.parseDouble(param.getParam3().replace("%", ""))*0.01*item.getDiscount() + "":param.getParam3());
			
			//施工提成
			if(!Utils.isEmpty(item.getPerformer())){
				String[] array = item.getPerformer().split(",");
				for(String empId : array){
					for(Employee emp : empList){
						if(emp.getId() == Integer.parseInt(empId.trim())){
							emp.setComm1((Utils.isEmpty(emp.getComm1()) ? 0.0 : emp.getComm1()) + pcomm / array.length);
						}
					}
				}
			}
			//销售提成
			if(!Utils.isEmpty(item.getSeller())){
				String[] array = item.getSeller().split(",");
				for(String empId : array){
					for(Employee emp : empList){
						if(emp.getId() == Integer.parseInt(empId.trim())){
							emp.setComm2((Utils.isEmpty(emp.getComm2()) ? 0.0 : emp.getComm2()) + scomm / array.length);
						}
					}
				}
			}
			//辅助销售提成
			if(!Utils.isEmpty(item.getMiddleman())){
				String[] array = item.getMiddleman().split(",");
				for(String empId : array){
					for(Employee emp : empList){
						if(emp.getId() == Integer.parseInt(empId.trim())){
							emp.setComm3((Utils.isEmpty(emp.getComm3()) ? 0.0 : emp.getComm3()) + mcomm / array.length);
						}
					}
				}
			}
		}
		
		for(OrderHead order : headList){
			Double ccomm = Utils.isEmpty(param.getParam4()) ? 0 : Double.parseDouble(param.getParam4().indexOf("%")>0?Double.parseDouble(param.getParam4().replace("%", ""))*0.01*order.getAprice() + "":param.getParam4());
			Double gcomm = Utils.isEmpty(param.getParam5()) ? 0 : Double.parseDouble(param.getParam5().indexOf("%")>0?Double.parseDouble(param.getParam5().replace("%", ""))*0.01*order.getAprice() + "":param.getParam5());
			Member member = memberDao.loadById(order.getMemId());
			for(Employee emp : empList){
				if(emp.getName().trim().equals(order.getCreator().trim())){
					emp.setComm4((Utils.isEmpty(emp.getComm4()) ? 0.0 : emp.getComm4()) + ccomm);
				}
				if(!Utils.isEmpty(member) && !Utils.isEmpty(member.getSales()) && emp.getName().trim().equals(member.getSales().trim()))
					emp.setComm5((Utils.isEmpty(emp.getComm5()) ? 0.0 : emp.getComm5()) + gcomm);
			}
		}
		
		return empList;
	}
	
	@Override
	public String getNamesByIds(String ids) throws Exception {
		StringBuffer namesStr = new StringBuffer();
		if(!Utils.isEmpty(ids)){
			if(ids.indexOf(",") > 0){
				String[] idsStr = ids.split(",");
				Employee emp = null;
				for(String id : idsStr){
					if(!Utils.isEmpty(id))
						emp = employeeDao.loadById(Integer.parseInt(id.trim()));
					if(!Utils.isEmpty(emp))
						namesStr.append(emp.getName() + ",");
				}
			}
		}
		return namesStr.toString();
	}

	@Override
	public void insert(Employee employee) throws Exception {
		employeeDao.save(employee);
	}

	@Override
	public void update(Employee employee) throws Exception {
		employeeDao.edit(employee);
	}

	@Override
	public void delete(Employee employee) throws Exception {
		employeeDao.remove(employee);
	}

	@Override
	public List<Employee> getListPage(Employee employee, int start, int limit) throws Exception {
		return employeeDao.loadListPage(employee, start, limit);
	}

	@Override
	public Integer getListCount(Employee employee) throws Exception {
		return employeeDao.loadListCount(employee);
	}
	
	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}
	@Resource
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	public OrderItemDao getOrderItemDao() {
		return orderItemDao;
	}
	@Resource
	public void setOrderItemDao(OrderItemDao orderItemDao) {
		this.orderItemDao = orderItemDao;
	}

	public GoodsMaterialDao getGoodsMaterialDao() {
		return goodsMaterialDao;
	}
	@Resource
	public void setGoodsMaterialDao(GoodsMaterialDao goodsMaterialDao) {
		this.goodsMaterialDao = goodsMaterialDao;
	}

	public TypeCommissionDao getTypeCommissionDao() {
		return typeCommissionDao;
	}
	@Resource
	public void setTypeCommissionDao(TypeCommissionDao typeCommissionDao) {
		this.typeCommissionDao = typeCommissionDao;
	}

	public CommParamDao getCommParamDao() {
		return commParamDao;
	}
	@Resource
	public void setCommParamDao(CommParamDao commParamDao) {
		this.commParamDao = commParamDao;
	}

	public OrderHeadDao getOrderHeadDao() {
		return orderHeadDao;
	}
	@Resource
	public void setOrderHeadDao(OrderHeadDao orderHeadDao) {
		this.orderHeadDao = orderHeadDao;
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}
	@Resource
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
}

package com.tcode.business.finance.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tcode.business.finance.dao.AssetsDao;
import com.tcode.business.finance.dao.PayableDao;
import com.tcode.business.finance.dao.ReceivableDao;
import com.tcode.business.finance.dao.RepaymentDao;
import com.tcode.business.finance.model.Payable;
import com.tcode.business.finance.model.Receivable;
import com.tcode.business.finance.model.Repayment;
import com.tcode.business.finance.service.RepaymentService;
import com.tcode.business.member.dao.MemberCarDao;
import com.tcode.business.member.dao.MemberDao;
import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.order.model.OrderHead;
import com.tcode.business.shop.dao.SupplierDao;
import com.tcode.business.shop.model.Supplier;
import com.tcode.core.util.Utils;
import com.tcode.system.dao.SysUserDao;
import com.tcode.system.model.SysUser;

@Component("repaymentService")
public class RepaymentServiceImpl implements RepaymentService {
	
	private RepaymentDao repaymentDao;
	private ReceivableDao receivableDao;
	private PayableDao payableDao;
	private AssetsDao assetsDao;
	private MemberDao memberDao;
	private MemberCarDao memberCarDao;
	private SupplierDao supplierDao;
	private SysUserDao sysUserDao;

	@Override
	public Repayment getById(Integer id) throws Exception {
		return repaymentDao.loadById(id);
	}

	@Override
	public List<Repayment> getByReceId(String deptCode, Integer memId, Integer carId) throws Exception {
		List<Repayment> list = repaymentDao.loadByReceId(deptCode, memId, carId);
		for(Repayment repayment : list) {
			Member member = memberDao.loadById(repayment.getMemId());
			if(!Utils.isEmpty(member)) {
				repayment.setName(member.getName());
				repayment.setMobile(member.getMobile());
				repayment.setVipNo(member.getVipNo());
			}
			MemberCar car = memberCarDao.loadById(repayment.getCarId());
			if(!Utils.isEmpty(car)) 
				repayment.setCarNumber(car.getCarShort() + car.getCarCode() + car.getCarNumber());
			SysUser sysUser = sysUserDao.loadById(repayment.getCreator());
			if(!Utils.isEmpty(sysUser))
				repayment.setCreatorName(sysUser.getRealName());
		}
		return list;
	}
	
	@Override
	public List<Repayment> getBySupplierId(String deptCode, Integer supplierId) throws Exception {
		List<Repayment> list = repaymentDao.loadBySupplierId(deptCode, supplierId);
		for(Repayment repayment : list) {
			Supplier supplier = supplierDao.loadById(repayment.getSupplierId());
			if(!Utils.isEmpty(supplier))
				repayment.setName(supplier.getName());
			SysUser sysUser = sysUserDao.loadById(repayment.getCreator());
			if(!Utils.isEmpty(sysUser))
				repayment.setCreatorName(sysUser.getRealName());
		}
		return list;
	}

	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void insert(Repayment repayment) throws Exception {
		if(!Utils.isEmpty(repayment.getSupplierId())){
			Payable payable = payableDao.loadById(repayment.getDeptCode(), repayment.getSupplierId());
			payable.setRepayment(payable.getRepayment() + repayment.getRepayment());
			if(payable.getRepayment() >= payable.getPayable())
				payable.setStatus(1);
			payable.setLastUpdate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
			assetsDao.editPriceByCode(repayment.getDeptCode(), repayment.getPayType(), repayment.getRepayment() * -1);
		} else {
			Receivable receivable = receivableDao.loadById(repayment.getDeptCode(), repayment.getMemId(), repayment.getCarId());
			receivable.setRepayment(receivable.getRepayment() + repayment.getRepayment());
			if(receivable.getRepayment() >= receivable.getBillPrice())
				receivable.setStatus(1);
			receivable.setLastUpdate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
			assetsDao.editPriceByCode(repayment.getDeptCode(), repayment.getPayType(), repayment.getRepayment());
		}
		repaymentDao.save(repayment);
	}

	@Override
	public void update(Repayment repayment) throws Exception {
		repaymentDao.edit(repayment);
	}

	@Override
	public void delete(Repayment repayment) throws Exception {
		repaymentDao.remove(repayment);
	}
	
	@Override
	public Double getSumByPay(OrderHead orderHead) throws Exception{
		Double sum = repaymentDao.loadSumByPay(orderHead);
		return Utils.isEmpty(sum) ? 0.0 : sum;
	}
	
	

	public RepaymentDao getRepaymentDao() {
		return repaymentDao;
	}
	@Resource
	public void setRepaymentDao(RepaymentDao repaymentDao) {
		this.repaymentDao = repaymentDao;
	}

	public ReceivableDao getReceivableDao() {
		return receivableDao;
	}
	@Resource
	public void setReceivableDao(ReceivableDao receivableDao) {
		this.receivableDao = receivableDao;
	}

	public AssetsDao getAssetsDao() {
		return assetsDao;
	}
	@Resource
	public void setAssetsDao(AssetsDao assetsDao) {
		this.assetsDao = assetsDao;
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}
	@Resource
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public MemberCarDao getMemberCarDao() {
		return memberCarDao;
	}
	@Resource
	public void setMemberCarDao(MemberCarDao memberCarDao) {
		this.memberCarDao = memberCarDao;
	}

	public SysUserDao getSysUserDao() {
		return sysUserDao;
	}
	@Resource
	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

	public PayableDao getPayableDao() {
		return payableDao;
	}
	@Resource
	public void setPayableDao(PayableDao payableDao) {
		this.payableDao = payableDao;
	}

	public SupplierDao getSupplierDao() {
		return supplierDao;
	}
	@Resource
	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

}

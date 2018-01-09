package com.tcode.business.member.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.member.model.MemberGrade;
import com.tcode.business.member.service.MemberGradePriceService;
import com.tcode.business.member.service.MemberGradeService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("memberGradeAction")
public class MemberGradeAction extends BaseAction {

	private static final long serialVersionUID = -779069155194990033L;
	private static Logger log = Logger.getLogger("SLog");
	
	private MemberGradeService memberGradeService;
	private MemberGradePriceService gradePriceService;
	
	private List<MemberGrade> gradeList;
	private MemberGrade grade;
	
	/**
	 * 根据等级编号查找等级
	 * @return
	 */
	public String queryGradeByGrade() {
		try {
			if(!Utils.isEmpty(grade) && !Utils.isEmpty(grade.getGrade())){
				grade = memberGradeService.getMemberGradeByGrade(this.getDept().getCompanyId(), grade.getGrade());
				gradeList = new ArrayList<MemberGrade>();
				gradeList.add(grade);
				this.setResult(true, "");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public String queryGradePage() {
		try {
			this.setTotalCount(memberGradeService.getListCount(grade));
			gradeList = memberGradeService.getListPage(grade, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询门店等级信息
	 * @return
	 */
	public String queryGradeByDeptCode() {
		try {
			gradeList = memberGradeService.getMemberGradeByDeptCode(super.getDept().getCompanyId());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertGrade() {
		try {
			if(!Utils.isEmpty(grade)){
				MemberGrade existGrade = memberGradeService.getMemberGradeByGrade(super.getDept().getCompanyId(), grade.getGrade());
				if(Utils.isEmpty(existGrade)){
					grade.setDeptCode(super.getDept().getCompanyId());
					if(Utils.isEmpty(grade.getOrderDiscount()))
						grade.setOrderDiscount(0.0);
					if(Utils.isEmpty(grade.getDiscount()))
						grade.setDiscount(0.0);
					if(Utils.isEmpty(grade.getNeedPoint()))
						grade.setNeedPoint(0);
					memberGradeService.insert(grade);
					this.setResult(true, "新建成功！");
				} else
					this.setResult(false, "等级已经存在！");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String updateGrade() {
		try {
			if(!Utils.isEmpty(grade) && !Utils.isEmpty(grade.getId())){
				grade.setDeptCode(super.getDept().getCompanyId());
				memberGradeService.update(grade);
				this.setResult(true, "修改成功！");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, "修改失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * 删除等级前，先删除等级价格信息
	 * @return
	 */
	public String deleteGrade() {
		try {
			if(!Utils.isEmpty(grade) && !Utils.isEmpty(grade.getId())){
				MemberGrade exist = memberGradeService.getMemberGradeById(grade.getId());
				if(!Utils.isEmpty(exist)){
					gradePriceService.deleteByDeptGrade(this.getDept().getCompanyId(), exist.getGrade());
					memberGradeService.delete(exist);
					this.setResult(true, "删除成功!");
				}
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, "删除失败！");
		}
		return SUCCESS;
	}
	
	
	public MemberGradeService getMemberGradeService() {
		return memberGradeService;
	}
	@Resource
	public void setMemberGradeService(MemberGradeService memberGradeService) {
		this.memberGradeService = memberGradeService;
	}

	public MemberGradePriceService getGradePriceService() {
		return gradePriceService;
	}
	@Resource
	public void setGradePriceService(MemberGradePriceService gradePriceService) {
		this.gradePriceService = gradePriceService;
	}

	public List<MemberGrade> getGradeList() {
		return gradeList;
	}

	public void setGradeList(List<MemberGrade> gradeList) {
		this.gradeList = gradeList;
	}

	public MemberGrade getGrade() {
		return grade;
	}

	public void setGrade(MemberGrade grade) {
		this.grade = grade;
	}
}

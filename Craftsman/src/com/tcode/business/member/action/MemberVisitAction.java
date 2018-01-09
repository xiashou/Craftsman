package com.tcode.business.member.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.member.model.MemberVisit;
import com.tcode.business.member.service.MemberVisitService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("memberVisitAction")
public class MemberVisitAction extends BaseAction {

	private static final long serialVersionUID = -5500968752139817502L;
	private static Logger log = Logger.getLogger("SLog");
	
	private MemberVisitService memberVisitService;
	
	private List<MemberVisit> visitList;
	
	private MemberVisit visit;
	
	/**
	 * 分页查询客户回访
	 * @return
	 */
	public String queryVisitListByPage(){
		try {
			if(Utils.isEmpty(visit))
				visit = new MemberVisit();
			visit.setDeptCode(this.getDept().getDeptCode());
			this.setTotalCount(memberVisitService.getVisitListCount(visit));
			visitList = memberVisitService.getVisitListByPage(visit, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 更新回访记录
	 * @return
	 */
	public String updateVisitRecord() {
		try {
			if(!Utils.isEmpty(visit) && !Utils.isEmpty(visit.getId())) {
				String result = visit.getResult();
				visit = memberVisitService.getById(visit.getId());
				visit.setDeptCode(this.getDept().getDeptCode());
				visit.setResult(result);
				visit.setVisitor(this.getUser().getUserName());
				visit.setVisitTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
				memberVisitService.update(visit);
				this.setResult(true, "");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	

	public MemberVisitService getMemberVisitService() {
		return memberVisitService;
	}
	@Resource
	public void setMemberVisitService(MemberVisitService memberVisitService) {
		this.memberVisitService = memberVisitService;
	}
	public List<MemberVisit> getVisitList() {
		return visitList;
	}
	public void setVisitList(List<MemberVisit> visitList) {
		this.visitList = visitList;
	}
	public MemberVisit getVisit() {
		return visit;
	}
	public void setVisit(MemberVisit visit) {
		this.visit = visit;
	}

}

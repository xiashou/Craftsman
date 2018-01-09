package com.tcode.business.member.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberBook;
import com.tcode.business.member.model.MemberBookTips;
import com.tcode.business.member.service.MemberBookService;
import com.tcode.common.action.BaseAction;
import com.tcode.common.message.MsgUtil;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("memberBookAction")
public class MemberBookAction extends BaseAction {

	private static final long serialVersionUID = -5500968752139817502L;
	private static Logger log = Logger.getLogger("SLog");
	
	private MemberBookService memberBookService;
	
	private List<MemberBook> bookList;
	
	private MemberBook book;
	private MemberBookTips bookTips;
	
	
	/**
	 * 预约界面
	 * @return
	 */
	public String initMemberBook() {
		try {
			Member member = (Member) this.getRequest().getSession().getAttribute("member");
			if(!Utils.isEmpty(member))
				bookTips = memberBookService.getTipsByDept(member.getDeptCode());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 预约温馨提示
	 * @return
	 */
	public String queryBookByDept() {
		try {
			if(Utils.isEmpty(book))
				book = new MemberBook();
			book.setDeptCode(this.getDept().getDeptCode());
			this.setTotalCount(memberBookService.getListCount(book));
			bookList = memberBookService.getListByPage(book, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String updateBookById() {
		try {
			if(!Utils.isEmpty(book) && !Utils.isEmpty(book.getId())){
				MemberBook exist = memberBookService.getById(book.getId());
				if(!Utils.isEmpty(exist)){
					exist.setStatus(book.getStatus());
					memberBookService.update(exist);
					this.setResult(true, "操作成功！");
				}
			}
		} catch(Exception e) {
			this.setResult(false, "操作失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 预约温馨提示
	 * @return
	 */
	public String queryMemberBookTipsByDept() {
		try {
			bookTips = memberBookService.getTipsByDept(this.getDept().getDeptCode());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加预约
	 * @return
	 */
	public String insertMemberBook() {
		try {
			if(!Utils.isEmpty(book)){
				Member member = (Member) this.getRequest().getSession().getAttribute("member");
				if(!Utils.isEmpty(member)){
					book.setMemId(member.getMemId());
					book.setDeptCode(member.getDeptCode());
					book.setStatus(1);
					book.setCreateTime(Utils.getSysTime());
					memberBookService.insert(book);
					MsgUtil.sendBookMsg(this.getRequest(), "OPENTM410192957", member, book);
					this.setResult(true, "预约成功！");
				} else
					this.setResult(false, "用户信息获取失败！");
			}
		} catch(Exception e) {
			this.setResult(false, "预约失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 保存温馨提示
	 * @return
	 */
	public String updateMemberBookTips() {
		try {
			if(!Utils.isEmpty(bookTips)){
				bookTips.setDeptCode(this.getDept().getDeptCode());
				memberBookService.updateTips(bookTips);
				this.setResult(true, "修改成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public MemberBookService getMemberBookService() {
		return memberBookService;
	}
	@Resource
	public void setMemberBookService(MemberBookService memberBookService) {
		this.memberBookService = memberBookService;
	}
	public List<MemberBook> getBookList() {
		return bookList;
	}
	public void setBookList(List<MemberBook> bookList) {
		this.bookList = bookList;
	}
	public MemberBook getBook() {
		return book;
	}
	public void setBook(MemberBook book) {
		this.book = book;
	}
	public MemberBookTips getBookTips() {
		return bookTips;
	}
	public void setBookTips(MemberBookTips bookTips) {
		this.bookTips = bookTips;
	}
	
	
	
}

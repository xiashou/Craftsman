package com.tcode.common.jcrontab.job.visit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.tcode.business.member.model.MemberVisit;
import com.tcode.business.member.service.MemberVisitService;
import com.tcode.core.util.Utils;

/**
 * 初始化客户回访数据JOB
 * @author TSM
 *
 */
@Component
public class MemberVisitInitJob {

	private static Logger log = Logger.getLogger("SLog");
	
	private static MemberVisitService memberVisitService;
	
	public static void main(String[] args) {
		System.out.println("正在删除旧回访数据...");
		deleteVisit();
		System.out.println("正在抽取保养到期回访数据...");
		initVisit1();
		System.out.println("正在抽取保险到期回访数据...");
		initVisit2();
		System.out.println("正在抽取年检到期回访数据...");
		initVisit3();
		System.out.println("正在抽取客户流失回访数据...");
		initVisit4();
		System.out.println("正在抽取服务到期回访数据...");
		initVisit5();
		System.out.println("正在抽取余额不足回访数据...");
		initVisit6();
		System.out.println("回访数据抽取完成！！！");
	}
	
	/**
	 * 删除固定时间以前的旧回访数据
	 */
	public static void deleteVisit() {
		try {
			log.warn("正在删除旧回访数据...");
			String today = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			memberVisitService.deleteOldVisit(Utils.dateApart(today, -90));
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
	}
	
	/**
	 * 保养到期回访
	 */
	public static void initVisit1() {
		try {
			log.warn("正在抽取保养到期回访数据...");
			String today = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			String now = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
			memberVisitService.updateVisit1(now, Utils.dateApart(today, 15));
			List<MemberVisit> list = memberVisitService.getVisitList1(today, Utils.dateApart(today, 15));
			for(MemberVisit visit : list) {
				visit.setCreateTime(now);
				memberVisitService.insert(visit);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
	}
	
	/**
	 * 保险到期回访
	 */
	public static void initVisit2() {
		try {
			log.warn("正在抽取保险到期回访数据...");
			String today = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			String now = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
			memberVisitService.updateVisit2(now, Utils.dateApart(today, 15));
			List<MemberVisit> list = memberVisitService.getVisitList2(today, Utils.dateApart(today, 15));
			for(MemberVisit visit : list) {
				visit.setCreateTime(now);
				memberVisitService.insert(visit);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
	}
	
	/**
	 * 年检到期回访
	 */
	public static void initVisit3() {
		try {
			log.warn("正在抽取年检到期回访数据...");
			String today = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			String now = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
			memberVisitService.updateVisit3(now, Utils.dateApart(today, 15));
			List<MemberVisit> list = memberVisitService.getVisitList3(today, Utils.dateApart(today, 15));
			for(MemberVisit visit : list) {
				visit.setCreateTime(now);
				memberVisitService.insert(visit);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
	}
	
	/**
	 * 客户流失
	 */
	public static void initVisit4() {
		try {
			log.warn("正在抽取客户流失回访数据...");
			String today = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			String now = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
			memberVisitService.updateVisit4(now, Utils.dateApart(today, -3));
			List<MemberVisit> list = memberVisitService.getVisitList4(Utils.dateApart(today, -60));
			for(MemberVisit visit : list) {
				visit.setCreateTime(now);
				memberVisitService.insert(visit);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
	}
	
	/**
	 * 剩余服务到期
	 */
	public static void initVisit5() {
		try {
			log.warn("正在抽取剩余服务到期回访数据...");
			String today = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			String now = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
			memberVisitService.updateVisit5(now);
			List<MemberVisit> list = memberVisitService.getVisitList5(today, Utils.dateApart(today, 15));
			for(MemberVisit visit : list) {
				visit.setCreateTime(now);
				memberVisitService.insert(visit);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
	}
	
	/**
	 * 余额不足
	 */
	public static void initVisit6() {
		try {
			log.warn("正在抽取余额不足回访数据...");
			String now = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
			memberVisitService.updateVisit5(now);
			List<MemberVisit> list = memberVisitService.getVisitList6();
			for(MemberVisit visit : list) {
				visit.setCreateTime(now);
				memberVisitService.insert(visit);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
	}

	public MemberVisitService getMemberVisitService() {
		return memberVisitService;
	}
	@Resource
	public void setMemberVisitService(MemberVisitService memberVisitService) {
		MemberVisitInitJob.memberVisitService = memberVisitService;
	}
	
}

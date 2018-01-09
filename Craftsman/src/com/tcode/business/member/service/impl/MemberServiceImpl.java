package com.tcode.business.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tcode.business.member.dao.MemberCarDao;
import com.tcode.business.member.dao.MemberDao;
import com.tcode.business.member.dao.MemberGradeDao;
import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.member.model.MemberGrade;
import com.tcode.business.member.service.MemberService;
import com.tcode.common.message.HttpSender;
import com.tcode.core.util.Utils;

@Component("memberService")
public class MemberServiceImpl implements MemberService {
	
	private MemberDao memberDao;
	private MemberCarDao memberCarDao;
	private MemberGradeDao memberGradeDao;

	@Override
	public Member getMemberById(Integer id) throws Exception {
		return memberDao.loadById(id);
	}
	
	@Override
	public Member getMemberByMobile(String companyId, String mobile) throws Exception {
		return memberDao.loadByMobile(companyId, mobile);
	}
	
	@Override
	public Member getByMobile(String mobile) throws Exception {
		return memberDao.loadByMobile(mobile);
	}
	
	/**
	 * 根据手机号和门店编码码查找
	 * @param  deptCode
	 * @param  mobile
	 * @return Member
	 * @throws Exception
	 */
	public Member getByMobileAndDeptCode(String deptCode, String mobile) throws Exception {
		return memberDao.loadByMobileAndDeptCode(deptCode, mobile);
	}
	
	/**
	 * 根据手机号和门店编码码查找
	 * @param companyId
	 * @param mobile
	 * @return Member
	 * @throws Exception
	 */
	public Member getByMobileAndCompanyId(String companyId, String mobile)  throws Exception {
		return memberDao.loadByMobileAndCompanyId(companyId, mobile);
	}
	
	@Override
	public List<Member> getMemberByVipNo(String vipNo) throws Exception {
		return memberDao.loadByVipNo(vipNo);
	}
	
	@Override
	public List<Member> getMemberByKeywordPage(String companyId, String keyword, int start, int limit) throws Exception {
		return memberDao.loadByKeywordPage(companyId, keyword, start, limit);
	}
	
	@Override
	public Integer getMemberByKeywordCount(String companyId, String keyword) throws Exception {
		return memberDao.loadByKeywordCount(companyId, keyword);
	}

	@Override
	public void insert(Member member) throws Exception {
		memberDao.save(member);
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public Integer insertMoreMember(List<Member> memberList) throws Exception {
		int count = 0;
		for(Member member : memberList){
			MemberCar car = null;
			if(!Utils.isEmpty(member.getCarShort()) && !Utils.isEmpty(member.getCarCode()) && !Utils.isEmpty(member.getCarNumber())) {
				MemberCar exist = memberCarDao.loadByCarNumber(member.getCompanyId(), member.getCarShort(), member.getCarCode(), member.getCarNumber());
				if(Utils.isEmpty(exist)){
					car = new MemberCar();
					car.setCarShort(member.getCarShort());
					car.setCarCode(member.getCarCode());
					car.setCarNumber(member.getCarNumber());
				}
			}
			memberDao.saveOrUpdate(member);
			if(!Utils.isEmpty(member.getMemId()) && !Utils.isEmpty(car)) {
				car.setMemberId(member.getMemId());
				memberCarDao.save(car);
			}
			count++;
		}
		return count;
	}

	@Override
	public void update(Member member) throws Exception {
		memberDao.edit(member);
	}
	
	@Override
	public void updateMemberAlbum(Integer memId, Integer imageId) throws Exception {
		memberDao.editMemberAlbum(memId, imageId);
	}
	
	@Override
	public void deleteMemberAlbum(Integer memId, Integer imageId) throws Exception {
		memberDao.removeMemberAlbum(memId, imageId);
	}

	@Override
	public void delete(Member member) throws Exception {
		memberDao.remove(member);
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void deleteByIds(Integer[] idStr) throws Exception {
		String ids= StringUtils.join(idStr, ",");
		memberCarDao.removeByMemIds(ids);
		memberDao.removeByIds(ids);
	}

	@Override
	public List<Member> getListPage(Member member, int start, int limit) throws Exception {
		List<Member> list = memberDao.loadListPage(member, start, limit);
		for(Member m : list){
			m.setCarList(memberCarDao.loadByMemberId(m.getMemId()));
			MemberGrade mGrade = memberGradeDao.loadByGrade(m.getDeptCode(), m.getGrade());
			if(!Utils.isEmpty(mGrade))
				m.setGradeName(mGrade.getName());
		}
		return list;
	}
	
	@Override
	public Integer sendMemberSms(Member member, Integer[] ids, String msg) throws Exception {
		List<Member> list = null;
		if(!Utils.isEmpty(ids))
			list = memberDao.loadListByIdsPage(ids, 0, 10000);
		else
			list = memberDao.loadListPage(member, 0, 10000);
		StringBuffer mobileStr = new StringBuffer();
		int count = 0;
		for(Member m : list){
			if(!Utils.isEmpty(m) && m.getMobile().length() == 11){
				mobileStr.append(m.getMobile() + ",");
				count++;
			}
		}
		int result = HttpSender.batchSend2(mobileStr.toString(), msg);
		return result == 0 ? count : 0;
	}
	
	@Override
	public Integer getListCount(Member member) throws Exception {
		return memberDao.loadListCount(member);
	}
	
	@Override
	public Integer getMemberCountByDept(String deptCode) throws Exception {
		Integer count = memberDao.loadMemberCountByDept(deptCode);
		return Utils.isEmpty(count) ? 0 : count;
	}
	
	@Override
	public Double getMemberBalanceSumByDept(String deptCode) throws Exception {
		Double sum = memberDao.loadMemberBalanceSumByDept(deptCode);
		return Utils.isEmpty(sum) ? 0.0 : sum;
	}
	
	@Override
	public Integer getMemberPointSumByDept(String deptCode) throws Exception {
		Integer sum = memberDao.loadMemberPointSumByDept(deptCode);
		return Utils.isEmpty(sum) ? 0 : sum;
	}
	
	@Override
	public List<Member> getListByMMdd(String MMdd) throws Exception {
		return memberDao.loadListByMMdd(MMdd);
	}
	
	@Override
	public List<Member> getByAppIdOpenId(String appId, String openId) throws Exception {
		return memberDao.loadByAppIdOpenId(appId, openId);
	}
	
	@Override
	public Integer getDayGrowth(String companyId, String date, String deptCode) throws Exception {
		return memberDao.loadDayGrowth(companyId, date, deptCode);
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

	public MemberGradeDao getMemberGradeDao() {
		return memberGradeDao;
	}
	@Resource
	public void setMemberGradeDao(MemberGradeDao memberGradeDao) {
		this.memberGradeDao = memberGradeDao;
	}

}

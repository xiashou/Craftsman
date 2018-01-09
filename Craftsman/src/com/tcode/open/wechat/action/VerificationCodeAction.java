package com.tcode.open.wechat.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.weixin4j.OAuth2User;

import com.tcode.business.basic.model.BaseArea;
import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.member.service.MemberCarService;
import com.tcode.business.member.service.MemberService;
import com.tcode.business.msg.model.MsgSendRecord;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.util.WechatAuthorizerParamsUtil;
import com.tcode.common.action.BaseAction;
import com.tcode.common.idgenerator.IdGenerator;
import com.tcode.common.message.HttpSender;
import com.tcode.common.message.MsgUtil;
import com.tcode.core.util.Utils;
import com.tcode.open.wechat.bean.VerificationCode;
import com.tcode.system.service.SysDeptService;

import net.sf.json.JSONObject;

/**
 * 验证码
 * @author supeng
 *
 */
@Scope("prototype")
@Component("VerificationCodeAction")
public class VerificationCodeAction extends BaseAction{
	
	private SysDeptService sysDeptService;
	private MemberCarService memberCarService;
	private MemberService memberService;
	private String sid;
	private VerificationCode verificationCode = new VerificationCode();
	private String telphoneNum;
	private String code;
	private String carShort;
	private String carCode;
	private String carNum;
	private MemberCar car;
	
	/**
	 * 初始化验证码
	 * @return
	 */
	public String initVerificationCode() {
		HttpServletRequest request = getRequest();
		HttpSession session = request.getSession();
		try {
			if(Utils.isEmpty(sid) || session.getAttribute("auth2User") == null) {//查看是否有鉴权参数，没有不允许操作
				verificationCode.setErrCode(-1);//无鉴权参数，不允许操作
				verificationCode.setErrMsg("oAuth Or sid empty!");
			} else {	
				
				//sid有效（存在，且被成功授权），则允许访问。否则无权限访问。
				if(WechatAuthorizerParamsUtil.validateSIdS(sid)) {
					
					if(!Utils.isEmpty(telphoneNum)) {//查看手机号是否为空
						WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
						String deptCode = wechatAuthorizerParams.getDeptCode();
						String companyId = wechatAuthorizerParams.getCompanyId();
						//检查该手机号是否已经进行过会员绑定操作，已绑定，则不能再次绑定
//						Member member = memberService.getByMobile(telphoneNum);
//						Member member = memberService.getByMobileAndDeptCode(deptCode, telphoneNum);
						Member member = memberService.getByMobileAndCompanyId(companyId, telphoneNum);
						if(member != null) {
							if(!Utils.isEmpty(member.getWechatNo())) {//已绑定，则不能再次绑定
								verificationCode.setErrCode(10001);
								verificationCode.setErrMsg("该手机已经完成绑定，请勿重复绑定！");
								return SUCCESS;
							}else {//如已在后台注册过，则显示车辆信息
								int memId = member.getMemId();
								//获取会员对应的车辆信息
								List<MemberCar> memberCarList = memberCarService.getMemberCarByMemberId(memId);
								if(memberCarList.size() > 0) {
									car = memberCarList.get(0);
								}
							}
						} 
						
						//判断距离上次发送验证码时间是否已经有一分钟，没有一分钟不允许发送验证码
						verificationCode =session.getAttribute("verificationCode") == null ? null : (VerificationCode) session.getAttribute("verificationCode");
						if(verificationCode != null) {
							String createTime = verificationCode.getCreateTime();
							String currentTime = Utils.getSysTime();
							int seconds = Utils.secondBetween(currentTime, createTime, "yyyy/MM/dd HH:mm:ss");
							
							if(seconds < 55) {//缓冲5秒
								verificationCode.setErrCode(-6);//验证码发送频繁
								verificationCode.setErrMsg("请勿重复获取验证码！");
								return SUCCESS;
							}
						}else verificationCode = new VerificationCode();
						
						//根据门店ID和手机号，无法获取会员信息，即不存在，则生成并发送验证码
						String code = Utils.getFixLenthString(6);
						
						//封装验证码对象
						verificationCode.setCode(code);
						verificationCode.setExpires_in(600);
						verificationCode.setCreateTime(Utils.getSysTime());
						verificationCode.setErrCode(0);
						verificationCode.setErrMsg("ok");
						
						//将验证码放入session
						session.setAttribute("verificationCode", verificationCode);
						session.setMaxInactiveInterval(600);//单位秒
						
						//发送短信
						String msgSignature = wechatAuthorizerParams.getMsgSignature();
						String content = msgSignature + "验证码为：" + code + "，有效时间10分钟，请勿告知他人。";
						MsgSendRecord msgSendRecord = new MsgSendRecord();
						msgSendRecord.setVipNo("");//会员号
						msgSendRecord.setMobile(telphoneNum);//手机号
						msgSendRecord.setContent(content);//短信内容
						msgSendRecord.setDeptCode(deptCode);//店柜编码
						msgSendRecord.setOrderId("");//订单ID
						msgSendRecord.setMissionID(0);//任务ID
//						MsgUtil.sendMsg(msgSendRecord);
						
					}
				
				}else {
					verificationCode.setErrCode(-2);//授权码无效
					verificationCode.setErrMsg("sid illegal!");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			verificationCode.setErrCode(-3);//系统错误
			verificationCode.setErrMsg("System Error!");
		}
		return SUCCESS;
	}

	/**
	 * 验证验证码（绑定会员）
	 * @return
	 */
	public String validateVerificationCode() {
		HttpServletRequest request = getRequest();
		HttpSession session = request.getSession();
		try {
			//获取鉴权信息
			OAuth2User auth2User = (OAuth2User) session.getAttribute("auth2User");
			if(auth2User != null || Utils.isEmpty(sid)) {
				//sid有效（存在，且被成功授权），则允许访问。否则无权限访问。
				if(WechatAuthorizerParamsUtil.validateSIdS(sid)) {
					if(!Utils.isEmpty(telphoneNum) && !Utils.isEmpty(code)) {//查看手机号、验证码、车牌是否为空
						//验证验证码
						verificationCode =session.getAttribute("verificationCode") == null ? null : (VerificationCode) session.getAttribute("verificationCode");
						String sessionCode = verificationCode.getCode();
						if(code.equals(sessionCode)) {
							WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
							String deptCode = wechatAuthorizerParams.getDeptCode();
							String companyId = wechatAuthorizerParams.getCompanyId();
							//检查该手机号是否已经进行过会员绑定操作，已绑定，则不能再次绑定
//							Member member = memberService.getByMobile(telphoneNum);
//							Member member = memberService.getByMobileAndDeptCode(deptCode, telphoneNum);
							Member member = memberService.getByMobileAndCompanyId(companyId, telphoneNum);
							if(member != null) {
								if(!Utils.isEmpty(member.getWechatNo())) {//已绑定，则不能再次绑定
									verificationCode.setErrCode(10001);
									verificationCode.setErrMsg("该手机已经完成绑定，请勿重复绑定！");
								} else {//进行绑定
//									if(!deptCode.equalsIgnoreCase(member.getDeptCode())) {
									if(!companyId.equalsIgnoreCase(member.getCompanyId())) {
										verificationCode.setErrCode(10002);
										verificationCode.setErrMsg("授权门店与会员门店不一致！");
									} else {
										String appId = wechatAuthorizerParams.getAuthorizerAppId();
										String openId = auth2User.getOpenid();
										String wechatNick = auth2User.getNickname();
										member.setWechatNo(openId);
										member.setWechatNick(wechatNick);
										member.setAppId(appId);
										member.setWehcatHead(auth2User.getHeadimgurl());
										memberService.update(member);
									}
								}
							} else {//不存在...
								//根据appId和openId查询是否已存在会员信息
								String appId = wechatAuthorizerParams.getAuthorizerAppId();
								String openId = auth2User.getOpenid();
								List<Member> memberList = memberService.getByAppIdOpenId(appId, openId);
								if(memberList.size() > 0) {//如果存在，则说明此微信已绑定过其他手机
									verificationCode.setErrCode(10003);
									verificationCode.setErrMsg("您的微信已绑定过其他手机！");
								} else {//没有则新增一套会员数据
									member = new Member();
									//获取公司ID
//									String companyId = sysDeptService.getByDeptCode(deptCode).getCompanyId();
									member.setVipNo("V" + IdGenerator.getVipNoGenerator());//会员号
									member.setCompanyId(companyId);
									member.setDeptCode(deptCode);
									member.setSource(wechatAuthorizerParams.getDeptName());
									member.setCreateTime(Utils.getSysTime());
									member.setName(auth2User.getNickname());
									member.setMobile(telphoneNum);
									member.setPoint(0);
									member.setBalance(0.0);
									member.setWechatNo(openId);
									member.setWechatNick(auth2User.getNickname());
									member.setAppId(appId);
									member.setWehcatHead(auth2User.getHeadimgurl());
									memberService.insert(member);
									
									//添加车辆信息
									MemberCar car = new MemberCar();
									car.setMemberId(member.getMemId());
									car.setCarShort(carShort);
									car.setCarCode(carCode);
									car.setCarNumber(carNum);
									memberCarService.insert(car);
									
									session.removeAttribute("verificationCode");
								}
							}
						}else {
							verificationCode.setErrCode(-5);//表单参数有误
							verificationCode.setErrMsg("验证码错误！");
						}
						
					}else {
						verificationCode.setErrCode(-4);//表单参数有误
						verificationCode.setErrMsg("手机号/验证码为空！");
					}
				} else {
					verificationCode.setErrCode(-2);//授权码无效
					verificationCode.setErrMsg("sid illegal!");
				}
				
			} else {
				verificationCode.setErrCode(-1);//无鉴权参数，不允许操作
				verificationCode.setErrMsg("oAuth Or sid empty!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			verificationCode.setErrCode(-3);//系统错误
			verificationCode.setErrMsg("System Error!");
		}
		return SUCCESS;
	}
	
	
	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public VerificationCode getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(VerificationCode verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getTelphoneNum() {
		return telphoneNum;
	}

	public void setTelphoneNum(String telphoneNum) {
		this.telphoneNum = telphoneNum;
	}
	
	public String getCarShort() {
		return carShort;
	}

	public void setCarShort(String carShort) {
		this.carShort = carShort;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	@Resource
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public MemberCarService getMemberCarService() {
		return memberCarService;
	}

	@Resource
	public void setMemberCarService(MemberCarService memberCarService) {
		this.memberCarService = memberCarService;
	}

	public SysDeptService getSysDeptService() {
		return sysDeptService;
	}

	@Resource
	public void setSysDeptService(SysDeptService sysDeptService) {
		this.sysDeptService = sysDeptService;
	}

	public MemberCar getCar() {
		return car;
	}

	public void setCar(MemberCar car) {
		this.car = car;
	}
	
}

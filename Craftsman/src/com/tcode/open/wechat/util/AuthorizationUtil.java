package com.tcode.open.wechat.util;

import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.weixin4j.Configuration;
import org.weixin4j.OAuth2User;

import com.tcode.business.member.model.Member;
import com.tcode.business.member.service.MemberService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.util.WechatAuthorizerParamsUtil;
import com.tcode.core.util.Utils;

/**
 * 访问权限验证
 * @author supeng
 *
 */
@Component
public class AuthorizationUtil {
	
	private static MemberService memberService;

	/**
	 * 验证访问
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static boolean validateAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean flag = false;
		
//		String oAuth2UserjsonStr = request.getParameter("json");
		//获取appId
		String sid = request.getParameter("sid");
		//sid有效（存在，且被成功授权），则允许访问。否则无权限访问。
		if(!Utils.isEmpty(sid) && WechatAuthorizerParamsUtil.validateSIdS(sid)) {
			WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
			String appId = wechatAuthorizerParams.getAuthorizerAppId();
			
			OAuth2User auth2User = (OAuth2User) request.getSession().getAttribute("auth2User");
			
			if(auth2User != null && appId.equals(auth2User.getAppId())) {//如已鉴权其前后为统一公众号鉴权，则进行会员登录操作
				//如果请求为会员注册，则不进行登录操作
				String serverName = request.getServerName();
				if(!"wechatRegister".equalsIgnoreCase(serverName)) {
					flag = oAuth2UserLogin(request, response);
				}
			} else {//为空即未鉴权，则进行鉴权操作。如果之前进入过其他公众号，为其他公众号鉴权也需要再次进行鉴权操作
				oAuth2User(request, response);
			}
		}else {
			String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
			response.sendRedirect(basePath + "/wct/noPermission.jsp");
//			request.getRequestDispatcher("/wct/noPermission.jsp").forward(request, response);
			return flag;
		}
		return flag;
	}
	
	/**
	 * 鉴权获取用户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static boolean oAuth2User(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean flag = false;
		String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
		String business_redirect_uri = request.getScheme() + "://" + Utils.convertServerName(request.getServerName()) 
			+ request.getContextPath() + request.getRequestURI() + "?" + Utils.getRequestParams(request);
		String sid = request.getParameter("sid");
		String scope = request.getParameter("scope");
		if(Utils.isEmpty(scope)) scope = "snsapi_userinfo";//snsapi_base和snsapi_userinfo
		
		//sid有效（存在，且被成功授权），则允许访问。否则无权限访问。
		if(!Utils.isEmpty(sid) && WechatAuthorizerParamsUtil.validateSIdS(sid)) {
			
			business_redirect_uri =  URLEncoder.encode(business_redirect_uri, "UTF-8");
			//鉴权，获取当前微信用户信息
			response.sendRedirect(basePath + "/open/wechat/sys/authorizeOauth2initOAuth2User.atc"
					+ "?scope=" + scope + "&business_redirect_uri=" + business_redirect_uri + "&sid=" + sid);
			return flag;
		} else {
			response.sendRedirect(basePath + "/wct/noPermission.jsp");
			return flag;
		}
	}
	
	/**
	 * 微信鉴权会员登录
	 * @param auth2User
	 * @throws Exception 
	 */
	public static boolean oAuth2UserLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean flag = false;
		String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
		//获取appId
		String sid = request.getParameter("sid");
		//sid有效（存在，且被成功授权），则允许访问。否则无权限访问。
		if(!Utils.isEmpty(sid) && WechatAuthorizerParamsUtil.validateSIdS(sid)) {
			WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
			String appId = wechatAuthorizerParams.getAuthorizerAppId();
			
			HttpSession session = request.getSession();
			String oAuth2UserjsonStr = request.getParameter("json");
//			OAuth2User auth2User = (OAuth2User) JSONObject.toBean(JSONObject.fromObject(oAuth2UserjsonStr), OAuth2User.class);
			OAuth2User auth2User = (OAuth2User) session.getAttribute("auth2User");
			
			//根据appId、openId查看该会员是否已通过微信注册会员
			//已注册则放行，正常访问系统功能
			//没有注册，则跳转至微信会员注册界面
			if(Utils.isEmpty(request.getParameter("auth"))){	//特殊渠道不需要绑定车主的带auth参数
				String openId = auth2User.getOpenid();
				List<Member> memberList = memberService.getByAppIdOpenId(appId, openId);
				if (Configuration.isDebug()) {
					System.out.println("微信鉴权会员登录：appId-" + appId + "-openId：" + openId);
				}
				Member member = null;
				if(memberList.size() > 0) {//已注册则放行，正常访问系统功能,同时更新会员信息
					member = memberList.get(0);
					member.setWechatNick(auth2User.getNickname());
					member.setWehcatHead(auth2User.getHeadimgurl());
					memberService.update(member);
					session.setAttribute("member", member);
					flag = true;
				} else response.sendRedirect(basePath + "/wct/center/binding.jsp?json=" + oAuth2UserjsonStr + "&sid=" + sid);
			} else if(request.getParameter("auth").equals("0"))
				flag = true;
		} else {
			response.sendRedirect(basePath + "/wct/noPermission.jsp");
			return flag;
		}
		
		return flag;
	}

	
	
	
	
	
	public MemberService getMemberService() {
		return memberService;
	}

	@Resource
	public void setMemberService(MemberService memberService) {
		AuthorizationUtil.memberService = memberService;
	}
	
}

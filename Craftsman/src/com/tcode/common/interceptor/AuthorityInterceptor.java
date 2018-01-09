package com.tcode.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.tcode.core.util.Utils;
import com.tcode.open.wechat.util.AuthorizationUtil;
import com.tcode.system.model.SysAgent;
import com.tcode.system.model.SysUser;

/**
 * 权限验证拦截器
 * @author xiashou
 * @since 2015/04/10
 */
public class AuthorityInterceptor implements Interceptor {

	private static final long serialVersionUID = -7668791469176996612L;
	
	private static Logger log = Logger.getLogger("SLog");
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String requestUri = request.getRequestURI();
		String reqHeader = request.getHeader("Request-By");
		//根据请求header设置错误返回类型
		String retype = (!Utils.isEmpty(reqHeader)) ? "2" : "1";
		String reqStr[] = requestUri.split("/");
		
		if("open|base|boss|mall|pay".indexOf(reqStr[1].toLowerCase()) < 0){
			if(reqStr.length > 2){
				SysUser loginUser = (SysUser) request.getSession().getAttribute("SESSION_USER");
				SysAgent loginAgent = (SysAgent) request.getSession().getAttribute("SESSION_AGENT");
				if(Utils.isEmpty(loginUser) && Utils.isEmpty(loginAgent))
					return "timeout" + retype;
			}
		}
		
		//验证当前访问，拦截微信外部访问，进行鉴权等相关操作
		if("open".equalsIgnoreCase(reqStr[1]) && "wechat".equalsIgnoreCase(reqStr[2]) 
				&& "biz".equalsIgnoreCase(reqStr[3]) && "auth".equalsIgnoreCase(reqStr[4])) {
			boolean flag = AuthorizationUtil.validateAccess(request, response);
			if(!flag) return null;
		}
		
		try {
			result = invocation.invoke();
			return result;
		} catch(Exception e) {
			log.error("IP:" + AuthorityInterceptor.getIpAddress(request) + " | Request:" + requestUri);
			//通过instanceof判断到底是什么异常类型   
            if (e instanceof RuntimeException) {
                //未知的运行时异常   
                RuntimeException re = (RuntimeException) e;
                log.error(re.getMessage().trim());
            }
            log.error(Utils.getErrorMessage(e));
		}
		return result;
	}
	
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	@Override
	public void destroy() {
//		System.out.println("----destroy()----");
	}

	@Override
	public void init() {
//		System.out.println("-----Init()-------"); 
	}


}

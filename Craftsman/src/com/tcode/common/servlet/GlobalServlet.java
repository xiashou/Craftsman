package com.tcode.common.servlet;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tcode.core.util.Utils;
import com.tcode.system.service.SysParamService;

@Component("globalServlet")
public class GlobalServlet extends HttpServlet {

	private static final long serialVersionUID = 4181430302529887362L;
	private static Logger log = Logger.getLogger("SLog");

	@Autowired
	private SysParamService sysParamService;

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	/**
	 * 项目启动时就执行此Servlet 项目启动就加载系统参数信息，放入Application中
	 */
	public void init() throws ServletException {
		try {
			WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
			sysParamService = (SysParamService) wac.getBean("sysParamService");
			// 取得Application对象
			ServletContext application = this.getServletContext();
			Map<String, String> sysParamMap = sysParamService.getSysParamMap();
			application.setAttribute("SysParam", sysParamMap);
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
	}

}

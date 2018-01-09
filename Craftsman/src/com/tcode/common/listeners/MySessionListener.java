package com.tcode.common.listeners;

import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.tcode.core.util.Constant;
import com.tcode.core.util.Utils;

public class MySessionListener implements HttpSessionListener, HttpSessionAttributeListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
        ServletContext application = session.getServletContext();
        @SuppressWarnings("unchecked")
		HashSet<HttpSession> sessions = (HashSet<HttpSession>) application.getAttribute("sessions");
        // 销毁的session均从HashSet集中移除
        sessions.remove(session);
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
        ServletContext application = session.getServletContext();
        
        // 在application范围由一个HashSet集保存所有的session
        @SuppressWarnings("unchecked")
		HashSet<HttpSession> sessions = (HashSet<HttpSession>) application.getAttribute("sessions");
        if (sessions == null) {
               sessions = new HashSet<HttpSession>();
               application.setAttribute("sessions", sessions);
        }
        // 新创建的session均添加到HashSet集中
        if(!Utils.isEmpty(session.getAttribute(Constant.SESSION_USER)))
        	sessions.add(session);
        // 可以在别处从application范围中取出sessions集合
        // 然后使用sessions.size()获取当前活动的session数，即为“在线人数”
		
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
        ServletContext application = session.getServletContext();
        @SuppressWarnings("unchecked")
		HashSet<HttpSession> sessions = (HashSet<HttpSession>) application.getAttribute("sessions");
        // 销毁的session均从HashSet集中移除
        if(!Utils.isEmpty(session.getAttribute(Constant.SESSION_USER)))
        	sessions.remove(session);
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
	}

}

package com.tcode.common.jcrontab.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.jcrontab.Crontab;
import org.jcrontab.log.Log;

public class LoadCrontabServlet extends HttpServlet {
	private Crontab crontab = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			System.out.print("Working?...");
			process();
			System.out.println("OK");
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected InputStream createPropertiesStream(String name) throws IOException {
		return new FileInputStream(name);
	}

	public void process() {
		String propz = "jcrontab.properties";

		String props = getServletConfig().getInitParameter("PROPERTIES_FILE");

		if (props == null) {
			props = propz;
		}
		String dynamicPath = this.getClass().getResource("/").getPath();
		props = dynamicPath + props;
		Properties propObj = new Properties();
		try {
			InputStream input = createPropertiesStream(props);
			propObj.load(input);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		ServletConfig c = getServletConfig();
		Enumeration keys = c.getInitParameterNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			propObj.setProperty(key, c.getInitParameter(key));
		}

		propObj.setProperty("org.jcrontab.data.file", dynamicPath + propObj.getProperty("org.jcrontab.data.file"));
		crontab = Crontab.getInstance();
		try {
//			ShutdownHook();
//			crontab.init(propObj);
		} catch (Exception e) {
			Log.error(e.toString(), e);
		}
	}

	public void ShutdownHook() throws Exception {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				doStop();
			}
		});
	}

	public void destroy() {
		doStop();
	}

	public void doStop() {
		Log.info("Shutting down...");

		crontab.uninit(100);
		Log.info("Stoped");
	}
}

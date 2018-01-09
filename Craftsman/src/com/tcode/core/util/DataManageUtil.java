package com.tcode.core.util;

import java.util.HashMap;
import java.util.Map;

public class DataManageUtil {
	
	private static class dataManageUtilHolder {
		 private static final DataManageUtil dataManageUtil = new DataManageUtil(); 
		 private static Map<Object, Object> map = new HashMap<>();
	}
	
	private DataManageUtil () {}
	public static final DataManageUtil getInstance() {   
		return dataManageUtilHolder.dataManageUtil;
	}
	
	/**
	 * key1-msgNotifyList-通知类短信集合
	 * key2-msgNotifyBirthdayList-生日提醒短信集合
	 * key3-wechatNotifyList-微信通知类集合
	 * @return
	 */
	public static final Map<Object, Object> getMap() {   
		return dataManageUtilHolder.map;
	}

}

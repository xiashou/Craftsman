package org.weixin4j.util;

import org.weixin4j.Configuration;

/**
 * 配置文件工具类
 * 
 * @author supeng
 *
 */
public class ConfigurationUtil {

	private static String systemEncrypt = null;
	private static String systemToken = null;
	private static String systemSymmetricKey = null;
	private static String systemAppid = null;
	private static String systemAppSecret = null;
	private static String systemComponentVerifyTicket = null;

	/**
	 * 获取配置文件配置的Appid
	 *
	 * @return 第三方平台Appid
	 */
	public static String getSystemAppid() {
		if (systemAppid == null) {
			systemAppid = Configuration.getProperty("weixin4j.systemAppid", "weixin4j");
		}
		return systemAppid;
	}
	
	
	/**
	 * 获取配置文件配置的AppSecret
	 *
	 * @return 第三方平台AppSecret
	 */
	public static String getSystemAppSecret() {
		if (systemAppSecret == null) {
			systemAppSecret = Configuration.getProperty("weixin4j.systemAppSecret", "weixin4j");
		}
		return systemAppSecret;
	}
	
	
	/**
	 * 获取配置文件配置的Token
	 *
	 * @return 第三方平台Token
	 */
	public static String getSystemToken() {
		if (systemToken == null) {
			systemToken = Configuration.getProperty("weixin4j.systemToken", "weixin4j");
		}
		return systemToken;
	}

	/**
	 * 获取配置文件配置的SymmetricKey
	 *
	 * @return 第三方平台SymmetricKey
	 */
	public static String getSystemSymmetricKey() {
		if (systemSymmetricKey == null) {
			systemSymmetricKey = Configuration.getProperty("weixin4j.systemSymmetricKey", "weixin4j");
		}
		return systemSymmetricKey;
	}

	/**
	 * 获取配置文件配置的Encrypt
	 *
	 * @return 第三方平台Encrypt
	 */
	public static String getSystemEncrypt() {
		if (systemEncrypt == null) {
			systemEncrypt = Configuration.getProperty("weixin4j.systemEncrypt", "weixin4j");
		}
		return systemEncrypt;
	}
	
	/**
	 * 获取配置文件配置的ComponentVerifyTicket
	 *
	 * @return 第三方平台ComponentVerifyTicket
	 */
	public static String getSystemComponentVerifyTicket() {
		if (systemComponentVerifyTicket == null) {
			systemComponentVerifyTicket = Configuration.getProperty("weixin4j.systemComponentVerifyTicket", "weixin4j");
		}
		return systemComponentVerifyTicket;
	}
	
}

package com.tcode.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ModelUtil {
	/**
	 * 输出一个实体类的全部属性
	 * 
	 * @param model
	 */
	public static String print(Object model) {
		StringBuffer modelString = new StringBuffer();
		Class cls = model.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			char[] buffer = field.getName().toCharArray();
			buffer[0] = Character.toUpperCase(buffer[0]);
			String mothodName = "get" + new String(buffer);
			try {
				Method method = cls.getDeclaredMethod(mothodName);
				Object resutl = method.invoke(model, null);
				modelString.append(field.getName() + ":" + resutl + " | ");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return modelString.toString();
	}

	/**
	 * 输出实体类指定的属性
	 * 
	 * @param model
	 * @param fields
	 */
	public static String print(Object model, String[] fields) {
		StringBuffer modelString = new StringBuffer();
		Class cls = model.getClass();
		for (String field : fields) {
			char[] buffer = field.toCharArray();
			buffer[0] = Character.toUpperCase(buffer[0]);
			String mothodName = "get" + new String(buffer);
			try {
				Method method = cls.getDeclaredMethod(mothodName);
				Object resutl = method.invoke(model, null);
				modelString.append(field + ": " + resutl + "|");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return modelString.toString();
	}
}

package org.weixin4j.message.template;

import org.weixin4j.Weixin;
import org.weixin4j.message.template.TM00504.TM00504;

public class Test {
	
	 public static void main(String[] args) {
		 try {
			 TM00504 tm00504 = new TM00504("我是抬头", "06月07日 19时24分", "0025", 
						"消费", "888.0", "291", "我是备注", null);
			 tm00504.setUrl("");//点击消息跳转URL
				new Weixin().sendTemplateMsgByAuthorizerAppId("wx8208c12eb8f85d78", "oJkwQxJyludXK7-PYB8nJGOScpJo", "TM00504", tm00504);
				new Weixin().sendTemplateMsgByDeptCode("SZ00", "oJkwQxJyludXK7-PYB8nJGOScpJo", "TM00504", tm00504);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}

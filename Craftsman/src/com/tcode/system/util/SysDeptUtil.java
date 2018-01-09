package com.tcode.system.util;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.system.model.SysDept;
import com.tcode.system.service.SysDeptService;

@Component
public class SysDeptUtil {
	
	private static SysDeptService sysDeptService;
	
	/**
	 * 根据门店编码得到门店信息
	 * @param deptCode
	 * @return
	 * @throws Exception 
	 */
	public static SysDept getSysDeptByDeptCode(String deptCode) throws Exception {
		SysDept sysDept = sysDeptService.getByDeptCode(deptCode);
		return sysDept;
	}

	public SysDeptService getSysDeptService() {
		return sysDeptService;
	}

	@Resource
	public void setSysDeptService(SysDeptService sysDeptService) {
		this.sysDeptService = sysDeptService;
	}
	
}

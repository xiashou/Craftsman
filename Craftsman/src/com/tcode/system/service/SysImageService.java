package com.tcode.system.service;

import java.util.List;

import com.tcode.system.model.SysImage;

public interface SysImageService {

	public List<SysImage> getSysImageList(String mode) throws Exception;
}

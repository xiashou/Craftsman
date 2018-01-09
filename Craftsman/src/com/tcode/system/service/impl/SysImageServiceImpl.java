package com.tcode.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.tcode.core.util.Constant;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysImage;
import com.tcode.system.service.SysImageService;

@Component("sysImageService")
public class SysImageServiceImpl implements SysImageService {

	/**
	 * 查询图片
	 * @param pId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysImage> getSysImageList(String mode) throws Exception {
		List<SysImage> imageList = null;
		String path = ServletActionContext.getServletContext().getRealPath("/");
		String before = "";
		if(!Utils.isEmpty(mode) && "mall".equals(mode))
			before = Constant.MALL_DETAIL_IMG_PATH;
		else
			before = Constant.SYS_IMG_PATH;
		File file = new File(path + before);
		File[] fileList = file.listFiles();
		imageList = new ArrayList<SysImage>();
		for (int i = 0; i < fileList.length; i++) {
			SysImage sysImage = new SysImage();
			if (fileList[i].isFile() && valiSuffix(fileList[i].getName())) {
				sysImage.setImgName(fileList[i].getName());
				sysImage.setImgPath(before + fileList[i].getName());
				imageList.add(sysImage);
			}
		}
		return imageList;
	}
	
	protected boolean valiSuffix(String fileName) {
		fileName = fileName.toLowerCase();
		if(fileName.indexOf(".jpg") > 0 || fileName.indexOf(".gif") > 0 || fileName.indexOf(".png") > 0)
			return true;
		return false;
	}
	
}

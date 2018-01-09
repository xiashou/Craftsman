package com.tcode.business.basic.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.basic.model.BaseImage;
import com.tcode.business.basic.service.BaseImageService;
import com.tcode.business.member.model.Member;
import com.tcode.business.member.service.MemberService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("baseImageAction")
public class BaseImageAction extends BaseAction {

	private static final long serialVersionUID = 3244603105247101016L;
	private static Logger log = Logger.getLogger("SLog");
	
	private static final String path = "/upload/album/";
	
	private BaseImageService baseImageService;
	private MemberService memberService;
	
	private List<BaseImage> imageList;
	private BaseImage image;
	private Integer memId;
	
	private File upload;
	private String uploadFileName;
	
	/**
	 * 根据多个ID组成的字符串查询图片
	 * @return
	 */
	public String queryImageByIds() {
		try {
			if(!Utils.isEmpty(memId)) {
				Member member = memberService.getMemberById(memId);
				if(!Utils.isEmpty(member) && !Utils.isEmpty(member.getAlbum())){
					imageList = baseImageService.getByIds(member.getAlbum());
				}
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 图片上传
	 * @return
	 */
	public String uploadImage() {
		try {
			if(!Utils.isEmpty(upload)) {
				if(upload.length() <= 2 * 1024 * 1024) {
					image = new BaseImage();
					
					String realpath = ServletActionContext.getServletContext().getRealPath(path);
					String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
					String suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".")).toLowerCase();
					
					File uploadFile = new File(realpath, name + suffix);
					if (!uploadFile.getParentFile().exists())
						uploadFile.getParentFile().mkdirs();
					
					image.setName(name + suffix);
					image.setPath(path + name + suffix);
					image.setSize((int)(upload.length() / 1024));
					baseImageService.insert(image);
					memberService.updateMemberAlbum(memId, image.getImageId());
					
					FileUtils.copyFile(upload, uploadFile);
					
					this.setResult(true, "上传成功！");
				} else
					this.setResult(false, "图片大小不能超过2M！");
			}
		} catch (NullPointerException e) {
			this.setResult(false, "上传文件不能为空！");
		} catch (Exception e) {
			this.setResult(false, "上传失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 图片删除
	 * @return
	 */
	public String deleteImage() {
		try {
			String realpath = ServletActionContext.getServletContext().getRealPath(path);
			if(!Utils.isEmpty(image) && !Utils.isEmpty(image.getImageId())){
				image = baseImageService.getById(image.getImageId());
				File delFile = new File(realpath, image.getName());
				if (delFile.exists()){
					memberService.deleteMemberAlbum(memId, image.getImageId());
					delFile.delete();
					this.setResult(true, "删除成功！");
				} else
					this.setResult(false, "删除失败！图片不存在！");
			}
		} catch (NullPointerException e) {
			this.setResult(false, "文件不存在！");
		} catch (Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	public BaseImageService getBaseImageService() {
		return baseImageService;
	}
	@Resource
	public void setBaseImageService(BaseImageService baseImageService) {
		this.baseImageService = baseImageService;
	}
	public MemberService getMemberService() {
		return memberService;
	}
	@Resource
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	public List<BaseImage> getImageList() {
		return imageList;
	}
	public void setImageList(List<BaseImage> imageList) {
		this.imageList = imageList;
	}
	public BaseImage getImage() {
		return image;
	}
	public void setImage(BaseImage image) {
		this.image = image;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	
	
}

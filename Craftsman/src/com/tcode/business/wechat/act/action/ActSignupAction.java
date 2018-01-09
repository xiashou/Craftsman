package com.tcode.business.wechat.act.action;

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

import com.tcode.business.wechat.act.model.ActSignup;
import com.tcode.business.wechat.act.service.ActSignupService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("actSignupAction")
public class ActSignupAction extends BaseAction {

	private static final long serialVersionUID = 1168609726247396281L;
	private static Logger log = Logger.getLogger("SLog");
	
	private ActSignupService actSignupService;
	
	private List<ActSignup> signupList;
	
	private ActSignup signup;
	
	private File upload;
	private String uploadFileName;
	
	
	
	public String queryActSignupPage() {
		try {
			WechatAuthorizerParams wechatApp = this.getWechatApp();
			if(!Utils.isEmpty(wechatApp)) {
				if(Utils.isEmpty(signup))
					signup = new ActSignup();
				signup.setAppId(wechatApp.getAuthorizerAppId());
				this.setTotalCount(actSignupService.getListCount(signup));
				signupList = actSignupService.getListPage(signup, this.getStart(), this.getLimit());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String insertActSignup() {
		try {
			if(!Utils.isEmpty(signup)){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					signup.setAppId(wechatApp.getAuthorizerAppId());
					signup.setSignNumber(0);
					signup.setReadNumber(0);
					signup.setCollNumber(0);
					signup.setStatus(1);
					actSignupService.insert(signup);
					this.setResult(true, "添加成功！");
				} else
					this.setResult(false, "参数缺失！");
			} else
				this.setResult(false, "参数缺失！");
		} catch(Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String updateActSignup() {
		try {
			if(!Utils.isEmpty(signup) && !Utils.isEmpty(signup.getId())){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					signup.setAppId(wechatApp.getAuthorizerAppId());
					actSignupService.update(signup);
					this.setResult(true, "编辑成功！");
				} else
					this.setResult(false, "参数缺失！");
			} else
				this.setResult(false, "参数缺失！");
		} catch(Exception e) {
			this.setResult(false, "编辑失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String deleteActSignup() {
		try {
			if(!Utils.isEmpty(signup) && !Utils.isEmpty(signup.getId())){
				actSignupService.delete(signup);
				this.setResult(true, "删除成功！");
			} else
				this.setResult(false, "参数缺失！");
		} catch(Exception e) {
			this.setResult(false, "编辑失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 上传商品图片
	 * @return
	 */
	public String uploadActSignupPictures() {
		try {
			String realpath = ServletActionContext.getServletContext().getRealPath("/upload/wechat/activity");
	        if (!Utils.isEmpty(upload)) {
	        	String suffix;
	        	if(uploadFileName != null && !"".equals(uploadFileName) && uploadFileName.indexOf(".") > 0)
					suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1).toLowerCase();
				else
					suffix = "";
	        	if("jpeg".equals(suffix) || "jpg".equals(suffix) || "png".equals(suffix) || "gif".equals(suffix) || "bmp".equals(suffix)){
	        		uploadFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + suffix;
	        		File savefile = new File(new File(realpath), uploadFileName);
	        		if (!savefile.getParentFile().exists())
	        			savefile.getParentFile().mkdirs();
	        		FileUtils.copyFile(upload, savefile);
	        		this.setResult(true, "上传成功！");
	        	} else
	        		this.setResult(false, "请上传正确的图片格式！");
	        }
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, "上传失败！");
		}
		return SUCCESS;
	}
	

	public ActSignupService getActSignupService() {
		return actSignupService;
	}
	@Resource
	public void setActSignupService(ActSignupService actSignupService) {
		this.actSignupService = actSignupService;
	}

	public List<ActSignup> getSignupList() {
		return signupList;
	}

	public void setSignupList(List<ActSignup> signupList) {
		this.signupList = signupList;
	}

	public ActSignup getSignup() {
		return signup;
	}

	public void setSignup(ActSignup signup) {
		this.signup = signup;
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
	

	
	
}

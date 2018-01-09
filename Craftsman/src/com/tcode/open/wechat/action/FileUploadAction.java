package com.tcode.open.wechat.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

/**
 * 文件上传
 * @author supeng
 *
 */
@Scope("prototype")
@Component("FileUploadAction")
public class FileUploadAction extends BaseAction {
	
	private File file;
	private String fileName;
	
	/**
	 * 微信文件上传
	 * @return
	 * @throws FileNotFoundException 
	 */
	public String mpFileUpload() {
		//取得根目录路径  
		String rootPath = getRequest().getSession().getServletContext().getRealPath("/");  
		String savePath = rootPath + fileName;
		FileInputStream fileInputStream = null;
		FileOutputStream outputStream = null;
		BufferedReader br = null;
		BufferedWriter out = null;
		try {
			fileInputStream = new FileInputStream(file);
//			outputStream = new FileOutputStream(savePath);
//			byte[] b = new byte[1024];
//			while(fileInputStream.read(b) != -1) {
//				outputStream.write(b);
//			}
//			outputStream.flush();
			
            InputStreamReader reader = new InputStreamReader(fileInputStream); // 建立一个输入流对象reader
            br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            File writename = new File(savePath); 
            writename.createNewFile(); // 创建新文件
            out = new BufferedWriter(new FileWriter(writename));
            String line = "";
            while (!Utils.isEmpty(line = br.readLine())) {
                out.write(line);
            }
            out.flush();
            
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}finally {
			try {
				if(outputStream != null) outputStream.close();
				if(fileInputStream != null) fileInputStream.close();
				if(br != null) br.close();
				if(out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return SUCCESS;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}

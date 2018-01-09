package com.tcode.business.basic.service;

import java.util.List;

import com.tcode.business.basic.model.BaseImage;

public interface BaseImageService {

	/**
	 * 根据id查找
	 * @param id
	 * @return BaseImage
	 * @throws Exception
	 */
	public BaseImage getById(Integer id) throws Exception;
	
	/**
	 * 根据会员ID查询会员相册
	 * @return
	 * @throws Exception
	 */
	public List<BaseImage> getByIds(String ids) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void insert(BaseImage image) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void update(BaseImage image) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void delete(BaseImage image) throws Exception;
}

package com.tcode.business.basic.dao;

import java.util.List;

import com.tcode.business.basic.model.BaseImage;

public interface BaseImageDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return BaseImage
	 * @throws Exception
	 */
	public BaseImage loadById(Integer id) throws Exception;
	
	/**
	 * 根据会员ID查询会员相册
	 * @return
	 * @throws Exception
	 */
	public List<BaseImage> loadByIds(String ids) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(BaseImage image) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(BaseImage image) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(BaseImage image) throws Exception;
	
}

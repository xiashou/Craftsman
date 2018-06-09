USE [Craftsman]
GO

--------------------------------- 系统管理 -------------------------------------

/**
 *系统参数表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sys_param]') AND type in (N'U'))
DROP TABLE [dbo].[sys_param]
GO

CREATE TABLE [dbo].[sys_param](
	[id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[param_key] [varchar](100) NOT NULL,
	[param_value] [varchar](255) NULL,
	[remark] [nvarchar](500) NULL
)
GO

SET ANSI_PADDING OFF
GO


/**
 *系统菜单表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sys_menu]') AND type in (N'U'))
DROP TABLE [dbo].[sys_menu]
GO

CREATE TABLE [dbo].[sys_menu](
	[id] [varchar](100) NOT NULL PRIMARY KEY,			--菜单编号
	[menu_name] [nvarchar](50) NOT NULL,				--菜单名称
	[parent_id] [varchar](100) NOT NULL,				--上级菜单编号
	[menu_url] [varchar](500) NULL,						--请求地址
	[expanded] [int] DEFAULT 0,							--展开状态(1:展开;0:收缩)
	[leaf] [int] NOT NULL DEFAULT 0,					--叶子节点(0:树枝节点;1:叶子节点)
	[icon_cls] [varchar](50) DEFAULT NULL,				--节点图标CSS类名
	[sort_no] [int] DEFAULT NULL,						--排序号
	[menu_type] [varchar](2) DEFAULT '0'				--菜单类型(1:系统菜单;0:业务菜单)
)
GO

SET ANSI_PADDING OFF
GO



/**
 *系统部门表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sys_dept]') AND type in (N'U'))
DROP TABLE [dbo].[sys_dept]
GO

CREATE TABLE [dbo].[sys_dept](
	[id] [varchar](100) NOT NULL PRIMARY KEY,
	[dept_name] [nvarchar](80) NOT NULL,
	[parent_id] [varchar](100) NOT NULL,
	[dept_type]	[int] NOT NULL,
	[dept_code] [varchar](30),
	[company_id] [varchar](100),
	[area_id] [varchar](20),
	[enable] [int] DEFAULT 1 NOT NULL,
	[create_time] [varchar](30)
)
GO

SET ANSI_PADDING OFF
GO


/**
 *系统用户表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sys_user]') AND type in (N'U'))
DROP TABLE [dbo].[sys_user]
GO

CREATE TABLE [dbo].[sys_user](
	[user_id] [int] IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
	[user_name] [varchar](30) NOT NULL,
	[password] [varchar](255) NULL,
	[dept_id] [varchar](100) NOT NULL,
	[real_name] [nvarchar](30) NULL,
	[role_id] [int] NULL,
	[locked] [int] DEFAULT 0,
	[last_login] [varchar](30) NULL
)
GO

SET ANSI_PADDING OFF
GO


/**
 *系统角色表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sys_role]') AND type in (N'U'))
DROP TABLE [dbo].[sys_role]
GO

CREATE TABLE [dbo].[sys_role](
	[role_id] [int] IDENTITY(1000, 1) NOT NULL PRIMARY KEY,
	[role_name] [nvarchar](80) NOT NULL,
	[dept_id] [varchar](100) NOT NULL,
	[role_type] [varchar](2) NOT NULL DEFAULT '1',
	[locked] [int] DEFAULT 0
)
GO

SET ANSI_PADDING OFF
GO


/**
 *代理用户表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sys_agent]') AND type in (N'U'))
DROP TABLE [dbo].[sys_agent]
GO

CREATE TABLE [dbo].[sys_agent](
	[agent_id] [int] IDENTITY(10, 1) NOT NULL PRIMARY KEY,
	[agent_name] [varchar](30) NOT NULL,
	[password] [varchar](255) NULL,
	[area_id] [varchar](100) NOT NULL,
	[role_id] [int] NULL,
	[real_name] [nvarchar](30) NULL,
	[locked] [int] DEFAULT 0,
	[last_login] [varchar](30) NULL
)
GO

SET ANSI_PADDING OFF
GO



/**
 *菜单按钮表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sys_button]') AND type in (N'U'))
DROP TABLE [dbo].[sys_button]
GO

CREATE TABLE [dbo].[sys_button](
	[btn_id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[btn_key] [varchar](20) NOT NULL,
	[btn_name] [nvarchar](80) NULL,
	[menu_id] [varchar](100) NOT NULL
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 角色菜单关联表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sys_role_menu]') AND type in (N'U'))
DROP TABLE [dbo].[sys_role_menu]
GO

CREATE TABLE [dbo].[sys_role_menu](
	[role_id] [int] NOT NULL,
	[menu_id] [varchar](100) NOT NULL,
	[buttons] [varchar](500) NULL
)
GO

ALTER TABLE [dbo].[sys_role_menu] ADD CONSTRAINT pk_role_menu PRIMARY KEY([role_id],[menu_id])
GO

SET ANSI_PADDING OFF
GO

/**
 * 编号表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sys_sequence]') AND type in (N'U'))
DROP TABLE [dbo].[sys_sequence]
GO

CREATE TABLE [dbo].[sys_sequence](
	[id] [int] IDENTITY(1000, 1) NOT NULL PRIMARY KEY,
	[field_name] [varchar](50) NOT NULL,
	[max_id] [varchar](50) NOT NULL,
	[pattern] [varchar](50) NOT NULL,
	[remark] [varchar](100),
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 操作指引表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sys_help]') AND type in (N'U'))
DROP TABLE [dbo].[sys_help]
GO

CREATE TABLE [dbo].[sys_help](
	[id] [varchar](100) NOT NULL PRIMARY KEY,
	[name] [varchar](200),
	[text] [text]
)
GO

SET ANSI_PADDING OFF
GO


--------------------------------- 基础信息 -------------------------------------

/**
 * 区域简称代码表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[base_area]') AND type in (N'U'))
DROP TABLE [dbo].[base_area]
GO

CREATE TABLE [dbo].[base_area](
	[area_id] [varchar](20) NOT NULL PRIMARY KEY,
	[area_name] [varchar](100) NOT NULL,
	[area_short] [varchar](10) NULL,
	[area_code] [varchar](2) NULL
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 车牌区域简称表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[base_area_short]') AND type in (N'U'))
DROP TABLE [dbo].[base_area_short]
GO

CREATE TABLE [dbo].[base_area_short](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,
	[area_short] [varchar](10) NOT NULL
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 车牌区域代码表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[base_area_code]') AND type in (N'U'))
DROP TABLE [dbo].[base_area_code]
GO

CREATE TABLE [dbo].[base_area_code](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,
	[area_code] [varchar](10) NOT NULL
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 汽车品牌表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[base_car_brand]') AND type in (N'U'))
DROP TABLE [dbo].[base_car_brand]
GO

CREATE TABLE [dbo].[base_car_brand](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,
	[short_code] [varchar](50) NOT NULL,
	[brand_name] [varchar](100) NOT NULL,
	[logo] [varchar](100)
)
GO

SET ANSI_PADDING OFF
GO

/**
 * 汽车系列表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[base_car_series]') AND type in (N'U'))
DROP TABLE [dbo].[base_car_series]
GO

CREATE TABLE [dbo].[base_car_series](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,
	[brand_id] [int] NOT NULL,
	[series_name] [varchar](200) NOT NULL
)
GO

SET ANSI_PADDING OFF
GO

/**
 * 汽车型号表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[base_car_model]') AND type in (N'U'))
DROP TABLE [dbo].[base_car_model]
GO

CREATE TABLE [dbo].[base_car_model](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,
	[series_id] [int] NOT NULL,
	[model_name] [varchar](500) NOT NULL
)
GO

SET ANSI_PADDING OFF
GO

/**
 * 图片表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[base_image]') AND type in (N'U'))
DROP TABLE [dbo].[base_image]
GO

CREATE TABLE [dbo].[base_image](
	[image_id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,	--ID
	[name] [varchar](50) NOT NULL,							--名称
	[path] [varchar](300) NOT NULL,							--路径
	[size] [int]											--大小（KB）
)
GO

SET ANSI_PADDING OFF
GO

--------------------------------- 会员管理 -------------------------------------

/**
 * 会员基本信息表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mem_member]') AND type in (N'U'))
DROP TABLE [dbo].[mem_member]
GO

CREATE TABLE [dbo].[mem_member](
	[id] [int] IDENTITY(100000, 1) NOT NULL PRIMARY KEY,
	[vip_no] [varchar](10) NOT NULL,						--会员卡号
	[company_id] [varchar](100) NOT NULL,					--公司ID
	[dept_code] [varchar](30) NOT NULL,						--门店代码
	[name] [varchar](100) NOT NULL,							--姓名
	[mobile] [varchar](20) NOT NULL,						--手机号码
	[sex] [varchar](2) DEFAULT '0',							--性别
	[grade] [int] DEFAULT 1,								--等级
	[appId]	[varchar](300),									--微信ID
	[wechat_no] [varchar](300),								--微信号
	[wechat_head] [varchar](300),							--微信头像
	[wechat_nick] [varchar](500),							--微信昵称
	[album]	[varchar](1000),								--相册
	[source] [varchar](20),									--来源
	[balance] [decimal](18, 6) DEFAULT 0,					--余额
	[point] [int] DEFAULT 0,								--积分
	[picture] [varchar](200),								--头像
	[birthday] [varchar](20),								--生日
	[create_time] [varchar](20),							--创建日期
	[sales] [varchar](20),									--销售顾问
	[remark] [varchar](500)									--备注
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 会员车辆信息表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mem_cars]') AND type in (N'U'))
DROP TABLE [dbo].[mem_cars]
GO

CREATE TABLE [dbo].[mem_cars](
	[id] [int] IDENTITY(1000000, 1) NOT NULL PRIMARY KEY,
	[member_id] [int] NOT NULL,				--会员ID
	[car_short] [varchar](5) NOT NULL,		--省份缩写
	[car_code] [varchar](5) NOT NULL,		--城市代码
	[car_number] [varchar](10) NOT NULL,	--车牌号
	[car_brand] [int],						--品牌ID
	[car_series] [int],						--系列ID
	[car_model] [int],						--型号ID
	[car_frame] [varchar](100),				--车架号
	[car_engine] [varchar](100),			--发动机号
	[car_kilometers] [int],					--进厂公里数
	[car_nextkilo] [int],					--下次进厂公里数
	[car_inspection] [varchar](20),			--年检时间
	[car_maintain] [varchar](20),			--保养日期
	[car_insurance] [varchar](20),			--保险时间
	[car_insu_company] [varchar](100),		--保险公司
	[car_illegal] [varchar](100),			--违章
	[car_mobile]	[varchar](20)			--手机号码
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 会员等级表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mem_grade]') AND type in (N'U'))
DROP TABLE [dbo].[mem_grade]
GO

CREATE TABLE [dbo].[mem_grade](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,
	[dept_code] [varchar](30),							--门店代码
	[grade] [int] NOT NULL,								--会员等级
	[name] [nvarchar](100) NOT NULL,					--等级名
	[need_point] [int] NOT NULL,						--等级所需积分
	[discount] [decimal](8, 6),							--等级折扣
	[order_discount] [decimal](8, 6),					--预约折扣
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 会员价格表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mem_grade_price]') AND type in (N'U'))
DROP TABLE [dbo].[mem_grade_price]
GO

CREATE TABLE [dbo].[mem_grade_price](	
	[goods_id] [varchar](10) NOT NULL,		--商品ID
	[grade_id] [int] NOT NULL,				--会员ID
	[dept_code] [varchar](30) NOT NULL,		--门店代码
	[mprice] [decimal](18, 6),				--会员价格
	[oprice] [decimal](18, 6)				--预约价格
)
GO

ALTER TABLE [dbo].[mem_grade_price] ADD CONSTRAINT pk_goods_grade_dept PRIMARY KEY([goods_id],[grade_id],[dept_code])
GO

SET ANSI_PADDING OFF
GO


/**
 * 会员库存表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mem_stock]') AND type in (N'U'))
DROP TABLE [dbo].[mem_stock]
GO

CREATE TABLE [dbo].[mem_stock](
	[mem_id] [int] NOT NULL,				--会员ID
	[goods_id] [varchar](10) NOT NULL,		--商品ID
	[end_date]	[varchar](20) NOT NULL,		--到期日期
	[source] [varchar](50) NOT NULL,		--来源
	[goods_type] [int] NOT NULL,			--类型(1、工时类 2、实物类 3、工时赠 4、实物赠 5、卡券 6、套餐)
	[type_id] [varchar](20),				--商品类型
	[goods_name] [varchar](300),			--商品名
	[number] [int] NOT NULL,				--数量
	
)
GO

ALTER TABLE [dbo].[mem_stock] ADD CONSTRAINT pk_member_goods_enddate_source PRIMARY KEY([mem_id],[goods_id],[end_date],[source])
GO

SET ANSI_PADDING OFF
GO


/**
 * 客户回访表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mem_visit]') AND type in (N'U'))
DROP TABLE [dbo].[mem_visit]
GO

CREATE TABLE [dbo].[mem_visit](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,
	[dept_code] [varchar](30),				--门店代码
	[mem_id] [int] NOT NULL,				--会员ID
	[type_id] [int] NOT NULL,				--类型ID（1：保养到期；2：保险到期；3：年检到期；4：客户流失；5：剩余服务到期；6：余额不足）
	[car_id] [int],							--商品ID
	[last_sale]	[varchar](20),				--最后消费日期
	[goods_id]	[varchar](10),				--剩余服务ID
	[end_date]	[varchar](20),				--到期日期
	[balance] [decimal](18, 6),				--余额
	[result] [varchar](1000),				--结果
	[visitor] [varchar](50),				--回访人员
	[visit_time]	[varchar](20),			--回访时间
	[create_time]	[varchar](20),			--创建时间
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 客户预约表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mem_book]') AND type in (N'U'))
DROP TABLE [dbo].[mem_book]
GO

CREATE TABLE [dbo].[mem_book](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,
	[dept_code] 	[varchar](30),				--门店代码
	[mem_id] 		[int] NOT NULL,				--会员ID
	[book_time]		[varchar](20),				--预约时间
	[phone]			[varchar](20),				--联系电话
	[car_number]	[varchar](20),				--车牌号码
	[service] 		[varchar](500),				--预约服务
	[status]		[int],						--状态
	[create_time]	[varchar](20),				--创建时间
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 预约温馨提示表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mem_book_tips]') AND type in (N'U'))
DROP TABLE [dbo].[mem_book_tips]
GO

CREATE TABLE [dbo].[mem_book_tips](
	[dept_code] 	[varchar](30) NOT NULL PRIMARY KEY,				--门店代码
	[tips]			[varchar](max),									--预约温馨提示
)
GO

SET ANSI_PADDING OFF
GO

--------------------------------- 商品管理 -------------------------------------


/**
 * 工时商品类别表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[god_hour_type]') AND type in (N'U'))
DROP TABLE [dbo].[god_hour_type]
GO

CREATE TABLE [dbo].[god_hour_type](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[dept_code] [varchar](30) NOT NULL,					--门店代码
	[type_name] [nvarchar](100),						--类型名称
	[sort_no] [int]										--排序码
)
GO

SET ANSI_PADDING OFF
GO

/**
 * 工时商品表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[god_hour]') AND type in (N'U'))
DROP TABLE [dbo].[god_hour]
GO

CREATE TABLE [dbo].[god_hour](
	[id] [varchar](10) NOT NULL PRIMARY KEY,			--ID，自增长
	[dept_code] [varchar](30) NOT NULL,					--门店代码
	[type_id] [int] NOT NULL,							--类型代码
	[name] [nvarchar](300) NOT NULL,					--名称
	[shorthand] [varchar](50),							--简称
	[price] [decimal](18, 6),							--工时费
	[remind_id]	[int]									--提醒ID
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 卡券
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[god_coupons]') AND type in (N'U'))
DROP TABLE [dbo].[god_coupons]
GO

CREATE TABLE [dbo].[god_coupons](
	[id] [varchar](50) NOT NULL PRIMARY KEY,			--ID
	[dept_code] [varchar](30) NOT NULL,					--门店代码
	[name] [nvarchar](300) NOT NULL,					--名称
	[price] [decimal](18, 6),							--价格
	[apply_id]	[varchar](100),							--适用服务
	[minimum]	[decimal](18, 6)						--最低限度
)
GO

SET ANSI_PADDING OFF
GO



/**
 * 实物商品类别表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[god_material_type]') AND type in (N'U'))
DROP TABLE [dbo].[god_material_type]
GO

CREATE TABLE [dbo].[god_material_type](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[dept_code] [varchar](30) NOT NULL,					--门店代码
	[type_name] [nvarchar](100) NOT NULL,				--类型名称
	[sort_no] [int] DEFAULT NULL,
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 实物商品表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[god_material]') AND type in (N'U'))
DROP TABLE [dbo].[god_material]
GO

CREATE TABLE [dbo].[god_material](
	[id] [varchar](10) NOT NULL PRIMARY KEY,			--ID，自增长
	[company_id] [varchar](100) NOT NULL,				--公司ID
	[dept_code] [varchar](30) NOT NULL,					--门店代码
	[code] [varchar](100),								--自定义编码
	[name] [nvarchar](300) NOT NULL,					--名称
	[shorthand] [varchar](300),							--简称
	[type_id] [int] NOT NULL,                           --类型ID
	[price] [decimal](18, 2),							--零售价
	[in_price] [decimal](18, 2),						--进货均价
	[spec] [nvarchar](50),								--规格
	[color] [nvarchar](50),								--颜色
	[size] [nvarchar](50),								--尺码
	[unit] [nvarchar](50),								--单位
	[suit_model] [nvarchar](200),						--适用车型
	[remark] [nvarchar](300),							--备注
)
GO

SET ANSI_PADDING OFF
GO

/**
 * 实物商品入库表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[god_instock]') AND type in (N'U'))
DROP TABLE [dbo].[god_instock]
GO

CREATE TABLE [dbo].[god_instock](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[dept_code] [varchar](30) NOT NULL,					--门店代码
	[goods_id] [varchar](10) NOT NULL,					--商品ID
	[goods_name] [nvarchar](300) NOT NULL,				--商品名称
	[number] [decimal](18, 6) NOT NULL,					--数量
	[in_price] [decimal](18, 6),						--进价
	[supplier]	[int],									--供应商
	[settlement] [int],									--是否结算
	[settdate]	[varchar](30),							--结算时间
	[purchdate]	[varchar](30),							--采购时间
	[creator]	[int],									--操作人
	[create_time]	[varchar](30)						--操作时间
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 实物商品调拨头表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[god_move_head]') AND type in (N'U'))
DROP TABLE [dbo].[god_move_head]
GO

CREATE TABLE [dbo].[god_move_head](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[dept_out] [varchar](30) NOT NULL,					--调出门店代码
	[dept_in] [varchar](30) NOT NULL,					--调入门店代码
	[total] [decimal](18, 6),							--商品总数量
	[status]	[int],									--状态（0：调出，1：完成）
	[creator]	[varchar](30) NOT NULL,					--操作人
	[create_time]	[varchar](30),						--操作时间
	[updator]	[varchar](30),							--收货人
	[update_time]	[varchar](30)						--收货时间
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 实物商品调拨项目表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[god_move_item]') AND type in (N'U'))
DROP TABLE [dbo].[god_move_item]
GO

CREATE TABLE [dbo].[god_move_item](
	[move_id] [int] NOT NULL,							--ID，自增长
	[item_no] [int] NOT NULL,							--行项目,主键
	[goods_id] [varchar](10) NOT NULL,					--商品ID
	[goods_name] [nvarchar](300),						--商品名称
	[number] [decimal](18, 6) NOT NULL,					--数量
	[in_price] [decimal](18, 6)							--进价
)
GO

ALTER TABLE [dbo].[god_move_item] ADD CONSTRAINT pk_move_item PRIMARY KEY([move_id],[item_no])
GO

SET ANSI_PADDING OFF
GO


/**
 * 实物商品库存表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[god_stock]') AND type in (N'U'))
DROP TABLE [dbo].[god_stock]
GO

CREATE TABLE [dbo].[god_stock](
	[goods_id] [varchar](10) NOT NULL,			--商品ID
	[dept_code] [varchar](30) NOT NULL,			--门店代码
	[number] [int],								--数量
)
GO

ALTER TABLE [dbo].[god_stock] ADD CONSTRAINT pk_good_dept PRIMARY KEY([goods_id],[dept_code])
GO

SET ANSI_PADDING OFF
GO

/**
 * 套餐商品表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[god_package]') AND type in (N'U'))
DROP TABLE [dbo].[god_package]
GO

CREATE TABLE [dbo].[god_package](
	[id] [varchar](20) NOT NULL PRIMARY KEY,			--ID,程序生成
	[dept_code] [varchar](30) NOT NULL,					--门店代码
	[name] [nvarchar](300) NOT NULL,					--套餐名称
	[price] [decimal](18, 6),							--套餐零售价
	[range] [int] NOT NULL,								--适用范围
	[start_date] [varchar](20),							--开始日期
	[end_date] [varchar](20),							--结束日期
	[expire] [int],										--有效期
	[create_time] [varchar](20),						--创建日期
	[explain] [varchar](1000),							--套餐说明
	[status] [varchar](2)								--状态
)
GO

SET ANSI_PADDING OFF
GO

/**
 * 盘点记录表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[god_check_record]') AND type in (N'U'))
DROP TABLE [dbo].[god_check_record]
GO

CREATE TABLE [dbo].[god_check_record](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[dept_code] [varchar](30) NOT NULL,					--门店代码
	[goods_id] [varchar](10) NOT NULL,					--商品ID
	[old_number] [decimal](18, 6),						--旧库存数
	[new_number] [decimal](18, 6),						--新库存数
	[creator] [varchar](20),							--操作人
	[create_time] [varchar](20),						--创建日期
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 套餐明细表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[god_package_detail]') AND type in (N'U'))
DROP TABLE [dbo].[god_package_detail]
GO

CREATE TABLE [dbo].[god_package_detail](
	[gp_id] [varchar](20) NOT NULL,						--套餐ID,主键
	[item_no] [int] NOT NULL,							--行项目,主键
	[goods_id] [int] NOT NULL,							--商品ID（包括工时类和实物类）
	[goods_type] [int] NOT NULL,						--商品类型1：工时 2：实物
	[type_id]	[varchar](100),							--商品类型ID
	[goods_name] [nvarchar](300),						--商品名称
	[number] [int]										--数量/次数
)
GO

ALTER TABLE [dbo].[god_package_detail] ADD CONSTRAINT pk_good_item PRIMARY KEY([gp_id],[item_no])
GO

SET ANSI_PADDING OFF
GO

--------------------------------- 订单管理 -------------------------------------



/**
 * 订单头表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[order_head]') AND type in (N'U'))
DROP TABLE [dbo].[order_head]
GO

CREATE TABLE [dbo].[order_head](
	[order_id]	[varchar](20) NOT NULL PRIMARY KEY,		--订单号，项目生成
	[order_type]	[varchar](10)NOT NULL,				--订单类型
	[dept_code]	[varchar](30) NOT NULL,					--门店代码
	[mem_id]	[int] NOT NULL,							--会员ID
	[vip_no]	[varchar](10),							--会员卡号
	[car_id]	[int],									--车辆ID
	[car_number]	[varchar](20),						--车牌号
	[sale_date]	[varchar](20),							--消费日期
	[oprice]	[decimal](18, 6),						--应收价格
	[aprice]	[decimal](18, 6),						--实收价格
	[point]		[int],									--所得积分
	[status]	[int] NOT NULL,							--订单状态(0:已删除 1:已完成 2:已开单待取车 3:已通知待取车 )
	[poffset]	[int],									--抵现积分
	[pdeposit]	[decimal](18, 6),						--支付定金
	[pbalance]	[decimal](18, 6),						--支付余额
	[pcash]		[decimal](18, 6),						--支付现金
	[pcard]		[decimal](18, 6),						--支付刷卡
	[ptransfer]	[decimal](18, 6),						--支付转账
	[pwechat]	[decimal](18, 6),						--支付微信
	[palipay]	[decimal](18, 6),						--支付其他
	[pbill]		[decimal](18, 6),						--支付挂账
	[mainten]	[int],									--保养公里数
	[estimate]	[varchar](20),							--预计交车时间
	[print_count]	[int],								--打印次数
	[inscompany]	[nvarchar](50),						--保险公司
	[sender]		[nvarchar](50),						--送修人
	[commission]	[decimal](18, 6),					--提成
	[creator]		[varchar](50),						--开单人员
	[create_time]	[varchar](20),						--创建时间
	[remark]		[nvarchar](1000)					--备注
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 订单项目表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[order_item]') AND type in (N'U'))
DROP TABLE [dbo].[order_item]
GO

CREATE TABLE [dbo].[order_item](
	[order_id] [varchar](20) NOT NULL,					--订单ID,主键
	[item_no] [int] NOT NULL,							--行项目,主键
	[goods_type] [int] NOT NULL,						--商品类别(1、工时类 2、实物类 3、工时赠 4、实物赠 5、卡券 6、套餐)
	[type_id]	[int],									--商品类型
	[goods_id] [varchar](10) NOT NULL,					--商品ID
	[goods_name] [varchar](500),						--商品名
	[unit_price] [decimal](18, 6),						--单价
	[number] [int],										--数量/次数
	[discount] [decimal](18, 6),						--折扣
	[price]	[decimal](18, 6),							--金额
	[performer] [varchar](255),							--实施方
	[seller]	[varchar](255),							--销售方
	[middleman]	[varchar](255),							--间接销售方
	[is_deduction] [int],								--是否为扣次项0\1\2
	[source] [varchar](255),							--扣次来源
	[end_date] [varchar](20)							--扣次项到期日期
)
GO

ALTER TABLE [dbo].[order_item] ADD CONSTRAINT pk_order_item PRIMARY KEY([order_id],[item_no])
GO

SET ANSI_PADDING OFF
GO


--------------------------------- 财务管理 -------------------------------------

/**
 * 资产管理表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fico_assets]') AND type in (N'U'))
DROP TABLE [dbo].[fico_assets]
GO

CREATE TABLE [dbo].[fico_assets](
	[id]	[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长（主键）
	[dept_code] [varchar](30) NOT NULL, 					--门店代码
	[code]	[varchar](5),									--代码
	[name]	[varchar](100) NOT NULL,						--名称
	[price]	[decimal](18, 6),								--金额
	[editable]	[int] DEFAULT 1								--可编辑性(0不可编辑，1可编辑)
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 应收总计表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fico_receivable]') AND type in (N'U'))
DROP TABLE [dbo].[fico_receivable]
GO

CREATE TABLE [dbo].[fico_receivable](
	[dept_code] [varchar](30) NOT NULL, 					--门店代码
	[mem_id]	[int] NOT NULL,								--会员ID
	[car_id]	[int] NOT NULL,								--车辆ID
	[bill_price]	[decimal](18, 6),						--总挂账金额
	[repayment]		[decimal](18, 6),						--总还款金额
	[status]		[int],									--状态，0未还完 1已结账
	[last_update]	[varchar](20)							--最后修改时间
)
GO

ALTER TABLE [dbo].[fico_receivable] ADD CONSTRAINT pk_dept_mem_car PRIMARY KEY([dept_code],[mem_id],[car_id])
GO

SET ANSI_PADDING OFF
GO


/**
 * 应收坏账记录
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fico_baddebt]') AND type in (N'U'))
DROP TABLE [dbo].[fico_baddebt]
GO

CREATE TABLE [dbo].[fico_baddebt](
	[id]	[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长（主键）
	[dept_code] [varchar](30) NOT NULL, 					--门店代码
	[mem_id]	[int] NOT NULL,								--会员ID
	[car_id]	[int] NOT NULL,								--车辆ID
	[baddebt]	[decimal](18, 6),							--总坏账金额
	[creator]		[varchar](50),							--创建人
	[create_time]	[varchar](50)							--创建日期
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 还款记录表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fico_repayment]') AND type in (N'U'))
DROP TABLE [dbo].[fico_repayment]
GO

CREATE TABLE [dbo].[fico_repayment](
	[id]	[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长（主键）
	[dept_code] [varchar](30), 								--门店代码
	[mem_id]	[int],										--会员ID
	[car_id]	[int],										--车辆ID
	[supplier_id]	[int],									--供应商ID
	[repayment]	[decimal](18, 6),							--还款金额
	[pay_type]	[varchar](5),								--还款方式
	[remark]	[varchar](500),								--备注
	[creator]	[int],										--创建人
	[create_time]	[varchar](20)							--创建时间
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 应付总计表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fico_payable]') AND type in (N'U'))
DROP TABLE [dbo].[fico_payable]
GO

CREATE TABLE [dbo].[fico_payable](
	[dept_code] [varchar](30) NOT NULL, 					--门店代码
	[supplier_id]	[int] NOT NULL,							--供应商代码
	[payable]		[decimal](18, 6),						--总应付金额
	[repayment]		[decimal](18, 6),						--总付款金额
	[status]		[int],									--状态，0未还完 1已结账
	[last_update]	[varchar](20)							--最后修改时间
)
GO

ALTER TABLE [dbo].[fico_payable] ADD CONSTRAINT pk_dept_supp PRIMARY KEY([dept_code],[supplier_id])
GO

SET ANSI_PADDING OFF
GO



/**
 * 支出记录表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fico_expenditure]') AND type in (N'U'))
DROP TABLE [dbo].[fico_expenditure]
GO

CREATE TABLE [dbo].[fico_expenditure](
	[id]	[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长（主键）
	[dept_code] [varchar](30) NOT NULL, 					--门店代码
	[exptype_id]	[int] NOT NULL,							--支出类型
	[assets_id]	[int] NOT NULL,								--支出来源
	[price]		[decimal](18, 6),							--支出金额
	[remark]		[varchar](500),							--备注
	[creator]		[varchar](100),							--创建人
	[create_time]	[varchar](20)							--创建时间
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 支出类型表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fico_exptype]') AND type in (N'U'))
DROP TABLE [dbo].[fico_exptype]
GO

CREATE TABLE [dbo].[fico_exptype](
	[id]	[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长（主键）
	[dept_code] [varchar](30) NOT NULL, 					--门店代码
	[name]	[nvarchar](200) NOT NULL,						--类型名称
)
GO

SET ANSI_PADDING OFF
GO


--------------------------------- 店铺设置 -------------------------------------

/**
 * 店铺充值设置头表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[shop_recharge]') AND type in (N'U'))
DROP TABLE [dbo].[shop_recharge]
GO

CREATE TABLE [dbo].[shop_recharge](
	[id]	[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[dept_code] [varchar](30) NOT NULL, 					--门店代码（主键）
	[name]	[varchar](100) NOT NULL,						--名称
	[price]	[decimal](18, 6) NOT NULL,						--充值金额
	[mode]	[int] DEFAULT 0									--赠送方式(多选多，0为全部赠送)
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 店铺充值设置明细表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[shop_recharge_detail]') AND type in (N'U'))
DROP TABLE [dbo].[shop_recharge_detail]
GO

CREATE TABLE [dbo].[shop_recharge_detail](
	[recharge_id]	[int] NOT NULL,	--头表ID
	[item_no]	[int] NOT NULL,		--行项目
	[type]	[int] NOT NULL,			--赠送类型（金额、服务、商品、卡券、积分、会员等级）
	[value] [varchar](50),			--ID或者金额
	[dname] [varchar](50),			--名称
	[number]	[int],				--次数
	[days]	[int],					--有效天数
	[dprice]	[decimal](18, 6),		--价值
	
)
GO

ALTER TABLE [dbo].[shop_recharge_detail] ADD CONSTRAINT pk_recharge_detail PRIMARY KEY([recharge_id],[item_no])
GO

SET ANSI_PADDING OFF
GO

--------------------------------- 报表管理 -------------------------------------

/**
 * 会员充值流水表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[rept_recharge]') AND type in (N'U'))
DROP TABLE [dbo].[rept_recharge]
GO

CREATE TABLE [dbo].[rept_recharge](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,				--ID，自增长
	[dept_code] [varchar](30) NOT NULL,							--门店代码
	[mem_id] [int] NOT NULL,									--会员ID
	[order_no]	[varchar](20),									--单据号
	[goods_id] [varchar](300),									--商品ID
	[goods_name] [varchar](300),								--商品名
	[number] [decimal](18, 6) NOT NULL,							--数量/金额
	[create_time]	[varchar](30)								--创建时间
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 商品历史流水表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[rept_goods]') AND type in (N'U'))
DROP TABLE [dbo].[rept_goods]
GO

CREATE TABLE [dbo].[rept_goods](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,				--ID，自增长
	[dept_code] [varchar](30) NOT NULL,							--门店代码
	[order_no]	[varchar](20),									--单据号
	[goods_id] [varchar](300),									--商品ID
	[type]	[varchar](10),										--类型S:售出 R:退货 I:入库
	[goods_name] [varchar](300),								--商品名
	[number] [decimal](18, 6) NOT NULL,							--数量/金额
	[create_time]	[varchar](30)								--创建时间
)
GO

SET ANSI_PADDING OFF
GO

/**
 * 会员库存流水表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[rept_member_stock]') AND type in (N'U'))
DROP TABLE [dbo].[rept_member_stock]
GO

CREATE TABLE [dbo].[rept_member_stock](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,				--ID，自增长
	[dept_code] [varchar](30) NOT NULL,							--门店代码
	[mem_id] [int] NOT NULL,									--会员ID
	[order_no]	[varchar](20),									--单据号
	[goods_id] [varchar](300),									--商品ID
	[goods_name] [varchar](300),								--商品名
	[number] [decimal](18, 6) NOT NULL,							--数量/金额
	[create_time]	[varchar](30)								--创建时间
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 订单逻辑删除记录表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[rept_delete]') AND type in (N'U'))
DROP TABLE [dbo].[rept_delete]
GO

CREATE TABLE [dbo].[rept_delete](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,				--ID，自增长
	[dept_code] [varchar](30) NOT NULL,							--门店代码
	[order_no]	[varchar](20),									--订单号
	[delete_user]	[varchar](20),								--单据号
	[delete_time]	[varchar](30)								--删除时间
)
GO

SET ANSI_PADDING OFF
GO


--------------------------------- 店铺管理 -------------------------------------



/**
 * 营业员信息表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[shop_employee]') AND type in (N'U'))
DROP TABLE [dbo].[shop_employee]
GO

CREATE TABLE [dbo].[shop_employee](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[dept_code] [varchar](30) NOT NULL, 				--门店代码
	[name] [nvarchar](100) NOT NULL,					--名称
	[is_group]	[int] NOT NULL,							--是否为组
	[group_leader]	[int],								--组长ID
	[group_member]	[varchar](500),						--组成员ID
	[position] [nvarchar](50),							--职位
	[entry_date] [varchar](20),							--入职日期
	[enable]	[int] DEFAULT 1,						--是否启用
	[last_date]	[varchar](20)							--最后操作日期
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 供应商信息表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[shop_supplier]') AND type in (N'U'))
DROP TABLE [dbo].[shop_supplier]
GO

CREATE TABLE [dbo].[shop_supplier](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[dept_code] [varchar](30) NOT NULL, 				--门店代码
	[name] [nvarchar](100) NOT NULL,					--供应商名称
	[address]	[varchar](500),							--地址
	[mobile]	[varchar](15),							--电话
	[contact]	[nvarchar](100)							--联系人
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 提成参数配置表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[shop_comm_param]') AND type in (N'U'))
DROP TABLE [dbo].[shop_comm_param]
GO

CREATE TABLE [dbo].[shop_comm_param](
	[dept_code] [varchar](30) NOT NULL PRIMARY KEY, 		--门店代码（主键）
	[param1]	[varchar](20),
	[param2]	[varchar](20),
	[param3]	[varchar](20),
	[param4]	[varchar](20),
	[param5]	[varchar](20),
	[param6]	[varchar](20),
	[param7]	[varchar](20),
	[param8]	[varchar](20)
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 提成员工分配表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[shop_comm_emp]') AND type in (N'U'))
DROP TABLE [dbo].[shop_comm_emp]
GO

CREATE TABLE [dbo].[shop_comm_emp](
	[order_id] 	[varchar](20) NOT NULL,					--订单ID,主键
	[item_no] 	[int] NOT NULL,							--行项目,主键
	[goods_id] 	[varchar](10) NOT NULL,					--商品ID
	[number] 	[int],									--数量/次数
	[performer] [varchar](255),							--实施方
	[seller]	[varchar](255),							--销售方
	[middleman]	[varchar](255),							--间接销售方
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 提成项目设置表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[shop_commission]') AND type in (N'U'))
DROP TABLE [dbo].[shop_commission]
GO

CREATE TABLE [dbo].[shop_commission](	
	[goods_id] [varchar](10) NOT NULL,		--商品ID
	[dept_code] [varchar](30) NOT NULL,		--门店代码
	[commission] [varchar](20)				--提成（带%为百分比提成，不带为固定金额提成）
)
GO

ALTER TABLE [dbo].[shop_commission] ADD CONSTRAINT pk_goods_dept PRIMARY KEY([goods_id],[dept_code])
GO

SET ANSI_PADDING OFF
GO


/**
 * 提成大项设置表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[shop_type_commission]') AND type in (N'U'))
DROP TABLE [dbo].[shop_type_commission]
GO

CREATE TABLE [dbo].[shop_type_commission](	
	[type_id] [int] NOT NULL,				--项目类型ID
	[dept_code] [varchar](30) NOT NULL,		--门店代码
	[commission] [varchar](20)				--提成（带%为百分比提成，不带为固定金额提成）
)
GO

ALTER TABLE [dbo].[shop_type_commission] ADD CONSTRAINT pk_type_dept PRIMARY KEY([type_id],[dept_code])
GO

SET ANSI_PADDING OFF
GO


/**
 * 店铺基本设置表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[shop_setting]') AND type in (N'U'))
DROP TABLE [dbo].[shop_setting]
GO

CREATE TABLE [dbo].[shop_setting](	
	[dept_code] [varchar](30) NOT NULL PRIMARY KEY,		--门店代码
	[shop_name] [varchar](100),							--店铺名称
	[logo]		[varchar](200),							--logo
	[phone]		[varchar](20),							--电话号码
	[address]	[varchar](500),							--店铺地址
	[shop_remark]	[varchar](1000),					--店注
	[xlocation]	[decimal](18, 10),						--店铺x坐标
	[ylocation]	[decimal](18, 10)						--店铺y坐标
)
GO

SET ANSI_PADDING OFF
GO



/**
 * 店铺参数配置表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[shop_param]') AND type in (N'U'))
DROP TABLE [dbo].[shop_param]
GO

CREATE TABLE [dbo].[shop_param](
	[dept_code] [varchar](30) NOT NULL PRIMARY KEY, 		--门店代码（主键）
	[param1]	[varchar](100),
	[param2]	[varchar](100),
	[param3]	[varchar](100),
	[param4]	[varchar](100),
	[param5]	[varchar](100),
	[param6]	[varchar](100),
	[param7]	[varchar](100),
	[param8]	[varchar](100),
	[param9]	[varchar](100),
	[param10]	[varchar](100),
)
GO

SET ANSI_PADDING OFF
GO


--------------------------------- 商城管理 -------------------------------------

/**
 * 商品品牌表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mall_brand]') AND type in (N'U'))
DROP TABLE [dbo].[mall_brand]
GO

CREATE TABLE [dbo].[mall_brand](
	[id] 			[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,	--ID，自增长
	[appId]			[varchar](30) NOT NULL,						--appId
	[brandName] 	[nvarchar](20),								--名称
	[shortCode]		[varchar](20),								--简写
	[logo]			[varchar](20)								--logo
)
GO


/**
 * 商城商品类型表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mall_goods_type]') AND type in (N'U'))
DROP TABLE [dbo].[mall_goods_type]
GO

CREATE TABLE [dbo].[mall_goods_type](
	[id] 		[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,	--ID，自增长
	[appId] 	[varchar](30) NOT NULL,						--公众号ID
	[typeName] 	[nvarchar](100),							--类型名称
	[sortNo] 	[int]										--排序码
)
GO

/**
 * 商城轮胎商品关联表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mall_tyre]') AND type in (N'U'))
DROP TABLE [dbo].[mall_tyre]
GO

CREATE TABLE [dbo].[mall_tyre](
	[id] 		[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,	--ID，自增长
	[appId] 	[varchar](30) NOT NULL,						--公众号ID
	[modelId] 	[int] NOT NULL,								--车辆型号
	[goodsId] 	[varchar](10) NOT NULL						--商品ID
)
GO

/**
 * 商城保养商品关联表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mall_maintain]') AND type in (N'U'))
DROP TABLE [dbo].[mall_maintain]
GO

CREATE TABLE [dbo].[mall_maintain](
	[id] 		[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,	--ID，自增长
	[appId] 	[varchar](30) NOT NULL,						--公众号ID
	[modelId] 	[int] NOT NULL,								--车辆型号
	[goodsId] 	[varchar](10) NOT NULL						--商品ID
)
GO


/**
 * 商城商品配送方式表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mall_send_mode]') AND type in (N'U'))
DROP TABLE [dbo].[mall_send_mode]
GO

CREATE TABLE [dbo].[mall_send_mode](
	[id] 		[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,	--ID，自增长
	[appId] 	[varchar](30) NOT NULL,						--公众号ID
	[modeName] 	[nvarchar](100)								--方式名称
)
GO


/**
 * 商城商品
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mall_goods]') AND type in (N'U'))
DROP TABLE [dbo].[mall_goods]
GO

CREATE TABLE [dbo].[mall_goods](
	[goodsId]	[varchar](10) NOT NULL PRIMARY KEY,		--商品ID
	[appId] 	[varchar](30) NOT NULL,					--公众号ID
	[goodsName]	[varchar](200) NOT NULL,				--商品名称
	[pictures]	[varchar](500),							--预览图片
	[typeId]	[int] NOT NULL,							--商城商品类型
	[brandId]	[int],									--商品品牌
	[oprice]	[decimal](18, 6),						--标签价格
	[aprice]	[decimal](18, 6),						--实际价格
	[number]	[decimal](18, 6),						--库存
	[isHot]		[int],									--是否热销商品
	[isPoint]	[int],									--是否允许积分抵扣
	[sendMode]	[varchar](50),							--支持的配送方式(多个id字符串, ","隔开)
	[status]	[int] NOT NULL,							--商品状态(0:已下架1：已上架)
	[spec]		[varchar](1000),						--规格描述
	[details]	[varchar](max),							--商品详情
	[creator]	[varchar](20),							--创建人
	[createTime][varchar](20)							--创建时间
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 购物车表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mall_cart]') AND type in (N'U'))
DROP TABLE [dbo].[mall_cart]
GO

CREATE TABLE [dbo].[mall_cart](
	[goodsId]	[varchar](10) NOT NULL,					--商品ID
	[memId]		[int] NOT NULL,							--会员ID
	[sendMode]	[int],									--配送方式
	[number]	[decimal](18, 6),						--数量
	[createTime][varchar](20)							--创建时间
)
GO

ALTER TABLE [dbo].[mall_cart] ADD CONSTRAINT pk_goodsId_memId PRIMARY KEY([goodsId],[memId])
GO

SET ANSI_PADDING OFF
GO


/**
 * 会员收货地址表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mall_address]') AND type in (N'U'))
DROP TABLE [dbo].[mall_address]
GO

CREATE TABLE [dbo].[mall_address](
	[id]		[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,	--ID，自增长
	[memId]		[int] NOT NULL,								--会员ID
	[isDefault]	[int],										--是否默认
	[name]		[varchar](20),								--收货人
	[mobile]	[varchar](20),								--电话号码
	[province]	[varchar](50),								--省份
	[city]		[varchar](50),								--城市
	[area]		[varchar](50),								--区
	[address]	[varchar](200),								--详细地址
	[postCode]	[varchar](10),								--邮编
	[createTime][varchar](20),								--创建时间
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 商城配置表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mall_setting]') AND type in (N'U'))
DROP TABLE [dbo].[mall_setting]
GO

CREATE TABLE [dbo].[mall_setting](
	[appId]			[varchar](30) NOT NULL PRIMARY KEY,			--公众号ID
	[mchId]			[varchar](30) NOT NULL,						--商户ID
	[appSecret]		[varchar](50) NOT NULL,						--API密钥
	[title]			[varchar](30) NOT NULL,						--商城名称
	[pointRule]		[int] NOT NULL,								--商城积分规则
)
GO


/**
 * 首页banner表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mall_banner]') AND type in (N'U'))
DROP TABLE [dbo].[mall_banner]
GO

CREATE TABLE [dbo].[mall_banner](
	[id] 			[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,	--ID，自增长
	[appId]			[varchar](30) NOT NULL,						--appId
	[picture]		[varchar](30) NOT NULL,						--名称
	[link]			[varchar](20),								--简写
	[sort]			[int]										--logo
)
GO


/**
 * 订单头表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mall_order_head]') AND type in (N'U'))
DROP TABLE [dbo].[mall_order_head]
GO

CREATE TABLE [dbo].[mall_order_head](
	[orderId]	[varchar](20) NOT NULL PRIMARY KEY,		--订单号，项目生成
	[orderType]	[int] NOT NULL,							--订单类型(1:正常，2：轮胎，3：保养)
	[appId] 	[varchar](30) NOT NULL,					--公众号ID
	[openId]	[varchar](50),							--openId
	[nickName]	[varchar](100),							--昵称
	[memId]		[int] NOT NULL,							--会员ID
	[modelId]	[int],									--车型号
	[saleDate]	[varchar](20),							--消费时间
	[oprice]	[decimal](18, 6),						--应收价格
	[aprice]	[decimal](18, 6),						--实收价格
	[point]		[int],									--所得积分
	[status]	[int] NOT NULL,							--订单状态(0:已删除 1:待付款 2:待发货 3:已发货4:已收货5:已完成 )
	[payType]	[int],									--支付方式(1:微信支付 2：线下门店支付)
	[poffset]	[int],									--抵现积分
	[consignee]	[varchar](30),							--收货人
	[contact]	[varchar](20),							--联系方式		
	[address]	[varchar](500),							--收货地址
	[remark]	[nvarchar](1000),						--备注
	[expressNo]	[varchar](50)							--快递单号
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 订单明细表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[mall_order_item]') AND type in (N'U'))
DROP TABLE [dbo].[mall_order_item]
GO

CREATE TABLE [dbo].[mall_order_item](
	[itemId]	[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,	--ID，自增长
	[orderId]	[varchar](20) NOT NULL,					--订单号，项目生成
	[goodsId]	[varchar](10) NOT NULL,					--商品ID
	[goodsName]	[varchar](200) NOT NULL,				--商品名称
	[oprice]	[decimal](18, 6),						--应收价格
	[aprice]	[decimal](18, 6),						--实收价格
	[number]	[int] NOT NULL,							--数量
	[sendMode]	[int]									--配送方式
)
GO

SET ANSI_PADDING OFF
GO




--------------------------------- 活动管理 -------------------------------------

/**
 * 活动表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[act_signup]') AND type in (N'U'))
DROP TABLE [dbo].[act_signup]
GO

CREATE TABLE [dbo].[act_signup](
	[id]			[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,	--ID，自增长
	[appId] 		[varchar](30) NOT NULL,					--公众号ID
	[name]			[varchar](200) NOT NULL,				--活动名称
	[dateStart]		[varchar](20) NOT NULL,					--活动开始时间
	[dateEnd]		[varchar](20) NOT NULL,					--活动结束时间
	[address]		[varchar](500) NOT NULL,				--活动地点
	[contact]		[varchar](20) NOT NULL,					--活动联系人
	[price]			[decimal](18, 6),						--价格
	[number]		[int] NOT NULL,							--限定人数
	[organization]	[varchar](100),							--组织方
	[introduce]		[varchar](max),							--活动介绍
	[signNumber]	[int],									--报名人数
	[signFicNumber]	[int],									--虚拟报名人数
	[readNumber]	[int],									--阅读人数
	[readFicNumber]	[int],									--虚拟阅读人数
	[collNumber]	[int],									--收藏人数
	[collFicNumber]	[int],									--虚拟收藏人数
	[pictures]		[varchar](500),							--图片
	[fields]		[varchar](100),							--输入字段
	[status]		[int]									--订单状态(0:已删除 1:已发布 )
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 活动规格表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[act_spec]') AND type in (N'U'))
DROP TABLE [dbo].[act_spec]
GO

CREATE TABLE [dbo].[act_spec](
	[id]			[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,	--ID，自增长
	[actId]			[int] NOT NULL,							--活动ID
	[name]			[varchar](200) NOT NULL,				--规格描述
	[price]			[decimal](18, 6)						--价格
)
GO

SET ANSI_PADDING OFF
GO


/**
 * 参与者表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[act_joiner]') AND type in (N'U'))
DROP TABLE [dbo].[act_joiner]
GO

CREATE TABLE [dbo].[act_joiner](
	[id]			[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,	--ID，自增长
	[actId]			[int] NOT NULL,							--活动名称
	[openId]		[varchar](200) NOT NULL,				--微信ID
	[nickName]		[varchar](200),							--微信昵称
	[headImg]		[varchar](200),							--头像
	[spec]			[varchar](200),							--增值服务
	[orderNo]		[varchar](25),							--订单号
	[price]			[decimal](18, 6),						--支付金额
	[status]		[int],									--支付状态
	[realName]		[varchar](20) NOT NULL,					--真实姓名
	[mobile]		[varchar](20) NOT NULL,					--手机号码
	[sex]			[int],									--性别 1：男 2：女 0：未知
	[age]			[int],									--年龄
	[cardNo]		[varchar](20),							--身份证号
	[carNumber]		[varchar](10)							--车牌号
)
GO

SET ANSI_PADDING OFF
GO

/**
 * 活动banner表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[act_banner]') AND type in (N'U'))
DROP TABLE [dbo].[act_banner]
GO

CREATE TABLE [dbo].[act_banner](
	[id] 			[int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,	--ID，自增长
	[appId]			[varchar](30) NOT NULL,						--appId
	[picture]		[varchar](30) NOT NULL,						--名称
	[link]			[varchar](20),								--简写
	[sort]			[int]										--logo
)
GO


/**
 * 免责声明表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[act_statement]') AND type in (N'U'))
DROP TABLE [dbo].[act_statement]
GO

CREATE TABLE [dbo].[act_statement](
	[appId] 		[varchar](30) NOT NULL PRIMARY KEY,		--公众号ID
	[statement]		[varchar](max),							--活动介绍
)
GO

SET ANSI_PADDING OFF
GO



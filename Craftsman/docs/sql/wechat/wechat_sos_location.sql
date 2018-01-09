USE [Craftsman]
GO

/**
 * 微信紧急救援地理位置表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[wechat_sos_location]') AND type in (N'U'))
DROP TABLE [dbo].[wechat_sos_location]
GO
CREATE TABLE [dbo].[wechat_sos_location](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[company_id] [varchar](30) NOT NULL,				--公司ID
	[company_name] [nvarchar](80) ,						--公司名称
	[dept_code] [varchar](30) NOT NULL,					--店铺编码
	[dept_name] [nvarchar](80) ,						--店铺名称
	[vip_no]	[varchar](10) NOT NULL,					--会员卡号
	[openid] [varchar](50) NOT NULL, 					--openId
	[appid] [varchar](50) NOT NULL, 					--appId
	[latitude] [varchar](10) NOT NULL, 					--纬度
	[longitude] [varchar](10) NOT NULL,					--经度
	[speed] [varchar](10) , 					--速度，以米/每秒计
	[accuracy] [varchar](10) ,					--位置精度
	[create_by]	[varchar](20) NOT NULL,					--创建人
	[update_by]	[varchar](20) NOT NULL,					--修改人
	[create_time]	[varchar](20) NOT NULL,				--创建时间
	[update_time]	[varchar](20) NOT NULL,				--修改时间
)
GO
 
SET ANSI_PADDING OFF
GO
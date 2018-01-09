USE [Craftsman]
GO

/**
 * 微信活动参与次数
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[wechat_activity_num]') AND type in (N'U'))
DROP TABLE [dbo].[wechat_activity_num]
GO
CREATE TABLE [dbo].[wechat_activity_num](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[activity_type] [int] DEFAULT 0   NOT NULL,			--活动类型 0-砸金蛋 1-大转盘
	[activity_code] [varchar](30) NOT NULL, 			--活动编码
	[company_id] [varchar](30) NOT NULL,							--公司ID
	[company_name] [nvarchar](80) ,				--公司名称
	[dept_code] [varchar](30) NOT NULL,							--店铺编码
	[dept_name] [nvarchar](80) ,				--店铺名称
	[vip_no] [varchar](10) NOT NULL,						--会员卡号
	[openid] [varchar](50) NOT NULL, 				--openId
	[appid] [varchar](50) NOT NULL, 				--appId
	[free_num] [int] DEFAULT 0  ,			--剩余免费参与活动次数
	[create_by]	[varchar](20) NOT NULL,					--创建人
	[update_by]	[varchar](20) NOT NULL,					--修改人
	[create_time]	[varchar](20) NOT NULL,				--创建时间
	[update_time]	[varchar](20) NOT NULL,				--修改时间
)
GO
 
SET ANSI_PADDING OFF
GO
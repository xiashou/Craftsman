USE [Craftsman]
GO

/**
 * 微信抽奖活动参与人表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[wechat_activity_lottery_part]') AND type in (N'U'))
DROP TABLE [dbo].[wechat_activity_lottery_part]
GO
CREATE TABLE [dbo].[wechat_activity_lottery_part](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[activity_code] [varchar](30) NOT NULL, 			--活动编码
	[activity_title] [varchar](100) NOT NULL,			--活动标题
	[redeem_code] [varchar](20)  NOT NULL,				--兑奖码
	[redeem_status] [int] DEFAULT 0  NOT NULL,				--兑奖状态 0-未兑奖，1-已兑奖
	[prize_level] [int] DEFAULT 0  NOT NULL,			--奖品等级 0-特等奖，1-一等奖，2-二等奖，3，4...
	[level_name] [varchar](20)  ,				--自定义奖项名称（如特等奖、幸运奖）
	[prize_desc] [varchar](30)  NOT NULL,				--奖品描述
	[prize_win] [int] DEFAULT 0  NOT NULL,				--中奖获得数量
	[lottery_time]	[varchar](20) NOT NULL,				--抽奖时间
	[redeem_time]	[varchar](20) ,				--兑奖时间
	[company_id] [varchar](30) NOT NULL,							--公司ID
	[company_name] [nvarchar](80) ,				--公司名称
	[dept_code] [varchar](30) NOT NULL,							--店铺编码
	[dept_name] [nvarchar](80) ,				--店铺名称
	[openid] [varchar](50) NOT NULL, 				--openId
	[appid] [varchar](50) NOT NULL, 				--appId
	[create_by]	[varchar](20) NOT NULL,					--创建人
	[update_by]	[varchar](20) NOT NULL,					--修改人
	[create_time]	[varchar](20) NOT NULL,				--创建时间
	[update_time]	[varchar](20) NOT NULL,				--修改时间
)
GO
 
SET ANSI_PADDING OFF
GO
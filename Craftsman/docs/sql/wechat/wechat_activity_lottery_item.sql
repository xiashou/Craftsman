USE [Craftsman]
GO

/**
 * 微信抽奖活动行项目表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[wechat_activity_lottery_item]') AND type in (N'U'))
DROP TABLE [dbo].[wechat_activity_lottery_item]
GO
CREATE TABLE [dbo].[wechat_activity_lottery_item](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[activity_code] [varchar](30) NOT NULL, 			--活动编码
	[prize_level] [int] DEFAULT 0  NOT NULL,			--奖品等级 0-特等奖，1-一等奖，2-二等奖，3，4...
	[level_name] [varchar](20)  ,				--自定义奖项名称（如特等奖、幸运奖）
	[prize_num] [int] DEFAULT 0  NOT NULL,				--奖品总数量
	[lottery_prize_num] DEFAULT 0  NOT NULL,			--已经中奖数量
	[prize_desc] [varchar](30)  NOT NULL,				--奖品描述
	[prize_type] [int] DEFAULT 0  NOT NULL,				--奖品类型 0-实物奖品，1-积分，2-套餐...
	[prize_win] [int] DEFAULT 0  NOT NULL,				--中奖获得数量（如中一次获得多少积分）
	[prize_rate] [int] DEFAULT 0  NOT NULL,				--中奖概率（%）
	[prize_code] [varchar](30) , 				--奖品编码（虚拟奖品 如套餐，优惠券等）
	[create_by]	[varchar](20) NOT NULL,					--创建人
	[update_by]	[varchar](20) NOT NULL,					--修改人
	[create_time]	[varchar](20) NOT NULL,				--创建时间
	[update_time]	[varchar](20) NOT NULL,				--修改时间
)
GO
 
SET ANSI_PADDING OFF
GO
USE [Craftsman]
GO

/**
 * 微信抽奖活动主表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[wechat_activity_lottery_head]') AND type in (N'U'))
DROP TABLE [dbo].[wechat_activity_lottery_head]
GO
CREATE TABLE [dbo].[wechat_activity_lottery_head](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[activity_code] [varchar](30) NOT NULL, 			--活动编码
	[activity_title] [varchar](100) NOT NULL,			--活动标题
	[activity_type] [int] DEFAULT 0   NOT NULL,			--活动类型 0-砸金蛋
	[activity_status] [int] DEFAULT 0  NOT NULL,		--活动状态 0-无效， 1-有效
	[free_add_type] [int] DEFAULT 0  ,			--增加免费参与活动次数类型 0-无，1-消费，2-时间（如每天免费一次）
	[free_add_num] [int] DEFAULT 0  ,			--每次增加免费参与活动次数
	[free_num] [int] DEFAULT 0  ,			--剩余免费参与活动次数
	[paid_consume_type] [int] DEFAULT 0  ,			--有偿消耗类型 0-无，1-积分，2-余额
	[paid_consume_limit] [int] DEFAULT 0 ,			--每次有偿消耗额度
	[b_date] [varchar](20) NOT NULL,				--活动开始日期
	[e_date] [varchar](20) NOT NULL,				--活动结束日期
	[activity_description] [ntext] NOT NULL,			--活动说明
	[remark] [varchar](300) ,			--备注
	[company_id] [varchar](30) NOT NULL,							--公司ID
	[company_name] [nvarchar](80) ,				--公司名称
	[dept_code] [varchar](30) NOT NULL,							--店铺编码
	[dept_name] [nvarchar](80) ,				--店铺名称
	[create_by]	[varchar](20) NOT NULL,					--创建人
	[update_by]	[varchar](20) NOT NULL,					--修改人
	[create_time]	[varchar](20) NOT NULL,				--创建时间
	[update_time]	[varchar](20) NOT NULL,				--修改时间
)
GO
 
SET ANSI_PADDING OFF
GO
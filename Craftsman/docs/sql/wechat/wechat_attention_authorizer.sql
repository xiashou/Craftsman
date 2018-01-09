USE [Craftsman]
GO

/**
 * 微信授权方关注信息
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[wechat_attention_authorizer]') AND type in (N'U'))
DROP TABLE [dbo].[wechat_attention_authorizer]
GO

CREATE TABLE [dbo].[wechat_attention_authorizer](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[authorizer_appid] [varchar](30) NOT NULL, 			--授权方appid
	[dept_code] [varchar](30) NOT NULL,					--店铺编码
	[dept_name] [nvarchar](80) ,						--店铺名称
	[openid] [varchar](30) NOT NULL,					--用户openid
	[nickname] [nvarchar](200),							--用户昵称
	[sex] [int] DEFAULT 0 NOT NULL,			--性别 0-未知，1-男，2-女
	[mobile] [nvarchar](20) ,							--手机
	[country] [varchar](50) ,					--国家
	[province] [varchar](50) ,					--省份
	[city] [varchar](50) ,						--城市
	[picture_url] [nvarchar](300) ,				--用户图像
	[attention_status] [int] DEFAULT 0 NOT NULL,		--关注状态 0-未关注/取消关注，1-已关注
	[event] [varchar](20) NOT NULL,						--事件类型 subscribe，unsubscribe，SCAN，LOCATION，CLICK，VIEW
	[event_key] [varchar](20) ,					--事件KEY值 qrscene_为前缀，后面为二维码的参数值
	[create_by]	[varchar](20) NOT NULL,					--创建人
	[update_by]	[varchar](20) NOT NULL,					--修改人
	[create_time]	[varchar](20) NOT NULL,				--创建时间
	[update_time]	[varchar](20) NOT NULL,				--修改时间
)
GO

SET ANSI_PADDING OFF
GO
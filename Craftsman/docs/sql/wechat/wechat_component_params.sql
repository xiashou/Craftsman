USE [Craftsman]
GO

/**
 * 微信第三方平台参数
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[wechat_component_params]') AND type in (N'U'))
DROP TABLE [dbo].[wechat_component_params]
GO

CREATE TABLE [dbo].[wechat_component_params](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[component_appid] [varchar](30) NOT NULL, 			--第三方平台appid
	[component_verify_ticket] [varchar](100) NOT NULL,	--第三方平台component_verify_ticket
	[component_access_token] [varchar](200) ,			--第三方平台component_access_token
	[ticket_fresh_time]	[varchar](20) NOT NULL,		    --Ticket刷新时间
	[token_fresh_time]	[varchar](20) ,		    		--Token刷新时间
	[token_fresh_rate] [int] ,	    					--Token刷新频率
	[create_time]	[varchar](20) NOT NULL,				--创建时间
	[update_time]	[varchar](20) NOT NULL,				--修改时间
)
GO

SET ANSI_PADDING OFF
GO
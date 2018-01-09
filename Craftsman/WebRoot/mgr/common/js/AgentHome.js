Ext.define('Ext.Tsingma.AgentHome',{ // 起始页
	extend : 'Ext.panel.Panel',
	initComponent : function() {
		var me = this;
		Ext.apply(this,{
			layout: 'column',
			defaults: {padding: '5 0 0 5'},
			autoScroll: true,
	        items:[{
	        	columnWidth: 1/2,
	        	items:[{
	        		title: '查找会员',
	            	iconCls: 'award_star_bronze_1',
//	            	items:[Ext.create('Tsingma.System.Home.FindMemberPanel')]
	            	html:''
	        	},{
	        		title: '营业流水',
	            	iconCls: 'table',
	            	padding: '5 0 0 0',
	            	tools:[{
	            		type:'refresh',
	            		tooltip: '刷新',
	            		callback: function(panel, tool, event) {
	            			panel.items.get('LastOrder').getStore().reload();
	            		}
	            	},{
	            		type:'plus',
	            		tooltip: '更多',
	            		callback: function(panel, tool, event) {
	            			//如果存在之前打印的选项卡则先关闭
		            		if(parent.mainTab.getComponent('010707'))
		            			parent.global.closeTab(parent.mainTab.getComponent('010707'));
		            		parent.global.openTabByMenuId("010707", "");
	            		}
	            	}],
//	            	items:[Ext.create('Tsingma.System.Home.LastOrderGrid')]
	            	html:''
	        	}]
	        },{
	        	columnWidth: 1/4,
	        	title: '信息记录',
        		iconCls: 'chart_bar',
        		tools:[{
            		type:'refresh',
            		tooltip: '刷新',
            		callback: function(panel, tool, event) {
            			panel.items.get('IndexStatistics').getStore().reload();
            		}
            	}],
//            	items:[Ext.create('Tsingma.System.Home.IndexStatisticsGrid')]
            	html:''
	        },{
	        	columnWidth: 1/4,
	        	title: '散客开单',
	        	iconCls: 'page',
//        		items:[Ext.create('Tsingma.System.Home.QuickCreateOrder')]
	        	html:''
	        },{
	        	columnWidth: 3/4,
	        	title: '未完成订单',
            	iconCls: 'table',
            	tools:[{
            		type:'refresh',
            		tooltip: '刷新',
            		callback: function(panel, tool, event) {
            			panel.items.get('IncompleteOrder').getStore().reload();
            		}
            	},{
            		type:'plus',
            		tooltip: '更多',
            		callback: function(panel, tool, event) {
            			//如果存在之前打印的选项卡则先关闭
	            		if(parent.mainTab.getComponent('010312'))
	            			parent.global.closeTab(parent.mainTab.getComponent('010312'));
	            		parent.global.openTabByMenuId("010312", "");
            		}
            	}],
//            	items:[Ext.create('Tsingma.System.Home.IncompleteOrderGrid')]
            	html:''
	        }],
	        isReLayout : false
		});
//		showMessageTip();
		this.callParent(arguments);
	}
});

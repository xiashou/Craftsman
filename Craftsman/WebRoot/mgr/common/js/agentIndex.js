/**
 * 代理后台首页
 * @author Xiashou
 * @since 2017/3/30
 */
Ext.Ajax.timeout = 60000;
Ext.Ajax.defaultHeaders = {'Request-By': 'TSINGMA'};
Ext.Loader.setConfig({enabled : true});
Ext.require(['Ext.ux.statusbar.StatusBar', 
             'Ext.ux.TabScrollerMenu', 
             'Ext.window.MessageBox', 
             'Ext.ux.TabCloseMenu',
             'Ext.Tsingma.AgentHome',
             'Ext.ux.IFrame',
             'Ext.state.*', 
             'Ext.tip.*'
]);

var globalVipNo, globalPageSize = 20;// 全局时间列宽度
var mainTab, west, currentTab;
var rButtons = new Array();

Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var mainPortal = Ext.create('Ext.Tsingma.AgentHome', {
		border: false,
        iconCls: 'house',
        title: "首页"
	});
	
	mainTab = Ext.create('Ext.TabPanel', {
		region : 'center',
		margins : 0,
		deferredRender : false,
		activeTab : 0,
		plugins : Ext.create('Ext.ux.TabCloseMenu', {
			closeTabText : '关闭面板',
			refreshTabText : '刷新面板',
			closeOthersTabsText : '关闭其他',
			closeAllTabsText : '关闭所有'
		}),
		items : [mainPortal]
	});
	
	var logoutButton = Ext.create('Ext.button.Button', {
		text : '退出',
		scale : 'medium',
		margin: '5 10 5 5',
		padding: '8 0',
		handler: function(){
			logout();
		}
	});
	
	var north = Ext.create('Ext.panel.Panel',{
		region : 'north',
		xtype : 'container',
		height : 60,
		id : 'app-header',
		//bodyStyle: 'background:#3892D3;',
		bodyStyle: 'background:#363B41;',
		layout : {type : 'hbox',align : 'middle'},
		defaults : {xtype : 'component'},
		items : [{
			xtype: 'displayfield',
			width: '45%',
//            value: "<div class=\'header\'><span class=\'caption\'>" + parent.HH + "</span></div>"
			value: "<div class='header'><img src='/resources/images/1.png' height='50px' />&nbsp;<span class=\'caption\'>" + parent.HH + "</span></div>"
		}, {xtype:'tbspacer',flex:1},{
			html : '欢迎您，' + DD + '<br>',
			id: 'loginInfo',
			padding: '0 20 0 0',
			style : 'text-align:right;font-size:12px;color:#fff;line-height:20px',
		}, logoutButton]
	});
	
	var south = Ext.create('Ext.panel.Panel',{
		region : 'south',
		height: 25,
		border : false,
		items : [ Ext.create('Ext.ux.StatusBar', {
			border : false,
			text : '',
			style : 'background:#363B41;',
			defaults : {style : 'color:#fff;'},
			items : [ '->', '2015 © copyright &nbsp;', '-', '深圳市智能工匠技术有限公司', '-', '粤ICP备16085903号', '->','->' ]
		})]
	});
	
	west = Ext.create('Ext.panel.Panel',{
		region: 'west',
        stateId: 'navigation-panel',
        id: 'west-panel',
        title: '导航',
        iconCls:'dh',
        split: true,
        width: 200,
        minWidth: 175,
        border: false,
        collapsible: true,
        animCollapse: true,
        margins: '0 0 0 5',
        layout: 'accordion'
	});
	
	
	var viewport = Ext.create('Ext.Viewport', {
		layout : 'border',
		items : [north, west, mainTab, south]
	});
	
	Ext.Ajax.request({
		url : '/sys/queryRoleTree.atc', 
		method : 'post', 
		params : {
			parentId: '01',
			roleId: BB
		}, 
		success: function(response, options) {
			contextMenuTree(Ext.JSON.decode(response.responseText));
		}
	});
	
    function contextMenuTree(menuList){
        for (var i = 0; i < menuList.length; i++) {
        	var menu = menuList[i];
            west.add(Ext.create("Ext.tree.Panel", {
                title : menu.menuName,  
                iconCls : menu.iconCls,  
                rootVisible : false,  
                viewConfig : {
                    loadingText : "正在加载..."  
                },  
                store : Ext.create("Ext.data.TreeStore",{
            		model: 'Menu',
            		proxy: {
            	        type: 'memory',
            	        data: menu,
            	        reader: {
            	            type: 'json',
            	            root: 'children'
            	        }
            	    }
            	}),
    			listeners : {
					'itemclick' : function(e, record) {
						if (record.data.leaf) {
							global.openTab(record.data.id, record.data.text, record.raw.menuUrl, record.raw.buttons);
						}
					}
				}
            }));  
            west.doLayout();
        }  
    }  
    
    Ext.define('Menu', {
        extend: 'Ext.data.Model',
        fields: ['text', 'id', 'leaf', 'expanded', 'menuUrl', 'buttons', 'children']
    });
    
    mainTab.on('tabchange', function(tabPanel, newCard, oldCard, eOpts) {
    	if(newCard.activeUI){
			rButtons = newCard.activeUI.split(',');
		} else 
			rButtons = [];
    },true);
	
    function logout(){
		Ext.MessageBox.show({
			title : '提示',
			msg : '确认要注销系统,退出登录吗?',
			width : 250,
			buttons : Ext.MessageBox.YESNO,
			animEl : Ext.getBody(),
			icon : Ext.MessageBox.QUESTION,
			fn : function(btn) {
				if (btn == 'yes') {
					window.location.href = '/agentLogout.atc';
				}
			}
		});
    }

});

var global = new Object();
var i = 0;

//打开tab
global.openTab = function(tabId, tabTitle, tabUrl, buttons) {
//	var _tab = mainTab.getComponent('tab' + tabId);
	
	//对项目消费选显卡特殊处理
	if("010301" == tabId) {
		tabId = tabId + "_" + i;
		tabTitle = tabTitle + (i+1);
		i++;
	}
	currentTab = mainTab.getComponent(tabId);
	
	if (!currentTab) {
		mainTab.setLoading('加载中...');
		
		currentTab = mainTab.add(new Ext.ux.IFrame({
            xtype: 'iframepanel',
            id: tabId,
            title: tabTitle,
            closable: true,
            target: 'self',
            layout: 'fit',
            loadMask: '页面加载中...',
            border: false
        }));
		currentTab.setUI(buttons);
		mainTab.setActiveTab(currentTab);
		currentTab.load(tabUrl);
		mainTab.setLoading(false);
	}
	mainTab.setActiveTab(currentTab);
};

global.closeTab = function(tabId) {
	mainTab.remove(tabId);
};

global.openTabByMenuId = function(menuId, param) {
	if(!Ext.isEmpty(menuId)){
		Ext.Ajax.request({
			url : '/sys/queryMenuById.atc', 
			method : 'post', 
			params : {
				'menu.id': menuId
			}, 
			success: function(response, options) {
				var result = Ext.JSON.decode(response.responseText);
				if(!Ext.isEmpty(param))
					global.openTab(result.menu.id, result.menu.menuName, result.menu.menuUrl + "?" + param, result.menu.buttons);
				else
					global.openTab(result.menu.id, result.menu.menuName, result.menu.menuUrl, result.menu.buttons);
				menuId = result.menu.id;
			}
		});
	}
};





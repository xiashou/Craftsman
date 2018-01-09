Ext.require(['Ext.ux.DataTip']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	Ext.define('Param', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'appId', type: 'string'},
	        {name: 'statement', type: 'string'}
	    ]
	});
	
	var paramForm = new Ext.form.FormPanel({
		id : 'paramForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            xtype:'textfield'
        },
		bodyPadding: '5 10 0 10',
		border : false,
		plugins: {
            ptype: 'datatip'
        },
		items : [{
			xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'hiddenfield',labelWidth:100,labelAlign:'right'},
		         items: [
	                   {id:'appId', name: 'statement.appId'}
		         ]
		     },{
		    	 xtype: 'htmleditor',
	    		 height: 550,
	    		 id: 'statement',
	    		 name: 'statement.statement',
	    		 anchor: '100%',
	    		 enableAlignments: true,
	    		 enableColors: true,
	    		 enableFont: true,
	    		 enableFontSize: true,
	    		 enableFormat: true,
	    		 enableLinks: true,
	    		 enableLists: true,
	    		 fontFamilies : ['宋体','黑体','隶书','微软雅黑','Arial','Courier New','Tahoma','Verdana']
		     }
		],
		buttons:[{
			text : '修 改',
			iconCls : 'accept',
			handler : function() {
				if (paramForm.form.isValid()) {
					paramForm.form.submit({
						url : '/wechat/act/updateActStatement.atc',
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							Ext.example.msg('提示', action.result.msg);
						},
						failure : function(form, action) {
							var msg = action.result.msg;
							Ext.MessageBox.alert('提示', msg);
						}
					});
				}
			}
		}]
	});
    
    var paramWindow = new Ext.Window({
		title : '免责声明', 
		layout : 'fit',
		width : 580,
		height : 660,
		y: 80,
		resizable : false,
		draggable : false,
		closable : false,
		modal : false,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		tools:[{
            type:'help',
            tooltip: '操作指引',
            callback: function(panel, tool, event) {
            	parent.global.openSystemHelpTab();
            }
        }],
		items : [paramForm]
	});
    
    function completeShopParam() {
		Ext.Ajax.request({
			url : '/wechat/act/queryActStatementByAppId.atc',
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result && !Ext.isEmpty(result)){
					param = new Param({
						appId: result.appId,
						statement: result.statement
					});
					paramForm.loadRecord(param);
				}
			},
			failure : function(resp, opts) {
                Ext.Msg.alert('提示', "店铺参数配置信息加载出错！");
			}
		});
	}
    
    completeShopParam();
    paramWindow.show();
	
});
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	Ext.define('MemberBookTips', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'bookTips', type: 'string'}
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
		items : [{
			xtype: 'fieldcontainer',
			layout: 'hbox',
			defaults: {flex: 1,xtype:'textarea',labelWidth:100,labelAlign:'right'},
			items: [{id:'tips', name: 'bookTips.tips', rows: 10}]
	    }],
		buttons:[{
			text : '修 改',
			iconCls : 'accept',
			handler : function() {
				if (paramForm.form.isValid()) {
					paramForm.form.submit({
						url : '/member/updateMemberBookTips.atc',
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
		title : '温馨提示', 
		layout : 'fit',
		width : 580,
		height : 260,
		y: 200,
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
    
    function completeBookTips() {
		Ext.Ajax.request({
			url : '/member/queryMemberBookTipsByDept.atc',
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result && !Ext.isEmpty(result)){
					Ext.getCmp("tips").setValue(result.tips);
				}
			},
			failure : function(resp, opts) {
                Ext.Msg.alert('提示', "提示信息加载出错！");
			}
		});
	}
    
    completeBookTips();
    paramWindow.show();
	
});
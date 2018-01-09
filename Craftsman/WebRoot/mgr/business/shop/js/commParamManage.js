Ext.require(['Ext.ux.DataTip']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	Ext.define('Param', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'param1', type: 'string'},
	        {name: 'param2', type: 'string'},
	        {name: 'param3', type: 'string'},
	        {name: 'param4', type: 'string'},
	        {name: 'param5', type: 'string'},
	        {name: 'param6', type: 'string'},
	        {name: 'param7', type: 'string'},
	        {name: 'param8', type: 'string'}
	    ]
	});
	
	var paramForm = new Ext.form.FormPanel({
		id : 'paramForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            xtype:'textfield'
        },
		bodyPadding: '5 10 0 0',
		border : false,
		plugins: {
            ptype: 'datatip'
        },
		items : [{
				 xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'textfield', labelWidth:100, labelAlign:'right'},
		         items: [
	                   {id:'param1', name: 'commParam.param1',fieldLabel: '施工提成比例', tooltip:'百分比或者固定金额'}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:100, labelAlign:'right'},
		    	 items: [
		    	       {id:'param2', name: 'commParam.param2',fieldLabel: '销售提成比例', tooltip:'百分比或者固定金额'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'textfield',labelWidth:100,labelAlign:'right'},
		         items: [
	                   {id:'param3', name: 'commParam.param3',fieldLabel: '辅助销售提成',tooltip: '百分比或者固定金额'}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'textfield', labelWidth:100,labelAlign:'right'},
		         items: [
	                    {id:'param4', name: 'commParam.param4',fieldLabel: '接单提成', tooltip:'百分比或者固定金额'}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'textfield', labelWidth:100,labelAlign:'right'},
		         items: [
	                    {id:'param5', name: 'commParam.param5',fieldLabel: '销售顾问提成', tooltip:'百分比或者固定金额'}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:100,labelAlign:'right'},
		    	 items: [
		    	        {id:'param6', name: 'commParam.param6',fieldLabel: '组长提成比例', tooltip:'百分比或者固定金额'},
		    	 ]
//		     },{
//		    	 xtype: 'fieldcontainer',
//		    	 layout: 'hbox',
//		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:100, labelAlign:'right'},
//		    	 items: [
//		    	         {id:'param7', name: 'commParam.param7',fieldLabel: '销售顾问提成', tooltip:'设置百分比或者固定金额'}
//		    	 ]
		     }
		],
		buttons:[{
			text : '修 改',
			iconCls : 'accept',
			hidden: !parent.haveActionMenu('Edit'),
			handler : function() {
				if (paramForm.form.isValid()) {
					paramForm.form.submit({
						url : '/shop/updateCommParan.atc',
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
		title : '参数配置', 
		layout : 'fit',
		width : 380,
		height : 280,
		y: 180,
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
			url : '/shop/queryCommParamById.atc',
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result && !Ext.isEmpty(result)){
					param = new Param({
						param1: result.param1,
						param2: result.param2,
						param3: result.param3,
						param4: result.param4,
						param5: result.param5,
						param6: result.param6,
						param7: result.param7,
						param8: result.param8,
					});
					paramForm.loadRecord(param);
				}
			},
			failure : function(resp, opts) {
                Ext.Msg.alert('提示', "加载出错！");
			}
		});
	}
    
    completeShopParam();
    paramWindow.show();
	
});
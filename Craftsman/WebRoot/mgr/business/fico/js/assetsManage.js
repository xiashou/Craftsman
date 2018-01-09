/**
 * 资产管理
 */
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var assetsForm = new Ext.form.FormPanel({
				id : 'assetsForm',
				layout: 'anchor',
				defaults: {
		            anchor: '100%',
		            layout: 'hbox',
		            xtype:'fieldcontainer'
		        },
				bodyPadding: '5 10 0 0',
				border : false,
				items : [
					{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
					    items: [
					           {id:'id', name:'assets.id', xtype:'hiddenfield'},
					           {id:'name', name: 'assets.name',allowBlank: false, fieldLabel: '名称', blankText:'名称不能为空!' }
					    ]
					}, {
					    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
					    items: [
					           {id:'price', name: 'assets.price', allowBlank: false, xtype:'numberfield',fieldLabel: '金额', value: 0}
					    ]
					}
				],
				buttons:[{
					text : '保 存',
					iconCls : 'accept',
					handler : function() {
						if (assetsForm.form.isValid()) {
							assetsForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									assetsWindow.hide();
									Ext.example.msg('提示', action.result.msg);
									store.reload();
								},
								failure : function(form, action) {
									var msg = action.result.msg;
									Ext.MessageBox.alert('提示', msg);
								}
							});
						}
					}
				},{
					text : '关 闭 ',
					iconCls : 'stop',
					handler : function() {
						assetsWindow.hide();
					}
				}]
			});
	
	var assetsWindow = new Ext.Window({
				title : '修改', // 窗口标题
				layout : 'fit',
				width : 250,
				height : 140,
				resizable : false,
				draggable : true,
				closeAction : 'hide',
				modal : false,
				collapsible : true,
				titleCollapse : true,
				maximizable : false,
				buttonAlign : 'right',
				border : false,
				animCollapse : true,
				constrain : true,
				items: [assetsForm]
			});
	
	var store = Ext.create('Ext.data.Store', {
          fields: ['id', 'code', 'name', 'price', 'editable'],
          proxy: {
              type: 'ajax',
              url : '/fico/queryAssetsByDept.atc',
              reader : {
                  root: 'assetsList'
              }
          }
    });
  	
  	var columns= [rowNum,
            {header : 'ID',dataIndex: 'id', hidden: true},
            {header : 'CODE',dataIndex: 'code', hidden: true},
            {header : '是否可编辑',dataIndex: 'editable', hidden: true},
            {header : '名称',dataIndex: 'name', width: 140},
            {header : '金额',dataIndex: 'price', width: 140}
  	]; 
	
	var tbar = Ext.create('Ext.toolbar.Toolbar', {
			region : 'north',
			border : false,
	        items: [{
	        	xtype: 'button',
	        	id: 'insert',
	        	text: '新建',
	        	iconCls: 'add',
	        	hidden : !parent.haveActionMenu('Add'),
	        	handler: function() {
	        		assetsWindow.setTitle('新建');
	        		assetsForm.getForm().reset();
	        		assetsForm.getForm().url = '/fico/insertAssets.atc';
	        		assetsWindow.show();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'update',
	        	text: '修改',
	        	iconCls: 'pencil',
	        	hidden : !parent.haveActionMenu('Edit'),
	        	handler: function() {
	        		initEdit();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'delete',
	        	text: '删除',
	        	iconCls: 'delete', 
	        	hidden : !parent.haveActionMenu('Del'),
	        	handler: function(){
	        		initDelete();
	            }
	        }]
	    });
	
	var selModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE',
		listeners : {
			beforeselect : function(sm, record, index) {
				if(record.data.editable == 0){
//					Ext.example.msg("提示", "系统固定资产不可修改！");
					return false;
				}
			}
		}
	});
	
	var grid = new Ext.grid.GridPanel({
		title: '资产管理',
		columns : columns,
		store : store, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : selModel,
		frame : false,
		tbar : tbar, 
		stripeRows:true,
		tools:[{
            type:'help',
            tooltip: '操作指引',
            callback: function(panel, tool, event) {
            	parent.global.openSystemHelpTab();
            }
        }],
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			region : 'center',
			layout : 'fit',
			border : false,
			items : [ grid ]
		}]
	});
	
	store.load();
	
	
	function initEdit() {
		var record = selModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
				title: '提示',
				msg: '你没有选中任何项目！',
				buttons: Ext.MessageBox.OK,
				icon: Ext.MessageBox.INFO
		    });
			return;
//		} else if(record.data.editable == 0) {
//			Ext.MessageBox.show({
//				title: '提示',
//				msg: '不可编辑！',
//				buttons: Ext.MessageBox.OK,
//				icon: Ext.MessageBox.INFO
//		    });
//			return;
		}
		assetsWindow.setTitle('修改');
		assetsForm.loadRecord(record);
		assetsForm.getForm().url = '/fico/updateAssets.atc';
		assetsWindow.show();
	}
	
	function initDelete() {
		var record = selModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '你没有选中任何项目！',
	           buttons: Ext.MessageBox.OK,
	           icon: Ext.MessageBox.INFO
		    });
			return;
		} 
		Ext.Msg.confirm('请确认', '确定要删除这个资产项吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/fico/deleteAssets.atc',
					params : {
						'assets.id' : record.data.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							assetsWindow.hide();
							Ext.example.msg('提示', result.msg);
							store.reload();
						} else 
							Ext.MessageBox.show({
     				           title: '提示',
     				           msg: result.msg,
     				           buttons: Ext.MessageBox.OK,
     				           icon: Ext.MessageBox.ERROR
                            });
					},
					failure : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
                        Ext.MessageBox.show({
 				           title: '提示',
 				           msg: result.msg,
 				           buttons: Ext.MessageBox.OK,
 				           icon: Ext.MessageBox.ERROR
                        });
					}
				});
			}
		});
	}
	
});
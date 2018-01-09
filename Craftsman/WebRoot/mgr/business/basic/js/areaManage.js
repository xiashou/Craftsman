Ext.require(['Ext.ux.TreePicker']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var currentId = '01';
	
	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
				header : 'NO',
				resizable: true,
				width : 40
			});
	
	var areaForm = new Ext.form.FormPanel({
				id : 'areaForm',
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
					           {id:'areaId', name:'area.areaId',fieldLabel: '区域ID',allowBlank: false, blankText:'区域ID不能为空!'},
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
						items: [
						        {id:'areaName', name: 'area.areaName',fieldLabel: '区域名称', allowBlank: false, blankText:'区域名称不能为空!' }
						]
					},{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
					    items: [
					           {id:'areaShort', name: 'area.areaShort', fieldLabel: '区域简称', allowBlank: false, blankText:'区域简称不能为空!' },
					           {id:'areaCode', name: 'area.areaCode', fieldLabel: '区域代码' }
					    ]
					}
				],
				buttons:[{
					text : '保 存', // 按钮文本
					iconCls : 'accept', // 按钮图标
					handler : function() { // 按钮响应函数
						if (areaForm.form.isValid()) {
							areaForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									areaWindow.hide();
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
					text : '关 闭 ', // 按钮文本
					iconCls : 'stop', // 按钮图标
					handler : function() { // 按钮响应函数
						areaWindow.hide();
					}
				}]
			});
	
	var areaWindow = new Ext.Window({
				title : '修改', // 窗口标题
				layout : 'fit',
				width : 400,
				height : 200,
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
				items: [areaForm]
			});
	
	var store = Ext.create('Ext.data.Store', {
		  		  pageSize : 20,
		          fields: ['areaId', 'areaName', 'areaShort', 'areaCode'],
		          proxy: {
		              type: 'ajax',
		              url : '/base/queryBaseAreaPage.atc',
		              reader : {
		                  root: 'areaList',
		                  totalProperty: 'totalCount'
		              }
		          }
		    });
  	
  	var columns= [rowNum,
	            {header : '区域ID',dataIndex: 'areaId', width: 140},
	            {header : '区域名称',dataIndex: 'areaName', width: 240},
	            {header : '区域简称',dataIndex: 'areaShort', width: 140},
	            {header : '区域代码',dataIndex: 'areaCode', width: 120}
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
	        		areaWindow.setTitle('新建');
	        		areaForm.getForm().reset();
	        		areaForm.getForm().url = '/base/insertBaseArea.atc';
	        		areaWindow.show();
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
//	        },{
//	        	xtype: 'button',
//	        	id: 'find',
//	        	text: '查询',
//	        	iconCls: 'preview', 
//	        	hidden : !parent.haveActionMenu('Find'),
//	        	handler: function(){
//	        		seachWindow.show();
//	            }
	        }]
	    });
	
	var pagesizeCombo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
							[ 100, '100条/页' ], [ 200, '200条/页' ], [ 500, '500条/页' ], [ 1000000, '更多条' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '20',
				editable : false,
				width : 85
			});
	
	var number = parseInt(pagesizeCombo.getValue());

	// 改变每页显示条数reload数据
	pagesizeCombo.on("select", function(comboBox) {
			bbar.pageSize = parseInt(comboBox.getValue());
			number = parseInt(comboBox.getValue());
			store.pageSize = parseInt(comboBox.getValue());
			store.reload({
				params : {
					start : 0,
					limit : bbar.pageSize
				}
			});
		});
	
	var bbar = new Ext.PagingToolbar({
			pageSize : number,
			store : store,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', pagesizeCombo ]
		});
	
	var selModel = Ext.create('Ext.selection.CheckboxModel', {
			injectCheckbox: 1,
			mode: 'SINGLE'
		});
	
	var grid = new Ext.grid.GridPanel({
			columns : columns,
			store : store, 
			region : 'center',
			autoScroll : true,
			columnLines : true,
			selModel : selModel,
			frame : false,
			tbar : tbar, 
			bbar : bbar,
			stripeRows:true,
			viewConfig : {
				enableTextSelection:true	//设置单元格可以选择
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			listeners:{
				itemdblclick: function(grid, record, item, index, e) {
					if(parent.haveActionMenu('Edit'))
						initEdit();
		        }
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
	
	store.load({
		params:{
			start : 0,
			limit : bbar.pageSize
		}
	});
	
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
		} 
		selModel.deselectAll();
		areaWindow.setTitle('修改');
		areaForm.loadRecord(record);
		areaForm.getForm().url = '/base/updateBaseArea.atc';
		areaWindow.show();
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
		Ext.Msg.confirm('请确认', '确定要删除这个按钮项吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/base/deleteBaseArea.atc',
					params : {
						'area.areaId' : record.raw.areaId
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							areaWindow.hide();
							Ext.example.msg('提示', result.msg);
							selModel.deselectAll();
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
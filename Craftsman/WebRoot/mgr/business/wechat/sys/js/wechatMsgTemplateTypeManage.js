Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
				header : 'NO',
				resizable: true,
				width : 40
			});
	
	var paramForm = new Ext.form.FormPanel({
				id : 'paramForm',
				layout: 'anchor',
				defaults: {
		            anchor: '100%',
		            layout: 'hbox',
		            xtype:'fieldcontainer'
		        },
				bodyPadding: '5 10 0 0',
				border : false,
				items : [{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'id', name:'wechatMsgTemplateType.id', xtype:'hiddenfield'}
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'templateNo', name: 'wechatMsgTemplateType.templateNo', fieldLabel: '模板编号'},
					           {id:'templateTitle', name: 'wechatMsgTemplateType.templateTitle',fieldLabel: '模板标题' }
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'createBy', name: 'wechatMsgTemplateType.createBy',fieldLabel: '创建人' , readOnly:true, fieldStyle:'background-color: #F0F0F0;'},
					           {id:'updateBy', name:'wechatMsgTemplateType.updateBy', fieldLabel: '修改人', readOnly:true, fieldStyle:'background-color: #F0F0F0;'}
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'createTime', name: 'wechatMsgTemplateType.createTime',fieldLabel: '创建时间' , readOnly:true, fieldStyle:'background-color: #F0F0F0;'},
					           {id:'updateTime', name:'wechatMsgTemplateType.updateTime', fieldLabel: '修改时间', readOnly:true, fieldStyle:'background-color: #F0F0F0;'}
					    ]
					}
				],
				buttons:[{
					text : '保 存',
					iconCls : 'accept',
					handler : function() {
						if (paramForm.form.isValid()) {
							paramForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									paramWindow.hide();
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
						paramWindow.hide();
					}
				}]
			});
	
	var paramWindow = new Ext.Window({
				title : '修改', // 窗口标题
				layout : 'fit',
				width : 650,
				height : 490,
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
				items: [paramForm]
			});
	
	var store = Ext.create('Ext.data.Store', {
		  		  pageSize : 20,
		          fields: ['id', 'templateNo', 'templateTitle',
		                   'createBy', 'updateBy', 'createTime', 'updateTime'],
		          proxy: {
		              type: 'ajax',
		              url : '/wechat/sys/queryAllWechatMsgTypeTemplate.atc',
		              reader : {
		                  root: 'wechatMsgTemplateTypeList',
		                  totalProperty: 'totalCount'
		              }
		          }
		    });
  	
  	var columns= [rowNum,
	            {header : 'ID',dataIndex: 'id', hidden: true},
	            {header : '模板编号',dataIndex: 'templateNo', width: 100},
	            {header : '模板标题',dataIndex: 'templateTitle', width: 100},
	            {header : '创建人',dataIndex: 'createBy', width: 140},
	            {header : '修改人',dataIndex: 'updateBy', width: 140},
	            {header : '创建时间',dataIndex: 'createTime', width: 140},
	            {header : '修改时间',dataIndex: 'updateTime', width: 140}
	  	]; 
	
	var tbar = Ext.create('Ext.toolbar.Toolbar', {
			region : 'north',
			border : false,
	        items: [{
	        	xtype: 'button',
	        	id: 'search',
	        	text: ' 查 询 ',
	        	iconCls: 'preview', 
	        	handler: function(){
	        		store.reload({
	        			params : {
	        				start : 0,
	        				limit : bbar.pageSize
	        			}
	        		});
	            }
	        },{
	        	xtype: 'button',
	        	id: 'insert',
	        	text: '新建',
	        	iconCls: 'add',
	        	hidden : !parent.haveActionMenu('Add'),
	        	handler: function() {
	        		paramWindow.setTitle('新建');
	        		paramForm.getForm().reset();
	        		paramForm.getForm().url = '/wechat/sys/insertWechatMsgTypeTemplate.atc';
	        		paramWindow.show();
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
		paramWindow.setTitle('修改');
		paramForm.loadRecord(record);
		paramForm.getForm().url = '/wechat/sys/updateWechatMsgTypeTemplate.atc';
		paramWindow.show();
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
					url : '/wechat/sys/deleteWechatMsgTypeTemplate.atc',
					params : {
						'wechatMsgTemplateType.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							paramWindow.hide();
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
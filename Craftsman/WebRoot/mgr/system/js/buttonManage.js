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
	
	var treeStore = Ext.create('Ext.data.TreeStore', {
				id:'treeStore',
		        proxy: {
		            type: 'ajax',
		            reader: 'json',
		            url: '/sys/queryNormalTree.atc?parentId=0'
		        }
		    });
	
	var menuTree = new Ext.tree.TreePanel({
			    store: treeStore,
			    height: '100%',
		        border : false,
		        rootVisible: false,
		        listeners:{
			    	itemclick: function(tree, record, item, index) {
			    		buttonWindow.hide();
			    		if(record.raw.leaf){
			    			currentId = record.raw.id;
			    			store.reload({
			    				params:{
			    					'button.menuId' : currentId,
			    					start : 0,
			    					limit : bbar.pageSize
			    				}
			    			});
			    			Ext.getCmp('insert').setDisabled(false);
			    			Ext.getCmp('update').setDisabled(false);
			    			Ext.getCmp('delete').setDisabled(false);
			    		} else {
			    			Ext.getCmp('insert').setDisabled(true);
			    			Ext.getCmp('update').setDisabled(true);
			    			Ext.getCmp('delete').setDisabled(true);
			    		}
			    	}
			    }
			});
	
	var buttonForm = new Ext.form.FormPanel({
				id : 'buttonForm',
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
					           {id:'btnId', name:'button.btnId', xtype:'hiddenfield'},
					           {id:'menuId', name:'button.menuId', xtype:'hiddenfield'},
					           {id:'btnKey', name: 'button.btnKey',allowBlank: false, fieldLabel: '按钮代码', blankText:'代码不能为空!' }
					    ]
					},{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
					    items: [
					           {id:'btnName', name: 'button.btnName', allowBlank: false, fieldLabel: '按钮名称', blankText:'名称不能为空!' }
					    ]
					}
				],
				buttons:[{
					text : '保 存', // 按钮文本
					iconCls : 'accept', // 按钮图标
					handler : function() { // 按钮响应函数
						if (buttonForm.form.isValid()) {
							buttonForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									buttonWindow.hide();
									Ext.example.msg('提示', action.result.msg);
									treeStore.reload();
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
						buttonWindow.hide();
					}
				}]
			});
	
	var buttonWindow = new Ext.Window({
				title : '修改', // 窗口标题
				layout : 'fit',
				width : 400,
				height : 150,
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
				items: [buttonForm]
			});
	
	var store = Ext.create('Ext.data.Store', {
		  		  pageSize : 20,
		          fields: ['btnId', 'btnName', 'btnKey', 'menuId'],
		          proxy: {
		              type: 'ajax',
		              url : '/sys/querySysButtonPage.atc',
		              reader : {
		                  root: 'buttonList',
		                  totalProperty: 'totalCount'
		              }
		          }
		    });
  	
  	var columns= [rowNum,
	            {header : '按钮代码',dataIndex: 'btnKey', width: 140},
	            {header : '按钮名称',dataIndex: 'btnName', width: 240},
	            {header : '关联菜单',dataIndex: 'menuId', width: 140, renderer:
	            	function(v){
	            		var menu = treeStore.getNodeById(v);
	            		return menu != null ? menu.raw.menuName : '无';
	            	}
	            },
	            {header : '按钮编号',dataIndex: 'btnId', width: 120}
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
	        		buttonWindow.setTitle('新建');
	        		buttonForm.getForm().reset();
	        		buttonForm.getForm().url = '/sys/insertSysButton.atc';
	        		Ext.getCmp('menuId').setValue(currentId);
	        		buttonWindow.show();
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
			title : '菜单资源',
			iconCls : 'layout',
			tools : [{
				id : 'refresh',
				handler : function() {
			        treeStore.reload();
				}
			}],
			collapsible : true,
			width : 240,
			minSize : 160,
			maxSize : 240,
			split : true,
			region : 'west',
			autoScroll : true,
			items : [ menuTree ]
		}, {
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
	
	Ext.getCmp('insert').setDisabled(true);
	Ext.getCmp('update').setDisabled(true);
	Ext.getCmp('delete').setDisabled(true);
	
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
		if(record.raw.id != '01'){
			buttonWindow.setTitle('修改');
			buttonForm.loadRecord(record);
			buttonForm.getForm().url = '/sys/updateSysButton.atc';
			buttonWindow.show();
		} else {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '顶级菜单不能修改！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
            });
		}
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
					url : '/sys/deleteSysButton.atc',
					params : {
						'button.btnId' : record.raw.btnId
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							buttonWindow.hide();
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
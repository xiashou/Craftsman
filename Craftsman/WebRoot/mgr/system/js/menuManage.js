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
			    width: '93%',
			    height: '100%',
		        border : false,
		        rootVisible: false,
		        listeners:{
			    	itemclick: function(tree, record, item, index) {
			    		menuWindow.hide();
			    		currentId = record.raw.id;
			    		store.reload({
			    			params:{
			    				'menu.parentId' : currentId,
			    				start : 0,
			    				limit : bbar.pageSize
			    			}
			    		});
			    	}
			    }
			});
	
	var menuForm = new Ext.form.FormPanel({
				id : 'menuForm',
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
					           {id:'id', name:'menu.id', xtype:'hiddenfield'},
					           {id:'leaf', name:'menu.leaf', xtype:'hiddenfield', value: 'true'},
					           {id:'menuName', name: 'menu.menuName',allowBlank: false, fieldLabel: '菜单名称', blankText:'名称不能为空!' },
					           {id:'sortNo', name: 'menu.sortNo', xtype:'numberfield',value:'1', allowBlank: false, fieldLabel: '排序号'}
					    ]
					},{
					    defaults: {flex: 1, xtype:'treepicker', labelWidth:70, labelAlign:'right'},
					    items: [
					           {id:'parentId', name: 'menu.parentId', store: treeStore, displayField: 'text', value: '0',
					        	minPickerHeight: 250, //最小高度，不设置的话有时候下拉会出问题
					        	autoScroll: true,
					        	shadow: true,
					        	allowBlank: false, fieldLabel: '上级菜单', blankText:'上级菜单不能为空!' }
					    ]
					},{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
					    items: [
					           {id:'menuUrl', name: 'menu.menuUrl',fieldLabel: '请求路径'}
					    ]
					},{
					    defaults: {flex: 1, xtype:'textfield',labelWidth:70, labelAlign:'right'},
					    items: [
					           {id:'iconCls', name: 'menu.iconCls',fieldLabel: '显示图标'}
					    ]
					},{
						defaults: {flex: 1,xtype:'radiogroup',labelWidth:70,labelAlign:'right'},
						items: [
						        {id:'menuType', fieldLabel: '菜单类型', columns:4, defaults: {name: 'menu.menuType'},
						        	items: [{inputValue: 0,boxLabel: '系统菜单'}, {inputValue: 1,boxLabel: '业务菜单', checked: true}]
						        }
						]
					},{
						defaults: {flex: 1,xtype:'radiogroup',labelWidth:70,labelAlign:'right'},
						items: [
						        {id:'expanded', fieldLabel: '是否展开', columns:4, defaults: {name: 'menu.expanded'},
						        	items: [{inputValue: false,boxLabel: '收缩', checked: true}, {inputValue: true,boxLabel: '展开'}]
						        }
						]
					}
				],
				buttons:[{
					text : '保 存', // 按钮文本
					iconCls : 'accept', // 按钮图标
					handler : function() { // 按钮响应函数
						if (menuForm.form.isValid()) {
							menuForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									menuWindow.hide();
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
						menuWindow.hide();
					}
				}]
			});
	
	var menuWindow = new Ext.Window({
				title : '修改', // 窗口标题
				layout : 'fit',
				width : 450,
				height : 270,
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
				items: [menuForm]
			});
	
	var store = Ext.create('Ext.data.Store', {
		  		  pageSize : 20,
		          fields: ['id', 'menuName', 'parentId', 'leaf', 'expanded', 'menuUrl', 'sortNo', 'iconCls', 'menuType'],
		          proxy: {
		              type: 'ajax',
		              url : '/sys/queryMenuPage.atc',
		              reader : {
		                  root: 'menuList',
		                  totalProperty: 'totalCount'
		              }
		          }
		    });
  	
  	var columns= [rowNum,
	            {header : '名称',dataIndex: 'menuName', width: 140},
	            {header : '请求路径',dataIndex: 'menuUrl', width: 240},
	            {header : '排序号',dataIndex: 'sortNo', width: 70},
	            {header : '上级菜单',dataIndex: 'parentId', width: 140, renderer:
	            	function(v){
	            		var menu = store.getById(v);
	            		return menu != null ? menu.data.menuName : '无';
	            	}
	            },
	            {header : '叶子节点',dataIndex: 'leaf', width: 80, renderer: function(v){if(v) return '是'; else return '否';}},
	            {header : '是否展开',dataIndex: 'expanded', width: 80, renderer: function(v){if(v) return '展开'; else return '收缩';}},
	            {header : '图标',dataIndex: 'iconCls', width: 80},
	            {header : '菜单类型',dataIndex: 'menuType', width: 80,renderer: function(v){if(v=='0') return '系统菜单'; else return '业务菜单';}},
	            {header : '菜单编号',dataIndex: 'id', width: 120}
	  	]; 
	
	var tbar = Ext.create('Ext.toolbar.Toolbar', {
			region : 'north',
			border : false,
	        items: [{
	        	xtype: 'button',
	        	text: '新建',
	        	iconCls: 'add',
	        	hidden : !parent.haveActionMenu('Add'),
	        	handler: function() {
	        		menuWindow.setTitle('新建');
	        		menuForm.getForm().reset();
	        		menuForm.getForm().url = '/sys/insertSysMenu.atc';
	        		Ext.getCmp('parentId').setValue(currentId);
	        		menuWindow.show();
	            }
	        },{
	        	xtype: 'button',
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
			title: '菜单详情',
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
	//		plugins: [cellEditing],
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
			'menu.parentId': currentId,
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
		if(record.raw.id != '01'){
			menuWindow.setTitle('修改');
			menuForm.loadRecord(record);
			menuForm.getForm().url = '/sys/updateSysMenu.atc';
//			Ext.getCmp('expanded').show();
			Ext.getCmp('menuType').setValue({'menu.menuType' : record.data.menuType});
			Ext.getCmp('expanded').setValue({'menu.expanded' : record.data.expanded});
			menuWindow.show();
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
		if(!record.raw.leaf){
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '该菜单含有子菜单，请先删除子菜单！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.ERROR
		    });
			return;
		}
		Ext.Msg.confirm('请确认', '确定要删除这个菜单项吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/sys/deleteSysMenu.atc',
					params : {
						'menu.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							menuWindow.hide();
							Ext.example.msg('提示', result.msg);
							treeStore.reload();
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
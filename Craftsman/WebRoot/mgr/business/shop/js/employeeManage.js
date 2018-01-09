Ext.require(['Ext.ux.TreePicker']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
				header : 'NO',
				resizable: true,
				width : 40
			});
	
	var employeeForm = new Ext.form.FormPanel({
				id : 'employeeForm',
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
					           {id:'id', name:'employee.id', xtype:'hiddenfield'},
					           {id:'isGroup', name:'employee.isGroup', xtype:'hiddenfield', value: false},
					           {id:'name', name:'employee.name', fieldLabel: '姓名', allowBlank: false, blankText:'姓名不能为空!'}
					    ]
					},{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
					    items: [
					           {id:'position', name: 'employee.position', fieldLabel: '职位' },
					           {id:'entryDate', name: 'employee.entryDate', fieldLabel: '入职日期', xtype:'datefield', format:'Y/m/d', value: new Date() }
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
						items: [
						        {id:'enable', xtype:'radiogroup', defaults: {name: 'employee.enable', labelWidth: 40},fieldLabel: '是否离职',
						        	items: [{inputValue: true, boxLabel: '否', checked: true}, {inputValue: false, boxLabel: '是'}]
						        },
						        {id:'lastDate', name: 'employee.lastDate', fieldLabel: '修改日期', disabled: true }
						]
					}
				],
				buttons:[{
					text : '保 存',
					iconCls : 'accept',
					handler : function() {
						if (employeeForm.form.isValid()) {
							employeeForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									employeeWindow.hide();
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
						employeeWindow.hide();
					}
				}]
			});
	
	var employeeWindow = new Ext.Window({
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
				items: [employeeForm]
			});
	
	var store = Ext.create('Ext.data.Store', {
		          fields: ['id', 'deptCode', 'name', 'position', 'entryDate', 'enable', 'lastDate'],
		          proxy: {
		              type: 'ajax',
		              url : '/shop/queryEmployeeByDept.atc',
		              reader : {
		                  root: 'employeeList'
		              }
		          }
		    });
  	
  	var columns= [rowNum,
	            {header : 'ID',dataIndex: 'id', hidden: true},
	            {header : '部门代码',dataIndex: 'deptCode', hidden: true},
	            {header : '名称',dataIndex: 'name',width: 100},
	            {header : '职位',dataIndex: 'position', width: 120},
	            {header : '入职日期',dataIndex: 'entryDate', width: 160},
	            {header : '是否离职',dataIndex: 'enable', width: 80, renderer: function(v){return (v ? '否' : '是');}},
	            {header : '最后操作日期',dataIndex: 'lastDate', width: 160}
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
	        		employeeWindow.setTitle('新建');
	        		employeeForm.getForm().reset();
	        		employeeForm.getForm().url = '/shop/insertEmployee.atc';
	        		employeeWindow.show();
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
		} 
		selModel.deselectAll();
		employeeWindow.setTitle('修改');
		employeeForm.loadRecord(record);
		Ext.getCmp('enable').setValue({'employee.enable' : record.data.enable});
		employeeForm.getForm().url = '/shop/updateEmployee.atc';
		employeeWindow.show();
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
		Ext.Msg.confirm('请确认', '确定要删除这个员工吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/shop/deleteEmployee.atc',
					params : {
						'employee.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							employeeWindow.hide();
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
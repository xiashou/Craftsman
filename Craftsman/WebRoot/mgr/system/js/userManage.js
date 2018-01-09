Ext.require(['Ext.ux.TreePicker']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var userId, roleId, areaId;
	if(typeof(parent.ZZ) != 'undefined') areaId = parent.ZZ;
	var currentId = parent.AA;
	
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
            url: '/sys/queryDeptTree.atc?parentId=' + parent.AA + (Ext.isEmpty(areaId) ? '' : '&areaId='+areaId)
        }
    });
	
	var deptTree = new Ext.tree.TreePanel({
	    store: treeStore,
	    height: '100%',
        border : false,
        rootVisible: false,
        listeners:{
	    	itemclick: function(tree, record, item, index) {
	    		userWindow.hide();
	    		currentId = record.raw.id;
	    		store.reload({
	    			params:{
	    				'sysUser.deptId' : currentId,
	    				'sysUser.areaId' : areaId,
	    				start : 0,
	    				limit : bbar.pageSize
	    			}
	    		});
	    	}
	    }
	});

	var store = Ext.create('Ext.data.Store', {
		pageSize : 20,
        fields: ['userId', 'userName', 'password', 'realName', 'roleId', 'deptId', 'lastLogin'],
        proxy: {
            type: 'ajax',
            url : '/sys/queryUserPage.atc',
            reader : {
                root: 'userList',
                totalProperty: 'totalCount'
            }
        }
    });
	
	var roleSimpleStore = Ext.create('Ext.data.Store', {
		fields : [{name: 'roleId'}, {name: 'roleName'}],  
		proxy:{
            type: 'ajax',
            url : '/sys/queryAllSimpleRole.atc',
            reader : {
                root: 'roleList'
            }
        }
	});
	roleSimpleStore.load();
	
	var deptSimpleStore = Ext.create('Ext.data.Store', {
		fields : [{name: 'id'}, {name: 'deptName'}],  
		proxy:{
            type: 'ajax',
            url : '/sys/queryAllDeptSimple.atc',
            reader : {
                root: 'deptList'
            }
        }
	});
	deptSimpleStore.load();
	
	var areaStore = Ext.create('Ext.data.Store', {
		fields : [{name: 'areaId'}, {name: 'areaName'}],  
		proxy:{
            type: 'ajax',
            url : '/base/queryAllBaseArea.atc',
            reader : {
                root: 'areaList'
            }
        }
	});
	areaStore.load();
	
	// 每页显示条数下拉选择框
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

	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesizeCombo ]
	});
	
	var userForm = new Ext.form.FormPanel({
		id : 'userForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            xtype: 'fieldcontainer',
	        layout: 'hbox'
        },
		bodyPadding: '5 10 0 0',
		border : false,
		items : [{
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
		                {id:'userId',name:'sysUser.userId',xtype:'hiddenfield'},
		                {id:'roleId',name:'sysUser.roleId',xtype:'hiddenfield'},
	                    {id:'userName', name: 'sysUser.userName', allowBlank: false, fieldLabel: '账号', blankText:'账号不能为空!'}
		         ]
		     },{
		         defaults: {flex: 1,xtype:'textfield',inputType: 'password',labelWidth:70,labelAlign:'right'},
		         items: [
	                    {id:'password', name: 'sysUser.password',allowBlank: false, fieldLabel: '密码', blankText:'密码不能为空!'}
		         ]
		     },{
		    	 defaults: {flex: 1, xtype:'treepicker', labelWidth:70, labelAlign:'right'},
				    items: [
				           {id:'deptId', name: 'sysUser.deptId', store: treeStore, displayField: 'text', value: '0',
				        	minPickerHeight: 250, //最小高度，不设置的话有时候下拉会出问题
				        	autoScroll: true,
				        	shadow: true,
				        	allowBlank: false, fieldLabel: '所属部门', blankText:'所属部门不能为空!' }
				    ]
		     },{
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
	                    {id:'realName', name: 'sysUser.realName', allowBlank: false, fieldLabel: '姓名', blankText:'名称不能为空!'}
		         ]
		     },{
				 defaults: {flex: 1,xtype:'radiogroup',labelWidth:70,labelAlign:'right'},
				 items: [
				        {id:'locked', fieldLabel: '是否锁定', columns:4, defaults: {name: 'sysUser.locked'},
				        	items: [{inputValue: false, boxLabel: '正常', checked: true}, {inputValue: true,boxLabel: '锁定'}]
				        }
				 ]
		     }
		],
		buttons:[{
			text : '保 存',
			iconCls : 'accept',
			handler : function() {
				if (userForm.form.isValid()) {
					userForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							Ext.example.msg('提示', action.result.msg);
							userWindow.hide();
							grid.getStore().reload();
						},
						failure : function(form, action) {
							var msg = action.result.msg;
							Ext.MessageBox.show({
     				           	title: '提示',
     				           	msg: msg,
     				           	buttons: Ext.MessageBox.OK,
     				           	icon: Ext.MessageBox.ERROR
                            });
						}
					});
				}
			}
		},{
			text : '关 闭 ', // 按钮文本
			iconCls : 'stop', // 按钮图标
			handler : function() { // 按钮响应函数
				userWindow.hide();
			}
		}]
	});
    
    var userWindow = new Ext.Window({
		title : '添加用户', 
		layout : 'fit',
		width : 380,
		height : 240,
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
		items : [userForm]
	});

	var toolbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '新建',
        	iconCls: 'add',
        	hidden: !parent.haveActionMenu('Add'),
        	handler: function(){
        		userWindow.setTitle('新建');
        		userForm.getForm().reset();
        		Ext.getCmp("userName").setReadOnly(false);
        		userForm.getForm().url = '/sys/insertSysUser.atc';
        		Ext.getCmp('deptId').setValue(currentId);
        		Ext.getCmp('locked').setValue({'sysUser.locked' : false});
        		userWindow.show();
            }
        },{
        	xtype: 'button',
        	text: '修改',
        	iconCls: 'pencil',
        	hidden: !parent.haveActionMenu('Edit'),
        	handler: function() {
        		initEdit();
            }
        },{
        	xtype: 'button',
        	id:'delete',
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
	 
	// 表格实例
	var grid = new Ext.grid.GridPanel({
		columns : [rowNum, 
		 {header : '绑定角色', align:'center', xtype: 'actioncolumn',width: 80,
            items: [{
                iconCls: 'group_link',
                tooltip: '绑定角色',
                handler: function(grid, rowIndex, colIndex) {
                	if(parent.haveActionMenu('Bind')){
                		var record = grid.getStore().getAt(rowIndex);
                		userId = record.raw.userId;
                		roleId = record.raw.roleId;
                		if(!Ext.isEmpty(roleId) && roleId < 10){
                			Ext.MessageBox.show({
                				title: '提示',
                				msg: '超级管理员无法绑定！',
                				buttons: Ext.MessageBox.OK,
                				icon: Ext.MessageBox.ERROR
                			});
                		} else if(roleId == parent.BB) {
                			Ext.MessageBox.show({
                				title: '提示',
                				msg: '无法操作自己！',
                				buttons: Ext.MessageBox.OK,
                				icon: Ext.MessageBox.ERROR
                			});
                		} else {
                			userGrantWindow.setTitle(record.raw.userName + ' 用户角色绑定');
                			userGrantWindow.show();
                		}
                	}
                },
                getClass: function(metadata, r){
                	if(!Ext.isEmpty(r.record.raw.roleId) && (r.record.raw.roleId < 10 || !parent.haveActionMenu('Bind') || r.record.raw.roleId == parent.BB))
                		return 'group_link2';
                	else
                		return 'group_link';
                		
                }
            }]
		}, {
			header : '用户ID',
			dataIndex : 'userId',
			hidden : true,
		}, {
			header : '账号',
			dataIndex : 'userName',
			sortable : true,
			width : 100
		}, {
			header : '姓名',
			dataIndex : 'realName',
			width : 180
		}, {
			header : '角色',
			dataIndex : 'roleId',
			width : 180,
			renderer: function(id){
				var role = roleSimpleStore.findRecord('roleId', id);
				return role ? role.raw.roleName : id;
			}
		}, {
			header : '所属部门',
			dataIndex : 'deptId',
			width : 200,
			renderer: function(value){
				var dept = deptSimpleStore.findRecord('id', value);
				return dept ? dept.raw.deptName : value;
			}
		}, {
			header : '所属区域',
			dataIndex : 'areaId',
			width : 120,
			renderer: function(value){
				var area = areaStore.findRecord('areaId', value);
				return area ? area.raw.areaName : value;
			}
		}, {
			header : '最后登录时间',
			dataIndex : 'lastLogin',
			width : 160
		}],
		autoScroll : true,
		columnLines : true,
		selModel : selModel,
		frame : false,
		region : 'center',
		store : store,
		tbar : toolbar, 
		bbar : bbar,
		stripeRows:true, 
		viewConfig : {
			enableTextSelection:true
			// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
			// forceFit : true
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
			title : '组织架构',
			iconCls : 'chart_organisation',
			tools : [{
				id : 'refresh',
				handler : function() {
			        treeStore.reload();
				}
			}],
			collapsible : true,
			width : 280,
			minSize : 160,
			maxSize : 280,
			split : true,
			region : 'west',
			autoScroll : true,
			items : [ deptTree ]
		}, {
			region : 'center',
			title : '用户管理',
			iconCls : 'user',
			layout : 'fit',
			border : false,
			items : [ grid ]
		}]
	});
	
	store.load({
		params : {
			start : 0,
			limit : bbar.pageSize,
			'sysUser.deptId': parent.AA,
			'sysUser.areaId': areaId
		}
	});
	
	var roleStore = Ext.create('Ext.data.Store', {
        fields: ['roleId', 'roleName', 'deptId', 'roleType', 'locked'],
        proxy: {
            type: 'ajax',
            url : '/sys/queryRolePage.atc',
            reader : {
                root: 'roleList',
                totalProperty: 'totalCount'
            }
        }
    });
	
	var selRoleModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	// 表格实例
	var roleGrid = new Ext.grid.GridPanel({
		columns : [ 
		{
			header : '角色ID',
			dataIndex : 'roleId',
			hidden : true,
		}, {
			header : '名称',
			dataIndex : 'roleName',
			width : 180
		}, {
			header : '所属部门',
			dataIndex : 'deptId',
			width : 180,
			renderer: function(value){
				return deptSimpleStore.findRecord('id', value).raw.deptName;
			}
		}, {
			header : '角色类型',
			dataIndex : 'roleType',
			width : 180,
			renderer: function(value){
				return value == '0' ? '管理角色' : '业务角色';
			}
		}, {
			header : '是否锁定',
			dataIndex : 'locked',
			width : 100,
			renderer: function(value){
				return value == 0 ? '<font color=green>否</font>' : '<font color=red>是</font>'
			}
		}],
		autoScroll : true,
		columnLines : true,
		selModel : selRoleModel,
		title: '角色列表',
		iconCls: 'folder_user',
		frame : false,
		region : 'center',
		store : roleStore,
		stripeRows:true, 
		viewConfig : {
			enableTextSelection:true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
	    }
	});
	
	var deptTreePanel = new Ext.tree.TreePanel({
		region : 'west',
		title : '办事处',
		iconCls : 'chart_organisation',
	    store: treeStore,
	    split : true,
		width : 240,
		minSize : 180,
		maxSize : 300,
		collapsible : false,
		autoScroll : true,
		animate : false,
        border : false,
        rootVisible: false,
        listeners:{
	    	itemclick: function(tree, record, item, index) {
	    		userWindow.hide();
	    		roleStore.reload({
	    			params:{
	    				'sysRole.deptId' : record.raw.id,
	    				'sysRole.areaId' : areaId,
	    				start : 0,
	    				limit : bbar.pageSize
	    			}
	    		});
	    	}
	    }
	});
	
	var userGrantWindow = new Ext.Window({
		layout : 'border',
		width : 930,
		height : document.body.clientHeight - 100,
		resizable : true,
		draggable : true,
		closeAction : 'hide',
		iconCls : 'application_side_tree',
		modal : true,
		collapsible : true,
		maximizable : true,
		animateTarget : document.body,
		//如果使用autoLoad,建议不要使用动画效果
		buttonAlign : 'right',
		constrain : true,
		border : false,
		items : [deptTreePanel, roleGrid],
		buttons : [{
					text : '保存',
					iconCls : 'accept',
					handler : function() {
						bindSysUserRole();
					}
				},{
					text : '关闭',
					iconCls : 'stop',
					handler : function() {
						userGrantWindow.hide();
					}
				}]
	});
	
	userGrantWindow.on('show', function() {
		roleStore.reload({
			params:{
				'sysRole.deptId' : parent.AA,
				start : 0,
				limit : bbar.pageSize
			}
		});
	});
	
	roleStore.on("load",function(){
		var selectRole = roleStore.findRecord('roleId', roleId);
		if(selectRole)
			selRoleModel.select(selectRole);  
    }); 
	
	function bindSysUserRole(){
		
		var record = selRoleModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		userGrantWindow.setLoading('用户角色保存中...');
		Ext.Ajax.request({
			url : '/sys/bindSysUserRole.atc',
			params : {
				'sysUser.userId' : userId,
				'sysUser.roleId': record.raw.roleId
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				userGrantWindow.setLoading(false);
				if(result.success){
					Ext.example.msg('提示', result.msg);
					userGrantWindow.hide();
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
				userGrantWindow.setLoading(false);
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
		userWindow.setTitle('修改');
		Ext.getCmp("userName").setReadOnly(true);
		userForm.getForm().url = '/sys/updateSysUser.atc';
		record.data.password = '';
		userForm.loadRecord(record);
		Ext.getCmp('deptId').setValue(currentId);
		Ext.getCmp('locked').setValue({'sysUser.locked' : record.raw.locked});
		userWindow.show();
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
		if(!Ext.isEmpty(record.data.roleId) && record.data.roleId < 10){
			Ext.MessageBox.show({
				title: '提示',
				msg: '超级管理员无法删除！',
				buttons: Ext.MessageBox.OK,
				icon: Ext.MessageBox.ERROR
			});
		} else if(record.data.roleId == parent.BB) {
			Ext.MessageBox.show({
				title: '提示',
				msg: '无法操作自己！',
				buttons: Ext.MessageBox.OK,
				icon: Ext.MessageBox.ERROR
			});
		} else {
			Ext.Msg.confirm('请确认', '确认删除这个用户吗?', function(btn, text) {
				if(btn == 'yes'){
					Ext.Ajax.request({
						url : '/sys/deleteSysUser.atc',
						params : {
							'sysUser.userId' : record.raw.userId
						},
						success : function(resp, opts) {
							var result = Ext.decode(resp.responseText);
							if(result.success){
								Ext.example.msg('提示', result.msg);
								store.load({
									params : {
										start : 0,
										limit : bbar.pageSize,
										'sysUser.deptId': parent.AA
									}
								});
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
	                        Ext.Msg.alert('提示', result.msg);
						}
					});
				}
			});      
		}
	}

});
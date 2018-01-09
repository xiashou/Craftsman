Ext.require(['Ext.ux.TreePicker']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var roleId, areaId;
	if(typeof(parent.ZZ) != 'undefined') areaId = parent.ZZ;
	var currentId = '001';
	
	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
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
	    		roleWindow.hide();
	    		currentId = record.raw.id;
	    		store.reload({
	    			params:{
	    				'sysRole.deptId' : currentId,
	    				'sysRole.areaId' : areaId,
	    				start : 0,
	    				limit : bbar.pageSize
	    			}
	    		});
	    	}
	    }
	});

	var store = Ext.create('Ext.data.Store', {
		pageSize : 20,
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
	
	var authorizationStore = Ext.create('Ext.data.TreeStore', {
		id: 'authorizationStore',
		autoLoad: false
    });
	
	var roleForm = new Ext.form.FormPanel({
		id : 'roleForm',
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
		                {id:'roleId',name:'sysRole.roleId',xtype:'hiddenfield'},
	                    {id:'roleName', name: 'sysRole.roleName', allowBlank: false, fieldLabel: '名称', blankText:'名称不能为空!'}
		         ]
			},{
				defaults: {flex: 1, xtype:'treepicker', labelWidth:70, labelAlign:'right'},
			    items: [
			           {id:'deptId', name: 'sysRole.deptId', store: treeStore, displayField: 'text', value: '0',
			        	minPickerHeight: 250, //最小高度，不设置的话有时候下拉会出问题
			        	autoScroll: true,
			        	shadow: true,
			        	allowBlank: false, fieldLabel: '所属部门', blankText:'所属部门不能为空!' }
			    ]
			},{
				 defaults: {flex: 1,xtype:'radiogroup',labelWidth:70,labelAlign:'right'},
				 items: [
				        {id:'roleType', fieldLabel: '角色类型', columns:3, defaults: {name: 'sysRole.roleType'},
				        	items: [{inputValue: 0, boxLabel: '管理角色', checked: true}, {inputValue: 1,boxLabel: '业务角色'}]
				        }
				 ]
		     },{
				 defaults: {flex: 1,xtype:'radiogroup',labelWidth:70,labelAlign:'right'},
				 items: [
				        {id:'locked', fieldLabel: '是否锁定', columns:4, defaults: {name: 'sysRole.locked'},
				        	items: [{inputValue: false, boxLabel: '正常', checked: true}, {inputValue: true,boxLabel: '锁定'}]
				        }
				 ]
		     }
		],
		buttons:[{
			text : '保 存', // 按钮文本
			iconCls : 'accept', // 按钮图标
			handler : function() { // 按钮响应函数
				if (roleForm.form.isValid()) {
					roleForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							Ext.example.msg('提示', action.result.msg);
							roleWindow.hide();
							treeStore.reload();
							deptSimpleStore.reload();
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
				roleWindow.hide();
			}
		}]
	});
	
	var authorizationPanel = Ext.create('Ext.tree.Panel', {
		id : 'authorizationPanel',
		xtype: 'check-tree',
		rootVisible: false,
		plain : true,
		border : false,
		region : 'center',
		autoScroll : true,
        containerScroll : true,
        multiSelect : true,
		store : authorizationStore,
		tbar : [{
			id : 'saveAuthorization',
			xtype : 'button',
			iconCls : 'disk',
			text : '保存权限',
			scope : this,
			handler : saveRolePermission
		}],
		listeners: {
		    checkchange: function(node, checked) {
//		    	//如果被勾选的节点有父节点，则将其父节点全部改为根节点状态
//		    	node.bubble(function(node){
//		    		node.set('checked', checked); 
//		    		return true; 
//		    	}); 
		    	checkboxSelected(node,checked);
		    }
		}
	});
	
    var roleWindow = new Ext.Window({
		title : '添加角色', 
		layout : 'fit',
		width : 380,
		height : 200,
		x: 300,
		y: 100,
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
		items : [roleForm]
	});
    
    var authorizationWindow = Ext.create('Ext.Window', {
    	title : '角色授权', 
		width : 400,
		height : 600,
		draggable : true,
		modal : false,
		closeAction:'hide',
		collapsible : true,
		titleCollapse : true,
		maximizable : true,
		border : false,
		animCollapse : true,
		constrain : true,
//		autoScroll : true,
		overflowY: 'scroll',
		items : [authorizationPanel]
    });

	var toolbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '新建',
        	iconCls: 'add',
        	hidden : !parent.haveActionMenu('Add'),
        	handler: function(){
        		roleWindow.setTitle('新建');
        		roleForm.getForm().reset();
        		roleForm.getForm().url = '/sys/insertSysRole.atc';
        		Ext.getCmp('deptId').setValue(currentId);
        		Ext.getCmp('roleType').setValue({'sysRole.roleType' : '1'});
        		roleWindow.show();
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
        	id:'delete',
        	text: '删除',
        	iconCls: 'delete',
        	hidden : !parent.haveActionMenu('Del'),
        	handler: function(){
        		initDelete();
            }
        }]
    });
	
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
				limit : bbar.pageSize,
				'sysRole.deptId': parent.AA
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
	
	var selModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	 
	// 表格实例
	var grid = new Ext.grid.GridPanel({
		columns : [rowNum, 
		{header : '授权', align:'center', xtype: 'actioncolumn',width: 50,
            items: [{
                iconCls: 'group_key',
                tooltip: '授权',
                handler: function(grid, rowIndex, colIndex) {
                	if(parent.haveActionMenu('Auth')){
                		var record = grid.getStore().getAt(rowIndex);
                		roleId = record.raw.roleId;
                		if(roleId < 10){
                			Ext.MessageBox.show({
                				title: '提示',
                				msg: '超级管理员无法授权！',
                				buttons: Ext.MessageBox.OK,
                				icon: Ext.MessageBox.ERROR
                			});
                		} else if(roleId == parent.BB) {
                			Ext.MessageBox.show({
                				title: '提示',
                				msg: '无法授权自己！',
                				buttons: Ext.MessageBox.OK,
                				icon: Ext.MessageBox.ERROR
                			});
                		} else {
                			authorizationWindow.setTitle(record.data.roleName + ' 角色授权');
                			authorizationStore.setProxy({
                				type: 'ajax',
                				reader: 'json',
                				url: '/sys/queryAuthorizationTree.atc?roleId=' + roleId + '&menuType=' + record.raw.roleType
                			});
                			authorizationStore.reload();
                			if(record.raw.locked)
                				Ext.getCmp('saveAuthorization').setDisabled(true);
                			else 
                				Ext.getCmp('saveAuthorization').setDisabled(false);
                			authorizationWindow.show();
                		}
                	}
                },
                getClass: function(metadata, r){
                	if(r.record.raw.roleId < 10 || !parent.haveActionMenu('Auth') || r.record.raw.roleId == parent.BB)
                		return 'group_key2';
                	else
                		return 'group_key';
                		
                }
            }]
        },{
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
			width : 200,
			renderer: function(value){
				var dept = deptSimpleStore.findRecord('id', value);
				return dept ? dept.raw.deptName : value;
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
		selModel : selModel,
		frame : false,
		region : 'center',
		store : store,
		tbar : toolbar, 
		bbar : bbar,
		stripeRows:true, 
//		plugins: [cellEditing],
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
			title : '角色列表',
			iconCls: 'group',
			region : 'center',
			layout : 'fit',
			border : false,
			items : [ grid ]
		}]
	});
	
	store.load({
		params : {
			start : 0,
			limit : bbar.pageSize,
			'sysRole.deptId': parent.AA,
			'sysRole.areaId': areaId
		}
	});
	
	function checkboxSelected(node, checked) {
		setChildChecked(node, checked);
		setParentChecked(node, checked);
	}
	// 选择子节点树
	function setChildChecked(node, checked) {
		node.expand();
		node.set('checked', checked);
		if (node.hasChildNodes()) {
			node.eachChild(function(child) {
				setChildChecked(child, checked);
			});
		}
	}
	// 选择父节点树
	function setParentChecked(node, checked) {
		node.set({
			checked : checked
		});
		var parentNode = node.parentNode;
		if (parentNode != null) {
			var flag = false;
			parentNode.eachChild(function(childnode) {
				if (childnode.get('checked')) {
					flag = true;
				}
			});
			if (checked == false) {
				if (!flag) {
					setParentChecked(parentNode, checked);
				}
			} else {
				if (flag) {
					setParentChecked(parentNode, checked);
				}
			}
		}
	}
	
	function saveRolePermission(){
		
		var selected = authorizationPanel.getChecked();
		var ids = new Array();
		for(var i = 0; i < selected.length; i++){
			Ext.Array.clean(ids);
			if(!Ext.Array.contains(ids, selected[i].raw.id) && selected[i].data.id != 'root'){
				ids.push(selected[i].raw.id);
			}
		}
		if(Ext.isEmpty(selected)){
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.ERROR
             });
			return;
		}
		if(Ext.isEmpty(roleId)){
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '参数 不能为空！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.ERROR
             });
			return;
		}
		
		authorizationPanel.setLoading('权限保存中...');
		Ext.Ajax.request({
			url : '/sys/bindSysRoleMenu.atc',
			params : {
				ids : ids,
				roleId : roleId
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				authorizationPanel.setLoading(false);
				authorizationWindow.hide();
                Ext.example.msg('提示', result.msg);
			},
			failure : function(resp, opts) {
				authorizationPanel.setLoading(false);
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
		roleWindow.setTitle('修改');
		roleForm.getForm().url = '/sys/updateSysRole.atc';
		roleForm.loadRecord(record);
		Ext.getCmp('locked').setValue({'sysRole.locked' : record.raw.locked});
		Ext.getCmp('roleType').setValue({'sysRole.roleType' : record.raw.roleType});
		roleWindow.show();
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
		Ext.Msg.confirm('请确认', '确认删除这个角色吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/sys/deleteSysRole.atc',
					params : {
						'sysRole.roleId' : selModel.getSelection()[0].data.roleId
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							Ext.example.msg('提示', result.msg);
							store.load({
								params : {
									start : 0,
									limit : bbar.pageSize,
									'sysRole.deptId': parent.AA
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
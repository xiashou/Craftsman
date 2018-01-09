Ext.require(['Ext.ux.TreePicker']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var areaId;
	if(typeof(parent.ZZ) != 'undefined') areaId = parent.ZZ;
	
	var currentId = '1';
	
	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var treeStore = Ext.create('Ext.data.TreeStore', {
		id:'treeStore',
		fields: ['id', 'parentId', 'deptName', 'deptCode', 'deptType', 'areaId', 'createTime', 'enable'],
        proxy: {
            type: 'ajax',
            reader: 'json',
            url: '/sys/queryDeptTree.atc?parentId=' + parent.AA + (Ext.isEmpty(areaId) ? '' : '&areaId='+areaId)
        }
    });
	
	var deptTypeCombo = Ext.create('Ext.form.ComboBox', {
		id: 'deptTypeCombo',
		name: 'dept.deptType',
		allowBlank: false, 
		fieldLabel: '类型',
		labelWidth: 80,
		labelAlign:'right',
		triggerAction : 'all',
		mode : 'local',
		store: parent.GDeptTypeStore,
		valueField : 'value',
		displayField : 'text',
		editable : false
	});
	
	var areaTreeStore = Ext.create('Ext.data.TreeStore', {
		id:'areaTreeStore',
		fields: ['areaId','areaName','text'],
        proxy: {
            type: 'ajax',
            reader: 'json',
            url: '/base/queryBaseAreaTree.atc'
        }
    });
	
	if(!Ext.isEmpty(areaId))
		areaTreeStore.load({
			params:{
				areaId: areaId
			}
		});
	else
		areaTreeStore.load();
	
	var deptTree = new Ext.tree.TreePanel({
			    store: treeStore,
			    height: '100%',
		        border : false,
		        rootVisible: false
			});
	
	var deptForm = new Ext.form.FormPanel({
				id : 'deptForm',
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
					    defaults: {flex: 1, xtype:'textfield', labelWidth:80, labelAlign:'right'},
					    items: [
					           {id:'id', name:'dept.id', xtype:'hiddenfield'},
					           {id:'deptName', name: 'dept.deptName', allowBlank: false, fieldLabel: '组织名称', blankText:'名称不能为空!' }
					    ]
					},{
					    defaults: {flex: 1, xtype:'treepicker', labelWidth:80, labelAlign:'right'},
					    items: [
					           {id:'parentId', name: 'dept.parentId', store: treeStore, displayField: 'deptName', value: '0',
					        	minPickerHeight: 250, //最小高度，不设置的话有时候下拉会出问题
					        	autoScroll: true,
					        	shadow: true,
					        	allowBlank: false, fieldLabel: '上级区域', blankText:'上级区域不能为空!' }
					    ]
					},{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:80, labelAlign:'right'},
					    items: [
					           deptTypeCombo, {id:'deptCode', name: 'dept.deptCode', fieldLabel: '业务对照码'} 
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:80, labelAlign:'right'},
						items: [
						        {id:'createTime', name: 'dept.createTime', xtype:'hiddenfield'}
						]
					},{
				    	 defaults: {flex: 1, xtype:'treepicker', labelWidth:80, labelAlign:'right'},
				    	 items: [
				    	         {id:'areaId', name: 'dept.areaId', store: areaTreeStore, displayField: 'text', value: areaId,
				    	        	 minPickerHeight: 250, //最小高度，不设置的话有时候下拉会出问题
				    	        	 autoScroll: true,
				    	        	 shadow: true,
				    	        	 fieldLabel: '所属省市', blankText:'所属区域不能为空!' }
				    	   ]
					}
				],
				buttons:[{
					text : '保 存', // 按钮文本
					iconCls : 'accept', // 按钮图标
					handler : function() { // 按钮响应函数
						if (deptForm.form.isValid()) {
							if(!Ext.isEmpty(deptTypeCombo.getValue()) && deptTypeCombo.getValue() == 3){
								if(Ext.isEmpty(Ext.getCmp("deptCode").getValue())){
									Ext.MessageBox.show({
								           title: '提示',
								           msg: '店铺类型业务对照码不能为空！',
								           buttons: Ext.MessageBox.OK,
								           icon: Ext.MessageBox.ERROR
								    });
									return false;
								}
							}
							deptForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									deptWindow.hide();
									Ext.example.msg('提示', action.result.msg);
									treeStore.reload();
//									store.reload();
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
						deptWindow.hide();
					}
				}]
			});
	
	var deptWindow = new Ext.Window({
				title : '修改', // 窗口标题
				layout : 'fit',
				width : 400,
				height : 210,
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
				items: [deptForm]
			});
	
	var tbar = Ext.create('Ext.toolbar.Toolbar', {
			region : 'north',
			border : false,
	        items: [{
	        	xtype: 'button',
	        	text: '新建',
	        	iconCls: 'add',
	        	hidden : !parent.haveActionMenu('Add'),
	        	handler: function() {
	        		deptWindow.setTitle('新建');
	        		deptForm.getForm().reset();
	        		deptForm.getForm().url = '/sys/insertSysDept.atc';
	        		Ext.getCmp('parentId').setValue(currentId);
	        		deptWindow.show();
	            }
	        },{
	        	xtype: 'button',
	        	text: '修改',
	        	iconCls: 'pencil',
	        	hidden : !parent.haveActionMenu('Edit'),
	        	handler: function() {
	        		if(currentId != '1'){
	        			var record = treeStore.getById(currentId);
	        			initEdit(record);
	        		} else
	        			Ext.MessageBox.show({
  				           title: '提示',
  				           msg: '顶级部门无法修改！',
  				           buttons: Ext.MessageBox.OK,
  				           icon: Ext.MessageBox.ERROR
                        });
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
	
	var statusRender = function(v){
		return v ? '<span style="color:green;">正常</span>' : '<span style="color:red;">禁用</span>';
	}
	
	var typeRender = function(v){
		return Ext.isEmpty(parent.GDeptTypeStore.findRecord("value", v)) ? '无' : parent.GDeptTypeStore.findRecord("value", v).data.text;
	}
	
	var columns= [
	  	            {xtype: 'treecolumn', text: '名称', flex: 1, dataIndex: 'deptName'},
		            {text : '编码',dataIndex: 'id', width: 180},
		            {text : '上级编码',dataIndex: 'parentId', width: 180},
		            {text : '类型',dataIndex: 'deptType', width: 180, renderer: typeRender},
		            {text : '业务对照码',dataIndex: 'deptCode', width: 100},
		            {text : '是否可用',dataIndex: 'enable', width: 100, renderer: statusRender},
		            {text : '创建时间',dataIndex: 'createTime', width: 180}
		  	]; 
	
	Ext.define('Neptune.view.tree.widget.TreeGrid', {
		id: 'treeGrid',
	    extend: 'Ext.tree.Panel',
	    xtype: 'treeGrid',
	    store: treeStore,
	    columns: columns,
	    rootVisible: false,
	    useArrows: true,
	    rowLines: true,
	    animate : false,
	    autoScroll : true,
	    tbar : tbar, 
		listeners:{
			itemdblclick: function(grid, record, item, index, e) {
				if(parent.haveActionMenu('Edit'))
					initEdit(record);
	        },
	        itemclick: function(grid, record, item, index, e) {
	        	currentId = record.data.id;
	        }
	    }
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			region : 'center',
		    layout: 'fit',
			items : [{
	            xtype: 'treeGrid',
	            initComponent: function() {
	                delete this.columns[0].flex;
	                this.columns[0].width = 450;
	                this.columns[0].locked = true;
	                Neptune.view.tree.widget.TreeGrid.prototype.initComponent.apply(this, arguments);
	            }
	        }]
		}]
	});

	function initEdit(record){
		deptWindow.setTitle('修改');
		deptForm.loadRecord(record);
		deptTypeCombo.setValue(record.data.deptType);
		deptForm.getForm().url = '/sys/updateSysDept.atc';
		deptWindow.show();
	}
	
	function initDelete(){
		var dept = treeStore.getById(currentId);
		if(!dept.data.leaf){
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '该菜单含有子部门，请先删除子部门！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.ERROR
		    });
			return;
		}
		Ext.Msg.confirm('请确认', '确定要删除这个部门吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/sys/deleteSysDept.atc',
					params : {
						'dept.id' : currentId
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							deptWindow.hide();
							Ext.example.msg('提示', result.msg);
							treeStore.reload();
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
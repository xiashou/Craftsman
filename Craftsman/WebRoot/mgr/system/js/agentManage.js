Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var areaStore = Ext.create('Ext.data.Store', {
		fields: ['areaId', 'areaName'],
		proxy: {
			type: 'ajax',
			url : '/base/queryProvinceBaseArea.atc',
			reader : {
				root: 'areaList'
			}
		}
	});
	areaStore.load();
	
	var areaCombo = Ext.create('Ext.form.ComboBox', {
		id: 'areaCombo',
		name: 'sysAgent.areaId',
		triggerAction : 'all',
		mode : 'local',
		store: areaStore,
		valueField : 'areaId',
		displayField : 'areaName',
		labelWidth:70,
		labelAlign:'right',
		fieldLabel: '代理区域',
		editable : false,
		allowBlank: false
	});
	
	var agentForm = new Ext.form.FormPanel({
		id : 'agentForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            layout: 'hbox',
            xtype:'fieldcontainer'
        },
		bodyPadding: '5 10 0 0',
		border : false,
		items : [{
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
		               {id:'agentId',name:'sysAgent.agentId',xtype:'hiddenfield'},
	                   {id:'agentName', name: 'sysAgent.agentName', allowBlank: false, fieldLabel: '账号', blankText:'账号不能为空!'}
		         ]
		     },{
		         defaults: {flex: 1,xtype:'textfield',inputType: 'password',labelWidth:70,labelAlign:'right'},
		         items: [
	                   {id:'password', name: 'sysAgent.password',allowBlank: false, fieldLabel: '密码', blankText:'密码不能为空!'}
		         ]
		     },{
		    	 defaults: {flex: 1, labelWidth:70, labelAlign:'right'},
				    items: [areaCombo]
		     },{
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
	                   {id:'realName', name: 'sysAgent.realName', allowBlank: false, fieldLabel: '姓名', blankText:'名称不能为空!'}
		         ]
		     },{
				 defaults: {flex: 1,xtype:'radiogroup',labelWidth:70,labelAlign:'right'},
				 items: [
				        {id:'locked', fieldLabel: '是否锁定', columns:4, defaults: {name: 'sysAgent.locked'},
				        	items: [{inputValue: false, boxLabel: '正常', checked: true}, {inputValue: true,boxLabel: '锁定'}]
				        }
				 ]
		     }
	    ],
		buttons:[{
			text : '保 存',
			iconCls : 'accept',
			handler : function() {
				if (agentForm.form.isValid()) {
					agentForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							agentWindow.hide();
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
				agentWindow.hide();
			}
		}]
	});
	
	var agentWindow = new Ext.Window({
		title : '修改', // 窗口标题
		layout : 'fit',
		width : 400,
		height : 250,
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
		items: [agentForm]
	});
	
	var store = Ext.create('Ext.data.Store', {
          fields: ['agentId', 'agentName', 'areaId', 'realName', 'locked', 'lastLogin'],
          proxy: {
              type: 'ajax',
              url : '/sys/queryAllSysAgent.atc',
              reader : {
                  root: 'agentList'
              }
          }
    });
  	
  	var columns= [rowNum,
            {header : 'ID',dataIndex: 'agentId', width: 80, hidden: true},
            {header : '账号',dataIndex: 'agentName', width: 140},
            {header : '区域',dataIndex: 'areaId', width: 140, renderer: function(value){
				var area = areaStore.findRecord('areaId', value);
				return area ? area.raw.areaName : value;
			}},
            {header : '姓名',dataIndex: 'realName', width: 140},
            {header : '是否锁定',dataIndex: 'locked', width: 140,renderer: function(value){
				return value == 0 ? '<font color=green>否</font>' : '<font color=red>是</font>'
			}},
            {header : '最后登录时间',dataIndex: 'lastLogin', width: 160}
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
	        		agentWindow.setTitle('新建');
	        		agentForm.getForm().reset();
	        		Ext.getCmp("agentName").setReadOnly(false);
	        		agentForm.getForm().url = '/sys/insertSysAgent.atc';
	        		agentWindow.show();
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
		title: '系统代理',
		iconCls: 'table_relationship',
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
		agentWindow.setTitle('修改');
		agentForm.loadRecord(record);
		Ext.getCmp("agentName").setReadOnly(true);
		areaCombo.setValue(record.data.areaId);
		Ext.getCmp('locked').setValue({'sysAgent.locked' : record.raw.locked});
		agentForm.getForm().url = '/sys/updateSysAgent.atc';
		agentWindow.show();
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
		Ext.Msg.confirm('请确认', '确定要删除这个代理吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/sys/deleteSysAgent.atc',
					params : {
						'sysAgent.agentId' : record.data.agentId
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							agentWindow.hide();
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
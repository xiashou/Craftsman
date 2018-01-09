/**
 * 资金支出
 */
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	

	var assetsStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'name'],
        proxy: {
            type: 'ajax',
            url : '/fico/queryAssetsByDept.atc',
            reader : {
                root: 'assetsList'
            }
        }
	});
	
	var assetsCombo = Ext.create('Ext.form.ComboBox', {
		id: 'assetsId',
		name: 'expend.assetsId',
		store: assetsStore,
		allowBlank: false,
        enforceMaxLength: true,
        fieldLabel: '选择来源',
        labelWidth:70, 
        labelAlign:'right',
        valueField: 'id',
        displayField: 'name',
        queryMode: 'local',
        editable : false,
        forceSelection: true
	});
	
	var assCombo = Ext.create('Ext.form.ComboBox', {
		store: assetsStore,
        enforceMaxLength: true,
        emptyText: '资金来源',
        width: 140,
        valueField: 'id',
        displayField: 'name',
        queryMode: 'local',
        editable : false
	});
	
	assetsStore.load();
	
	var exptypeStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'name'],
        proxy: {
            type: 'ajax',
            url : '/fico/queryExptypeListByDept.atc',
            reader : {
                root: 'typeList'
            }
        }
	});
	
	var exptypeCombo = Ext.create('Ext.form.ComboBox', {
		id: 'exptypeId',
		name: 'expend.exptypeId',
		store: exptypeStore,
		allowBlank: false,
        enforceMaxLength: true,
        fieldLabel: '支出类型',
        labelWidth:70, 
        labelAlign:'right',
        valueField: 'id',
        displayField: 'name',
        queryMode: 'local',
        editable : false,
        forceSelection: true
	});
	
	var typeCombo = Ext.create('Ext.form.ComboBox', {
		store: exptypeStore,
        enforceMaxLength: true,
        emptyText: '支出类型',
        width: 140,
        valueField: 'id',
        displayField: 'name',
        queryMode: 'local',
        editable : false
	});
	
	exptypeStore.load();
	
	var expendForm = new Ext.form.FormPanel({
				id : 'expendForm',
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
					           {id:'id', name:'expend.id', xtype:'hiddenfield'},
					           exptypeCombo,assetsCombo
					    ]
					}, {
					    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
					    items: [
					           {id:'price', name: 'expend.price', xtype:'numberfield',fieldLabel: '金额',allowBlank: false},
					           {xtype:'displayfield', value:''}
					    ]
					}, {
						defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
						items: [
						        {id:'remark', name: 'expend.remark', xtype:'textfield',fieldLabel: '备注'}
						]
					}
				],
				buttons:[{
					text : '保 存',
					iconCls : 'accept',
					handler : function() {
						if (expendForm.form.isValid()) {
							expendForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									expendWindow.hide();
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
						expendWindow.hide();
					}
				}]
			});
	
	var expendWindow = new Ext.Window({
		title : '修改', // 窗口标题
		layout : 'fit',
		width : 450,
		height : 180,
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
		items: [expendForm]
	});
	
	var store = Ext.create('Ext.data.Store', {
		  pageSize: 20,
          fields: ['id', 'assetsId', 'price', 'exptypeId', 'remark', 'creator', 'createTime'],
          proxy: {
              type: 'ajax',
              url : '/fico/queryExpenditureByDeptPage.atc',
              reader : {
                  root: 'expendList',
                  totalProperty: 'totalCount'
              }
          }
    });
	
	var typeRenderer = function(value, cellmeta, record) {
        var index = exptypeStore.find("id", value);
        var rec = exptypeStore.getAt(index);
        var displayText = "";
        if (rec == null)
        	displayText = value;
        else {
        	displayText = rec.data.name;
        	record.data.exptypeId = rec.data.name;
        }
        return displayText;
    };
	
	var assetsRenderer = function(value, cellmeta, record) {
        var index = assetsStore.find("id", value);
        var rec = assetsStore.getAt(index);
        var displayText = "";
        if (rec == null)
        	displayText = value;
        else
        	displayText = rec.data.name;
        return displayText;
    };
  	
  	var columns= [rowNum,
            {header : 'ID',dataIndex: 'id', hidden: true},
            {header : '支出类型',dataIndex: 'exptypeId', width: 140, renderer: typeRenderer},
            {header : '支出来源',dataIndex: 'assetsId', width: 140, renderer: assetsRenderer},
            {header : '金额',dataIndex: 'price', width: 140, renderer: Ext.util.Format.numberRenderer("0.00"),summaryType:'sum'},
            {header : '备注',dataIndex: 'remark', width: 140},
            {header : '创建人',dataIndex: 'creator', width: 140},
            {header : '创建时间',dataIndex: 'createTime', width: 140}
  	]; 
  	
	var tbar = Ext.create('Ext.toolbar.Toolbar', {
			region : 'north',
			border : false,
	        items: [{
	        	xtype: 'datefield',
	        	id: 'startDate',
	        	emptyText: '开始日期',
	        	width: 140,
	        	format:'Y/m/d'
	        },{
	        	xtype: 'datefield',
	        	id: 'endDate',
	        	emptyText: '结束日期',
	        	width: 140,
	        	format:'Y/m/d'
	        },typeCombo, assCombo,{
	        	xtype: 'button',
	        	id: 'search',
	        	text: ' 查 询 ',
	        	iconCls: 'preview', 
	        	handler: function(){
	        		store.load({
	        			params : getParams()
	        		});
	            }
	        },{
	        	xtype: 'button',
	        	id: 'insert',
	        	text: '新建',
	        	iconCls: 'add',
	        	hidden : !parent.haveActionMenu('Add'),
	        	handler: function() {
	        		expendWindow.setTitle('新建');
	        		expendForm.getForm().reset();
	        		expendForm.getForm().url = '/fico/insertExpenditure.atc';
	        		expendWindow.show();
	            }
//	        },{
//	        	xtype: 'button',
//	        	id: 'update',
//	        	text: '修改',
//	        	iconCls: 'pencil',
////	        	hidden : !parent.haveActionMenu('Edit'),
//	        	handler: function() {
//	        		initEdit();
//	            }
	        },{
	        	xtype: 'button',
	        	id: 'delete',
	        	text: '删除',
	        	iconCls: 'delete', 
	        	hidden : !parent.haveActionMenu('Del'),
	        	handler: function(){
	        		initDelete();
	            }
	        },'->',{
	        	xtype: 'button',
	        	text:'导出Excel',
	        	iconCls : 'page_white_excel',
	        	handler: function() {
	        		grid.downloadExcelXml();
	        	}
	        }]
	    });
	
	var PagesizeCombo = new Ext.form.ComboBox({
		name : 'Pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 200, '200条/页' ]]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});

	var number = parseInt(PagesizeCombo.getValue());
	
	// 改变每页显示条数reload数据
	PagesizeCombo.on("select", function(comboBox) {
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
		items : [ '-', '&nbsp;&nbsp;', PagesizeCombo ]
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
		title: '资金支出',
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
		tools:[{
            type:'help',
            tooltip: '操作指引',
            callback: function(panel, tool, event) {
            	parent.global.openSystemHelpTab();
            }
        }],
        features: [{
            ftype: 'summary',
            dock: 'bottom'
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
	
	store.load({
		params:{
			start: 0,
			limit: 20
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
//		} else if(record.data.editable == 0) {
//			Ext.MessageBox.show({
//				title: '提示',
//				msg: '不可编辑！',
//				buttons: Ext.MessageBox.OK,
//				icon: Ext.MessageBox.INFO
//		    });
//			return;
		}
		expendWindow.setTitle('修改');
		expendForm.loadRecord(record);
		expendForm.getForm().url = '/fico/updateExpenditure.atc';
		expendWindow.show();
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
		Ext.Msg.confirm('请确认', '确定要删除这个项吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/fico/deleteExpenditure.atc',
					params : {
						'expend.id' : record.data.id,
						'expend.assetsId': record.data.assetsId,
						'expend.price': record.data.price
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							expendWindow.hide();
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
	
	function getParams(){
		return {
			'expend.assetsId': assCombo.getValue(),
			'expend.exptypeId': typeCombo.getValue(),
			'expend.startDate': parent.dateFormat(Ext.getCmp("startDate").getValue()),
			'expend.endDate': parent.dateFormat(Ext.getCmp("endDate").getValue())
		};
	}
	
});
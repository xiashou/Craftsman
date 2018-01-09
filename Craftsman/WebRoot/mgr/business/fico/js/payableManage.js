/**
 * 应付管理
 */
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var supplierId, allRece = 0;
	
	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var supplierStore = Ext.create('Ext.data.Store', {
		autoLoad: true,
		fields: ['id', 'name'],
		proxy: {
			type: 'ajax',
			url : '/shop/querySupplierByDept.atc',
			reader : {
				root: 'supplierList'
			}
		}
	});
	
	var supplierCombo = Ext.create('Ext.form.ComboBox', {
		store: supplierStore,
        enforceMaxLength: true,
        emptyText: '供应商',
        width: 140,
        valueField: 'id',
        displayField: 'name',
        queryMode: 'local',
        editable : true,
        forceSelection: true
	});
	
	var payableForm = new Ext.form.FormPanel({
				id : 'payableForm',
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
					           {id:'id', name:'assets.id', xtype:'hiddenfield'},
					           {id:'name', name: 'assets.name',allowBlank: false, fieldLabel: '名称', blankText:'名称不能为空!' }
					    ]
					}, {
					    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
					    items: [
					           {id:'price', name: 'assets.price', xtype:'numberfield',fieldLabel: '金额'}
					    ]
					}
				],
				buttons:[{
					text : '保 存',
					iconCls : 'accept',
					handler : function() {
						if (assetsForm.form.isValid()) {
							assetsForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									assetsWindow.hide();
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
						assetsWindow.hide();
					}
				}]
			});
	
	var receivableWindow = new Ext.Window({
		title : '修改', // 窗口标题
		layout : 'fit',
		width : 350,
		height : 140,
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
		items: [payableForm]
	});
	
	var store = Ext.create('Ext.data.Store', {
          fields: ['supplierId', 'name', 'mobile', 'payable', 'contact', 'repayment', 'residual', 'lastUpdate'],
          proxy: {
              type: 'ajax',
              url : '/fico/queryPayableByDeptPage.atc',
              reader : {
                  root: 'payableList',
                  totalProperty: 'totalCount'
              }
          }
    });
	
  	var columns= [rowNum,
            {header : 'ID',dataIndex: 'supplierId', hidden: true},
            {header : '供应商',dataIndex: 'name', width: 140,summaryType: function(){return '合计'}},
            {header : '联系人',dataIndex: 'contact', width: 140},
            {header : '手机号',dataIndex: 'mobile', width: 140},
            {header : '总应付金额',dataIndex: 'payable', renderer: Ext.util.Format.numberRenderer("0.00"),summaryType:'sum'},
            {header : '总还款金额',dataIndex: 'repayment', renderer: Ext.util.Format.numberRenderer("0.00"),summaryType:'sum'},
            {header : '剩余未付金额',dataIndex: 'residual', renderer: Ext.util.Format.numberRenderer("0.00"),summaryType:'sum'},
            {header : '最后修改时间',dataIndex: 'lastUpdate', width: 140},
            {xtype: 'actioncolumn',header : '付款',width: 70,align:'center',sortable: false,menuDisabled: true, hidden: !parent.haveActionMenu('Pay'),
                items: [{
                    iconCls: 'money',
                    tooltip: '付款',
                    scope: this,
                    handler: function(grid, rowIndex){
                    	backForm.getForm().reset();
                    	var record = grid.getStore().getAt(rowIndex);
                    	Ext.getCmp("supplierId").setValue(record.data.supplierId);
                    	allRece = record.data.residual;
                    	backWindow.setTitle(record.data.name + " 应付");
                    	backWindow.show();
                    }
                }]
            },
            {xtype: 'actioncolumn',header : '结清',width: 70,align:'center',sortable: false,menuDisabled: true, hidden: !parent.haveActionMenu('Done'),
                items: [{
                    iconCls: 'newspaper',
                    tooltip: '结清',
                    scope: this,
                    handler: function(grid, rowIndex){
                    	var record = grid.getStore().getAt(rowIndex);
                    	settmentDone(record.data.supplierId);
                    }
                }]
            },
            {xtype: 'actioncolumn',header : '应付记录',width: 70,align:'center',sortable: false,menuDisabled: true,
                items: [{
                    iconCls: 'application_view_columns',
                    tooltip: '应付记录',
                    scope: this,
                    handler: function(grid, rowIndex){
                    	var record = grid.getStore().getAt(rowIndex);
                    	supplierId = record.data.supplierId;
                    	HistoryBillWindow.show();
                    }
                }]
            },
            {xtype: 'actioncolumn',header : '付款记录',width: 70,align:'center',sortable: false,menuDisabled: true,
                items: [{
                    iconCls: 'application_view_columns',
                    tooltip: '付款记录',
                    scope: this,
                    handler: function(grid, rowIndex){
                    	var record = grid.getStore().getAt(rowIndex);
                    	supplierId = record.data.supplierId;
                    	repaymentWindow.show();
                    }
                }]
            }
  	]; 
  	
  	var MemberStore = Ext.create('Ext.data.Store', {
		fields: ['memId', 'vipNo', 'name', 'mobile', 'carShort', 'carCode', 'carNumber', 'fullCarNumber'],
		proxy:{
            type: 'ajax',
            url : '/member/queryMemberByKeyword.atc',
            reader : {
                root: 'memberList',
                totalProperty: 'totalCount'
            }
        }
    });
  	
  	var MemberCombo = Ext.create('Ext.form.ComboBox', {
        store: MemberStore,
        name: 'orderItem.memId',
        allowBlank: false,
        valueField: 'memId',
        displayField: 'vipNo',
        typeAhead: false,
        hideTrigger:true,
        width: 190,
        queryParam: 'keyword',	//搜索参数字段
        minChars: 2,
        emptyText: '会员',
        pageSize: 10,
        listConfig: {
            loadingText: '查找中...',
            emptyText: '<div style="padding: 5px;">没有找到相关车主！</div>',
            getInnerTpl: function() {
                return '<div>{fullCarNumber}/{name}/{mobile}</div>';
            }
		}
	});
  	
  	var statusCombo = Ext.create('Ext.form.ComboBox', {
		store: Ext.create('Ext.data.Store', {
			fields: ['id', 'name'],
			data:[
			      {id: '0', name: '未结清'},
			      {id: '1', name: '已结清'}
//			      {id: '', name: '全部'}
			]
		}),
		allowBlank: false,
        enforceMaxLength: true,
        listConfig: {
            minWidth: null
        },
        width: 100,
        valueField: 'id',
        displayField: 'name',
        value: '0',
        queryMode: 'local',
        emptyText: '状态',
        editable : false,
        forceSelection: true
	});
	
	var tbar = Ext.create('Ext.toolbar.Toolbar', {
			region : 'north',
			border : false,
	        items: [statusCombo, supplierCombo, {
	        	xtype: 'button',
	        	id: 'search',
	        	text: ' 查 询 ',
	        	iconCls: 'preview', 
	        	handler: function(){
	        		store.load({
	        			params: {
	        				'payable.status': statusCombo.getValue(),
	        				'payable.supplierId': supplierCombo.getValue()
	        			}
	        		});
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
				limit : bbar.pageSize,
				memId : memId
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
		title: '应付管理',
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
		forceFit: true,
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
		params: {
			'payable.status': statusCombo.getValue(),
			'payable.supplierId': supplierCombo.getValue()
		}
	});
	

    function settmentDone(supplierId){
    	if(!Ext.isEmpty(supplierId)){
    		Ext.Msg.confirm('请确认', '确认与这个供应商结清吗?', function(btn, text) {
    			if(btn == 'yes'){
    				Ext.Ajax.request({
    					url : '/fico/updatePayableDone.atc',
    					params : {
    						'payable.supplierId' : supplierId
    					},
    					success : function(resp, opts) {
    						var result = Ext.decode(resp.responseText);
    						if(result.success){
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
    	                    Ext.Msg.alert('提示', result.msg);
    					}
    				});
    			}
    		}); 
		}
    }
	
	
	///////////////////////////////////////////////还款
	var backForm = new Ext.form.FormPanel({
		id : 'backForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            xtype:'textfield'
        },
		bodyPadding: '5 10 0 0',
		border : false,
		items : [{
			xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'numberfield',labelWidth:70,labelAlign:'right'},
		         items: [
	                   {id:'supplierId', name: 'repayment.supplierId',xtype:'hiddenfield'},
	                   {id:'payType', name: 'repayment.payType',xtype:'hiddenfield'},
	                   {flex: 4, id:'repayment', name: 'repayment.repayment',fieldLabel: '付款金额',allowBlank: false, blankText:'付款金额不能为空!'},
	                   {flex: 1, xtype: 'button', text:'付全款', handler : function() {
	                	   Ext.getCmp("repayment").setValue(allRece);
	                   }}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textarea', labelWidth:70, labelAlign:'right'},
		    	 items: [
		    	         {id:'remark', name: 'repayment.remark',fieldLabel: '备注'},
		    	 ]
		     }
		],
		buttons:[{
			text : '现 金',
			iconCls : 'accept',
			handler : function() {
				Ext.getCmp("payType").setValue("C");
				submitBcakForm();
			}
		},{
			text : '银行卡',
			iconCls : 'accept',
			handler : function() {
				Ext.getCmp("payType").setValue("B");
				submitBcakForm();
			}
		},{
			text : ' 微 信 ',
			iconCls : 'accept',
			handler : function() {
				Ext.getCmp("payType").setValue("W");
				submitBcakForm();
			}
		},{
			text : ' 支付宝 ',
			iconCls : 'accept',
			handler : function() {
				Ext.getCmp("payType").setValue("A");
				submitBcakForm();
			}
		}]
	});
	
    var backWindow = new Ext.Window({
		title : '付款', 
		layout : 'fit',
		width : 380,
		height : 200,
		y: 150,
		resizable : false,
		draggable : false,
		closable : true,
		modal : false,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		closeAction : 'hide',
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		items : [backForm]
	});
    
    function submitBcakForm() {
    	if (backForm.form.isValid()) {
			backForm.form.submit({
				url : '/fico/insertRepayment.atc',
				waitTitle : '提示',
				method : 'POST',
				waitMsg : '正在处理数据,请稍候...',
				success : function(form, action) {
					Ext.example.msg('提示', action.result.msg);
					store.reload();
					backWindow.hide();
				},
				failure : function(form, action) {
					var msg = action.result.msg;
					Ext.MessageBox.alert('提示', msg);
				}
			});
		}
    }
	
	
	///////////////////////////////////////////////历史应付记录
	var HistoryBillStore = Ext.create('Ext.data.Store', {
		fields: ['goodsId', 'goodsName', 'number', 'inPrice', 'supplier', 'settlement', 'purchdate', 'creator'],
		proxy:{
            type: 'ajax',
            url : '/goods/queryAllGoodsInStockListPage.atc',
            reader : {
                root: 'inStockList',
                totalProperty: 'totalCount'
            }
        }
    });
	var HistoryBillRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var supplierRenderer = function(value, cellmeta, record) {
        var index = supplierStore.find("id", value);
        var record = supplierStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else
        	displayText = record.data.name;
        return displayText;
    };
	
	var HistoryBillColumns= [HistoryBillRowNum,
	            {header : '商品id',dataIndex: 'goodsId',menuDisabled: true,hidden:true},
	            {header : '供应商',dataIndex: 'supplier',menuDisabled: true, renderer: supplierRenderer},
	 	        {header : '商品',dataIndex: 'goodsName',menuDisabled: true, width:140},
	 	        {header : '数量',dataIndex: 'number',menuDisabled: true},
	 	        {header : '进货价',dataIndex: 'inPrice',menuDisabled: true, width:110, renderer: Ext.util.Format.numberRenderer("0.00")},
	 	        {header : '是否结算',dataIndex: 'settlement',menuDisabled: true,renderer: function(v){return v == '1' ? '是' : '否'}},
	 	        {header : '采购日期',dataIndex: 'purchdate',menuDisabled: true}
//	 	        {header : '操作人',dataIndex: 'creator',menuDisabled: true}
	 	];
	
	var HistoryBillPagesizeCombo = new Ext.form.ComboBox({
		name : 'HistoryBillPagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 200, '200条/页' ]]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '10',
		editable : false,
		width : 85
	});

	var HistoryBillNumber = parseInt(HistoryBillPagesizeCombo.getValue());
	
	// 改变每页显示条数reload数据
	HistoryBillPagesizeCombo.on("select", function(comboBox) {
		HistoryBillBbar.pageSize = parseInt(comboBox.getValue());
		HistoryBillNumber = parseInt(comboBox.getValue());
		HistoryBillStore.pageSize = parseInt(comboBox.getValue());
		HistoryBillStore.load({
			params : {
				start : 0,
				limit : HistoryBillBbar.pageSize,
				'inStock.supplier' : supplierId,
				'inStock.settlement' : 0
			}
		});
	});
	
	var HistoryBillBbar = new Ext.PagingToolbar({
		pageSize : HistoryBillNumber,
		store : HistoryBillStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', HistoryBillPagesizeCombo ]
	});
		
	var HistoryBillGrid = Ext.create('Ext.grid.GridPanel', {
		minHeight: 220,
		columns : HistoryBillColumns,
		store : HistoryBillStore, 
		bbar : HistoryBillBbar,
		region : 'center',
		autoScroll : true,
		columnLines : true,
		frame : false,
		stripeRows: true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
	    }
	});
	
	var HistoryBillWindow = Ext.create('Ext.Window',{
		layout : 'fit',
		title: '历史应付记录',
		iconCls: 'award_star_bronze_3',
		width : 730,
		height : 440,
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
		items: [HistoryBillGrid]
	});
	HistoryBillStore.on('refresh', function(){
		HistoryBillStore.getProxy().extraParams = {'inStock.supplierId' : supplierId,'inStock.settlement' : 0};
	});
	HistoryBillWindow.on('show', function(){
		HistoryBillStore.load({
			params : {
				start : 0,
				limit : HistoryBillBbar.pageSize,
				'inStock.supplier' : supplierId,
				'inStock.settlement' : 0
			}
		});
	});
	
	
	///////////////////////////////////////////////付款记录
	var repaymentStore = Ext.create('Ext.data.Store', {
		fields: ['supplierId', 'name', 'mobile', 'repayment', 'payType', 'remark', 'creator', 'creatorName', 'createTime'],
		proxy:{
            type: 'ajax',
            url : '/fico/queryRepaymentBySupplierId.atc',
            reader : {
                root: 'repayList'
            }
        }
    });
	var repaymentRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var payTypeRenderer = function(value, cellmeta, record) {
		var type = parent.GAssetsTypeStore.findRecord("value", value);
		if(type)
			return type.data.text;
		else
			return value;
	}
	
	var repaymentColumns= [repaymentRowNum,
            {header : '供应商',dataIndex: 'name',menuDisabled: true},
 	        {header : '支付方式',dataIndex: 'payType',menuDisabled: true, renderer: payTypeRenderer},
 	        {header : '还款金额',dataIndex: 'repayment',menuDisabled: true, width:110, renderer: Ext.util.Format.numberRenderer("0.00")},
 	        {header : '备注',dataIndex: 'remark',menuDisabled: true},
 	        {header : '创建人',dataIndex: 'creatorName',menuDisabled: true},
 	        {header : '创建时间',dataIndex: 'createTime',menuDisabled: true, width:180}
 	];
	
		
	var repaymentGrid = Ext.create('Ext.grid.GridPanel', {
		minHeight: 220,
		columns : repaymentColumns,
		store : repaymentStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		frame : false,
		stripeRows: true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
	    }
	});
	
	var repaymentWindow = Ext.create('Ext.Window',{
		layout : 'fit',
		title: '付款记录',
		iconCls: 'award_star_bronze_3',
		width : 730,
		height : 440,
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
		items: [repaymentGrid]
	});
	repaymentWindow.on('show', function(){
		repaymentStore.load({
			params : {
				'repayment.supplierId': supplierId
			}
    	});
	});
	
});
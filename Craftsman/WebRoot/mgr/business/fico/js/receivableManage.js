/**
 * 应收管理
 */
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var memId, carId, allRece = 0;
	
	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var receivableForm = new Ext.form.FormPanel({
				id : 'receivableForm',
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
		items: [receivableForm]
	});
	
	var store = Ext.create('Ext.data.Store', {
          fields: ['carId', 'memId', 'name', 'mobile', 'carNumber', 'billPrice', 'repayment', 'residual', 'lastUpdate'],
          proxy: {
              type: 'ajax',
              url : '/fico/queryReceivableByDeptPage.atc',
              reader : {
                  root: 'rList',
                  totalProperty: 'totalCount'
              }
          }
    });
	
  	var columns= [rowNum,
            {header : 'ID',dataIndex: 'memId', hidden: true},
            {header : 'CODE',dataIndex: 'carId', hidden: true},
            {header : '姓名',dataIndex: 'name', width: 140,summaryType: function(){return '合计'}},
            {header : '手机号',dataIndex: 'mobile', width: 140},
            {header : '车牌号',dataIndex: 'carNumber', width: 140,summaryType:'count'},
            {header : '总挂账金额',dataIndex: 'billPrice', renderer: Ext.util.Format.numberRenderer("0.00"),summaryType:'sum'},
            {header : '总还款金额',dataIndex: 'repayment', renderer: Ext.util.Format.numberRenderer("0.00"),summaryType:'sum'},
            {header : '剩余未还金额',dataIndex: 'residual', renderer: Ext.util.Format.numberRenderer("0.00"),summaryType:'sum'},
            {header : '最后修改时间',dataIndex: 'lastUpdate', width: 140},
            {xtype: 'actioncolumn',header : '还款',width: 70,align:'center',sortable: false,menuDisabled: true, hidden: !parent.haveActionMenu('Repay'),
                items: [{
                    iconCls: 'money',
                    tooltip: '还款',
                    scope: this,
                    handler: function(grid, rowIndex){
                    	backForm.getForm().reset();
                    	var record = grid.getStore().getAt(rowIndex);
                    	Ext.getCmp("memId").setValue(record.data.memId);
                    	Ext.getCmp("carId").setValue(record.data.carId);
                    	allRece = record.data.residual;
                    	backWindow.setTitle(record.data.name + "/" + record.data.carNumber + " 还款");
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
                    	receivableDone(record.data.memId, record.data.carId);
                    }
                }]
            },
            {xtype: 'actioncolumn',header : '挂账记录',width: 70,align:'center',sortable: false,menuDisabled: true,
                items: [{
                    iconCls: 'application_view_columns',
                    tooltip: '挂账记录',
                    scope: this,
                    handler: function(grid, rowIndex){
                    	var record = grid.getStore().getAt(rowIndex);
                    	memId = record.data.memId;
                    	HistoryBillWindow.show();
                    }
                }]
            },
            {xtype: 'actioncolumn',header : '还款记录',width: 70,align:'center',sortable: false,menuDisabled: true,
                items: [{
                    iconCls: 'application_view_columns',
                    tooltip: '还款记录',
                    scope: this,
                    handler: function(grid, rowIndex){
                    	var record = grid.getStore().getAt(rowIndex);
                    	memId = record.data.memId;
                    	carId = record.data.carId;
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
	        items: [statusCombo, MemberCombo, {
	        	xtype: 'button',
	        	id: 'search',
	        	text: ' 查 询 ',
	        	iconCls: 'preview', 
	        	handler: function(){
	        		store.load({
	        			params : {
	        				'receivable.memId' : MemberCombo.getValue(),
	        				'receivable.status' : statusCombo.getValue()
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
		title: '应收管理',
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
	
	store.load();
	
	function receivableDone(memId, carId){
    	if(!Ext.isEmpty(memId) && !Ext.isEmpty(carId)){
    		Ext.Msg.confirm('请确认', '确认与这个会员结清吗?', function(btn, text) {
    			if(btn == 'yes'){
    				Ext.Ajax.request({
    					url : '/fico/updateReceivableDone.atc',
    					params : {
    						'receivable.memId' : memId,
    						'receivable.carId' : carId
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
	                   {id:'memId', name: 'repayment.memId',xtype:'hiddenfield'},
	                   {id:'carId', name: 'repayment.carId',xtype:'hiddenfield'},
	                   {id:'payType', name: 'repayment.payType',xtype:'hiddenfield'},
	                   {flex: 4, id:'repayment', name: 'repayment.repayment',fieldLabel: '还款金额',allowBlank: false, blankText:'还款金额不能为空!'},
	                   {flex: 1, xtype: 'button', text:'还全款', handler : function() {
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
		title : '还款', 
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
	
	
	///////////////////////////////////////////////历史挂账记录
	var HistoryBillStore = Ext.create('Ext.data.Store', {
		fields: ['orderId', 'saleDate', 'name', 'mobile', 'carId', 'carNumber', 'pbill', 'creator'],
		proxy:{
            type: 'ajax',
            url : '/order/queryBillHeadListByMemIdPage.atc',
            reader : {
                root: 'hList',
                totalProperty: 'totalCount'
            }
        }
    });
	var HistoryBillRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var HistoryBillColumns= [HistoryBillRowNum,
	            {header : '订单号',dataIndex: 'orderId',menuDisabled: true},
	 	        {header : '销售日期',dataIndex: 'saleDate',menuDisabled: true, width:140},
	 	        {header : '姓名',dataIndex: 'name',menuDisabled: true},
	 	        {header : '电话号码',dataIndex: 'mobile',menuDisabled: true, width:110},
	 	        {header : '车辆ID',dataIndex: 'carId',menuDisabled: true, hidden:true},
	 	        {header : '车牌号',dataIndex: 'carNumber',menuDisabled: true},
	 	        {header : '挂账金额',dataIndex: 'pbill',menuDisabled: true, renderer: Ext.util.Format.numberRenderer("0.00")},
	 	        {header : '操作人',dataIndex: 'creator',menuDisabled: true}
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
		number = parseInt(comboBox.getValue());
		HistoryBillStore.pageSize = parseInt(comboBox.getValue());
		HistoryBillStore.reload({
			params : {
				start : 0,
				limit : HistoryBillBbar.pageSize,
				memId : memId
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
		title: '历史挂账记录',
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
		HistoryBillStore.getProxy().extraParams = {'memId': memId};
	});
	HistoryBillWindow.on('show', function(){
		HistoryBillStore.load({
			params : {
				start : 0,
				limit : HistoryBillBbar.pageSize,
				memId : memId
			}
		});
	});
	
	
	///////////////////////////////////////////////还款记录
	var repaymentStore = Ext.create('Ext.data.Store', {
		fields: ['carId', 'memId', 'name', 'mobile', 'carNumber', 'repayment', 'payType', 'remark', 'creator', 'creatorName', 'createTime'],
		proxy:{
            type: 'ajax',
            url : '/fico/queryRepaymentByReceId.atc',
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
            {header : '会员姓名',dataIndex: 'name',menuDisabled: true},
// 	        {header : '手机号',dataIndex: 'mobile',menuDisabled: true, width:140},
 	        {header : '车牌号',dataIndex: 'carNumber',menuDisabled: true},
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
		title: '还款记录',
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
				'repayment.memId': memId,
	    		'repayment.carId': carId
			}
    	});
	});
	
});
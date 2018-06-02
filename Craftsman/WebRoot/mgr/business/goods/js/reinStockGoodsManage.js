/**
 * 退货出库
 */
Ext.onReady(function() {
	
	var inStockRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	Ext.define('InStockModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'goodsId', type: 'string'},
	        {name: 'goodsName', type: 'string'},
	        {name: 'number', type: 'string'},
	        {name: 'inPrice', type: 'float'},
	        {name: 'supplier', type: 'string'},
	        {name: 'settlement', type: 'string'},
	        {name: 'settdate', type: 'string'},
	        {name: 'purchdate', type: 'string'}
	    ]
	});
	
	var supplierStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'name'],
		proxy: {
			type: 'ajax',
			url : '/shop/querySupplierByDept.atc',
			reader : {
				root: 'supplierList'
			}
		}
	});
	supplierStore.load();
	
	var supplierCombo = Ext.create('Ext.form.ComboBox', {
		id: 'supplierCombo',
		triggerAction : 'all',
		mode : 'local',
		store: supplierStore,
		valueField : 'id',
		displayField : 'name',
		emptyText: '供应商',
		editable : true
	});
	
	var settlementCombo = Ext.create('Ext.form.ComboBox', {
		name : 'settlement',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 1, '是' ], [ 0, '否' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : 1,
		editable : false,
		listeners: {
        	select: function(cb, records){
        		var rowRecord = inStockGrid.getSelectionModel().getLastSelected();
        		inStockCellEditing.startEdit(rowRecord, 6);
            }
        }
	});
	
	var stockStore = Ext.create('Ext.data.Store', {
		pageSize : 20,
		fields: ['goodsId', 'goodsName', 'number', 'stockNum', 'inPrice', 'tPrice', 'supplier', 'settlement', 'settdate', 'purchdate', 'creator', 'createTime'],
		proxy: {
		    type: 'ajax',
			url : '/goods/queryGoodsInStockListPage.atc',
			reader : {
			    root: 'inStockList',
			    totalProperty: 'totalCount'
			}
		}
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
    
    var stockColumns= [inStockRowNum,
               {header : 'ID',dataIndex: 'goodsId', width: 60, hidden: true},
               {header : '商品',dataIndex: 'goodsName', width: 250},
               {header : '进货数量（直接修改数量退货）',dataIndex: 'number', width: 120, renderer: Ext.util.Format.numberRenderer("0.00"), editor: {xtype:'numberfield', minValue: 1}},
               {header : '当前库存',dataIndex: 'stockNum', width: 120, renderer: Ext.util.Format.numberRenderer("0.00")},
               {header : '进货单价',dataIndex: 'inPrice', width: 120,summaryType: 'average', renderer: Ext.util.Format.numberRenderer("0.00")},
               {header : '供应商',dataIndex: 'supplier', width: 180, renderer: supplierRenderer},
//               {header : '是否结算',dataIndex: 'settlement', width: 80, renderer: function(v){return v == 1 ? '结算':'未结算';}},
//               {header : '结算时间',dataIndex: 'settdate', width: 120},
//               {header : '采购时间',dataIndex: 'purchdate', width: 120},
               {header : '创建人',dataIndex: 'creator', width: 180, hidden:true},
               {header : '创建时间',dataIndex: 'createTime', width: 180}
     	];
    
	
	var inStockCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	
	var inStockTbar = Ext.create('Ext.toolbar.Toolbar', {
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
        },supplierCombo,{
        	xtype: 'textfield',
        	id: 'goodsName',
        	emptyText: '商品名称或编码',
        	width: 160,
        },{
        	xtype: 'button',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		stockStore.load({
        			params : getParams()
        		});
        	}
        },{
        	xtype: 'button',
        	id: 'return',
        	text: '退货',
        	iconCls: 'arrow_undo', 
        	hidden : !parent.haveActionMenu('Return'),
        	handler: function(){
        		var record = inStockModel.getSelection()[0];
        		if(Ext.isEmpty(record)) {
        			Ext.MessageBox.show({
        				title: '提示',
        				msg: '你没有选中任何项目！',
        				buttons: Ext.MessageBox.OK,
        				icon: Ext.MessageBox.INFO
        		    });
        			return;
        		}
        		Ext.Msg.confirm('请确认', '确定要将选中商品退给供应商吗?', function(btn, text) {
        			if(btn == 'yes'){
        				var jsonArray = [], errorArray = [];
        				
                        Ext.each(inStockGrid.getSelectionModel().getSelection(), function(item){
                        	if(item.data.number > item.data.stockNum){
                        		errorArray.push(item.data);
                        	}
                    		if(!Ext.isEmpty(item.data.goodsId) && Number(item.data.number) > 0){
                        		jsonArray.push(item.data);
                        	}
                        });
                        if(!Ext.isEmpty(errorArray)){
                        	Ext.MessageBox.show({
 					           title: '提示',
 					           msg: "退货数不能大于库存数！",
 					           buttons: Ext.MessageBox.OK,
 					           icon: Ext.MessageBox.ERROR
 			                });
                        	return;
                        }
                        if(Ext.isEmpty(jsonArray)){
                        	Ext.MessageBox.show({
 					           title: '提示',
 					           msg: "请选择正确的入库记录！",
 					           buttons: Ext.MessageBox.OK,
 					           icon: Ext.MessageBox.ERROR
 			                });
                        	return;
                        }
                        Ext.MessageBox.show({
        					title: '请稍等',
        					msg: '正在处理 ...',
        					width: 200,
        					closable: false
        		        });
                        Ext.Ajax.request({
                            method:'POST',
                            url: '/goods/returnGoodsInStock.atc',
                            params : {
        						jsonStr : Ext.encode(jsonArray)
        					},
        					success : function(resp, opts) {
        						Ext.MessageBox.hide();
        						var result = Ext.decode(resp.responseText);
        						if(result.success){
        							//Ext.example.msg('提示', result.msg);
        							Ext.MessageBox.show({
        					            title: '结果',
        					            msg: result.msg  + "",
        					            buttons: Ext.MessageBox.YESNO,
        					            closable: false,
        					            buttonText:{
        					                yes: "打印退货单", 
        					                no: "继续操作" 
        					            },
        					            fn: function(btn) {
        					            	if(btn == 'yes'){
        					            		stockStore.reload();
        					            		//打印订单
        					            		parent.global.openOutStockPrintTab(Ext.encode(jsonArray));
        					        		} else if(btn == 'no'){
        					        			stockStore.reload();
        					        		}
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
        						Ext.MessageBox.hide();
        						var result = Ext.decode(resp.responseText);
                                Ext.Msg.alert('提示', result.msg);
        					}
                        });
        			}
        		});
            }
        },'->',{
        	xtype: 'button',
        	id: 'import',
        	text: '批量导入',
        	hidden : !parent.haveActionMenu('Import'),
        	iconCls: 'page_excel', 
        	handler: function(){
        		importWindow.show();
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
		stockStore.pageSize = parseInt(comboBox.getValue());
		var params = getParams();
		params.start = 0;
		params.limit = bbar.pageSize;
		stockStore.reload({
			params : params
		});
	});
	
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : stockStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesizeCombo ]
	});
	
	var inStockModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1
	});
	
	var inStockGrid = new Ext.grid.GridPanel({
		title: '商品退货',
		columns : stockColumns,
		store : stockStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : inStockTbar, 
		bbar: bbar,
		selModel : inStockModel,
		stripeRows:true,
//		forceFit: true,
		plugins: [inStockCellEditing],
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
	    }
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [inStockGrid]
	});
	
	stockStore.on('beforeload', function() {
		Ext.apply(stockStore.proxy.extraParams, getParams());
	});
	
	stockStore.load({
		params:{
			start : 0,
			limit : bbar.pageSize
		}
	});
	
	function getParams(){
		return {
			'inStock.goodsName': Ext.getCmp("goodsName").getValue(),
			'inStock.supplier': supplierCombo.getValue(),
			'inStock.startDate': parent.dateFormat(Ext.getCmp("startDate").getValue()),
			'inStock.endDate': parent.dateFormat(Ext.getCmp("endDate").getValue()),
			'inStock.stockNum': 1.0
		};
	}
	
});
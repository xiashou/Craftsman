Ext.onReady(function() {
	
	var stockRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var stockStore = Ext.create('Ext.data.Store', {
		pageSize: 20,
		  fields: ['inNumber', 'goodsId', 'goodsName', 'number', 'inPrice', 'tPrice', 'supplier', 'settlement', 'settdate', 'purchdate', 'creator', 'createTime'],
		  proxy: {
		      type: 'ajax',
			  url : '/goods/queryGoodsInStockListPage.atc',
			  reader : {
			      root: 'inStockList',
			      totalProperty: 'totalCount'
			  }
		  }
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
	
	supplierStore.load();
	
	var typeCombo = Ext.create('Ext.form.ComboBox', {
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 0, '全部' ], [ 1, '只看入库' ], [ 2, '只看退货' ]]
		}),
        enforceMaxLength: true,
        emptyText: '全部',
        width: 100,
        valueField: 'value',
        displayField: 'text',
        queryMode: 'local',
        editable : false
	});
	
	typeCombo.on("select", function(comboBox) {
		if(comboBox.getValue() == 1){
			stockStore.clearFilter();
			stockStore.filter("number", /^\d+$/);
		} else if(comboBox.getValue() == 2) {
			stockStore.clearFilter();
			stockStore.filter("number", /^-[0-9]*[1-9][0-9]*$/);
		} else {
			stockStore.clearFilter();
		}
	});
	
	var settlementCombo = Ext.create('Ext.form.ComboBox', {
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 1, '已结算' ], [ 0, '未结算' ]]
		}),
        enforceMaxLength: true,
        emptyText: '是否结算',
        width: 100,
        valueField: 'value',
        displayField: 'text',
        queryMode: 'local',
        editable : true
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
    
//    var tPriceRenderer = function(value, cellmeta, record) {
//    	record.data.tPrice = Number(Number(record.data.number) * Number(record.data.inPrice)).toFixed(2);
//        return record.data.tPrice;
//    };
    
	var stockColumns= [stockRowNum,
            {header : 'ID',dataIndex: 'goodsId', width: 60, hidden: true},
            {header : '入库单号',dataIndex: 'inNumber', width: 160},
            {header : '商品',dataIndex: 'goodsName', width: 260},
            {header : '数量',dataIndex: 'number', width: 100, summaryType: 'sum', renderer: Ext.util.Format.numberRenderer("0.00")},
            {header : '进货单价',dataIndex: 'inPrice', width: 100,summaryType: 'average', renderer: Ext.util.Format.numberRenderer("0.00")},
            {header : '总价',dataIndex: 'tPrice', width: 100,renderer: Ext.util.Format.numberRenderer("0.00"),
            	summaryType: function(records){
                    var i = 0, length = records.length, total = 0, record;
                    for (; i < length; ++i) {
                        record = records[i];
                        total += Number(record.get('inPrice')) * Number(record.get('number'));
                    }
                    return total.toFixed(2);
                }
            },
            {header : '供应商',dataIndex: 'supplier', width: 180, renderer: supplierRenderer},
//            {header : '是否结算',dataIndex: 'settlement', width: 80, renderer: function(v){return v == 1 ? '结算':'未结算';}},
//            {header : '结算时间',dataIndex: 'settdate', width: 120},
//            {header : '采购时间',dataIndex: 'purchdate', width: 120},
            {header : '创建人',dataIndex: 'creator', width: 80, hidden:true},
            {header : '创建时间',dataIndex: 'createTime', width: 160}
  	];
	
	var stockTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [typeCombo, {
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
        },supplierCombo, {
        	xtype: 'textfield',
        	id: 'inNumber',
        	emptyText: '入库单号',
        	width: 140,
        },{
        	xtype: 'textfield',
        	id: 'goodsName',
        	emptyText: '商品',
        	width: 140,
        },{
        	xtype: 'button',
        	id: 'delete',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		stockStore.load({
        			params : getParams()
        		});
            }
        },'->',{
        	xtype: 'button',
        	text:'打印入库单',
        	iconCls: 'printer',
        	handler: function() {
        		Ext.MessageBox.prompt('输入', '请输入要打印的入库单号:', function (btn, text){
        			if(btn == 'ok'){
        				parent.global.openInStockPrintTab(text);
        			}
        	    });
        	}
        },{
        	xtype: 'button',
        	text:'导出Excel',
        	iconCls : 'page_white_excel',
        	handler: function() {
        		stockGrid.downloadExcelXml();
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
	
	var stockGrid = new Ext.grid.GridPanel({
		title: '入库记录',
		columns : stockColumns,
		store : stockStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : stockTbar, 
		bbar : bbar,
		stripeRows:true,
//		forceFit: true,
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
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [stockGrid]
	});
	
	stockStore.on('load', function(){
		stockStore.each(function(record){
			record.data.tPrice = Number(Number(record.data.number) * Number(record.data.inPrice)).toFixed(2);
			record.commit();
		});
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
			'inStock.supplier': isNaN(supplierCombo.getValue())?0:supplierCombo.getValue(),
			'inStock.inNumber': Ext.getCmp("inNumber").getValue(),
//			'inStock.settlement': settlementCombo.getValue(),
			'inStock.goodsName': Ext.getCmp("goodsName").getValue(),
			'inStock.startDate': parent.dateFormat(Ext.getCmp("startDate").getValue()),
			'inStock.endDate': parent.dateFormat(Ext.getCmp("endDate").getValue())
		};
	}
	
});
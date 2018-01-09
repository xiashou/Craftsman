/**
 * 退货记录
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
		fields: ['goodsId', 'goodsName', 'number', 'stockNum', 'inPrice', 'tPrice', 'supplier', 'purchdate', 'creator', 'createTime'],
		proxy: {
			type: 'ajax',
			url : '/goods/queryReturnGoodsInStockListPage.atc',
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
               {header : '商品',dataIndex: 'goodsName', width: 200},
               {header : '退货数量',dataIndex: 'number', width: 120, summaryType: function(records){return '总计'}},
               {header : '进货单价',dataIndex: 'inPrice', width: 120,renderer: Ext.util.Format.numberRenderer("0.00"),
            	   summaryType: function(records){
                       var i = 0, length = records.length, total = 0, record;
                       for (; i < length; ++i) {
                           record = records[i];
                           if(record.data.goodsId)
                        	   total += Number(record.get('inPrice')) * Number(record.get('number'));
                       }
                       return total.toFixed(2);
                   }},
               {header : '供应商',dataIndex: 'supplier', width: 160, renderer: supplierRenderer},
               {header : '采购时间',dataIndex: 'purchdate', width: 120},
               {header : '退货人',dataIndex: 'creator', width: 80, hidden:true},
               {header : '退货时间',dataIndex: 'createTime', width: 180}
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
        	emptyText: '商品',
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
			'inStock.endDate': parent.dateFormat(Ext.getCmp("endDate").getValue())
		};
	}
	
});
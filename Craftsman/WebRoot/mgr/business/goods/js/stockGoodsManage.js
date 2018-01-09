Ext.onReady(function() {
	
	var stockRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var goodsTypeStore = Ext.create('Ext.data.Store', {
		autoLoad: true,
		fields: ['id', 'typeName'],
		proxy: {
		    type: 'ajax',
		    url : '/goods/queryMaterialTypeByDeptCode.atc',
			reader : {
			    root: 'materialTypeList'
			}
		}
	});
	
	var goodsTypeCombo = new Ext.form.ComboBox({
		id: 'goodsType',
		triggerAction : 'all',
		mode : 'local',
		store: goodsTypeStore,
		emptyText: '商品类型',
		valueField : 'id',
		displayField : 'typeName',
		editable : true,
		width: 150
	});
	
	var stockStore = Ext.create('Ext.data.Store', {
		  pageSize: 20,
		  fields: ['goodsId', 'deptCode', 'code', 'name', 'typeId', 'typeName', 'number', 'price', 
		           'inPrice', 'spec', 'color', 'size', 'unit', 'suitModel'],
		  proxy: {
		      type: 'ajax',
			  url : '/goods/queryGoodsStockByDeptPage.atc',
			  reader : {
			      root: 'stockList',
			      totalProperty: 'totalCount'
			  }
		  }
	});
	
	var stockColumns= [stockRowNum,
            {header : 'ID',dataIndex: 'goodsId', width: 60, hidden: true},
            {header : '编码',dataIndex: 'code', width: 100},
            {header : '名称',dataIndex: 'name', width: 150},
            {header : '类型',dataIndex: 'typeName', width: 150},
            {header : '数量',dataIndex: 'number', width: 60},
            {header : '价格',dataIndex: 'price', width: 80, xtype: 'numbercolumn', format:'0.00'},
            {header : '进货价',dataIndex: 'inPrice', width: 80, xtype: 'numbercolumn', format:'0.00'},
            {header : '规格',dataIndex: 'spec', width: 80},
            {header : '颜色',dataIndex: 'color', width: 80},
            {header : '尺寸',dataIndex: 'size', width: 80},
            {header : '单位',dataIndex: 'unit', width: 60},
            {header : '适用车型',dataIndex: 'suitModel', width: 150}
  	];
	
	var stockTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'textfield',
        	id: 'code',
        	emptyText : '编码',
        },{
        	xtype: 'textfield',
        	id: 'name',
        	emptyText : '名称',
        },goodsTypeCombo,{
        	xtype: 'button',
        	id: 'delete',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		if(!Ext.isEmpty(goodsTypeCombo.getValue()) && isNaN(goodsTypeCombo.getValue())){
        			Ext.MessageBox.show({
        	        	title: '提示',
        	        	msg: "请选择正确的商品类型！",
        	        	buttons: Ext.MessageBox.OK,
        	        	icon: Ext.MessageBox.INFO
        			});
        		} else {
        			stockStore.load({
            			params : getParams()
            		});
        		}
            }
        },'->',{
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
		title: '库存查询',
		columns : stockColumns,
		store : stockStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : stockTbar, 
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
		viewConfig : {
			enableTextSelection:true,	//设置单元格可以选择
			getRowClass:changeRowClass
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		listeners:{
	        itemclick: function(grid, record, item, index, e) {
	        	
	        }
	    }
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [stockGrid]
	});
	
	stockStore.on('beforeload', function() {
		Ext.apply(stockStore.proxy.extraParams, getParams());
	});
	
	stockStore.load();
	
	function getParams(){
		return {
			'stock.code': Ext.getCmp("code").getValue(),
			'stock.name': Ext.getCmp("name").getValue(),
			'stock.typeId': goodsTypeCombo.getValue()
		};
	}
	
	function changeRowClass(record, rowIndex, rowParams, store){
		if (record.get("number") <= 1)
			return 'x-grid-font-red';
	}
	
});
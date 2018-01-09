Ext.onReady(function() {
	
	var moveRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var moveStore = Ext.create('Ext.data.Store', {
		pageSize : 20,
		fields: ['id', 'deptOut', 'deptIn', 'total', 'status', 'deptOutName', 'deptInName', 'goodsId', 'goodsName', 'number', 'inPrice', 'creator', 'createTime', 'updator', 'updateTime'],
		proxy: {
            type: 'ajax',
            url : '/goods/queryGoodsMovePage.atc',
            reader : {
                root: 'moveHeadList',
                totalProperty: 'totalCount'
            }
        }
    });
	
	var statusRenderer = function(value, cellmeta, record) {
        return value == 1 ? "已收货" : "未收货";
    };
	
	var moveColumns= [moveRowNum,
            {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
            {header : '调出门店',dataIndex: 'deptOutName', width: 200},
            {header : '调入门店',dataIndex: 'deptInName', width: 200},
            {header : '总数量',dataIndex: 'total', width: 80, xtype: 'numbercolumn', format:'0.00'},
            {header : '状态',dataIndex: 'status', width: 100, renderer: statusRenderer},
            {header : '商品',dataIndex: 'goodsName', width: 200},
            {header : '数量',dataIndex: 'number', width: 120, xtype: 'numbercolumn', format:'0.00'},
            {header : '进货价',dataIndex: 'inPrice', width: 120, xtype: 'numbercolumn', format:'0.00'},
            {header : '创建人',dataIndex: 'creator', width: 100},
            {header : '创建时间',dataIndex: 'createTime', width: 200},
            {header : '收货人',dataIndex: 'updator', width: 100},
            {header : '收货时间',dataIndex: 'updateTime', width: 200}
  	];
	

	var deptStore = Ext.create('Ext.data.Store', {
		autoLoad : true,
		fields : [ 'deptCode', 'deptName' ],
		proxy : {
			type : 'ajax',
			url : '/sys/queryAllSysDeptByCompany.atc',
			reader : {
				root : 'deptList'
			}
		}
	});
	
	var shopInCombo = Ext.create('Ext.form.ComboBox', {
		id: 'shopInCombo',
		width: 150,
		store: deptStore,
        enforceMaxLength: true,
        emptyText: '调入门店',
        listConfig: {
            minWidth: null
        },
        valueField: 'deptCode',
        displayField: 'deptName',
        queryMode: 'local',
        forceSelection: true
	});
	
	var shopOutCombo = Ext.create('Ext.form.ComboBox', {
		id: 'shopOutCombo',
		width: 150,
		store: deptStore,
        enforceMaxLength: true,
        emptyText: '调出门店',
        listConfig: {
            minWidth: null
        },
        valueField: 'deptCode',
        displayField: 'deptName',
        queryMode: 'local',
        forceSelection: true
	});
	
	var moveTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [shopOutCombo, shopInCombo, {
        	xtype: 'textfield',
        	id: 'goodsName',
        	emptyText: '商品名称或编码'
        },{
        	xtype: 'button',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		moveStore.load({
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
		moveStore.pageSize = parseInt(comboBox.getValue());
		var params = getParams();
		params.start = 0;
		params.limit = bbar.pageSize;
		moveStore.reload({
			params : params
		});
	});
	
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : moveStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesizeCombo ]
	});
	
	var moveGrid = new Ext.grid.GridPanel({
		title: '商品调拨查询',
		columns : moveColumns,
		store : moveStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : moveTbar,
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
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
	    }
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [moveGrid]
	});
	
	moveStore.load({
		params:{
			start : 0,
			limit : bbar.pageSize
		}
	});
	
	function getParams(){
		return {
			'moveHead.deptOut': shopOutCombo.getValue(),
			'moveHead.deptIn': shopInCombo.getValue(),
			'moveHead.goodsName': Ext.getCmp("goodsName").getValue()
		};
	}
	
});
Ext.onReady(function() {
	Ext.QuickTips.init();
	var memberRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var store = Ext.create('Ext.data.Store', {
		pageSize: 20,
		fields: ['memId', 'bookTime', 'phone', 'carNumber', 'service', 'status', 'createTime'],
		proxy: {
			type: 'ajax',
			url : '/member/queryBookByDept.atc',
			reader : {
			    root: 'bookList',
			    totalProperty: 'totalCount'
			}
		}
	});
	
	var statusRenerer = function(value, cellmeta, record) {
        if(value == 1)
        	return "待处理";
        else if(value == 2)
        	return "已到店";
        else if(value == 3)
        	return "已取消";
    };
    
    var serRenderer = function(value, metaData, record) {
		if(value)
  			metaData.tdAttr = "data-qtip='" + value + "'"; 
		return value;
    };
	
	var columns= [memberRowNum,
            {header : 'ID',dataIndex: 'id', hidden: true},
            {header : 'memId',dataIndex: 'memId', hidden: true},
            {header : '电话号码',dataIndex: 'phone', width: 140},
            {header : '车牌号码',dataIndex: 'carNumber', width: 120},
            {header : '预约时间',dataIndex: 'bookTime', width: 160},
            {header : '预约服务',dataIndex: 'service', width: 360, tooltip:"", renderer: serRenderer},
            {header : '状态',dataIndex: 'status', width: 80, renderer: statusRenerer},
            {header : '到店',xtype: 'actioncolumn',menuDisabled: true, sortable: false,width: 60, align: 'center',
            	items: [{
            		iconCls: 'tick',
            		tooltip: '车辆已到店',
            		scope: this,
                    handler: function(grid, rowIndex){
                    	var record = store.getAt(rowIndex);
                    	execBook(record.data.id, 2);
                    }
            	}]
            },
            {header : '取消',xtype: 'actioncolumn',menuDisabled: true, sortable: false,width: 60, align: 'center',
            	items: [{
            		iconCls: 'cross',
            		tooltip: '取消预约',
            		scope: this,
                    handler: function(grid, rowIndex){
                    	var record = store.getAt(rowIndex);
                    	execBook(record.data.id, 3);
                    }
            	}]
            }
//            {header : '创建时间',dataIndex: 'createTime', width: 160}
  	];
	
	
	var memberTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'textfield',
        	id: 'mobile',
        	width: 140,
        	emptyText : '手机号码',
        },{
        	xtype: 'textfield',
        	id: 'carNumber',
        	width: 140,
        	emptyText : '车牌',
        },{
        	xtype: 'button',
        	id: 'search',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		store.load();
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
		var params = getParams();
		params.start = 0;
		params.limit = bbar.pageSize;
		store.reload({
			params : params
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
	
	var memberGrid = new Ext.grid.GridPanel({
		title: '预约列表',
		columns : columns,
		store : store, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : memberTbar, 
		bbar : bbar,
		stripeRows:true,
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
		items : [memberGrid]
	});
	
	store.on('beforeload', function() {
		Ext.apply(store.proxy.extraParams, getParams());
	});
	
	store.load();
	
	function getParams(){
		return {
			'book.phone': Ext.getCmp("mobile").getValue(),
			'book.carNumber': Ext.getCmp("carNumber").getValue()
		};
	}
	
	function execBook(id, status) {
		Ext.Ajax.request({
			url : '/member/updateBookById.atc',
			params : {
				'book.id': id,
				'book.status': status
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result.success){
					Ext.example.msg('提示', result.msg + "");
					store.reload();
				}
			},
			failure : function(resp, opts) {
                Ext.Msg.alert('提示', "出错！");
			}
		});
	}
});
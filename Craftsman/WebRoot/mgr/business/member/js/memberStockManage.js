Ext.onReady(function() {
	
	var memberRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var store = Ext.create('Ext.data.Store', {
		pageSize: 20,
		fields: ['memId', 'goodsId', 'goodsName', 'goodsType', 'typeId', 'number', 'name', 'sex', 'mobile', 'endDate', 'source'],
		proxy: {
			type: 'ajax',
			url : '/member/queryMemberStockByDept.atc',
			reader : {
			    root: 'msList',
			    totalProperty: 'totalCount'
			}
		}
	});
	
	var goodsTypeRenderer = function(value, cellmeta, record) {
		var itemType = parent.GOrderItemTypeStore.findRecord("value", value);
		if(itemType)
			return itemType.data.text;
		else
			return value;
    };
	
	var memberColumns= [memberRowNum,
            {header : 'ID',dataIndex: 'memId', hidden: true},
            {header : 'source',dataIndex: 'source', hidden: true},
            {header : '姓名',dataIndex: 'name', width: 150},
            {header : '电话号码',dataIndex: 'mobile', width: 150},
            {header : '性别',dataIndex: 'sex', width: 80, renderer: function(v){return (v == 1) ? '先生': (v==2 ? '女士' : '未知');}},
            {header : '类型',dataIndex: 'goodsType', renderer: goodsTypeRenderer},
            {header : '项目/商品',dataIndex: 'goodsName', width: 260},
            {header : '数量',dataIndex: 'number', field: {xtype: 'numberfield'}},
            {header : '到期日期',dataIndex: 'endDate'}
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
        valueField: 'memId',
        displayField: 'mobile',
        typeAhead: false,
        hideTrigger:true,
        width: 290,
        queryParam: 'keyword',	//搜索参数字段
        minChars: 2,
        emptyText: '输入车牌号码或手机号码后4位',
        pageSize: 10,
        listConfig: {
            loadingText: '查找中...',
            emptyText: '<div style="padding: 5px;">没有找到相关车主！</div>',
            getInnerTpl: function() {
                return '<div>{fullCarNumber}/{name}/{mobile}</div>';
            }
		}
	});
	
	var memberTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [MemberCombo, {
//        	xtype: 'textfield',
//        	id: 'vipNo',
//        	emptyText : '会员卡号',
//        },{
//        	xtype: 'textfield',
//        	id: 'name',
//        	emptyText : '姓名',
//        },{
//        	xtype: 'textfield',
//        	id: 'mobile',
//        	emptyText : '手机号码',
//        },{
//        	xtype: 'textfield',
//        	id: 'grade',
//        	emptyText : '等级',
//        },{
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
	
	var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
		disabled: true,
		saveBtnText: '保存',
        cancelBtnText: '取消',
        listeners: {
        	edit: function(rowEditing, context) {
        		if(!Ext.isEmpty(context.record)){
        			Ext.Ajax.request({
						url : '/member/modifyMemberStock.atc',
						params : {
							'memberStock.memId': context.record.data.memId,
							'memberStock.goodsId': context.record.data.goodsId,
							'memberStock.endDate': context.record.data.endDate,
							'memberStock.source': context.record.data.source,
							'memberStock.goodsName': context.record.data.goodsName,
							'memberStock.goodsType': context.record.data.goodsType,
							'memberStock.number': context.record.data.number,
							'memberStock.typeId': context.record.data.typeId
						},
						success : function(resp, opts) {
							var result = Ext.decode(resp.responseText);
							if(result.success){
								Ext.example.msg('提示', result.msg + "");
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
            }
        }
    });
	
	var memberGrid = new Ext.grid.GridPanel({
		title: '会员库存',
		columns : memberColumns,
		store : store, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : memberTbar, 
		bbar : bbar,
		stripeRows:true,
		plugins: [rowEditing],
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
			memId: MemberCombo.getValue()
		};
	}
	
});
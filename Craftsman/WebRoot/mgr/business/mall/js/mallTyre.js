/**
 * 轮胎配置
 */
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var brandId, seriesId;
	
	var brandRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var seriesRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var modelRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var typeRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});

	var goodsRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var tyreStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'modelId', 'goodsId'],
		proxy: {
			type: 'ajax',
			url : '/mall/queryMallTyreByModel.atc',
			reader : {
				root: 'tyreList'
			}
		}
	});
	
	var typeStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'typeName', 'sortNo'],
		proxy: {
			type: 'ajax',
			url : '/mall/queryMallGoodsTypeByAppId.atc',
			reader : {
				root: 'goodsTypeList'
			}
		}
	});

	var goodsStore = Ext.create('Ext.data.Store', {
        fields: ['goodsId', 'goodsName', 'pictures', 'typeId', 'brandId', 'oprice', 'aprice', 'number', 
                 'isHot', 'isPoint', 'sendMode', 'status', 'spec', 'details', 'creator', 'createTime'],
        proxy: {
            type: 'ajax',
            url : '/mall/queryMallGoodsByPage.atc',
            reader : {
                root: 'goodsList',
                totalProperty: 'totalCount'
            }
        }
	});
	
	var brandStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'shortCode', 'brandName', 'logo'],
		proxy: {
			type: 'ajax',
			url : '/base/queryAllCarBrand.atc',
			reader : {
				root: 'carBrandList',
				totalProperty: 'totalCount'
			}
		}
    });
	
	var seriesStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'brandId', 'seriesName'],
        proxy: {
            type: 'ajax',
            url : '/base/queryCarSeriesByBrandId.atc',
            reader : {
                root: 'carSeriesList',
                totalProperty: 'totalCount'
            }
        }
	});
	
	var modelStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'seriesId', 'modelName'],
        proxy: {
            type: 'ajax',
            url : '/base/queryCarModelBySeriesId.atc',
            reader : {
                root: 'carModelList',
                totalProperty: 'totalCount'
            }
        }
	});
	
	var logoRender = function(value, metaData, record) {
		if(value){
  			metaData.tdAttr = "data-qtip=\"<img src='/upload/brand/" + value + "' style='width:100px'/>\""; 
  			return '<img src="/upload/brand/' + value + '" style="height:30px" onerror="this.src=\'/resources/images/noImage.jpg\'" />';
  		} else
  			return '<img src="/resources/images/noImage.jpg" style="height:30px" />';
    };
  	
  	var brandColumns= [brandRowNum,
    	{header : 'ID',dataIndex: 'id', width: 60, hidden: true},
    	{header : 'Logo',dataIndex: 'logo', width: 42, renderer: logoRender},
    	{header : '品牌名称',dataIndex: 'brandName', width: 130,editor: {xtype: 'textfield'},
    		items : {
    			xtype: 'textfield', flex : 1, margin: 2, enableKeyEvents: true,
    			listeners: {
    				keyup: function() {
    					brandStore.clearFilter();
    					if (this.value) {
    						brandStore.filter({
    							property      : 'shortCode',
    							value         : this.value,
    							anyMatch      : true,
    							caseSensitive : false
    						});
    					}
    				},
    				buffer: 500
    			}
    		}}
  	];
  	
  	var seriesColumns= [seriesRowNum,
    	{header : 'ID',dataIndex: 'id', width: 60, hidden: true},
    	{header : '系列名称',dataIndex: 'seriesName', width: 150,editor: {xtype: 'textfield'},
    		items : {
    			xtype: 'textfield', flex : 1, margin: 2, enableKeyEvents: true,
    			listeners: {
    				keyup: function() {
    					seriesStore.clearFilter();
    					if (this.value) {
    						seriesStore.filter({
    							property      : 'seriesName',
    							value         : this.value,
    							anyMatch      : true,
    							caseSensitive : false
    						});
    					}
    				},
    				buffer: 500
    			}
    		}}
  	];
  	
  	var modelColumns= [modelRowNum,
    	{header : 'ID',dataIndex: 'id', width: 60, hidden: true},
    	{header : '型号名称',dataIndex: 'modelName', width: 150, editor: {xtype: 'textfield'}, 
    		items : {
    			xtype: 'textfield', flex : 1, margin: 2, enableKeyEvents: true,
    			listeners: {
    				keyup: function() {
    					modelStore.clearFilter();
    					if (this.value) {
    						modelStore.filter({
    							property      : 'modelName',
    							value         : this.value,
    							anyMatch      : true,
    							caseSensitive : false
    						});
    					}
    				},
    				buffer: 500
    			}
    		}}
  	];
  	
  	var goodsBrandStore = Ext.create('Ext.data.Store', {
		fields : [ 'id', 'brandName'],
		proxy : {
			type : 'ajax',
			url : '/mall/queryMallBrandByPage.atc',
			reader : {
				root : 'brandList'
			}
		}
	});
	goodsBrandStore.load({
		params:{
			start : 0,
			limit : 100
		}
	});
	
  	var typeColumns= [typeRowNum,
        {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
        {header : '类型名称',dataIndex: 'typeName', width: 150},
        {header : '排序',dataIndex: 'sortNo', width: 60}
  	];
  	
  	var statusRenderer = function(value) {
  		if(value == 0)
  			return '<font color="red">已下架</font>';
  		else if(value == 1)
  			return '<font color="green">已上架</font>';
  		else
  			return '';
  	}
  	var brandRenderer = function(value) {
  		var brand = goodsBrandStore.findRecord("id", value);
		if(brand) 
			return brand.data.brandName;
		else
			return value;
  	}
  	
  	var goodsColumns= [goodsRowNum,
  	        {header : 'ID',dataIndex: 'goodsId', hidden: true},
            {header : '商品名称',dataIndex: 'goodsName', width: 160},
            {header : '类型',dataIndex: 'typeId', hidden: true},
            {header : '品牌',dataIndex: 'brandId', width: 100, renderer: brandRenderer},
//            {header : '标签价',dataIndex: 'oprice', width: 100, xtype: 'numbercolumn', format:'0.00'},
//            {header : '零售价',dataIndex: 'aprice', width: 100, xtype: 'numbercolumn', format:'0.00'},
            {header : '商城库存',dataIndex: 'number', width: 80, xtype: 'numbercolumn', format:'0.00'},
            {header : '热销商品',dataIndex: 'isHot', width: 60, renderer: function(v){return v == 1?'是':'否';}},
            {header : '状态',dataIndex: 'status', width: 60, renderer: statusRenderer},
  	];
	
//	var brandSelModel = Ext.create('Ext.selection.CheckboxModel', {
//		injectCheckbox: 1,
//		mode: 'SINGLE'
//	});
//	
//	var seriesSelModel = Ext.create('Ext.selection.CheckboxModel', {
//		injectCheckbox: 1,
//		mode: 'SINGLE'
//	});
	
	var modelSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1
	});
	
//	var typeSelModel = Ext.create('Ext.selection.CheckboxModel', {
//		injectCheckbox: 1,
//		mode: 'SINGLE'
//	});
	
	var goodsSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1
//		mode: 'SINGLE'
	});
	
	var typeGrid = new Ext.grid.GridPanel({
		columns : typeColumns,
		store : typeStore, 
		autoScroll : true,
		columnLines : true,
//		selModel : typeSelModel,
//		tbar : typeTbar, 
		stripeRows:true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		listeners:{
	        itemclick: function(grid, record, item, index, e) {
	        	typeId = record.data.id;
				goodsStore.load({
					params: {
						'goods.typeId': record.data.id
					}
				});
	        }
	    }
	});
	
	var goodsTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	id: 'upGoods',
        	text: '关联',
        	iconCls: 'tick',
//        	hidden : !parent.haveActionMenu('EditMaterial'),
        	handler: function() {
        		saveRelation();
            }
        }]
    });
	
	var goodsPagesizeCombo = new Ext.form.ComboBox({
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

	var goodsNumber = parseInt(goodsPagesizeCombo.getValue());
	
	goodsPagesizeCombo.on("select", function(comboBox) {
		goodsBbar.pageSize = parseInt(comboBox.getValue());
		goodsNumber = parseInt(comboBox.getValue());
		goodsStore.pageSize = parseInt(comboBox.getValue());
		var params = getParams();
		params.start = 0;
		params.limit = goodsBbar.pageSize;
		goodsStore.reload({
			params : params
		});
	});
	
	var goodsBbar = new Ext.PagingToolbar({
		pageSize : goodsNumber,
		store : goodsStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', goodsPagesizeCombo ]
	});
	
	var goodsGrid = new Ext.grid.GridPanel({
		columns : goodsColumns,
		store : goodsStore, 
		autoScroll : true,
		columnLines : true,
		selModel : goodsSelModel,
		tbar : goodsTbar, 
		bbar : goodsBbar,
		stripeRows:true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
	});
	
	var brandGrid = new Ext.grid.GridPanel({
		columns : brandColumns,
		store : brandStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
//		selModel : brandSelModel,
		stripeRows:true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		listeners:{
	        itemclick: function(grid, record, item, index, e) {
				seriesStore.load({
					params: {
						brandId : record.raw.id
					}
				});
				brandId = record.raw.id;
	        }
	    }
	});
	
	var seriesGrid = new Ext.grid.GridPanel({
		columns : seriesColumns,
		store : seriesStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
//		selModel : seriesSelModel,
		stripeRows:true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		listeners:{
			itemclick: function(grid, record, item, index, e) {
				modelStore.load({
					params: {
						'carModel.seriesId' : record.raw.id
					}
				});
				seriesId = record.raw.id;
	        }
	    }
	});
	
	var modelGrid = new Ext.grid.GridPanel({
		columns : modelColumns,
		store : modelStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : modelSelModel,
//		tbar : modelTbar, 
		stripeRows:true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		listeners:{
			itemclick: function(grid, record, item, index, e) {
				tyreStore.load({
					params: {
						modelId : record.data.id
					}
				});
				if(!Ext.isEmpty(typeId))
					goodsStore.reload();
	        }
	    }
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			title : '品牌',
			iconCls : 'car',
			tools : [{
				type:'refresh',
				handler : function() {
					brandStore.reload();
				}
			}],
//			collapsible : true,
			width : '15%',
			split : true,
			region : 'west',
			layout : 'fit',
			border : false,
			items : [ brandGrid ]
		}, {
			title : '系列',
			iconCls : 'car',
			tools : [{
				type:'refresh',
				handler : function() {
					seriesStore.reload();
				}
			}],
			region : 'west',
			width : '15%',
			split : true,
			layout : 'fit',
			border : false,
			items : [ seriesGrid ]
		}, {
			title : '型号',
			iconCls : 'car',
			tools : [{
				type:'refresh',
				handler : function() {
					modelStore.reload();
				}
			}],
			region : 'center',
			width : '20%',
			split : true,
			layout : 'fit',
			border : false,
			items : [ modelGrid ]
		}, {
			title : '商品类型',
			iconCls : 'tag_blue',
			tools : [{
				type:'refresh',
				handler : function() {
					typeStore.reload();
				}
			}],
			region : 'east',
			width : '15%',
			split : true,
			layout : 'fit',
			border : false,
			items : [ typeGrid ]
		}, {
			title : '商品',
			iconCls : 'bricks',
			tools : [{
				type:'refresh',
				handler : function() {
					goodsStore.reload();
				}
			}],
			region : 'east',
			width : '35%',
			split : true,
			layout : 'fit',
			border : false,
			items : [ goodsGrid ]
		}]
	});
	
	brandStore.load();
	typeStore.load();
	
	goodsStore.on("load",function(){
		var selectArray = [];
		goodsStore.each(function(goods){
			var select = tyreStore.findRecord('goodsId', goods.data.goodsId);
			if(select)
				selectArray.push(goods);
		});
		goodsSelModel.select(selectArray);  
    }); 
	
	function saveRelation() {
		var modelSelect = modelSelModel.getSelection();
		var goodsSelect = goodsSelModel.getSelection();
        if(Ext.isEmpty(goodsSelect)) {
			Ext.MessageBox.show({
				title: '提示',
				msg: '你没有选中任何商品！',
				buttons: Ext.MessageBox.OK,
				icon: Ext.MessageBox.INFO
		    });
			return;
		}
        if(Ext.isEmpty(modelSelect)) {
			Ext.MessageBox.show({
				title: '提示',
				msg: '你没有选中任何车型！',
				buttons: Ext.MessageBox.OK,
				icon: Ext.MessageBox.INFO
		    });
			return;
		}
        var mIds = new Array();
        var gIds = new Array();
        Ext.each(goodsSelect, function(item){
        	if(!Ext.isEmpty(item.data.goodsId)){
        		gIds.push(item.data.goodsId);
        	}
        });  
        Ext.each(modelSelect, function(item){
        	if(!Ext.isEmpty(item.data.id)){
        		mIds.push(item.data.id);
        	}
        });  
        Ext.MessageBox.show({
			title: '请稍等',
			msg: '正在处理 ...',
			width: 200,
			progress: true,
			closable: false
        });
        Ext.Ajax.request({
            method:'POST',
            url: '/mall/saveMallTyreRelation.atc',
            params : {
            	goodsIds : gIds,
            	modelIds : mIds
			},
			success : function(resp, opts) {
				Ext.MessageBox.hide();
				var result = Ext.decode(resp.responseText);
				if(result.success){
					Ext.example.msg('提示', result.msg + "");
					goodsStore.reload();
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
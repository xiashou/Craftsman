Ext.onReady(function() {
	
	Ext.QuickTips.init();
	var typeId;
	
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
	
	var typeForm = new Ext.form.FormPanel({
		id : 'typeForm',
		layout : 'anchor',
		defaults : {
			anchor : '100%',
			layout : 'hbox',
			xtype : 'fieldcontainer'
		},
		bodyPadding : '5 10 0 0',
		border : false,
		items : [ {
			defaults : {
				flex : 1,
				xtype : 'textfield',
				labelWidth : 70,
				labelAlign : 'right'
			},
			items : [ {
				id : 'typeName',
				name : 'goodsType.typeName',
				allowBlank : false,
				fieldLabel : '名称',
				blankText : '品牌名称不能为空!'
			},{
				id : 'id',
				name : 'goodsType.id',
				xtype: 'hiddenfield'
			} ]
		},{
			defaults : {
				flex : 1,
				xtype : 'numberfield',
				labelWidth : 70,
				labelAlign : 'right'
			},
			items : [ {
				id : 'sortNo',
				name : 'goodsType.sortNo',
				fieldLabel : '排序'
			} ]
		} ],
		buttons : [ {
			text : '保 存',
			iconCls : 'accept',
			handler : function() {
				if (typeForm.form.isValid()) {
					typeForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							typeWindow.hide();
							Ext.example.msg('提示', action.result.msg);
							typeStore.reload();
						},
						failure : function(form, action) {
							var msg = action.result.msg;
							Ext.MessageBox.alert('提示', msg);
						}
					});
				}
			}
		}, {
			text : '关 闭 ',
			iconCls : 'stop',
			handler : function() {
				typeWindow.hide();
			}
		} ]
	});

	var typeWindow = new Ext.Window({
		title : '修改',
		layout : 'fit',
		width : 300,
		height : 150,
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
		items : [ typeForm ]
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
  		var brand = brandStore.findRecord("id", value);
		if(brand) 
			return brand.data.brandName;
		else
			return value;
  	}
  	var sendModeRenderer = function(value) {
  		if(!Ext.isEmpty(value) && value.indexOf(",") > 0){
  			var array = value.split(",");
  			var result = '';
  			Ext.each(array, function(item){
  				var sendMode = sendModeStore.findRecord("id", item.trim());
  	  			if(sendMode)
  	  			result += sendMode.data.modeName + ",";
  			});
  			return result;
  		} else {
  			var sendMode = sendModeStore.findRecord("id", value);
  			if(sendMode) 
  				return sendMode.data.modeName;
  			else
  				return value;
  		}
  	}
  	
  	var goodsColumns= [goodsRowNum,
  	        {header : 'ID',dataIndex: 'goodsId', hidden: true},
            {header : '编辑',xtype: 'actioncolumn',menuDisabled: true, sortable: false,width: 40, align: 'center',
            	items: [{
            		iconCls: 'pencil',
            		tooltip: '编辑商品详情',
            		scope: this,
                    handler: function(grid, rowIndex){
                    	var record = goodsStore.getAt(rowIndex);
                    	goodsForm.getForm().reset();
                    	goodsForm.getForm().loadRecord(record);
                    	Ext.getCmp("isHot").setValue({'goods.isHot' : record.data.isHot});
                    	Ext.getCmp("isPoint").setValue({'goods.isPoint' : record.data.isPoint});
                    	goodsForm.down('#sendMode').removeAll() ;   
                		Ext.each(sendModeStore.data.items, function(mode){
                			if(!Ext.isEmpty(record.data.sendMode) && record.data.sendMode.indexOf(mode.data.id) >= 0)
	                			goodsForm.down('#sendMode').add([
	                          	   {boxLabel: mode.data.modeName, inputValue: mode.data.id, checked: true}
	                          	]);
                			else
                				goodsForm.down('#sendMode').add([
	                          	   {boxLabel: mode.data.modeName, inputValue: mode.data.id}
	                          	]);
                        }); 
                    	goodsWindow.show();
                    }
            	}]
            },
            {header : '商品名称',dataIndex: 'goodsName', width: 160},
            {header : '类型',dataIndex: 'typeId', hidden: true},
            {header : '品牌',dataIndex: 'brandId', width: 100, renderer: brandRenderer},
            {header : '标签价',dataIndex: 'oprice', width: 100, xtype: 'numbercolumn', format:'0.00'},
            {header : '零售价',dataIndex: 'aprice', width: 100, xtype: 'numbercolumn', format:'0.00'},
            {header : '商城库存',dataIndex: 'number', width: 80, xtype: 'numbercolumn', format:'0.00'},
            {header : '热销商品',dataIndex: 'isHot', width: 60, renderer: function(v){return v == 1?'是':'否';}},
            {header : '积分抵现',dataIndex: 'isPoint', width: 60, renderer: function(v){return v == 1?'是':'否';}},
            {header : '配送方式',dataIndex: 'sendMode', width: 100, renderer: sendModeRenderer},
            {header : '状态',dataIndex: 'status', width: 60, renderer: statusRenderer},
            {header : '规格',dataIndex: 'spec', hidden: true},
            {header : '详情',dataIndex: 'details', hidden: true},
            {header : '图片',dataIndex: 'pictures', hidden: true},
            {header : '创建人',dataIndex: 'creator', width: 80, hidden: true},
            {header : '创建时间',dataIndex: 'createTime', width: 120}
  	];
  	
  	var typeTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '新建',
        	iconCls: 'add',
//        	hidden : !parent.haveActionMenu('AddMaterial'),
        	handler: function() {
        		typeWindow.setTitle('新增类型');
        		typeForm.getForm().reset();
        		typeForm.getForm().url = '/mall/insertMallGoodsType.atc';
        		typeWindow.show();
            }
        },{
        	xtype: 'button',
        	text: '修改',
        	iconCls: 'pencil',
//        	hidden : !parent.haveActionMenu('EditMaterial'),
        	handler: function() {
        		initTypeEdit();
            }
        },{
        	xtype: 'button',
        	text: '删除',
        	iconCls: 'delete', 
//        	disabled: true,
//        	hidden : !parent.haveActionMenu('DelMaterial'),
        	handler: function(){
        		initTypeDelete();
            }
        }]
    });
  	
	var goodsTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	id: 'materialInsert',
        	text: '选择商品',
        	iconCls: 'add',
//        	hidden : !parent.haveActionMenu('AddMaterial'),
        	handler: function() {
        		if(!Ext.isEmpty(typeId)) 
        			choiceGoodsWindow.show();
        		else
        			Ext.MessageBox.show({
			           title: '提示',
			           msg: '请先选择商品的类型！',
			           buttons: Ext.MessageBox.OK,
			           icon: Ext.MessageBox.INFO
                    });
            }
        },{
        	xtype: 'button',
        	id: 'upGoods',
        	text: '上架',
        	iconCls: 'tick',
//        	hidden : !parent.haveActionMenu('EditMaterial'),
        	handler: function() {
        		handleGoods(1);
            }
        },{
        	xtype: 'button',
        	id: 'downGoods',
        	text: '下架',
        	iconCls: 'cross', 
//        	hidden : !parent.haveActionMenu('DelMaterial'),
        	handler: function(){
        		handleGoods(0);
            }
//        },{
//        	xtype: 'button',
//        	id: 'search',
//        	text: ' 查 询 ',
//        	iconCls: 'preview', 
//        	handler: function(){
//        		if(!Ext.isEmpty(typeId)){
//        			materialStore.load({
//						params: {
//							'typeId' : typeId,
//							'keyword' : Ext.getCmp("keyword").getValue()
//						}
//					});
//        		} else {
//        			Ext.MessageBox.show({
//        	        	title: '提示',
//        	        	msg: "请先选择商品类型！",
//        	        	buttons: Ext.MessageBox.OK,
//        	        	icon: Ext.MessageBox.INFO
//        			});
//        		}
//            }
        }]
    });
	
	var typeSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var goodsSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1
	});
	
	var typeGrid = new Ext.grid.GridPanel({
		columns : typeColumns,
		store : typeStore, 
		autoScroll : true,
		columnLines : true,
		selModel : typeSelModel,
		tbar : typeTbar, 
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
				goodsStore.reload();
	        }
	    }
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
	
	var sendModeStore = Ext.create('Ext.data.Store', {
		fields : [ 'id', 'modeName'],
		proxy : {
			type : 'ajax',
			url : '/mall/queryMallSendModeByPage.atc',
			reader : {
				root : 'sendModeList'
			}
		}
	});
	sendModeStore.load();
	
	var brandStore = Ext.create('Ext.data.Store', {
		fields : [ 'id', 'brandName'],
		proxy : {
			type : 'ajax',
			url : '/mall/queryMallBrandByPage.atc',
			reader : {
				root : 'brandList'
			}
		}
	});
	brandStore.load({
		params:{
			start : 0,
			limit : 100
		}
	});
	
	var brandCombo = Ext.create('Ext.form.ComboBox', {
		store: brandStore,
        enforceMaxLength: true,
        fieldLabel: '品牌',
        labelAlign:'right',
        labelWidth: 90,
        margin: '5 0 0 0',
        listConfig: {
            minWidth: null
        },
        id: 'brandId',
        name: 'goods.brandId',
        valueField: 'id',
        displayField: 'brandName',
        queryMode: 'local',
        editable : false,
        forceSelection: true
	});
	
	var typeCombo = Ext.create('Ext.form.ComboBox', {
		store: typeStore,
		allowBlank: false,
        enforceMaxLength: true,
        fieldLabel: '类型',
        labelAlign:'right',
        labelWidth: 90,
        margin: '5 0 0 0',
        listConfig: {
            minWidth: null
        },
        id: 'typeId',
        name: 'goods.typeId',
        valueField: 'id',
        displayField: 'typeName',
        queryMode: 'local',
        editable : false,
        forceSelection: true
	});
	
	var picId;
	
	Ext.define('Image', {
        extend: 'Ext.data.Model',
        fields: ['id', 'src']
    });
	
	var imgStore = new Ext.data.Store({
		model: Image
	});
	
	var contextMenu = Ext.create('Ext.menu.Menu',{
		items:[{
			iconCls: 'add',
			text: '添加',
			handler: function() {
				uploadWindow.show();
			}
		},{
			iconCls: 'delete',
			text: '删除',
			handler: function() {
				var picValue = Ext.getCmp("pictures").getValue();
				if(picValue.indexOf(picId+",") >= 0)
					picValue = picValue.replace(picId + ",", "");
				Ext.getCmp("pictures").setValue(picValue);
			}
		}]
	});
	
	var imgView = Ext.create('Ext.view.View', {
    	id: 'imageView1',
        store: imgStore,
        tpl: [
              '<tpl for=".">',
                  '<div class="thumb-wrap">',
                  '<div class="thumb">',
                  '<a href="{src}" data-lightbox="img-view" >',
                  '<img src="{src}" /></a></div>',
                  '</div>',
              '</tpl>'
        ],
        //一定要有一个高度，否则trackOver失效
        autoScroll: true,
        height: '144px',
        trackOver: true,
        overItemCls: 'x-item-over',
        itemSelector: 'div.thumb-wrap',
        emptyText: '<div style="padding:10px;width:100%;text-align:center">No picture！</div>',
        loadMask: false,
        listeners: {
        	itemcontextmenu: function(view, record, item, index, e) {
        		picId = record.data.id;
        		e.stopEvent();
        		contextMenu.showAt(e.getXY());
        		return false;
        	}
        }
	});
	
	var uploadForm = new Ext.form.FormPanel({
		id : 'uploadForm',
		padding: '5 5 5 5',
		frame : false,
		items : [{
			fieldLabel : '图片文件',
			labelWidth: 80,
			labelAlign: 'right',
			id : 'upload',
			name: 'upload',
			xtype: 'filefield',
			buttonText: '浏览...',
			anchor : '99%'
		}]
	});
	
	var uploadWindow = new Ext.Window({
		layout : 'fit',
		width : 380,
		height : 120,
		resizable : false,
		draggable : true,
		closeAction : 'hide',
		title : '上传图片',
		modal : false,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		items : [uploadForm],
		buttons : [{
			text : '上 传',
			iconCls : 'image',
			handler : function() {
				var upload = Ext.getCmp('upload').getValue();
				if (Ext.isEmpty(upload)) {
					Ext.MessageBox.show({
			           title: '提示',
			           msg: 'No file ...',
			           buttons: Ext.MessageBox.OK,
			           icon: Ext.MessageBox.INFO
					});
					return;
				}
				Ext.MessageBox.show({
					title: '请稍等',
					msg: 'uploading ...',
					width: 200,
					progress: true,
					closable: false
		        });
				uploadForm.form.submit({
					url : '/mall/uploadMallGoodsPictures.atc',
					waitTitle : '提示',
					method : 'POST',
					waitMsg : '正在处理数据,请稍候...',
					success : function(form, action) {
						Ext.MessageBox.hide();
						if(action.result.uploadFileName){
							Ext.getCmp("pictures").setValue(Ext.getCmp("pictures").getValue() + action.result.uploadFileName + ",");
							uploadWindow.hide();
							Ext.example.msg("提示", action.result.msg);
						}
					},
					failure : function(form, action) {
						Ext.MessageBox.hide();
						var msg = action.result.msg;
                        Ext.MessageBox.show({
 				           title: '提示',
 				           msg: msg,
 				           buttons: Ext.MessageBox.OK,
 				           icon: Ext.MessageBox.ERROR
                        });
					}
				});
			}
		}, {
			text : '关 闭',
			iconCls : 'stop',
			handler : function() {
				uploadWindow.hide();
			}
		}]
	});
	
	var goodsForm = new Ext.form.FormPanel({
		id : 'goodsForm',
		layout: 'hbox',
		items:[{
			flex: 1,
			height: '100%',
			xtype: 'container',
			items:[{
				xtype: 'fieldset',
				title: '基本信息',
				layout: 'anchor',
				height: '70%',
				margin: '5 0 0 5',
				defaults: {
					anchor: '100%',
					labelWidth : 90,
					labelAlign : 'right'
				},
				items : [ {
                	xtype: 'textfield',
                    id: 'goodsName',
                    name: 'goods.goodsName',
                    fieldLabel: '商品名称',
    				allowBlank : false,
    				blankText : '商品名称不能为空!'
                },{
                	xtype: 'hiddenfield',
                	id: 'goodsId',
                	name: 'goods.goodsId'
                },{
                	xtype: 'hiddenfield',
                	id: 'status',
                	name: 'goods.status'
                },{
                	layout: 'hbox',
                	defaults : {
        				flex : 1,
        				xtype : 'textfield',
        				labelWidth : 90,
        				labelAlign : 'right'
        			},
        			items : [brandCombo, typeCombo]
                },{
                	layout: 'hbox',
                	defaults : {
                		flex : 1,
                		xtype : 'numberfield',
                		labelWidth : 90,
                		allowDecimals: true,
                		decimalPrecision: 2,
                		minValue: '0',
                		margin: '5 0 0 0',
                		labelAlign : 'right'
                	},
                	items : [ {
                		id: 'oprice',
                		name: 'goods.oprice',
                		fieldLabel: '标签价',
                	},{
                		id: 'aprice',
                		name: 'goods.aprice',
                		fieldLabel: '销售价',
                		allowBlank: false,
                		blankText : '销售价不能为空!'
                	} ]
                },{
                	layout: 'hbox',
                	defaults : {
                		flex : 1,
                		xtype : 'numberfield',
                		labelWidth : 90,
                		allowDecimals: true,
                		decimalPrecision: 2,
                		minValue: '0',
                		margin: '5 0 0 0',
                		labelAlign : 'right'
                	},
                	items : [ {
                		id: 'number',
                		name: 'goods.number',
                		fieldLabel: '商城库存',
                	},{
                		xtype : 'displayfield'
                	}]
                },{
                	xtype: 'checkboxgroup',
                    fieldLabel: '支持配送方式',
                    itemId: 'sendMode',
                    margin: '5 0 0 0',
                    defaults : {
                    	name : 'goods.sendMode'
                	}
                },{
                	layout: 'hbox',
                	defaults : {
                		flex : 1,
                		xtype : 'radiogroup',
                		labelWidth : 90,
                		allowDecimals: true,
                		decimalPrecision: 2,
                		minValue: '0',
                		margin: '5 0 0 0',
                		labelAlign : 'right'
                	},
                	items : [ {
                		id: 'isPoint',
                		defaults: {name: 'goods.isPoint'},
                		fieldLabel: '是否支持积分',
                        items: [
                            {boxLabel: '是', inputValue: 1, checked: true},
                            {boxLabel: '否', inputValue: 0},
                        ]
                	},{
                		id: 'isHot',
                		defaults: {name: 'goods.isHot'},
                		fieldLabel: '热销商品',
                		items: [
                            {boxLabel: '是', inputValue: '1'},
                            {boxLabel: '否', inputValue: '0', checked: true},
                        ]
                	} ]
                },{
                	layout: 'hbox',
                	defaults : {
                		flex : 1,
                		xtype : 'textfield',
                		labelWidth : 90,
                		margin: '5 0 0 0',
                		readOnly: true,
                		labelAlign : 'right'
                	},
                	items : [ {
                		id: 'creator',
                		name: 'goods.creator',
                		fieldLabel: '创建人',
                	},{
                		id: 'createTime',
                		name: 'goods.createTime',
                		fieldLabel: '创建时间',
                	} ]
                },{
                	layout: 'hbox',
                	defaults : {
                		flex : 1,
                		xtype : 'textarea',
                		labelWidth : 90,
                		rows: 8,
                		margin: '5 0 0 0',
                		labelAlign : 'right'
                	},
                	items : [ {
                		id: 'spec',
                		name: 'goods.spec',
                		fieldLabel: '规格',
                	} ]
                }]
			},{
				xtype: 'fieldset',
				title: '预览图片',
				height: '28.5%',
				margin: '5 0 0 5',
    			itemId: 'picPanel',
    			collapsible: false,
    			defaults: {
    			    labelWidth: 89,
    			    anchor: '100%',
    			    layout: {
    			        type: 'hbox',
    			        defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
    			    }
    			},
    			items:[Ext.create('Ext.toolbar.Toolbar', {
  		    	   items: [{
 		    		   xtype: 'button',
 		    		   text:'添加',
 		    		   handler: function() {
 		    			   uploadWindow.show();
 		    		   }
 		    	   }]
    			}),imgView,{
    				xtype: 'hiddenfield',
    				id: 'pictures',
    				name: 'goods.pictures',
    				listeners: {
    					change: function(field, newValue, oldValue) {
    						if(!Ext.isEmpty(newValue)) {
    							imgStore.removeAll();
    							newValue = newValue.substring(0, newValue.length - 1);
    							if(newValue.indexOf(",") > 0){
    								var pics = newValue.split(",");
    								Ext.each(pics, function(pic){
    									imgStore.add({id: pic, src:'/upload/mall/goods/' + pic});
    								});
    							} else
    								imgStore.add({id: newValue, src:'/upload/mall/goods/' + newValue});
    								
    						}
    					}
    				}
    			}]
			}]
		}, {
          xtype: 'component',
          width: 10
		},{
			flex: 1,
			xtype: 'fieldset',
			title: '商品详情',
			height: '100%',
			margin: '5 5 0 0',
			items: [{
	        	xtype: 'htmleditor',
	        	height: document.body.scrollHeight - 150,
	        	id: 'details',
	            name: 'goods.details',
	            anchor: '100%',
	            enableAlignments: true,
	            enableColors: true,
	            enableFont: true,
	            enableFontSize: true,
	            enableFormat: true,
	            enableLinks: true,
	            enableLists: true,
	            fontFamilies : ['宋体','黑体','隶书','微软雅黑','Arial','Courier New','Tahoma','Verdana'],
	            plugins: Ext.create('Ext.ux.custom.EditorGoodsImagePlugin')
			}]
		}]
	});
	
	var goodsWindow = new Ext.Window({
		title : '编辑商品',
		layout : 'fit',
		width : '80%',
		height : '95%',
		resizable : false,
		draggable : true,
		closeAction : 'hide',
		modal : false,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'center',
		border : false,
		animCollapse : true,
		constrain : true,
		items : [ goodsForm ],
		buttons : [ {
			text : '保 存',
			iconCls : 'accept',
			handler : function() {
				if (goodsForm.form.isValid()) {
					goodsForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						url : '/mall/updateMallGoods.atc',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							goodsWindow.hide();
							Ext.example.msg('提示', action.result.msg);
							goodsStore.reload();
						},
						failure : function(form, action) {
							var msg = action.result.msg;
							Ext.MessageBox.alert('提示', msg);
						}
					});
				}
			}
		}, {
			text : '关 闭 ',
			iconCls : 'stop',
			handler : function() {
				goodsWindow.hide();
			}
		} ]
	});
	
	//页面初始布局
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			title : '类型',
			iconCls : 'tag_blue',
			tools : [{
				type:'refresh',
				handler : function() {
					typeStore.reload();
				}
			}],
			collapsible : true,
			width : '15%',
			split : true,
			region : 'west',
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
			},{
	            type:'help',
	            tooltip: '操作指引',
	            callback: function(panel, tool, event) {
	            	parent.global.openSystemHelpTab();
	            }
	        }],
			region : 'center',
			split : true,
			layout : 'fit',
			border : false,
			items : [ goodsGrid ]
		}]
	});
	
	goodsStore.on('beforeload', function() {
		Ext.apply(goodsStore.proxy.extraParams, getParams());
	});
	
	function getParams(){
		return {
			'goods.typeId' : typeId
		};
	}
	
	/////////////////////////////////////////////////////////选择商品
	
	var storeRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var storeStore = Ext.create('Ext.data.Store', {
		pageSize: 20,
		fields: ['goodsId', 'deptCode', 'code', 'name', 'number', 'typeName', 'price', 'inPrice', 'spec', 'color', 'size', 'unit', 'suitModel'],
		proxy: {
			type: 'ajax',
			url : '/goods/queryGoodsStockByDeptPage.atc',
			reader : {
				root: 'stockList',
				totalProperty: 'totalCount'
			}
		}
	});
	
	var storeColumns= [storeRowNum,
       {header : 'ID',dataIndex: 'goodsId', width: 60, hidden: true},
       {header : '编码',dataIndex: 'code', width: 100},
       {header : '名称',dataIndex: 'name', width: 150},
       {header : '类型',dataIndex: 'typeName', width: 150},
       {header : '数量',dataIndex: 'number', width: 60},
       {header : '价格',dataIndex: 'price', width: 80, xtype: 'numbercolumn', format:'0.00'},
       {header : '进货价',dataIndex: 'inPrice', width: 80, xtype: 'numbercolumn', format:'0.00'},
       {header : '规格',dataIndex: 'spec', width: 80, hidden: true},
       {header : '颜色',dataIndex: 'color', width: 80, hidden: true},
       {header : '尺寸',dataIndex: 'size', width: 80, hidden: true},
       {header : '单位',dataIndex: 'unit', width: 60},
       {header : '适用车型',dataIndex: 'suitModel', width: 150}
	];
	
	var storeTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '选择',
        	iconCls: 'accept',
        	handler: function() {
        		choiceStoreGoods();
            }
        },{
        	xtype: 'textfield',
        	id: 'storeName',
        	emptyText : '名称',
        },{
        	xtype: 'button',
        	id: 'storeSearch',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		storeStore.load({
        			params : storeGetParams()
        		});
            }
        }]
	});
	
	var storeSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1
	});
	
	var storePagesizeCombo = new Ext.form.ComboBox({
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

	var storeNumber = parseInt(storePagesizeCombo.getValue());
	
	// 改变每页显示条数reload数据
	storePagesizeCombo.on("select", function(comboBox) {
		storeBbar.pageSize = parseInt(comboBox.getValue());
		storeNumber = parseInt(comboBox.getValue());
		storeStore.pageSize = parseInt(comboBox.getValue());
		var params = storeGetParams();
		params.start = 0;
		params.limit = storeBbar.pageSize;
		storeStore.reload({
			params : params
		});
	});
	
	var storeBbar = new Ext.PagingToolbar({
		pageSize : storeNumber,
		store : storeStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', storePagesizeCombo ]
	});
	
	var storeGrid = new Ext.grid.GridPanel({
		title: ' 库 存 ',
		region : 'center',
		columns : storeColumns,
		store : storeStore, 
		autoScroll : true,
		columnLines : true,
		frame : false,
		selModel : storeSelModel,
		tbar : storeTbar, 
		bbar : storeBbar,
		stripeRows:true,
		forceFit: true,
		viewConfig : {
			enableTextSelection: true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	storeStore.on("load",function(){
		var selectArray = [];
		storeStore.each(function(store){
			var select = goodsStore.findRecord('goodsId', store.data.goodsId);
			if(select)
				selectArray.push(store);
		});
		storeSelModel.select(selectArray);  
    }); 
	
	storeStore.on('beforeload', function() {
		Ext.apply(storeStore.proxy.extraParams, storeGetParams());
	});
	
	function storeGetParams(){
		return {
			'stock.name': Ext.getCmp("storeName").getValue()
		};
	}
	
	/////////////////////项目商品
	
	var hourTypeRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var hourRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var hourTypeStore = Ext.create('Ext.data.Store', {
		  fields: ['id', 'typeName', 'sortNo'],
		  proxy: {
		      type: 'ajax',
			  url : '/goods/queryAllHourType.atc',
			  reader : {
			      root: 'hourTypeList',
			      totalProperty: 'totalCount'
			  }
		  }
	});
	
	var hourStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'typeId', 'name', 'price'],
        proxy: {
            type: 'ajax',
            url : '/goods/queryGoodsHourByType.atc',
            reader : {
                root: 'hourList',
                totalProperty: 'totalCount'
            }
        }
	});
	
	var hourTypeColumns= [hourTypeRowNum,
  		{header : 'ID',dataIndex: 'id', width: 60, hidden: true},
  		{header : '类型名称',dataIndex: 'typeName', width: 120},
  		{header : '排序',dataIndex: 'sortNo', width: 80}
  	];
	
	var hourColumns= [hourRowNum,
    	{header : 'ID',dataIndex: 'id', hidden: true},
    	{header : '类型',dataIndex: 'typeId', width: 100, renderer: function(v){return hourTypeStore.findRecord("id", v).data.typeName;}},
    	{header : '商品名称',dataIndex: 'name', width: 160},
    	{header : '价格',dataIndex: 'price', width: 100}
	];
	
	var hourTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '选择',
        	iconCls: 'accept',
        	handler: function() {
        		choiceHourGoods();
            }
        }]
	});
	
	var hourSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1
	});
	
	var hourTypeGrid = new Ext.grid.GridPanel({
		columns : hourTypeColumns,
		store : hourTypeStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
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
				hourStore.load({
					params: {
						'typeId' : record.data.id
					}
				});
	        }
	    }
	});
	
	var hourGrid = new Ext.grid.GridPanel({
		region : 'center',
		columns : hourColumns,
		store : hourStore, 
		autoScroll : true,
		columnLines : true,
		frame : false,
		selModel : hourSelModel,
		tbar : hourTbar, 
		stripeRows:true,
		forceFit: true,
		viewConfig : {
			enableTextSelection: true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var hourPanel = new Ext.panel.Panel({
		title: ' 项 目 ',
		layout : 'border',
		items : [{
			title : '类型',
			iconCls : 'tag_blue',
			tools : [{
				type:'refresh',
				handler : function() {
					hourTypeStore.reload();
				}
			}],
			collapsible : true,
			width : '30%',
			split : true,
			region : 'west',
			layout : 'fit',
			border : false,
			items : [ hourTypeGrid ]
		}, {
			title : '商品',
			iconCls : 'bricks',
			region : 'center',
			width : '70%',
			split : true,
			layout : 'fit',
			border : false,
			items : [ hourGrid ]
		}],
		listeners: {
            activate: function(tab) {
            	hourTypeStore.load();
            }
        }
	});
	
	hourStore.on("load",function(){
		var selectArray = [];
		hourStore.each(function(hour){
			var select = goodsStore.findRecord('goodsId', hour.data.id);
			if(select)
				selectArray.push(hour);
		});
		hourSelModel.select(selectArray);  
    }); 
	
	/////////////套餐商品
	var packageRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var packageStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'name','price', 'range', 'expire', 'startDate','endDate','explain','status','createTime'],
		proxy: {
			type: 'ajax',
			url : '/goods/queryAllPackageGoods.atc',
			reader : {
				root: 'packageList',
				totalProperty: 'totalCount'
			}
		}
	});
	
	var rangeRenderer = function(value) {
		if(value == 1)
			return '仅本店';
		else if(value == 2)
			return '连锁通用';
		else
			return '';
	}
	
	var pgStatusRenderer = function(value) {
		if(value == 1)
			return '启用';
		else if(value == 0)
			return '禁用';
		else
			return '';
	}
	
	var packageColumns= [packageRowNum,
    	{header : 'ID',dataIndex: 'id', width: 60, hidden: true},
    	{header : '套餐名称',dataIndex: 'name', width: 120},
    	{header : '价格',dataIndex: 'price', width: 80},
    	{header : '适用范围',dataIndex: 'range', width: 80, renderer: rangeRenderer},
    	{header : '有效天数',dataIndex: 'expire', width: 80},
    	{header : '状态',dataIndex: 'status', width: 80, renderer: pgStatusRenderer},
    	{header : '创建日期',dataIndex: 'createTime', width: 120}
  	];
	
	var packageTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '选择',
        	iconCls: 'accept',
        	handler: function() {
        		choicePackageGoods();
            }
        }]
    });
	
	var packageSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1
	});
	
	var packageGrid = new Ext.grid.GridPanel({
		title: ' 套 餐 ',
		columns : packageColumns,
		store : packageStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : packageSelModel,
		tbar : packageTbar, 
		stripeRows:true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		listeners:{
			activate: function(tab) {
				packageStore.load();
            }
	    }
	});
	
	packageStore.on("load",function(){
		var selectArray = [];
		packageStore.each(function(pack){
			var select = goodsStore.findRecord('goodsId', pack.data.id);
			if(select)
				selectArray.push(pack);
		});
		packageSelModel.select(selectArray);  
    }); 
	
	var goodsTab = Ext.create('Ext.TabPanel', {
		region : 'center',
        activeTab: 0,
        plain: true,
        deferredRender : false,
        defaults :{
            autoScroll: true
        },
        items: [storeGrid, hourPanel, packageGrid]
    });
	
	var choiceGoodsWindow = new Ext.Window({
		title : '选择商品',
		layout : 'border',
		width : 930,
		height : document.body.clientHeight - 100,
		resizable : true,
		draggable : true,
		closeAction : 'hide',
		iconCls : 'application_side_tree',
		modal : true,
		collapsible : true,
		maximizable : true,
		constrain : true,
		border : false,
		items: [goodsTab]
	});
	
	choiceGoodsWindow.on('show', function() {
		storeStore.load({
			params : {
				start : 0,
				limit : storeBbar.pageSize
			}
		});
	});
	
	typeStore.load();
	
	//选择库存商品
	function choiceStoreGoods(){
		var storeSelect = storeSelModel.getSelection();
		var jsonArray = [];
        var eMsg = "";
        Ext.each(storeSelect, function(item){
        	if(!Ext.isEmpty(item.data.goodsId)){
        		jsonArray.push(item.data);
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
            url: '/mall/insertMoreStockMallGoods.atc',
            params : {
            	typeId: typeId,
				jsonStr : Ext.encode(jsonArray)
			},
			success : function(resp, opts) {
				Ext.MessageBox.hide();
				var result = Ext.decode(resp.responseText);
				if(result.success){
					if(Ext.isArray(result.goodsList)){
						choiceGoodsWindow.hide();
						goodsStore.reload();
					}
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
	
	//选择工时商品
	function choiceHourGoods(){
		var hourSelect = hourSelModel.getSelection();
		var jsonArray = [];
        var eMsg = "";
        Ext.each(hourSelect, function(item){
        	if(!Ext.isEmpty(item.data.id)){
        		jsonArray.push(item.data);
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
            url: '/mall/insertMoreHourMallGoods.atc',
            params : {
            	typeId: typeId,
				jsonStr : Ext.encode(jsonArray)
			},
			success : function(resp, opts) {
				Ext.MessageBox.hide();
				var result = Ext.decode(resp.responseText);
				if(result.success){
					if(Ext.isArray(result.goodsList)){
						choiceGoodsWindow.hide();
						goodsStore.reload();
					}
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
	
	//选择套餐商品
	function choicePackageGoods(){
		var packageSelect = packageSelModel.getSelection();
		var jsonArray = [];
        var eMsg = "";
        Ext.each(packageSelect, function(item){
        	if(!Ext.isEmpty(item.data.id)){
        		jsonArray.push(item.data);
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
            url: '/mall/insertMorePackageMallGoods.atc',
            params : {
            	typeId: typeId,
				jsonStr : Ext.encode(jsonArray)
			},
			success : function(resp, opts) {
				Ext.MessageBox.hide();
				var result = Ext.decode(resp.responseText);
				if(result.success){
					if(Ext.isArray(result.goodsList)){
						choiceGoodsWindow.hide();
						goodsStore.reload();
					}
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
	
	
	//上架或下架商品
	function handleGoods(status){
		var goodsSelect = goodsSelModel.getSelection();
		var jsonArray = [];
        var eMsg = "";
        Ext.each(goodsSelect, function(item){
        	if(!Ext.isEmpty(item.data.goodsId)){
        		jsonArray.push(item.data);
        	}
        });  
        Ext.Msg.confirm('请确认', '确定要' + (status == 1 ? '上架' : '下架') + '这' + jsonArray.length + '个商品吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.MessageBox.show({
					title: '请稍等',
					msg: '正在处理 ...',
					width: 200,
					progress: true,
					closable: false
		        });
		        Ext.Ajax.request({
		            method:'POST',
		            url: '/mall/handleMoreMallGoods.atc',
		            params : {
		            	status : status,
						jsonStr : Ext.encode(jsonArray)
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
	}
	
	function initTypeEdit() {
		var record = typeSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '你没有选中任何项目！',
	           buttons: Ext.MessageBox.OK,
	           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		typeWindow.hide();
		typeWindow.setTitle('修改类型');
		typeForm.getForm().reset();
		typeForm.loadRecord(record);
		typeForm.getForm().url = '/mall/updateMallGoodsType.atc';
		typeWindow.show();
		
	}
	
	function initTypeDelete() {
		var record = typeSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '你没有选中任何项目！',
	           buttons: Ext.MessageBox.OK,
	           icon: Ext.MessageBox.INFO
		    });
			return;
		} 
		Ext.Msg.confirm('请确认', '确定要删除这个类型吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/mall/deleteMallGoodsType.atc',
					params : {
						'goodsType.id' : record.data.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							typeWindow.hide();
							Ext.example.msg('提示', result.msg);
							typeSelModel.deselectAll();
							typeStore.reload();
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
                        Ext.MessageBox.show({
 				           title: '提示',
 				           msg: result.msg,
 				           buttons: Ext.MessageBox.OK,
 				           icon: Ext.MessageBox.ERROR
                        });
					}
				});
			}
		});
	}
});
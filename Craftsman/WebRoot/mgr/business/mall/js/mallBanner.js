Ext.onReady(function() {

	Ext.QuickTips.init();

	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable : true,
		width : 40
	});
	
	var goodsStore = Ext.create('Ext.data.Store', {
		fields: ['goodsId', 'goodsName'],
		proxy: {
			type: 'ajax',
			url : '/mall/queryMallGoodsList.atc',
			reader : {
				root: 'goodsList'
			}
		}
	});
	goodsStore.load();
	
	var goodsCombo = Ext.create('Ext.form.ComboBox', {
		id: 'goodsCombo',
		name: 'banner.link',
		fieldLabel : '链接产品',
		labelWidth : 70,
		labelAlign : 'right',
		triggerAction : 'all',
		mode : 'local',
		store: goodsStore,
		valueField : 'goodsId',
		displayField : 'goodsName',
		editable : false
	});

	var mallBannerForm = new Ext.form.FormPanel({
		id : 'mallBannerForm',
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
				id : 'sort',
				xtype : 'numberfield',
				name : 'banner.sort',
				fieldLabel : '排序',
			},{
				id : 'id',
				name : 'banner.id',
				xtype: 'hiddenfield'
			}]
		},goodsCombo,{
			defaults : {
				flex : 1,
				xtype : 'filefield',
				labelWidth : 70,
				labelAlign : 'right'
			},
			items : [ {
				fieldLabel : '图片',
				id : 'upload',
				name: 'upload',
				buttonText: '浏览...',
				anchor : '99%'
			} ]
		},{
			defaults : {
				flex : 1,
				xtype : 'displayfield',
				labelWidth : 70
			},
			items: [ {
				fieldLabel : '&nbsp;',
				labelSeparator: '',
				value:'图片最佳尺寸640*300'
			} ]
		} ],
		buttons : [ {
			text : '保 存', // 按钮文本
			iconCls : 'accept', // 按钮图标
			handler : function() { // 按钮响应函数
				if (mallBannerForm.form.isValid()) {
					mallBannerForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							mallBannerWindow.hide();
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
		}, {
			text : '关 闭 ', // 按钮文本
			iconCls : 'stop', // 按钮图标
			handler : function() { // 按钮响应函数
				mallBannerWindow.hide();
			}
		} ]
	});

	var mallBannerWindow = new Ext.Window({
		title : '修改', // 窗口标题
		layout : 'fit',
		width : 400,
		height : 200,
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
		items : [ mallBannerForm ]
	});

	var store = Ext.create('Ext.data.Store', {
		pageSize : 20,
		fields : [ 'id', 'appId', 'picture', 'link', 'sort' ],
		proxy : {
			type : 'ajax',
			url : '/mall/queryMallBannerList.atc',
			reader : {
				root : 'bannerList'
			}
		}
	});
	
	var logoRenderer = function(value, metaData, record) {
		if(value){
  			metaData.tdAttr = "data-qtip=\"<img src='/upload/mall/banner/" + value + "' style='width:320px; height:150px'/>\""; 
  			return '<img src="/upload/mall/banner/' + value + '" style="height:60px" onerror="this.src=\'/resources/images/noImage.jpg\'" />';
  		} else
  			return '<img src="/resources/images/noImage.jpg" style="height:60px" />';
    };
    
    var goodsRenderer = function(value, cellmeta, record) {
        var index = goodsStore.find("goodsId", value);
        var record = goodsStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else
        	displayText = record.data.goodsName;
        return displayText;
    };

	var columns = [ rowNum, {
		header : 'ID',
		dataIndex : 'id',
		hidden : true
	}, {
		header : '图片',
		dataIndex : 'picture',
		width : 300,
		renderer: logoRenderer
	}, {
		header : '链接到产品',
		dataIndex : 'link',
		width : 200,
		renderer: goodsRenderer
	}, {
		header : '排序',
		dataIndex : 'sort',
		width : 80
	} ];

	var tbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
		items : [ {
			xtype : 'button',
			id : 'insert',
			text : '新建',
			iconCls : 'add',
			handler : function() {
				mallBannerWindow.setTitle('新建');
				mallBannerForm.getForm().reset();
				mallBannerForm.getForm().url = '/mall/insertMallBanner.atc';
				mallBannerWindow.show();
			}
		}, {
			xtype : 'button',
			id : 'update',
			text : '修改',
			iconCls : 'pencil',
			handler : function() {
				initEdit();
			}
		}, {
			xtype : 'button',
			id : 'delete',
			text : '删除',
			iconCls : 'delete',
			handler : function() {
				initDelete();
			}
		} ]
	});

	var pagesizeCombo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 200, '200条/页' ], [ 500, '500条/页' ],
					[ 1000000, '更多条' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});

	var selModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox : 1,
		width: 40, 
		mode : 'SINGLE'
	});

	var grid = new Ext.grid.GridPanel({
		title:'首页Banner管理',
		columns : columns,
		store : store,
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : selModel,
		frame : false,
		tbar : tbar,
		stripeRows : true,
		viewConfig : {
			enableTextSelection : true
		//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		listeners : {
			itemdblclick : function(grid, record, item, index, e) {
				if (parent.haveActionMenu('Edit'))
					initEdit();
			}
		}
	});

	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [ {
			region : 'center',
			layout : 'fit',
			border : false,
			items : [ grid ]
		} ]
	});

	store.load();

	function initEdit() {
		var record = selModel.getSelection()[0];
		if (Ext.isEmpty(record)) {
			Ext.MessageBox.show({
				title : '提示',
				msg : '你没有选中任何项目！',
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.INFO
			});
			return;
		}
		selModel.deselectAll();
		mallBannerWindow.setTitle('修改');
		mallBannerForm.loadRecord(record);
		mallBannerForm.getForm().url = '/mall/updateMallBanner.atc';
		mallBannerWindow.show();
	}

	function initDelete() {
		var record = selModel.getSelection()[0];
		if (Ext.isEmpty(record)) {
			Ext.MessageBox.show({
				title : '提示',
				msg : '你没有选中任何项目！',
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.INFO
			});
			return;
		}
		Ext.Msg.confirm('请确认', '确定要删除这个品牌吗?', function(btn, text) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : '/mall/deleteMallBanner.atc',
					params : {
						'banner.id' : record.data.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if (result.success) {
							mallBannerWindow.hide();
							Ext.example.msg('提示', result.msg);
							selModel.deselectAll();
							store.reload();
						} else
							Ext.MessageBox.show({
								title : '提示',
								msg : result.msg,
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.ERROR
							});
					},
					failure : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						Ext.MessageBox.show({
							title : '提示',
							msg : result.msg,
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.ERROR
						});
					}
				});
			}
		});
	}

});
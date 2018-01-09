Ext.onReady(function() {
	
	Ext.QuickTips.init();
	var selectedIds = new Array();
	var actId;
	
	var joinRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});

	var actRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var specStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'actId', 'name', 'price'],
        proxy: {
            type: 'ajax',
            url : '/wechat/act/queryActSpecListByActId.atc',
            reader : {
                root: 'specList'
            }
        }
	});
	
	var joinStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'actId', 'openId', 'nickName', 'headImg', 'spec', 'realName', 'orderNo', 'price', 
                 'number', 'status', 'mobile', 'sex', 'age', 'cardNo', 'carNumber'],
        proxy: {
            type: 'ajax',
            url : '/wechat/act/queryActJoinerPage.atc',
            reader : {
                root: 'joinerList',
                totalProperty: 'totalCount'
            }
        }
	});
	
	var actStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'appId', 'name'],
        proxy: {
            type: 'ajax',
            url : '/wechat/act/queryActSignupPage.atc',
            reader : {
                root: 'signupList'
            }
        }
	});
	
	var specRenderer = function(value, cellmeta, record) {
		if(!Ext.isEmpty(value)){
			if(value.indexOf(",") > 0){
				var names = "";
				var ids = value.split(",");
				for (index in ids){
					if(!Ext.isEmpty(ids[index].trim()))
						names += specStore.findRecord("id", ids[index].trim()).data.name + ',';
				}
				record.data.spec = names;
				return names;
			} else {
				var str = specStore.findRecord("id", value.trim()).data.name;
				record.data.spec = str;
				return str;
			}
		}
    };
    
    var statusRenderer = function(value) {
    	if(value == 1)
			return '<font color=green>已支付</font>';
		else 
			return '<font color=red>已取消</font>';
    };
    
    var sexRenderer = function(value) {
    	if(value == 1)
			return '男';
    	else if(value == 2)
    		return '女';
		else 
			return '未知';
    };
	
  	var joinColumns= [joinRowNum,
        {header : 'actId',dataIndex: 'actId', hidden: true},
        {header : 'openId',dataIndex: 'openId', hidden: true},
        {header : '编码',dataIndex: 'id', width: 60},
        {header : '昵称',dataIndex: 'nickName', width: 120},
        {header : '增值服务',dataIndex: 'spec', width: 120, renderer: specRenderer},
        {header : '交易单号',dataIndex: 'orderNo', width: 130},
        {header : '数量',dataIndex: 'number', width: 60},
        {header : '交易金额',dataIndex: 'price', width: 80},
        {header : '姓名',dataIndex: 'realName', width: 100},
        {header : '手机号码',dataIndex: 'mobile', width: 110},
        {header : '性别',dataIndex: 'sex', width: 60, renderer: sexRenderer},
        {header : '年龄',dataIndex: 'age', width: 80},
        {header : '身份证号',dataIndex: 'cardNo', width: 100},
        {header : '车牌号',dataIndex: 'carNumber', width: 80},
        {header : '状态',dataIndex: 'status', width: 80, renderer: statusRenderer}
  	];
  	
  	var actColumns= [actRowNum,
        {header : 'ID',dataIndex: 'id', hidden: true},
        {header : '活动名称',dataIndex: 'name', width: 160},
  	];
  	
  	var joinTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	id: 'mobile',
        	xtype: 'textfield',
        	emptyText: '手机号',
        	width: 180
//        },{
//        	xtype: 'button',
//        	text: '修改',
//        	iconCls: 'pencil',
//        	handler: function() {
//        		initSpecEdit();
//          }
        },{
        	xtype: 'button',
        	text: '查询',
        	iconCls: 'preview', 
        	handler: function(){
        		joinStore.load();
            }
        },{
        	xtype: 'button',
        	text: '短信群发',
        	iconCls: 'phone', 
        	handler: function(){
        		if(selectedIds == ''){
        			Ext.MessageBox.show({
        				title: '提示',
        				msg: '没有选中任何记录！',
        				buttons: Ext.MessageBox.OK,
        				icon: Ext.MessageBox.INFO
                    });
        		} else 
        			smsWindow.show();
        	}
        },'->',{
        	xtype: 'button',
        	text:'导出Excel',
        	iconCls : 'page_white_excel',
        	handler: function() {
        		joinGrid.downloadExcelXml();
        	}
        }]
    });
  	
  	var actPagesizeCombo = new Ext.form.ComboBox({
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

	var actNumber = parseInt(actPagesizeCombo.getValue());
	
	actPagesizeCombo.on("select", function(comboBox) {
		actBbar.pageSize = parseInt(comboBox.getValue());
		actNumber = parseInt(comboBox.getValue());
		joinStore.pageSize = parseInt(comboBox.getValue());
		var params = getParams();
		params.start = 0;
		params.limit = actBbar.pageSize;
		joinStore.reload({
			params : params
		});
	});
	
	var actBbar = new Ext.PagingToolbar({
		pageSize : actNumber,
		store : joinStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', actPagesizeCombo ]
	});
	
	var selModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		listeners : {
			selectionchange : function(sm, selections) {
				var rows = sm.getSelection();
				selectedIds = new Array();
        		for(var i = 0; i < rows.length; i++){
        			Ext.Array.clean(selectedIds);
        			if(!Ext.Array.contains(selectedIds,rows[i].data.mobile) && (/^1[34578]\d{9}$/.test(rows[i].data.mobile))){
        				selectedIds.push(rows[i].data.mobile);
        			}
        		}
			}
		}
	});
	
	var joinGrid = new Ext.grid.GridPanel({
		columns : joinColumns,
		store : joinStore, 
		autoScroll : true,
		columnLines : true,
		tbar : joinTbar, 
		bbar: actBbar,
		stripeRows:true,
		selModel : selModel,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var actGrid = new Ext.grid.GridPanel({
		columns : actColumns,
		store : actStore, 
		autoScroll : true,
		columnLines : true,
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
	        	actId = record.data.id;
//	        	specStore.load({
//	        		params:{
//	        			actId : actId
//	        		}
//	        	});
//	        	joinStore.load();
	        	specStore.load({
	        		params:{
	        			actId : actId
	        		},
	    			callback: function(records){
	    				joinStore.load();
	    	        }
	    		});
	        }
	    }
	});
	
	
	//页面初始布局
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			title : '活动',
			iconCls : 'bricks',
			tools : [{
				type:'refresh',
				handler : function() {
					actStore.reload();
				}
			}],
			region : 'west',
			width : '20%',
			split : true,
			layout : 'fit',
			border : false,
			items : [ actGrid ]
		}, {
			title : '参与者',
			iconCls : 'tag_blue',
			tools : [{
				type:'refresh',
				handler : function() {
					joinStore.reload();
				}
			},{
	            type:'help',
	            tooltip: '操作指引',
	            callback: function(panel, tool, event) {
	            	parent.global.openSystemHelpTab();
	            }
	        }],
			collapsible : true,
			split : true,
			region : 'center',
			layout : 'fit',
			border : false,
			items : [ joinGrid ]
		}]
	});
	
	var smsForm = new Ext.form.FormPanel({
		id : 'smsForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            xtype: 'fieldcontainer',
	        layout: 'hbox'
        },
		bodyPadding: '5 10 0 0',
		border : false,
		items : [{
				defaults: {flex: 1, xtype:'textarea', labelWidth:70, labelAlign:'right'},
				items: [
				        {name: 'message', rows: 4, fieldLabel: '短信内容',
				        	listeners: {
								change(textarea, newValue, oldValue) {
					            	Ext.getCmp("smsnumber").setValue("（共" + getByteLen(newValue) + "字），短信内容需带上店铺签名！");
					            }
				        	}
				        }
				]
		    },{
		    	defaults: {flex: 1, xtype:'displayfield', labelWidth:70, labelAlign:'right'},
		    	items: [
		    	        {id: 'smsnumber', fieldLabel: '短信字数', value: ''}
		    	]
			}
		],
		buttons:[{
			text : '发 送',
			iconCls : 'accept',
			handler : function() {
				smsForm.form.submit({
					waitTitle : '提示',
					method : 'POST',
					url: '/wechat/act/sendActJoinerSMS.atc',
					waitMsg : '正在处理数据,请稍候...',
					params : {
						mobileStr : selectedIds,
					},
					success : function(form, action) {
						smsForm.getForm().reset();
						smsWindow.hide();
						Ext.example.msg("提示", action.result.msg + "");
					},
					failure : function(form, action) {
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
		},{
			text : '关 闭 ', // 按钮文本
			iconCls : 'stop', // 按钮图标
			handler : function() { // 按钮响应函数
				smsWindow.hide();
			}
		}]
	});
	
	var smsWindow = new Ext.Window({
		title : '短信群发', 
		layout : 'fit',
		width : 550,
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
		items : [smsForm]
	});
	
	actStore.load();
	
	joinStore.on('beforeload', function() {
		Ext.apply(joinStore.proxy.extraParams, getParams());
	});
	
	function getParams(){
		return {
			'joiner.actId' : actId,
			'joiner.mobile' : Ext.getCmp("mobile").getValue()
		};
	}
	
	function getByteLen(val) {
        var len = 0;
        for (var i = 0; i < val.length; i++) {
           var length = val.charCodeAt(i);
           if(length>=0&&length<=128) {
                len += 1;
           } else {
                len += 1;
           }
        }
        return len;
    }
	
	function initSpecEdit() {
		var record = specSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '你没有选中任何项目！',
	           buttons: Ext.MessageBox.OK,
	           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		specWindow.hide();
		specWindow.setTitle('修改服务');
		specForm.getForm().reset();
		specForm.loadRecord(record);
		specForm.getForm().url = '/wechat/act/updateActSpec.atc';
		specWindow.show();
		
	}
	
	function initSpecDelete() {
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
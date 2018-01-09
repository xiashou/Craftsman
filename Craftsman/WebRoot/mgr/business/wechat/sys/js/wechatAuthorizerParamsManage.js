Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var selectedIds = new Array();
	
	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
				header : 'NO',
				resizable: true,
				width : 40
			});
	
	var paramForm = new Ext.form.FormPanel({
				id : 'paramForm',
				layout: 'anchor',
				defaults: {
		            anchor: '100%',
		            layout: 'hbox',
		            xtype:'fieldcontainer'
		        },
				bodyPadding: '5 10 0 0',
				border : false,
				items : [{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'id', name:'wechatAuthorizerParams.id', xtype:'hiddenfield'}
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'companyId', name: 'wechatAuthorizerParams.companyId',allowBlank: false, fieldLabel: '公司ID', blankText:'公司ID不能为空！'},
					           {id:'companyName', name: 'wechatAuthorizerParams.companyName',allowBlank: false, fieldLabel: '公司名称', blankText:'公司名称不能为空！'},
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'deptCode', name: 'wechatAuthorizerParams.deptCode',allowBlank: false, fieldLabel: '门店编码', blankText:'门店编码不能为空！'},
					           {id:'deptName', name: 'wechatAuthorizerParams.deptName',allowBlank: false, fieldLabel: '门店名称', blankText:'门店名称不能为空！'},
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'sid', name: 'wechatAuthorizerParams.sid', fieldLabel: '自定义授权码', readOnly:true, fieldStyle:'background-color: #F0F0F0;'},
					           {id:'authorizerAppName', name: 'wechatAuthorizerParams.authorizerAppName',fieldLabel: 'app名称' }
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'authorizerStatus', name: 'wechatAuthorizerParams.authorizerStatus', fieldLabel: '授权状态', readOnly:true, fieldStyle:'background-color: #F0F0F0;' },
					           {id:'authorizerAppId', name: 'wechatAuthorizerParams.authorizerAppId',fieldLabel: 'appId', readOnly:true, fieldStyle:'background-color: #F0F0F0;' }
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'authorizerAppSecret', name: 'wechatAuthorizerParams.authorizerAppSecret',fieldLabel: 'appsecret' , readOnly:true, fieldStyle:'background-color: #F0F0F0;'},
					           {id:'deptTelephone', name: 'wechatAuthorizerParams.deptTelephone', fieldLabel: '门店电话' }
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'linkman', name: 'wechatAuthorizerParams.linkman',fieldLabel: '联系人'},
					           {id:'telephone', name: 'wechatAuthorizerParams.telephone', fieldLabel: '联系电话' }
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'province', name: 'wechatAuthorizerParams.province',fieldLabel: '省份'},
					           {id:'city', name: 'wechatAuthorizerParams.city', fieldLabel: '城市' }
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'area', name: 'wechatAuthorizerParams.area',fieldLabel: '区县'},
					           {id:'address', name: 'wechatAuthorizerParams.address', fieldLabel: '详细地址' }
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'latitudeLongitude', name: 'wechatAuthorizerParams.latitudeLongitude',fieldLabel: '经纬度'},
					           {id:'msgSignature', name: 'wechatAuthorizerParams.msgSignature', fieldLabel: '短信签名' }
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'authorizerAccessToken', name:'wechatAuthorizerParams.authorizerAccessToken', fieldLabel: 'Token', readOnly:true, fieldStyle:'background-color: #F0F0F0;'},
					           {id:'tokenFreshTime', name: 'wechatAuthorizerParams.tokenFreshTime',fieldLabel: 'Token刷新时间' , readOnly:true, fieldStyle:'background-color: #F0F0F0;'}
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'tokenFreshRate', name:'wechatAuthorizerParams.tokenFreshRate', fieldLabel: '刷新频率', readOnly:true, fieldStyle:'background-color: #F0F0F0;'},
					           {id:'authorizerRefreshToken', name: 'wechatAuthorizerParams.authorizerRefreshToken',fieldLabel: '刷新令牌' , readOnly:true, fieldStyle:'background-color: #F0F0F0;'}
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'funcInfo', name:'wechatAuthorizerParams.funcInfo', fieldLabel: '权限列表', readOnly:true, fieldStyle:'background-color: #F0F0F0;'},
					           {id:'createBy', name: 'wechatAuthorizerParams.createBy',fieldLabel: '创建人' , readOnly:true, fieldStyle:'background-color: #F0F0F0;'}
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'updateBy', name:'wechatAuthorizerParams.updateBy', fieldLabel: '修改人', readOnly:true, fieldStyle:'background-color: #F0F0F0;'},
					           {id:'createTime', name: 'wechatAuthorizerParams.createTime',fieldLabel: '创建时间' , readOnly:true, fieldStyle:'background-color: #F0F0F0;'}
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:100, labelAlign:'right'},
					    items: [
					           {id:'updateTime', name:'wechatAuthorizerParams.updateTime', fieldLabel: '修改时间', readOnly:true, fieldStyle:'background-color: #F0F0F0;'},
					    ]
					}
				],
				buttons:[{
					text : '保 存',
					iconCls : 'accept',
					handler : function() {
						if (paramForm.form.isValid()) {
							paramForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									paramWindow.hide();
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
				},{
					text : '关 闭 ',
					iconCls : 'stop',
					handler : function() {
						paramWindow.hide();
					}
				}]
			});
	
	var paramWindow = new Ext.Window({
				title : '修改', // 窗口标题
				layout : 'fit',
				width : 650,
				height : 490,
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
				items: [paramForm]
			});
	
	var store = Ext.create('Ext.data.Store', {
		  		  pageSize : 20,
		          fields: ['id','companyId','companyName', 'deptCode', 'deptName', 'sid', 'authorizerStatus',
		                   'authorizerAppId', 'authorizerAppSecret', 'authorizerAppName',
		                   'deptTelephone','linkman', 'telephone', 'province', 'city', 'area', 'address',
		                   'latitudeLongitude','msgSignature', 
		                   'authorizerAccessToken', 'tokenFreshTime', 'tokenFreshRate', 'authorizerRefreshToken', 
		                   'funcInfo',
		                   'createBy', 'updateBy', 'createTime', 'updateTime'],
		          proxy: {
		              type: 'ajax',
		              url : '/wechat/sys/queryAllWechatAuthorizerParams.atc',
		              reader : {
		                  root: 'wechatAuthorizerParamsList',
		                  totalProperty: 'totalCount'
		              }
		          }
		    });
  	
  	var columns= [rowNum,
	            {header : 'ID',dataIndex: 'id', hidden: true},
	            {header : '公司ID',dataIndex: 'companyId', width: 80},
	            {header : '公司名称',dataIndex: 'companyName', width: 140},
	            {header : '门店编码',dataIndex: 'deptCode', width: 70},
	            {header : '门店名称',dataIndex: 'deptName', width: 140},
	            {header : '自定义授权码',dataIndex: 'sid', width: 280},
	            {header : '授权状态',dataIndex: 'authorizerStatus', width: 70, renderer: function(v){return (v == 2) ? '已授权': '未授权'}},
	            {header : '授权方appid',dataIndex: 'authorizerAppId', width: 160},
	            {header : '授权方appsecret',dataIndex: 'authorizerAppSecret', width: 140},
	            {header : '授权方名称',dataIndex: 'authorizerAppName', width: 140},
	            {header : '门店电话',dataIndex: 'deptTelephone', width: 140},
	            {header : '联系人',dataIndex: 'linkman', width: 140},
	            {header : '联系电话',dataIndex: 'telephone', width: 140},
	            {header : '省份',dataIndex: 'province', width: 140},
	            {header : '城市',dataIndex: 'city', width: 140},
	            {header : '区县',dataIndex: 'area', width: 140},
	            {header : '详细地址',dataIndex: 'address', width: 140},
	            {header : '经纬度',dataIndex: 'latitudeLongitude', width: 140},
	            {header : '短信签名',dataIndex: 'msgSignature', width: 140},
	            {header : '授权方接口调用凭据',dataIndex: 'authorizerAccessToken', width: 140},
	            {header : 'Token刷新时间',dataIndex: 'tokenFreshTime', width: 140},
	            {header : '刷新频率',dataIndex: 'tokenFreshRate', width: 140},
	            {header : '接口调用凭据刷新令牌',dataIndex: 'authorizerRefreshToken', width: 140},
	            {header : '权限集列表',dataIndex: 'funcInfo', width: 140},
	            {header : '创建人',dataIndex: 'createBy', width: 140},
	            {header : '修改人',dataIndex: 'updateBy', width: 140},
	            {header : '创建时间',dataIndex: 'createTime', width: 140},
	            {header : '修改时间',dataIndex: 'updateTime', width: 140}
	  	]; 
	
	var tbar = Ext.create('Ext.toolbar.Toolbar', {
			region : 'north',
			border : false,
	        items: [{
	        	xtype: 'button',
	        	id: 'search',
	        	text: ' 查 询 ',
	        	iconCls: 'preview', 
	        	handler: function(){
	        		store.reload({
	        			params : {
	        				start : 0,
	        				limit : bbar.pageSize
	        			}
	        		});
	            }
	        },{
	        	xtype: 'button',
	        	id: 'insert',
	        	text: '新建',
	        	iconCls: 'add',
	        	hidden : !parent.haveActionMenu('Add'),
	        	handler: function() {
	        		paramWindow.setTitle('新建');
	        		paramForm.getForm().reset();
	        		paramForm.getForm().url = '/wechat/sys/insertWechatAuthorizerParamsUUID.atc';
	        		paramWindow.show();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'update',
	        	text: '修改',
	        	iconCls: 'pencil',
	        	hidden : !parent.haveActionMenu('Edit'),
	        	handler: function() {
	        		initEdit();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'delete',
	        	text: '删除',
	        	iconCls: 'delete', 
	        	hidden : !parent.haveActionMenu('Del'),
	        	handler: function(){
	        		initDelete();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'refreshMenu',
	        	text: '刷新菜单',
	        	iconCls: 'arrow_refresh_small', 
	        	hidden : !parent.haveActionMenu('refreshMenu'),
	        	handler: function(){
	        		initRereshMenu();
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
		store.reload({
			params : {
				start : 0,
				limit : bbar.pageSize
			}
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
	
	var selModel = Ext.create('Ext.selection.CheckboxModel', {
			injectCheckbox: 1,
//			mode: 'SINGLE'
			listeners : {
				selectionchange : function(sm, selections) {
					var rows = sm.getSelection();
					selectedIds = new Array();
	        		for(var i = 0; i < rows.length; i++){
	        			Ext.Array.clean(selectedIds);
	        			if(!Ext.Array.contains(selectedIds,rows[i].data.id)){
	        				selectedIds.push(rows[i].data.id);
	        			}
	        		}
				}
			}
		});
	
	var grid = new Ext.grid.GridPanel({
			columns : columns,
			store : store, 
			region : 'center',
			autoScroll : true,
			columnLines : true,
			selModel : selModel,
			frame : false,
			tbar : tbar, 
			bbar : bbar,
			stripeRows:true,
			viewConfig : {
				enableTextSelection:true	//设置单元格可以选择
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			listeners:{
				itemdblclick: function(grid, record, item, index, e) {
					if(parent.haveActionMenu('Edit'))
						initEdit();
		        }
		    }
		});
	
	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			region : 'center',
			layout : 'fit',
			border : false,
			items : [ grid ]
		}]
	});
	
	store.load({
		params:{
			start : 0,
			limit : bbar.pageSize
		}
	});
	
	
	function initEdit() {
		var record = selModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		} 
		paramWindow.setTitle('修改');
		paramForm.loadRecord(record);
		paramForm.getForm().url = '/wechat/sys/updateWechatAuthorizerParams.atc';
		paramWindow.show();
	}
	
	function initDelete() {
		var record = selModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		} 
		Ext.Msg.confirm('请确认', '确定要删除这个按钮项吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/wechat/sys/deleteWechatAuthorizerParams.atc',
					params : {
						'wechatAuthorizerParams.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							paramWindow.hide();
							Ext.example.msg('提示', result.msg);
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
	
	function initRereshMenu() {
		if(selectedIds == ''){
			Ext.MessageBox.show({
				title: '提示',
				msg: '没有选中任何记录！',
				buttons: Ext.MessageBox.OK,
				icon: Ext.MessageBox.INFO
            });
		} else {
			Ext.Msg.confirm('请确认', '确定要刷新选中的' + selectedIds.length + '个公众号菜单么?', function(btn, text) {
				if(btn == 'yes'){
					Ext.Ajax.request({
						url : '/wechat/sys/refreshMenuWechatAuthorizerParams.atc',
						params : {
							ids : selectedIds
//							'wechatAuthorizerParams.id' : record.raw.id,
//							'wechatAuthorizerParams.sid' : record.raw.sid,
//							'wechatAuthorizerParams.authorizerAppId' : record.raw.authorizerAppId
						},
						success : function(resp, opts) {
							var result = Ext.decode(resp.responseText);
							if(result.success){
								paramWindow.hide();
								Ext.example.msg('提示', result.msg);
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
//		var record = selModel.getSelection()[0];
//		if(Ext.isEmpty(record)) {
//			Ext.MessageBox.show({
//		           title: '提示',
//		           msg: '你没有选中任何项目！',
//		           buttons: Ext.MessageBox.OK,
//		           icon: Ext.MessageBox.INFO
//		    });
//			return;
//		} 
//		Ext.Msg.confirm('请确认', '确定要刷新选中公众号菜单么?', function(btn, text) {
//			if(btn == 'yes'){
//				Ext.Ajax.request({
//					url : '/wechat/sys/refreshMenuWechatAuthorizerParams.atc',
//					params : {
//						'wechatAuthorizerParams.id' : record.raw.id,
//						'wechatAuthorizerParams.sid' : record.raw.sid,
//						'wechatAuthorizerParams.authorizerAppId' : record.raw.authorizerAppId
//					},
//					success : function(resp, opts) {
//						var result = Ext.decode(resp.responseText);
//						if(result.success){
//							paramWindow.hide();
//							Ext.example.msg('提示', result.msg);
//							store.reload();
//						} else 
//							Ext.MessageBox.show({
//     				           title: '提示',
//     				           msg: result.msg,
//     				           buttons: Ext.MessageBox.OK,
//     				           icon: Ext.MessageBox.ERROR
//                            });
//					},
//					failure : function(resp, opts) {
//						var result = Ext.decode(resp.responseText);
//                        Ext.MessageBox.show({
// 				           title: '提示',
// 				           msg: result.msg,
// 				           buttons: Ext.MessageBox.OK,
// 				           icon: Ext.MessageBox.ERROR
//                        });
//					}
//				});
//			}
//		});
	}
	
});
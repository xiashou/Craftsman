Ext.onReady(function() {
	
	Ext.QuickTips.init();
	var actId;
	
	var specRowNum = new Ext.grid.RowNumberer({
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
	
	var actStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'appId', 'name', 'dateStart', 'dateEnd', 'address', 'contact', 'price', 'number', 'organization', 'introduce', 
                 'signNumber', 'signFicNumber', 'readNumber', 'readFicNumber', 'collNumber', 'collFicNumber', 'pictures', 'fields', 'status'],
        proxy: {
            type: 'ajax',
            url : '/wechat/act/queryActSignupPage.atc',
            reader : {
                root: 'signupList',
                totalProperty: 'totalCount'
            }
        }
	});
	
	var specForm = new Ext.form.FormPanel({
		id : 'specForm',
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
				labelWidth : 50,
				labelAlign : 'right'
			},
			items : [ {
				id : 'specName',
				name : 'spec.name',
				allowBlank : false,
				fieldLabel : '描述',
				blankText : '描述不能为空!'
			},{
				id : 'specId',
				name : 'spec.id',
				xtype: 'hiddenfield'
			},{
				id : 'specActId',
				name : 'spec.actId',
				xtype: 'hiddenfield'
			} ]
		},{
			defaults : {
				flex : 1,
				xtype : 'numberfield',
				labelWidth : 50,
				labelAlign : 'right'
			},
			items : [ {
				id : 'specPrice',
				name : 'spec.price',
				fieldLabel : '价格'
			} ]
		} ],
		buttons : [ {
			text : '保 存',
			iconCls : 'accept',
			handler : function() {
				if (specForm.form.isValid()) {
					if(actId != ''){
						Ext.getCmp("specActId").setValue(actId);
						specForm.form.submit({
							waitTitle : '提示',
							method : 'POST',
							waitMsg : '正在处理数据,请稍候...',
							success : function(form, action) {
								specWindow.hide();
								Ext.example.msg('提示', action.result.msg);
								specStore.reload();
							},
							failure : function(form, action) {
								var msg = action.result.msg;
								Ext.MessageBox.alert('提示', msg);
							}
						});
					} else 
						Ext.MessageBox.show({
 				           title: '提示',
 				           msg: '请选择活动！',
 				           buttons: Ext.MessageBox.OK,
 				           icon: Ext.MessageBox.ERROR
                        });
				}
			}
		}, {
			text : '关 闭 ',
			iconCls : 'stop',
			handler : function() {
				specWindow.hide();
			}
		} ]
	});

	var specWindow = new Ext.Window({
		title : '修改',
		layout : 'fit',
		width : 400,
		height : 170,
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
		items : [ specForm ]
	});
	
  	var specColumns= [specRowNum,
        {header : 'ID',dataIndex: 'id', hidden: true},
        {header : 'actId',dataIndex: 'actId', hidden: true},
        {header : '描述',dataIndex: 'name', width: 140},
        {header : '价格',dataIndex: 'price', width: 80}
  	];
  	
  	var statusRenderer = function(value) {
    	if(value == 1)
			return '<font color=green>开启</font>';
		else 
			return '<font color=red>关闭</font>';
    };
  	
  	var actColumns= [actRowNum,
  	        {header : 'ID',dataIndex: 'id', hidden: true},
            {header : '编辑',xtype: 'actioncolumn',menuDisabled: true, sortable: false,width: 40, align: 'center',
            	items: [{
            		iconCls: 'pencil',
            		tooltip: '编辑活动详情',
            		scope: this,
                    handler: function(grid, rowIndex){
                    	var record = actStore.getAt(rowIndex);
                    	actForm.getForm().reset();
                    	actForm.getForm().loadRecord(record);
                    	if(!Ext.isEmpty(record.data.fields)){
                    		Ext.each(Ext.getCmp("sfields").items.items, function(item){
                    			if(record.data.fields.indexOf(item.inputValue) >= 0)
                    				item.setValue(true);
                			});
                    	}
                    	actForm.getForm().url = '/wechat/act/updateActSignup.atc';
                    	actWindow.show();
                    }
            	}]
            },
            {header : '活动名称',dataIndex: 'name', width: 160},
            {header : '活动开始',dataIndex: 'dateStart', width: 100},
            {header : '活动结束',dataIndex: 'dateEnd', width: 100},
            {header : '联系方式',dataIndex: 'contact', width: 120},
            {header : '地址',dataIndex: 'address', width: 120},
            {header : '价格',dataIndex: 'price', width: 80, xtype: 'numbercolumn', format:'0.00'},
            {header : '限定人数',dataIndex: 'number', width: 80},
            {header : '报名数',dataIndex: 'signNumber', width: 80},
            {header : '虚拟报名数',dataIndex: 'signFicNumber', width: 80},
            {header : '阅读数',dataIndex: 'readNumber', width: 80},
            {header : '虚拟阅读数',dataIndex: 'readFicNumber', width: 80},
            {header : '状态',dataIndex: 'status', width: 60, renderer: statusRenderer},
  	];
  	
  	var specTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '新建',
        	iconCls: 'add',
//        	hidden : !parent.haveActionMenu('AddMaterial'),
        	handler: function() {
        		specWindow.setTitle('新增服务');
        		specForm.getForm().reset();
        		specForm.getForm().url = '/wechat/act/insertActSpec.atc';
        		specWindow.show();
            }
        },{
        	xtype: 'button',
        	text: '修改',
        	iconCls: 'pencil',
//        	hidden : !parent.haveActionMenu('EditMaterial'),
        	handler: function() {
        		initSpecEdit();
            }
        },{
        	xtype: 'button',
        	text: '删除',
        	iconCls: 'delete', 
//        	hidden : !parent.haveActionMenu('DelMaterial'),
        	handler: function(){
        		initSpecDelete();
            }
        }]
    });
  	
	var actTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '新建',
        	iconCls: 'add',
        	handler: function() {
        		actForm.getForm().reset();
        		actForm.getForm().url = '/wechat/act/insertActSignup.atc';
        		actWindow.show();
            }
        },{
        	xtype: 'button',
        	text: '删除',
        	iconCls: 'delete', 
        	handler: function(){
        		initActDelete();
            }
        }]
    });
	
	var specSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var actSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1
	});
	
	var specGrid = new Ext.grid.GridPanel({
		columns : specColumns,
		store : specStore, 
		autoScroll : true,
		columnLines : true,
		selModel : specSelModel,
		tbar : specTbar, 
		stripeRows:true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
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
		actStore.pageSize = parseInt(comboBox.getValue());
		var params = getParams();
		params.start = 0;
		params.limit = actBbar.pageSize;
		actStore.reload({
			params : params
		});
	});
	
	var actBbar = new Ext.PagingToolbar({
		pageSize : actNumber,
		store : actStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', actPagesizeCombo ]
	});
	
	var actGrid = new Ext.grid.GridPanel({
		columns : actColumns,
		store : actStore, 
		autoScroll : true,
		columnLines : true,
		selModel : actSelModel,
		tbar : actTbar, 
		bbar : actBbar,
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
				specStore.load();
	        }
	    }
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
					url : '/wechat/act/uploadActSignupPictures.atc',
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
	
	var actForm = new Ext.form.FormPanel({
		id : 'actForm',
		layout: 'hbox',
		items:[{
			flex: 1,
			height: '100%',
			xtype: 'container',
			items:[{
				xtype: 'fieldset',
				title: '基本信息',
				layout: 'anchor',
				height: '60%',
				margin: '5 0 0 5',
				defaults: {
					anchor: '100%',
					labelWidth : 90,
					labelAlign : 'right'
				},
				items : [ {
                	xtype: 'textfield',
                    id: 'name',
                    name: 'signup.name',
                    fieldLabel: '活动名称',
    				allowBlank : false,
    				blankText : '活动名称不能为空!'
				},{
                	layout: 'hbox',
                	defaults : {
                		flex : 1,
                		xtype : 'datefield',
                		format: 'Y/m/d',
                		labelWidth : 90,
                		margin: '5 0 5 0',
                		labelAlign : 'right'
                	},
                	items : [ {
                		id: 'dateStart',
                		name: 'signup.dateStart',
                		fieldLabel: '开始日期',
                	},{
                		id: 'dateEnd',
                		name: 'signup.dateEnd',
                		fieldLabel: '结束日期'
                	} ]
                },{
                	xtype: 'textfield',
                    id: 'contact',
                    name: 'signup.contact',
                    fieldLabel: '联系方式',
    				allowBlank : false,
    				blankText : '联系方式不能为空!'
                },{
                	xtype: 'textfield',
                    id: 'address',
                    name: 'signup.address',
                    fieldLabel: '活动地址',
    				allowBlank : false,
    				blankText : '活动地址不能为空!'
                },{
                	xtype: 'textfield',
                	id: 'organization',
                	name: 'signup.organization',
                	fieldLabel: '组织方'
                },{
                	xtype: 'hiddenfield',
                	id: 'id',
                	name: 'signup.id'
                },{
                	xtype: 'hiddenfield',
                	id: 'status',
                	name: 'signup.status'
                },{
                	layout: 'hbox',
                	defaults : {
                		flex : 1,
                		xtype : 'numberfield',
                		labelWidth : 90,
                		margin: '0 0 0 0',
                		labelAlign : 'right'
                	},
                	items : [ {
                		id: 'price',
                		name: 'signup.price',
                		fieldLabel: '价格',
                	},{
                		id: 'number',
                		name: 'signup.number',
                		fieldLabel: '限定人数',
                	} ]
                },{
                	layout: 'hbox',
                	defaults : {
                		flex : 1,
                		xtype : 'numberfield',
                		labelWidth : 90,
                		margin: '5 0 0 0',
                		labelAlign : 'right'
                	},
                	items : [ {
                		id: 'signNumber',
                		name: 'signup.signNumber',
                		fieldLabel: '报名人数',
                		readOnly: true
                	},{
                		id: 'signFicNumber',
                		name: 'signup.signFicNumber',
                		fieldLabel: '虚拟人数',
                	} ]
                },{
                	layout: 'hbox',
                	defaults : {
                		flex : 1,
                		xtype : 'numberfield',
                		labelWidth : 90,
                		margin: '5 0 0 0',
                		labelAlign : 'right'
                	},
                	items : [ {
                		id: 'readNumber',
                		name: 'signup.readNumber',
                		fieldLabel: '阅读人数',
                		readOnly: true
                	},{
                		id: 'readFicNumber',
                		name: 'signup.readFicNumber',
                		fieldLabel: '虚拟人数',
                	} ]
                },{
                	layout: 'hbox',
                	xtype: 'checkboxgroup',
                    fieldLabel: '必填字段',
                    itemId: 'sfields',
                    id:'sfields',
                    margin: '5 5 0 0',
                    defaults : {
                    	name : 'signup.fields',
                    	margin: '0 10 0 0'
                	},
                	items : [ {
                		boxLabel: '性别', inputValue: '1'
                	},{
                		boxLabel: '年龄', inputValue: '2'
                	},{
                		boxLabel: '身份证号', inputValue: '3'
                	},{
                		boxLabel: '车牌号', inputValue: '4'
                	} ]
                },new Ext.form.ComboBox({
                	id: 'status',
            		name : 'signup.status',
            		labelWidth : 90,
            		margin: '5 0 0 0',
            		labelAlign : 'right',
            		triggerAction : 'all',
            		mode : 'local',
            		fieldLabel: '状态',
            		store : new Ext.data.ArrayStore({
            			fields : [ 'value', 'text' ],
            			data : [ [ 1, '开启' ], [ 0, '关闭' ] ]
            		}),
            		valueField : 'value',
            		displayField : 'text',
            		editable : false
            	})]
			},{
				xtype: 'fieldset',
				title: '预览图片',
				height: '38%',
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
    				name: 'signup.pictures',
    				listeners: {
    					change: function(field, newValue, oldValue) {
    						if(!Ext.isEmpty(newValue)) {
    							imgStore.removeAll();
    							newValue = newValue.substring(0, newValue.length - 1);
    							if(newValue.indexOf(",") > 0){
    								var pics = newValue.split(",");
    								Ext.each(pics, function(pic){
    									imgStore.add({id: pic, src:'/upload/wechat/activity/' + pic});
    								});
    							} else
    								imgStore.add({id: newValue, src:'/upload/wechat/activity/' + newValue});
    								
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
			title: '活动详情',
			height: '99.5%',
			margin: '5 5 0 0',
			items: [{
	        	xtype: 'htmleditor',
	        	height: document.body.scrollHeight - 150,
	        	id: 'introduce',
	            name: 'signup.introduce',
	            anchor: '100%',
	            enableAlignments: true,
	            enableColors: true,
	            enableFont: true,
	            enableFontSize: true,
	            enableFormat: true,
	            enableLinks: true,
	            enableLists: true,
	            fontFamilies : ['宋体','黑体','隶书','微软雅黑','Arial','Courier New','Tahoma','Verdana'],
	            plugins: Ext.create('Ext.ux.custom.ImageButtonPlugin')
			}]
		}]
	});
	
	var actWindow = new Ext.Window({
		title : '编辑商品',
		layout : 'fit',
		width : 1100,
		height : '90%',
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
		items : [ actForm ],
		buttons : [ {
			text : '保 存',
			iconCls : 'accept',
			handler : function() {
				if (actForm.form.isValid()) {
					actForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							actWindow.hide();
							Ext.example.msg('提示', action.result.msg);
							actStore.reload();
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
				actWindow.hide();
			}
		} ]
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
			items : [ actGrid ]
		}, {
			title : '增值服务',
			iconCls : 'tag_blue',
			tools : [{
				type:'refresh',
				handler : function() {
					specStore.reload();
				}
			}],
			collapsible : true,
			width : '20%',
			split : true,
			region : 'east',
			layout : 'fit',
			border : false,
			items : [ specGrid ]
		}]
	});
	
	actStore.load();
	
	specStore.on('beforeload', function() {
		Ext.apply(specStore.proxy.extraParams, getParams());
	});
	
	function getParams(){
		return {
			actId : actId
		};
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
		Ext.getCmp("specId").setValue(record.data.id);
		Ext.getCmp("specName").setValue(record.data.name);
		Ext.getCmp("specPrice").setValue(record.data.price);
		specForm.getForm().url = '/wechat/act/updateActSpec.atc';
		specWindow.show();
	}
	

	function initActDelete() {
		var record = actSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '你没有选中任何项目！',
	           buttons: Ext.MessageBox.OK,
	           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		Ext.Msg.confirm('请确认', '确定要删除这个活动吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/wechat/act/deleteActSignup.atc',
					params : {
						'signup.id' : record.data.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							actWindow.hide();
							Ext.example.msg('提示', result.msg);
							actSelModel.deselectAll();
							actStore.reload();
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
	
	function initSpecDelete() {
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
		Ext.Msg.confirm('请确认', '确定要删除这个服务吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/wechat/act/deleteActSpec.atc',
					params : {
						'spec.id' : record.data.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							specWindow.hide();
							Ext.example.msg('提示', result.msg);
							specSelModel.deselectAll();
							specStore.reload();
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
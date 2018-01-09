Ext.define('Ext.Tsingma.HomePage',{ // 起始页
	extend : 'Ext.panel.Panel',
	initComponent : function() {
		var me = this;
		Ext.apply(this,{
			layout: 'column',
			defaults: {padding: '5 0 0 5'},
			autoScroll: true,
	        items:[{
	        	columnWidth: 1/2,
	        	items:[{
	        		title: '查找会员',
	            	iconCls: 'award_star_bronze_1',
	            	items:[Ext.create('Tsingma.System.Home.FindMemberPanel')]
	        	},{
	        		title: '通知中心',
	            	iconCls: 'comment',
	            	padding: '5 0 0 0',
	            	tools:[{
	            		type:'refresh',
	            		tooltip: '刷新',
	            		callback: function(panel, tool, event) {
	            			panel.items.get('LastOrder').getStore().reload();
	            		}
//	            	},{
//	            		type:'plus',
//	            		tooltip: '更多',
//	            		callback: function(panel, tool, event) {
//	            			//如果存在之前打印的选项卡则先关闭
//		            		if(parent.mainTab.getComponent('010707'))
//		            			parent.global.closeTab(parent.mainTab.getComponent('010707'));
//		            		parent.global.openTabByMenuId("010707", "");
//	            		}
	            	}],
//	            	items:[Ext.create('Tsingma.System.Home.LastOrderGrid')]
	            	items:[Ext.create('Tsingma.System.Home.NotifyCenter')]
	        	}]
	        },{
	        	columnWidth: 1/4,
	        	title: '信息记录',
        		iconCls: 'chart_bar',
        		tools:[{
            		type:'refresh',
            		tooltip: '刷新',
            		callback: function(panel, tool, event) {
            			panel.items.get('IndexStatistics').getStore().reload();
            		}
            	}],
            	items:[Ext.create('Tsingma.System.Home.IndexStatisticsGrid')]
	        },{
	        	columnWidth: 1/4,
	        	title: '客户回访',
	        	iconCls: 'page',
//        		items:[Ext.create('Tsingma.System.Home.QuickCreateOrder')]
        		items:[Ext.create('Tsingma.System.Home.IndexVisitGrid')]
	        },{
	        	columnWidth: 3/4,
	        	title: '在厂维修订单',
            	iconCls: 'table',
            	tools:[{
            		type:'refresh',
            		tooltip: '刷新',
            		callback: function(panel, tool, event) {
            			panel.items.get('IncompleteOrder').getStore().reload();
            		}
            	},{
            		type:'plus',
            		tooltip: '更多',
            		callback: function(panel, tool, event) {
            			//如果存在之前打印的选项卡则先关闭
	            		if(parent.mainTab.getComponent('010312'))
	            			parent.global.closeTab(parent.mainTab.getComponent('010312'));
	            		parent.global.openTabByMenuId("010312", "");
            		}
            	}],
            	items:[Ext.create('Tsingma.System.Home.IncompleteOrderGrid')]
	        }],
	        isReLayout : false
		});
//		showMessageTip();
		this.callParent(arguments);
	}
});


/**
 * 查找会员
 */
Ext.define('Tsingma.System.Home.FindMemberPanel', {
	extend : 'Ext.panel.Panel',
	border : true,
	initComponent : function() {
		var me = this;
		
		var salesStore = Ext.create('Ext.data.Store', {
			autoLoad: true,
	        fields: ['id', 'name'],
	        proxy: {
	            type: 'ajax',
	            url : '/shop/queryEmployeeByDept.atc',
	            reader : {
	                root: 'employeeList'
	            }
	        }
		});
		
		var salesCombo = Ext.create('Ext.form.ComboBox', {
			id: 'sales',
			name: 'member.sales',
			flex: 1,
			triggerAction : 'all',
			fieldLabel: '销售顾问',
			labelWidth:70, 
			labelAlign:'right',
			mode : 'local',
			store: salesStore,
			valueField : 'name',
			displayField : 'name',
			editable : false
		});
		
		var areaShortCombo = new Ext.form.ComboBox({
			id: 'areaShortCombo',
			name: 'car.carShort',
			flex: 4,
			allowBlank: false, 
			fieldLabel: '车牌号码',
			labelWidth: 70,
			labelAlign: 'right',
			triggerAction : 'all',
			mode : 'local',
			store: parent.GAreaShortStore,
			valueField : 'areaShort',
			displayField : 'areaShort',
			value: parent.II,
			editable : false,
			width : 85
		});
		
		var areaCodeCombo = new Ext.form.ComboBox({
			id: 'areaCodeCombo',
			name: 'car.carCode',
			flex: 2,
			allowBlank: false, 
			triggerAction : 'all',
			mode : 'local',
			store: parent.GAreaCodeStore,
			valueField : 'areaCode',
			displayField : 'areaCode',
			value: parent.JJ,
			editable : false,
			width : 85
		});
		
	    var brandStore = Ext.create('Ext.data.Store', {
	        pageSize: 10,
        	autoLoad: true,
			fields : [{name: 'id'}, {name: 'shortCode'}, {name: 'brandName'}],  
			proxy:{
	            type: 'ajax',
	            url : '/base/queryCarBrandPage.atc',
	            reader : {
	                root: 'carBrandList',
	                totalProperty: 'totalCount'
	            }
	        }
	    });
		
		var memberForm = new Ext.form.FormPanel({
			id : 'memberForm',
			layout: 'anchor',
			defaults: {
	            anchor: '100%',
	            xtype: 'fieldcontainer',
		        layout: 'hbox'
	        },
			bodyPadding: '5 10 0 0',
			border : false,
			items : [{
			         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
			         items: [
							areaShortCombo, areaCodeCombo,
		                    {id:'carNumber', name: 'car.carNumber', maxLength: 6, flex: 4, allowBlank: false, blankText:'车牌号不能为空!'}
			         ]
			     },{
			         defaults: {flex: 1,xtype:'combo'},
			         items: [
		                    {store: brandStore,
		                    fieldLabel: '品牌',
		                    labelWidth: 70,
		                    labelAlign:'right',
		                    id:'carBrand',
		                    name: 'car.carBrand',
	                        displayField: 'brandName',
	                        valueField: 'id',
	                        queryParam: 'carBrand.shortCode',	//搜索参数字段
	                        typeAhead: false,
	                        hideTrigger:true,
	                        minChars: 1,
	                        listConfig: {
	                            loadingText: '正在查找...',
	                            emptyText: '<div style="padding: 5px;">没有找到对应的品牌！</div>',
	                            getInnerTpl: function() {
	                                return '<span>{shortCode} -> {brandName}</span>';
	                            }
	                        },
	                        pageSize: 10}
			         ]
			     },{
			         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
			         items: [
		                    {id:'name', name: 'member.name', allowBlank: false, fieldLabel: '姓名', blankText:'名称不能为空!'}
			         ] 
			     },{
			    	 defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
			    	 items: [
			    	         {id:'mobile', name: 'member.mobile', maxLength: 11, fieldLabel: '电话号码'}
			    	 ] 
			     },{
					 defaults: {flex: 1,xtype:'radiogroup',labelWidth:70,labelAlign:'right'},
					 items: [
					        {id:'sex', fieldLabel: '性别', columns:4, defaults: {name: 'member.sex'},
					        	items: [{inputValue: '1', boxLabel: '男', checked: true}, {inputValue: '2', boxLabel: '女'}]
					        }
					 ]
			     },{
			         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
			         items: [salesCombo]
			     }
			],
			buttons:[{
				text : '创 建',
				iconCls : 'accept',
				handler : function() {
					if (memberForm.form.isValid()) {
						if(isNaN(Ext.getCmp('carBrand').getValue())){
							Ext.MessageBox.show({
	 				           	title: '提示',
	 				           	msg: '请输入品牌首字母选择汽车品牌！',
	 				           	buttons: Ext.MessageBox.OK,
	 				           	icon: Ext.MessageBox.ERROR
	                        });
						} else {
							memberForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								url: '/member/createMember.atc',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									memberWindow.hide();
									memberForm.getForm().reset();
									Ext.example.msg("提示", "会员" + action.result.msg + " 创建成功!");
									global.openTabByMenuId('010301', "vip=" + action.result.msg);
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
					}
				}
			},{
				text : '关 闭 ', // 按钮文本
				iconCls : 'stop', // 按钮图标
				handler : function() { // 按钮响应函数
					memberWindow.hide();
				}
			}]
		});
		
		var memberWindow = new Ext.Window({
			title : '创建车主', 
			layout : 'fit',
			width : 400,
			height : 280,
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
			items : [memberForm]
		});
		
		var memberStore = Ext.create('Ext.data.Store', {
			fields: ['memId', 'vipNo', 'name', 'mobile', 'carShort', 'carCode', 'carNumber', 'carId', 'fullCarNumber'],
			proxy:{
	            type: 'ajax',
	            url : '/member/queryMemberByKeyword.atc',
	            reader : {
	                root: 'memberList',
	                totalProperty: 'totalCount'
	            }
	        }
	    });
		
		var memberCombo = Ext.create('Ext.form.ComboBox', {
            columnWidth: 7/9,
            store: memberStore,
            valueField: 'vipNo',
            displayField: '',
            typeAhead: false,
            hideLabel: true,
            hideTrigger:true,
            anchor: '60%',
            height: 31,
            queryParam: 'keyword',	//搜索参数字段
            minChars: 2,
            emptyText: '请输入 车牌/手机号',
            pageSize: 10,
            listConfig: {
                loadingText: '查找中...',
                emptyText: '<div style="padding: 5px;">没有找到相关车主！</div>',
                getInnerTpl: function() {
                    return '<div class="search">会员卡号:{vipNo}/车牌号:{carShort}{carCode}{carNumber}/姓名:{name}/电话:{mobile}</div>';
                }
            },
            listeners : {
				select: function(cb, records) {
	            	var record = records[0];
	            	var param = "vip=" + record.data.vipNo + "&car=" + record.data.carId;
	            	global.openTabByMenuId('010301', param);
				}
			}
		});

		Ext.apply(this, {
	        bodyPadding: 12,
	        layout: 'column',
	        items: [memberCombo, {
	            xtype: 'button',
	            columnWidth: 2/9,
	            height: 32,
	            style: 'margin-left:10px',
	            text: '创建车主',
	            iconCls: 'user_add',
	            handler: function(){
	            	memberWindow.show();
	            }
	        }],
	        isReLayout : false
		});
		this.callParent(arguments);
	}
});


/**
 * 通知中心
 */
Ext.define('Tsingma.System.Home.NotifyCenter', {
	extend : 'Ext.panel.Panel',
	border : true,
	initComponent : function() {
		var me = this;
		
		Ext.apply(this, {
	        bodyPadding: 12,
	        height: 158,
//	        layout: 'column',
//	        items: [memberCombo, {
//	            xtype: 'button',
//	            columnWidth: 2/9,
//	            height: 32,
//	            style: 'margin-left:10px',
//	            text: '创建车主',
//	            iconCls: 'user_add',
//	            handler: function(){
//	            	memberWindow.show();
//	            }
//	        }],
	        html: '<div class="notice">1、微信个人中心页面全新改版。 <br>2、微信商城完成我的订单！</div>',
	        isReLayout : false
		});
		this.callParent(arguments);
	}
});


/**
 * 营业流水
 */
Ext.define('Tsingma.System.Home.LastOrderGrid', {
	extend : 'Ext.grid.Panel',
	plain : true,
	border : true,
	region : 'center',
	autoScroll : true,
	split : true,
	initComponent : function() {
		var me = this;

		Ext.define('OrderList', {
			extend : 'Ext.data.Model',
			fields : [ 'orderId', 'saleDate', 'carNumber', 'goodsName', 'price']
		});

		var store = Ext.create('Ext.data.Store', {
			model : 'OrderList',
			remoteSort : true,
			proxy : {
				type : 'ajax',
				url : '/order/queryLastOrderByDept.atc',
				extraParams : {
					limit : 5
				},
				reader : {
					root : 'orderList',
					totalProperty : 'totalCount'
				}
			}
		});

		var columns = [{
			text : "订单号",
			dataIndex : 'orderId',
			menuDisabled: true,
			sortable : false
		}, {
			text : "车牌号码",
			dataIndex : 'carNumber',
			menuDisabled: true,
			sortable : false
		}, {
			text : "服务项目",
			dataIndex : 'goodsName',
			menuDisabled: true,
			sortable : false
		}, {
			text : "金额",
			dataIndex : 'price',
			sortable : false,
			menuDisabled: true,
			renderer: Ext.util.Format.numberRenderer("0.00")
		}, {
			text : "时间",
			dataIndex : 'saleDate',
			menuDisabled: true,
			sortable : false
		}];

		Ext.apply(this, {
			id : 'LastOrder',
			store : store,
			flex: true,
			forceFit: true,
			columns : columns,
		});
		store.load();
		this.callParent(arguments);
	}
});


/**
 * 信息记录
 */
Ext.define('Tsingma.System.Home.IndexStatisticsGrid', {
	extend : 'Ext.grid.Panel',
	plain : true,
	border : true,
	region : 'center',
	autoScroll : true,
	split : true,
	initComponent : function() {
		var me = this;

		Ext.define('IndexStatistics', {
			extend : 'Ext.data.Model',
			fields : [ 'id', 'text', 'value']
		});

		var store = Ext.create('Ext.data.Store', {
			model : 'IndexStatistics',
			remoteSort : true,
			proxy : {
				type : 'ajax',
				url : '/shop/queryIndexStatistics.atc',
				reader : {
					root : 'statisList'
				}
			}
		});

		var columns = [{
			dataIndex: "id",
			hidden: true,
			menuDisabled: true,
			sortable : false
		},{
			text : "统计",
			dataIndex : 'text',
			menuDisabled: true,
			sortable : false
		}, {
			text : "结果",
			dataIndex : 'value',
			menuDisabled: true,
			sortable : false
		}];

		Ext.apply(this, {
			id : 'IndexStatistics',
			store : store,
			flex: true,
			forceFit: true,
			columns : columns,
			listeners: {
		    	itemclick: function(grid, record, item, index){
		    		if(!Ext.isEmpty(record)) {
		    			if(record.data.id == 1){
		            		parent.global.openTabByMenuId("010312", "");
		    			} else if(record.data.id == 2) {
		            		parent.global.openTabByMenuId("010601", "");
		    			} else if(record.data.id == 7) {
		    				parent.global.openTabByMenuId("01060801", "");
		    			}
		    		}
	            }
	        }
		});
		store.load();
		this.callParent(arguments);
	}
});


/**
 * 客户回访
 */
Ext.define('Tsingma.System.Home.IndexVisitGrid', {
	extend : 'Ext.grid.Panel',
	plain : true,
	border : true,
	region : 'center',
	autoScroll : true,
	split : true,
	initComponent : function() {
		var me = this;

		Ext.define('IndexStatistics', {
			extend : 'Ext.data.Model',
			fields : [ 'id', 'text', 'value']
		});

		var store = Ext.create('Ext.data.Store', {
			model : 'IndexStatistics',
			remoteSort : true,
			proxy : {
				type : 'ajax',
				url : '/shop/queryIndexVisit.atc',
				reader : {
					root : 'statisList'
				}
			}
		});

		var columns = [{
			dataIndex: "id",
			hidden: true,
			menuDisabled: true,
			sortable : false
		},{
			text : "回访类型",
			dataIndex : 'text',
			menuDisabled: true,
			sortable : false
		}, {
			text : "结果",
			dataIndex : 'value',
			menuDisabled: true,
			sortable : false
		}];

		Ext.apply(this, {
			id : 'IndexVisit',
			store : store,
			flex: true,
			forceFit: true,
			columns : columns,
			listeners: {
		    	itemclick: function(grid, record, item, index){
		    		parent.global.openTabByMenuId("010607", "");
	            }
	        }
		});
		store.load();
		this.callParent(arguments);
	}
});


/**
 * 快速开单
 */
Ext.define('Tsingma.System.Home.QuickCreateOrder', {
	extend : 'Ext.form.Panel',
	plain : true,
	border : true,
	region : 'center',
	autoScroll : true,
	split : true,
	minHeight: 169,
	initComponent : function() {
		var me = this;
		
		var areaShortCombo = Ext.create('Ext.form.ComboBox', {
			name: 'orderItem.carNumber',
			allowBlank: false, 
			labelAlign: 'right',
			triggerAction : 'all',
			mode : 'local',
			store: parent.GAreaShortStore,
			valueField : 'areaShort',
			displayField : 'areaShort',
			value: parent.II,
			editable : false,
            typeAhead: false,
            hideLabel: true
		});
		var areaCodeCombo = Ext.create('Ext.form.ComboBox', {
			name: 'orderItem.carNumber',
			allowBlank: false, 
			triggerAction : 'all',
			mode : 'local',
			store: parent.GAreaCodeStore,
			valueField : 'areaCode',
			displayField : 'areaCode',
			value: parent.JJ,
			editable : false,
			typeAhead: false,
            hideLabel: true
		});
	    var brandStore = Ext.create('Ext.data.Store', {
	        pageSize: 10,
        	autoLoad: true,
			fields : [{name: 'id'}, {name: 'shortCode'}, {name: 'brandName'}],  
			proxy:{
	            type: 'ajax',
	            url : '/base/queryCarBrandPage.atc',
	            reader : {
	                root: 'carBrandList',
	                totalProperty: 'totalCount'
	            }
	        }
	    });
	    Ext.define('ProjectSecondItem', {
		    extend: 'Ext.data.Model',
		    fields: [
		        {name: 'id', type: 'string'},
		        {name: 'name', type: 'string'},
		        {name: 'typeId', type: 'string'},
		        {name: 'price', type: 'float'}
		    ]
		});
		var ProjectSecondItemStore = new Ext.data.Store({
			autoLoad: false,
			model: ProjectSecondItem,
			proxy:{
	            type: 'ajax',
	            url : '/goods/queryAllGoodsHour.atc',
	            reader : {
	                root: 'hourList'
	            }
	        }
		});
		ProjectSecondItemStore.load();
		var ProjectSecondItemCombo = Ext.create('Ext.form.ComboBox', {
			store: ProjectSecondItemStore,
			name: 'orderItem.goodsId',
			valueField: 'id',
			displayField: 'name',
			queryMode: 'local',
	        editable : false,
	        allowBlank: false,
//	        selectOnFocus : true,
	        emptyText:'请选择项目...', 
//	        typeAhead: true,
	        triggerAction: 'all',
	        lastQuery: '',
	        listeners: {
	        	select: function(cb, records){
	                var record = records[0];
	                if(!Ext.isEmpty(record)){
	                	Ext.getCmp("price").setValue(record.data.price);
	                	Ext.getCmp("unitPrice").setValue(record.data.price);
	                	Ext.getCmp("goodsName").setValue(record.data.name);
	                }
	        	}
	        }
		});
		
		var EmployeeStore = Ext.create('Ext.data.Store', {
	        fields: ['id', 'name', 'isGroup'],
	        proxy: {
	            type: 'ajax',
	            url : '/shop/queryAllEmployeeByDept.atc',
	            reader : {
	                root: 'employeeList'
	            }
	        }
		});
		var SellerPanel = Ext.create('Ext.form.Panel', {
			layout: 'anchor',
            defaults: {
                anchor: '100%',
                layout: {
					type: 'hbox',
					defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
				}
            },
            bodyPadding: 10,
            items: [{
            	xtype: 'fieldset',
				itemId: 'SellerSet',
				title: '员工',
				collapsible: false,
                items: [{
    				xtype: 'container',
    				id:'SellerSet',
    				itemId: 'SellerContainer',
    				layout: 'column',
                    defaults: {
                    	columnWidth: 1/3,
                    	margin: 5,
                    }, 
                    defaultType: 'checkbox'
                }]
            },{
            	xtype: 'fieldset',
				itemId: 'SellerGroupSet',
				title: '员工组',
				collapsible: false,
                items: [{
    				xtype: 'container',
    				id: 'SellerGroupSet',
    				itemId: 'SellerGroupContainer',
    				layout: 'column',
                    defaults: {
                    	columnWidth: 1/3,
                    	margin: 5,
                    }, 
                    defaultType: 'checkbox'
                }]
            }],
            buttons:[{
            	text : '确 定',
    			iconCls : 'accept',
    			handler : function() {
    				var employee = Ext.getCmp('SellerSet').items;
    				var group = Ext.getCmp('SellerGroupSet').items;
                    var value = '', display = '';
                    for(var i = 0; i < employee.length; i++){
                        if(employee.get(i).checked){
                        	value += employee.get(i).inputValue + ",";
                        	display += employee.get(i).boxLabel + ",";
                        }
                    }
                    for(var i = 0; i < group.length; i++){
                        if(group.get(i).checked){
                        	value += group.get(i).inputValue + ",";
                        	display += group.get(i).boxLabel + ",";
                        }
                    }
                    Ext.getCmp("seller").setValue(value);
                    Ext.getCmp("sellerDisplay").setValue(display);
                    SellerWindow.hide();
    			}
            }]
		});
		var PerformerPanel = Ext.create('Ext.form.Panel', {
			layout: 'anchor',
            defaults: {
                anchor: '100%',
                layout: {
					type: 'hbox',
					defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
				}
            },
            bodyPadding: 10,
            items: [{
            	xtype: 'fieldset',
				itemId: 'PerformerSet',
				title: '员工',
				collapsible: false,
                items: [{
    				xtype: 'container',
    				id:'PerformerSet',
    				itemId: 'PerformerContainer',
    				layout: 'column',
                    defaults: {
                    	columnWidth: 1/3,
                    	margin: 5,
                    }, 
                    defaultType: 'checkbox'
                }]
            },{
            	xtype: 'fieldset',
				itemId: 'PerformerGroupSet',
				title: '员工组',
				collapsible: false,
                items: [{
    				xtype: 'container',
    				id: 'PerformerGroupSet',
    				itemId: 'PerformerGroupContainer',
    				layout: 'column',
                    defaults: {
                    	columnWidth: 1/3,
                    	margin: 5,
                    }, 
                    defaultType: 'checkbox'
                }]
            }],
            buttons:[{
            	text : '确 定',
    			iconCls : 'accept',
    			handler : function() {
    				var employee = Ext.getCmp('PerformerSet').items;
    				var group = Ext.getCmp('PerformerGroupSet').items;
                    var value = '', display = '';
                    for(var i = 0; i < employee.length; i++){
                        if(employee.get(i).checked){
                        	value += employee.get(i).inputValue + ",";
                        	display += employee.get(i).boxLabel + ",";
                        }
                    }
                    for(var i = 0; i < group.length; i++){
                        if(group.get(i).checked){
                        	value += group.get(i).inputValue + ",";
                        	display += group.get(i).boxLabel + ",";
                        }
                    }
                    Ext.getCmp("performer").setValue(value);
                    Ext.getCmp("performerDisplay").setValue(display);
                    PerformerWindow.hide();
    			}
            }]
		});
		var PerformerWindow = Ext.create('Ext.Window',{
			layout : 'fit',
			width : 400,
			height : 280,
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
			items : [PerformerPanel]
		});
		var SellerWindow = Ext.create('Ext.Window',{
			layout : 'fit',
			width : 400,
			height : 280,
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
			items : [SellerPanel]
		});
		EmployeeStore.on('load', function(){
			if(!Ext.isEmpty(EmployeeStore)){
				Ext.each(EmployeeStore.data.items, function(employee){
					if(!employee.data.isGroup){
						SellerPanel.getComponent("SellerSet").getComponent("SellerContainer").add([{
							boxLabel: employee.data.name, inputValue: employee.data.id
						}]);
						PerformerPanel.getComponent("PerformerSet").getComponent("PerformerContainer").add([{
							boxLabel: employee.data.name, inputValue: employee.data.id
						}]);
					} else {
						SellerPanel.getComponent("SellerGroupSet").getComponent("SellerGroupContainer").add([{
							boxLabel: employee.data.name, inputValue: employee.data.id
						}]);
						PerformerPanel.getComponent("PerformerGroupSet").getComponent("PerformerGroupContainer").add([{
							boxLabel: employee.data.name, inputValue: employee.data.id
						}]);
						
					}
				});
			}
		});
		EmployeeStore.load();
		function submitQuickOrder(){
			if(me.isValid()){
				Ext.MessageBox.show({
					title: '请稍等',
					msg: '正在提交订单 ...',
					width: 200,
					progress: true,
					closable: false
		        });
				Ext.getCmp("carNum").setValue(areaShortCombo.getValue()+areaCodeCombo.getValue()+Ext.getCmp("orderCarNumber").getValue());
				me.submit({
					waitTitle : '提示',
					method : 'POST',
					url: '/order/submitQuickOrder.atc',
					waitMsg : '正在处理数据,请稍候...',
					success : function(form, action) {
						var result = action.result;
						Ext.MessageBox.hide();
						if(result.success){
							Ext.MessageBox.show({
					            title: '结果',
					            msg: result.msg,
					            buttons: Ext.MessageBox.YESNO,
					            buttonText:{
					                yes: "打印订单", 
					                no: "继续开单" 
					            },
					            fn: afterSubmit
					        });
						} else 
							Ext.MessageBox.show({
					           title: '提示',
					           msg: result.msg,
					           buttons: Ext.MessageBox.OK,
					           icon: Ext.MessageBox.ERROR
			                });
					},
					failure : function(form, action) {
						var msg = action.result.msg;
						Ext.MessageBox.alert('提示', msg);
					}
				});
			}
		}
		
		function afterSubmit(btn){
			if(btn == 'yes') {
				Ext.example.msg("提示", "建设中。。。");
			} else if(btn == 'no') {
				me.getForm().reset();
				areaShortCombo.setValue(parent.II);
				areaCodeCombo.setValue(parent.JJ);
			}
		}
		
		Ext.apply(this, {
			layout: 'anchor',
			defaults: {
	            anchor: '100%',
	            layout: 'hbox',
	            xtype:'fieldcontainer'
	        },
	        bodyPadding: 10,
			border : true,
			items:[{
				defaults: {flex: 1, xtype:'textfield', margin: '10 0 0 0'},
				items: [
				        areaShortCombo, areaCodeCombo,
				        {id:'orderCarNumber', name: 'orderItem.carNumber', allowBlank: false, blankText:'车牌号不能为空!'},
				        {name: 'orderHead.orderType', xtype:'hiddenfield',value:'6'},	//订单类型为散客开单
		        	    {name: 'orderHead.memId', xtype:'hiddenfield',value:0},
		        	    {name: 'orderHead.carId', xtype:'hiddenfield',value:0},
		        	    {name: 'orderHead.carNumber', id:'carNum', xtype:'hiddenfield'},
		        	    {name: 'orderHead.oprice', id:'oprice', xtype:'hiddenfield'},
		        	    {name: 'orderHead.aprice', id:'aprice', xtype:'hiddenfield'},
		        	    {name: 'orderHead.pcash', id:'pcash', xtype:'hiddenfield'},
		        	    {name: 'orderItem.goodsName', id:'goodsName', xtype:'hiddenfield'},
		        	    {name: 'orderItem.unitPrice', id:'unitPrice', xtype:'hiddenfield'},
		        	    {name: 'orderItem.seller', id:'seller', xtype:'hiddenfield'},
		        	    {name: 'orderItem.performer', id:'performer',  xtype:'hiddenfield'}
			    ]
			},{
				defaults: {flex: 1, xtype:'numberfield', margin: '10 0 0 0'},
				items: [ProjectSecondItemCombo,
				        {id:'price', name:'orderItem.price', allowBlank: false,
							listeners: {
		                    	change: function(field, newValue, oldValue){
		                    		Ext.getCmp("oprice").setValue(field.value);
		                    		Ext.getCmp("aprice").setValue(field.value);
		                    		Ext.getCmp("pcash").setValue(field.value);
		                        }
		                    }
				        }
			    ]
			},{
				defaults: {flex: 1, xtype:'textfield', margin: '10 0 0 0'},
				items:[{
					id:'sellerDisplay',
					name: 'orderItem.carNumber',
					emptyText: '选择接单人',
					listeners: {
			            focus: function(text, event) {
			            	PerformerWindow.hide();
			            	SellerWindow.setTitle("选择接单人");
			            	SellerWindow.show();
			            }
			        }
				},{
					id:'performerDisplay',
					name: 'orderItem.carNumber',
					emptyText: '选择施工人',
					listeners: {
			            focus: function(text, event) {
			            	SellerWindow.hide();
			            	PerformerWindow.setTitle("选择施工人");
			            	PerformerWindow.show();
			            }
			        }
				}]
			},{
				xtype: 'button',
				text: '立即开单',
				scale: 'medium',
				margin: '10 0 0 0',
				handler: function(){
					submitQuickOrder();
				}
			}],
	        isReLayout : false
		});
		this.callParent(arguments);
	}
});


/**
 * 未完成订单
 */
Ext.define('Tsingma.System.Home.IncompleteOrderGrid', {
	extend : 'Ext.grid.Panel',
	plain : true,
	border : true,
	region : 'center',
	autoScroll : true,
	split : true,
	initComponent : function() {
		var me = this;
		
		//应收、实收、定金、积分规则、会员记录
		var oprice = 0, aprice = 0, deposit = 0, pointRule = 0, memberRecord;
		
		Ext.define('Member', {
		    extend: 'Ext.data.Model',
		    fields: [
		        {name: 'memId', type: 'int'},
		        {name: 'vipNo', type: 'string'},
		        {name: 'deptCode', type: 'string'},
		        {name: 'name', type: 'string'},
		        {name: 'sex', type: 'string'},
		        {name: 'mobile', type: 'string'},
		        {name: 'grade', type: 'string'},
		        {name: 'point', type: 'int'},
		        {name: 'wechatNo', type: 'string'},
		        {name: 'wehcatHead', type: 'string'},
		        {name: 'wechatNick', type: 'string'},
		        {name: 'source', type: 'string'},
		        {name: 'balance', type: 'float'},
		        {name: 'birthday', type: 'string'},
		        {name: 'createTime', type: 'string'},
		        {name: 'sales', type: 'string'},
		        {name: 'remark', type: 'string'}
		    ]
		});

		Ext.define('OrderList', {
			extend : 'Ext.data.Model',
			fields : [ 'orderId', 'memId', 'carId', 'saleDate', 'carNumber', 'goodsName', 'oprice', 'price', 'name', 'mobile', 'status']
		});

		var store = Ext.create('Ext.data.Store', {
			model : 'OrderList',
			remoteSort : true,
			proxy : {
				type : 'ajax',
				url : '/order/queryIncompleteOrderByDept.atc',
				reader : {
					root : 'orderList'
				}
			}
		});

		var columns = [{
			text : "订单号",
			dataIndex : 'orderId',
			menuDisabled: true,
			sortable : false
		}, {
			text : "车主",
			dataIndex : 'name',
			menuDisabled: true,
			sortable : false
		}, {
			text : "车牌号码",
			dataIndex : 'carNumber',
			menuDisabled: true,
			sortable : false
		}, {
			text : "电话",
			dataIndex : 'mobile',
			menuDisabled: true,
			sortable : false
		}, {
			text : "服务项目",
			dataIndex : 'goodsName',
			menuDisabled: true,
			sortable : false
		}, {
			text : "定金",
			dataIndex : 'price',
			menuDisabled: true,
			sortable : false,
			renderer: Ext.util.Format.numberRenderer("0.00")
		}, {
			text : "时间",
			menuDisabled: true, 
			sortable: false,
			dataIndex : 'saleDate',
		}, {
			text : "状态",
			menuDisabled: true, 
			sortable: false,
			hidden : true,
			dataIndex : 'status',
		}, {
			text : "通知取车",
			xtype: 'actioncolumn',
			menuDisabled: true, 
			sortable: false,
			width: 60, 
			align: 'center',
			items: [{
          		iconCls: 'bell',
                tooltip: '通知取车',
                handler: function(grid, rowIndex, colIndex) {
                	var rec = grid.getStore().getAt(rowIndex);
                	if(!Ext.isEmpty(rec) && rec.data.status == 2){
                		Ext.Ajax.request({
                			url : '/order/noticeTakeOrderCar.atc',
                			params : {
                				orderNo : rec.get("orderId").trim()
                			},
                			success : function(resp, opts) {
                				var result = Ext.decode(resp.responseText);
                				if(result.success){
                					Ext.example.msg('提示', result.msg);
                					store.reload();
                				}
                			},
                			failure : function(resp, opts) {
                				Ext.Msg.alert('提示', "通知短信发送出错！");
                			}
                		});
                	}
                },
                getClass: function(metadata, r){
                	if(r.record.raw.status == 2)
                		return 'bell';
                	else
                		return 'bell2';
                }
          	}]
		}, {
			text : "结算",
			xtype: 'actioncolumn',
			menuDisabled: true, 
			sortable: false,
			width: 40, 
			align: 'center',
			items: [{
          		iconCls: 'money',
                tooltip: '结算订单',
                handler: function(grid, rowIndex, colIndex) {
                	var rec = grid.getStore().getAt(rowIndex);
                	global.openSettlementTab(rec.get("orderId"), rec.get("carId"));
                }
          	}]
		}, {
			text : '删除',
			xtype: 'actioncolumn',
			menuDisabled: true, 
			sortable: false,
			width: 40, 
			align: 'center',
			items: [{
          		iconCls: 'cross',
                tooltip: '删除订单',
                handler: function(grid, rowIndex, colIndex) {
                    var rec = grid.getStore().getAt(rowIndex);
                    deleteOrderByNo(rec.get("orderId"));
                }
          	}]
		}];
		
		//删除订单
		function deleteOrderByNo(orderId){
			Ext.Msg.confirm('请确认', '确定要删除整张订单吗?', function(btn, text) {
				if(btn == 'yes'){
					if(!Ext.isEmpty(orderId)){
						Ext.Ajax.request({
							url : '/order/deleteOrderByNo.atc',
							async: false,
							params : {
								orderNo : orderId,
							},
							success : function(resp, opts) {
								var result = Ext.decode(resp.responseText);
								if(result.success){
									Ext.example.msg("提示", result.msg + "");
									store.reload();
								}
							},
							failure : function(resp, opts) {
								var result = Ext.decode(resp.responseText);
				                Ext.Msg.alert('提示', "获取项目价格信息失败！");
							}
						});
					}
				}
			});
		}
		
		Ext.apply(this, {
			id : 'IncompleteOrder',
			store : store,
			flex: true,
			forceFit: true,
			columns : columns,
		});
		store.load();
		this.callParent(arguments);
	}
});

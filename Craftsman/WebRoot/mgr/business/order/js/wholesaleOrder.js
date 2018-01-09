/**
 * 批发（暂没用）
 */
Ext.require([
    'Ext.ux.DataTip',
    'Ext.ux.form.SearchField'
]);
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	//定义、接收会员号参数
	var vipNo;
	if(typeof(param) != 'undefined') vipNo = param;

	var memberRecord, memberGrade;	//会员基本信息,会员等级基本信息
	var orderType = "1", receivable = 0, actual = 0;	//订单类型1普通订单, 应收, 实收
	
	//////////////////////////////////////////////////////////////   订单部分     //////////////////////////////////////////////////////////////
	
	var EmployeeSingleStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'name'],
        proxy: {
            type: 'ajax',
            url : '/shop/queryEmployeeByDept.atc',
            reader : {
                root: 'employeeList'
            }
        }
	});
	EmployeeSingleStore.load();
	
	var EmployeeSingleCombo = Ext.create('Ext.form.ComboBox', {
		labelWidth: 50, 
		labelAlign:'right',
		allowBlank: false,
		store: EmployeeSingleStore,
        enforceMaxLength: true,
        fieldLabel: '开单人',
        width: 200,
        labelWidth: 65,
        listConfig: {
            minWidth: null
        },
        valueField: 'name',
        displayField: 'name',
        queryMode: 'local',
        editable : false,
        forceSelection: true
	});
	
	//订单抬头
	var OrderHead = new Ext.form.FormPanel({
		region: 'north',
		height : 40,
		border: false,
		items : [{
			layout : 'column',
			border : false,
			margin: '10 10 0 10',
			items : [{
				columnWidth : .15,
				border : false,
				xtype: 'toolbar',
				style:{
					padding: '0',
					marginLeft: '20'
				},
				items: [{
				    xtype:'splitbutton',
				    text: '批发订单',
				    iconCls: 'application_view_icons',
				    menu: [
				           {text: '普通订单', value:'normal', handler: OrderTypeChange},
				           {text: '换货订单', value:'change', handler: OrderTypeChange},
				           {text: '退货订单', value:'return', handler: OrderTypeChange},
				           {text: '会员充值', value:'recharge', handler: OrderTypeChange}
				    ]
				}]
			},{
				columnWidth : .35,
				border : false,
				items:[{
					xtype: 'textfield'
				}]
			},{
				columnWidth : .27,
				border : false,
				items : [EmployeeSingleCombo]
			},{
				columnWidth : .23,
				border : false,
				items : [{
					fieldLabel : '消费日期',
					id: 'saleDate',
					width: 170,
					labelWidth : 60,
					labelAlign: 'right',
					xtype : 'datefield',
					format: 'Y/m/d',
					maxValue: new Date(),
					value: new Date()
				}]
			}]
		}]
	});
	
	var EmployeeStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'name'],
        proxy: {
            type: 'ajax',
            url : '/shop/queryAllEmployeeByDept.atc',
            reader : {
                root: 'employeeList'
            }
        }
	});
	EmployeeStore.load();
	
	var EmployeeRenderer = function(value, cellmeta, record) {
		if(!Ext.isEmpty(value)){
			if(value.indexOf(",") > 0){
				var names = "";
				var ids = value.split(",");
				for (index in ids){
					names += EmployeeStore.findRecord("id", ids[index].trim()).data.name + ',';
				}
				return names;
			} else 
				return EmployeeStore.findRecord("id", value.trim()).data.name;
		} else
			return "";
    };
	
	Ext.define('ProductModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'itemNo', type: 'string'},
	        {name: 'goodsId', type: 'string'},
	        {name: 'goodsType', type: 'string'},
	        {name: 'goodsName', type: 'string'},
	        {name: 'unitPrice', type: 'float'},
	        {name: 'number', type: 'int'},
	        {name: 'discount', type: 'float'},
	        {name: 'price', type: 'float'}
	    ]
	});

	
	var StockStore = Ext.create('Ext.data.Store', {
		fields: ['goodsId', 'name', 'number', 'price', 'position'],
		proxy:{
            type: 'ajax',
            url : '/goods/queryGoodsStockByDept.atc',
            reader : {
                root: 'stockList',
                totalProperty: 'totalCount'
            }
        }
    });
	var StockCombo = Ext.create('Ext.form.ComboBox', {
        store: StockStore,
        labelWidth : 40,
        valueField: 'goodsId',
        displayField: 'name',
        typeAhead: false,
        hideTrigger:true,
        width: 300,
        queryParam: 'keyword',	//搜索参数字段
        minChars: 1,
        listeners : {
        	select: function(cb, records) {
            	var record = records[0];
            	var rowRecord = ProductGrid.getSelectionModel().getLastSelected();
            	rowRecord.data.unitPrice = record.data.price;
            	rowRecord.data.price = record.data.price;
            	rowRecord.data.price = record.data.price;
            	ProductCellEditing.startEditByPosition({
            		row: Number(rowRecord.id.charAt(rowRecord.id.length - 1)) - 2,
        	        column: 4
        	    });
			}
		}
	});
	ProductStore = new Ext.data.Store({
        autoDestroy: true,
        model: ProductModel
    });
	var ProductCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	var ProductRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	var ProductTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '添加产品',
        	iconCls: 'add',
        	handler: function() {
        		insertProduct();
            }
        }]
    });
    var ProductRenderer = function(value, cellmeta, record) {
        var index = StockStore.find("goodsId", value);
        var record = StockStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else
        	displayText = record.data.name;
        return displayText;
    };
	var ProductColumns= [ProductRowNum,
	        {header : '产品',dataIndex: 'goodsId',menuDisabled: true,width: '35%', editor: StockCombo, renderer: ProductRenderer },
	        {header : '单价（元）',dataIndex: 'unitPrice',menuDisabled: true,xtype: 'numbercolumn', renderer: Ext.util.Format.numberRenderer("0.00")},
	        {header : '数量',dataIndex: 'number',menuDisabled: true,summaryType: 'sum',
	        	editor: {
	                xtype: 'numberfield',
	                allowBlank: false,
	                minValue: 0,
	                maxValue: 100000,
	                listeners: {
                    	change: function(field, newValue, oldValue){
                    		var rowRecord = ProductGrid.getSelectionModel().getLastSelected();
                    		var index = StockStore.find("goodsId", rowRecord.data.goodsId);
                            var record = StockStore.getAt(index);
                    		if(Number(newValue) > Number(record.data.number)){
                    			field.setValue(oldValue);
                    			Ext.MessageBox.show({
                 		           title: '提示',
                 		           msg: '库存不够！',
                 		           buttons: Ext.MessageBox.OK,
                 		           icon: Ext.MessageBox.INFO
                    			});
                    		}
                    		rowRecord.data.price = Number(rowRecord.data.unitPrice) * Number(newValue) * Number(rowRecord.data.discount) * 0.1;
                    		rowRecord.commit();
                        }
                    }
            }},
	        {header : '折扣',dataIndex: 'discount',menuDisabled: true,xtype: 'numbercolumn', 
            	editor: {
            		xtype: 'numberfield',
            		allowBlank: false,
                    minValue: 0,
                    maxValue: 10,
                    listeners: {
                    	change: function(field, newValue, oldValue){
                    		var rowRecord = ProductGrid.getSelectionModel().getLastSelected();
                    		rowRecord.data.price = Number(rowRecord.data.unitPrice) * Number(rowRecord.data.number) * Number(newValue) * 0.1;
                    		rowRecord.commit();
                        }
                    }
            }},
	        {header : '总价（元）',dataIndex: 'price',menuDisabled: true,xtype: 'numbercolumn', format:'0.00', summaryType: 'sum',
	        	editor: {xtype: 'numberfield', allowBlank: false, minValue: 0,
	        		listeners: {
                    	change: function(field, newValue, oldValue){
                    		var rowRecord = ProjectGrid.getSelectionModel().getLastSelected();
                    		rowRecord.commit();
                        }
                    }
	        	}
		    },
		    {dataIndex: 'number', hidden: true},
	        {xtype: 'actioncolumn',header : '操作',width: 50,align:'center',sortable: false,menuDisabled: true,
                items: [{
                    iconCls: 'delete',
                    tooltip: '删除',
                    scope: this,
                    handler: function(grid, rowIndex){
                    	ProductGrid.getStore().removeAt(rowIndex);
                    }
                }]
            }
	];
	var ProductGrid = Ext.create('Ext.grid.GridPanel', {
		region: 'center',
		minHeight: 430,
		title: '产品列表',
		columns : ProductColumns,
		store : ProductStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		frame : false,
		tbar : ProductTbar, 
		stripeRows: true,
		forceFit: true,
		plugins: [ProductCellEditing],
        features: [{
            ftype: 'summary',
            dock: 'bottom'
        }],
		viewConfig : {
			enableTextSelection: true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
	    }
	});
	ProductStore.on('update', function(){
		completeReceivable();
	});
	
	//订单提交
	var OrderSubmit = Ext.create('Ext.form.FormPanel',{
		region: 'south',
		title: '订单支付',
		iconCls: 'money_dollar',
		bodyPadding: 10,
		defaults: {
            anchor: '100%',
            labelWidth: 100
        },
		items : [{
			layout : 'column',
			border : false,
			items : [{
				columnWidth : .78,
				border : false,
				xtype: 'toolbar',
				style:{
					padding: '0',
					marginLeft: '20'
				},
				items: [{
				    xtype:'button',
				    text: '服务赠送',
				    iconCls: 'award_star_gold_2'
				},{
				    xtype:'button',
				    text: '卡券赠送',
				    iconCls: 'creditcards'
				}]
			},{
				columnWidth : .22,
				border : false,
				items : [{
					id : 'receivable',
					fieldLabel : '总消费',
					labelStyle : 'font-size: 18px;',
					fieldStyle : 'font-size: 20px; color:red;',
					labelAlign: 'right',
					labelWidth: 60,
					xtype: 'displayfield',
					value: '0.00',
					anchor : '100%'
				}]
			}]
		},{
			layout : 'column',
			items: [{
				columnWidth : .12,
				id: 'pbalance',
				labelWidth: 40,
				xtype: 'numberfield',
				fieldLabel: '余额',
				listeners: {
                	change: function(field, newValue, oldValue){
                		if(Number(newValue) > Number(memberRecord.data.balance)){
                			field.setValue(oldValue);
                			Ext.MessageBox.show({
             		           title: '提示',
             		           msg: '余额不够！',
             		           buttons: Ext.MessageBox.OK,
             		           icon: Ext.MessageBox.INFO
                			});
                		}
                		completeActual();
                    }
                }
			},{
				columnWidth : .14,
				id: 'pcash',
				padding: '0 0 0 10',
				labelWidth: 50,
				xtype: 'numberfield',
				fieldLabel: '+ 现金',
				listeners: {
                	change: function(field, newValue, oldValue){
                		completeActual();
                    }
                }
			},{
				columnWidth : .14,
				id: 'pcard',
				padding: '0 0 0 10',
				labelWidth: 50,
				xtype: 'numberfield',
				fieldLabel: '+ 刷卡',
				listeners: {
                	change: function(field, newValue, oldValue){
                		completeActual();
                    }
                }
			},{
				columnWidth : .14,
				id: 'pbill',
				padding: '0 0 0 10',
				labelWidth: 50,
				xtype: 'numberfield',
				fieldLabel: '+ 挂账',
				listeners: {
                	change: function(field, newValue, oldValue){
                		completeActual();
                    }
                }
			},{
				columnWidth : .14,
				id: 'pwechat',
				padding: '0 0 0 10',
				labelWidth: 50,
				xtype: 'numberfield',
				fieldLabel: '+ 微信',
				listeners: {
                	change: function(field, newValue, oldValue){
                		completeActual();
                    }
                }
			},{
				columnWidth : .14,
				id: 'pother',
				padding: '0 0 0 10',
				labelWidth: 50,
				xtype: 'numberfield',
				fieldLabel: '+ 其他',
				listeners: {
                	change: function(field, newValue, oldValue){
                		completeActual();
                    }
                }
			},{
				columnWidth : .18,
				id: 'actual',
				padding: '0 0 0 10',
				labelWidth: 18,
				labelStyle : 'font-size: 20px;',
				fieldStyle : 'font-size: 20px; color:red; text-align: left;',
				xtype: 'displayfield',
				labelSeparator:'',
				fieldLabel: '= ',
				value: '0.00'
			}]
		},{
			layout : 'column',
			items: [{
				columnWidth : .7,
				labelWidth: 40,
				id: 'orderRemark',
				xtype: 'textarea',
				rows: 2,
				margin: '5 0 0 0',
				fieldLabel: '备注'
			},{
				columnWidth : .3,
				border : false,
				bbar: ['->',{
				    xtype:'button',
				    text: '立即开单',
				    padding: '8',
				    iconCls: 'page_white_text'
				},{
				    xtype:'button',
				    padding: '8',
				    text: '提交订单',
				    iconCls: 'accept',
				    handler : function() {
				    	submitOrder();
				    }
				}]
			}]
		}]
	});
	
	/////////////////////////////////////////////////////////////  板块布局   //////////////////////////////////////////////////
	
	//订单信息
	var OrderInfo = Ext.create('Ext.panel.Panel',{
		region: 'center',
        id: 'orderInfo',
        title: '订单信息',
        iconCls:'application_view_columns',
        border: false,
        autoScroll: true,
        animCollapse: true,
        layout:'border',
        items:[OrderHead, ProductGrid, OrderSubmit]
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [OrderInfo] 
	});
	
	
	/////////////////////////////////////////////////////////////  页面初始化  //////////////////////////////////////////////////
	
	if(!Ext.isEmpty(vipNo)){
		completeMemberInfo(vipNo);
		MemberCombo.setValue(vipNo);
		//初始插入一条项目
		var record = new ProjectModel({
			itemNo: '',
			goodsId: '',
			goodsType: '1',
			unitPrice: 0,
			discount: 10,
			productPrice: 0,
			price: 0,
			performer: '',
			seller: ''
		});
		ProjectStore.insert(ProjectStore.getCount(), record);
		ProjectCellEditing.startEditByPosition({
			row: ProjectStore.getCount() - 1,
			column: 1
		});
	} else {
		MemberCombo.focus();
	}
	
	
	/////////////////////////////////////////////////////////////  方法部分  //////////////////////////////////////////////////
	
	//更改订单类型
	function OrderTypeChange(item){
		if(item.value == 'package'){
			parent.global.openTabByMenuId('010302', vipNo);
		} else if(item.value == 'change') {
			parent.global.openTabByMenuId('010303', vipNo);
		} else if(item.value == 'return') {
			parent.global.openTabByMenuId('010304', vipNo);
		} else if(item.value == 'recharge') {
			parent.global.openTabByMenuId('010305', vipNo);
		} else {
			parent.global.openTabByMenuId('010301', vipNo);
		}
    }
	
	//添加一行项目
	function insertProject(){
		if(!Ext.isEmpty(memberRecord)){
			var record = new ProjectModel({
				itemNo: '',
				goodsId: '',
				goodsType: '1',
				unitPrice: 0,
				discount: 10,
				productPrice: 0,
				price: 0,
				performer: '',
				seller: ''
			});
			ProjectStore.insert(ProjectStore.getCount(), record);
			ProjectCellEditing.startEditByPosition({
				row: ProjectStore.getCount() - 1,
				column: 1
			});
		} else 
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '请先选择会员！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
			});
	}
	
	//添加一行产品
	function insertProduct(){
		var record = new ProductModel({
			itemNo: '',
			goodsId: '',
			goodsType: '2',
			price: 0,
			number: 1,
			discount: 10,
			price: 0
		});
		ProductStore.insert(ProductStore.getCount(), record);
		ProductCellEditing.startEditByPosition({
			row: ProductStore.getCount() - 1, 
			column: 1
		});
	}
	
	//根据会员卡号加载会员信息
	function completeMemberInfo(vipNo){
		Ext.Ajax.request({
			url : '/member/queryMemberByVipNo.atc',
			params : {
				vipNo: vipNo
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result.success){
					if(Ext.isArray(result.memberList)){
						if(result.memberList.length == 1){
							var record = result.memberList[0];
							memberRecord = new Member({
						        id: record.id,
						        vipNo: record.vipNo,
						        deptCode: record.deptCode,
						        name: record.name,
						        mobile: record.mobile,
						        sex: record.sex,
						        grade: record.grade,
						        wechatNo: record.wechatNo,
						        wehcatHead: record.wechatHead,
						        wechatNick: record.wechatNick,
						        source: record.source,
						        balance: record.balance,
						        birthday: record.birthday,
						        createTime: record.createTime,
						        sales: record.sales,
						        remark: record.remark
						    });
							InfoPanel.loadRecord(memberRecord);
							completeMemberCarCombo(record.id);
							completeMemberGrade(record.grade);
						} else if(result.memberList.length == 0)
							Ext.MessageBox.show({
						           title: '提示',
						           msg: '没有找到该会员！',
						           buttons: Ext.MessageBox.OK,
						           icon: Ext.MessageBox.ERROR
		                    });
						else
							Ext.MessageBox.show({
						           title: '提示',
						           msg: '会员号码重复，请联系管理员！',
						           buttons: Ext.MessageBox.OK,
						           icon: Ext.MessageBox.ERROR
		                    });
					} else 
						Ext.MessageBox.show({
					           title: '提示',
					           msg: '获取会员信息错误！',
					           buttons: Ext.MessageBox.OK,
					           icon: Ext.MessageBox.ERROR
	                    });
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
	
	//根据会员卡号加载会员信息
	function completeCarInfo(vipNo){
		Ext.Ajax.request({
			url : '/member/queryMemberByVipNo.atc',
			params : {
				vipNo: vipNo
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result.success){
					if(Ext.isArray(result.memberList)){
						if(result.memberList.length == 1){
							var record = result.memberList[0];
							memberRecord = new Member({
						        id: record.id,
						        vipNo: record.vipNo,
						        deptCode: record.deptCode,
						        name: record.name,
						        mobile: record.mobile,
						        sex: record.sex,
						        grade: record.grade,
						        wechatNo: record.wechatNo,
						        wehcatHead: record.wechatHead,
						        wechatNick: record.wechatNick,
						        source: record.source,
						        balance: record.balance,
						        birthday: record.birthday,
						        createTime: record.createTime,
						        sales: record.sales,
						        remark: record.remark
						    });
							InfoPanel.loadRecord(memberRecord);
							completeMemberCarCombo(record.id);
						} else if(result.memberList.length == 0)
							Ext.MessageBox.show({
						           title: '提示',
						           msg: '没有找到该会员！',
						           buttons: Ext.MessageBox.OK,
						           icon: Ext.MessageBox.ERROR
		                    });
						else
							Ext.MessageBox.show({
						           title: '提示',
						           msg: '会员号码重复，请联系管理员！',
						           buttons: Ext.MessageBox.OK,
						           icon: Ext.MessageBox.ERROR
		                    });
					} else 
						Ext.MessageBox.show({
					           title: '提示',
					           msg: '获取会员信息错误！',
					           buttons: Ext.MessageBox.OK,
					           icon: Ext.MessageBox.ERROR
	                    });
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
	
	//根据会员ID加载车辆下拉框
	function completeMemberCarCombo(memberId){
		CarStore.load({
			params:{
				memberId: memberId
			}
		});
	}
	
	//加载会员等级信息
	function completeMemberGrade(grade) {
		Ext.Ajax.request({
			url : '/member/queryGradeByGrade.atc',
			params : {
				'grade.grade' : grade
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result.success){
					if(Ext.isArray(result.gradeList)){
						if(result.gradeList.length == 1){
							var record = result.gradeList[0];
							memberGrade = new MemberGrade({
								id: record.id,
								deptCode: record.deptCode,
								name: record.name,
								grade: record.grade,
								needPoint: record.needPoint,
								discount: record.discount,
								orderDiscount: record.orderDiscount
							});
						}
					}
				}		
			},
			failure : function(resp, opts) {
                Ext.Msg.alert('提示', "会员等级信息加载出错！");
			}
		});
	}
	
	//加载区域信息
	function LoadAreaInfo(areaId){
		Ext.Ajax.request({
			url : '/base/queryBaseAreaById.atc',
			params : {
				'area.areaId' : areaId
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				areaShortCombo.setValue(result.area.areaShort);
				areaCodeCombo.setValue(result.area.areaCode);
			},
			failure : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
                Ext.Msg.alert('提示', result.msg);
			}
		});
	}
	
	//根据小项完善当前行信息
	function completeProjectRow(row, record){
		row.data.unitPrice = record.data.price;
		row.data.goodsName = record.data.name;
		Ext.Ajax.request({
			url : '/member/queryGradePriceById.atc',
			async: false,
			params : {
				'gradePrice.goodsId' : record.data.id,
				'gradePrice.gradeId' : memberRecord.data.grade
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result.success){
					if(Ext.isArray(result.gradeList)){
						var grade = result.gradeList[0];
						row.data.discount = grade.mcount;
						row.data.price = (Number(grade.mcount) * Number(record.data.price) * 0.1).toFixed(2);
					} else {
						row.data.discount = memberGrade.data.discount;
						row.data.price = (Number(memberGrade.data.discount) * Number(record.data.price) * 0.1).toFixed(2);
					}
					row.commit();
				}
			},
			failure : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
                Ext.Msg.alert('提示', "获取项目价格信息失败！");
			}
		});
	}
	
	//计算应收
	function completeReceivable(){
		receivable = 0
		ProjectStore.each(function(record){
			receivable += Number(record.data.price);
		});
		ProductStore.each(function(record){
			receivable += Number(record.data.price)
		});
		Ext.getCmp("receivable").setValue(receivable.toFixed(2));
	}
	
	//计算实收
	function completeActual(){
		if(receivable == 0){
			Ext.MessageBox.show({
		           title: '提示',
		           msg: "当前没有任何消费项目！",
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
			});
		}
		actual = 0;
		actual = Number(Ext.getCmp("pbalance").getValue()) + Number(Ext.getCmp("pcash").getValue()) + Number(Ext.getCmp("pcard").getValue()) +
				 Number(Ext.getCmp("pbill").getValue()) + Number(Ext.getCmp("pwechat").getValue()) + Number(Ext.getCmp("pother").getValue());
		Ext.getCmp("actual").setValue(actual.toFixed(2));
	}
	
	//提交订单
	function submitOrder(){
		
		if(receivable == 0){
			Ext.MessageBox.show({
		           title: '提示',
		           msg: "当前没有任何消费项目！",
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
			});
			return false;
		} else if(actual == 0){
			Ext.MessageBox.show({
		           title: '提示',
		           msg: "实收金额不能为 0！",
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
			});
			return false;
		} else if(actual < receivable){
			Ext.MessageBox.show({
		           title: '提示',
		           msg: "实收金额不能小于应收金额！",
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
			});
			return false;
		} else if(Ext.isEmpty(memberRecord)){
			Ext.MessageBox.show({
				title: '提示',
				msg: "会员不能为空！",
				buttons: Ext.MessageBox.OK,
				icon: Ext.MessageBox.INFO
			});
			MemberCombo.focus();
			return false;
		} else if(Ext.isEmpty(CarCombo.getValue())){
			Ext.MessageBox.show({
				title: '提示',
				msg: "没有选择车辆！",
				buttons: Ext.MessageBox.OK,
				icon: Ext.MessageBox.INFO
			});
			CarCombo.focus();
			return false;
		} else if(Ext.isEmpty(EmployeeSingleCombo.getValue())){
			Ext.MessageBox.show({
		           title: '提示',
		           msg: "开单人不能为空！",
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
			});
			EmployeeSingleCombo.focus();
			return false;
		}
		
		var projects = ProjectStore.getModifiedRecords().slice(0);  
		var products = ProductStore.getModifiedRecords().slice(0);  
        var projectArray = [];
        var productArray = [];
        Ext.each(projects, function(item){  
        	projectArray.push(item.data);  
        });  
        Ext.each(products, function(item){  
        	productArray.push(item.data);  
        });  
        Ext.Ajax.request({  
            method:'POST',
            url: '/order/submitOrder.atc',
            params : {
            	'orderHead.orderType' : orderType,
            	'orderHead.vipNo' : memberRecord.data.vipNo,
            	'orderHead.carId' : CarStore.getAt(0).data.id,
            	'orderHead.saleDate' : Ext.getCmp("saleDate").getValue(),
            	'orderHead.oprice' : Ext.getCmp("receivable").getValue(),
            	'orderHead.aprice' : Ext.getCmp("receivable").getValue(),
            	'orderHead.pbalance' : Ext.getCmp("pbalance").getValue(),
            	'orderHead.pcash' : Ext.getCmp("pcash").getValue(),
            	'orderHead.pcard' : Ext.getCmp("pcard").getValue(),
            	'orderHead.pbill' : Ext.getCmp("pbill").getValue(),
            	'orderHead.pwechat' : Ext.getCmp("pwechat").getValue(),
            	'orderHead.pother' : Ext.getCmp("pother").getValue(),
            	'orderHead.status' : 1,
            	'orderHead.creater' : EmployeeSingleCombo.getValue(),
            	'orderHead.remark' : Ext.getCmp("orderRemark").getValue(),
            	projectString : Ext.encode(projectArray),
            	productString : Ext.encode(productArray)
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result.success){
					Ext.MessageBox.show({
			            title: '结果',
			            msg: result.msg,
			            buttons: Ext.MessageBox.YESNOCANCEL,
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
			failure : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
                Ext.Msg.alert('提示', result.msg);
			}
        });
	}
	
	function afterSubmit(btn){
		if(btn == 'yes'){
			Ext.example.msg("提示", "建设中。。。");
		} else if(btn == 'no'){
			location.reload();
		} else {
			location.reload();
		}
	}
	
	
    
});
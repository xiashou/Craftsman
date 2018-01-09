/**
 * 综合数据报表
 */
Ext.onReady(function() {

	Ext.QuickTips.init();
	
	var aprice = 0;
	
	///////////////////////////// 大项统计 //////////////////////////////////
	
	Ext.define('OrderModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'typeName2', type: 'string'},
	        {name: 'itemNo2', type: 'int'},
	        {name: 'price2', type: 'float'}
	    ]
	});
	
	Ext.define('PayModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'payment3', type: 'string'},
	        {name: 'price3', type: 'float'}
	    ]
	});
	
	Ext.define('BillModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'remark4', type: 'string'},
	        {name: 'payType4', type: 'string'}
	    ]
	});
	
	Ext.define('StoreModel', {
		extend: 'Ext.data.Model',
		fields: [
	         {name: 'remark5', type: 'string'},
	         {name: 'payType5', type: 'string'}
	    ]
	});
	
	Ext.define('OtherModel', {
		extend: 'Ext.data.Model',
		fields: [
	         {name: 'remark6', type: 'string'},
	         {name: 'payType6', type: 'string'}
	    ]
	});
	
	var store1 = Ext.create('Ext.data.Store', {
		fields : [ 'typeId', 'typeName', 'itemNo', 'price'],
		proxy : {
			type : 'ajax',
			url : '/fico/queryOverallProject.atc',
			reader : {
				root : 'itemList'
			}
		}
	});
	
	var store2 = Ext.create('Ext.data.Store', {
		fields : [ 'typeName', 'itemNo', 'price'],
		proxy : {
			type : 'ajax',
			url : '/fico/queryOverallOrder.atc',
			reader : {
				root : 'itemList'
			}
		}
	});
	
	var store3 = Ext.create('Ext.data.Store', {
		fields : [ 'poffset', 'pdeposit', 'pbalance', 'pcash', 'pcard', 'ptransfer', 'pwechat', 'palipay', 'pbill'],
		proxy : {
			type : 'ajax',
			url : '/fico/queryOverallPay.atc',
			reader : {
				root : 'headList'
			}
		}
	});
	
	var store4 = Ext.create('Ext.data.Store', {
		fields : [ 'remark', 'payType'],
		proxy : {
			type : 'ajax',
			url : '/fico/queryOverallBill.atc',
			reader : {
				root : 'headList'
			}
		}
	});
	
	var store5 = Ext.create('Ext.data.Store', {
		fields : [ 'remark', 'payType'],
		proxy : {
			type : 'ajax',
			url : '/fico/queryOverallStore.atc',
			reader : {
				root : 'headList'
			}
		}
	});
	
	var store6 = Ext.create('Ext.data.Store', {
		fields : [ 'remark', 'payType'],
		proxy : {
			type : 'ajax',
			url : '/fico/queryOverallOther.atc',
			reader : {
				root : 'headList'
			}
		}
	});
	
	var columns = [{text : '大项统计',
    		columns : [{
    			header : '大项',
    			dataIndex : 'typeName',
    		}, {
    			header : '次数(次)',
    			dataIndex : 'itemNo',
    			summaryType: function(records){
                    var i = 0, length = records.length, total = 0, record;
                    for (; i < length; ++i) {
                        record = records[i];
                        total += Number(Ext.isEmpty(record.data.itemNo)?'0':record.data.itemNo);
                    }
                    return total;
                }
    		}, {
    			header : '销售金额',
    			dataIndex : 'price',
    			renderer: Ext.util.Format.numberRenderer("0.00"),
    			summaryType: 'sum',
    			summaryType: function(records){
                    var i = 0, length = records.length, total = 0, record;
                    for (; i < length; ++i) {
                        record = records[i];
                        total += Number(Ext.isEmpty(record.data.price)?'0':record.data.price);
                    }
                    return total;
                }
    		}]
	}];
	
	var tbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
		items : [ {
			xtype : 'datefield',
			fieldLabel: '时间段',
			labelWidth: 50,
			id : 'start',
			width: 170,
			format: 'Y/m/d',
			value: new Date()
		},'--', {
			xtype : 'datefield',
			id : 'end',
			width: 120,
			format: 'Y/m/d',
			value: new Date()
		}, {
			xtype : 'button',
			id : 'search',
			text : ' 查 询 ',
			iconCls : 'preview',
			handler : function() {
				store1.load({
					params:{
						'orderItem.startDate': parent.dateFormat(Ext.getCmp("start").getValue()),
						'orderItem.endDate': parent.dateFormat(Ext.getCmp("end").getValue())
					}
				});
			}
		}]
	});

	var grid = new Ext.grid.GridPanel({
		title : '综合数据',
		columns : columns,
		store: store1,
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : tbar,
		stripeRows : true,
		tools : [ {
			type : 'help',
			tooltip : '操作指引',
			callback : function(panel, tool, event) {
				parent.global.openSystemHelpTab();
			}
		} ],
		features: [{
            ftype: 'summary',
            dock: 'bottom'
        }],
		viewConfig : {
			enableTextSelection : true// 设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var orderTypeRenderer = function(value, cellmeta, record) {
        var index = parent.GOrderTypeStore.find("value", value);
        var record = parent.GOrderTypeStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else
        	displayText = record.data.text;
        return displayText;
    };
    
	columns.push({text: '营业数据',
		columns:[
			{text: "订单类型", dataIndex: "typeName2", renderer: orderTypeRenderer},
			{text: "数量(笔)", dataIndex: "itemNo2",summaryType: 'sum',
				summaryType: function(records){
                var i = 0, length = records.length, total = 0, record;
                for (; i < length; ++i) {
                    record = records[i];
                    total += Number(Ext.isEmpty(record.data.itemNo2)?'0':record.data.itemNo2);
                }
                return total;
            }},
		    {text: "销售金额", dataIndex: "price2", renderer: Ext.util.Format.numberRenderer("0.00"),summaryType: 'sum',
            	summaryType: function(records){
                    var i = 0, length = records.length, total = 0, record;
                    for (; i < length; ++i) {
                        record = records[i];
                        total += Number(Ext.isEmpty(record.data.price2)?'0':record.data.price2).toFixed(2) * 1;
                    }
                    return total;
            }}
		]
	},{
		text:'支付数据',
		columns:[
			{text: "支付方式", dataIndex: "payment3", renderer: orderTypeRenderer},
		    {text: "金额", dataIndex: "price3", renderer: Ext.util.Format.numberRenderer("0.00"),summaryType: 'sum',
            	summaryType: function(records){
                    var i = 0, length = records.length, total = 0, record;
                    for (; i < length; ++i) {
                        record = records[i];
                        total += Number(Ext.isEmpty(record.data.price3)?'0':record.data.price3).toFixed(2) * 1;
                    }
                    return total;
            }}
		]
	},{
		text:'挂账数据',
		columns:[
	         {text: "描述", dataIndex: "remark4"},
	         {text: "值", dataIndex: "payType4", renderer: Ext.util.Format.numberRenderer("0.00"),summaryType: 'sum',
            	summaryType: function(records){
                    var i = 0, length = records.length, total = 0, record;
                    for (; i < length; ++i) {
                        record = records[i];
                        total += Number(Ext.isEmpty(record.data.payType4)?'0':record.data.payType4).toFixed(2) * 1;
                    }
                    return total;
	         }}
	    ]
	},{
		text:'库存数据',
		columns:[
	         {text: "描述", dataIndex: "remark5"},
	         {text: "值", dataIndex: "payType5"}
	    ]
	},{
		text:'其他数据',
		columns:[
		     {text: "描述", dataIndex: "remark6"},
		     {text: "值", dataIndex: "payType6"}
		]
	}); 
	
	store1.on('load', function(){
		store2.load({
			params:{
				'orderItem.startDate': parent.dateFormat(Ext.getCmp("start").getValue()),
				'orderItem.endDate': parent.dateFormat(Ext.getCmp("end").getValue())
			}
		});
	});
	
	store2.on('load', function(){
		var i = 0;
		store2.each(function(rec){
			if(store1.getCount() >= (i+1)){
				store1.getAt(i).set('typeName2', rec.data.typeName);
				store1.getAt(i).set('itemNo2', rec.data.itemNo);
				store1.getAt(i).set('price2', rec.data.price);
			} else {
				var record = new OrderModel({
					typeName2: rec.data.typeName,
					itemNo2: rec.data.itemNo,
					price2: rec.data.price
				});
				store1.insert(store1.getCount(), record);
			}
			i++;
		});
		store3.load({
			params:{
				'orderHead.startDate': parent.dateFormat(Ext.getCmp("start").getValue()),
				'orderHead.endDate': parent.dateFormat(Ext.getCmp("end").getValue())
			}
		});
	});
	
	store3.on('load', function(){
		store3.each(function(rec){
			aprice = 0;
			if(store1.getCount() > 0){
				if(store1.getCount() >= 1){
					store1.getAt(0).set('payment3', '积分抵现');
					store1.getAt(0).set('price3', rec.data.poffset);
				} else {
					var record = new PayModel({
						payment3: '积分抵现',
						price3: rec.data.poffset,
					});
					store1.insert(store1.getCount(), record);
				}
				if(store1.getCount() >= 2){
					store1.getAt(1).set('payment3', '余额');
					store1.getAt(1).set('price3', rec.data.pbalance);
				} else {
					var record = new PayModel({
						payment3: '余额',
						price3: rec.data.pbalance,
					});
					store1.insert(store1.getCount(), record);
				}
				if(store1.getCount() >= 3){
					store1.getAt(2).set('payment3', '挂账');
					store1.getAt(2).set('price3', rec.data.pbill);
				} else {
					var record = new PayModel({
						payment3: '挂账',
						price3: rec.data.pbill,
					});
					store1.insert(store1.getCount(), record);
				}
				if(store1.getCount() >= 4){
					store1.getAt(3).set('payment3', '定金');
					store1.getAt(3).set('price3', rec.data.pdeposit);
				} else {
					var record = new PayModel({
						payment3: '定金',
						price3: rec.data.pdeposit,
					});
					store1.insert(store1.getCount(), record);
				}
				if(store1.getCount() >= 5){
					store1.getAt(4).set('payment3', '现金');
					store1.getAt(4).set('price3', rec.data.pcash);
				} else {
					var record = new PayModel({
						payment3: '现金',
						price3: rec.data.pcash,
					});
					store1.insert(store1.getCount(), record);
				}
				if(store1.getCount() >= 6){
					store1.getAt(5).set('payment3', '刷卡');
					store1.getAt(5).set('price3', rec.data.pcard);
				} else {
					var record = new PayModel({
						payment3: '刷卡',
						price3: rec.data.pcard,
					});
					store1.insert(store1.getCount(), record);
				}
				if(store1.getCount() >= 7){
					store1.getAt(6).set('payment3', '转账');
					store1.getAt(6).set('price3', rec.data.ptransfer);
				} else {
					var record = new PayModel({
						payment3: '转账',
						price3: rec.data.ptransfer,
					});
					store1.insert(store1.getCount(), record);
				}
				if(store1.getCount() >= 8){
					store1.getAt(7).set('payment3', '微信');
					store1.getAt(7).set('price3', rec.data.pwechat);
				} else {
					var record = new PayModel({
						payment3: '微信',
						price3: rec.data.pwechat,
					});
					store1.insert(store1.getCount(), record);
				}
				if(store1.getCount() >= 9){
					store1.getAt(8).set('payment3', '支付宝');
					store1.getAt(8).set('price3', rec.data.palipay);
				} else {
					var record = new PayModel({
						payment3: '支付宝',
						price3: rec.data.palipay,
					});
					store1.insert(store1.getCount(), record);
				}
				//统计实际收入
				aprice += (Number(rec.data.palipay) + Number(rec.data.pwechat) + Number(rec.data.ptransfer) + 
						Number(rec.data.pcard) + Number(rec.data.pcash) + Number(rec.data.pdeposit));
			}
		});
		store4.load({
			params:{
				'orderHead.startDate': parent.dateFormat(Ext.getCmp("start").getValue()),
				'orderHead.endDate': parent.dateFormat(Ext.getCmp("end").getValue())
			}
		});
	});
	
	store4.on('load', function(){
		if(store1.getCount() > 0){
			var i = 0;
			store4.each(function(rec){
				if(store1.getCount() >= (i+1)){
					store1.getAt(i).set('remark4', rec.data.remark);
					store1.getAt(i).set('payType4', rec.data.payType);
				} else {
					var record = new BillModel({
						remark4: rec.data.remark,
						payType4: rec.data.payType,
					});
					store1.insert(store1.getCount(), record);
				}
				//统计实际收入
				aprice += Number(rec.data.payType);
				i++;
			});
		}
		store5.load();
	});
	
	store5.on('load', function(){
		var i = 0;
		store5.each(function(rec){
			if(store1.getCount() >= (i+1)){
				store1.getAt(i).set('remark5', rec.data.remark);
				store1.getAt(i).set('payType5', rec.data.payType);
			} else {
				var record = new StoreModel({
					remark5: rec.data.remark,
					payType5: rec.data.payType,
				});
				store1.insert(store1.getCount(), record);
			}
			i++;
		});
		store6.load({
			params:{
				'orderHead.startDate': parent.dateFormat(Ext.getCmp("start").getValue()),
				'orderHead.endDate': parent.dateFormat(Ext.getCmp("end").getValue())
			}
		});
	});
	
	store6.on('load', function(){
		if(store1.getCount() > 0){
			var i = 0;
			store6.each(function(rec){
				if(store1.getCount() >= (i+1)){
					store1.getAt(i).set('remark6', rec.data.remark);
					store1.getAt(i).set('payType6', rec.data.payType);
				} else {
					var record = new OtherModel({
						remark6: rec.data.remark,
						payType6: rec.data.payType,
					});
					store1.insert(store1.getCount(), record);
				}
				i++;
			});
			if(store1.getCount() >= (i+1)){
				store1.getAt(i).set('remark6', '总收入');
				store1.getAt(i).set('payType6', aprice);
			} else {
				var record = new OtherModel({
					remark6: '总收入',
					payType6: aprice,
				});
				store1.insert(store1.getCount(), record);
			}
		}
		grid.reconfigure(store1, columns);
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
	
	store1.load({
		params:{
			'orderItem.startDate': parent.dateFormat(Ext.getCmp("start").getValue()),
			'orderItem.endDate': parent.dateFormat(Ext.getCmp("end").getValue())
		}
	});

});
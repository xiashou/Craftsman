/**
 * 提成设置
 */
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var typeRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var hourRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var mateRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var mateTypeRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var hourTypeStore = Ext.create('Ext.data.Store', {
		fields: ['id','typeName'],
		proxy: {
			type: 'ajax',
			url : '/goods/queryAllHourType.atc',
			reader : {
				root: 'hourTypeList',
				totalProperty: 'totalCount'
			}
		}
	});
	hourTypeStore.load();
	
	var mateTypeStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'typeName'],
		proxy: {
			type: 'ajax',
			url : '/goods/queryAllMaterialType.atc',
			reader : {
				root: 'materialTypeList',
	    	  totalProperty: 'totalCount'
			}
		}
	});
	mateTypeStore.load();
	
	var typeStore = Ext.create('Ext.data.Store', {
		  fields: ['typeId', 'commission'],
		  proxy: {
		      type: 'ajax',
		      url : '/shop/queryTypeCommission.atc',
		      reader : {
		    	  root: 'tcList'
		      }
		  }
	});
	
	var hourStore = Ext.create('Ext.data.Store', {
		  fields: ['goodsId', 'goodsName', 'price', 'commission', 'cPrice', 'type'],
		  proxy: {
		      type: 'ajax',
		      url : '/shop/queryHourCommission.atc',
		      reader : {
		    	  root: 'cList'
		      }
    	}
    });
	
	var mateStore = Ext.create('Ext.data.Store', {
		fields: ['goodsId', 'goodsName', 'price', 'commission', 'cPrice', 'type'],
        proxy: {
            type: 'ajax',
            url : '/shop/queryMaterialCommission.atc',
            reader : {
                root: 'cList'
            }
        }
	});
	
	var hourTypeRenderer = function(value, cellmeta, record) {
        var index = hourTypeStore.find("id", value);
        var record = hourTypeStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else
        	displayText = record.data.typeName;
        return displayText;
    };
    
    var mateTypeRenderer = function(value, cellmeta, record) {
        var index = mateTypeStore.find("id", value);
        var record = mateTypeStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else
        	displayText = record.data.typeName;
        return displayText;
    };
    
    var typeColumns= [typeRowNum,
          {header : '类型',dataIndex: 'typeId', width: 110, renderer: hourTypeRenderer},
          {header : '提成',dataIndex: 'commission', width: 100,editor: {xtype: 'textfield'}}
  	];
  	
  	var hourColumns= [hourRowNum,
  	                  {header : 'ID',dataIndex: 'goodsId', width: 60, hidden: true},
  	                  {header : '类型',dataIndex: 'type', width: 110, renderer: hourTypeRenderer},
  	                  {header : '项目',dataIndex: 'goodsName', width: 120},
  	                  {header : '价格',dataIndex: 'price', width: 80,renderer: Ext.util.Format.numberRenderer("0.00")},
  	                  {header : '提成',dataIndex: 'commission', width: 80,editor: {
  	                	  xtype: 'textfield',
  	                	  listeners: {
  	                		  change: function(field, newValue, oldValue){
  	                			  var rowRecord = hourGrid.getSelectionModel().getLastSelected();
  	                			  if(newValue.indexOf('%') > 0)
  	                			  	  rowRecord.data.cPrice = Number(Number(newValue.replace('%', '')) * Number(rowRecord.get('price') * 0.01)).toFixed(2);
	  	                  		  else
	  	                  			  rowRecord.data.cPrice = Number(newValue).toFixed(2);
  	                      		rowRecord.commit();
  	                          }
  	                      }
  	                  }},
  	                  {header : '提成金额',dataIndex: 'cPrice', width: 100}
  	];
  	
  	var mateTypeColumns= [mateTypeRowNum,
            {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
            {header : '类型名称',dataIndex: 'typeName', width: 150}
  	];

  	
  	var mateColumns= [mateRowNum,
  	                {header : 'ID',dataIndex: 'goodsId', width: 60, hidden: true},
	                  {header : '类型',dataIndex: 'type', width: 100, renderer: mateTypeRenderer},
	                  {header : '商品',dataIndex: 'goodsName', width: 120},
	                  {header : '零售价',dataIndex: 'price', width: 80,renderer: Ext.util.Format.numberRenderer("0.00")},
	                  {header : '提成',dataIndex: 'commission', width: 80,editor: {
	                	  xtype: 'textfield',
	                	  listeners: {
	                		  change: function(field, newValue, oldValue){
	                			  var rowRecord = mateGrid.getSelectionModel().getLastSelected();
	                			  if(newValue.indexOf('%') > 0)
	                			  	  rowRecord.data.cPrice = Number(Number(newValue.replace('%', '')) * Number(rowRecord.get('price') * 0.01)).toFixed(2);
	  	                  		  else
	  	                  			  rowRecord.data.cPrice = Number(newValue).toFixed(2);
	                      		rowRecord.commit();
	                          }
	                      }
	                  }}
//	                  {header : '提成金额',dataIndex: 'cPrice', width: 100}
  	];
  	
  	var typeTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '保存全部',
        	iconCls: 'accept',
        	hidden : !parent.haveActionMenu('SaveType'),
        	handler: function(){
        		var jsonArray = [];  
                Ext.each(typeStore.data.items, function(item){  
                    jsonArray.push(item.data);
                });
                Ext.MessageBox.show({
        			title: '请稍等',
        			msg: '正在保存配置 ...',
        			width: 200,
        			closable: false
                });
                Ext.Ajax.request({
                    method:'POST',
                    url: '/shop/saveTypeCommission.atc',
                    params : {
						jsonStr: Ext.encode(jsonArray)
					},
					success : function(resp, opts) {
						Ext.MessageBox.hide();
						var result = Ext.decode(resp.responseText);
						if(result.success){
							Ext.example.msg('提示', result.msg);
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
						Ext.MessageBox.hide();
						var result = Ext.decode(resp.responseText);
                        Ext.Msg.alert('提示', result.msg);
					}
                });
        	}
        }]
    });
  	
	var hourTbar = Ext.create('Ext.toolbar.Toolbar', {
			region : 'north',
			border : false,
	        items: [{
	        	xtype: 'textfield',
	        	id: 'hourComm',
	        	width: 60
	        },{
	        	xtype: 'button',
	        	text: '一键设置提成',
	        	iconCls: 'coins_delete',
	        	handler: function(){
	        		var i = 0;
	        		var comm = Ext.getCmp('hourComm').getValue();
	            	hourStore.each(function(rec){
	            		if(comm.indexOf('%') > 0)
	            			hourStore.getAt(i).set('cPrice', Number(Number(comm.replace('%', '')) * Number(rec.get('price') * 0.01)).toFixed(2));
	            		else
	            			hourStore.getAt(i).set('cPrice', Number(comm).toFixed(2));
	            		hourStore.getAt(i).set('commission', comm);
	            		i++;
	            	});
	        	}
	        },{
	        	xtype: 'button',
	        	text: '保存全部',
	        	iconCls: 'accept',
	        	hidden : !parent.haveActionMenu('SaveProject'),
	        	handler: function(){
	        		var jsonArray = [];  
	                Ext.each(hourStore.data.items, function(item){  
	                    jsonArray.push(item.data);
	                });
	                Ext.MessageBox.show({
	        			title: '请稍等',
	        			msg: '正在保存配置 ...',
	        			width: 200,
	        			closable: false
	                });
	                Ext.Ajax.request({
	                    method:'POST',
	                    url: '/shop/saveCommission.atc',
	                    params : {
							jsonStr : Ext.encode(jsonArray)
						},
						success : function(resp, opts) {
							Ext.MessageBox.hide();
							var result = Ext.decode(resp.responseText);
							if(result.success){
								Ext.example.msg('提示', result.msg);
								hourStore.reload();
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
	        }]
	    });
	
	var mateTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'textfield',
        	id: 'mateComm',
        	width: 60
        },{
        	xtype: 'button',
        	text: '一键设置提成',
        	iconCls: 'coins_delete',
        	handler: function(){
        		var i = 0;
        		var comm = Ext.getCmp('mateComm').getValue();
        		mateStore.each(function(rec){
//            		if(comm.indexOf('%') > 0)
//            			mateStore.getAt(i).set('cPrice', Number(Number(comm.replace('%', '')) * Number(rec.get('price') * 0.01)).toFixed(2));
//            		else
//            			mateStore.getAt(i).set('cPrice', Number(comm).toFixed(2));
            		mateStore.getAt(i).set('commission', comm);
            		i++;
            	});
        	}
        },{
        	xtype: 'button',
        	text: '保存全部',
        	iconCls: 'accept',
        	hidden : !parent.haveActionMenu('SaveProduct'),
        	handler: function(){
        		var jsonArray = [];  
                Ext.each(mateStore.data.items, function(item){  
                    jsonArray.push(item.data);
                });  
                Ext.MessageBox.show({
        			title: '请稍等',
        			msg: '正在保存配置 ...',
        			width: 200,
        			closable: false
                });
                Ext.Ajax.request({
                    method:'POST',
                    url: '/shop/saveCommission.atc',
                    params : {
						jsonStr : Ext.encode(jsonArray)
					},
					success : function(resp, opts) {
						Ext.MessageBox.hide();
						var result = Ext.decode(resp.responseText);
						if(result.success){
							Ext.example.msg('提示', result.msg);
							mateStore.reload();
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
        }]
    });
	
	var typeSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var hourSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var mateSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var typeCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	
	var hourCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	
	var mateCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	
	var typeGrid = new Ext.grid.GridPanel({
		columns : typeColumns,
		store : typeStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : typeSelModel,
		tbar : typeTbar, 
		stripeRows:true,
		forceFit: true,
		plugins: [typeCellEditing],
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var hourGrid = new Ext.grid.GridPanel({
		columns : hourColumns,
		store : hourStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : hourSelModel,
		tbar : hourTbar, 
		stripeRows:true,
		forceFit: true,
		plugins: [hourCellEditing],
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var mateTypeGrid = new Ext.grid.GridPanel({
		columns : mateTypeColumns,
		store : mateTypeStore, 
		region : 'center',
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
	        	mateStore.load({
					params: {
						'typeId' : record.data.id
					}
				});
	        }
	    }
	});
	
	var mateGrid = new Ext.grid.GridPanel({
		columns : mateColumns,
		store : mateStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : mateSelModel,
		tbar : mateTbar, 
		stripeRows:true,
		forceFit: true,
		plugins: [mateCellEditing],
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			title : '大类',
			iconCls : 'bricks',
			tools : [{
				type:'refresh',
				handler : function() {
					typeStore.reload();
				}
			}],
			width : '15%',
			split : true,
			region : 'west',
			layout : 'fit',
			border : false,
			items : [ typeGrid ]
		}, {
			title : '项目',
			iconCls : 'bricks',
			tools : [{
				type:'refresh',
				handler : function() {
					hourStore.reload();
				}
			},{
	            type:'help',
	            tooltip: '操作指引',
	            callback: function(panel, tool, event) {
	            	parent.global.openSystemHelpTab();
	            }
	        }],
			width : '35%',
			split : true,
			region : 'center',
			layout : 'fit',
			border : false,
			items : [ hourGrid ]
		}, {
			title : '类型',
			iconCls : 'bricks',
			region : 'east',
			width : '15%',
			split : true,
			layout : 'fit',
			border : false,
			items : [ mateTypeGrid ]
		}, {
			title : '商品',
			iconCls : 'bricks',
			tools : [{
				type:'refresh',
				handler : function() {
					mateStore.reload();
				}
			}],
			region : 'east',
			width : '35%',
			split : true,
			layout : 'fit',
			border : false,
			items : [ mateGrid ]
		}]
	});
	
	typeStore.load();
	
	hourStore.load();
	hourStore.on('load', function(){
    	var i = 0;
    	hourStore.each(function(rec){
    		if(rec.data.commission.indexOf('%') > 0)
    			hourStore.getAt(i).set('cPrice', Number(Number(rec.data.commission.replace('%', '')) * Number(rec.get('price') * 0.01)).toFixed(2));
    		else
    			hourStore.getAt(i).set('cPrice', Number(rec.data.commission).toFixed(2));
    		i++;
    	});
	});
	
//	mateStore.load();
//	mateStore.on('load', function(){
//    	var i = 0;
//    	mateStore.each(function(rec){
//    		if(rec.data.commission.indexOf('%') > 0)
//    			mateStore.getAt(i).set('cPrice', Number(Number(rec.data.commission.replace('%', '')) * Number(rec.get('price') * 0.01)).toFixed(2));
//    		else
//    			mateStore.getAt(i).set('cPrice', Number(rec.data.commission).toFixed(2));
//    		i++;
//    	});
//	});
	
	
});
Ext.require('Ext.chart.*');
Ext.require(['Ext.Window', 'Ext.layout.container.Fit', 'Ext.fx.target.Sprite', 'Ext.window.MessageBox']);

/**
 * 销售图表分析
 */
Ext.onReady(function () {
	
	var yearStore = Ext.create('Ext.data.ArrayStore', {
		fields : [ 'value', 'text' ],
		data : [ [ '2016', '2016' ], [ '2017', '2017' ] ]
	});
	
	var yearCombo = Ext.create('Ext.form.ComboBox', {
		labelWidth: 40, 
		labelAlign:'right',
		store: yearStore,
        enforceMaxLength: true,
        fieldLabel: '年份',
        valueField: 'value',
        displayField: 'text',
        editable : false
	});
	
	var monthStore = Ext.create('Ext.data.ArrayStore', {
		fields : [ 'value', 'text' ],
		data : [ [ '01', '1月' ], [ '02', '2月' ], [ '03', '3月' ], [ '04', '4月' ], [ '05', '5月' ], [ '06', '6月' ], 
		         [ '07', '7月' ], [ '08', '8月' ], [ '09', '9月' ], [ '10', '10月' ], [ '11', '11月' ], [ '12', '12月' ] ]
	});
	
	var monthCombo = Ext.create('Ext.form.ComboBox', {
		labelWidth: 40, 
		labelAlign:'right',
		store: monthStore,
        enforceMaxLength: true,
        fieldLabel: '月份',
        valueField: 'value',
        displayField: 'text',
        editable : false
	});
	
	var store1 = Ext.create('Ext.data.JsonStore', {
        fields: [{name: 'days',type: 'string'}, {name: 'price', type: 'float'}],
        proxy: {
            type: 'ajax',
            url : '/analysis/querySalesAnalysis.atc',
            reader : {
                root: 'saList'
            }
        }
    });
	
	var store2 = Ext.create('Ext.data.JsonStore', {
        fields: [{name: 'days',type: 'string'}, {name: 'price', type: 'float'}],
        proxy: {
            type: 'ajax',
            url : '/analysis/querySalesAnalysis.atc',
            reader : {
                root: 'saList'
            }
        }
    });
	
	var store3 = Ext.create('Ext.data.JsonStore', {
        fields: [{name: 'days',type: 'string'}, {name: 'price', type: 'float'}],
        proxy: {
            type: 'ajax',
            url : '/analysis/querySalesAnalysis.atc',
            reader : {
                root: 'saList'
            }
        }
    });
	
	var store4 = Ext.create('Ext.data.JsonStore', {
        fields: [{name: 'days',type: 'string'}, {name: 'price', type: 'float'}],
        proxy: {
            type: 'ajax',
            url : '/analysis/querySalesAnalysis.atc',
            reader : {
                root: 'saList'
            }
        }
    });
	
    var chart1 = Ext.create('Ext.chart.Chart', {
            style: 'background:#fff',
            animate: true,
            shadow: true,
            store: store1,
            axes: [{
                type: 'Numeric',
                position: 'left',
                fields: ['price'],
                label: {
                    renderer: Ext.util.Format.numberRenderer('0,0')
                },
//                title: 'Number of Hits',
                grid: true,
                minimum: 0
            }, {
                type: 'Category',
                position: 'bottom',
                fields: ['days'],
                title: '项目消费统计'
            }],
            series: [{
                type: 'column',
                axis: 'left',
                highlight: true,
                tips: {
                  trackMouse: true,
                  width: 140,
                  height: 28,
                  renderer: function(storeItem, item) {
                    this.setTitle(storeItem.get('days') + ': ' + storeItem.get('price') + ' 元');
                  }
                },
                label: {
                  display: 'insideEnd',
                  'text-anchor': 'middle',
                    field: 'data1',
                    renderer: Ext.util.Format.numberRenderer('0'),
                    orientation: 'vertical',
                    color: '#333'
                },
                xField: 'days',
                yField: 'price'
            }]
        });
    
    var chart2 = Ext.create('Ext.chart.Chart', {
        style: 'background:#fff',
        animate: true,
        shadow: true,
        store: store2,
        axes: [{
            type: 'Numeric',
            position: 'left',
            fields: ['price'],
            label: {
                renderer: Ext.util.Format.numberRenderer('0,0')
            },
            grid: true,
            minimum: 0
        }, {
            type: 'Category',
            position: 'bottom',
            fields: ['days'],
            title: '产品消费统计'
        }],
        series: [{
            type: 'column',
            axis: 'left',
            highlight: true,
            tips: {
              trackMouse: true,
              width: 140,
              height: 28,
              renderer: function(storeItem, item) {
                this.setTitle(storeItem.get('days') + ': ' + storeItem.get('price') + ' 元');
              }
            },
            label: {
              display: 'insideEnd',
              'text-anchor': 'middle',
                field: 'price',
                renderer: Ext.util.Format.numberRenderer('0'),
                orientation: 'vertical',
                color: '#333'
            },
            xField: 'days',
            yField: 'price'
        }]
    });
    
    var chart3 = Ext.create('Ext.chart.Chart', {
        xtype: 'chart',
        animate: true,
        store: store3,
        shadow: true,
        legend: {
            position: 'right'
        },
        insetPadding: 60,
        theme: 'Base:gradients',
        series: [{
            type: 'pie',
            field: 'price',
            showInLegend: true,
//            donut: donut,
            tips: {
              trackMouse: true,
              width: 180,
              height: 28,
              renderer: function(storeItem, item) {
                //calculate percentage.
                var total = 0;
                store1.each(function(rec) {
                    total += rec.get('price');
                });
                this.setTitle(storeItem.get('days') + ': ' + Math.round(storeItem.get('price') / total * 100) + '%' + ' ' + storeItem.get('price') + '元');
              }
            },
            highlight: {
              segment: {
                margin: 20
              }
            },
            label: {
                field: 'days',
                display: 'rotate',
                contrast: true,
                font: '18px Arial'
            }
        }]
    });
    
    var chart4 = Ext.create('Ext.chart.Chart', {
        xtype: 'chart',
        animate: true,
        store: store4,
        shadow: true,
        legend: {
            position: 'right'
        },
        insetPadding: 60,
        theme: 'Base:gradients',
        series: [{
            type: 'pie',
            field: 'price',
            showInLegend: true,
//            donut: donut,
            tips: {
              trackMouse: true,
              width: 180,
              height: 28,
              renderer: function(storeItem, item) {
                //calculate percentage.
                var total = 0;
                store1.each(function(rec) {
                    total += rec.get('price');
                });
                this.setTitle(storeItem.get('days') + ': ' + Math.round(storeItem.get('price') / total * 100) + '%' + ' ' + storeItem.get('price') + '元');
              }
            },
            highlight: {
              segment: {
                margin: 20
              }
            },
            label: {
                field: 'days',
                display: 'rotate',
                contrast: true,
                font: '18px Arial'
            }
        }]
    });


    var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
	    	tbar: [yearCombo, monthCombo, {
		    	text: ' 查 询 ',
		    	iconCls: 'preview', 
		        handler: function() {
		        	init();
//		        	Ext.MessageBox.confirm('Confirm Download', 'Would you like to download the chart as an image?', function(choice){
//		                if(choice == 'yes'){
//		                    chart.save({
//		                        type: 'image/png'
//		                    });
//		                }
//		            });
		        }
		    }],
			width : '100%',
			height: '50%',
			region : 'north',
			layout : 'fit',
			border : false,
			items : [{
				layout : 'border',
				items : [{
					width : '50%',
					region : 'west',
					layout : 'fit',
					border : false,
					items: [chart1]
				},{
					width : '50%',
					region : 'east',
					layout : 'fit',
					border : false,
					items: [chart2]
				}]
			}]
		}, {
			width : '100%',
			height: '50%',
			region : 'south',
			layout : 'fit',
			border : false,
			items : [{
				layout : 'border',
				items : [{
					width : '50%',
					region : 'west',
					layout : 'fit',
					border : false,
					items: [chart3]
				},{
					width : '50%',
					region : 'east',
					layout : 'fit',
					border : false,
					items: [chart4]
				}]
			}]
		}]
	});
    
    var myDate = new Date();
	myDate.getFullYear();
	myDate.getMonth();
	yearCombo.setValue(myDate.getFullYear());
	monthCombo.setValue((myDate.getMonth()+1 > 9) ? myDate.getMonth()+1 : '0' + (myDate.getMonth()+1));
	
    function init() {
    	store1.load({
    		params: {
    			type: 1,
    			goodsType: 1,
    			salesDate: yearCombo.getValue() + "/" + monthCombo.getValue()
    		}
    	});
    	store2.load({
    		params: {
    			type: 1,
    			goodsType: 2,
    			salesDate: yearCombo.getValue() + "/" + monthCombo.getValue()
    		}
    	});
    	store3.load({
    		params: {
    			type: 2,
    			goodsType: 1,
    			salesDate: yearCombo.getValue() + "/" + monthCombo.getValue()
    		}
    	});
    	store4.load({
    		params: {
    			type: 2,
    			goodsType: 2,
    			salesDate: yearCombo.getValue() + "/" + monthCombo.getValue()
    		}
    	});
    }
    
    init();
    
    
});
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
        fields: [{name: 'days',type: 'string'}, {name: 'count', type: 'int'}],
        proxy: {
            type: 'ajax',
            url : '/analysis/queryMemberAnalysis.atc',
            reader : {
                root: 'saList'
            }
        }
    });
	
	var chart = Ext.create('Ext.chart.Chart', {
        style: 'background:#fff',
        animate: true,
        store: store1,
        shadow: true,
        theme: 'Category1',
        legend: {
            position: 'right'
        },
        axes: [{
            type: 'Numeric',
            minimum: 0,
            position: 'left',
            fields: ['count'],
//            title: 'Number of Hits',
            minorTickSteps: 1,
            grid: {
                odd: {
                    opacity: 1,
                    fill: '#ddd',
                    stroke: '#bbb',
                    'stroke-width': 0.5
                }
            }
        }, {
            type: 'Category',
            position: 'bottom',
            fields: ['days'],
            title: '会员增长趋势'
        }],
        series: [{
            type: 'line',
            highlight: {
                size: 7,
                radius: 7
            },
            axis: 'left',
            xField: 'days',
            yField: 'count',
            markerConfig: {
                type: 'cross',
                size: 4,
                radius: 4,
                'stroke-width': 0
            }
//        }, {
//            type: 'line',
//            highlight: {
//                size: 7,
//                radius: 7
//            },
//            axis: 'left',
//            smooth: true,
//            xField: 'name',
//            yField: 'data2',
//            markerConfig: {
//                type: 'circle',
//                size: 4,
//                radius: 4,
//                'stroke-width': 0
//            }
//        }, {
//            type: 'line',
//            highlight: {
//                size: 7,
//                radius: 7
//            },
//            axis: 'left',
//            smooth: true,
//            fill: true,
//            xField: 'name',
//            yField: 'data3',
//            markerConfig: {
//                type: 'circle',
//                size: 4,
//                radius: 4,
//                'stroke-width': 0
//            }
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
		        }
		    }],
			width : '100%',
			height: '50%',
			region : 'center',
			layout : 'fit',
			border : false,
			items : [chart]
		}]
	});
    
    var myDate = new Date();
	myDate.getFullYear();
	myDate.getMonth();
	yearCombo.setValue(myDate.getFullYear());
	monthCombo.setValue((myDate.getMonth()+1 > 9) ? myDate.getMonth()+1 : '0' + (myDate.getMonth()+1));
	
    init();
    
    function init() {
    	store1.load({
    		params: {
    			createTime: yearCombo.getValue() + "/" + monthCombo.getValue()
    		}
    	});
    }
    
    
    
    
    
    
});
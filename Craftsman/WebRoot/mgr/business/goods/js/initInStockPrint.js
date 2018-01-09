Ext.onReady(function() {
	
	var number;
	if(typeof(inNumber) != 'undefined') number = inNumber;
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items: [{
            region: 'center',
            title: '打印入库单',
            iconCls: 'printer',
            tools:[{
                type:'help',
                tooltip: '操作指引',
                callback: function(panel, tool, event) {
                    // show help here
                }
            }],
            html: "<iframe src='/goods/InStockPrint.atc?inNumber=" + number + "' border='0' frameborder='no' style='width:100%; height:100%;'></iframe>"
        }]
	});
	
});
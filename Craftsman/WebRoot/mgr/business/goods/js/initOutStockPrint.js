Ext.onReady(function() {
	
	var jsonArray;
	if(typeof(jsonStr) != 'undefined') jsonArray = jsonStr;
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items: [{
            region: 'center',
            title: '打印退货单',
            iconCls: 'printer',
            tools:[{
                type:'help',
                tooltip: '操作指引',
                callback: function(panel, tool, event) {
                    // show help here
                }
            }],
            html: "<iframe src='/goods/OutStockPrint.atc?jsonStr=" + jsonStr + "' border='0' frameborder='no' style='width:100%; height:100%;'></iframe>"
        }]
	});
	
});
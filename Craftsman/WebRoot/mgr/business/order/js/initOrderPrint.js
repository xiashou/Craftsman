Ext.onReady(function() {
	
	var order;
	if(typeof(orderNo) != 'undefined') order = orderNo;
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items: [{
            region: 'center',
            xtype: 'tabpanel',
            title: '打印订单',
            iconCls: 'printer',
            tools:[{
                type:'help',
                tooltip: '操作指引',
                callback: function(panel, tool, event) {
                    // show help here
                }
            }],
            border: false,
            frame: false,
            items: [{
                rtl: false,
                title: '结算单',
                html: "<iframe src='/order/OrderPrint1.atc?orderNo=" + order + "' border='0' frameborder='no' style='width:100%; height:100%;'></iframe>"
            }, {
                title: '虚拟单',
                html: "<iframe src='/order/OrderPrint2.atc?orderNo=" + order + "' border='0' frameborder='no' style='width:100%; height:100%;'></iframe>"
//            }, {
//                title: '销售订单三',
//                html: "<iframe src='/order/OrderPrint3.atc?orderNo=" + order + "' border='0' frameborder='no' style='width:100%; height:100%;'></iframe>"
            }, {
                title: '结算小票',
                html: "<iframe src='/order/OrderPrint4.atc?orderNo=" + order + "' border='0' frameborder='no' style='width:100%; height:100%;'></iframe>"
            }]
        }]
	});
	
});
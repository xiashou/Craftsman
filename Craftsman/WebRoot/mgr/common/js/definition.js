var GDeptTypeStore, GSexStore, GRechargeTypeStore, GAreaShortStore, GAreaCodeStore, GOrderTypeStore, GOrderItemTypeStore, GOrderStatusStore, GAssetsTypeStore;
var GAreaShortStore, GAreaCodeStore, MOrderStatusStore, MOrderTypeStore, MPayTypeStore;

//组织架构类型，最高权限才能设置公司
if(FF == '0'){
	GDeptTypeStore = Ext.create('Ext.data.ArrayStore', {
		fields : [ 'value', 'text' ],
		data : [ [ 1, '公司' ], [ 2, '区域' ], [ 3, '店铺' ] ]
	});
} else if(FF == '1') {
	GDeptTypeStore = Ext.create('Ext.data.ArrayStore', {
		fields : [ 'value', 'text' ],
		data : [ [ 2, '区域' ], [ 3, '店铺' ] ]
	});
}

//性别
GSexStore = Ext.create('Ext.data.ArrayStore', {
	fields : [ 'value', 'text' ],
	data : [ [ 1, '男' ], [ 2, '女' ], [ 0, '未知' ] ]
});

//充值赠送类型
GRechargeTypeStore = Ext.create('Ext.data.ArrayStore', {
	fields : [ 'value', 'text' ],
	data : [ [ 1, '金额' ], [ 2, '服务' ], [ 3, '商品' ], [ 4, '卡券' ], [ 5, '积分' ], [ 6, '会员等级' ] ]
});

//订单类型
GOrderTypeStore = Ext.create('Ext.data.ArrayStore', {
	fields : [ 'value', 'text' ],
	data : [ [ 1, '项目消费' ], [ 2, '套餐购买' ], [ 3, '客户退货' ], [ 4, '会员充值' ], [ 5, '保险理赔' ], [ 6, '散客开单' ], [7, '套餐扣次'] ]
});

//订单项目类型
GOrderItemTypeStore = Ext.create('Ext.data.ArrayStore', {
	fields : [ 'value', 'text' ],
	data : [ [ 1, '项目' ], [ 2, '商品' ], [ 3, '赠送项目' ], [ 4, '赠送商品' ], [ 5, '赠送卡券' ], [6, '套餐'], [7, '会员充值'] ]
});

//订单状态
GOrderStatusStore = Ext.create('Ext.data.ArrayStore', {
	fields : [ 'value', 'text' ],
	data : [ [ 1, '已完成' ], [ 2, '未完成' ], [ 3, '待取车' ], [ 0, '已删除' ] ]
});

//商品出入记录类型
GGoodsRecordTypeStore = Ext.create('Ext.data.ArrayStore', {
	fields : [ 'value', 'text' ],
	data : [ [ 'I', '入库' ], [ 'S', '售出' ], [ 'R', '销售退货' ], [ 'G', '赠送' ], [ 'D', '删除' ], [ 'Q', '供应商退货' ] ]
});

//资产类型
GAssetsTypeStore = Ext.create('Ext.data.ArrayStore', {
	fields : [ 'value', 'text' ],
	data : [ [ 'C', '现金' ], [ 'A', '支付宝' ], [ 'W', '微信' ], [ 'B', '银行卡' ] ]
});

//商城订单状态
MOrderTypeStore = Ext.create('Ext.data.ArrayStore', {
	fields : [ 'value', 'text' ],
	data : [ [ 1, '普通订单' ], [ 2, '轮胎订单' ], [ 3, '保养订单' ] ]
});

//商城订单状态
MOrderStatusStore = Ext.create('Ext.data.ArrayStore', {
	fields : [ 'value', 'text' ],
	data : [ [ 1, '待付款' ], [ 2, '待发货' ], [ 3, '已发货' ], [ 4, '已收货' ], [ 5, '已完成' ], [ 0, '已取消' ] ]
});

//商城支付方式
MPayTypeStore = Ext.create('Ext.data.ArrayStore', {
	fields : [ 'value', 'text' ],
	data : [ [ 1, '微信支付' ], [ 2, '门店支付' ]]
});

//地区简称
GAreaShortStore = Ext.create('Ext.data.Store', {
	fields : [{name: 'id'}, {name: 'areaShort'}],  
	proxy:{
        type: 'ajax',
        url : '/base/queryAllAreaShort.atc',
        reader : {
            root: 'areaShortList'
        }
    }
});
GAreaShortStore.load();

//地区代码
GAreaCodeStore = Ext.create('Ext.data.Store', {
	autoLoad: true,
	fields : [{name: 'id'}, {name: 'areaCode'}],  
	proxy:{
        type: 'ajax',
        url : '/base/queryAllAreaCode.atc',
        reader : {
            root: 'areaCodeList'
        }
    }
});
GAreaCodeStore.load();

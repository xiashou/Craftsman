--创建一笔订单时，更改资金表中的入账金额
if (object_id('tgr_orderhead_insert', 'TR') is not null)
    drop trigger tgr_orderhead_insert
go
create trigger tgr_orderhead_insert
on order_head
    after insert --插入触发
as
    --定义变量
    declare @dept varchar(20), 
			@pwechat decimal(18, 2), 
			@palipay decimal(18, 2), 
			@pcash decimal(18, 2), 
			@pcard decimal(18, 2), 
			@ptransfer decimal(18, 2);
    
    select @dept = dept_code, @pwechat = pwechat, @palipay = palipay, @pcash = pcash, @pcard = pcard, @ptransfer = ptransfer from inserted;
    if(@pwechat > 0)
		update fico_assets set price = price + @pwechat where dept_code = @dept and code = 'W';
	if(@palipay > 0)
		update fico_assets set price = price + @pwechat where dept_code = @dept and code = 'A';
	if(@pcash > 0)
		update fico_assets set price = price + @pwechat where dept_code = @dept and code = 'C';
	if(@pcard > 0)
		update fico_assets set price = price + @pwechat where dept_code = @dept and code = 'B';
	if(@ptransfer > 0)
		update fico_assets set price = price + @pwechat where dept_code = @dept and code = 'B';
    
go
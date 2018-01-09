--改变会员库存，有则更新，无则添加
if (exists (select * from sys.objects where name = 'pro_memstock_update'))
    drop proc pro_memstock_update
go
create proc pro_memstock_update
@memId int,
@goodsId varchar(10),
@endDate varchar(20),
@source varchar(50),
@goodsType int,
@typeId varchar(20),
@goodsName varchar(300),
@number int
as 
begin
    declare @count int
    select @count=count(*) from mem_stock where mem_id=@memId and goods_id = @goodsId and end_date = @endDate and [source] = @source
    if @count>0
		update mem_stock set mem_id=@memId, goods_id = @goodsId, end_date = @endDate, [source] = @source, goods_type = @goodsType, [type_id] = @typeId, goods_name = @goodsName, number = number + @number
		where mem_id=@memId and goods_id = @goodsId and end_date = @endDate and [source] = @source
	else
		insert into mem_stock(mem_id, goods_id, end_date, [source], goods_type, [type_id], goods_name, number) values (@memId, @goodsId, @endDate, @source, @goodsType, @typeId, @goodsName, @number)
end
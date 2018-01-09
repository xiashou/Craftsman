CREATE FUNCTION F_Split(
    @string VARCHAR(255),--´ý·Ö¸î×Ö·û´®  
    @separator VARCHAR(10)--·Ö¸î·û  
)RETURNS @array TABLE(item VARCHAR(255))  
AS
BEGIN 
    DECLARE @begin INT,@end INT,@item VARCHAR(255)  
    SET @begin = 1  
    SET @end=charindex(@separator,@string,@begin)  
    WHILE(@end<>0)  
 
    BEGIN
        SET @item = substring(@string,@begin,@end-@begin)  
        INSERT INTO @array(item) VALUES(@item)  
        SET @begin = @end+1  
        SET @end=charindex(@separator,@string,@begin)  
    END  
    SET @item = substring(@string,@begin,len(@string)+1-@begin)  
    IF (len(@item)>0)  
        INSERT INTO @array(item) VALUES(substring(@string,@begin,len(@string)+1-@begin))  
    RETURN  
END



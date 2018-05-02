<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试页面</title>
</head>
<body>

<!-- <form id="inputForm" action="/edu_pay/smvc/user/modifyHead.v" method="post" enctype="multipart/form-data">
选择图片：<input type="file" id="img" name="img"/>
<br/>
图片名称：<input type="text" name="imgName"/>
<br/>
<button>提交</button> 
</form>-->

<form id="inputForm" action="/edu_pay/smvc/order/submitOrder.v" method="post">
地址ID：<input type="text" id="addressId" name="addressId"/>
<br/>
商品ID：<input type="text" name="goods[0].goodsId"/>&nbsp;&nbsp;
商品数量：<input type="text" name="goods[0].goodsCount"/>
<br/>
商品ID：<input type="text" name="goods[1].goodsId"/>&nbsp;&nbsp;
商品数量：<input type="text" name="goods[1].goodsCount"/>
<br/>
商品ID：<input type="text" name="goods[2].goodsId"/>&nbsp;&nbsp;
商品数量：<input type="text" name="goods[2].goodsCount"/>
<br/>
<button>提交</button> 
</form>


</body>
</html>
<%@page contentType="text/html;charset=UTF-8"%>

<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>内部办公自动化系统</title>
<%@ include file="/common/head.jsp" %>
<link rel="stylesheet" href="${ctx}/css/content.css" type="text/css" />

<style type="text/css">

a {font-size:16px} 
#未访问：蓝色、无下划线 
a:link {
	color: green; 
	text-decoration:none;
} 
#激活：红色 
a:active:{color: red; } 
#已访问：purple、无下划线 
a:visited {
	color:green;
	text-decoration:none;
}
a:hover {
	color: red; 
	text-decoration:underline;
} 
			.gonggao{
			    position:absolute;
			    top:30px;
			    left:700px;
			    width:100px;
			    height:100px;
			    border:1px solid #993300;
			    background-color:#ffffff;
			}
			.gonggao h1{
			    line-height:50px;
			    font-size:24px;
			    font-family:"黑体";
			    font-weight:bold;
			    text-align:center;
			}
			.gonggao div{
			    line-height:30px;
			    font-size:18px;
			    font-family:"黑体";
			    text-align:center;
			    color:#993300;
			}
			.gonggao div a,.gonggao div a:visited{
			    color:#993300;
			}

</style>

<script type="text/javascript">
$(document).ready(function () {
	$("#filter_EQI_ord_status").attr("value",'${param.filter_EQI_ord_status}');
});

</script>

</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}</p>
  <div class="sitemap">
    <span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="add2custom"><img class="pointer" onclick="add2custom('${authId}');return false;" id="aCustom" width="24" align="absmiddle" height="24" title="添加到常用操作" src="${ctx}/images/favorite.png"/></span>
  </div>
</div>
   <div class="titt">
     <h2><img alt="" src="${ctx}/images/web/311.gif" align="absmiddle" />${auth.label}</h2>
   </div>
   <form id="mainForm" action="order!list.action" method="post">
    <input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
    <input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
    <input type="hidden" name="page.order" id="order" value="${page.order}"/>
    <input type="hidden" name="authId" id="authId" value="${authId}"/>
    <input type="hidden" name="page.pageSize" id="pageSize" value="${page.pageSize}" />
    <input type="hidden" name="page.excelExp" id="excelExp" value="${page.excelExp}" />
  <!-- 查询条件 -->
   <div id="filter" class="filter">
    <div id="item" class="item">
    <div class="where">
        <div class="and">
           <span>订单号:</span>
           	<input type="text" class="small" name="filter_LIKES_ord_no" value="${param['filter_LIKES_ord_no']}" />
        </div>
        
        <div class="and">
           <span>地址:</span>
           	<input type="text" class="small" name="filter_LIKES_adr_id" value="${param['filter_LIKES_adr_id']}" />
        </div>
        
        <div class="and">
           <span>产品经理编号:</span>
           	<input type="text" class="small" name="filter_LIKES_ord_managerno" value="${param['filter_LIKES_ord_managerno']}" />
        </div>
        
        <div class="and">
           <span>产品序列号:</span>
           	<input type="text" class="small" name="filter_LIKES_pro_no" value="${param['filter_LIKES_pro_no']}" />
        </div>
        
        <div class="and">
        <span>状态:</span>
	        <select name="filter_EQI_ord_status"  id ="filter_EQI_ord_status" >
	        	<option value="">全部</option>
	        	<option value='0'>等待支付</option>
	        	<option value='1'>支付成功</option>
	        	<option value='2'>取消支付</option>
	        	<option value='3'>已绑定</option>
	        	<option value='4'>续费</option>
	        	<option value='5'>过期</option>
	        	
	        </select>
        </div>
        
    </div>

      <div class="space"></div>
      <div style="text-align: center;">
      <img src="${ctx}/images/b_select.gif" alt="" onclick="search();" class="pointer" align="middle"/>
      <img src="${ctx}/images/b_reset.gif" alt="" onclick="resetb();" class="pointer"align="middle"/>
      </div>
    </div>
    <div id="contral" class="contral pointer" onclick="contral(this);"><img src="${ctx}/images/f_close.gif" title="收起查询面板"/></div>
   </div>
  
   <div class="mainC">
	<!-- 增删改查...操作菜单-->
<%-- 	<div id="operate" class="operate" style="display: ${user.username=='admin'?'block':'none'}"> --%>
<%-- 	  <div><img src="${ctx}/images/b_add.gif" alt="增加" onclick="opr_input('partner!input.action',false,'${authId}');"/></div> --%>
<%-- 	  <div><img src="${ctx}/images/b_edit.gif" alt="修改" onclick="opr_update('partner!input.action','mainForm',false,'${authId}');" /></div> --%>
<%-- 	  <div><img src="${ctx}/images/b_del.gif" alt="删除" onclick="opr_delete('partner!delete.action','mainForm');" /></div> --%>
<!-- 	</div> -->
      <table class="mtab" cellpadding="2" cellspacing="1" border="0">
			<tr align="center">
				<th class="first"><input type="checkbox" id="box" name="box" onclick="checkedAll('ids')"/></th>
				<th>订单号</th>
				<th>地址</th>
				<th>产品类别</th>
				<th>产品序列号</th>
				<th>支付方式</th>
				<th>购买方式</th>
				<th>订单价格</th>
				<th>产品经理编号</th>
				<th>产品颜色</th>
				<th>联系方式</th>
				<th>收货人</th>
				<th>支付状态</th>
				<th>安装时间</th>
				<th>最后修改时间</th>
			</tr> 
			<c:forEach items="${page.result}" var="a" varStatus="c">
				<tr align="center">
					<td><input type="checkbox" value="${a.ord_id}" id="id_${a.ord_id}" name="ids"/></td>
					<td>${a.ord_no}</td>
					<td>${a.adr_id}</td>
					<td>
						<c:if test="${a.pro_id == 1}">壁挂式净水机</c:if>
						<c:if test="${a.pro_id == 2}">台式净水机</c:if>
						<c:if test="${a.pro_id == 3}">立式净水机</c:if>
					</td>
					<td>${a.pro_no}</td>
					<td>
						<c:if test="${a.ord_way == 0}">支付宝</c:if>
						<c:if test="${a.ord_way == 1}">微信</c:if>
						<c:if test="${a.ord_way == 2}">银联</c:if>
					</td>
					<td>
						<c:if test="${a.ord_protypeid == 0}">包年</c:if>
						<c:if test="${a.ord_protypeid == 1}">按流量付费</c:if>
					</td>
					<td>${a.ord_price}</td>
					<td>${a.ord_managerno}</td>
					<td>${a.ord_color}</td>
					<td>${a.ord_phone}</td>
					<td><c:if test="${!empty a.ord_receivename}">${a.ord_receivename}</c:if>
						<c:if test="${empty a.ord_receivename}">暂无收货人</c:if>
					</td>
					<td>
						<c:if test="${a.ord_status == 0}">等待支付</c:if>
						<c:if test="${a.ord_status == 1}">支付成功</c:if>
						<c:if test="${a.ord_status == 2}">取消支付</c:if>
						<c:if test="${a.ord_status == 3}">已绑定</c:if>
						<c:if test="${a.ord_status == 4}">续费</c:if>
						<c:if test="${a.ord_status == 5}">过期</c:if>
						
					</td>
					<td>${a.ord_sertime}</td>
					<td><c:if test="${!empty a.ord_modtime}">${a.ord_modtime}</c:if>
						<c:if test="${empty a.ord_modtime}">${a.ord_addtime}</c:if>
					</td>
					
					
				</tr>
			</c:forEach>
		</table>
	</div> 
</form>
<!-- 分页区域-->
<div id="page" class="page">
<wlps:page page="${page}" showPageSize="true" excelExp="false" /></div>

<!-- end--------------------------------------- -->

</body>
</html>

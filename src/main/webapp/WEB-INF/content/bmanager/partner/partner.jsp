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

</style>

<script type="text/javascript">
$(document).ready(function () {
	//$("#status").val("${param.filter_EQI_status}");
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
   <form id="mainForm" action="partner!list.action" method="post">
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
           <span>合伙人姓名:</span>
           <input type="text" class="small" name="parName"></input>
        </div>
        
        <div class="and">
           <span>合伙人编号:</span>
           <input type="text" class="small" name="parId"></input>
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
	<div id="operate" class="operate" style="display: ${user.username=='admin'?'block':'none'}">
	  <div><img src="${ctx}/images/b_add.gif" alt="增加" onclick="opr_input('partner!input.action',false,'${authId}');"/></div>
	  <div><img src="${ctx}/images/b_edit.gif" alt="修改" onclick="opr_update('partner!input.action','mainForm',false,'${authId}');" /></div>
	  <div><img src="${ctx}/images/b_del.gif" alt="删除" onclick="opr_delete('partner!delete.action','mainForm');" /></div>
	</div>
<!-- id,apk_digest,apk_name,apk_md5,apk_version,system_version,
apk_path,apk_url,apk_icon,icon_md5,apk_size,create_time -->
      <table class="mtab" cellpadding="2" cellspacing="1" border="0">
			<tr align="center">
				<th class="first"><input type="checkbox" id="box" name="box" onclick="checkedAll('ids')"/></th>
				<th>编号</th>
				<th>姓名</th>
				<th>级别</th>
<!-- 				<input type="hidden" value="上級合伙人id"></input> -->
				<th>上级合伙人</th>
				<th>上级合伙人编号</th>
				<th>负责地区</th>
				<th>地址</th>
				<th>联系电话</th>
				<th>销售设备的总台数</th>
				<th>详情</th>
			</tr> 
			<c:forEach items="${page.result}" var="a" varStatus="c">
				<tr align="center">
					<td><input type="checkbox" value="${a.id}" id="id_${a.id}" name="ids"/></td>
					<td>
						<c:if test="${a.PAR_LEVEL == 1}">
							<a href="partner!select.action?authId=${authId}&id=${a.id}"><b>${a.id}</b></a>
						</c:if>
						<c:if test="${a.PAR_LEVEL == 2}">
							<a href="partner!select.action?authId=${authId}&id=${a.id}"><b>${a.id}</b></a>
						</c:if>
						<c:if test="${a.PAR_LEVEL == 3}">
							<a href="partner!select.action?authId=${authId}&id=${a.id}"><b>${a.id}</b></a>
						</c:if>
						<c:if test="${a.PAR_LEVEL == 4}">${a.id}</c:if>
					</td>
					<td>${a.PAR_NAME}</td>
					<td>
						<c:if test="${a.PAR_LEVEL == 1}">省</c:if>
						<c:if test="${a.PAR_LEVEL == 2}">市</c:if>
						<c:if test="${a.PAR_LEVEL == 3}">区</c:if>
						<c:if test="${a.PAR_LEVEL == 4}">产品经理</c:if>
					</td>
<%-- 					<input type="hidden" value="${a.parParentid}"></input> --%>
					<td>${a.PAR_PARENT}</td>
					<td>${a.parParentid}</td>
					<td>${a.PAR_AREA}</td>
					<td>${a.PAR_ADDRESS}</td>
					<td>${a.PAR_PHONE}</td>
					<td>${a.par_sellernum}</td>
					<td><a href="orders!list.action?authId=${authId}">详情</a></td>
					<%-- <td><a href = "partner!selectresourcer.action?parParentid=${a.parParentid}">详情</a></td> --%>
				</tr>
			</c:forEach>
		</table>
	</div> 
	<center><input class="button" type="button" value="返回" onclick="history.back()" /></center>
</form>
<!-- 分页区域-->
<div id="page" class="page">
<wlps:page page="${page}" showPageSize="true" excelExp="false" /></div>

<!-- end--------------------------------------- -->

</body>
</html>

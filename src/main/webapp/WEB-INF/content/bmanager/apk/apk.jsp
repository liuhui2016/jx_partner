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
   <form id="mainForm" action="share!list.action" method="post">
    <input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
    <input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
    <input type="hidden" name="page.order" id="order" value="${page.order}"/>
    <input type="hidden" name="authId" id="authId" value="${authId}"/>
    <input type="hidden" name="page.pageSize" id="pageSize" value="${page.pageSize}" />
    <input type="hidden" name="page.excelExp" id="excelExp" value="${page.excelExp}" />
   <!-- 查询条件 -->
  
   <div class="mainC">
	<!-- 增删改查...操作菜单-->
	<div id="operate" class="operate">
	  <div><img src="${ctx}/images/b_add.gif" alt="增加" onclick="opr_input('apk!input.action',false,'${authId}');"/></div>
	  <%-- <div><img src="${ctx}/images/b_edit.gif" alt="修改" onclick="opr_update('share!input.action','mainForm',false,'${authId}');" /></div> --%>
	  <%-- <div><img src="${ctx}/images/b_del.gif" alt="删除" onclick="opr_delete('apk!delAll.action','mainForm');" /></div> --%>
	</div>
<!-- id,apk_digest,apk_name,apk_md5,apk_version,system_version,
apk_path,apk_url,apk_icon,icon_md5,apk_size,create_time -->
      <table class="mtab" cellpadding="2" cellspacing="1" border="0">
			<tr align="center">
				<th class="first"><input type="checkbox" id="box" name="box" onclick="checkedAll('ids')"/></th>
				<th>apk摘要</th>
				<th>apk名称</th>
				<th>版本号</th>
				<th>版本名称</th>
				<th>下载地址</th>
				<th>apk大小</th>
				<th>创建时间</th>
				<th>平台</th>
				<th>是否强制升级</th>
			</tr> 
			<c:forEach items="${page.result}" var="a" varStatus="c">
				<tr align="center">
					<td><input type="checkbox" value="${a.id}" id="id_${a.id}" name="ids"/></td>
					<td>${a.apkDigest}</td>
					<td>${a.apkName}</td>
					<td>${a.apkVersion}</td>
					<td>${a.systemVersion}</td>
					<td>${a.apkUrl}</td>
					<td>${a.apkSize}</td>
					<td><fmt:formatDate value="${a.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${a.type==0?"平板":(a.type==1?"安卓":"苹果")}</td>
					<td>${a.mustupgrade==0?"否":"是"}</td>
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

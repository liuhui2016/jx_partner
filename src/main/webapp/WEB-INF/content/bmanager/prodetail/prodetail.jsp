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

function clsGG(srcpath) {
	window.document.getElementById("viewimg").src = srcpath;
	document.all("gonggao").style.display = "block";
}
function closediv() {
	document.all("gonggao").style.display = "none";
}

</script>

</head>
<body>
<div class="currloca">
  <p>业务管理》产品明细管理</p>
  <div class="sitemap">
    <span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="add2custom"><img class="pointer" onclick="add2custom('${authId}');return false;" id="aCustom" width="24" align="absmiddle" height="24" title="添加到常用操作" src="${ctx}/images/favorite.png"/></span>
  </div>
</div>
   <div class="titt">
     <h2><img alt="" src="${ctx}/images/web/311.gif" align="absmiddle" />产品明细管理</h2>
   </div>
   <form id="dage" action="protatil!list.action" method="post">
    <div id="gonggao" class="gonggao" style="display: none">
				<img name="viewimg" id="viewimg" />
				<a href="javascript:closediv();">关闭</a>
			</div>
    <input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
    <input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
    <input type="hidden" name="page.order" id="order" value="${page.order}"/>
    <input type="hidden" name="authId" id="authId" value="${authId}"/>
    <input type="hidden" name="page.pageSize" id="pageSize" value="${page.pageSize}" />
    <input type="hidden" name="page.excelExp" id="excelExp" value="${page.excelExp}" />
   <div class="mainC">
<!-- 	增删改查...操作菜单 -->
	<div id="operate" class="operate">
	  <div><img src="${ctx}/images/b_add.gif" alt="增加" onclick="opr_input('prodetail!input.action',false,'${authId}');"/></div>
	  <div><img src="${ctx}/images/b_edit.gif" alt="修改" onclick="opr_update('/jx_smart/bmanager/view/view!input.action','dage',false,'${authId}');" /></div>
<%-- 	  <a href="/jx_smart/bmanager/view/view!input.action?id=${a.id}&authid=${authId}">修改</a> --%>
	  <div><img src="${ctx}/images/b_del.gif" alt="删除" onclick="opr_delete('prodetail!delete.action','dage');" /></div>
	</div>
<!-- id,apk_digest,apk_name,apk_md5,apk_version,system_version,
apk_path,apk_url,apk_icon,icon_md5,apk_size,create_time -->
      <table class="mtab" cellpadding="2" cellspacing="1" border="0">
			<tr align="center">
				<th class="first"><input type="checkbox" id="box" name="box" onclick="checkedAll('ids')"/></th>
<!-- 				<th>查看详情</th> -->
				<th>产品明细</th>
				<th>图片颜色</th>
				<th>颜色色值</th>
				<th>查看图片</th>
				<th>明细产品新增时间</th>
			</tr> 
			<c:forEach items="${page.result}" var="a" varStatus="c">
				<tr align="center">
					<td><input type="checkbox" value="${a.id}" id="id_${a.id}" name="ids"/></td>
					<td>${a.prod_name}</td>
					<td>${a.pic_color}</td>
					<td>${a.pic_tone}</td>
					<td>
						<c:if test="${a.pic_url != null && a.pic_url != ''}">
								&nbsp;&nbsp;<a href="#" id="icondelete" onclick="clsGG('${a.pic_url}');">查看图片</a>
						</c:if>
					</td>
					<td>${a.prod_addtime}</td>
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
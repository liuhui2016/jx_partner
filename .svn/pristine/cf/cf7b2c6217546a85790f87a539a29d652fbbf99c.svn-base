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


$(function() {
	
	// 选中跳转
	$(".tab_sel tr:gt(0)").bind("mouseover", function() {
		$(this).addClass("rowchange");
	}).bind("mouseout", function() {
		$(this).removeClass("rowchange");
	}).bind("click", function() {
		$('input[type=radio]').removeAttr("checked");
		$(this).children().first().children().attr("checked", true);
		ok();
	});

	// 弹出层用lhgdialog
	var DG = frameElement.lhgDG;
	DG.addBtn('ok', '确定', ok);

	function ok() {
		// 这里写你要操作的代码，最后写刷新代码
		var $radio = $("input[name=ids][checked]").val();
		var name = $("td[name=PAR_NAME_"+$radio+"]").text();
		if ($radio.length != 0) {
			parent.$('input[id="parParentid"]').val($radio);
			parent.$('input[id="PAR_PARENT"]').val(name);
		}

		// 确定按钮回调函数
		if ('${param.type}'.indexOf("-") == -1) {
			if (typeof (eval('parent.callback_${param.type}')) == "function") {
				eval('parent.callback_${param.type}($radio.val(),function(){DG.cancel();});');
			} else if (typeof (eval('parent.callback_${param.type}_name')) == "function") {
				eval('parent.callback_${param.type}_name($radio.val(),function(){DG.cancel();},"${name}");');
			} else if (!window.frameElement.lhgDG.parent) {
				DG.cancel();
			}
		}
	}

});

function checkid(cid) {
	window.returnValue = cid;
	this.close();
}

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
     <h2><img alt="" src="${ctx}/images/web/311.gif" align="absmiddle" />选择上级合伙人</h2>
   </div>
   <form id="mainForm" action="partner!selectresourcer.action?par_level=${par_level}" method="post">
    <input type="hidden" name="page.pageNo" id="pageNo" value="${pageResourcer.pageNo}"/>
    <input type="hidden" name="page.orderBy" id="orderBy" value="${pageResourcer.orderBy}"/>
    <input type="hidden" name="page.order" id="order" value="${pageResourcer.order}"/>
    <input type="hidden" name="authId" id="authId" value="${authId}"/>
    <input type="hidden" name="page.pageSize" id="pageSize" value="${pageResourcer.pageSize}" />
    <input type="hidden" name="page.excelExp" id="excelExp" value="${pageResourcer.excelExp}" />
  <!-- 查询条件 -->
   <div id="filter" class="filter">
    <div id="item" class="item">
    <div class="where">
        <div class="and">
           <span>合伙人姓名:</span>
				<input type="text" class="small" name="par_names"></input>
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
      <table class="mtab tab_sel" cellpadding="2" cellspacing="1" border="0">
			<tr align="center">
				<th>选择</th>
				<th>合伙人编号</th>
				<th>合伙人姓名</th>
				<th>合伙人级别</th>
				<input type="hidden" value="上級合伙人id"></input>
				<th>上级合伙人</th>
				<th>合伙人所在地区</th>
				<th>地址</th>
				<th>合伙人联系电话</th>
			</tr> 
			<c:forEach items="${pageResourcer.result}" var="a" varStatus="c">
				<tr align="center">
					<td><input type="radio" value="${a.id}" id="id_${a.id}" name="ids"/></td>
					<td>${a.id}</td>
					<td name="PAR_NAME_${a.id}">${a.PAR_NAME}</td>
					<td>
						<c:if test="${a.PAR_LEVEL == 1}">省</c:if>
						<c:if test="${a.PAR_LEVEL == 2}">市</c:if>
						<c:if test="${a.PAR_LEVEL == 3}">区</c:if>
						<c:if test="${a.PAR_LEVEL == 4}">产品经理</c:if>
					</td>
					<input type="hidden" value="${a.parParentid}"></input>
					<td>${a.PAR_PARENT}</td>
					<td>${a.PAR_AREA}</td>
					<td>${a.PAR_ADDRESS}</td>
					<td>${a.PAR_PHONE}</td>
				</tr>
			</c:forEach>
		</table>
	</div> 
</form>
<!-- 分页区域-->
<div id="page" class="page">
<wlps:page page="${pageResourcer}" showPageSize="true" excelExp="false" /></div>
<!-- end--------------------------------------- -->

</body>
</html>

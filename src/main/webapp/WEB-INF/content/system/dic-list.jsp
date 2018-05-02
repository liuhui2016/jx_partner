<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ page import="com.game.modules.utils.SpringContextHolder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<%@ include file="/common/head.jsp" %>
	<script type="text/javascript">
	<!--  初始化select型变量值  -->
	$(document).ready(function() {
		$("#filter_EQI_status").attr("value","${param['filter_EQI_status']}");
	});
	</script>
</head>

<body>
<div class="currloca">
  <p>${auth.fullMenu}</p>
  <div class="sitemap">
  	<span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="add2custom"><img class="pointer" onclick="add2custom('${authId}');return false;" id="aCustom" width="19" height="18" title="添加到常用操作" src="../images/add_custom.gif"/></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${auth.label}</h2></div>
  
  <form id="mainForm" action="dic!dict.action" method="post">
  <input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
	<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
	<input type="hidden" name="page.order" id="order" value="${page.order}"/>
	<input type="hidden" name="authId" id="authId" value="${authId}"/>
	<input type="hidden" name="page.pageSize" id="pageSize" value="${page.pageSize}" />
	<input type="hidden" name="page.excelExp" id="excelExp" value="${page.excelExp}" />
	
	  <!-- 查询条件 -->
	  <div id="filter" class="filter">
	    <div id="item" class="item">
	      <span>名称:</span><input type="text" class="small" name="filter_LIKES_name" value="${param.filter_LIKES_name }" size="9"/>
	      <div class="space"></div>
	      <div style="text-align: center;">
	      <img src="../images/b_select.gif" alt="" onclick="search();" class="pointer" align="middle"/>
	      <img src="../images/b_reset.gif" alt="" onclick="resetb();" class="pointer"align="middle"/>
	      </div>
	    </div>
	    <div id="contral" class="contral pointer" onclick="contral(this);"><img src="../images/f_close.gif" title="收起查询面板"/></div>
	  </div>
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <!-- 增删改查...操作菜单-->
  <div id="operate" class="operate">
    <div><security:authorize ifAnyGranted="A_base_DIC_add"><img src="../images/b_add.gif" alt="增加" onclick="opr_input('dic!input.action?flag=${flag}',false,'${authId}');"/></security:authorize></div>
  </div>
  
  <!-- 列表区域-->
  <div id="content" class="content">
    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
    <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
		  <tr>
		      <th class="first"><a href="javascript:sort('code','asc')">编码</a></th>
		      <th><a href="javascript:sort('name','asc')">名称</a></th>
		      <th>附加一</th>
		      <th>附加二</th>
		      <th>附加三</th>
		      <th>描述</th>
		      <th class="last">操作</th>
		    </tr>
			
			<c:forEach items="${page.result}" var="c">
				<tr>
					<td height="22"><a href="${ctx}/system/dic!detail.action?id=${c.id}&authId=${authId}&flag=${flag}">${c.code}</a></td>
					<td>${c.name}</td>
					<td>${c.val1}</td>
					<td>${c.val2}</td>
					<td>${c.val3}</td>
					<td>${c.description}</td>
					<td>
   						<security:authorize ifAnyGranted="A_base_DIC_add">
                                    <a href="dic!input.action?id=${c.id}&authId=${authId}&flag=${flag}"><img
                                        src="${ctx}/images/edt.gif" width="16" height="16" border="0"
                                        alt="修改" /></a>
                                </security:authorize>
                                &nbsp;
					</td>
				</tr>
			</c:forEach>
    </tbody>
    </table>
    </div>
  </div>
  </form>
  
  <!-- 分页区域-->
  <div id="page" class="page">
  	<wlps:page page="${page}" showPageSize="true" excelExp="false" />
  </div>  
  
</div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.game.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${auth.label}</title>
	<%@ include file="/common/head.jsp" %>
</head>

<body>
<div class="currloca">
  <p>${auth.fullMenu}</p>
  <div class="sitemap">
    <span id="add2custom"><img class="pointer" onclick="add2custom('${authId}');return false;" id="aCustom" width="19" height="18" title="添加到常用操作" src="../images/add_custom.gif"/></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${auth.label}</h2></div>
  
  <form id="mainForm" action="journal!historyList.action" method="post">
  <input type="hidden" name="historyPage.pageNo" id="pageNo" value="${historyPage.pageNo}"/>
	<input type="hidden" name="historyPage.orderBy" id="orderBy" value="${historyPage.orderBy}"/>
	<input type="hidden" name="historyPage.order" id="order" value="${historyPage.order}"/>
	<input type="hidden" name="domainId" id="domainId" value="${domainId}"/>
	<input type="hidden" name="authId" id="authId" value="${authId}"/>
	<input type="hidden" name="historyPage.pageSize" id="pageSize" value="${historyPage.pageSize}" />
	<input type="hidden" name="historyPage.excelExp" id="excelExp" value="${historyPage.excelExp}" />
	
	
  <!-- 查询条件 -->
  <div id="filter" class="filter">
    <div id="item" class="item">
      <span>操作类型:</span>
      <select id="filter_EQS_operationType" name="filter_EQS_operationType">
      	<option value="">全部</option>
      	<s:iterator value="ormTypeList" status="stat">
      		<option value="${value}"<c:if test="${value==param['filter_EQS_operationType']}">selected="selected"</c:if>>${label}</option>
      	</s:iterator>
      </select>
      <span>对象ID:</span><input type="text" class="small" name="filter_EQL_entityId" value="${param['filter_EQL_entityId']}" size="9"/>
      <span>操作对象:</span><input type="text" class="small" name="filter_LIKES_entity" value="${param['filter_LIKES_entity']}" size="9"/>
      <span>操作人:</span><input type="text" class="small" name="filter_EQS_operator" value="${param['filter_EQS_operator']}" size="9"/>
      <span style="margin-left:20px; width: 100px;">SessionCode:</span><input type="text" class="small" name="filter_EQI_sessionCode" value="${param['filter_EQI_sessionCode']}" size="9"/>
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
  
  <!-- 列表区域-->
  <div id="content" class="content">
    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
    <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
		  <tr>
		      <th class="first"><a href="javascript:sort('id','asc')">序号</a></th>
		      <th><a href="javascript:sort('entity','asc')">操作对象</a></th>
		      <th><a href="javascript:sort('entityId','asc')">对象ID</a></th>
		      <th><a href="javascript:sort('operationType','asc')">操作类型</a></th>
		      <th><a href="javascript:sort('sessionCode','asc')">sessionCode</a></th>
		      <th><a href="javascript:sort('operator','asc')">操作人</a></th>
		      <th class="last"><a href="javascript:sort('timestamp','asc')">操作时间</a></th>
		    </tr>

			<s:iterator value="historyPage.result">
				<tr>
					<td><a href="journal!detail.action?sessionCode=${sessionCode}&authId=${authId}">${id}</a></td>
					<td>${entity}</td>
					<td>${entityId}</td>
					<td>${operationName}</td>
					<td>${sessionCode}</td>
					<td>${operator}</td>
					<td><fmt:formatDate value="${timestamp}" pattern="yyyy-MM-dd HH:mm"/></td>
				</tr>
			</s:iterator>
    </tbody>
    </table>
    </div>
  </div>
  </form>
  
  <!-- 分页区域-->
  <div id="page" class="page">
  	<wlps:page page="${historyPage}" showPageSize="true" excelExp="false" />
  </div>  
  
</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.game.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${auth.label}</title>
	<%@ include file="/common/head.jsp" %>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#filter_EQI_operationCode").attr("value","${param['filter_EQI_operationCode']}");
	});
	</script>
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
  
  <form id="mainForm" action="journal.action" method="post">
  <input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
	<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
	<input type="hidden" name="page.order" id="order" value="${page.order}"/>
	<input type="hidden" name="domainId" id="domainId" value="${domainId}"/>
	<input type="hidden" name="authId" id="authId" value="${authId}"/>
	<input type="hidden" name="page.pageSize" id="pageSize" value="${page.pageSize}" />
	<input type="hidden" name="page.excelExp" id="excelExp" value="${page.excelExp}" />
	
	
  <!-- 查询条件 -->
  <div id="filter" class="filter">
    <div id="item" class="item">
      <div class="where">
	      <div class="and">
	      <span>业务模块:</span>
	      <select id="filter_EQI_operationCode" class="small" name="filter_EQI_operationCode">
	      	<option value="">全部</option>
	      	<s:iterator value="operateCodeLabelValueList" status="stat">
	      		<option value="${value}">${label}</option>
	      	</s:iterator>
	      </select>
	      </div>
	      <div class="and">
	      <span>用户登录名:</span><input type="text" class="small" name="filter_LIKES_operatorName" value="${param['filter_LIKES_operatorName']}" size="9"/>
	      </div>
	      <div class="and">
	      <span>IP地址:</span><input type="text" class="small" name="filter_LIKES_operatorIpAddr" value="${param['filter_LIKES_operatorIpAddr']}" size="9"/>
	      </div>
	      <div class="and">
	      <span>所属域:</span><input type="text" class="small" name="filter_LIKES_domainLabel" value="${param['filter_LIKES_domainLabel']}" size="9"/>
	      </div>
      </div>
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
		      <th><a href="javascript:sort('operatorName','asc')">用户登录名</a></th>
		      <th><a href="javascript:sort('domainLabel','asc')">所在域</a></th>
		      <th><a href="javascript:sort('operationCode','asc')">业务模块</a></th>
		      <th><a href="javascript:sort('operatorIpAddr','asc')">IP地址</a></th>
		      <th><a href="javascript:sort('creatTime','asc')">创建时间</a></th>
		      <th class="last">描述</th>
		    </tr>

			<s:iterator value="page.result">
				<tr>
					<td><a href="journal!detail.action?sessionCode=${sessionCode}&authId=${authId}&id=${id}">${id}</a></td>
					<td>${operatorName}</td>
					<td>${domainLabel}</td>
					<td>${operationName}</td>					
					<td>${operatorIpAddr}</td>
					<td><fmt:formatDate value="${creatTime}" pattern="yyyy-MM-dd HH:mm"/></td>
					<td>${description}</td>
				</tr>
			</s:iterator>
    </tbody>
    </table>
    </div>
  </div>
  </form>
  
  <!-- 分页区域-->
  <div id="page" class="page">
  	<wlps:page page="${page}" showPageSize="true" excelExp="true" />
  </div>  
  
</div>
</body>
</html>
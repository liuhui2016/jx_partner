<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.game.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<%@ include file="/common/head.jsp" %>
	<style type="text/css">
		.tab_cont td{padding:1px 10px;}
	</style>
</head>

<body>
<div class="currloca">
  <p>${auth.fullMenu}&nbsp;»&nbsp;<span>查看日志</span></p>
  <div class="sitemap">
  	<span id="add2custom"></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>查看日志</h2></div>
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <!-- 列表区域-->
  <div id="content">
  		<table  cellspacing="0" cellpadding="0" border="0">
  			<c:if test="${entity != null }">
	  			<tr>
	  				<td colspan="2">
	  					<table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
	  						<tr>
						      <th class="first">序号</th>
						      <th>用户登录名</th>
						      <th>所在域</th>
						      <th>业务模块</th>
						      <th>IP地址</th>
						      <th>创建时间</th>
						      <th class="last">描述</th>
						    </tr>
						    <tr>
								<td>${entity.id}</td>
								<td>${entity.operatorName}</td>
								<td>${entity.domainLabel}</td>
								<td>${entity.operationName}</td>					
								<td>${entity.operatorIpAddr}</td>
								<td><fmt:formatDate value="${entity.creatTime}" pattern="yyyy-MM-dd HH:mm"/></td>
								<td>${entity.description}</td>
							</tr>
	  					</table>
	  				</td>
	  				<td colspan="2">&nbsp;</td>
	  			</tr>
  			</c:if>
  			<s:iterator value="histories" id="obj" status="stat">
  				<tr valign="top">
	  				<td width="width:50%">
	  					<table class="tab_cont" width="99%" cellspacing="0" cellpadding="0" border="0">
						    <tbody>
						       <tr>
									<th class="first">序号</th><td>${obj.id}</td>
							   </tr>
							   <tr>					
									<th class="first">操作对象</th><td>${obj.entity}</td>
							   </tr>
							   <tr>	
									<th class="first">对象ID</th><td>${obj.entityId}</td>
							   </tr>
							   <tr>	
									<th class="first">操作类型</th><td>${obj.operationType}</td>
							   </tr>
							   <tr>	
									<th class="first">操作人</th><td>${obj.operator}</td>
							   </tr>
							   <tr>	
									<th class="first">操作时间</th><td>${obj.timestamp}</td>
							   </tr>
							   <tr>	
									<th class="first">描述</th><td>
										
										<textarea rows="8" cols="65" style="border:0">${obj.description}</textarea>
									</td>
							  </tr>
							  <tr>
								<th class="first" width="130"></th>
								<th class="last">
									<s:if test="#stat.last">
										<input class="button" type="button" value="返回" onclick="history.back()"/>
									</s:if>
								</th>
							</tr>
						    </tbody>
						</table>
	  				</td>
	  				<td width="width:50%">
	  					<table class="tab_cont" width="99%" cellspacing="0" cellpadding="0" border="0" style="float:right">
							<tbody>
					  			<tr>
					  				<th class="first">序号</th>
						            <th>操作属性</th>
						            <th>修改前值</th>
						            <th class="last">修改后值</th>
					  			</tr>
					  			<s:iterator value="#obj.historyItem">
					  				<tr>
						  				<td>${id}</td>
						  				<td>${property}</td>
						  				<td>${previousValue}</td>
						  				<td>${newValue}</td>
						  			</tr>
					  			</s:iterator>
				  			</tbody>
				  		</table>
	  				</td>
	  			</tr>
	  			<tr><td colspan="2" height="2"></td></tr>
  			</s:iterator>
  		</table>
  </div>
</div>
</body>
</html>
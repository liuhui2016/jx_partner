<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>权限信息</title>
	<%@ include file="/common/head.jsp" %>
    <script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
	<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
</head>

<body>
<div class="currloca">
  <p>${auth.fullMenu}&nbsp;»&nbsp;<span>权限信息</span></p>
  <div class="sitemap">
  	<span id="add2custom"></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>权限信息</h2></div>
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
    <!-- 列表区域-->
  <div id="content" class="content input">
    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
	    <table class="tab_cont" width="40%" cellspacing="0" cellpadding="0" border="0" align="left">
		    <tbody>
	        <tr>
		      <th class="first" width="130">标准信息</th>
		      <th class="last"></th>
		    </tr>
		    <tr>
				<td>权限名称:</td>
				<td>${authority.label}</td>
			</tr>
			<tr>
				<td>权限编码:</td>
				<td>${authority.name}</td>
			</tr>
			<tr>
				<td>所属资源:</td>
				<td>
				<s:if test="#request.authority.resources!=null">
				<table class="noborder">
					<% int colNum = 0; int i=0; %>
						<tr>
						<c:forEach var="res" items="${authority.resources}" varStatus="sta">
							<td>
								 ${res.label }
								<% colNum++; %>
							</td>
								<c:if test="<%=colNum % 6 == 0%>">
									</tr>
								</c:if>
						</c:forEach>
					</table>
					</s:if>
					</td>
			<tr>
				<td>是否对应菜单:</td>
				<td>
					<c:if test="${authority.menu==true}">是</c:if>
					<c:if test="${authority.menu==false}">否</c:if>
				</td>
			</tr>
			<tr>
				<td>排序数值:</td>
				<td>${authority.position}</td>
			</tr>
			<tr>
				<td>权限资源:</td>
				<td>
					${authority.resource.label}
		      	</td>
			</tr>
			<tr>
				<td>上级权限:</td>
				<td>
					${authority.parent.label}
		      	</td>
			</tr>
		    <tr>
		      <th class="first" width="130">附加信息</th>
		      <th class="last"></th>
		    </tr>
			<tr>
				<td>权限描述:</td>
				<td ><s:textarea name="authority.description" cols="60" rows="4" theme="simple"></s:textarea></td>
			</tr>
			<tr>
				<th class="first" width="130"></th>
					<th class="last">
					<input class="button" type="button" value="返回" onclick="history.back()"/>
				</th>
			</tr>
		    </tbody>
	    </table>
  	</div>
  </div>
</div>

</body>
</html>

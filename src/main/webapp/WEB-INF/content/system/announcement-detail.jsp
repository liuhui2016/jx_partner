<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<%@ include file="/common/head.jsp" %>	
</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}&nbsp;»&nbsp;<span>内容查看</span></p>
  <div class="sitemap">
  	<span id="add2custom"></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>查看公告</h2></div>
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
	  <!-- 列表区域-->
	  <div id="content" class="content input">
	    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
			<table class="tab_cont" width="" cellspacing="0" cellpadding="0" border="0" align="left">
				<tbody>
				    <tr>
				      <th class="first" width="130">标准信息</th>
				      <th class="last" style="min-width: 160px"></th>
				    </tr>
				    <tr>
						<td  class="right">公告名称:</td>
						<td>${announcement.title}</td>
					</tr>
					<tr>
						<td  class="right">开始时间:</td>
						<td><fmt:formatDate value="${announcement.start}" pattern="yyyy-MM-dd"/></td>
					</tr>
					<tr>
						<td  class="right">结束时间:</td>
						<td><fmt:formatDate value="${announcement.end}" pattern="yyyy-MM-dd"/></td>
					</tr>
					<tr>
						<td  class="right">公告类型:</td>
						<td>
							<c:forEach items="${annoTypeList}" var="anno">
								<c:if test="${anno.value == announcement.type}">${anno.label}</c:if>
							</c:forEach>
						</td>
					</tr>			
					<tr>
						<td  class="right">创  建  人:</td>
						<td>${announcement.createBy}</td>
					</tr>			
					<tr>
						<td  class="right">创建时间:</td>
						<td><fmt:formatDate value="${announcement.createTime}" pattern="yyyy-MM-dd"/></td>
					</tr>			
					<tr>
						<td  class="right">公告内容:</td>
						<td>
							${announcement.content}
						</td>
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

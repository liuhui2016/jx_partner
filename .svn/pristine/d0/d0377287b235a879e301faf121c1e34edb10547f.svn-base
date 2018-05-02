<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>内容修改</title>
	<%@ include file="/common/head.jsp" %>
    <script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
	<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
	<script>
		$(document).ready(function() {
			$("#position").focus();
			$("#inputForm").validate({
				rules: {
		 	 		position:{
						number:true
		 	 		}
	        	}
			});
		});
	</script>
	
</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}&nbsp;»&nbsp;<span>内容修改</span></p>
  <div class="sitemap">
  	<span id="add2custom"></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>内容修改</h2></div>
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <form id="inputForm" action="custom-menu!save.action" method="post">
	  <!-- 列表区域-->
	  <div id="content" class="content input">
	    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
	    	<input type="hidden" name="id" value="${id}"/>
			<input type="hidden" name="authId" value="${authId}"/>
			<table class="tab_cont" width="" cellspacing="0" cellpadding="0" border="0" align="left">
				<tbody>
				    <tr>
				      <th class="first" width="130">标准信息</th>
				      <th class="last"></th>
				    </tr>
				    <tr>
						<td class="right">权限名称:</td>
						<td>${auth.label}</td>
					</tr>
					<tr>
						<td  class="right">资源字符串:</td>
						<td>${auth.resource.resString}</td>
					</tr>
					<tr>
						<td class="right">资源描述:</td>
						<td>${description}</td>
					</tr>
					<tr>
						<td class="right">资源类型:</td>
						<td>
							<s:iterator value="resTypeLabelValueList" id="resTypeLabelValue">
								<s:if test="#resTypeLabelValue.value == auth.resource.resType">
									<s:property value="#resTypeLabelValue.label"/>
								</s:if>
							</s:iterator>
						</td>
					</tr>			
					<tr>
						<td  class="right">显示序号:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
						<td><span tip="前台根据此序号进行排序显示"></span>
							<s:textfield name="position" theme="simple" id="position_id"></s:textfield>
						</td>
					</tr>			
					<tr>
						<th class="first" width="130"></th>
						<th class="last">
								<input class="button" type="submit" value="提交"/>&nbsp;&nbsp;
							<input class="button" type="button" value="返回" onclick="history.back()"/>
						</th>
					</tr>
				</tbody>
			</table>
	    </div>
	  </div>
  </form>
</div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>内容修改</title>
	<%@ include file="/common/head.jsp" %>
	<script type="text/javascript" src="${ctx}/js/jQuery/ui/jquery.ui.datepicker-zh-CN.js"></script>	
	<script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
	<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
	<script src="${ctx}/js/jQuery/timepicker.js" type="text/javascript"></script>
<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#label").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
					"entity.defaultfrom": {
						required: true
						//,email: true
					},
					"entity.host": {
						required: true
					},
					"entity.username": {
						required: true
					},
					"entity.password": {
						required: true
					}
				},
				messages: {
					"entity.defaultfrom": {
						required: "请输入发件人地址",
						email: "电子邮件格式不对！"
					},
					"entity.host": {
						required: "请输入主机地址"
					},
					"entity.username": {
						required: "请输入用户名"
					},
					"entity.password": {
						required: "请输入密码"
					}
				}
			})	;
		});
	</script>
	
	<script type="text/javascript">
	$(document).ready(function() {
	    $("#manufactureDate").datepicker($.datepicker.regional['zh-CN']);
	});
	</script>
</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}&nbsp;»&nbsp;<span>${id==null?'Mail服务器设置':'Mail服务器设置' }</span></p>
  <div class="sitemap">
  	<span id="add2custom"><img class="pointer" onclick="add2custom('${authId}');return false;" id="aCustom" width="19" height="18" title="添加到常用操作" src="../images/add_custom.gif"/></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${id==null?'Mail服务器设置':'Mail服务器设置' }</h2></div>
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <form id="inputForm" action="mailconf!save.action" method="post">
  <input type="hidden" name="entity.mail" value="mail" />
  <input type="hidden" name="entity.id" value="${entity.id}" />
<input type="hidden" name="authId" value="${authId}"/>
	  <!-- 列表区域-->
	  <div id="content" class="content input">
	    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
			<table class="tab_cont" cellspacing="0" cellpadding="0" border="0" align="left">
				<tbody>
				    <tr>
				      <th class="first" width="130">标准信息</th>
				      <th class="last"></th>
				    </tr>				   
					<tr>
						<td  class="right"><span class="red">*</span>发件人地址:</td>
						<td><input type="text" id="from" name="entity.defaultfrom" size="40" maxlength="255" value="${entity.defaultfrom}"/></td>
					</tr>
					<tr>
						<td  class="right"><span class="red">*</span>主机:</td>
						<td><input type="text" id="host" name="entity.host" size="40" maxlength="255" value="${entity.host}"/></td>
					</tr>
					<!-- tr>
						<td  class="right"><span class="red">*</span>协议:</td>
						<td><input type="text" id="protocol" name="protocol" size="40" maxlength="255" value="${protocol}"/></td>
					</tr -->
					<tr>
						<td  class="right"><span class="red">*</span>用户名:</td>
						<td><input type="text" id="username" name="entity.username" size="40" maxlength="255" value="${entity.username}"/></td>
					</tr>
					<tr>
						<td  class="right"><span class="red">*</span>密码:</td>
						<td><input type="password" id="password" name="entity.password" size="41" maxlength="255" value="${entity.password}"/></td>
					</tr>
					<tr>
						<td class="right"></td>
		<td align="left">
			<input type="submit" value="提交" />&nbsp; 
			<input type="button" value="取消" onclick="history.back()"/>
		</td>
					</tr>
				</tbody>
			</table>
	    </div>
	  </div>
  </form>
</div>
</body>
</html>
